package de.cech12.usefulhats;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/**
 * A static class for all loaders which initializes everything which is used by all loaders.
 */
public class CommonLoader {

    private static HolderLookup.RegistryLookup<Enchantment> enchantmentLookup = null;

    private CommonLoader() {}

    /**
     * Initialize method that should be called by every loader mod in the constructor.
     */
    public static void init() {
        Services.CONFIG.init();
    }

    public static int getEnchantmentLevel(ItemStack stack, ResourceKey<Enchantment> enchantment) {
        if (enchantmentLookup == null) {
            enchantmentLookup = VanillaRegistries.createLookup().lookup(Registries.ENCHANTMENT).get();
        }
        return EnchantmentHelper.getItemEnchantmentLevel(enchantmentLookup.getOrThrow(enchantment), stack);
    }

}
