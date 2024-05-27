package de.cech12.usefulhats.platform.services;

/**
 * Common configuration helper service interface.
 */
public interface IConfigHelper {

    String AQUANAUT_HELMET_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Aquanaut Helmet should be enabled.";
    boolean AQUANAUT_HELMET_DAMAGE_ENABLED_DEFAULT = true;

    String AQUANAUT_HELMET_DURABILITY_DESCRIPTION = "Aquanaut Helmet durability.";
    int AQUANAUT_HELMET_DURABILITY_DEFAULT = 600;
    int AQUANAUT_HELMET_DURABILITY_MIN = 1;
    int AQUANAUT_HELMET_DURABILITY_MAX = 10000;

    String AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_0_DESCRIPTION = "Aquanaut Helmet effect time of Conduit Power without Efficiency enchantment. (in seconds)";
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_0_DEFAULT = 60;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_0_MIN = 10;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_0_MAX = 120;

    String AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_1_DESCRIPTION = "Aquanaut Helmet effect time of Conduit Power with Efficiency I. (in seconds)";
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_1_DEFAULT = 120;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_1_MIN = 10;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_1_MAX = 180;

    String AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_2_DESCRIPTION = "Aquanaut Helmet effect time of Conduit Power with Efficiency II. (in seconds)";
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_2_DEFAULT = 180;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_2_MIN = 10;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_2_MAX = 240;

    String AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_3_DESCRIPTION = "Aquanaut Helmet effect time of Conduit Power with Efficiency III. (in seconds)";
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_3_DEFAULT = 240;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_3_MIN = 10;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_3_MAX = 300;

    String AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_4_DESCRIPTION = "Aquanaut Helmet effect time of Conduit Power with Efficiency IV. (in seconds)";
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_4_DEFAULT = 300;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_4_MIN = 10;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_4_MAX = 360;

    String AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_5_DESCRIPTION = "Aquanaut Helmet effect time of Conduit Power with Efficiency V. (in seconds)";
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_5_DEFAULT = 360;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_5_MIN = 10;
    int AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_5_MAX = 420;

    String BUNNY_EARS_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Bunny Ears should be enabled.";
    boolean BUNNY_EARS_DAMAGE_ENABLED_DEFAULT = true;

    String BUNNY_EARS_DURABILITY_DESCRIPTION = "Bunny Ears durability.";
    int BUNNY_EARS_DURABILITY_DEFAULT = 450;
    int BUNNY_EARS_DURABILITY_MIN = 1;
    int BUNNY_EARS_DURABILITY_MAX = 10000;

    String CHOPPING_HAT_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Chopping Hat should be enabled.";
    boolean CHOPPING_HAT_DAMAGE_ENABLED_DEFAULT = true;

    String CHOPPING_HAT_DURABILITY_DESCRIPTION = "Chopping Hat durability.";
    int CHOPPING_HAT_DURABILITY_DEFAULT = 300;
    int CHOPPING_HAT_DURABILITY_MIN = 1;
    int CHOPPING_HAT_DURABILITY_MAX = 10000;

    String CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_DESCRIPTION = "Chopping Hat speed increase without Efficiency enchantment.";
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT = 0.2;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_MIN = 0.0;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_MAX = 5.0;

    String CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_DESCRIPTION = "Chopping Hat speed increase with Efficiency I.";
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT = 0.4;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_MIN = 0.0;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_MAX = 5.0;

    String CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_DESCRIPTION = "Chopping Hat speed increase with Efficiency II.";
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT = 0.6;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_MIN = 0.0;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_MAX = 5.0;

    String CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_DESCRIPTION = "Chopping Hat speed increase with Efficiency III.";
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT = 0.8;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_MIN = 0.0;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_MAX = 5.0;

    String CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_DESCRIPTION = "Chopping Hat speed increase with Efficiency IV.";
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT = 1.0;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_MIN = 0.0;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_MAX = 5.0;

    String CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_DESCRIPTION = "Chopping Hat speed increase with Efficiency V.";
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT = 1.5;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_MIN = 0.0;
    double CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_MAX = 5.0;

    String ENDER_HELMET_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Ender Helmet should be enabled.";
    boolean ENDER_HELMET_DAMAGE_ENABLED_DEFAULT = true;

    String ENDER_HELMET_DURABILITY_DESCRIPTION = "Ender Helmet durability.";
    int ENDER_HELMET_DURABILITY_DEFAULT = 80;
    int ENDER_HELMET_DURABILITY_MIN = 1;
    int ENDER_HELMET_DURABILITY_MAX = 10000;

    String ENDER_HELMET_INTERDIMENSIONAL_ENABLED_DESCRIPTION = "Whether or not interdimensional teleporting with the Ender Helmet should be enabled.";
    boolean ENDER_HELMET_INTERDIMENSIONAL_ENABLED_DEFAULT = true;

    String HALO_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Halo should be enabled.";
    boolean HALO_DAMAGE_ENABLED_DEFAULT = true;

    String HALO_DURABILITY_DESCRIPTION = "Halo durability.";
    int HALO_DURABILITY_DEFAULT = 600;
    int HALO_DURABILITY_MIN = 1;
    int HALO_DURABILITY_MAX = 10000;

    String LUCKY_HAT_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Lucky Hat should be enabled.";
    boolean LUCKY_HAT_DAMAGE_ENABLED_DEFAULT = true;

    String LUCKY_HAT_DURABILITY_DESCRIPTION = "Lucky Hat durability.";
    int LUCKY_HAT_DURABILITY_DEFAULT = 300;
    int LUCKY_HAT_DURABILITY_MIN = 1;
    int LUCKY_HAT_DURABILITY_MAX = 10000;

    String LUCKY_HAT_UNLUCK_ENABLED_DESCRIPTION = "Whether or not Unluck effect of Lucky Hat should be enabled after fishing/killing.";
    boolean LUCKY_HAT_UNLUCK_ENABLED_DEFAULT = true;

    String MINING_HAT_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Mining Hat should be enabled.";
    boolean MINING_HAT_DAMAGE_ENABLED_DEFAULT = true;

    String MINING_HAT_DURABILITY_DESCRIPTION = "Mining Hat durability.";
    int MINING_HAT_DURABILITY_DEFAULT = 450;
    int MINING_HAT_DURABILITY_MIN = 1;
    int MINING_HAT_DURABILITY_MAX = 10000;

    String MINING_HAT_NIGHT_VISION_ENABLED_DESCRIPTION = "Whether or not Night Vision effect of Mining Hat should be enabled in dark areas.";
    boolean MINING_HAT_NIGHT_VISION_ENABLED_DEFAULT = true;

    String MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED_DESCRIPTION = "Whether or not wearing a Mining Hat should make Piglins neutral.";
    boolean MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED_DEFAULT = true;

    String MINING_HAT_SPEED_WITH_EFFICIENCY_0_DESCRIPTION = "Mining Hat speed increase without Efficiency enchantment.";
    double MINING_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT = 0.2;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_0_MIN = 0.0;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_0_MAX = 5.0;

    String MINING_HAT_SPEED_WITH_EFFICIENCY_1_DESCRIPTION = "Mining Hat speed increase with Efficiency I.";
    double MINING_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT = 0.4;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_1_MIN = 0.0;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_1_MAX = 5.0;

    String MINING_HAT_SPEED_WITH_EFFICIENCY_2_DESCRIPTION = "Mining Hat speed increase with Efficiency II.";
    double MINING_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT = 0.6;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_2_MIN = 0.0;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_2_MAX = 5.0;
    
    String MINING_HAT_SPEED_WITH_EFFICIENCY_3_DESCRIPTION = "Mining Hat speed increase with Efficiency III.";
    double MINING_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT = 0.8;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_3_MIN = 0.0;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_3_MAX = 5.0;
    
    String MINING_HAT_SPEED_WITH_EFFICIENCY_4_DESCRIPTION = "Mining Hat speed increase with Efficiency IV.";
    double MINING_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT = 1.0;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_4_MIN = 0.0;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_4_MAX = 5.0;
    
    String MINING_HAT_SPEED_WITH_EFFICIENCY_5_DESCRIPTION = "Mining Hat speed increase with Efficiency V.";
    double MINING_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT = 1.5;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_5_MIN = 0.0;
    double MINING_HAT_SPEED_WITH_EFFICIENCY_5_MAX = 5.0;
    
    String MUSHROOM_HAT_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Mushroom Hat should be enabled.";
    boolean MUSHROOM_HAT_DAMAGE_ENABLED_DEFAULT = true;

    String MUSHROOM_HAT_DURABILITY_DESCRIPTION = "Mushroom Hat durability.";
    int MUSHROOM_HAT_DURABILITY_DEFAULT = 80;
    int MUSHROOM_HAT_DURABILITY_MIN = 1;
    int MUSHROOM_HAT_DURABILITY_MAX = 10000;
    
    String MUSHROOM_HAT_EAT_INTERVAL_DESCRIPTION = "Interval the Mushroom Hat is eaten by the player (in ticks). default: 60 (3 seconds)";
    int MUSHROOM_HAT_EAT_INTERVAL_DEFAULT = 60;
    int MUSHROOM_HAT_EAT_INTERVAL_MIN = 1;
    int MUSHROOM_HAT_EAT_INTERVAL_MAX = 10000;
    
    String POSTMAN_HAT_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Postman Hat should be enabled.";
    boolean POSTMAN_HAT_DAMAGE_ENABLED_DEFAULT = true;

    String POSTMAN_HAT_DURABILITY_DESCRIPTION = "Postman Hat durability.";
    int POSTMAN_HAT_DURABILITY_DEFAULT = 600;
    int POSTMAN_HAT_DURABILITY_MIN = 1;
    int POSTMAN_HAT_DURABILITY_MAX = 10000;
    
    String POSTMAN_HAT_HUNGER_ENABLED_DESCRIPTION = "Whether or not Hunger effect of Postman Hat should be enabled while running.";
    boolean POSTMAN_HAT_HUNGER_ENABLED_DEFAULT = true;

    String SHULKER_HELMET_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Shulker Helmet should be enabled.";
    boolean SHULKER_HELMET_DAMAGE_ENABLED_DEFAULT = true;

    String SHULKER_HELMET_DURABILITY_DESCRIPTION = "Shulker Helmet durability.";
    int SHULKER_HELMET_DURABILITY_DEFAULT = 600;
    int SHULKER_HELMET_DURABILITY_MIN = 1;
    int SHULKER_HELMET_DURABILITY_MAX = 10000;
    
    String STOCKING_CAP_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Stocking Cap should be enabled.";
    boolean STOCKING_CAP_DAMAGE_ENABLED_DEFAULT = true;

    String STOCKING_CAP_DURABILITY_DESCRIPTION = "Stocking Cap durability.";
    int STOCKING_CAP_DURABILITY_DEFAULT = 600;
    int STOCKING_CAP_DURABILITY_MIN = 1;
    int STOCKING_CAP_DURABILITY_MAX = 10000;
    
    String STRAW_HAT_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Straw Hat should be enabled.";
    boolean STRAW_HAT_DAMAGE_ENABLED_DEFAULT = true;

    String STRAW_HAT_DURABILITY_DESCRIPTION = "Straw Hat durability.";
    int STRAW_HAT_DURABILITY_DEFAULT = 300;
    int STRAW_HAT_DURABILITY_MIN = 1;
    int STRAW_HAT_DURABILITY_MAX = 10000;
    
    String STRAW_HAT_SPEED_WITH_EFFICIENCY_0_DESCRIPTION = "Straw Hat speed increase without Efficiency enchantment.";
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT = 0.2;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_0_MIN = 0.0;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_0_MAX = 5.0;
    
    String STRAW_HAT_SPEED_WITH_EFFICIENCY_1_DESCRIPTION = "Straw Hat speed increase with Efficiency I.";
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT = 0.4;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_1_MIN = 0.0;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_1_MAX = 5.0;
    
    String STRAW_HAT_SPEED_WITH_EFFICIENCY_2_DESCRIPTION = "Straw Hat speed increase with Efficiency II.";
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT = 0.6;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_2_MIN = 0.0;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_2_MAX = 5.0;
    
    String STRAW_HAT_SPEED_WITH_EFFICIENCY_3_DESCRIPTION = "Straw Hat speed increase with Efficiency III.";
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT = 0.8;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_3_MIN = 0.0;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_3_MAX = 5.0;
    
    String STRAW_HAT_SPEED_WITH_EFFICIENCY_4_DESCRIPTION = "Straw Hat speed increase with Efficiency IV.";
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT = 1.0;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_4_MIN = 0.0;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_4_MAX = 5.0;
    
    String STRAW_HAT_SPEED_WITH_EFFICIENCY_5_DESCRIPTION = "Straw Hat speed increase with Efficiency V.";
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT = 1.5;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_5_MIN = 0.0;
    double STRAW_HAT_SPEED_WITH_EFFICIENCY_5_MAX = 5.0;
    
    String WING_HELMET_DAMAGE_ENABLED_DESCRIPTION = "Whether or not damaging of Wing Helmet should be enabled.";
    boolean WING_HELMET_DAMAGE_ENABLED_DEFAULT = true;

    String WING_HELMET_DURABILITY_DESCRIPTION = "Wing Helmet durability.";
    int WING_HELMET_DURABILITY_DEFAULT = 600;
    int WING_HELMET_DURABILITY_MIN = 1;
    int WING_HELMET_DURABILITY_MAX = 10000;
    
    String WING_HELMET_LEVITATION_ENABLED_DESCRIPTION = "Whether or not Levitation effect of Wing Helmet should be enabled.";
    boolean WING_HELMET_LEVITATION_ENABLED_DEFAULT = true;

    /**
     * Initialization method for the Service implementations.
     */
    void init();

    boolean isAquanautHelmetDamageEnabled();
    int getAquanautHelmetDurability();
    int getAquanautHelmetEffectTimeWithEfficiency(int enchantmentLevel);

    boolean isBunnyEarsDamageEnabled();
    int getBunnyEarsDurability();

    boolean isChoppingHatDamageEnabled();
    int getChoppingHatDurability();
    double getChoppingHatSpeedWithEfficiency(int enchantmentLevel);

    boolean isEnderHelmetDamageEnabled();
    int getEnderHelmetDurability();
    boolean isEnderHelmetInterdimensionalEnabled();

    boolean isHaloDamageEnabled();
    int getHaloDurability();

    boolean isLuckyHatDamageEnabled();
    int getLuckyHatDurability();
    boolean isLuckyHatUnluckEnabled();

    boolean isMiningHatDamageEnabled();
    int getMiningHatDurability();
    boolean isMiningHatNightVisionEnabled();
    boolean isMiningHatMakePiglinsNeutralEnabled();
    double getMiningHatSpeedWithEfficiency(int enchantmentLevel);

    boolean isMushroomHatDamageEnabled();
    int getMushroomHatDurability();
    int getMushroomHatEatInterval();

    boolean isPostmanHatDamageEnabled();
    int getPostmanHatDurability();
    boolean isPostmanHatHungerEnabled();

    boolean isShulkerHelmetDamageEnabled();
    int getShulkerHelmetDurability();

    boolean isStockingCapDamageEnabled();
    int getStockingCapDurability();

    boolean isStrawHatDamageEnabled();
    int getStrawHatDurability();
    double getStrawHatSpeedWithEfficiency(int enchantmentLevel);

    boolean isWingHelmetDamageEnabled();
    int getWingHelmetDurability();
    boolean isWingHelmetLevitationEnabled();

}