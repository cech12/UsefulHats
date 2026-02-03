package de.cech12.usefulhats;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class UsefulHatsTags {

    public static class Items {
        public static final TagKey<Item> REPAIRS_AQUANAUT_HELMET = tag("repairs_aquanaut_helmet");
        public static final TagKey<Item> REPAIRS_BUNNY_EARS = tag("repairs_bunny_ears");
        public static final TagKey<Item> REPAIRS_CHOPPING_HAT = tag("repairs_chopping_hat");
        public static final TagKey<Item> REPAIRS_ENDER_HELMET = tag("repairs_ender_helmet");
        public static final TagKey<Item> REPAIRS_HALO = tag("repairs_halo");
        public static final TagKey<Item> REPAIRS_LUCKY_HAT = tag("repairs_lucky_hat");
        public static final TagKey<Item> REPAIRS_MINING_HELMET = tag("repairs_mining_hat");
        public static final TagKey<Item> REPAIRS_MUSHROOM_HAT = tag("repairs_mushroom_hat");
        public static final TagKey<Item> REPAIRS_POSTMAN_HAT = tag("repairs_postman_hat");
        public static final TagKey<Item> REPAIRS_SHULKER_HELMET = tag("repairs_shulker_helmet");
        public static final TagKey<Item> REPAIRS_STOCKING_CAP = tag("repairs_stocking_cap");
        public static final TagKey<Item> REPAIRS_STRAW_HAT = tag("repairs_straw_hat");
        public static final TagKey<Item> REPAIRS_WING_HELMET = tag("repairs_wing_helmet");

        public static final TagKey<Item> MUSHROOM_CAPS = tag("c", "mushroom_caps");
    }

    private static TagKey<Item> tag(final String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
    }

    private static TagKey<Item> tag(final String namespace, final String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(namespace, name));
    }

}
