package de.cech12.usefulhats.client;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.client.compat.CurioClientCompat;
import de.cech12.usefulhats.item.IGameOverlayRenderer;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UsefulHatsForgeClientEvents {

    public static final ModelLayerLocation USEFUL_HAT_LAYER = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "usefulhat_layer"), "main");

    public static UsefulHatModel<LivingEntity> usefulHatModel = null;

    @SubscribeEvent
    public static void onClientRegister(FMLClientSetupEvent event) {
        //curio rendering
        if (Services.PLATFORM.isModLoaded(Constants.CURIOS_MOD_ID)) {
            CurioClientCompat.register();
        }
    }

    @SubscribeEvent
    public static void initLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(USEFUL_HAT_LAYER, () -> UsefulHatModel.createLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION, 0));
    }

    @SubscribeEvent
    public static void initModels(EntityRenderersEvent.AddLayers event) {
        usefulHatModel = new UsefulHatModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(USEFUL_HAT_LAYER));
    }

    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        ItemColors itemcolors = event.getItemColors();
        for (Item item : Services.REGISTRY.getAllHatItems()) {
            //if (item instanceof IDyeableArmorItem) {
            itemcolors.register((itemStack, layer) -> layer > 0 ? -1 : ((DyeableLeatherItem)itemStack.getItem()).getColor(itemStack), item);
            //}
        }
    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("aquanaut_helmet_overlay", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            if (Minecraft.useFancyGraphics()) {
                gui.setupOverlayRenderState(true, false);
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.options.getCameraType().isFirstPerson()) {
                    for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(mc.player)) {
                        if (headSlotItemStack.getItem() instanceof IGameOverlayRenderer gameOverlayRenderer) {
                            gameOverlayRenderer.onRenderGameOverlay(screenWidth, screenHeight, partialTicks);
                        }
                    }
                }
            }
        });
    }

}
