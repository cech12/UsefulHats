package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.item.AbstractHatItem;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the Item class to enable the possibility to configure the item's durability and hook into other hidden functionality.
 */
@Mixin(Item.class)
public class CommonItemMixin {

    @Unique
    private boolean usefulhats$isAbstractHatItem() {
        return ((Object) this) instanceof AbstractHatItem;
    }

    @Unique
    private int usefulhats$getDurability() {
        if (((Object) this) instanceof AbstractHatItem hatItem) {
            return hatItem.getDurability();
        }
        return ((Item) (Object) this).getMaxDamage();
    }

    /**
     * @param cir CallbackInfoReturnable
     */
    @Inject(at = @At("HEAD"), method = "getMaxDamage", cancellable = true)
    public void getMaxDamageProxy(CallbackInfoReturnable<Integer> cir) {
        if (usefulhats$isAbstractHatItem()) {
            cir.setReturnValue(usefulhats$getDurability());
            cir.cancel();
        }
    }

    /**
     * @param cir CallbackInfoReturnable
     */
    @Inject(at = @At("HEAD"), method = "canBeDepleted", cancellable = true)
    public void canBeDepletedProxy(CallbackInfoReturnable<Boolean> cir) {
        if (usefulhats$isAbstractHatItem()) {
            cir.setReturnValue(usefulhats$getDurability() > 0);
            cir.cancel();
        }
    }

    /**
     * @param stack ItemStack
     * @param cir CallbackInfoReturnable
     */
    @Inject(at = @At("HEAD"), method = "getBarWidth", cancellable = true)
    public void getBarWidthProxy(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (usefulhats$isAbstractHatItem()) {
            cir.setReturnValue(Math.round(13.0F - (float)stack.getDamageValue() * 13.0F / (float)usefulhats$getDurability()));
            cir.cancel();
        }
    }

    /**
     * @param stack ItemStack
     * @param cir CallbackInfoReturnable
     */
    @Inject(at = @At("HEAD"), method = "getBarColor", cancellable = true)
    public void getBarColorProxy(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (usefulhats$isAbstractHatItem()) {
            int durability = usefulhats$getDurability();
            float x = Math.max(0.0F, ((float)durability - (float)stack.getDamageValue()) / (float)durability);
            cir.setReturnValue(Mth.hsvToRgb(x / 3.0F, 1.0F, 1.0F));
            cir.cancel();
        }
    }

}
