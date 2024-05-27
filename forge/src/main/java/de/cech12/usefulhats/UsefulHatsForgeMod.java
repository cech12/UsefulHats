package de.cech12.usefulhats;

import de.cech12.usefulhats.compat.CuriosMod;
import de.cech12.usefulhats.init.ModCreativeTabs;
import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.platform.Services;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid= Constants.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class UsefulHatsForgeMod {

    public UsefulHatsForgeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modEventBus);
        ModItems.DATA_COMPONENTS.register(modEventBus);
        ModItems.addEventListeners();
        ModCreativeTabs.TABS.register(modEventBus);
        //Configs
        CommonLoader.init();
    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent evt) {
        if (Services.PLATFORM.isModLoaded(Constants.CURIOS_MOD_ID)) {
            CuriosMod.addHeadSlot();
        }
    }

}
