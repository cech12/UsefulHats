package de.cech12.usefulhats;

import de.cech12.usefulhats.init.ModCreativeTabs;
import de.cech12.usefulhats.init.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("unused")
@Mod(Constants.MOD_ID)
public class UsefulHatsForgeMod {

    public UsefulHatsForgeMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ModItems.ITEMS.register(modEventBus);
        ModItems.DATA_COMPONENTS.register(modEventBus);
        ModItems.addEventListeners();
        ModCreativeTabs.TABS.register(modEventBus);
        //Configs
        CommonLoader.init();
    }

}
