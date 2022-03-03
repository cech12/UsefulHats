package cech12.usefulhats;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class UsefulHatsTags {

    public static class Items {
        public static final TagKey<Item> MUSHROOM_CAPS = tag("forge:mushroom_caps");
    }

    private static TagKey<Item> tag(final String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(name));
    }

}
