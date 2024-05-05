package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.item.IItemFishedListener;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.advancements.critereon.FishingRodHookedTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

/**
 * Mixin for the FishingHook class to add hat event handling.
 */
@Mixin(FishingRodHookedTrigger.class)
public class FishingRodHookedTriggerMixin {

    @Inject(at = @At("HEAD"), method = "trigger")
    public void retrieveProxy(ServerPlayer serverPlayer, ItemStack itemStack, FishingHook fishingHook, Collection<ItemStack> collection, CallbackInfo ci) {
        if (collection.isEmpty()) {
            return;
        }
        for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(serverPlayer)) {
            for (Item item : ModItems.ALL_HATS) {
                if (item instanceof IItemFishedListener itemFishedListener && headSlotItemStack.getItem() == item) {
                    itemFishedListener.onItemFishedListener(serverPlayer, headSlotItemStack);
                }
            }
        }
    }

}
