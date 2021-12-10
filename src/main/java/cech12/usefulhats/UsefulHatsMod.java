package cech12.usefulhats;

import cech12.usefulhats.client.UsefulHatLayers;
import cech12.usefulhats.compat.CuriosMod;
import cech12.usefulhats.config.ServerConfig;
import cech12.usefulhats.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import static cech12.usefulhats.UsefulHatsMod.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(modid= MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class UsefulHatsMod {

    public static final String MOD_ID = "usefulhats";

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab("usefulhats") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.STOCKING_CAP);
        }
    };

    public UsefulHatsMod() {
        ModItems.addEventListeners();
        //Configs
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_CONFIG);
        ServerConfig.loadConfig(ServerConfig.SERVER_CONFIG, FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).resolve(MOD_ID + "-server.toml"));
        //register render layers & models
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(UsefulHatLayers::initLayers);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(UsefulHatLayers::initModels);
        });

    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent evt) {
        CuriosMod.addHeadSlot();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientRegister(FMLClientSetupEvent event) {
        ModItems.setupClient();
    }

}
