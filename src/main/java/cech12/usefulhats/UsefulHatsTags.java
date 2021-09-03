package cech12.usefulhats;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class UsefulHatsTags {

    public static class Items {
        public static final ITag.INamedTag<Item> MUSHROOM_CAPS = tag("forge:mushroom_caps");
    }

    private static ITag.INamedTag<Item> tag(final String name) {
        return ItemTags.bind(name);
    }

}
