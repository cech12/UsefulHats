package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.client.UsefulHatsFabricClientMod;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for the EntityRenderDispatcher class to add model reloading.
 */
@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "onResourceManagerReload", at = @At("RETURN"))
    public void dropAllDeathLootProxy(ResourceManager resourceManager, CallbackInfo ci) {
        UsefulHatsFabricClientMod.resetUsefulHatModel();
    }

}
