package de.cech12.usefulhats;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Optional;

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
            try {
                Optional<HolderLookup.RegistryLookup<Enchantment>> optional = VanillaRegistries.createLookup().lookup(Registries.ENCHANTMENT);
                if (optional.isPresent()) {
                    enchantmentLookup = optional.get();
                } else {
                    Constants.LOG.error("Cannot get a Lookup for the enchantment registry. Some mod is messing around with the enchantment registry!");
                    return 0;
                }
            } catch (IllegalStateException ex) {
                Constants.LOG.error("Cannot get a Lookup for the enchantment registry. Some mod is messing around with the registries or is trying to get the enchantment level too early!", ex);
                return 0;
            }
        }
        return EnchantmentHelper.getItemEnchantmentLevel(enchantmentLookup.getOrThrow(enchantment), stack);
    }

}
