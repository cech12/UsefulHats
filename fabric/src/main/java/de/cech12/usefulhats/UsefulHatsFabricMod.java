package de.cech12.usefulhats;

import de.cech12.usefulhats.compat.AccessoriesCompat;
import de.cech12.usefulhats.init.ModCreativeTabs;
import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.platform.Services;
import net.fabricmc.api.ModInitializer;

public class UsefulHatsFabricMod implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonLoader.init();
        ModItems.init();
        ModCreativeTabs.init();

        /* TODO
        if (Services.PLATFORM.isModLoaded(TrinketsCompat.MOD_ID)) {
            TrinketsCompat.register();
        }
         */
        if (Services.PLATFORM.isModLoaded(Constants.ACCESSORIES_MOD_ID)) {
            AccessoriesCompat.register();
        }
    }

}
