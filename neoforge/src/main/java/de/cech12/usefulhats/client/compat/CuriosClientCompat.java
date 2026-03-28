package de.cech12.usefulhats.client.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.client.AbstractUsefulHatsRenderer;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CuriosClientCompat {

    private CuriosClientCompat() {}

    public static void register() {
        UsefulHatsCurioRenderer renderer = new UsefulHatsCurioRenderer();
        for (Item item : Services.REGISTRY.getAllHatItems()) {
            ICurioRenderer.register(item, () -> renderer);
        }
    }

    private static class UsefulHatsCurioRenderer extends AbstractUsefulHatsRenderer implements ICurioRenderer {
        @Override
        public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S renderState, RenderLayerParent<S, M> renderLayerParent, EntityRendererProvider.Context context, float yRotation, float xRotation) {
            this.render(stack, (HumanoidRenderState) renderState, poseStack, submitNodeCollector, packedLight, (EntityModel<HumanoidRenderState>) renderLayerParent.getModel());
        }
    }

}
