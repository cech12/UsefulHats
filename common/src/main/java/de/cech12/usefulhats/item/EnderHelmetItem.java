package de.cech12.usefulhats.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;

public class EnderHelmetItem extends AbstractHatItem implements IRightClickListener {

    public EnderHelmetItem() {
        super(HatArmorMaterials.ENDER, rawColorFromRGB(43, 203, 175), Services.CONFIG::getEnderHelmetDurability, Services.CONFIG::isEnderHelmetDamageEnabled);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("item.usefulhats.ender_helmet.desc.define_teleport").withStyle(ChatFormatting.BLUE));
        Position position = getPosition(stack);
        if (position != null) {
            super.appendHoverText(stack, context, tooltip, flagIn);
            tooltip.add(Component.translatable("item.usefulhats.ender_helmet.desc.teleport").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.translatable("item.usefulhats.ender_helmet.desc.teleport_position", position.pos.getX(), position.pos.getY(), position.pos.getZ()).withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.literal(position.dimName.toString()).withStyle(ChatFormatting.BLUE));
        }
    }

    private static void setPosition(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
        stack.set(Constants.ENDER_HELMET_POSITION.get(), new Position(level.dimension().registry(), level.dimension().location(), entity.blockPosition()));
    }

    private static boolean hasPosition(@Nonnull ItemStack stack) {
        return stack.has(Constants.ENDER_HELMET_POSITION.get());
    }

    private static Position getPosition(@Nonnull ItemStack stack) {
        if (hasPosition(stack)) {
            return stack.get(Constants.ENDER_HELMET_POSITION.get());
        }
        return null;
    }

    private static boolean levelEqualsPosition(Level level, Position position) {
        return level.dimension().registry().equals(position.dimKey) && level.dimension().location().equals(position.dimName);
    }

    private ServerLevel getLevel(@Nonnull MinecraftServer server, @Nonnull ItemStack stack) {
        Position position = getPosition(stack);
        if (position != null) {
            for (ServerLevel world : server.getAllLevels()) {
                if (levelEqualsPosition(world, position)) {
                    return world;
                }
            }
        }
        return null;
    }

    private static boolean canTeleportToPosition(@Nonnull Level level, @Nonnull BlockPos pos) {
        return !level.getBlockState(pos).canOcclude() && !level.getBlockState(pos.above()).canOcclude();
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level worldIn, @Nonnull Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (playerIn.isShiftKeyDown() && !stack.isEmpty()) { //shift right click
            if (!worldIn.isClientSide) {
                //save position on item stack
                setPosition(stack, worldIn, playerIn);
                //inform player about saved position
                ((ServerPlayer) playerIn).connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("item.usefulhats.ender_helmet.message.position_saved")));
            }
            return InteractionResultHolder.sidedSuccess(stack, worldIn.isClientSide());
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public boolean onRightClickItemEvent(Level level, Player player, ItemStack usedStack, InteractionHand hand, ItemStack headSlotItemStack) {
        if (!Services.REGISTRY.getEquippedHatItemStacks(player).contains(headSlotItemStack)) return false; //only one worn stack of this item should add its effect
        //only do teleportation when using an ender pearl and the hat item has a stored position
        if (usedStack.getItem() != Items.ENDER_PEARL || !hasPosition(headSlotItemStack)) {
            return false;
        }
        player.swing(hand);
        if (!level.isClientSide) {
            //check for correct dimension
            Position position = getPosition(headSlotItemStack);
            if (position != null && (Services.CONFIG.isEnderHelmetInterdimensionalEnabled() || levelEqualsPosition(level, position))) {
                ServerLevel destinationWorld = getLevel(player.getServer(), headSlotItemStack);
                BlockPos destinationPos = position.pos;
                //check for correct position
                if (destinationPos != null && destinationWorld != null && canTeleportToPosition(destinationWorld, destinationPos)) {
                    //set cooldown for ender pearls
                    player.getCooldowns().addCooldown(usedStack.getItem(), 20);
                    //teleport player
                    player.fallDistance = 0;
                    player.playNotifySound(SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1F, 1F);
                    if (player.level() != destinationWorld) {
                        ((ServerPlayer) player).teleportTo(destinationWorld, destinationPos.getX() + 0.5, destinationPos.getY(), destinationPos.getZ() + 0.5, player.yRotO, player.xRotO);
                    } else {
                        player.teleportTo(destinationPos.getX() + 0.5, destinationPos.getY(), destinationPos.getZ() + 0.5);
                    }
                    player.playNotifySound(SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1F, 1F);
                    //remove one ender pearl
                    player.awardStat(Stats.ITEM_USED.get(usedStack.getItem()));
                    if (!player.getAbilities().instabuild) {
                        usedStack.shrink(1);
                    }
                    //damage hat item
                    this.damageHatItemByOne(headSlotItemStack, player);
                } else {
                    ((ServerPlayer) player).connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("item.usefulhats.ender_helmet.message.position_obstructed")));
                }
            } else {
                ((ServerPlayer) player).connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("item.usefulhats.ender_helmet.message.wrong_dimension")));
            }
        }
        //cancel other right click operations
        return true;
    }

    public record Position(ResourceLocation dimKey, ResourceLocation dimName, BlockPos pos) {

        public static final Codec<Position> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                ResourceLocation.CODEC.fieldOf("dimKey").forGetter(Position::dimKey),
                ResourceLocation.CODEC.fieldOf("dimName").forGetter(Position::dimName),
                BlockPos.CODEC.fieldOf("pos").forGetter(Position::pos)
        ).apply(instance, Position::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Position> STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC, Position::dimKey,
                ResourceLocation.STREAM_CODEC, Position::dimName,
                BlockPos.STREAM_CODEC, Position::pos,
                Position::new);

    }

}
