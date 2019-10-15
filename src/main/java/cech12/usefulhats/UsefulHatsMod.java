package cech12.usefulhats;

import cech12.usefulhats.init.ModItems;
import net.minecraftforge.fml.common.Mod;

import static cech12.usefulhats.UsefulHatsMod.MOD_ID;

@Mod(MOD_ID)
public class UsefulHatsMod {

    public static final String MOD_ID = "usefulhats";

    public UsefulHatsMod() {
        ModItems.addEventListeners();
    }

}
