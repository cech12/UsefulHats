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

    protected float getEnchantmentValue(ItemStack stack) {
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) + 1;
        float enchantmentValue = (0.2F * enchantmentLevel);
        if (enchantmentLevel > 5) {
            enchantmentValue += 0.3F;
        }
        return enchantmentValue;
    }

}
