package de.cech12.usefulhats;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.resources.ResourceLocation;

/**
 * A static class for all loaders which initializes everything which is used by all loaders.
 */
public class CommonLoader {

    /**
     * Initialize method that should be called by every loader mod in the constructor.
     */
    public static void init() {
        Services.CONFIG.init();
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    private CommonLoader() {}

}
