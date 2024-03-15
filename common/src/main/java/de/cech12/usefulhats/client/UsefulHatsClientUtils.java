package de.cech12.usefulhats.client;

import de.cech12.usefulhats.item.IUsefulHatModelOwner;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Calendar;

public class UsefulHatsClientUtils {

    private static final boolean IS_CHRISTMAS = Calendar.getInstance().get(Calendar.MONTH) + 1 == 12;

    public static String getArmorTexture(ItemStack stack, String type) {
        if (stack.getItem() instanceof IUsefulHatModelOwner) {
            ResourceLocation resourceLocation = Services.REGISTRY.getItemResourceLocation(stack.getItem());
            if (resourceLocation != null) {
                String texture = resourceLocation.getPath();
                String domain = resourceLocation.getNamespace();
                return String.format("%s:textures/models/usefulhats/%s%s%s.png", domain, texture,
                        (IS_CHRISTMAS && ((IUsefulHatModelOwner) stack.getItem()).hasChristmasVariant()) ? "_xmas" : "",
                        type == null ? "" : String.format("_%s", type));
            }
        }
        return null;
    }
}
