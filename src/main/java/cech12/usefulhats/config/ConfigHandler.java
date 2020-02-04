package cech12.usefulhats.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {
    public static ForgeConfigSpec COMMON;

    public static List<IResettableConfigType> allValues = new ArrayList<>();

    public static final ConfigType.Boolean AQUANAUT_HELMET_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean AQUANAUT_HELMET_DAMAGE_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean CHOPPING_HAT_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean CHOPPING_HAT_DAMAGE_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean HALO_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean HALO_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer HALO_DETECTING_RANGE = new ConfigType.Integer(16);

    public static final ConfigType.Boolean LUCKY_HAT_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean LUCKY_HAT_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean LUCKY_HAT_UNLUCK_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean MINING_HAT_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean MINING_HAT_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean MINING_HAT_NIGHT_VISION_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean POSTMAN_HAT_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean POSTMAN_HAT_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean POSTMAN_HAT_HUNGER_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean STOCKING_CAP_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean STOCKING_CAP_DAMAGE_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean STRAW_HAT_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean STRAW_HAT_DAMAGE_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean WING_HELMET_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean WING_HELMET_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean WING_HELMET_LEVITATION_ENABLED = new ConfigType.Boolean(true);

    static {
        final ForgeConfigSpec.Builder common = new ForgeConfigSpec.Builder();

        common.comment("Enable or disable certain hats. Disabled hats will not be craftable.").push("Accesibility Config");

        AQUANAUT_HELMET_ENABLED.configObj = common
                .comment("Whether or not Aquanaut Helmet should be enabled.")
                .define("aquanautHelmetEnabled", AQUANAUT_HELMET_ENABLED.getDefaultValue());
        CHOPPING_HAT_ENABLED.configObj = common
                .comment("Whether or not Chopping Hat should be enabled.")
                .define("choppingHatEnabled", CHOPPING_HAT_ENABLED.getDefaultValue());
        HALO_ENABLED.configObj = common
                .comment("Whether or not Halo should be enabled.")
                .define("haloEnabled", HALO_ENABLED.getDefaultValue());
        LUCKY_HAT_ENABLED.configObj = common
                .comment("Whether or not Lucky Hat should be enabled.")
                .define("luckyHatEnabled", LUCKY_HAT_ENABLED.getDefaultValue());
        MINING_HAT_ENABLED.configObj = common
                .comment("Whether or not Mining Hat should be enabled.")
                .define("miningHatEnabled", MINING_HAT_ENABLED.getDefaultValue());
        POSTMAN_HAT_ENABLED.configObj = common
                .comment("Whether or not Postman Hat should be enabled.")
                .define("postmanHatEnabled", POSTMAN_HAT_ENABLED.getDefaultValue());
        STOCKING_CAP_ENABLED.configObj = common
                .comment("Whether or not Stocking Cap should be enabled.")
                .define("stockingCapEnabled", STOCKING_CAP_ENABLED.getDefaultValue());
        STRAW_HAT_ENABLED.configObj = common
                .comment("Whether or not Straw Hat should be enabled.")
                .define("strawHatEnabled", STRAW_HAT_ENABLED.getDefaultValue());
        WING_HELMET_ENABLED.configObj = common
                .comment("Whether or not Wing Helmet should be enabled.")
                .define("wingHelmetEnabled", WING_HELMET_ENABLED.getDefaultValue());

        common.pop();

        common.comment("Various options that affect individual hats.").push("Balance Options");

        AQUANAUT_HELMET_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Aquanaut Helmet should be enabled.")
                .define("aquanautHelmetDamageEnabled", AQUANAUT_HELMET_DAMAGE_ENABLED.getDefaultValue());
        CHOPPING_HAT_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Chopping Hat should be enabled.")
                .define("choppingHatDamageEnabled", CHOPPING_HAT_DAMAGE_ENABLED.getDefaultValue());
        HALO_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Halo should be enabled.")
                .define("haloDamageEnabled", HALO_DAMAGE_ENABLED.getDefaultValue());
        HALO_DETECTING_RANGE.configObj = common
                .comment("Detecting range of Halo for damage calculation. (in blocks)")
                .defineInRange("haloDetectingRange", HALO_DETECTING_RANGE.getDefaultValue(), 1, 100);
        LUCKY_HAT_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Lucky Hat should be enabled.")
                .define("luckyHatDamageEnabled", LUCKY_HAT_DAMAGE_ENABLED.getDefaultValue());
        LUCKY_HAT_UNLUCK_ENABLED.configObj = common
                .comment("Whether or not Hunger effect of Lucky Hat should be enabled while running.")
                .define("luckyHatUnluckEnabled", LUCKY_HAT_UNLUCK_ENABLED.getDefaultValue());
        MINING_HAT_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Mining Hat should be enabled.")
                .define("miningHatDamageEnabled", MINING_HAT_DAMAGE_ENABLED.getDefaultValue());
        MINING_HAT_NIGHT_VISION_ENABLED.configObj = common
                .comment("Whether or not Night Vision effect of Mining Hat should be enabled in dark areas.")
                .define("miningHatNightVisionEnabled", MINING_HAT_NIGHT_VISION_ENABLED.getDefaultValue());
        POSTMAN_HAT_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Postman Hat should be enabled.")
                .define("postmanHatDamageEnabled", POSTMAN_HAT_DAMAGE_ENABLED.getDefaultValue());
        POSTMAN_HAT_HUNGER_ENABLED.configObj = common
                .comment("Whether or not Hunger effect of Postman Hat should be enabled while running.")
                .define("postmanHatHungerEnabled", POSTMAN_HAT_HUNGER_ENABLED.getDefaultValue());
        STOCKING_CAP_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Stocking Cap should be enabled.")
                .define("stockingCapDamageEnabled", STOCKING_CAP_DAMAGE_ENABLED.getDefaultValue());
        STRAW_HAT_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Straw Hat should be enabled.")
                .define("strawHatDamageEnabled", STRAW_HAT_DAMAGE_ENABLED.getDefaultValue());
        WING_HELMET_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Wing Helmet should be enabled.")
                .define("wingHelmetDamageEnabled", WING_HELMET_DAMAGE_ENABLED.getDefaultValue());
        WING_HELMET_LEVITATION_ENABLED.configObj = common
                .comment("Whether or not Levitation effect of Wing Helmet should be enabled.")
                .define("wingHelmetLevitationEnabled", WING_HELMET_LEVITATION_ENABLED.getDefaultValue());

        common.pop();

        COMMON = common.build();
    }

    public static void resetConfig() {
        for (IResettableConfigType par : allValues){
            par.reset();
        }
    }
}
