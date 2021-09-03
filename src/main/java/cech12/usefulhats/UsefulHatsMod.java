package cech12.usefulhats;

import cech12.usefulhats.compat.CuriosMod;
import cech12.usefulhats.config.Config;
import cech12.usefulhats.helper.UsefulHatsRecipeSerializers;
import cech12.usefulhats.init.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON, "usefulhats-legacy-common.toml");
        FMLJavaModLoadingContext.get().getModEventBus().register(new UsefulHatsRecipeSerializers());
    }

    @SubscribeEvent
    public static void sendImc(InterModEnqueueEvent evt) {
        CuriosMod.addHeadSlot();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientRegister(FMLClientSetupEvent event) {
        //register client event listeners
        ModItems.addClientEventListeners();
    }

}
