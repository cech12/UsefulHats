package de.cech12.usefulhats.client;

import de.cech12.usefulhats.item.IUsefulHatModelOwner;
import net.minecraft.world.item.ArmorItem;

import java.util.Calendar;

public class UsefulHatsClientUtils {

    private static final boolean IS_CHRISTMAS = Calendar.getInstance().get(Calendar.MONTH) + 1 == 12;

    public static String getArmorTexture(ArmorItem armorItem, String type) {
        String texture = armorItem.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        if (armorItem instanceof IUsefulHatModelOwner usefulHatModelOwner) {
            return String.format("%s:textures/models/usefulhats/%s%s%s.png", domain, texture,
                    (IS_CHRISTMAS && usefulHatModelOwner.hasChristmasVariant()) ? "_xmas" : "",
                    type == null ? "" : String.format("_%s", type));
        }
        return String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture,
                1, type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));
    }
}
