package de.cech12.usefulhats;

import de.cech12.usefulhats.init.ModCreativeTabs;
import de.cech12.usefulhats.init.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@SuppressWarnings("unused")
@Mod(Constants.MOD_ID)
public class UsefulHatsNeoForgeMod {

    public UsefulHatsNeoForgeMod(IEventBus modEventBus) {
        ModItems.ITEMS.register(modEventBus);
        ModItems.DATA_COMPONENTS.register(modEventBus);
        ModItems.addEventListeners();
        ModCreativeTabs.TABS.register(modEventBus);
        //Configs
        CommonLoader.init();
    }

}
