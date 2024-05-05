package de.cech12.usefulhats.init;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.compat.CuriosMod;
import de.cech12.usefulhats.item.*;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid= Constants.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    public static final RegistryObject<Item> AQUANAUT_HELMET = ITEMS.register("aquanaut_helmet", AquanautHelmetItem::new);
    public static final RegistryObject<Item> BUNNY_EARS = ITEMS.register("bunny_ears", BunnyEarsItem::new);
    public static final RegistryObject<Item> CHOPPING_HAT = ITEMS.register("chopping_hat", ChoppingHatItem::new);
    public static final RegistryObject<Item> ENDER_HELMET = ITEMS.register("ender_helmet", EnderHelmetItem::new);
    public static final RegistryObject<Item> HALO = ITEMS.register("halo", HaloItem::new);
    public static final RegistryObject<Item> LUCKY_HAT = ITEMS.register("lucky_hat", LuckyHatItem::new);
    public static final RegistryObject<Item> MINING_HAT = ITEMS.register("mining_hat", MiningHatItem::new);
    public static final RegistryObject<Item> MUSHROOM_HAT = ITEMS.register("mushroom_hat", MushroomHatItem::new);
    public static final RegistryObject<Item> POSTMAN_HAT = ITEMS.register("postman_hat", PostmanHatItem::new);
    public static final RegistryObject<Item> SHULKER_HELMET = ITEMS.register("shulker_helmet", ShulkerHelmetItem::new);
    public static final RegistryObject<Item> STOCKING_CAP = ITEMS.register("stocking_cap", StockingCapItem::new);
    public static final RegistryObject<Item> STRAW_HAT = ITEMS.register("straw_hat", StrawHatItem::new);
    public static final RegistryObject<Item> WING_HELMET = ITEMS.register("wing_helmet", WingHelmetItem::new);

    @SuppressWarnings("unused")
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        ItemColors itemcolors = event.getItemColors();
        for (RegistryObject<Item> item : ITEMS.getEntries()) {
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
        MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, ModItems::onBreakSpeedEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onBreakEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onEntityJoinWorldEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onItemFishedEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingDropsEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingEquipmentChangeEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingChangeTargetEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingUseItemEventStart);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onRightClickItemEvent);
        //curios events
        if (Services.PLATFORM.isModLoaded(Constants.CURIOS_MOD_ID)) {
            MinecraftForge.EVENT_BUS.addListener(CuriosMod::onCuriosEquipmentChangeEvent);
        }
    }

    /**
     * Called at mod initialization. (client side)
     */
    @OnlyIn(Dist.CLIENT)
    public static void setupClient() {
        //curio rendering
        if (Services.PLATFORM.isModLoaded(Constants.CURIOS_MOD_ID)) {
            CuriosMod.setupClient();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("aquanaut_helmet_overlay", (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
            if (Minecraft.useFancyGraphics()) {
                gui.setupOverlayRenderState(true, false);
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.options.getCameraType().isFirstPerson()) {
                    for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(mc.player)) {
                        for (RegistryObject<Item> itemRegistryObject : ITEMS.getEntries()) {
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
            for (RegistryObject<Item> itemRegistryObject : ITEMS.getEntries()) {
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
            for (RegistryObject<Item> itemRegistryObject : ITEMS.getEntries()) {
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
            for (RegistryObject<Item> itemRegistryObject : ITEMS.getEntries()) {
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
            for (RegistryObject<Item> itemRegistryObject : ITEMS.getEntries()) {
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
                for (RegistryObject<Item> itemRegistryObject : ITEMS.getEntries()) {
                    Item item = itemRegistryObject.get();
                    if (item instanceof ILivingDropsListener && item == headSlotItemStack.getItem()) {
                        ((ILivingDropsListener) item).onLivingDropsEvent(player, headSlotItemStack);
                    }
                }
            }
        }
    }

    private static void onLivingUseItemEventStart(LivingEntityUseItemEvent event) {
        if (event.isCanceled() || !(event instanceof LivingEntityUseItemEvent.Start)) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(player)) {
                for (RegistryObject<Item> itemRegistryObject : ITEMS.getEntries()) {
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

    public static void onEquipmentChanged(LivingEntity entity, ItemStack fromItemStack, ItemStack toItemStack) {
        Item fromItem = fromItemStack.getItem();
        Item toItem = toItemStack.getItem();
        if (fromItem != toItem && (fromItem instanceof IEquipmentChangeListener || toItem instanceof IEquipmentChangeListener)) {
            for (RegistryObject<Item> itemRegistryObject : ITEMS.getEntries()) {
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
        if (event.isCanceled()) {
            return;
        }
        if (event.getSlot() == EquipmentSlot.HEAD) {
            onEquipmentChanged(event.getEntity(), event.getFrom(), event.getTo());
        }
    }

    private static void onLivingChangeTargetEvent(LivingChangeTargetEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (event.getEntity() instanceof Mob mob && event.getNewTarget() instanceof Player player) {
            for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(player)) {
                for (RegistryObject<Item> itemRegistryObject : ITEMS.getEntries()) {
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
            for (RegistryObject<Item> itemRegistryObject : ITEMS.getEntries()) {
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
