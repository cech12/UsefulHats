package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.item.IAttackTargetChanger;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for the Mob class to add hat event handling.
 */
@Mixin(Mob.class)
public class MobMixin {

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    public void setTargetProxy(LivingEntity livingEntity, CallbackInfo ci) {
        if (livingEntity instanceof Player player) {
            for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(player)) {
                for (Item item : ModItems.ALL_HATS) {
                    if (item instanceof IAttackTargetChanger attackTargetChanger && item == headSlotItemStack.getItem()) {
                        if (attackTargetChanger.avoidMobChangingTarget(headSlotItemStack, (Mob) (Object) this, player)) {
                            ci.cancel();
                        }
                    }
                }
            }
        }
    }

}
