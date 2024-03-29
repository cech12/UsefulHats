package de.cech12.usefulhats.item;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class EnderHelmetItem extends AbstractHatItem implements IRightClickListener {

    public static final String TELEPORT_POSITION_ID = "TeleportPosition";

    public EnderHelmetItem() {
        super(HatArmorMaterial.ENDER, rawColorFromRGB(43, 203, 175), Services.CONFIG::isEnderHelmetDamageEnabled);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        tooltip.add(Component.translatable("item.usefulhats.ender_helmet.desc.define_teleport").withStyle(ChatFormatting.BLUE));
        if (hasPosition(stack)) {
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
            BlockPos pos = getPosition(stack);
            tooltip.add(Component.translatable("item.usefulhats.ender_helmet.desc.teleport").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.translatable("item.usefulhats.ender_helmet.desc.teleport_position", pos.getX(), pos.getY(), pos.getZ()).withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.literal(getDimensionString(stack)).withStyle(ChatFormatting.BLUE));
        }
    }

    private static void setPosition(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Player player) {
        CompoundTag nbt = stack.getOrCreateTag().copy();
        CompoundTag positionNBT = new CompoundTag();
        BlockPos pos = player.blockPosition();
        positionNBT.putInt("X", pos.getX());
        positionNBT.putInt("Y", pos.getY());
        positionNBT.putInt("Z", pos.getZ());
        positionNBT.putString("dimKey", level.dimension().registry().toString()); //dimension registry key
        positionNBT.putString("dimName", level.dimension().location().toString()); //dimension name
        nbt.put(TELEPORT_POSITION_ID, positionNBT);
        stack.setTag(nbt);
    }

    private static boolean hasPosition(@Nonnull ItemStack stack) {
        return stack.getOrCreateTag().contains(TELEPORT_POSITION_ID);
    }

    private static BlockPos getPosition(@Nonnull ItemStack stack) {
        if (hasPosition(stack)) {
            CompoundTag nbt = stack.getOrCreateTag();
            CompoundTag positionNBT = nbt.getCompound(TELEPORT_POSITION_ID);
            return new BlockPos(positionNBT.getInt("X"), positionNBT.getInt("Y"), positionNBT.getInt("Z"));
        }
        return null;
    }

    private String getDimensionString(@Nonnull ItemStack stack) {
        if (hasPosition(stack)) {
            return stack.getOrCreateTag().getCompound(TELEPORT_POSITION_ID).getString("dimName");
        }
        return "?";
    }

    private static boolean equalsWorldAndNBT(Level world, CompoundTag positionNBT) {
        return (!positionNBT.contains("dimKey")  //to be compatible with older versions
                || world.dimension().registry().toString().equals(positionNBT.getString("dimKey")))
                && world.dimension().location().toString().equals(positionNBT.getString("dimName"));
    }

    private ServerLevel getLevel(@Nonnull MinecraftServer server, @Nonnull ItemStack stack) {
        if (hasPosition(stack)) {
            CompoundTag positionNBT = stack.getOrCreateTag().getCompound(TELEPORT_POSITION_ID);
            for (ServerLevel world : server.getAllLevels()) {
                if (equalsWorldAndNBT(world, positionNBT)) {
                    return world;
                }
            }
        }
        return null;
    }

    private static boolean isRightDimension(@Nonnull Level world, @Nonnull ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (hasPosition(stack)) {
            CompoundTag positionNBT = nbt.getCompound(TELEPORT_POSITION_ID);
            return equalsWorldAndNBT(world, positionNBT);
        }
        return false;
    }

    private static boolean canTeleportToPosition(@Nonnull Level world, @Nonnull BlockPos pos) {
        return !world.getBlockState(pos).canOcclude()
                && !world.getBlockState(pos.above()).canOcclude();
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
            if (Services.CONFIG.isEnderHelmetInterdimensionalEnabled() || isRightDimension(level, headSlotItemStack)) {
                ServerLevel destinationWorld = getLevel(player.getServer(), headSlotItemStack);
                BlockPos destinationPos = getPosition(headSlotItemStack);
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
}
