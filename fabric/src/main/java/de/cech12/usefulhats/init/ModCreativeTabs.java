package de.cech12.usefulhats.init;

import de.cech12.usefulhats.Constants;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {

    public static final CreativeModeTab MOD_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.id("main_tab"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.STOCKING_CAP))
                    .title(Component.translatable("tabs.usefulhats.main_tab"))
                    .displayItems((featureFlags, output) -> ModItems.ALL_HATS.forEach(output::accept)).build());

    public static void init() {}

}
