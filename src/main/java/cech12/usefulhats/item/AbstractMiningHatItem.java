package cech12.usefulhats.item;

import cech12.usefulhats.config.ConfigType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

public class AbstractMiningHatItem extends AbstractHatItem {

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

}
