package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.item.AbstractHatItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the Enchantment class to add hat book enchanting.
 */
@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Inject(method = "canEnchant", at = @At(value = "HEAD"), cancellable = true)
    public void canEnchantProxy(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (itemStack.getItem() instanceof AbstractHatItem hatItem) {
            cir.setReturnValue(hatItem.canApplyAtEnchantingTable(itemStack, (Enchantment) (Object) this));
        }
    }

}
