package de.cech12.usefulhats;

import de.cech12.usefulhats.client.UsefulHatLayers;
import de.cech12.usefulhats.compat.CuriosMod;
import de.cech12.usefulhats.init.ModCreativeTabs;
import de.cech12.usefulhats.init.ModItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid= Constants.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class UsefulHatsNeoForgeMod {

    public UsefulHatsNeoForgeMod(IEventBus modEventBus) {
        ModItems.ITEMS.register(modEventBus);
        ModItems.addEventListeners();
        ModCreativeTabs.TABS.register(modEventBus);
        //Configs
        CommonLoader.init();
        //register render layers & models
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(UsefulHatLayers::initLayers);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(UsefulHatLayers::initModels);
        });
    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent evt) {
        CuriosMod.addHeadSlot();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientRegister(FMLClientSetupEvent event) {
        ModItems.setupClient();
    }

}
