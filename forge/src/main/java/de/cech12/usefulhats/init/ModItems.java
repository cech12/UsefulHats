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
import net.minecraftforge.eventbus.api.listener.Priority;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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
        Constants.ENDER_HELMET_POSITION = DATA_COMPONENTS.register("ender_helmet_position", () -> createDataComponent((builder) -> builder.persistent(EnderHelmetItem.Position.CODEC).networkSynchronized(EnderHelmetItem.Position.STREAM_CODEC)));
    }

    private static RegistryObject<Item> register(String name, Function<String, Item> itemFactory) {
        return ITEMS.register(name, () -> itemFactory.apply(name));
    }

    private static <T> DataComponentType<T> createDataComponent(UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return (unaryOperator.apply(DataComponentType.builder())).build();
    }

    @SubscribeEvent(priority = Priority.LOWEST) //reduce event priority to support other mods that are overriding the speed
    public static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        //use getNewSpeed() instead of getOriginalSpeed() to support other mods that are changing the break speed with this event.
        event.setNewSpeed(UsefulHatsEventUtils.onBreakSpeedCalculation(event.getEntity(), event.getState(), event.getNewSpeed()));
    }

    @SubscribeEvent
    public static void onBreakEvent(BlockEvent.BreakEvent event) {
        UsefulHatsEventUtils.onBlockBreak(event.getPlayer(), event.getState());
    }

    @SubscribeEvent
    public static void onEntityJoinWorldEvent(EntityJoinLevelEvent event) {
        UsefulHatsEventUtils.onEntityJoinWorld(event.getEntity());
    }

    @SubscribeEvent
    public static void onItemFishedEvent(ItemFishedEvent event) {
        UsefulHatsEventUtils.onItemFished(event.getEntity());
    }

    @SubscribeEvent
    public static void onLivingDropsEvent(LivingDropsEvent event) {
        UsefulHatsEventUtils.onLivingDiesBecauseOf(event.getSource().getDirectEntity());
    }

    @SubscribeEvent
    public static void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        UsefulHatsEventUtils.onLivingJump(event.getEntity());
    }

    @SubscribeEvent
    public static void onLivingUseItemEventStart(LivingEntityUseItemEvent event) {
        if (event instanceof LivingEntityUseItemEvent.Start) {
            event.setDuration(UsefulHatsEventUtils.onLivingStartsUsingItem(event.getEntity(), event.getItem(), event.getDuration()));
        }
    }

    @SubscribeEvent
    public static void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == EquipmentSlot.HEAD) {
            UsefulHatsEventUtils.onUnequip(event.getEntity(), event.getFrom());
            UsefulHatsEventUtils.onEquip(event.getEntity(), event.getTo());
        }
    }

    @SubscribeEvent
    public static boolean onLivingChangeTargetEvent(LivingChangeTargetEvent event) {
        return UsefulHatsEventUtils.shouldEntityAvoidChangingTarget(event.getEntity(), event.getNewTarget());
    }

    @SubscribeEvent
    public static boolean onRightClickItemEvent(PlayerInteractEvent.RightClickItem event) {
        return UsefulHatsEventUtils.shouldRightClickBeCancelled(event.getLevel(), event.getEntity(), event.getItemStack(), event.getHand());
    }

}
