package cech12.usefulhats.init;

import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UsefulHatsMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_TAB = TABS.register("main_tab", () ->
            CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.STOCKING_CAP.get()))
                    .title(Component.translatable("tabs.usefulhats.main_tab"))
                    .displayItems((featureFlags, output) -> {
                        ModItems.ITEMS.getEntries().forEach(itemRegistryObject -> output.accept(itemRegistryObject.get()));
                    }).build());

}
