package cech12.usefulhats;

import cech12.usefulhats.client.UsefulHatLayer;
import cech12.usefulhats.client.UsefulHatModel;
import cech12.usefulhats.compat.CuriosMod;
import cech12.usefulhats.config.Config;
import cech12.usefulhats.helper.UsefulHatsRecipeSerializers;
import cech12.usefulhats.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static cech12.usefulhats.UsefulHatsMod.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(modid= MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class UsefulHatsMod {

    public static final String MOD_ID = "usefulhats";

    public UsefulHatsMod() {
        ModItems.addEventListeners();
        //Configs
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON, "usefulhats-legacy-common.toml");
        FMLJavaModLoadingContext.get().getModEventBus().register(new UsefulHatsRecipeSerializers());
    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent evt) {
        CuriosMod.addHeadSlot();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientRegister(FMLClientSetupEvent event) {
        //register client event listeners
        ModItems.addClientEventListeners();
        //add layer to armor stand renderer
        (Minecraft.getInstance().getRenderManager().renderers).forEach((e, r) -> {
            if (r instanceof ArmorStandRenderer) {
                ArmorStandRenderer renderer = (ArmorStandRenderer) r;
                renderer.addLayer(new UsefulHatLayer<>(renderer, new UsefulHatModel.ArmorStandModel()));
            }
        });
        //add layer to player renderer
        Minecraft.getInstance().getRenderManager().getSkinMap().values().forEach(r -> {
            r.addLayer(new UsefulHatLayer<>(r, new UsefulHatModel<>()));
        });
    }

}
