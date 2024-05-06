package de.cech12.usefulhats.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.cech12.usefulhats.CommonLoader;
import de.cech12.usefulhats.item.IUsefulHatModelOwner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;
import java.util.Map;

public abstract class AbstractUsefulHatsRenderer {

    public static final ModelLayerLocation USEFUL_HAT_LAYER = new ModelLayerLocation(CommonLoader.id("usefulhat_layer"), "main");

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    private static final boolean IS_CHRISTMAS = Calendar.getInstance().get(Calendar.MONTH) + 1 == 12;

    private HumanoidModel<LivingEntity> usefulHatModel;
    private HumanoidModel<LivingEntity> model;

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
        int i = ((net.minecraft.world.item.DyeableLeatherItem)item).getColor(stack);
        float f = (float)(i >> 16 & 255) / 255.0F;
        float f1 = (float)(i >> 8 & 255) / 255.0F;
        float f2 = (float)(i & 255) / 255.0F;
        this.renderLayer(matrices, vertexConsumers, light, flag1, model, f, f1, f2, this.getTexture((ArmorItem) stack.getItem(), null));
        this.renderLayer(matrices, vertexConsumers, light, flag1, model, 1.0F, 1.0F, 1.0F, this.getTexture((ArmorItem) stack.getItem(), "overlay"));
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
        if (stack.getItem() instanceof IUsefulHatModelOwner) {
            if (usefulHatModel == null) {
                usefulHatModel = new UsefulHatModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(USEFUL_HAT_LAYER));
            }
            return usefulHatModel;
        } else {
            if (model == null) {
                model = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
            }
            return model;
        }
    }

    private ResourceLocation getTexture(ArmorItem armorItem, @Nullable String type) {
        String location = getArmorTexture(armorItem, type);
        ResourceLocation result = ARMOR_TEXTURE_RES_MAP.get(location);
        if (result == null) {
            result = new ResourceLocation(location);
            ARMOR_TEXTURE_RES_MAP.put(location, result);
        }
        return result;
    }

    public static String getArmorTexture(ArmorItem armorItem, String type) {
        String texture = armorItem.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        if (armorItem instanceof IUsefulHatModelOwner usefulHatModelOwner) {
            return String.format("%s:textures/models/usefulhats/%s%s%s.png", domain, texture,
                    (IS_CHRISTMAS && usefulHatModelOwner.hasChristmasVariant()) ? "_xmas" : "",
                    type == null ? "" : String.format("_%s", type));
        }
        return String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture,
                1, type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));
    }

}
