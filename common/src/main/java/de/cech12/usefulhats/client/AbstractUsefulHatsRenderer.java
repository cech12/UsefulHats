package de.cech12.usefulhats.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.Equippable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractUsefulHatsRenderer {

    public static final ModelLayerLocation USEFUL_HAT_LAYER = new ModelLayerLocation(Constants.id("usefulhat_layer"), "main");

    private final Function<LayerTextureKey, Identifier> layerTextureLookup;
    private HumanoidModel<HumanoidRenderState> usefulHatModel;

    public AbstractUsefulHatsRenderer() {
        this.layerTextureLookup = Util.memoize((textureKey) -> textureKey.layer.getTextureLocation(textureKey.layerType));
    }

    public void render(ItemStack stack, HumanoidRenderState humanoidRenderState, PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, HumanoidModel<HumanoidRenderState> entityModel) {
        HumanoidModel<HumanoidRenderState> model = getArmorModel(stack);
        //entityModel.copyPropertiesTo(model); //follow rotation
        this.internalRender(stack, humanoidRenderState, matrices, submitNodeCollector, light, model);
    }

    public void render(ItemStack stack, HumanoidRenderState humanoidRenderState, PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, EntityModel<HumanoidRenderState> entityModel) {
        HumanoidModel<HumanoidRenderState> model = getArmorModel(stack);
        //model.setupAnim(humanoidRenderState); //follow rotation
        this.internalRender(stack, humanoidRenderState, matrices, submitNodeCollector, light, model);
    }

    protected <S extends LivingEntityRenderState> void internalRender(ItemStack stack, S humanoidRenderState, PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, EntityModel<S> model) {
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && equippable.assetId().isPresent() && equippable.slot() == EquipmentSlot.HEAD) {
            ResourceKey<EquipmentAsset> assetId = equippable.assetId().orElseThrow();
            EquipmentClientInfo.LayerType layerType = EquipmentClientInfo.LayerType.HUMANOID;
            List<EquipmentClientInfo.Layer> layers = Minecraft.getInstance().getEntityRenderDispatcher().equipmentAssets.get(assetId).getLayers(layerType);
            if (!layers.isEmpty()) {
                int defaultColor = stack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(stack, 0) : 0;
                boolean glint = stack.hasFoil();
                int i = 0;
                int outlineColor = 0;

                for (EquipmentClientInfo.Layer layer : layers) {
                    int color = getColorForLayer(layer, defaultColor);
                    if (color != 0) {
                        Identifier layerTexture = this.layerTextureLookup.apply(new LayerTextureKey(layerType, layer));
                        submitNodeCollector.order(i++).submitModel(model, humanoidRenderState, matrices, RenderTypes.armorTranslucent(layerTexture), light, OverlayTexture.NO_OVERLAY, color, null, outlineColor, null);
                        if (glint) {
                            submitNodeCollector.order(i++).submitModel(model, humanoidRenderState, matrices, RenderTypes.armorEntityGlint(), light, OverlayTexture.NO_OVERLAY, color, null, outlineColor, null);
                        }
                        glint = false;
                    }

                }
            }
        }
    }

    private static int getColorForLayer(EquipmentClientInfo.Layer layer, int defaultColor) {
        Optional<EquipmentClientInfo.Dyeable> optional = layer.dyeable();
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

    record LayerTextureKey(EquipmentClientInfo.LayerType layerType, EquipmentClientInfo.Layer layer) {}

}
