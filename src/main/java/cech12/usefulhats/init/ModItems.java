package cech12.usefulhats.init;

import cech12.usefulhats.item.*;
import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.event.LivingCurioChangeEvent;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(modid= UsefulHatsMod.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    private static final Item[] items = {
            new AquanautHelmetItem(),
            new ChoppingHatItem(),
            new HaloItem(),
            new LuckyHatItem(),
            new MiningHatItem(),
            new PostmanHatItem(),
            new StockingCapItem(),
            new StrawHatItem(),
            new WingHelmetItem()
    };

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : ModItems.items) {
            event.getRegistry().register(item);
        }
    }

    @SuppressWarnings("unused")
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerColors(ColorHandlerEvent.Item event) {
        ItemColors itemcolors = event.getItemColors();
        for (Item item : ModItems.items) {
            //if (item instanceof IDyeableArmorItem) {
            itemcolors.register((itemStack, layer) -> layer > 0 ? -1 : ((IDyeableArmorItem)itemStack.getItem()).getColor(itemStack), item);
            //}
        }
    }

    /**
     * Called at mod initialization.
     */
    public static void addEventListeners() {
        MinecraftForge.EVENT_BUS.addListener(ModItems::onBreakSpeedEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onBreakEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onEntityJoinWorldEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onItemFishedEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingDropsEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingEquipmentChangeEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingSetAttackTargetEvent);
        //curios events
        if (ModList.get().isLoaded("curios")) {
            MinecraftForge.EVENT_BUS.addListener(ModItems::onCuriosEquipmentChangeEvent);
        }
    }

    /**
     * Called at mod initialization. (client side)
     */
    @OnlyIn(Dist.CLIENT)
    public static void addClientEventListeners() {
        MinecraftForge.EVENT_BUS.addListener(ModItems::onItemToolTipEvent);
    }

    /**
     * Get all equipped head slot item stacks. Some APIs like Curios enables to have more
     * than one head slot.
     * @param entity entity
     * @return List of all equipped head slot item stacks
     */
    public static List<ItemStack> getHeadSlotItemStacks(LivingEntity entity) {
        List<ItemStack> stacks = new LinkedList<>();
        //vanilla head slot
        stacks.add(entity.getItemStackFromSlot(EquipmentSlotType.HEAD));
        //curios head slots
        CuriosAPI.getCuriosHandler(entity).ifPresent(itemHandler -> {
            int slots = itemHandler.getSlots();
            for (int i = 0; i < slots; i++) {
                ItemStack stack = itemHandler.getStackInSlot("head", i);
                if (!stack.isEmpty()) {
                    stacks.add(stack);
                }
            }
        });
        return stacks;
    }

    private static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        for (ItemStack headSlotItemStack : getHeadSlotItemStacks(event.getPlayer())) {
            for (Item item : ModItems.items) {
                if (item instanceof IBreakSpeedChanger && headSlotItemStack.getItem() == item) {
                    ((IBreakSpeedChanger) item).onBreakSpeedEvent(event, headSlotItemStack);
                }
            }
        }
    }

    private static void onBreakEvent(BlockEvent.BreakEvent event) {
        for (ItemStack headSlotItemStack : getHeadSlotItemStacks(event.getPlayer())) {
            for (Item item : ModItems.items) {
                if (item instanceof IBreakSpeedChanger && headSlotItemStack.getItem() == item) {
                    ((IBreakSpeedChanger) item).onBreakEvent(event, headSlotItemStack);
                }
            }
        }
    }

    private static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof MobEntity) {
            MobEntity entity = (MobEntity) event.getEntity();
            for (Item item : ModItems.items) {
                if (item instanceof IMobEntityChanger) {
                    ((IMobEntityChanger) item).onEntityJoinWorldEvent(entity, event);
                }
            }
        }
    }

    private static void onItemFishedEvent(ItemFishedEvent event) {
        for (ItemStack headSlotItemStack : getHeadSlotItemStacks(event.getPlayer())) {
            for (Item item : ModItems.items) {
                if (item instanceof IItemFishedListener && headSlotItemStack.getItem() == item) {
                    ((IItemFishedListener) item).onItemFishedListener(event, headSlotItemStack);
                }
            }
        }
    }

    private static void onItemToolTipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        for (Item item : ModItems.items) {
            if (/*item instanceof AbstractHatItem &&*/ stack.getItem() == item) {
                ((AbstractHatItem) item).onItemToolTipEvent(stack, event.getToolTip());
            }
        }
    }

    private static void onLivingDropsEvent(LivingDropsEvent event) {
        if (event.getSource().getImmediateSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getImmediateSource();
            for (ItemStack headSlotItemStack : getHeadSlotItemStacks(player)) {
                for (Item item : ModItems.items) {
                    if (item instanceof ILivingDropsListener && item == headSlotItemStack.getItem()) {
                        ((ILivingDropsListener) item).onLivingDropsEvent(event, player, headSlotItemStack);
                    }
                }
            }
        }
    }

    private static void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == EquipmentSlotType.HEAD) {
            ItemStack fromItem = event.getFrom();
            ItemStack toItem = event.getTo();
            for (Item item : ModItems.items) {
                if (item instanceof IEquipmentChangeListener) {
                    if (fromItem.getItem() == item && toItem.getItem() != item) {
                        ((IEquipmentChangeListener) item).onUnequippedHatItem(event.getEntityLiving(), fromItem);
                    }
                    if (fromItem.getItem() != item && toItem.getItem() == item) {
                        ((IEquipmentChangeListener) item).onEquippedHatItem(event.getEntityLiving(), toItem);
                    }
                }
            }
        }
    }

    /**
     * equipment change event of curios mod
     */
    private static void onCuriosEquipmentChangeEvent(LivingCurioChangeEvent event) {
        if (event.getTypeIdentifier().equals("head")) {
            ItemStack fromItem = event.getFrom();
            ItemStack toItem = event.getTo();
            for (Item item : ModItems.items) {
                if (item instanceof IEquipmentChangeListener) {
                    if (fromItem.getItem() == item && toItem.getItem() != item) {
                        ((IEquipmentChangeListener) item).onUnequippedHatItem(event.getEntityLiving(), fromItem);
                    }
                    if (fromItem.getItem() != item && toItem.getItem() == item) {
                        ((IEquipmentChangeListener) item).onEquippedHatItem(event.getEntityLiving(), toItem);
                    }
                }
            }
        }
    }

    private static void onLivingSetAttackTargetEvent(LivingSetAttackTargetEvent event) {
        if (event.getEntity() instanceof MobEntity && event.getTarget() instanceof PlayerEntity) {
            MobEntity mob = (MobEntity) event.getEntity();
            PlayerEntity player = (PlayerEntity) event.getTarget();
            for (ItemStack headSlotItemStack : getHeadSlotItemStacks(player)) {
                for (Item item : ModItems.items) {
                    if (item instanceof IAttackTargetChanger && item == headSlotItemStack.getItem()) {
                        ((IAttackTargetChanger) item).onLivingSetAttackTarget(mob, player);
                    }
                }
            }
        }
    }
}
