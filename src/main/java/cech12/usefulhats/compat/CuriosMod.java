package cech12.usefulhats.compat;

import net.minecraftforge.fml.ModList;

public class CuriosMod {

    private CuriosMod() {}

    public static boolean isLoaded() {
        return ModList.get().isLoaded("curios");
    }

}
