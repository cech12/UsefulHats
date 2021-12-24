package cech12.usefulhats.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public static ForgeConfigSpec COMMON_CONFIG;

    public static final ForgeConfigSpec.BooleanValue CURIOS_ADD_HEAD_SLOT;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Some configs in relation to other mods.").push("CompatibilityOptions");
        CURIOS_ADD_HEAD_SLOT = builder
                .comment("Whether or not a Curios head slot should be added if Curios is installed.")
                .define("curiosAddHeadSlot", false);
        builder.pop();

        COMMON_CONFIG = builder.build();
    }
}
