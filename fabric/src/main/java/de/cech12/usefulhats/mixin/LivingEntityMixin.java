package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.item.IEquipmentChangeListener;
import de.cech12.usefulhats.item.IItemUseListener;
import de.cech12.usefulhats.item.ILivingDropsListener;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the LivingEntity class to add hat event handling.
 */
@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Shadow
    protected int useItemRemaining;

    @Inject(method = "dropAllDeathLoot", at = @At("RETURN"))
    public void dropAllDeathLootProxy(DamageSource damageSource, CallbackInfo ci) {
        if ((Object)this instanceof Player player) {
            for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(player)) {
                for (Item item : ModItems.ALL_HATS) {
                    if (item instanceof ILivingDropsListener livingDropsListener && item == headSlotItemStack.getItem()) {
                        livingDropsListener.onLivingDropsEvent(player, headSlotItemStack);
                    }
                }
            }
        }
    }

    @Inject(method = "startUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseDuration()I", shift = At.Shift.BY, by = 2))
    public void startUsingItemProxy(InteractionHand interactionHand, CallbackInfo ci) {
        if ((Object)this instanceof Player player) {
            for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(player)) {
                for (Item item : ModItems.ALL_HATS) {
                    if (item instanceof IItemUseListener itemUseListener && item == headSlotItemStack.getItem()) {
                        Integer newDuration = itemUseListener.onItemUseEventStart(player, player.getItemInHand(interactionHand), useItemRemaining, headSlotItemStack);
                        if (newDuration != null) {
                            useItemRemaining = newDuration;
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "equipmentHasChanged", at = @At("RETURN"))
    public void equipmentHasChangedProxy(ItemStack fromItemStack, ItemStack toItemStack, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue().equals(Boolean.TRUE)) {
            Item fromItem = fromItemStack.getItem();
            Item toItem = toItemStack.getItem();
            if (fromItem != toItem && (fromItem instanceof IEquipmentChangeListener || toItem instanceof IEquipmentChangeListener)) {
                for (Item item : ModItems.ALL_HATS) {
                    if (item instanceof IEquipmentChangeListener equipmentChangeListener) {
                        if (fromItem == item) {
                            equipmentChangeListener.onUnequippedHatItem((LivingEntity) (Object) this, fromItemStack);
                        } else if (toItem == item) {
                            equipmentChangeListener.onEquippedHatItem((LivingEntity) (Object) this, toItemStack);
                        }
                    }
                }
            }
        }
    }

}
