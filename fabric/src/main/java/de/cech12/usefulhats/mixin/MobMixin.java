package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.UsefulHatsEventUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
        if (UsefulHatsEventUtils.shouldEntityAvoidChangingTarget((Mob) (Object) this, livingEntity)) {
            ci.cancel();
        }
    }

}
