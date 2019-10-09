package cech12.init;

import cech12.item.ChoppingHatItem;
import cech12.item.MiningHatItem;
import cech12.item.StockingCapItem;
import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= UsefulHatsMod.MOD_ID)
public class ModItems {

    private static final Item[] items = {
            new ChoppingHatItem(),
            new MiningHatItem(),
            new StockingCapItem()
    };

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : ModItems.items) {
            event.getRegistry().register(item);
        }
    }

}
