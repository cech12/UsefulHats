package de.cech12.usefulhats;

import de.cech12.usefulhats.platform.Services;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * A static class for all loaders which initializes everything which is used by all loaders.
 */
public class CommonLoader {

    private CommonLoader() {}

    /**
     * Initialize method that should be called by every loader mod in the constructor.
     */
    public static void init() {
        Services.CONFIG.init();
    }

    public static int getEnchantmentLevel(ItemStack stack, ResourceKey<Enchantment> enchantment) {
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : stack.getEnchantments().entrySet()) {
            if (entry.getKey().is(enchantment)) {
                return entry.getIntValue();
            }
        }
        return 0;
    }

}
