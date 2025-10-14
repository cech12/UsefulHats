package de.cech12.usefulhats.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class UsefulHatsArmorRenderer extends AbstractUsefulHatsRenderer implements ArmorRenderer {

    @Override
    public void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack stack, HumanoidRenderState humanoidRenderState, EquipmentSlot equipmentSlot, int light, HumanoidModel<HumanoidRenderState> humanoidModel) {
        if (equipmentSlot != EquipmentSlot.HEAD) {
            return;
        }
        this.render(stack, humanoidRenderState, poseStack, submitNodeCollector, light, humanoidModel);
    }

}
