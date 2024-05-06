package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.UsefulHatsEventUtils;
import net.minecraft.advancements.critereon.FishingRodHookedTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FishingHook;
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
        if (!collection.isEmpty()) {
            UsefulHatsEventUtils.onItemFished(serverPlayer);
        }
    }

}
