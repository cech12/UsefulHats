package cech12.usefulhats.item;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;

public abstract class AbstractMiningHatItem extends AbstractHatItem implements IBreakSpeedChanger {

    AbstractMiningHatItem(HatArmorMaterial material, int initColor, ForgeConfigSpec.BooleanValue enabledDamageConfig) {
        super(material, initColor, enabledDamageConfig);
        this.addAllowedEnchantment(Enchantments.BLOCK_EFFICIENCY);
    }

    protected double getEnchantmentValue(final ItemStack stack, final double[] speedConfig) {
        if (speedConfig == null || speedConfig.length == 0) {
            return 0.0;
        }
        int enchantmentLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, stack);
        if (enchantmentLevel >= speedConfig.length) {
            return speedConfig[speedConfig.length - 1];
        }
        if (enchantmentLevel < 0) {
            return speedConfig[0];
        }
        return speedConfig[enchantmentLevel];
    }

    protected abstract double[] getSpeedConfig();

    protected abstract boolean isToolEffective(ItemStack tool, BlockState state);

    @Override
    public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && this.isToolEffective(event.getEntity().getMainHandItem(), event.getState())) {
            //use getNewSpeed() instead of getOriginalSpeed() to support other mods that are changing the break speed with this event.
            event.setNewSpeed((1.0F + (float) this.getEnchantmentValue(headSlotItemStack, this.getSpeedConfig())) * event.getNewSpeed());
        }
    }

    @Override
    public void onBreakEvent(BlockEvent.BreakEvent event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && this.isToolEffective(event.getPlayer().getMainHandItem(), event.getState())) {
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
        }
    }

}
