package cech12.usefulhats;

import cech12.usefulhats.init.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static cech12.usefulhats.UsefulHatsMod.MOD_ID;

@Mod(MOD_ID)
public class UsefulHatsMod {

    public static final String MOD_ID = "usefulhats";

    private static final Logger LOGGER = LogManager.getLogger();

    public UsefulHatsMod() {
        FMLJavaModLoadingContext.get().getModEventBus().register(ModItems.class);
    }

}
