package de.cech12.usefulhats.init;

import de.cech12.usefulhats.CommonLoader;
import de.cech12.usefulhats.Constants;
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
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModItems {

    public static final List<Item> ALL_HATS = new ArrayList<>();

    public static final Item AQUANAUT_HELMET = register("aquanaut_helmet", AquanautHelmetItem::new);
    public static final Item BUNNY_EARS = register("bunny_ears", BunnyEarsItem::new);
    public static final Item CHOPPING_HAT = register("chopping_hat", ChoppingHatItem::new);
    public static final Item ENDER_HELMET = register("ender_helmet", EnderHelmetItem::new);
    public static final Item HALO = register("halo", HaloItem::new);
    public static final Item LUCKY_HAT = register("lucky_hat", LuckyHatItem::new);
    public static final Item MINING_HAT = register("mining_hat", MiningHatItem::new);
    public static final Item MUSHROOM_HAT = register("mushroom_hat", MushroomHatItem::new);
    public static final Item POSTMAN_HAT = register("postman_hat", PostmanHatItem::new);
    public static final Item SHULKER_HELMET = register("shulker_helmet", ShulkerHelmetItem::new);
    public static final Item STOCKING_CAP = register("stocking_cap", StockingCapItem::new);
    public static final Item STRAW_HAT = register("straw_hat", StrawHatItem::new);
    public static final Item WING_HELMET = register("wing_helmet", WingHelmetItem::new);

    public static final DataComponentType<EnderHelmetItem.Position> ENDER_HELMET_POSITION = register("ender_helmet_position", (builder) -> builder.networkSynchronized(EnderHelmetItem.Position.STREAM_CODEC));

    static {
        Constants.ENDER_HELMET_POSITION = () -> ENDER_HELMET_POSITION;
    }

    public static void init() {}

    private static Item register(String name, Supplier<Item> item) {
        Item hat = Registry.register(BuiltInRegistries.ITEM, CommonLoader.id(name), item.get());
        ALL_HATS.add(hat);
        return hat;
    }

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, name, (unaryOperator.apply(DataComponentType.builder())).build());
    }

}
