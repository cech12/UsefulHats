package cech12.usefulhats.init;

import cech12.usefulhats.item.*;
import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= UsefulHatsMod.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    private static final Item[] items = {
            new AquanautHelmetItem(),
            new ChoppingHatItem(),
            new MiningHatItem(),
            new PostmanHatItem(),
            new StockingCapItem(),
            new StrawHatItem(),
            new WingHelmetItem()
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

    /**
     * Called at mod initialization.
     */
    public static void addEventListeners() {
        MinecraftForge.EVENT_BUS.addListener(ModItems::onBreakSpeedEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onBreakEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onItemToolTipEvent);
    }

    private static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        ItemStack headSlotItemStack = event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD);
        for (Item item : ModItems.items) {
            if (item instanceof IBreakSpeedChanger && headSlotItemStack.getItem() == item) {
                ((IBreakSpeedChanger) item).onBreakSpeedEvent(event, headSlotItemStack);
            }
        }
    }

    private static void onBreakEvent(BlockEvent.BreakEvent event) {
        ItemStack headSlotItemStack = event.getPlayer().getItemStackFromSlot(EquipmentSlotType.HEAD);
        for (Item item : ModItems.items) {
            if (item instanceof IBreakSpeedChanger && headSlotItemStack.getItem() == item) {
                ((IBreakSpeedChanger) item).onBreakEvent(event, headSlotItemStack);
            }
        }
    }

    private static void onItemToolTipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        for (Item item : ModItems.items) {
            if (item instanceof AbstractHatItem && stack.getItem() == item) {
                ((AbstractHatItem) item).onItemToolTipEvent(stack, event.getToolTip());
            }
        }
    }
}
