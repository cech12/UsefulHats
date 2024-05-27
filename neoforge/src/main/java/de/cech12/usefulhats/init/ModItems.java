package de.cech12.usefulhats.init;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.UsefulHatsEventUtils;
import de.cech12.usefulhats.compat.CuriosMod;
import de.cech12.usefulhats.item.AquanautHelmetItem;
import de.cech12.usefulhats.item.BunnyEarsItem;
import de.cech12.usefulhats.item.ChoppingHatItem;
import de.cech12.usefulhats.item.EnderHelmetItem;
import de.cech12.usefulhats.item.HaloItem;
import de.cech12.usefulhats.item.LuckyHatItem;
import de.cech12.usefulhats.item.MiningHatItem;
import de.cech12.usefulhats.item.MushroomHatItem;
import de.cech12.usefulhats.item.PostmanHatItem;
import de.cech12.usefulhats.item.ShulkerHelmetItem;
import de.cech12.usefulhats.item.StockingCapItem;
import de.cech12.usefulhats.item.StrawHatItem;
import de.cech12.usefulhats.item.WingHelmetItem;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

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

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

    static {
        Constants.ENDER_HELMET_POSITION = DATA_COMPONENTS.register("ender_helmet_position", () -> createDataComponent((builder) -> builder.networkSynchronized(EnderHelmetItem.Position.STREAM_CODEC)));
    }

    private static <T> DataComponentType<T> createDataComponent(UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return (unaryOperator.apply(DataComponentType.builder())).build();
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
        NeoForge.EVENT_BUS.addListener(ModItems::onLivingJumpEvent);
        NeoForge.EVENT_BUS.addListener(ModItems::onLivingEquipmentChangeEvent);
        NeoForge.EVENT_BUS.addListener(ModItems::onLivingChangeTargetEvent);
        NeoForge.EVENT_BUS.addListener(ModItems::onLivingUseItemEventStart);
        NeoForge.EVENT_BUS.addListener(ModItems::onRightClickItemEvent);
        //curios events
        if (Services.PLATFORM.isModLoaded(Constants.CURIOS_MOD_ID)) {
            NeoForge.EVENT_BUS.addListener(CuriosMod::onCuriosEquipmentChangeEvent);
        }
    }

    private static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        if (!event.isCanceled()) {
            //use getNewSpeed() instead of getOriginalSpeed() to support other mods that are changing the break speed with this event.
            event.setNewSpeed(UsefulHatsEventUtils.onBreakSpeedCalculation(event.getEntity(), event.getState(), event.getNewSpeed()));
        }
    }

    private static void onBreakEvent(BlockEvent.BreakEvent event) {
        if (!event.isCanceled()) {
            UsefulHatsEventUtils.onBlockBreak(event.getPlayer(), event.getState());
        }
    }

    private static void onEntityJoinWorldEvent(EntityJoinLevelEvent event) {
        if (!event.isCanceled()) {
            UsefulHatsEventUtils.onEntityJoinWorld(event.getEntity());
        }
    }

    private static void onItemFishedEvent(ItemFishedEvent event) {
        if (!event.isCanceled()) {
            UsefulHatsEventUtils.onItemFished(event.getEntity());
        }
    }

    private static void onLivingDropsEvent(LivingDropsEvent event) {
        if (!event.isCanceled()) {
            UsefulHatsEventUtils.onLivingDiesBecauseOf(event.getSource().getEntity());
        }
    }

    private static void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        UsefulHatsEventUtils.onLivingJump(event.getEntity());
    }

    private static void onLivingUseItemEventStart(LivingEntityUseItemEvent event) {
        if (event instanceof LivingEntityUseItemEvent.Start) {
            event.setDuration(UsefulHatsEventUtils.onLivingStartsUsingItem(event.getEntity(), event.getItem(), event.getDuration()));
        }
    }

    private static void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == EquipmentSlot.HEAD) {
            UsefulHatsEventUtils.onUnequip(event.getEntity(), event.getFrom());
            UsefulHatsEventUtils.onEquip(event.getEntity(), event.getTo());
        }
    }

    private static void onLivingChangeTargetEvent(LivingChangeTargetEvent event) {
        if (!event.isCanceled() && UsefulHatsEventUtils.shouldEntityAvoidChangingTarget(event.getEntity(), event.getNewTarget())) {
            event.setCanceled(true);
        }
    }

    private static void onRightClickItemEvent(PlayerInteractEvent.RightClickItem event) {
        if (!event.isCanceled() && UsefulHatsEventUtils.shouldRightClickBeCancelled(event.getLevel(), event.getEntity(), event.getItemStack(), event.getHand())) {
            event.setCanceled(true);
        }
    }

}
