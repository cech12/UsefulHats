package de.cech12.usefulhats.init;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.client.compat.CurioRenderer;
import de.cech12.usefulhats.item.*;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

@Mod.EventBusSubscriber(modid= Constants.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Constants.MOD_ID);

    public static final DeferredHolder<Item, Item> AQUANAUT_HELMET = ITEMS.register("aquanaut_helmet", AquanautHelmetItem::new);
    public static final DeferredHolder<Item, Item> BUNNY_EARS = ITEMS.register("bunny_ears", BunnyEarsItem::new);
    public static final DeferredHolder<Item, Item> CHOPPING_HAT = ITEMS.register("chopping_hat", ChoppingHatItem::new);
    public static final DeferredHolder<Item, Item> ENDER_HELMET = ITEMS.register("ender_helmet", EnderHelmetItem::new);
    public static final DeferredHolder<Item, Item> HALO = ITEMS.register("halo", HaloItem::new);
    public static final DeferredHolder<Item, Item> LUCKY_HAT = ITEMS.register("lucky_hat", LuckyHatItem::new);
    public static final DeferredHolder<Item, Item> MINING_HAT = ITEMS.register("mining_hat", MiningHatItem::new);
    public static final DeferredHolder<Item, Item> MUSHROOM_HAT = ITEMS.register("mushroom_hat", MushroomHatItem::new);
    public static final DeferredHolder<Item, Item> POSTMAN_HAT = ITEMS.register("postman_hat", PostmanHatItem::new);
    public static final DeferredHolder<Item, Item> SHULKER_HELMET = ITEMS.register("shulker_helmet", ShulkerHelmetItem::new);
    public static final DeferredHolder<Item, Item> STOCKING_CAP = ITEMS.register("stocking_cap", StockingCapItem::new);
    public static final DeferredHolder<Item, Item> STRAW_HAT = ITEMS.register("straw_hat", StrawHatItem::new);
    public static final DeferredHolder<Item, Item> WING_HELMET = ITEMS.register("wing_helmet", WingHelmetItem::new);

    @SuppressWarnings("unused")
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        ItemColors itemcolors = event.getItemColors();
        for (DeferredHolder<Item, ?> item : ITEMS.getEntries()) {
            //if (item instanceof IDyeableArmorItem) {
            itemcolors.register((itemStack, layer) -> layer > 0 ? -1 : ((DyeableLeatherItem)itemStack.getItem()).getColor(itemStack), item.get());
            //}
        }
    }

    /**
     * Called at mod initialization.
     */
    public static void addEventListeners() {
        //reduce event priority to support other mods that are overriding the speed
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, ModItems::onBreakSpeedEvent);
        NeoForge.EVENT_BUS.addListener(ModItems::onBreakEvent);
        NeoForge.EVENT_BUS.addListener(ModItems::onEntityJoinWorldEvent);
        NeoForge.EVENT_BUS.addListener(ModItems::onItemFishedEvent);
        NeoForge.EVENT_BUS.addListener(ModItems::onLivingDropsEvent);
        NeoForge.EVENT_BUS.addListener(ModItems::onLivingEquipmentChangeEvent);
        NeoForge.EVENT_BUS.addListener(ModItems::onLivingChangeTargetEvent);
        NeoForge.EVENT_BUS.addListener(ModItems::onLivingUseItemEventStart);
        NeoForge.EVENT_BUS.addListener(ModItems::onRightClickItemEvent);
        //curios events
        if (Services.PLATFORM.isModLoaded(Constants.CURIOS_MOD_ID)) {
            NeoForge.EVENT_BUS.addListener(ModItems::onCuriosEquipmentChangeEvent);
        }
    }

    /**
     * Called at mod initialization. (client side)
     */
    @OnlyIn(Dist.CLIENT)
    public static void setupClient() {
        //curio rendering
        if (Services.PLATFORM.isModLoaded(Constants.CURIOS_MOD_ID)) {
            for (DeferredHolder<Item, ?> item : ITEMS.getEntries()) {
                CuriosRendererRegistry.register(item.get(), CurioRenderer::getInstance);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(new ResourceLocation(Constants.MOD_ID, "aquanaut_helmet_overlay"), (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            if (Minecraft.useFancyGraphics()) {
                gui.setupOverlayRenderState(true, false);
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.options.getCameraType().isFirstPerson()) {
                    for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(mc.player)) {
                        for (DeferredHolder<Item, ?> itemRegistryObject : ITEMS.getEntries()) {
                            Item item = itemRegistryObject.get();
                            if (item instanceof IGameOverlayRenderer && item == headSlotItemStack.getItem()) {
                                ((IGameOverlayRenderer) item).onRenderGameOverlay(screenWidth, screenHeight, partialTicks);
                            }
                        }
                    }
                }
            }
        });
    }

    private static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        if (event.isCanceled()) {
            return;
        }
        for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(event.getEntity())) {
            for (DeferredHolder<Item, ?> itemRegistryObject : ITEMS.getEntries()) {
                Item item = itemRegistryObject.get();
                if (item instanceof IBreakSpeedChanger && headSlotItemStack.getItem() == item) {
                    Float newSpeed = ((IBreakSpeedChanger) item).onBreakSpeedEvent(event.getEntity(), event.getState(), event.getNewSpeed(), headSlotItemStack);
                    if (newSpeed != null) {
                        event.setNewSpeed(newSpeed);
                    }
                }
            }
        }
    }

    private static void onBreakEvent(BlockEvent.BreakEvent event) {
        if (event.isCanceled()) {
            return;
        }
        for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(event.getPlayer())) {
            for (DeferredHolder<Item, ?> itemRegistryObject : ITEMS.getEntries()) {
                Item item = itemRegistryObject.get();
                if (item instanceof IBreakSpeedChanger && headSlotItemStack.getItem() == item) {
                    ((IBreakSpeedChanger) item).onBreakEvent(event.getPlayer(), event.getState(), headSlotItemStack);
                }
            }
        }
    }

    private static void onEntityJoinWorldEvent(EntityJoinLevelEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (event.getEntity() instanceof Mob entity) {
            for (DeferredHolder<Item, ?> itemRegistryObject : ITEMS.getEntries()) {
                Item item = itemRegistryObject.get();
                if (item instanceof IMobEntityChanger) {
                    ((IMobEntityChanger) item).onEntityJoinWorldEvent(entity);
                }
            }
        }
    }

    private static void onItemFishedEvent(ItemFishedEvent event) {
        if (event.isCanceled()) {
            return;
        }
        for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(event.getEntity())) {
            for (DeferredHolder<Item, ?> itemRegistryObject : ITEMS.getEntries()) {
                Item item = itemRegistryObject.get();
                if (item instanceof IItemFishedListener && headSlotItemStack.getItem() == item) {
                    ((IItemFishedListener) item).onItemFishedListener(event.getEntity(), headSlotItemStack);
                }
            }
        }
    }

    private static void onLivingDropsEvent(LivingDropsEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (event.getSource().getDirectEntity() instanceof Player player) {
            for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(player)) {
                for (DeferredHolder<Item, ?> itemRegistryObject : ITEMS.getEntries()) {
                    Item item = itemRegistryObject.get();
                    if (item instanceof ILivingDropsListener && item == headSlotItemStack.getItem()) {
                        ((ILivingDropsListener) item).onLivingDropsEvent(player, headSlotItemStack);
                    }
                }
            }
        }
    }

    private static void onLivingUseItemEventStart(LivingEntityUseItemEvent event) {
        if (!(event instanceof LivingEntityUseItemEvent.Start)) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(player)) {
                for (DeferredHolder<Item, ?> itemRegistryObject : ITEMS.getEntries()) {
                    Item item = itemRegistryObject.get();
                    if (item instanceof IItemUseListener && item == headSlotItemStack.getItem()) {
                        Integer newDuration = ((IItemUseListener) item).onItemUseEventStart(player, event.getItem(), event.getDuration(), headSlotItemStack);
                        if (newDuration != null) {
                            event.setDuration(newDuration);
                        }
                    }
                }
            }
        }
    }

    private static void onEquipmentChanged(LivingEntity entity, ItemStack fromItemStack, ItemStack toItemStack) {
        Item fromItem = fromItemStack.getItem();
        Item toItem = toItemStack.getItem();
        if (fromItem != toItem && (fromItem instanceof IEquipmentChangeListener || toItem instanceof IEquipmentChangeListener)) {
            for (DeferredHolder<Item, ?> itemRegistryObject : ITEMS.getEntries()) {
                Item item = itemRegistryObject.get();
                if (item instanceof IEquipmentChangeListener) {
                    if (fromItem == item) {
                        ((IEquipmentChangeListener) item).onUnequippedHatItem(entity, fromItemStack);
                    } else if (toItem == item) {
                        ((IEquipmentChangeListener) item).onEquippedHatItem(entity, toItemStack);
                    }
                }
            }
        }
    }

    private static void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == EquipmentSlot.HEAD) {
            onEquipmentChanged(event.getEntity(), event.getFrom(), event.getTo());
        }
    }

    /**
     * equipment change event of curios mod
     */
    private static void onCuriosEquipmentChangeEvent(CurioChangeEvent event) {
        onEquipmentChanged(event.getEntity(), event.getFrom(), event.getTo());
    }

    private static void onLivingChangeTargetEvent(LivingChangeTargetEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (event.getEntity() instanceof Mob mob && event.getNewTarget() instanceof Player player) {
            for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(player)) {
                for (DeferredHolder<Item, ?> itemRegistryObject : ITEMS.getEntries()) {
                    Item item = itemRegistryObject.get();
                    if (item instanceof IAttackTargetChanger && item == headSlotItemStack.getItem()) {
                        if (((IAttackTargetChanger) item).avoidMobChangingTarget(headSlotItemStack, mob, player)) {
                            event.setCanceled(true);
                            return;
                        }
                    }
                }
            }
        }
    }

    private static void onRightClickItemEvent(PlayerInteractEvent.RightClickItem event) {
        if (event.isCanceled()) {
            return;
        }
        Player player = event.getEntity();
        for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(player)) {
            for (DeferredHolder<Item, ?> itemRegistryObject : ITEMS.getEntries()) {
                Item item = itemRegistryObject.get();
                if (item instanceof IRightClickListener && item == headSlotItemStack.getItem()) {
                    boolean cancelEvent = ((IRightClickListener) item).onRightClickItemEvent(event.getLevel(), event.getEntity(), event.getItemStack(), event.getHand(), headSlotItemStack);
                    if (cancelEvent) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

}
