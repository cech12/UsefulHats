package de.cech12.usefulhats;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class UsefulHatsTags {

    public static class Items {
        public static final TagKey<Item> MUSHROOM_CAPS = tag(Services.PLATFORM.getModSharingNamespace() + ":mushroom_caps");
    }

    private static TagKey<Item> tag(final String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(name));
    }

}
