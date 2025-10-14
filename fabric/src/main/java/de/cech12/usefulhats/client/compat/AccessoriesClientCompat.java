package de.cech12.usefulhats.client.compat;
/*
import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.client.AbstractUsefulHatsRenderer;
import de.cech12.usefulhats.platform.Services;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.renderers.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotPath;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AccessoriesClientCompat {

    private static final ResourceLocation RENDERER_ID = Constants.id("accessories_renderer");

    private AccessoriesClientCompat() {}

    public static void register() {
        UsefulHatsAccessoriesRenderer renderer = new UsefulHatsAccessoriesRenderer();
        AccessoriesRendererRegistry.registerRenderer(RENDERER_ID, () -> renderer);
        for (Item item : Services.REGISTRY.getAllHatItems()) {
            AccessoriesRendererRegistry.bindItemToRenderer(item, RENDERER_ID);
        }
    }

    private static class UsefulHatsAccessoriesRenderer extends AbstractUsefulHatsRenderer implements AccessoryRenderer {

        @Override
        public <S extends LivingEntityRenderState> void render(ItemStack stack, SlotPath slotPath, PoseStack matrices, EntityModel<S> model, S renderState, MultiBufferSource multiBufferSource, int light, float partialTicks) {
            if (renderState instanceof HumanoidRenderState humanoidRenderState) {
                this.render(stack, matrices, multiBufferSource, light, humanoidRenderState);
            }
        }
    }

}
 */
