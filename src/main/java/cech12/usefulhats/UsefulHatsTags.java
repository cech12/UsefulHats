package cech12.usefulhats;

import net.minecraft.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class UsefulHatsTags {

    public static class Items {
        public static final Tag<Item> MUSHROOM_CAPS = tag("forge:mushroom_caps");
    }

    private static Tag<Item> tag(final String name) {
        return new ItemTags.Wrapper(new ResourceLocation(name));
    }

}
