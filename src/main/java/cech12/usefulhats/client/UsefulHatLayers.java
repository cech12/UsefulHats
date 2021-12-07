package cech12.usefulhats.client;

import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class UsefulHatLayers {

    public static final ModelLayerLocation USEFUL_HAT_LAYER = new ModelLayerLocation(new ResourceLocation(UsefulHatsMod.MOD_ID, "usefulhat_layer"), "main");

    public static UsefulHatModel<LivingEntity> usefulHatModel = null;


    public static void initLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(USEFUL_HAT_LAYER, () -> UsefulHatModel.createLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION, 0));
    }

    public static void initModels(EntityRenderersEvent.AddLayers event) {
        usefulHatModel = new UsefulHatModel<>(net.minecraft.client.Minecraft.getInstance().getEntityModels().bakeLayer(USEFUL_HAT_LAYER));
    }

}
