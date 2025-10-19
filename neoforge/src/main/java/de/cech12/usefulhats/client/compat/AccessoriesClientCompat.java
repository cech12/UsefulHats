package de.cech12.usefulhats.client.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.client.AbstractUsefulHatsRenderer;
import de.cech12.usefulhats.platform.Services;
import io.wispforest.accessories.api.client.AccessoriesRenderStateKeys;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderState;
import io.wispforest.accessories.api.client.RenderStateStorage;
import io.wispforest.accessories.api.client.renderers.AccessoryRenderer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
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
        public <S extends LivingEntityRenderState> void render(AccessoryRenderState accessoryRenderState, S renderState, EntityModel<S> model, PoseStack matrices, SubmitNodeCollector submitNodeCollector) {
            if (renderState instanceof HumanoidRenderState humanoidRenderState) {
                ItemStack stack = accessoryRenderState.getStateData(AccessoriesRenderStateKeys.ITEM_STACK);
                int light = (renderState instanceof RenderStateStorage) ? ((RenderStateStorage)renderState).getStateData(AccessoriesRenderStateKeys.LIGHT) : 0;
                this.render(stack, humanoidRenderState, matrices, submitNodeCollector, light, (EntityModel<HumanoidRenderState>) model);
            }
        }
    }

}
