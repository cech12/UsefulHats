package de.cech12.usefulhats.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.cech12.usefulhats.CommonLoader;
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

    public static final ModelLayerLocation USEFUL_HAT_LAYER = new ModelLayerLocation(CommonLoader.id("usefulhat_layer"), "main");

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    private static final boolean IS_CHRISTMAS = Calendar.getInstance().get(Calendar.MONTH) + 1 == 12;

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
        int i = stack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(stack, ((AbstractHatItem)stack.getItem()).getDefaultColor()) : ((AbstractHatItem)stack.getItem()).getDefaultColor();
        float f = (float) FastColor.ARGB32.red(i) / 255.0F;
        float f1 = (float) FastColor.ARGB32.green(i) / 255.0F;
        float f2 = (float) FastColor.ARGB32.blue(i) / 255.0F;
        for (ArmorMaterial.Layer layer : ((ArmorItem) item).getMaterial().value().layers()) {
            if (layer.dyeable()) {
                this.renderLayer(matrices, vertexConsumers, light, flag1, model, f, f1, f2, this.getTexture((ArmorItem) stack.getItem(), layer));
            } else {
                this.renderLayer(matrices, vertexConsumers, light, flag1, model, 1.0F, 1.0F, 1.0F, this.getTexture((ArmorItem) stack.getItem(), layer));
            }
        }
        if (stack.hasFoil()) {
            this.renderGlint(matrices, vertexConsumers, light, model);
        }
    }

    protected abstract void followBodyRotations(LivingEntity livingEntity, HumanoidModel<LivingEntity> humanoidModel);

    private void renderLayer(PoseStack p_241738_1_, MultiBufferSource p_241738_2_, int p_241738_3_, boolean p_241738_5_, HumanoidModel<LivingEntity> p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource) {
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(p_241738_2_, p_241738_6_.renderType(armorResource), false, p_241738_5_);
        p_241738_6_.renderToBuffer(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
    }

    private void renderGlint(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, HumanoidModel<LivingEntity> humanoidModel) {
        humanoidModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.armorEntityGlint()), i, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
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
        return new ResourceLocation(location.getNamespace(), String.format("%s%s.png",
                location.getPath().replace("_layer_1", "").replace(".png", ""),
                (IS_CHRISTMAS && ((AbstractHatItem) armorItem).hasChristmasVariant()) ? "_xmas" : ""));
    }

}
