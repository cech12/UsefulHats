package de.cech12.usefulhats.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import de.cech12.usefulhats.item.AbstractHatItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Mixin for the EnchantmentHelper class to add hat enchanting.
 */
@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(method = "getAvailableEnchantmentResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/Enchantment;isDiscoverable()Z", shift = At.Shift.AFTER))
    private static void getAvailableEnchantmentResultsProxy(int i, ItemStack itemStack, boolean bl, CallbackInfoReturnable<List<EnchantmentInstance>> cir, @Local(ordinal = 1) LocalBooleanRef bl2, @Local LocalRef<Enchantment> enchantment) {
        if (itemStack.getItem() instanceof AbstractHatItem hatItem) {
            if (hatItem.canApplyAtEnchantingTable(itemStack, enchantment.get())) {
                bl2.set(true);
            } else {
                bl2.set(false);
                enchantment.set(Enchantments.FISHING_LUCK); //set to an enchantment which cannot be enchanted to an armor item
            }
        }
    }

}
