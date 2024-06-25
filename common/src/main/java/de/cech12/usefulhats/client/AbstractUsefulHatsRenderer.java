package de.cech12.usefulhats.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.item.AbstractHatItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.Calendar;
import java.util.Map;

public abstract class AbstractUsefulHatsRenderer {

    public static final ModelLayerLocation USEFUL_HAT_LAYER = new ModelLayerLocation(Constants.id("usefulhat_layer"), "main");

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    private static final boolean IS_CHRISTMAS = Calendar.getInstance().get(Calendar.MONTH) + 1 == 12;
    private static final int WHITE = FastColor.ARGB32.colorFromFloat(1.0F, 1.0F, 1.0F, 1.0F);

    private HumanoidModel<LivingEntity> usefulHatModel;

    public void render(ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        HumanoidModel<LivingEntity> model = this.getModel(stack);
        model.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        model.prepareMobModel(entity, limbAngle, limbDistance, tickDelta);
        this.render(stack, matrices, vertexConsumers, light, entity, model);
    }

    public void render(ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity) {
        this.render(stack, matrices, vertexConsumers, light, entity, this.getModel(stack));
    }

    private void render(ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, HumanoidModel<LivingEntity> model) {
        Item item = stack.getItem();
        this.followBodyRotations(entity, model);
        model.copyPropertiesTo(model);
        boolean flag1 = stack.hasFoil();
        int color = stack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(stack, ((AbstractHatItem)stack.getItem()).getDefaultColor()) : ((AbstractHatItem)stack.getItem()).getDefaultColor();
        for (ArmorMaterial.Layer layer : ((ArmorItem) item).getMaterial().value().layers()) {
            if (layer.dyeable()) {
                this.renderLayer(matrices, vertexConsumers, light, flag1, model, color, this.getTexture((ArmorItem) stack.getItem(), layer));
            } else {
                this.renderLayer(matrices, vertexConsumers, light, flag1, model, WHITE, this.getTexture((ArmorItem) stack.getItem(), layer));
            }
        }
        if (stack.hasFoil()) {
            this.renderGlint(matrices, vertexConsumers, light, model);
        }
    }

    protected abstract void followBodyRotations(LivingEntity livingEntity, HumanoidModel<LivingEntity> humanoidModel);

    private void renderLayer(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, boolean bl, HumanoidModel<LivingEntity> humanoidModel, int color, ResourceLocation armorResource) {
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBuffer(multiBufferSource, humanoidModel.renderType(armorResource), false, bl);
        humanoidModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, color);
    }

    private void renderGlint(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, HumanoidModel<LivingEntity> humanoidModel) {
        humanoidModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.armorEntityGlint()), light, OverlayTexture.NO_OVERLAY, WHITE);
    }

    private HumanoidModel<LivingEntity> getModel(ItemStack stack) {
        if (usefulHatModel == null) {
            usefulHatModel = new UsefulHatModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(USEFUL_HAT_LAYER));
        }
        return usefulHatModel;
    }

    private ResourceLocation getTexture(ArmorItem armorItem, ArmorMaterial.Layer layer) {
        ResourceLocation location = getArmorTexture(armorItem, layer);
        return ARMOR_TEXTURE_RES_MAP.computeIfAbsent(location.toString(), k -> location);
    }

    public static ResourceLocation getArmorTexture(ArmorItem armorItem, ArmorMaterial.Layer layer) {
        ResourceLocation location = layer.texture(false);
        return location.withPath(String.format("%s%s.png",
                location.getPath().replace("_layer_1", "").replace(".png", ""),
                (IS_CHRISTMAS && ((AbstractHatItem) armorItem).hasChristmasVariant()) ? "_xmas" : ""));
    }

}
