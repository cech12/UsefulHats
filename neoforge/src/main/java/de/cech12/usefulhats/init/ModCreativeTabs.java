package de.cech12.usefulhats.init;

import de.cech12.usefulhats.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = TABS.register("main_tab", () ->
            CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.STOCKING_CAP.get()))
                    .title(Component.translatable("tabs.usefulhats.main_tab"))
                    .displayItems((featureFlags, output) -> {
                        ModItems.ITEMS.getEntries().forEach(itemRegistryObject -> output.accept(itemRegistryObject.get()));
                    }).build());

}
