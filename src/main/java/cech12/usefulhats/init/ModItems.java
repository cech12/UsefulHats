package cech12.usefulhats.init;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.compat.CuriosMod;
import cech12.usefulhats.item.*;
import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

@Mod.EventBusSubscriber(modid= UsefulHatsMod.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    public static final StockingCapItem STOCKING_CAP = new StockingCapItem();

    private static final Item[] items = {
            new AquanautHelmetItem(),
            new BunnyEarsItem(),
            new ChoppingHatItem(),
            new EnderHelmetItem(),
            new HaloItem(),
            new LuckyHatItem(),
            new MiningHatItem(),
            new MushroomHatItem(),
            new PostmanHatItem(),
            new ShulkerHelmetItem(),
            STOCKING_CAP,
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
            itemcolors.register((itemStack, layer) -> layer > 0 ? -1 : ((DyeableLeatherItem)itemStack.getItem()).getColor(itemStack), item);
            //}
        }
    }

    /**
     * Called at mod initialization.
     */
    public static void addEventListeners() {
        //reduce event priority to support other mods that are overriding the speed
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, ModItems::onBreakSpeedEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onBreakEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onEntityJoinWorldEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onItemFishedEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingDropsEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingEquipmentChangeEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingSetAttackTargetEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingUseItemEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onRightClickItemEvent);
        //curios events
        if (CuriosMod.isLoaded()) {
            MinecraftForge.EVENT_BUS.addListener(ModItems::onCuriosEquipmentChangeEvent);
        }
    }

    /**
     * Called at mod initialization. (client side)
     */
    @OnlyIn(Dist.CLIENT)
    public static void addClientEventListeners() {
        MinecraftForge.EVENT_BUS.addListener(ModItems::onRenderGameOverlayEvent);
    }

    private static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        for (ItemStack headSlotItemStack : UsefulHatsUtils.getEquippedHatItemStacks(event.getPlayer())) {
            for (Item item : ModItems.items) {
                if (item instanceof IBreakSpeedChanger && headSlotItemStack.getItem() == item) {
                    ((IBreakSpeedChanger) item).onBreakSpeedEvent(event, headSlotItemStack);
                }
            }
        }
    }

    private static void onBreakEvent(BlockEvent.BreakEvent event) {
        for (ItemStack headSlotItemStack : UsefulHatsUtils.getEquippedHatItemStacks(event.getPlayer())) {
            for (Item item : ModItems.items) {
                if (item instanceof IBreakSpeedChanger && headSlotItemStack.getItem() == item) {
                    ((IBreakSpeedChanger) item).onBreakEvent(event, headSlotItemStack);
                }
            }
        }
    }

    private static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Mob) {
            Mob entity = (Mob) event.getEntity();
            for (Item item : ModItems.items) {
                if (item instanceof IMobEntityChanger) {
                    ((IMobEntityChanger) item).onEntityJoinWorldEvent(entity, event);
                }
            }
        }
    }

    private static void onItemFishedEvent(ItemFishedEvent event) {
        for (ItemStack headSlotItemStack : UsefulHatsUtils.getEquippedHatItemStacks(event.getPlayer())) {
            for (Item item : ModItems.items) {
                if (item instanceof IItemFishedListener && headSlotItemStack.getItem() == item) {
                    ((IItemFishedListener) item).onItemFishedListener(event, headSlotItemStack);
                }
            }
        }
    }

    private static void onLivingDropsEvent(LivingDropsEvent event) {
        if (event.getSource().getDirectEntity() instanceof Player) {
            Player player = (Player) event.getSource().getDirectEntity();
            for (ItemStack headSlotItemStack : UsefulHatsUtils.getEquippedHatItemStacks(player)) {
                for (Item item : ModItems.items) {
                    if (item instanceof ILivingDropsListener && item == headSlotItemStack.getItem()) {
                        ((ILivingDropsListener) item).onLivingDropsEvent(event, player, headSlotItemStack);
                    }
                }
            }
        }
    }

    private static void onLivingUseItemEvent(LivingEntityUseItemEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();
            for (ItemStack headSlotItemStack : UsefulHatsUtils.getEquippedHatItemStacks(player)) {
                for (Item item : ModItems.items) {
                    if (item instanceof IItemUseListener && item == headSlotItemStack.getItem()) {
                        ((IItemUseListener) item).onItemUseEvent(event, player, headSlotItemStack);
                    }
                }
            }
        }
    }

    private static void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == EquipmentSlot.HEAD) {
            ItemStack fromItemStack = event.getFrom();
            Item fromItem = fromItemStack.getItem();
            ItemStack toItemStack = event.getTo();
            Item toItem = toItemStack.getItem();
            if (fromItem != toItem && (fromItem instanceof IEquipmentChangeListener || toItem instanceof IEquipmentChangeListener)) {
                for (Item item : ModItems.items) {
                    if (item instanceof IEquipmentChangeListener) {
                        if (fromItem == item) {
                            ((IEquipmentChangeListener) item).onUnequippedHatItem(event.getEntityLiving(), fromItemStack);
                        } else if (toItem == item) {
                            ((IEquipmentChangeListener) item).onEquippedHatItem(event.getEntityLiving(), toItemStack);
                        }
                    }
                }
            }
        }
    }

    /**
     * equipment change event of curios mod
     */
    private static void onCuriosEquipmentChangeEvent(CurioChangeEvent event) {
        ItemStack fromItemStack = event.getFrom();
        Item fromItem = fromItemStack.getItem();
        ItemStack toItemStack = event.getTo();
        Item toItem = toItemStack.getItem();
        if (fromItem != toItem && (fromItem instanceof IEquipmentChangeListener || toItem instanceof IEquipmentChangeListener)) {
            for (Item item : ModItems.items) {
                if (item instanceof IEquipmentChangeListener) {
                    if (fromItem == item) {
                        ((IEquipmentChangeListener) item).onUnequippedHatItem(event.getEntityLiving(), fromItemStack);
                    } else if (toItem == item) {
                        ((IEquipmentChangeListener) item).onEquippedHatItem(event.getEntityLiving(), toItemStack);
                    }
                }
            }
        }
    }

    private static void onLivingSetAttackTargetEvent(LivingSetAttackTargetEvent event) {
        if (event.getEntity() instanceof Mob && event.getTarget() instanceof Player) {
            Mob mob = (Mob) event.getEntity();
            Player player = (Player) event.getTarget();
            for (ItemStack headSlotItemStack : UsefulHatsUtils.getEquippedHatItemStacks(player)) {
                for (Item item : ModItems.items) {
                    if (item instanceof IAttackTargetChanger && item == headSlotItemStack.getItem()) {
                        ((IAttackTargetChanger) item).onLivingSetAttackTarget(mob, player);
                    }
                }
            }
        }
    }

    private static void onRightClickItemEvent(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        for (ItemStack headSlotItemStack : UsefulHatsUtils.getEquippedHatItemStacks(player)) {
            for (Item item : ModItems.items) {
                if (item instanceof IRightClickListener && item == headSlotItemStack.getItem()) {
                    ((IRightClickListener) item).onRightClickItemEvent(event, headSlotItemStack);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event) {
        if (!event.isCanceled() && event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.options.getCameraType().isFirstPerson()) { //isNotThirdPerson
                for (ItemStack headSlotItemStack : UsefulHatsUtils.getEquippedHatItemStacks(mc.player)) {
                    for (Item item : ModItems.items) {
                        if (item instanceof IGameOverlayRenderer && item == headSlotItemStack.getItem()) {
                            ((IGameOverlayRenderer) item).onRenderGameOverlay(mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(), event.getPartialTicks());
                        }
                    }
                }
            }
        }
    }

}
