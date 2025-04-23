package de.cech12.usefulhats.init;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.UsefulHatsEventUtils;
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
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    public static final RegistryObject<Item> AQUANAUT_HELMET = register("aquanaut_helmet", AquanautHelmetItem::new);
    public static final RegistryObject<Item> BUNNY_EARS = register("bunny_ears", BunnyEarsItem::new);
    public static final RegistryObject<Item> CHOPPING_HAT = register("chopping_hat", ChoppingHatItem::new);
    public static final RegistryObject<Item> ENDER_HELMET = register("ender_helmet", EnderHelmetItem::new);
    public static final RegistryObject<Item> HALO = register("halo", HaloItem::new);
    public static final RegistryObject<Item> LUCKY_HAT = register("lucky_hat", LuckyHatItem::new);
    public static final RegistryObject<Item> MINING_HAT = register("mining_hat", MiningHatItem::new);
    public static final RegistryObject<Item> MUSHROOM_HAT = register("mushroom_hat", MushroomHatItem::new);
    public static final RegistryObject<Item> POSTMAN_HAT = register("postman_hat", PostmanHatItem::new);
    public static final RegistryObject<Item> SHULKER_HELMET = register("shulker_helmet", ShulkerHelmetItem::new);
    public static final RegistryObject<Item> STOCKING_CAP = register("stocking_cap", StockingCapItem::new);
    public static final RegistryObject<Item> STRAW_HAT = register("straw_hat", StrawHatItem::new);
    public static final RegistryObject<Item> WING_HELMET = register("wing_helmet", WingHelmetItem::new);

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

    static {
        Constants.ENDER_HELMET_POSITION = DATA_COMPONENTS.register("ender_helmet_position", () -> createDataComponent((builder) -> builder.networkSynchronized(EnderHelmetItem.Position.STREAM_CODEC)));
    }

    private static RegistryObject<Item> register(String name, Function<String, Item> itemFactory) {
        return ITEMS.register(name, () -> itemFactory.apply(name));
    }

    private static <T> DataComponentType<T> createDataComponent(UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return (unaryOperator.apply(DataComponentType.builder())).build();
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
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingJumpEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingEquipmentChangeEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingChangeTargetEvent);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onLivingUseItemEventStart);
        MinecraftForge.EVENT_BUS.addListener(ModItems::onRightClickItemEvent);
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
            UsefulHatsEventUtils.onLivingDiesBecauseOf(event.getSource().getDirectEntity());
        }
    }

    private static void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        if (!event.isCanceled()) {
            UsefulHatsEventUtils.onLivingJump(event.getEntity());
        }
    }

    private static void onLivingUseItemEventStart(LivingEntityUseItemEvent event) {
        if (!event.isCanceled() && event instanceof LivingEntityUseItemEvent.Start) {
            event.setDuration(UsefulHatsEventUtils.onLivingStartsUsingItem(event.getEntity(), event.getItem(), event.getDuration()));
        }
    }

    private static void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
        if (!event.isCanceled() && event.getSlot() == EquipmentSlot.HEAD) {
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
