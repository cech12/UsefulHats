package de.cech12.usefulhats.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.cech12.usefulhats.item.IUsefulHatModelOwner;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class UsefulHatsArmorRenderer implements ArmorRenderer {

    private static final Map<String, ResourceLocation> LOCATIONS = new HashMap<>();

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, ItemStack stack, LivingEntity livingEntity, EquipmentSlot equipmentSlot, int light, HumanoidModel<LivingEntity> humanoidModel) {
        if (equipmentSlot != EquipmentSlot.HEAD) {
            return;
        }
        ItemStack itemStack = livingEntity.getItemBySlot(equipmentSlot);
        Item item = itemStack.getItem();
        if (item instanceof ArmorItem armorItem && armorItem instanceof IUsefulHatModelOwner) {
            if (armorItem.getEquipmentSlot() == equipmentSlot) {
                humanoidModel = UsefulHatsFabricClientMod.getUsefulHatModel();
                copyPropertiesFromParentModel(livingEntity, humanoidModel);
                if (armorItem instanceof DyeableArmorItem dyeableArmorItem) {
                    int j = dyeableArmorItem.getColor(itemStack);
                    float f = (float)(j >> 16 & 255) / 255.0F;
                    float g = (float)(j >> 8 & 255) / 255.0F;
                    float h = (float)(j & 255) / 255.0F;
                    this.renderModel(poseStack, multiBufferSource, light, armorItem, humanoidModel, f, g, h, null);
                    this.renderModel(poseStack, multiBufferSource, light, armorItem, humanoidModel, 1.0F, 1.0F, 1.0F, "overlay");
                } else {
                    this.renderModel(poseStack, multiBufferSource, light, armorItem, humanoidModel, 1.0F, 1.0F, 1.0F, null);
                }

                if (itemStack.hasFoil()) {
                    this.renderGlint(poseStack, multiBufferSource, light, humanoidModel);
                }

            }
        }
    }

    private void copyPropertiesFromParentModel(LivingEntity livingEntity, HumanoidModel<LivingEntity> humanoidModel) {
        if (Minecraft.getInstance().player != null) {
            ((HumanoidModel)((RenderLayerParent)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingEntity)).getModel()).copyPropertiesTo(humanoidModel);;
        }
    }

    private void renderModel(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, ArmorItem armorItem, HumanoidModel<LivingEntity> humanoidModel, float f, float g, float h, @Nullable String string) {
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucent(this.getArmorLocation(armorItem, string)));
        humanoidModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, f, g, h, 1.0F);
    }

    private void renderGlint(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, HumanoidModel<LivingEntity> humanoidModel) {
        humanoidModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.armorEntityGlint()), i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private ResourceLocation getArmorLocation(ArmorItem armorItem, @Nullable String type) {
        String location = UsefulHatsClientUtils.getArmorTexture(armorItem, type);
        ResourceLocation result = LOCATIONS.get(location);
        if (result == null) {
            result = new ResourceLocation(location);
            LOCATIONS.put(location, result);
        }
        return result;
    }

}
