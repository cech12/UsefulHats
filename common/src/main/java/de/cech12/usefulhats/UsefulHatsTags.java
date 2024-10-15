package de.cech12.usefulhats;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class UsefulHatsTags {

    public static class Items {
        public static final TagKey<Item> MUSHROOM_CAPS = tag("c", "mushroom_caps");
    }

    private static TagKey<Item> tag(final String namespace, final String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, name));
    }

}
