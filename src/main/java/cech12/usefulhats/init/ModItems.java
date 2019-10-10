package cech12.usefulhats.init;

import cech12.usefulhats.item.*;
import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= UsefulHatsMod.MOD_ID)
public class ModItems {

    private static final Item[] items = {
            new ChoppingHatItem(),
            new MiningHatItem(),
            new PostmanHatItem(),
            new StockingCapItem(),
            new StrawHatItem()
    };

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : ModItems.items) {
            event.getRegistry().register(item);
        }
    }

    @SubscribeEvent
    public static void registerColors(ColorHandlerEvent.Item event) {
        ItemColors itemcolors = event.getItemColors();
        for (Item item : ModItems.items) {
            if (item instanceof IDyeableArmorItem) {
                itemcolors.register((itemStack, layer) -> {
                    return layer > 0 ? -1 : ((IDyeableArmorItem)itemStack.getItem()).getColor(itemStack);
                }, item);
            }
        }

    }

}
