package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.item.IGameOverlayRenderer;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for the Gui class to add hat overlay rendering.
 */
@Mixin(Gui.class)
public class GuiMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    public void renderProxy(GuiGraphics guiGraphics, float tickDelta, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.options.getCameraType().isFirstPerson()) {
            for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(mc.player)) {
                if (headSlotItemStack.getItem() instanceof IGameOverlayRenderer gameOverlayRenderer) {
                    gameOverlayRenderer.onRenderGameOverlay(guiGraphics.guiWidth(), guiGraphics.guiHeight(), tickDelta);
                }
            }
        }
    }

}
