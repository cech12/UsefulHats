package cech12.usefulhats.init;

import cech12.usefulhats.item.*;
import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= UsefulHatsMod.MOD_ID)
public class ModItems {

    private static final Item AQUANAUT_HELMET = new AquanautHelmetItem();

    private static final Item[] items = {
            AQUANAUT_HELMET,
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

    /**
     * GUI overlay for Aquanaut Helmet.
     */
    @SubscribeEvent
    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.gameSettings.thirdPersonView == 0) {
                ItemStack itemStack = mc.player.inventory.armorItemInSlot(3);
                if (!itemStack.isEmpty() && itemStack.getItem() == AQUANAUT_HELMET) {
                    AquanautHelmetItem.renderAquanautOverlay();
                    event.setCanceled(true);
                }
            }
        }
    }
}
