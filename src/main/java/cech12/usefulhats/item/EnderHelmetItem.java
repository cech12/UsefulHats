package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class EnderHelmetItem extends AbstractHatItem implements IRightClickListener {

    public static final String TELEPORT_POSITION_ID = "TeleportPosition";

    public EnderHelmetItem() {
        super("ender_helmet", HatArmorMaterial.ENDER, rawColorFromRGB(43, 203, 175), Config.ENDER_HELMET_ENABLED, Config.ENDER_HELMET_DAMAGE_ENABLED);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        this.addTextLineToTooltip(tooltip, new TranslationTextComponent("item.usefulhats.ender_helmet.desc.define_teleport").withStyle(TextFormatting.BLUE));
        if (hasPosition(stack)) {
            super.appendHoverText(stack, worldIn, tooltip, flagIn);
            BlockPos pos = getPosition(stack);
            this.addTextLineToTooltip(tooltip, new TranslationTextComponent("item.usefulhats.ender_helmet.desc.teleport").withStyle(TextFormatting.BLUE));
            this.addTextLineToTooltip(tooltip, new TranslationTextComponent("item.usefulhats.ender_helmet.desc.teleport_position", pos.getX(), pos.getY(), pos.getZ()).withStyle(TextFormatting.BLUE));
            this.addTextLineToTooltip(tooltip, new StringTextComponent(getDimensionString(stack)).withStyle(TextFormatting.BLUE));
        }
    }

    private static void setPosition(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull PlayerEntity player) {
        CompoundNBT nbt = stack.getOrCreateTag().copy();
        CompoundNBT positionNBT = new CompoundNBT();
        BlockPos pos = player.blockPosition();
        positionNBT.putInt("X", pos.getX());
        positionNBT.putInt("Y", pos.getY());
        positionNBT.putInt("Z", pos.getZ());
        positionNBT.putString("dimKey", world.dimension().getRegistryName().toString()); //dimension registry key
        positionNBT.putString("dimName", world.dimension().location().toString()); //dimension name
        nbt.put(TELEPORT_POSITION_ID, positionNBT);
        stack.setTag(nbt);
    }

    private static boolean hasPosition(@Nonnull ItemStack stack) {
        return stack.getOrCreateTag().contains(TELEPORT_POSITION_ID);
    }

    private static BlockPos getPosition(@Nonnull ItemStack stack) {
        if (hasPosition(stack)) {
            CompoundNBT nbt = stack.getOrCreateTag();
            CompoundNBT positionNBT = nbt.getCompound(TELEPORT_POSITION_ID);
            return new BlockPos(positionNBT.getInt("X"), positionNBT.getInt("Y"), positionNBT.getInt("Z"));
        }
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    private String getDimensionString(@Nonnull ItemStack stack) {
        if (hasPosition(stack)) {
            return stack.getOrCreateTag().getCompound(TELEPORT_POSITION_ID).getString("dimName");
        }
        return "?";
    }

    private static boolean equalsWorldAndNBT(World world, CompoundNBT positionNBT) {
        return (!positionNBT.contains("dimKey")  //to be compatible with older versions
                || world.dimension().getRegistryName().toString().equals(positionNBT.getString("dimKey")))
                && world.dimension().location().toString().equals(positionNBT.getString("dimName"));
    }

    private ServerWorld getWorld(@Nonnull MinecraftServer server, @Nonnull ItemStack stack) {
        if (hasPosition(stack)) {
            CompoundNBT positionNBT = stack.getOrCreateTag().getCompound(TELEPORT_POSITION_ID);
            for (ServerWorld world : server.getAllLevels()) {
                if (equalsWorldAndNBT(world, positionNBT)) {
                    return world;
                }
            }
        }
        return null;
    }

    private static boolean isRightDimension(@Nonnull World world, @Nonnull ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (hasPosition(stack)) {
            CompoundNBT positionNBT = nbt.getCompound(TELEPORT_POSITION_ID);
            return equalsWorldAndNBT(world, positionNBT);
        }
        return false;
    }

    private static boolean canTeleportToPosition(@Nonnull World world, @Nonnull BlockPos pos) {
        return !world.getBlockState(pos).canOcclude()
                && !world.getBlockState(pos.above()).canOcclude();
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World worldIn, @Nonnull PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (playerIn.isShiftKeyDown() && !stack.isEmpty()) { //shift right click
            if (!worldIn.isClientSide) {
                //save position on item stack
                setPosition(stack, worldIn, playerIn);
                //inform player about saved position
                ((ServerPlayerEntity) playerIn).connection.send(new STitlePacket(STitlePacket.Type.ACTIONBAR, new TranslationTextComponent("item.usefulhats.ender_helmet.message.position_saved"), 10, 60, 10));
            }
            return ActionResult.sidedSuccess(stack, worldIn.isClientSide());
        }
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public void onRightClickItemEvent(PlayerInteractEvent.RightClickItem event, ItemStack headSlotItemStack) {
        PlayerEntity player = event.getPlayer();
        if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(headSlotItemStack)) return; //only one worn stack of this item should add its effect
        World world = event.getWorld();
        ItemStack usedStack = event.getItemStack();
        //only do teleportation when using an ender pearl and the hat item has a stored position
        if (usedStack.getItem() == Items.ENDER_PEARL && hasPosition(headSlotItemStack)) {
            player.swing(event.getHand());

            if (!world.isClientSide) {
                //check for correct dimension
                if (Config.ENDER_HELMET_INTERDIMENSIONAL_ENABLED.getValue() || isRightDimension(world, headSlotItemStack)) {
                    ServerWorld destinationWorld = getWorld(player.getServer(), headSlotItemStack);
                    BlockPos destinationPos = getPosition(headSlotItemStack);
                    //check for correct position
                    if (destinationPos != null && destinationWorld != null && canTeleportToPosition(destinationWorld, destinationPos)) {
                        //set cooldown for ender pearls
                        player.getCooldowns().addCooldown(usedStack.getItem(), 20);
                        //teleport player
                        player.fallDistance = 0;
                        player.playNotifySound(SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1F, 1F);
                        if (player.level != destinationWorld) {
                            ((ServerPlayerEntity) player).teleportTo(destinationWorld, destinationPos.getX() + 0.5, destinationPos.getY(), destinationPos.getZ() + 0.5, player.yRot, player.xRot);
                        } else {
                            player.teleportTo(destinationPos.getX() + 0.5, destinationPos.getY(), destinationPos.getZ() + 0.5);
                        }
                        player.playNotifySound(SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1F, 1F);
                        //remove one ender pearl
                        player.awardStat(Stats.ITEM_USED.get(usedStack.getItem()));
                        if (!player.abilities.instabuild) {
                            usedStack.shrink(1);
                        }
                        //damage hat item
                        this.damageHatItemByOne(headSlotItemStack, player);
                    } else {
                        ((ServerPlayerEntity) player).connection.send(new STitlePacket(STitlePacket.Type.ACTIONBAR, new TranslationTextComponent("item.usefulhats.ender_helmet.message.position_obstructed"), 10, 60, 10));
                    }
                } else {
                    ((ServerPlayerEntity) player).connection.send(new STitlePacket(STitlePacket.Type.ACTIONBAR, new TranslationTextComponent("item.usefulhats.ender_helmet.message.wrong_dimension"), 10, 60, 10));
                }
            }
            //cancel other right click operations
            event.setCanceled(true);
        }
    }
}
