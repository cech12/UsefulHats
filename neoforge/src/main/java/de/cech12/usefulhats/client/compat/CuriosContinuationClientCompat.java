package de.cech12.usefulhats.client.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.client.AbstractUsefulHatsRenderer;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class CuriosContinuationClientCompat {

    private CuriosContinuationClientCompat() {}

    public static void register() {
        UsefulHatsCurioRenderer renderer = new UsefulHatsCurioRenderer();
        for (Item item : Services.REGISTRY.getAllHatItems()) {
            CuriosRendererRegistry.register(item, () -> renderer);
        }
    }

    // see https://github.com/TheIllusiveC4/Curios/wiki/1.16.5-to-1.17:-Updates-and-Changes#rendering-system
    private static class UsefulHatsCurioRenderer extends AbstractUsefulHatsRenderer implements ICurioRenderer {
        @Override
        public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            this.render(stack, matrixStack, renderTypeBuffer, light, slotContext.entity(), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        }

        @Override
        protected void followBodyRotations(LivingEntity livingEntity, HumanoidModel<LivingEntity> humanoidModel) {
            ICurioRenderer.followBodyRotations(livingEntity, humanoidModel);
        }
    }

}
