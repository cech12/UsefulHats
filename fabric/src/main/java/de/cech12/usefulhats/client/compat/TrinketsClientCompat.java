package de.cech12.usefulhats.client.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.client.AbstractUsefulHatsRenderer;
import de.cech12.usefulhats.platform.Services;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TrinketsClientCompat {

    private TrinketsClientCompat() {}

    public static void register() {
        UsefulHatsTrinketRenderer renderer = new UsefulHatsTrinketRenderer();
        for (Item item : Services.REGISTRY.getAllHatItems()) {
            TrinketRendererRegistry.registerRenderer(item, renderer);
        }
    }

    private static class UsefulHatsTrinketRenderer extends AbstractUsefulHatsRenderer implements TrinketRenderer {

        @Override
        public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
            this.render(stack, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
        }

        @Override
        protected void followBodyRotations(LivingEntity livingEntity, HumanoidModel<LivingEntity> humanoidModel) {
            TrinketRenderer.followBodyRotations(livingEntity, humanoidModel);
        }
    }

}
