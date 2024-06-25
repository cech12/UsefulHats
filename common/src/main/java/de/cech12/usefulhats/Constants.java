package de.cech12.usefulhats;

import de.cech12.usefulhats.item.EnderHelmetItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Class that contains all common constants.
 */
public class Constants {

    /** mod id */
    public static final String MOD_ID = "usefulhats";
    /** mod name*/
    public static final String MOD_NAME = "Useful Hats";
    /** Logger instance */
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final String CURIOS_MOD_ID = "curios";
    public static final String LUCENT_MOD_ID = "lucent";

    public static Supplier<DataComponentType<EnderHelmetItem.Position>> ENDER_HELMET_POSITION;

    private Constants() {}

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

}