package cech12.usefulhats.item;

import cech12.usefulhats.config.ConfigType;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Set;

public abstract class AbstractMiningHatItem extends AbstractHatItem implements IBreakSpeedChanger {

    AbstractMiningHatItem(String name, HatArmorMaterial material, int initColor, ConfigType.Boolean enabledConfig, ConfigType.Boolean enabledDamageConfig) {
        super(name, material, initColor, enabledConfig, enabledDamageConfig);
        this.addAllowedEnchantment(Enchantments.EFFICIENCY);
    }

    protected double getEnchantmentValue(final ItemStack stack, final double[] speedConfig) {
        if (speedConfig == null || speedConfig.length == 0) {
            return 0.0;
        }
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        if (enchantmentLevel >= speedConfig.length) {
            return speedConfig[speedConfig.length - 1];
        }
        if (enchantmentLevel < 0) {
            return speedConfig[0];
        }
        return speedConfig[enchantmentLevel];
    }

    protected abstract double[] getSpeedConfig();

    protected abstract boolean isToolEffective(Set<ToolType> toolTypes, BlockState state);

    @Override
    public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && this.isToolEffective(event.getPlayer().getHeldItemMainhand().getToolTypes(), event.getState())) {
            //use getNewSpeed() instead of getOriginalSpeed() to support other mods that are changing the break speed with this event.
            event.setNewSpeed((1.0F + (float) this.getEnchantmentValue(headSlotItemStack, this.getSpeedConfig())) * event.getNewSpeed());
        }
    }

    @Override
    public void onBreakEvent(BlockEvent.BreakEvent event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && this.isToolEffective(event.getPlayer().getHeldItemMainhand().getToolTypes(), event.getState())) {
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
        }
    }

}
