package de.cech12.usefulhats.client;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.item.AbstractHatItem;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class UsefulHatsNeoForgeClientEvents {

    public static final ModelLayerLocation USEFUL_HAT_LAYER = new ModelLayerLocation(Constants.id("usefulhat_layer"), "main");

    public static UsefulHatModel<LivingEntity> usefulHatModel = null;

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(USEFUL_HAT_LAYER, () -> UsefulHatModel.createLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION, 0));
    }

    @SubscribeEvent
    public static void registerModels(EntityRenderersEvent.AddLayers event) {
        usefulHatModel = new UsefulHatModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(USEFUL_HAT_LAYER));
    }

    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        for (Item item : Services.REGISTRY.getAllHatItems()) {
            event.register((itemStack, layer) -> layer > 0 ? -1 : DyedItemColor.getOrDefault(itemStack, ((AbstractHatItem)itemStack.getItem()).getDefaultColor()), item);
        }
    }

}
