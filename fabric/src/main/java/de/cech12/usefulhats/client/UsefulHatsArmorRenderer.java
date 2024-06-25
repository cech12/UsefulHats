package de.cech12.usefulhats.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class UsefulHatsArmorRenderer extends AbstractUsefulHatsRenderer implements ArmorRenderer {

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, ItemStack stack, LivingEntity livingEntity, EquipmentSlot equipmentSlot, int light, HumanoidModel<LivingEntity> humanoidModel) {
        if (equipmentSlot != EquipmentSlot.HEAD) {
            return;
        }
        this.render(stack, poseStack, multiBufferSource, light, livingEntity);
    }

    @Override
    protected void followBodyRotations(LivingEntity livingEntity, HumanoidModel<LivingEntity> humanoidModel) {
        if (Minecraft.getInstance().player != null) {
            ((HumanoidModel)((RenderLayerParent)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingEntity)).getModel()).copyPropertiesTo(humanoidModel);
        }
    }

}
