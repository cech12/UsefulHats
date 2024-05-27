package de.cech12.usefulhats;

import de.cech12.usefulhats.compat.CuriosMod;
import de.cech12.usefulhats.init.ModCreativeTabs;
import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.platform.Services;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;

@Mod(Constants.MOD_ID)
@EventBusSubscriber(modid= Constants.MOD_ID, bus= EventBusSubscriber.Bus.MOD)
public class UsefulHatsNeoForgeMod {

    public UsefulHatsNeoForgeMod(IEventBus modEventBus) {
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
