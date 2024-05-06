package de.cech12.usefulhats.client.compat;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.cech12.usefulhats.client.UsefulHatsClientUtils;
import de.cech12.usefulhats.client.UsefulHatsFabricClientMod;
import de.cech12.usefulhats.item.IUsefulHatModelOwner;
import de.cech12.usefulhats.platform.Services;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class TrinketsClientCompat {

    public static void register() {
        for (Item item : Services.REGISTRY.getAllHatItems()) {
            TrinketRendererRegistry.registerRenderer(item, UsefulHatsTrinketRenderer.getInstance());
        }
    }

    private static class UsefulHatsTrinketRenderer implements TrinketRenderer {

        private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();

        private static UsefulHatsTrinketRenderer instance;

        private HumanoidModel<LivingEntity> model;

        @Override
        public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
            Item item = stack.getItem();
            HumanoidModel<LivingEntity> model = this.getModel(stack);
            model.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            model.prepareMobModel(entity, limbAngle, limbDistance, tickDelta);
            TrinketRenderer.followBodyRotations(entity, model);
            //mostly copied from UsefulHatLayer
            model.copyPropertiesTo(model);
            boolean flag1 = stack.hasFoil();
            int i = ((net.minecraft.world.item.DyeableLeatherItem)item).getColor(stack);
            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            this.renderLayer(matrices, vertexConsumers, light, flag1, model, f, f1, f2, this.getTexture(stack, null));
            this.renderLayer(matrices, vertexConsumers, light, flag1, model, 1.0F, 1.0F, 1.0F, this.getTexture(stack, "overlay"));
        }

        private void renderLayer(PoseStack p_241738_1_, MultiBufferSource p_241738_2_, int p_241738_3_, boolean p_241738_5_, HumanoidModel<LivingEntity> p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource) {
            //mostly copied from UsefulHatLayer
            VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(p_241738_2_, p_241738_6_.renderType(armorResource), false, p_241738_5_);
            p_241738_6_.renderToBuffer(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
        }

        public static UsefulHatsTrinketRenderer getInstance() {
            if (instance == null) {
                instance = new UsefulHatsTrinketRenderer();
            }
            return instance;
        }

        private HumanoidModel<LivingEntity> getModel(ItemStack stack) {
            if (stack.getItem() instanceof IUsefulHatModelOwner) {
                return UsefulHatsFabricClientMod.getUsefulHatModel();
            } else {
                if (model == null) {
                    model = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
                }
                return model;
            }
        }

        private ResourceLocation getTexture(ItemStack stack, String type) {
            ArmorItem item = (ArmorItem) stack.getItem();
            String locationString;
            if (item instanceof IUsefulHatModelOwner) {
                locationString = UsefulHatsClientUtils.getArmorTexture(item, type);
            } else {
                //mostly copied from BipedArmorLayer class
                String texture = item.getMaterial().getName();
                String domain = "minecraft";
                int idx = texture.indexOf(':');
                if (idx != -1) {
                    domain = texture.substring(0, idx);
                    texture = texture.substring(idx + 1);
                }
                locationString = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, 1, type == null ? "" : String.format("_%s", type));
            }
            ResourceLocation location = ARMOR_TEXTURE_RES_MAP.get(locationString);
            if (location == null && locationString != null) {
                location = new ResourceLocation(locationString);
                ARMOR_TEXTURE_RES_MAP.put(locationString, location);
            }
            return location;
        }

    }

}
