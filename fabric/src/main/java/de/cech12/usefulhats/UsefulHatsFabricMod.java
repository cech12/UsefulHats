package de.cech12.usefulhats;

import de.cech12.usefulhats.init.ModCreativeTabs;
import de.cech12.usefulhats.init.ModItems;
import net.fabricmc.api.ModInitializer;

public class UsefulHatsFabricMod implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonLoader.init();
        ModItems.init();
        ModCreativeTabs.init();
    }

}
