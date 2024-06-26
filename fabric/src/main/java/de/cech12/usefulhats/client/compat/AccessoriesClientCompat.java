package de.cech12.usefulhats.client.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.client.AbstractUsefulHatsRenderer;
import de.cech12.usefulhats.platform.Services;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
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
        public <M extends LivingEntity> void render(ItemStack stack, SlotReference reference, PoseStack matrices, EntityModel<M> model, MultiBufferSource multiBufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            this.render(stack, matrices, multiBufferSource, light, reference.entity(), limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        }

        @Override
        protected void followBodyRotations(LivingEntity livingEntity, HumanoidModel<LivingEntity> humanoidModel) {
            AccessoryRenderer.followBodyRotations(livingEntity, humanoidModel);
        }
    }

}
