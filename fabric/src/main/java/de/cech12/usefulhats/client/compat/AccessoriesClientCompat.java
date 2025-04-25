package de.cech12.usefulhats.client.compat;
/*
import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.client.AbstractUsefulHatsRenderer;
import de.cech12.usefulhats.platform.Services;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AccessoriesClientCompat {

    private AccessoriesClientCompat() {}

    public static void register() {
        UsefulHatsAccessoriesRenderer renderer = new UsefulHatsAccessoriesRenderer();
        for (Item item : Services.REGISTRY.getAllHatItems()) {
            AccessoriesRendererRegistry.registerRenderer(item, () -> renderer);
        }
    }

    private static class UsefulHatsAccessoriesRenderer extends AbstractUsefulHatsRenderer implements AccessoryRenderer {

        @Override
        public <S extends LivingEntityRenderState> void render(ItemStack stack, SlotReference reference, PoseStack matrices, EntityModel<S> model, S renderState, MultiBufferSource multiBufferSource, int light, float partialTicks) {
            if (renderState instanceof HumanoidRenderState humanoidRenderState) {
                this.render(stack, matrices, multiBufferSource, light, humanoidRenderState);
            }
        }
    }

}
*/
