package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.UsefulHatsEventUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the PersistentEntitySectionManager class to add hat event handling.
 */
@Mixin(PersistentEntitySectionManager.class)
public class PersistentEntitySectionManagerMixin<T extends EntityAccess> {

    @Inject(at = @At("HEAD"), method = "addEntity")
    public void addEntityProxy(T entityAccess, boolean bl, CallbackInfoReturnable<Boolean> cir) {
        if (entityAccess instanceof LivingEntity entity) {
            UsefulHatsEventUtils.onEntityJoinWorld(entity);
        }
    }

}
