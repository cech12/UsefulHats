package cech12.usefulhats.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ServerConfig {
    public static ForgeConfigSpec SERVER_CONFIG;

    public static final ForgeConfigSpec.BooleanValue CURIOS_ENABLED;

    public static final ForgeConfigSpec.BooleanValue AQUANAUT_HELMET_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue AQUANAUT_HELMET_DURABILITY;
    public static final ForgeConfigSpec.IntValue AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0;
    public static final ForgeConfigSpec.IntValue AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1;
    public static final ForgeConfigSpec.IntValue AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2;
    public static final ForgeConfigSpec.IntValue AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3;

    public static final ForgeConfigSpec.BooleanValue BUNNY_EARS_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue BUNNY_EARS_DURABILITY;

    public static final ForgeConfigSpec.BooleanValue CHOPPING_HAT_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue CHOPPING_HAT_DURABILITY;
    public static final ForgeConfigSpec.DoubleValue CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0;
    public static final ForgeConfigSpec.DoubleValue CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1;
    public static final ForgeConfigSpec.DoubleValue CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2;
    public static final ForgeConfigSpec.DoubleValue CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3;
    public static final ForgeConfigSpec.DoubleValue CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4;
    public static final ForgeConfigSpec.DoubleValue CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5;

    public static final ForgeConfigSpec.BooleanValue ENDER_HELMET_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue ENDER_HELMET_DURABILITY;
    public static final ForgeConfigSpec.BooleanValue ENDER_HELMET_INTERDIMENSIONAL_ENABLED;

    public static final ForgeConfigSpec.BooleanValue HALO_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue HALO_DURABILITY;

    public static final ForgeConfigSpec.BooleanValue LUCKY_HAT_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue LUCKY_HAT_DURABILITY;
    public static final ForgeConfigSpec.BooleanValue LUCKY_HAT_UNLUCK_ENABLED;

    public static final ForgeConfigSpec.BooleanValue MINING_HAT_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue MINING_HAT_DURABILITY;
    public static final ForgeConfigSpec.BooleanValue MINING_HAT_NIGHT_VISION_ENABLED;
    public static final ForgeConfigSpec.BooleanValue MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED;
    public static final ForgeConfigSpec.DoubleValue MINING_HAT_SPEED_WITH_EFFICIENCY_0;
    public static final ForgeConfigSpec.DoubleValue MINING_HAT_SPEED_WITH_EFFICIENCY_1;
    public static final ForgeConfigSpec.DoubleValue MINING_HAT_SPEED_WITH_EFFICIENCY_2;
    public static final ForgeConfigSpec.DoubleValue MINING_HAT_SPEED_WITH_EFFICIENCY_3;
    public static final ForgeConfigSpec.DoubleValue MINING_HAT_SPEED_WITH_EFFICIENCY_4;
    public static final ForgeConfigSpec.DoubleValue MINING_HAT_SPEED_WITH_EFFICIENCY_5;

    public static final ForgeConfigSpec.BooleanValue MUSHROOM_HAT_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue MUSHROOM_HAT_DURABILITY;
    public static final ForgeConfigSpec.IntValue MUSHROOM_HAT_EAT_INTERVAL;

    public static final ForgeConfigSpec.BooleanValue POSTMAN_HAT_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue POSTMAN_HAT_DURABILITY;
    public static final ForgeConfigSpec.BooleanValue POSTMAN_HAT_HUNGER_ENABLED;

    public static final ForgeConfigSpec.BooleanValue SHULKER_HELMET_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue SHULKER_HELMET_DURABILITY;

    public static final ForgeConfigSpec.BooleanValue STOCKING_CAP_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue STOCKING_CAP_DURABILITY;

    public static final ForgeConfigSpec.BooleanValue STRAW_HAT_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue STRAW_HAT_DURABILITY;
    public static final ForgeConfigSpec.DoubleValue STRAW_HAT_SPEED_WITH_EFFICIENCY_0;
    public static final ForgeConfigSpec.DoubleValue STRAW_HAT_SPEED_WITH_EFFICIENCY_1;
    public static final ForgeConfigSpec.DoubleValue STRAW_HAT_SPEED_WITH_EFFICIENCY_2;
    public static final ForgeConfigSpec.DoubleValue STRAW_HAT_SPEED_WITH_EFFICIENCY_3;
    public static final ForgeConfigSpec.DoubleValue STRAW_HAT_SPEED_WITH_EFFICIENCY_4;
    public static final ForgeConfigSpec.DoubleValue STRAW_HAT_SPEED_WITH_EFFICIENCY_5;

    public static final ForgeConfigSpec.BooleanValue WING_HELMET_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue WING_HELMET_DURABILITY;
    public static final ForgeConfigSpec.BooleanValue WING_HELMET_LEVITATION_ENABLED;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Some configs in relation to other mods.").push("CompatibilityOptions");
        CURIOS_ENABLED = builder
                .comment("Whether or not hats from this mod should be placeable in Curios head slots if Curios is installed.")
                .define("curiosEnabled", true);
        builder.pop();

        builder.comment("Various options that affect individual hats.").push("BalanceOptions");

        builder.push("AquanautHelmet");
        AQUANAUT_HELMET_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Aquanaut Helmet should be enabled.")
                .define("aquanautHelmetDamageEnabled", true);
        AQUANAUT_HELMET_DURABILITY = builder
                .comment("Aquanaut Helmet durability.")
                .defineInRange("aquanautHelmetDurability", 600, 1, 10000);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0 = builder
                .comment("Aquanaut Helmet effect time of Conduit Power without Respiration enchantment. (in seconds)")
                .defineInRange("aquanautHelmetEffectTimeWithRespiration0", 60, 10, 120);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1 = builder
                .comment("Aquanaut Helmet effect time of Conduit Power with Respiration I. (in seconds)")
                .defineInRange("aquanautHelmetEffectTimeWithRespiration1", 120, 10, 180);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2 = builder
                .comment("Aquanaut Helmet effect time of Conduit Power with Respiration II. (in seconds)")
                .defineInRange("aquanautHelmetEffectTimeWithRespiration2", 180, 10, 240);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3 = builder
                .comment("Aquanaut Helmet effect time of Conduit Power with Respiration III. (in seconds)")
                .defineInRange("aquanautHelmetEffectTimeWithRespiration3", 240, 10, 300);
        builder.pop();

        builder.push("BunnyEars");
        BUNNY_EARS_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Bunny Ears should be enabled.")
                .define("bunnyEarsDamageEnabled", true);
        BUNNY_EARS_DURABILITY = builder
                .comment("Bunny Ears durability.")
                .defineInRange("bunnyEarsDurability", 600, 1, 10000);
        builder.pop();

        builder.push("ChoppingHat");
        CHOPPING_HAT_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Chopping Hat should be enabled.")
                .define("choppingHatDamageEnabled", true);
        CHOPPING_HAT_DURABILITY = builder
                .comment("Chopping Hat durability.")
                .defineInRange("choppingHatDurability", 300, 1, 10000);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0 = builder
                .comment("Chopping Hat speed increase without Efficiency enchantment.")
                .defineInRange("choppingHatSpeedWithEfficiency0", 0.2, 0.0, 5.0);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1 = builder
                .comment("Chopping Hat speed increase with Efficiency I.")
                .defineInRange("choppingHatSpeedWithEfficiency1", 0.4, 0.0, 5.0);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2 = builder
                .comment("Chopping Hat speed increase with Efficiency II.")
                .defineInRange("choppingHatSpeedWithEfficiency2", 0.6, 0.0, 5.0);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3 = builder
                .comment("Chopping Hat speed increase with Efficiency III.")
                .defineInRange("choppingHatSpeedWithEfficiency3", 0.8, 0.0, 5.0);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4 = builder
                .comment("Chopping Hat speed increase with Efficiency IV.")
                .defineInRange("choppingHatSpeedWithEfficiency4", 1.0, 0.0, 5.0);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5 = builder
                .comment("Chopping Hat speed increase with Efficiency V.")
                .defineInRange("choppingHatSpeedWithEfficiency5", 1.5, 0.0, 5.0);
        builder.pop();

        builder.push("EnderHelmet");
        ENDER_HELMET_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Ender Helmet should be enabled.")
                .define("enderHelmetDamageEnabled", true);
        ENDER_HELMET_DURABILITY = builder
                .comment("Ender Helmet durability.")
                .defineInRange("enderHelmetDurability", 80, 1, 10000);
        ENDER_HELMET_INTERDIMENSIONAL_ENABLED = builder
                .comment("Whether or not interdimensional teleporting with the Ender Helmet should be enabled.")
                .define("enderHelmetInterdimensionalEnabled", true);
        builder.pop();

        builder.push("Halo");
        HALO_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Halo should be enabled.")
                .define("haloDamageEnabled", true);
        HALO_DURABILITY = builder
                .comment("Halo durability.")
                .defineInRange("haloDurability", 600, 1, 10000);
        builder.pop();

        builder.push("LuckyHat");
        LUCKY_HAT_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Lucky Hat should be enabled.")
                .define("luckyHatDamageEnabled", true);
        LUCKY_HAT_DURABILITY = builder
                .comment("Lucky Hat durability.")
                .defineInRange("luckyHatDurability", 300, 1, 10000);
        LUCKY_HAT_UNLUCK_ENABLED = builder
                .comment("Whether or not Hunger effect of Lucky Hat should be enabled while running.")
                .define("luckyHatUnluckEnabled", true);
        builder.pop();

        builder.push("MiningHat");
        MINING_HAT_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Mining Hat should be enabled.")
                .define("miningHatDamageEnabled", true);
        MINING_HAT_DURABILITY = builder
                .comment("Mining Hat durability.")
                .defineInRange("miningHatDurability", 450, 1, 10000);
        MINING_HAT_NIGHT_VISION_ENABLED = builder
                .comment("Whether or not Night Vision effect of Mining Hat should be enabled in dark areas.")
                .define("miningHatNightVisionEnabled", true);
        MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED = builder
                .comment("Whether or not wearing a Mining Hat should make Piglins neutral.")
                .define("miningHatMakePiglinsNeutralEnabled", true);
        MINING_HAT_SPEED_WITH_EFFICIENCY_0 = builder
                .comment("Mining Hat speed increase without Efficiency enchantment.")
                .defineInRange("miningHatSpeedWithEfficiency0", 0.2, 0.0, 5.0);
        MINING_HAT_SPEED_WITH_EFFICIENCY_1 = builder
                .comment("Mining Hat speed increase with Efficiency I.")
                .defineInRange("miningHatSpeedWithEfficiency1", 0.4, 0.0, 5.0);
        MINING_HAT_SPEED_WITH_EFFICIENCY_2 = builder
                .comment("Mining Hat speed increase with Efficiency II.")
                .defineInRange("miningHatSpeedWithEfficiency2", 0.6, 0.0, 5.0);
        MINING_HAT_SPEED_WITH_EFFICIENCY_3 = builder
                .comment("Mining Hat speed increase with Efficiency III.")
                .defineInRange("miningHatSpeedWithEfficiency3", 0.8, 0.0, 5.0);
        MINING_HAT_SPEED_WITH_EFFICIENCY_4 = builder
                .comment("Mining Hat speed increase with Efficiency IV.")
                .defineInRange("miningHatSpeedWithEfficiency4", 1.0, 0.0, 5.0);
        MINING_HAT_SPEED_WITH_EFFICIENCY_5 = builder
                .comment("Mining Hat speed increase with Efficiency V.")
                .defineInRange("miningHatSpeedWithEfficiency5", 1.5, 0.0, 5.0);
        builder.pop();

        builder.push("MushroomHat");
        MUSHROOM_HAT_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Mushroom Hat should be enabled.")
                .define("mushroomHatDamageEnabled", true);
        MUSHROOM_HAT_DURABILITY = builder
                .comment("Mushroom Hat durability.")
                .defineInRange("mushroomHatDurability", 80, 1, 10000);
        MUSHROOM_HAT_EAT_INTERVAL = builder
                .comment("Interval the Mushroom Hat is eaten by the player (in ticks). default: 60 (3 seconds)")
                .defineInRange("mushroomHatEatInterval", 60, 1, 10000);
        builder.pop();

        builder.push("PostmanHat");
        POSTMAN_HAT_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Postman Hat should be enabled.")
                .define("postmanHatDamageEnabled", true);
        POSTMAN_HAT_DURABILITY = builder
                .comment("Postman Hat durability.")
                .defineInRange("postmanHatDurability", 600, 1, 10000);
        POSTMAN_HAT_HUNGER_ENABLED = builder
                .comment("Whether or not Hunger effect of Postman Hat should be enabled while running.")
                .define("postmanHatHungerEnabled", true);
        builder.pop();

        builder.push("ShulkerHelmet");
        SHULKER_HELMET_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Shulker Helmet should be enabled.")
                .define("shulkerHelmetDamageEnabled", true);
        SHULKER_HELMET_DURABILITY = builder
                .comment("Shulker Helmet durability.")
                .defineInRange("shulkerHelmetDurability", 600, 1, 10000);
        builder.pop();

        builder.push("StockingCap");
        STOCKING_CAP_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Stocking Cap should be enabled.")
                .define("stockingCapDamageEnabled", true);
        STOCKING_CAP_DURABILITY = builder
                .comment("Stocking Cap durability.")
                .defineInRange("stockingCapDurability", 600, 1, 10000);
        builder.pop();

        builder.push("StrawHat");
        STRAW_HAT_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Straw Hat should be enabled.")
                .define("strawHatDamageEnabled", true);
        STRAW_HAT_DURABILITY = builder
                .comment("Straw Hat durability.")
                .defineInRange("strawHatDurability", 300, 1, 10000);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_0 = builder
                .comment("Straw Hat speed increase without Efficiency enchantment.")
                .defineInRange("strawHatSpeedWithEfficiency0", 0.2, 0.0, 5.0);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_1 = builder
                .comment("Straw Hat speed increase with Efficiency I.")
                .defineInRange("strawHatSpeedWithEfficiency1", 0.4, 0.0, 5.0);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_2 = builder
                .comment("Straw Hat speed increase with Efficiency II.")
                .defineInRange("strawHatSpeedWithEfficiency2", 0.6, 0.0, 5.0);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_3 = builder
                .comment("Straw Hat speed increase with Efficiency III.")
                .defineInRange("strawHatSpeedWithEfficiency3", 0.8, 0.0, 5.0);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_4 = builder
                .comment("Straw Hat speed increase with Efficiency IV.")
                .defineInRange("strawHatSpeedWithEfficiency4", 1.0, 0.0, 5.0);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_5 = builder
                .comment("Straw Hat speed increase with Efficiency V.")
                .defineInRange("strawHatSpeedWithEfficiency5", 1.5, 0.0, 5.0);
        builder.pop();

        builder.push("WingHelmet");
        WING_HELMET_DAMAGE_ENABLED = builder
                .comment("Whether or not damaging of Wing Helmet should be enabled.")
                .define("wingHelmetDamageEnabled", true);
        WING_HELMET_DURABILITY = builder
                .comment("Wing Helmet durability.")
                .defineInRange("wingHelmetDurability", 600, 1, 10000);
        WING_HELMET_LEVITATION_ENABLED = builder
                .comment("Whether or not Levitation effect of Wing Helmet should be enabled.")
                .define("wingHelmetLevitationEnabled", true);
        builder.pop();

        builder.pop();

        SERVER_CONFIG = builder.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        spec.setConfig(configData);
    }
}
