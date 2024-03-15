package de.cech12.usefulhats.platform;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.platform.services.IConfigHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

/**
 * The config service implementation for Forge.
 */
public class ForgeConfigHelper implements IConfigHelper {

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec SERVER_CONFIG;

    public static final ForgeConfigSpec.BooleanValue CURIOS_ADD_HEAD_SLOT;

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
        final ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();

        commonBuilder.comment("Some configs in relation to other mods.").push("CompatibilityOptions");
        CURIOS_ADD_HEAD_SLOT = commonBuilder
                .comment(CURIOS_ADD_HEAD_SLOT_DESCRIPTION)
                .define("curiosAddHeadSlot", CURIOS_ADD_HEAD_SLOT_DEFAULT);
        commonBuilder.pop();

        COMMON_CONFIG = commonBuilder.build();

        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Various options that affect individual hats.").push("BalanceOptions");

        builder.push("AquanautHelmet");
        AQUANAUT_HELMET_DAMAGE_ENABLED = builder
                .comment(AQUANAUT_HELMET_DAMAGE_ENABLED_DESCRIPTION)
                .define("aquanautHelmetDamageEnabled", AQUANAUT_HELMET_DAMAGE_ENABLED_DEFAULT);
        AQUANAUT_HELMET_DURABILITY = builder
                .comment(AQUANAUT_HELMET_DURABILITY_DESCRIPTION)
                .defineInRange("aquanautHelmetDurability", AQUANAUT_HELMET_DURABILITY_DEFAULT, AQUANAUT_HELMET_DURABILITY_MIN, AQUANAUT_HELMET_DURABILITY_MAX);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0 = builder
                .comment(AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0_DESCRIPTION)
                .defineInRange("aquanautHelmetEffectTimeWithRespiration0", AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0_DEFAULT, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0_MIN, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0_MAX);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1 = builder
                .comment(AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1_DESCRIPTION)
                .defineInRange("aquanautHelmetEffectTimeWithRespiration1", AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1_DEFAULT, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1_MIN, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1_MAX);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2 = builder
                .comment(AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2_DESCRIPTION)
                .defineInRange("aquanautHelmetEffectTimeWithRespiration2", AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2_DEFAULT, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2_MIN, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2_MAX);
        AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3 = builder
                .comment(AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3_DESCRIPTION)
                .defineInRange("aquanautHelmetEffectTimeWithRespiration3", AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3_DEFAULT, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3_MIN, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3_MAX);
        builder.pop();

        builder.push("BunnyEars");
        BUNNY_EARS_DAMAGE_ENABLED = builder
                .comment(BUNNY_EARS_DAMAGE_ENABLED_DESCRIPTION)
                .define("bunnyEarsDamageEnabled", BUNNY_EARS_DAMAGE_ENABLED_DEFAULT);
        BUNNY_EARS_DURABILITY = builder
                .comment(BUNNY_EARS_DURABILITY_DESCRIPTION)
                .defineInRange("bunnyEarsDurability", BUNNY_EARS_DURABILITY_DEFAULT, BUNNY_EARS_DURABILITY_MIN, BUNNY_EARS_DURABILITY_MAX);
        builder.pop();

        builder.push("ChoppingHat");
        CHOPPING_HAT_DAMAGE_ENABLED = builder
                .comment(CHOPPING_HAT_DAMAGE_ENABLED_DESCRIPTION)
                .define("choppingHatDamageEnabled", CHOPPING_HAT_DAMAGE_ENABLED_DEFAULT);
        CHOPPING_HAT_DURABILITY = builder
                .comment(CHOPPING_HAT_DURABILITY_DESCRIPTION)
                .defineInRange("choppingHatDurability", CHOPPING_HAT_DURABILITY_DEFAULT, CHOPPING_HAT_DURABILITY_MIN, CHOPPING_HAT_DURABILITY_MAX);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0 = builder
                .comment(CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_DESCRIPTION)
                .defineInRange("choppingHatSpeedWithEfficiency0", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_MAX);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1 = builder
                .comment(CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_DESCRIPTION)
                .defineInRange("choppingHatSpeedWithEfficiency1", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_MAX);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2 = builder
                .comment(CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_DESCRIPTION)
                .defineInRange("choppingHatSpeedWithEfficiency2", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_MAX);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3 = builder
                .comment(CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_DESCRIPTION)
                .defineInRange("choppingHatSpeedWithEfficiency3", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_MAX);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4 = builder
                .comment(CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_DESCRIPTION)
                .defineInRange("choppingHatSpeedWithEfficiency4", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_MAX);
        CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5 = builder
                .comment(CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_DESCRIPTION)
                .defineInRange("choppingHatSpeedWithEfficiency5", CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_MAX);
        builder.pop();

        builder.push("EnderHelmet");
        ENDER_HELMET_DAMAGE_ENABLED = builder
                .comment(ENDER_HELMET_DAMAGE_ENABLED_DESCRIPTION)
                .define("enderHelmetDamageEnabled", ENDER_HELMET_DAMAGE_ENABLED_DEFAULT);
        ENDER_HELMET_DURABILITY = builder
                .comment(ENDER_HELMET_DURABILITY_DESCRIPTION)
                .defineInRange("enderHelmetDurability", ENDER_HELMET_DURABILITY_DEFAULT, ENDER_HELMET_DURABILITY_MIN, ENDER_HELMET_DURABILITY_MAX);
        ENDER_HELMET_INTERDIMENSIONAL_ENABLED = builder
                .comment(ENDER_HELMET_INTERDIMENSIONAL_ENABLED_DESCRIPTION)
                .define("enderHelmetInterdimensionalEnabled", ENDER_HELMET_INTERDIMENSIONAL_ENABLED_DEFAULT);
        builder.pop();

        builder.push("Halo");
        HALO_DAMAGE_ENABLED = builder
                .comment(HALO_DAMAGE_ENABLED_DESCRIPTION)
                .define("haloDamageEnabled", HALO_DAMAGE_ENABLED_DEFAULT);
        HALO_DURABILITY = builder
                .comment(HALO_DURABILITY_DESCRIPTION)
                .defineInRange("haloDurability", HALO_DURABILITY_DEFAULT, HALO_DURABILITY_MIN, HALO_DURABILITY_MAX);
        builder.pop();

        builder.push("LuckyHat");
        LUCKY_HAT_DAMAGE_ENABLED = builder
                .comment(LUCKY_HAT_DAMAGE_ENABLED_DESCRIPTION)
                .define("luckyHatDamageEnabled", LUCKY_HAT_DAMAGE_ENABLED_DEFAULT);
        LUCKY_HAT_DURABILITY = builder
                .comment(LUCKY_HAT_DURABILITY_DESCRIPTION)
                .defineInRange("luckyHatDurability", LUCKY_HAT_DURABILITY_DEFAULT, LUCKY_HAT_DURABILITY_MIN, LUCKY_HAT_DURABILITY_MAX);
        LUCKY_HAT_UNLUCK_ENABLED = builder
                .comment(LUCKY_HAT_UNLUCK_ENABLED_DESCRIPTION)
                .define("luckyHatUnluckEnabled", LUCKY_HAT_UNLUCK_ENABLED_DEFAULT);
        builder.pop();

        builder.push("MiningHat");
        MINING_HAT_DAMAGE_ENABLED = builder
                .comment(MINING_HAT_DAMAGE_ENABLED_DESCRIPTION)
                .define("miningHatDamageEnabled", MINING_HAT_DAMAGE_ENABLED_DEFAULT);
        MINING_HAT_DURABILITY = builder
                .comment(MINING_HAT_DURABILITY_DESCRIPTION)
                .defineInRange("miningHatDurability", MINING_HAT_DURABILITY_DEFAULT, MINING_HAT_DURABILITY_MIN, MINING_HAT_DURABILITY_MAX);
        MINING_HAT_NIGHT_VISION_ENABLED = builder
                .comment(MINING_HAT_NIGHT_VISION_ENABLED_DESCRIPTION)
                .define("miningHatNightVisionEnabled", MINING_HAT_NIGHT_VISION_ENABLED_DEFAULT);
        MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED = builder
                .comment(MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED_DESCRIPTION)
                .define("miningHatMakePiglinsNeutralEnabled", MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED_DEFAULT);
        MINING_HAT_SPEED_WITH_EFFICIENCY_0 = builder
                .comment(MINING_HAT_SPEED_WITH_EFFICIENCY_0_DESCRIPTION)
                .defineInRange("miningHatSpeedWithEfficiency0", MINING_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT, MINING_HAT_SPEED_WITH_EFFICIENCY_0_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_0_MAX);
        MINING_HAT_SPEED_WITH_EFFICIENCY_1 = builder
                .comment(MINING_HAT_SPEED_WITH_EFFICIENCY_1_DESCRIPTION)
                .defineInRange("miningHatSpeedWithEfficiency1", MINING_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT, MINING_HAT_SPEED_WITH_EFFICIENCY_1_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_1_MAX);
        MINING_HAT_SPEED_WITH_EFFICIENCY_2 = builder
                .comment(MINING_HAT_SPEED_WITH_EFFICIENCY_2_DESCRIPTION)
                .defineInRange("miningHatSpeedWithEfficiency2", MINING_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT, MINING_HAT_SPEED_WITH_EFFICIENCY_2_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_2_MAX);
        MINING_HAT_SPEED_WITH_EFFICIENCY_3 = builder
                .comment(MINING_HAT_SPEED_WITH_EFFICIENCY_3_DESCRIPTION)
                .defineInRange("miningHatSpeedWithEfficiency3", MINING_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT, MINING_HAT_SPEED_WITH_EFFICIENCY_3_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_3_MAX);
        MINING_HAT_SPEED_WITH_EFFICIENCY_4 = builder
                .comment(MINING_HAT_SPEED_WITH_EFFICIENCY_4_DESCRIPTION)
                .defineInRange("miningHatSpeedWithEfficiency4", MINING_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT, MINING_HAT_SPEED_WITH_EFFICIENCY_4_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_4_MAX);
        MINING_HAT_SPEED_WITH_EFFICIENCY_5 = builder
                .comment(MINING_HAT_SPEED_WITH_EFFICIENCY_5_DESCRIPTION)
                .defineInRange("miningHatSpeedWithEfficiency5", MINING_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT, MINING_HAT_SPEED_WITH_EFFICIENCY_5_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_5_MAX);
        builder.pop();

        builder.push("MushroomHat");
        MUSHROOM_HAT_DAMAGE_ENABLED = builder
                .comment(MUSHROOM_HAT_DAMAGE_ENABLED_DESCRIPTION)
                .define("mushroomHatDamageEnabled", MUSHROOM_HAT_DAMAGE_ENABLED_DEFAULT);
        MUSHROOM_HAT_DURABILITY = builder
                .comment(MUSHROOM_HAT_DURABILITY_DESCRIPTION)
                .defineInRange("mushroomHatDurability", MUSHROOM_HAT_DURABILITY_DEFAULT, MUSHROOM_HAT_DURABILITY_MIN, MUSHROOM_HAT_DURABILITY_MAX);
        MUSHROOM_HAT_EAT_INTERVAL = builder
                .comment(MUSHROOM_HAT_EAT_INTERVAL_DESCRIPTION)
                .defineInRange("mushroomHatEatInterval", MUSHROOM_HAT_EAT_INTERVAL_DEFAULT, MUSHROOM_HAT_EAT_INTERVAL_MIN, MUSHROOM_HAT_EAT_INTERVAL_MAX);
        builder.pop();

        builder.push("PostmanHat");
        POSTMAN_HAT_DAMAGE_ENABLED = builder
                .comment(POSTMAN_HAT_DAMAGE_ENABLED_DESCRIPTION)
                .define("postmanHatDamageEnabled", POSTMAN_HAT_DAMAGE_ENABLED_DEFAULT);
        POSTMAN_HAT_DURABILITY = builder
                .comment(POSTMAN_HAT_DURABILITY_DESCRIPTION)
                .defineInRange("postmanHatDurability", POSTMAN_HAT_DURABILITY_DEFAULT, POSTMAN_HAT_DURABILITY_MIN, POSTMAN_HAT_DURABILITY_MAX);
        POSTMAN_HAT_HUNGER_ENABLED = builder
                .comment(POSTMAN_HAT_HUNGER_ENABLED_DESCRIPTION)
                .define("postmanHatHungerEnabled", POSTMAN_HAT_HUNGER_ENABLED_DEFAULT);
        builder.pop();

        builder.push("ShulkerHelmet");
        SHULKER_HELMET_DAMAGE_ENABLED = builder
                .comment(SHULKER_HELMET_DAMAGE_ENABLED_DESCRIPTION)
                .define("shulkerHelmetDamageEnabled", SHULKER_HELMET_DAMAGE_ENABLED_DEFAULT);
        SHULKER_HELMET_DURABILITY = builder
                .comment(SHULKER_HELMET_DURABILITY_DESCRIPTION)
                .defineInRange("shulkerHelmetDurability", SHULKER_HELMET_DURABILITY_DEFAULT, SHULKER_HELMET_DURABILITY_MIN, SHULKER_HELMET_DURABILITY_MAX);
        builder.pop();

        builder.push("StockingCap");
        STOCKING_CAP_DAMAGE_ENABLED = builder
                .comment(STOCKING_CAP_DAMAGE_ENABLED_DESCRIPTION)
                .define("stockingCapDamageEnabled", STOCKING_CAP_DAMAGE_ENABLED_DEFAULT);
        STOCKING_CAP_DURABILITY = builder
                .comment(STOCKING_CAP_DURABILITY_DESCRIPTION)
                .defineInRange("stockingCapDurability", STOCKING_CAP_DURABILITY_DEFAULT, STOCKING_CAP_DURABILITY_MIN, STOCKING_CAP_DURABILITY_MAX);
        builder.pop();

        builder.push("StrawHat");
        STRAW_HAT_DAMAGE_ENABLED = builder
                .comment(STRAW_HAT_DAMAGE_ENABLED_DESCRIPTION)
                .define("strawHatDamageEnabled", STRAW_HAT_DAMAGE_ENABLED_DEFAULT);
        STRAW_HAT_DURABILITY = builder
                .comment(STRAW_HAT_DURABILITY_DESCRIPTION)
                .defineInRange("strawHatDurability", STOCKING_CAP_DURABILITY_DEFAULT, STRAW_HAT_DURABILITY_MIN, STRAW_HAT_DURABILITY_MAX);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_0 = builder
                .comment(STRAW_HAT_SPEED_WITH_EFFICIENCY_0_DESCRIPTION)
                .defineInRange("strawHatSpeedWithEfficiency0", STRAW_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT, STRAW_HAT_SPEED_WITH_EFFICIENCY_0_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_0_MAX);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_1 = builder
                .comment(STRAW_HAT_SPEED_WITH_EFFICIENCY_1_DESCRIPTION)
                .defineInRange("strawHatSpeedWithEfficiency1", STRAW_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT, STRAW_HAT_SPEED_WITH_EFFICIENCY_1_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_1_MAX);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_2 = builder
                .comment(STRAW_HAT_SPEED_WITH_EFFICIENCY_2_DESCRIPTION)
                .defineInRange("strawHatSpeedWithEfficiency2", STRAW_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT, STRAW_HAT_SPEED_WITH_EFFICIENCY_2_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_2_MAX);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_3 = builder
                .comment(STRAW_HAT_SPEED_WITH_EFFICIENCY_3_DESCRIPTION)
                .defineInRange("strawHatSpeedWithEfficiency3", STRAW_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT, STRAW_HAT_SPEED_WITH_EFFICIENCY_3_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_3_MAX);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_4 = builder
                .comment(STRAW_HAT_SPEED_WITH_EFFICIENCY_4_DESCRIPTION)
                .defineInRange("strawHatSpeedWithEfficiency4", STRAW_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT, STRAW_HAT_SPEED_WITH_EFFICIENCY_4_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_4_MAX);
        STRAW_HAT_SPEED_WITH_EFFICIENCY_5 = builder
                .comment(STRAW_HAT_SPEED_WITH_EFFICIENCY_5_DESCRIPTION)
                .defineInRange("strawHatSpeedWithEfficiency5", STRAW_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT, STRAW_HAT_SPEED_WITH_EFFICIENCY_5_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_5_MAX);
        builder.pop();

        builder.push("WingHelmet");
        WING_HELMET_DAMAGE_ENABLED = builder
                .comment(WING_HELMET_DAMAGE_ENABLED_DESCRIPTION)
                .define("wingHelmetDamageEnabled", WING_HELMET_DAMAGE_ENABLED_DEFAULT);
        WING_HELMET_DURABILITY = builder
                .comment(WING_HELMET_DURABILITY_DESCRIPTION)
                .defineInRange("wingHelmetDurability", WING_HELMET_DURABILITY_DEFAULT, WING_HELMET_DURABILITY_MIN, WING_HELMET_DURABILITY_MAX);
        WING_HELMET_LEVITATION_ENABLED = builder
                .comment(WING_HELMET_LEVITATION_ENABLED_DESCRIPTION)
                .define("wingHelmetLevitationEnabled", WING_HELMET_LEVITATION_ENABLED_DEFAULT);
        builder.pop();

        builder.pop();

        SERVER_CONFIG = builder.build();
    }

    @Override
    public void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
        Path path = FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).resolve(Constants.MOD_ID + "-server.toml");
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        SERVER_CONFIG.setConfig(configData);
    }

    private boolean getBoolean(ForgeConfigSpec.BooleanValue config, boolean defaultValue) {
        try {
            return config.get();
        } catch (IllegalStateException ex) {
            return defaultValue;
        }
    }

    private int getInteger(ForgeConfigSpec.IntValue config, int defaultValue) {
        try {
            return config.get();
        } catch (IllegalStateException ex) {
            return defaultValue;
        }
    }

    private int getEnchantmentInteger(int enchantmentLevel, ForgeConfigSpec.IntValue config0, int defaultValue0, ForgeConfigSpec.IntValue config1, int defaultValue1, ForgeConfigSpec.IntValue config2, int defaultValue2, ForgeConfigSpec.IntValue config3, int defaultValue3, ForgeConfigSpec.IntValue config4, int defaultValue4, ForgeConfigSpec.IntValue config5, int defaultValue5) {
        if (enchantmentLevel < 0) {
            return getInteger(config0, defaultValue0);
        }
        return switch (enchantmentLevel) {
            case 0 -> getInteger(config0, defaultValue0);
            case 1 -> getInteger(config1, defaultValue1);
            case 2 -> getInteger(config2, defaultValue2);
            case 3 -> getInteger(config3, defaultValue3);
            case 4 -> getInteger(config4, defaultValue4);
            default -> getInteger(config5, defaultValue5);
        };
    }

    private double getDouble(ForgeConfigSpec.DoubleValue config, double defaultValue) {
        try {
            return config.get();
        } catch (IllegalStateException ex) {
            return defaultValue;
        }
    }

    private double getEnchantmentDouble(int enchantmentLevel, ForgeConfigSpec.DoubleValue config0, double defaultValue0, ForgeConfigSpec.DoubleValue config1, double defaultValue1, ForgeConfigSpec.DoubleValue config2, double defaultValue2, ForgeConfigSpec.DoubleValue config3, double defaultValue3, ForgeConfigSpec.DoubleValue config4, double defaultValue4, ForgeConfigSpec.DoubleValue config5, double defaultValue5) {
        if (enchantmentLevel < 0) {
            return getDouble(config0, defaultValue0);
        }
        return switch (enchantmentLevel) {
            case 0 -> getDouble(config0, defaultValue0);
            case 1 -> getDouble(config1, defaultValue1);
            case 2 -> getDouble(config2, defaultValue2);
            case 3 -> getDouble(config3, defaultValue3);
            case 4 -> getDouble(config4, defaultValue4);
            default -> getDouble(config5, defaultValue5);
        };
    }

    @Override
    public boolean isCuriosAddHeadSlot() {
        return getBoolean(CURIOS_ADD_HEAD_SLOT, CURIOS_ADD_HEAD_SLOT_DEFAULT);
    }

    @Override
    public boolean isAquanautHelmetDamageEnabled() {
        return getBoolean(AQUANAUT_HELMET_DAMAGE_ENABLED, AQUANAUT_HELMET_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getAquanautHelmetDurability() {
        return getInteger(AQUANAUT_HELMET_DURABILITY, AQUANAUT_HELMET_DURABILITY_DEFAULT);
    }

    @Override
    public int getAquanautHelmetEffectTimeWithRespiration(int enchantmentLevel) {
        return getEnchantmentInteger(enchantmentLevel,
                AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_0_DEFAULT,
                AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_1_DEFAULT,
                AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_2_DEFAULT,
                AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3_DEFAULT,
                AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3_DEFAULT,
                AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3, AQUANAUT_HELMET_EFFECT_TIME_WITH_RESPIRATION_3_DEFAULT
        );
    }

    @Override
    public boolean isBunnyEarsDamageEnabled() {
        return getBoolean(BUNNY_EARS_DAMAGE_ENABLED, BUNNY_EARS_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getBunnyEarsDurability() {
        return getInteger(BUNNY_EARS_DURABILITY, BUNNY_EARS_DURABILITY_DEFAULT);
    }

    @Override
    public boolean isChoppingHatDamageEnabled() {
        return getBoolean(CHOPPING_HAT_DAMAGE_ENABLED, CHOPPING_HAT_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getChoppingHatDurability() {
        return getInteger(CHOPPING_HAT_DURABILITY, CHOPPING_HAT_DURABILITY_DEFAULT);
    }

    @Override
    public double getChoppingHatSpeedWithEfficiency(int enchantmentLevel) {
        return getEnchantmentDouble(enchantmentLevel,
                CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT,
                CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT,
                CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT,
                CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT,
                CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT,
                CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT
        );
    }

    @Override
    public boolean isEnderHelmetDamageEnabled() {
        return getBoolean(ENDER_HELMET_DAMAGE_ENABLED, ENDER_HELMET_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getEnderHelmetDurability() {
        return getInteger(ENDER_HELMET_DURABILITY, ENDER_HELMET_DURABILITY_DEFAULT);
    }

    @Override
    public boolean isEnderHelmetInterdimensionalEnabled() {
        return getBoolean(ENDER_HELMET_INTERDIMENSIONAL_ENABLED, ENDER_HELMET_INTERDIMENSIONAL_ENABLED_DEFAULT);
    }

    @Override
    public boolean isHaloDamageEnabled() {
        return getBoolean(HALO_DAMAGE_ENABLED, HALO_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getHaloDurability() {
        return getInteger(HALO_DURABILITY, HALO_DURABILITY_DEFAULT);
    }

    @Override
    public boolean isLuckyHatDamageEnabled() {
        return getBoolean(LUCKY_HAT_DAMAGE_ENABLED, LUCKY_HAT_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getLuckyHatDurability() {
        return getInteger(LUCKY_HAT_DURABILITY, LUCKY_HAT_DURABILITY_DEFAULT);
    }

    @Override
    public boolean isLuckyHatUnluckEnabled() {
        return getBoolean(LUCKY_HAT_UNLUCK_ENABLED, LUCKY_HAT_UNLUCK_ENABLED_DEFAULT);
    }

    @Override
    public boolean isMiningHatDamageEnabled() {
        return getBoolean(MINING_HAT_DAMAGE_ENABLED, MINING_HAT_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getMiningHatDurability() {
        return getInteger(MINING_HAT_DURABILITY, MINING_HAT_DURABILITY_DEFAULT);
    }

    @Override
    public boolean isMiningHatNightVisionEnabled() {
        return getBoolean(MINING_HAT_NIGHT_VISION_ENABLED, MINING_HAT_NIGHT_VISION_ENABLED_DEFAULT);
    }

    @Override
    public boolean isMiningHatMakePiglinsNeutralEnabled() {
        return getBoolean(MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED, MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED_DEFAULT);
    }

    @Override
    public double getMiningHatSpeedWithEfficiency(int enchantmentLevel) {
        return getEnchantmentDouble(enchantmentLevel,
                MINING_HAT_SPEED_WITH_EFFICIENCY_0, MINING_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT,
                MINING_HAT_SPEED_WITH_EFFICIENCY_1, MINING_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT,
                MINING_HAT_SPEED_WITH_EFFICIENCY_2, MINING_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT,
                MINING_HAT_SPEED_WITH_EFFICIENCY_3, MINING_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT,
                MINING_HAT_SPEED_WITH_EFFICIENCY_4, MINING_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT,
                MINING_HAT_SPEED_WITH_EFFICIENCY_5, MINING_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT
        );
    }

    @Override
    public boolean isMushroomHatDamageEnabled() {
        return getBoolean(MUSHROOM_HAT_DAMAGE_ENABLED, MUSHROOM_HAT_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getMushroomHatDurability() {
        return getInteger(MUSHROOM_HAT_DURABILITY, MUSHROOM_HAT_DURABILITY_DEFAULT);
    }

    @Override
    public int getMushroomHatEatInterval() {
        return getInteger(MUSHROOM_HAT_EAT_INTERVAL, MUSHROOM_HAT_EAT_INTERVAL_DEFAULT);
    }

    @Override
    public boolean isPostmanHatDamageEnabled() {
        return getBoolean(POSTMAN_HAT_DAMAGE_ENABLED, POSTMAN_HAT_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getPostmanHatDurability() {
        return getInteger(POSTMAN_HAT_DURABILITY, POSTMAN_HAT_DURABILITY_DEFAULT);
    }

    @Override
    public boolean isPostmanHatHungerEnabled() {
        return getBoolean(POSTMAN_HAT_HUNGER_ENABLED, POSTMAN_HAT_HUNGER_ENABLED_DEFAULT);
    }

    @Override
    public boolean isShulkerHelmetDamageEnabled() {
        return getBoolean(SHULKER_HELMET_DAMAGE_ENABLED, SHULKER_HELMET_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getShulkerHelmetDurability() {
        return getInteger(SHULKER_HELMET_DURABILITY, SHULKER_HELMET_DURABILITY_DEFAULT);
    }

    @Override
    public boolean isStockingCapDamageEnabled() {
        return getBoolean(STOCKING_CAP_DAMAGE_ENABLED, STOCKING_CAP_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getStockingCapDurability() {
        return getInteger(STOCKING_CAP_DURABILITY, STOCKING_CAP_DURABILITY_DEFAULT);
    }

    @Override
    public boolean isStrawHatDamageEnabled() {
        return getBoolean(STRAW_HAT_DAMAGE_ENABLED, STRAW_HAT_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getStrawHatDurability() {
        return getInteger(STRAW_HAT_DURABILITY, STRAW_HAT_DURABILITY_DEFAULT);
    }

    @Override
    public double getStrawHatSpeedWithEfficiency(int enchantmentLevel) {
        return getEnchantmentDouble(enchantmentLevel,
                STRAW_HAT_SPEED_WITH_EFFICIENCY_0, STRAW_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT,
                STRAW_HAT_SPEED_WITH_EFFICIENCY_1, STRAW_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT,
                STRAW_HAT_SPEED_WITH_EFFICIENCY_2, STRAW_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT,
                STRAW_HAT_SPEED_WITH_EFFICIENCY_3, STRAW_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT,
                STRAW_HAT_SPEED_WITH_EFFICIENCY_4, STRAW_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT,
                STRAW_HAT_SPEED_WITH_EFFICIENCY_5, STRAW_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT
        );
    }

    @Override
    public boolean isWingHelmetDamageEnabled() {
        return getBoolean(WING_HELMET_DAMAGE_ENABLED, WING_HELMET_DAMAGE_ENABLED_DEFAULT);
    }

    @Override
    public int getWingHelmetDurability() {
        return getInteger(WING_HELMET_DURABILITY, WING_HELMET_DURABILITY_DEFAULT);
    }

    @Override
    public boolean isWingHelmetLevitationEnabled() {
        return getBoolean(WING_HELMET_LEVITATION_ENABLED, WING_HELMET_LEVITATION_ENABLED_DEFAULT);
    }

}
