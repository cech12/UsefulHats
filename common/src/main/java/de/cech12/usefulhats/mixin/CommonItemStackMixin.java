package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.item.AbstractHatItem;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the Item class to enable the possibility to configure the item's durability and hook into other hidden functionality.
 */
@Mixin(ItemStack.class)
public abstract class CommonItemStackMixin implements DataComponentHolder {

    @Shadow public abstract Item getItem();

    /**
     * @param cir CallbackInfoReturnable
     */
    @Inject(at = @At("HEAD"), method = "getMaxDamage", cancellable = true)
    public void getMaxDamageProxy(CallbackInfoReturnable<Integer> cir) {
        if (this.getItem() instanceof AbstractHatItem  hatItem) {
            cir.setReturnValue(hatItem.getDurabilityFromConfig());
            cir.cancel();
        }
    }

    /**
     * @param cir CallbackInfoReturnable
     */
    @Inject(at = @At("HEAD"), method = "isDamageableItem", cancellable = true)
    public void isDamageableItemProxy(CallbackInfoReturnable<Boolean> cir) {
        if (this.getItem() instanceof AbstractHatItem  hatItem) {
            cir.setReturnValue(hatItem.getDurabilityFromConfig() > 0 && !this.has(DataComponents.UNBREAKABLE));
            cir.cancel();
        }
    }

}
