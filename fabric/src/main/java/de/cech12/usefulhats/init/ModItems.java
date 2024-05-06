package de.cech12.usefulhats.init;

import de.cech12.usefulhats.CommonLoader;
import de.cech12.usefulhats.item.*;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

    public static void init() {}

    private static Item register(String name, Supplier<Item> item) {
        Item hat = Registry.register(BuiltInRegistries.ITEM, CommonLoader.id(name), item.get());
        ALL_HATS.add(hat);
        return hat;
    }

}
