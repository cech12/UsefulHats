package de.cech12.usefulhats;

import de.cech12.usefulhats.client.UsefulHatLayers;
import de.cech12.usefulhats.compat.CuriosMod;
import de.cech12.usefulhats.init.ModCreativeTabs;
import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.platform.Services;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid= Constants.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class UsefulHatsForgeMod {

    public UsefulHatsForgeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientRegister(FMLClientSetupEvent event) {
        ModItems.setupClient();
    }

}
