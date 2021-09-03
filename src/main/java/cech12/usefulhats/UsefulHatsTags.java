package cech12.usefulhats;

import net.minecraft.world.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;

public class UsefulHatsTags {

    public static class Items {
        public static final Tag.Named<Item> MUSHROOM_CAPS = tag("forge:mushroom_caps");
    }

    private static Tag.Named<Item> tag(final String name) {
        return ItemTags.bind(name);
    }

}
