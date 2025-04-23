package de.cech12.usefulhats.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.item.AbstractHatItem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.world.item.equipment.Equippable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractUsefulHatsRenderer {

    public static final ModelLayerLocation USEFUL_HAT_LAYER = new ModelLayerLocation(Constants.id("usefulhat_layer"), "main");

    private final Function<LayerTextureKey, ResourceLocation> layerTextureLookup;
    private HumanoidModel<HumanoidRenderState> usefulHatModel;

    public AbstractUsefulHatsRenderer() {
        this.layerTextureLookup = Util.memoize((textureKey) -> textureKey.layer.getTextureLocation(textureKey.layerType));
    }

    public void render(ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, int light, HumanoidModel<HumanoidRenderState> entityModel) {
        HumanoidModel<HumanoidRenderState> model = getArmorModel(stack);
        entityModel.copyPropertiesTo(model); //follow rotation
        this.internalRender(stack, matrices, vertexConsumers, light, model);
    }

    public void render(ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, int light, HumanoidRenderState humanoidRenderState) {
        HumanoidModel<HumanoidRenderState> model = getArmorModel(stack);
        model.setupAnim(humanoidRenderState); //follow rotation
        this.internalRender(stack, matrices, vertexConsumers, light, model);
    }

    protected <S extends LivingEntityRenderState> void internalRender(ItemStack stack, PoseStack matrices, MultiBufferSource vertexConsumers, int light, EntityModel<S> model) {
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && equippable.model().isPresent() && equippable.slot() == EquipmentSlot.HEAD) {
            ResourceLocation modelId = equippable.model().orElseThrow();
            EquipmentModel.LayerType layerType = EquipmentModel.LayerType.HUMANOID;
            List<EquipmentModel.Layer> layers = Minecraft.getInstance().getEquipmentModels().get(modelId).getLayers(layerType);
            if (!layers.isEmpty()) {
                int defaultColor = getDefaultColor(stack);
                boolean glint = stack.hasFoil();

                for (EquipmentModel.Layer layer : layers) {
                    int color = getColorForLayer(layer, defaultColor);
                    if (color != 0) {
                        ResourceLocation layerTexture = this.layerTextureLookup.apply(new LayerTextureKey(layerType, layer));
                        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorTranslucent(layerTexture), glint);
                        model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, color);
                        glint = false;
                    }
                }
            }
        }
    }

    public static int getDefaultColor(ItemStack stack) {
        if (stack.getItem() instanceof AbstractHatItem abstractHatItem) {
            return stack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(stack, abstractHatItem.getDefaultColor()) : abstractHatItem.getDefaultColor();
        }
        return 0;
    }

    private static int getColorForLayer(EquipmentModel.Layer layer, int defaultColor) {
        Optional<EquipmentModel.Dyeable> optional = layer.dyeable();
        if (optional.isPresent()) {
            int color = optional.get().colorWhenUndyed().map(ARGB::opaque).orElse(0);
            return defaultColor != 0 ? defaultColor : color;
        } else {
            return -1;
        }
    }

    private HumanoidModel<HumanoidRenderState> getArmorModel(ItemStack stack) {
        if (usefulHatModel == null) {
            usefulHatModel = new UsefulHatModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(USEFUL_HAT_LAYER));
        }
        return usefulHatModel;
    }

    record LayerTextureKey(EquipmentModel.LayerType layerType, EquipmentModel.Layer layer) {}

}
