package de.cech12.usefulhats;

import de.cech12.usefulhats.client.UsefulHatLayers;
import de.cech12.usefulhats.compat.CuriosMod;
import de.cech12.usefulhats.init.ModCreativeTabs;
import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.platform.Services;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.common.Mod;
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
        if (Services.PLATFORM.isModLoaded(Constants.CURIOS_MOD_ID)) {
            CuriosMod.addHeadSlot();
        }
    }

}
