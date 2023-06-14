package cech12.usefulhats.compat;

import net.minecraftforge.fml.ModList;

public class LucentMod {

    public static final String MOD_ID = "lucent";

    private LucentMod() {}

    public static boolean isLoaded() {
        return false; //TODO re-add Lucent
        //return ModList.get().isLoaded(MOD_ID);
    }

}
