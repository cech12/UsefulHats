package cech12.usefulhats.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static ForgeConfigSpec COMMON;

    public static List<IResettableConfigType> allValues = new ArrayList<>();

    public static final ConfigType.Boolean BAUBLES_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean CURIOS_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean CURIOS_ADD_HEAD_SLOT = new ConfigType.Boolean(false);

    public static final ConfigType.Boolean AQUANAUT_HELMET_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean AQUANAUT_HELMET_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer AQUANAUT_HELMET_DURABILITY = new ConfigType.Integer(600);
    public static final ConfigType.Integer AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0 = new ConfigType.Integer(60);
    public static final ConfigType.Integer AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1 = new ConfigType.Integer(120);
    public static final ConfigType.Integer AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2 = new ConfigType.Integer(180);
    public static final ConfigType.Integer AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3 = new ConfigType.Integer(240);

    public static final ConfigType.Boolean BUNNY_EARS_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean BUNNY_EARS_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer BUNNY_EARS_DURABILITY = new ConfigType.Integer(600);

    public static final ConfigType.Boolean CHOPPING_HAT_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean CHOPPING_HAT_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer CHOPPING_HAT_DURABILITY = new ConfigType.Integer(300);
    public static final ConfigType.Double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0 = new ConfigType.Double(0.2);
    public static final ConfigType.Double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1 = new ConfigType.Double(0.4);
    public static final ConfigType.Double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2 = new ConfigType.Double(0.6);
    public static final ConfigType.Double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3 = new ConfigType.Double(0.8);
    public static final ConfigType.Double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4 = new ConfigType.Double(1.0);
    public static final ConfigType.Double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5 = new ConfigType.Double(1.5);

    public static final ConfigType.Boolean HALO_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean HALO_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer HALO_DURABILITY = new ConfigType.Integer(600);
    public static final ConfigType.Integer HALO_DETECTING_RANGE = new ConfigType.Integer(16);

    public static final ConfigType.Boolean LUCKY_HAT_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean LUCKY_HAT_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer LUCKY_HAT_DURABILITY = new ConfigType.Integer(300);
    public static final ConfigType.Boolean LUCKY_HAT_UNLUCK_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean MINING_HAT_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean MINING_HAT_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer MINING_HAT_DURABILITY = new ConfigType.Integer(450);
    public static final ConfigType.Boolean MINING_HAT_NIGHT_VISION_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Double MINING_HAT_SPEED_WITH_EFFICIENCY_0 = new ConfigType.Double(0.2);
    public static final ConfigType.Double MINING_HAT_SPEED_WITH_EFFICIENCY_1 = new ConfigType.Double(0.4);
    public static final ConfigType.Double MINING_HAT_SPEED_WITH_EFFICIENCY_2 = new ConfigType.Double(0.6);
    public static final ConfigType.Double MINING_HAT_SPEED_WITH_EFFICIENCY_3 = new ConfigType.Double(0.8);
    public static final ConfigType.Double MINING_HAT_SPEED_WITH_EFFICIENCY_4 = new ConfigType.Double(1.0);
    public static final ConfigType.Double MINING_HAT_SPEED_WITH_EFFICIENCY_5 = new ConfigType.Double(1.5);

    public static final ConfigType.Boolean MUSHROOM_HAT_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean MUSHROOM_HAT_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer MUSHROOM_HAT_DURABILITY = new ConfigType.Integer(80);
    public static final ConfigType.Integer MUSHROOM_HAT_EAT_INTERVAL = new ConfigType.Integer(60);

    public static final ConfigType.Boolean POSTMAN_HAT_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean POSTMAN_HAT_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer POSTMAN_HAT_DURABILITY = new ConfigType.Integer(600);
    public static final ConfigType.Boolean POSTMAN_HAT_HUNGER_ENABLED = new ConfigType.Boolean(true);

    public static final ConfigType.Boolean SHULKER_HELMET_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean SHULKER_HELMET_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer SHULKER_HELMET_DURABILITY = new ConfigType.Integer(600);

    public static final ConfigType.Boolean STOCKING_CAP_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean STOCKING_CAP_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer STOCKING_CAP_DURABILITY = new ConfigType.Integer(600);

    public static final ConfigType.Boolean STRAW_HAT_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean STRAW_HAT_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer STRAW_HAT_DURABILITY = new ConfigType.Integer(300);
    public static final ConfigType.Double STRAW_HAT_SPEED_WITH_EFFICIENCY_0 = new ConfigType.Double(0.2);
    public static final ConfigType.Double STRAW_HAT_SPEED_WITH_EFFICIENCY_1 = new ConfigType.Double(0.4);
    public static final ConfigType.Double STRAW_HAT_SPEED_WITH_EFFICIENCY_2 = new ConfigType.Double(0.6);
    public static final ConfigType.Double STRAW_HAT_SPEED_WITH_EFFICIENCY_3 = new ConfigType.Double(0.8);
    public static final ConfigType.Double STRAW_HAT_SPEED_WITH_EFFICIENCY_4 = new ConfigType.Double(1.0);
    public static final ConfigType.Double STRAW_HAT_SPEED_WITH_EFFICIENCY_5 = new ConfigType.Double(1.5);

    public static final ConfigType.Boolean WING_HELMET_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Boolean WING_HELMET_DAMAGE_ENABLED = new ConfigType.Boolean(true);
    public static final ConfigType.Integer WING_HELMET_DURABILITY = new ConfigType.Integer(600);
    public static final ConfigType.Boolean WING_HELMET_LEVITATION_ENABLED = new ConfigType.Boolean(true);

    static {
        final ForgeConfigSpec.Builder common = new ForgeConfigSpec.Builder();

        common.comment("Some configs in relation to other mods.").push("Compatibility option");

        BAUBLES_ENABLED.configObj = common
                .comment("Whether or not hats from this mod should be placeable in Baubles head slot if Baubles is installed.")
                .define("baublesEnabled", BAUBLES_ENABLED.getDefaultValue());
        CURIOS_ENABLED.configObj = common
                .comment("Whether or not hats from this mod should be placeable in Curios head slots if Curios is installed.")
                .define("curiosEnabled", CURIOS_ENABLED.getDefaultValue());
        CURIOS_ADD_HEAD_SLOT.configObj = common
                .comment("Whether or not a Curios head slot should be added if Curios is installed.")
                .define("curiosAddHeadSlot", CURIOS_ADD_HEAD_SLOT.getDefaultValue());

        common.pop();

        common.comment("Enable or disable certain hats. Disabled hats will not be craftable.").push("Accessibility Config");

        AQUANAUT_HELMET_ENABLED.configObj = common
                .comment("Whether or not Aquanaut Helmet should be enabled.")
                .define("aquanautHelmetEnabled", AQUANAUT_HELMET_ENABLED.getDefaultValue());
        BUNNY_EARS_ENABLED.configObj = common
                .comment("Whether or not Bunny Ears should be enabled.")
                .define("bunnyEarsEnabled", BUNNY_EARS_ENABLED.getDefaultValue());
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
        MUSHROOM_HAT_ENABLED.configObj = common
                .comment("Whether or not Mushroom Hat should be enabled.")
                .define("mushroomHatEnabled", MUSHROOM_HAT_ENABLED.getDefaultValue());
        POSTMAN_HAT_ENABLED.configObj = common
                .comment("Whether or not Postman Hat should be enabled.")
                .define("postmanHatEnabled", POSTMAN_HAT_ENABLED.getDefaultValue());
        SHULKER_HELMET_ENABLED.configObj = common
                .comment("Whether or not Shulker Helmet should be enabled.")
                .define("shulkerHelmetEnabled", SHULKER_HELMET_ENABLED.getDefaultValue());
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
        common.push("Aquanaut Helmet");
        AQUANAUT_HELMET_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Aquanaut Helmet should be enabled.")
                .define("aquanautHelmetDamageEnabled", AQUANAUT_HELMET_DAMAGE_ENABLED.getDefaultValue());
        AQUANAUT_HELMET_DURABILITY.configObj = common
                .comment("Aquanaut Helmet durability.")
                .defineInRange("aquanautHelmetDurability", AQUANAUT_HELMET_DURABILITY.getDefaultValue(), 1, 10000);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0.configObj = common
                .comment("Aquanaut Helmet effect time of Conduit Power without Respiration enchantment. (in seconds)")
                .defineInRange("aquanautHelmetEffectTimeWithRespiration0", AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0.getDefaultValue(), 10, 120);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1.configObj = common
                .comment("Aquanaut Helmet effect time of Conduit Power with Respiration I. (in seconds)")
                .defineInRange("aquanautHelmetEffectTimeWithRespiration1", AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1.getDefaultValue(), 10, 180);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2.configObj = common
                .comment("Aquanaut Helmet effect time of Conduit Power with Respiration II. (in seconds)")
                .defineInRange("aquanautHelmetEffectTimeWithRespiration2", AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2.getDefaultValue(), 10, 240);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3.configObj = common
                .comment("Aquanaut Helmet effect time of Conduit Power with Respiration III. (in seconds)")
                .defineInRange("aquanautHelmetEffectTimeWithRespiration3", AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3.getDefaultValue(), 10, 300);
        common.pop();

        common.push("Bunny Ears");
        HALO_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Bunny Ears should be enabled.")
                .define("bunnyEarsDamageEnabled", HALO_DAMAGE_ENABLED.getDefaultValue());
        HALO_DURABILITY.configObj = common
                .comment("Bunny Ears durability.")
                .defineInRange("bunnyEarsDurability", HALO_DURABILITY.getDefaultValue(), 1, 10000);
        common.pop();

        common.push("Chopping Hat");
        CHOPPING_HAT_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Chopping Hat should be enabled.")
                .define("choppingHatDamageEnabled", CHOPPING_HAT_DAMAGE_ENABLED.getDefaultValue());
        CHOPPING_HAT_DURABILITY.configObj = common
                .comment("Chopping Hat durability.")
                .defineInRange("choppingHatDurability", CHOPPING_HAT_DURABILITY.getDefaultValue(), 1, 10000);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0.configObj = common
                .comment("Chopping Hat speed increase without Efficiency enchantment.")
                .defineInRange("choppingHatSpeedWithEfficiency0", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0.getDefaultValue(), 0.0, 1.0);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1.configObj = common
                .comment("Chopping Hat speed increase with Efficiency I.")
                .defineInRange("choppingHatSpeedWithEfficiency1", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1.getDefaultValue(), 0.0, 1.0);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2.configObj = common
                .comment("Chopping Hat speed increase with Efficiency II.")
                .defineInRange("choppingHatSpeedWithEfficiency2", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2.getDefaultValue(), 0.0, 2.0);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3.configObj = common
                .comment("Chopping Hat speed increase with Efficiency III.")
                .defineInRange("choppingHatSpeedWithEfficiency3", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3.getDefaultValue(), 0.0, 3.0);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4.configObj = common
                .comment("Chopping Hat speed increase with Efficiency IV.")
                .defineInRange("choppingHatSpeedWithEfficiency4", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4.getDefaultValue(), 0.0, 4.0);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5.configObj = common
                .comment("Chopping Hat speed increase with Efficiency V.")
                .defineInRange("choppingHatSpeedWithEfficiency5", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5.getDefaultValue(), 0.0, 5.0);
        common.pop();

        common.push("Halo");
        HALO_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Halo should be enabled.")
                .define("haloDamageEnabled", HALO_DAMAGE_ENABLED.getDefaultValue());
        HALO_DURABILITY.configObj = common
                .comment("Halo durability.")
                .defineInRange("haloDurability", HALO_DURABILITY.getDefaultValue(), 1, 10000);
        HALO_DETECTING_RANGE.configObj = common
                .comment("Detecting range of Halo for damage calculation. (in blocks)")
                .defineInRange("haloDetectingRange", HALO_DETECTING_RANGE.getDefaultValue(), 1, 100);
        common.pop();

        common.push("Lucky Hat");
        LUCKY_HAT_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Lucky Hat should be enabled.")
                .define("luckyHatDamageEnabled", LUCKY_HAT_DAMAGE_ENABLED.getDefaultValue());
        LUCKY_HAT_DURABILITY.configObj = common
                .comment("Lucky Hat durability.")
                .defineInRange("luckyHatDurability", LUCKY_HAT_DURABILITY.getDefaultValue(), 1, 10000);
        LUCKY_HAT_UNLUCK_ENABLED.configObj = common
                .comment("Whether or not Hunger effect of Lucky Hat should be enabled while running.")
                .define("luckyHatUnluckEnabled", LUCKY_HAT_UNLUCK_ENABLED.getDefaultValue());
        common.pop();

        common.push("Mining Hat");
        MINING_HAT_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Mining Hat should be enabled.")
                .define("miningHatDamageEnabled", MINING_HAT_DAMAGE_ENABLED.getDefaultValue());
        MINING_HAT_DURABILITY.configObj = common
                .comment("Mining Hat durability.")
                .defineInRange("miningHatDurability", MINING_HAT_DURABILITY.getDefaultValue(), 1, 10000);
        MINING_HAT_NIGHT_VISION_ENABLED.configObj = common
                .comment("Whether or not Night Vision effect of Mining Hat should be enabled in dark areas.")
                .define("miningHatNightVisionEnabled", MINING_HAT_NIGHT_VISION_ENABLED.getDefaultValue());
        MINING_HAT_SPEED_WITH_EFFICIENCY_0.configObj = common
                .comment("Mining Hat speed increase without Efficiency enchantment.")
                .defineInRange("miningHatSpeedWithEfficiency0", MINING_HAT_SPEED_WITH_EFFICIENCY_0.getDefaultValue(), 0.0, 1.0);
        MINING_HAT_SPEED_WITH_EFFICIENCY_1.configObj = common
                .comment("Mining Hat speed increase with Efficiency I.")
                .defineInRange("miningHatSpeedWithEfficiency1", MINING_HAT_SPEED_WITH_EFFICIENCY_1.getDefaultValue(), 0.0, 1.0);
        MINING_HAT_SPEED_WITH_EFFICIENCY_2.configObj = common
                .comment("Mining Hat speed increase with Efficiency II.")
                .defineInRange("miningHatSpeedWithEfficiency2", MINING_HAT_SPEED_WITH_EFFICIENCY_2.getDefaultValue(), 0.0, 2.0);
        MINING_HAT_SPEED_WITH_EFFICIENCY_3.configObj = common
                .comment("Mining Hat speed increase with Efficiency III.")
                .defineInRange("miningHatSpeedWithEfficiency3", MINING_HAT_SPEED_WITH_EFFICIENCY_3.getDefaultValue(), 0.0, 3.0);
        MINING_HAT_SPEED_WITH_EFFICIENCY_4.configObj = common
                .comment("Mining Hat speed increase with Efficiency IV.")
                .defineInRange("miningHatSpeedWithEfficiency4", MINING_HAT_SPEED_WITH_EFFICIENCY_4.getDefaultValue(), 0.0, 4.0);
        MINING_HAT_SPEED_WITH_EFFICIENCY_5.configObj = common
                .comment("Mining Hat speed increase with Efficiency V.")
                .defineInRange("miningHatSpeedWithEfficiency5", MINING_HAT_SPEED_WITH_EFFICIENCY_5.getDefaultValue(), 0.0, 5.0);
        common.pop();

        common.push("Mushroom Hat");
        MUSHROOM_HAT_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Mushroom Hat should be enabled.")
                .define("mushroomHatDamageEnabled", LUCKY_HAT_DAMAGE_ENABLED.getDefaultValue());
        MUSHROOM_HAT_DURABILITY.configObj = common
                .comment("Mushroom Hat durability.")
                .defineInRange("mushroomHatDurability", MUSHROOM_HAT_DURABILITY.getDefaultValue(), 1, 10000);
        MUSHROOM_HAT_EAT_INTERVAL.configObj = common
                .comment("Interval the Mushroom Hat is eaten by the player (in ticks). default: 60 (3 seconds)")
                .defineInRange("mushroomHatEatInterval", MUSHROOM_HAT_EAT_INTERVAL.getDefaultValue(), 1, 10000);
        common.pop();

        common.push("Postman Hat");
        POSTMAN_HAT_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Postman Hat should be enabled.")
                .define("postmanHatDamageEnabled", POSTMAN_HAT_DAMAGE_ENABLED.getDefaultValue());
        POSTMAN_HAT_DURABILITY.configObj = common
                .comment("Postman Hat durability.")
                .defineInRange("postmanHatDurability", POSTMAN_HAT_DURABILITY.getDefaultValue(), 1, 10000);
        POSTMAN_HAT_HUNGER_ENABLED.configObj = common
                .comment("Whether or not Hunger effect of Postman Hat should be enabled while running.")
                .define("postmanHatHungerEnabled", POSTMAN_HAT_HUNGER_ENABLED.getDefaultValue());
        common.pop();

        common.push("Shulker Helmet");
        SHULKER_HELMET_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Shulker Helmet should be enabled.")
                .define("shulkerHelmetDamageEnabled", SHULKER_HELMET_DAMAGE_ENABLED.getDefaultValue());
        SHULKER_HELMET_DURABILITY.configObj = common
                .comment("Shulker Helmet durability.")
                .defineInRange("shulkerHelmetDurability", SHULKER_HELMET_DURABILITY.getDefaultValue(), 1, 10000);
        common.pop();

        common.push("Stocking Cap");
        STOCKING_CAP_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Stocking Cap should be enabled.")
                .define("stockingCapDamageEnabled", STOCKING_CAP_DAMAGE_ENABLED.getDefaultValue());
        STOCKING_CAP_DURABILITY.configObj = common
                .comment("Stocking Cap durability.")
                .defineInRange("stockingCapDurability", STOCKING_CAP_DURABILITY.getDefaultValue(), 1, 10000);
        common.pop();

        common.push("Straw Hat");
        STRAW_HAT_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Straw Hat should be enabled.")
                .define("strawHatDamageEnabled", STRAW_HAT_DAMAGE_ENABLED.getDefaultValue());
        STRAW_HAT_DURABILITY.configObj = common
                .comment("Straw Hat durability.")
                .defineInRange("strawHatDurability", STRAW_HAT_DURABILITY.getDefaultValue(), 1, 10000);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_0.configObj = common
                .comment("Straw Hat speed increase without Efficiency enchantment.")
                .defineInRange("strawHatSpeedWithEfficiency0", STRAW_HAT_SPEED_WITH_EFFICIENCY_0.getDefaultValue(), 0.0, 1.0);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_1.configObj = common
                .comment("Straw Hat speed increase with Efficiency I.")
                .defineInRange("strawHatSpeedWithEfficiency1", STRAW_HAT_SPEED_WITH_EFFICIENCY_1.getDefaultValue(), 0.0, 1.0);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_2.configObj = common
                .comment("Straw Hat speed increase with Efficiency II.")
                .defineInRange("strawHatSpeedWithEfficiency2", STRAW_HAT_SPEED_WITH_EFFICIENCY_2.getDefaultValue(), 0.0, 2.0);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_3.configObj = common
                .comment("Straw Hat speed increase with Efficiency III.")
                .defineInRange("strawHatSpeedWithEfficiency3", STRAW_HAT_SPEED_WITH_EFFICIENCY_3.getDefaultValue(), 0.0, 3.0);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_4.configObj = common
                .comment("Straw Hat speed increase with Efficiency IV.")
                .defineInRange("strawHatSpeedWithEfficiency4", STRAW_HAT_SPEED_WITH_EFFICIENCY_4.getDefaultValue(), 0.0, 4.0);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_5.configObj = common
                .comment("Straw Hat speed increase with Efficiency V.")
                .defineInRange("strawHatSpeedWithEfficiency5", STRAW_HAT_SPEED_WITH_EFFICIENCY_5.getDefaultValue(), 0.0, 5.0);
        common.pop();

        common.push("Wing Helmet");
        WING_HELMET_DAMAGE_ENABLED.configObj = common
                .comment("Whether or not damaging of Wing Helmet should be enabled.")
                .define("wingHelmetDamageEnabled", WING_HELMET_DAMAGE_ENABLED.getDefaultValue());
        WING_HELMET_DURABILITY.configObj = common
                .comment("Wing Helmet durability.")
                .defineInRange("wingHelmetDurability", WING_HELMET_DURABILITY.getDefaultValue(), 1, 10000);
        WING_HELMET_LEVITATION_ENABLED.configObj = common
                .comment("Whether or not Levitation effect of Wing Helmet should be enabled.")
                .define("wingHelmetLevitationEnabled", WING_HELMET_LEVITATION_ENABLED.getDefaultValue());
        common.pop();

        common.pop();

        COMMON = common.build();
    }

    public static void resetConfig() {
        for (IResettableConfigType par : allValues){
            par.reset();
        }
    }
}
