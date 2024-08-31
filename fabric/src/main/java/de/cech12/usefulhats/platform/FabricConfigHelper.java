package de.cech12.usefulhats.platform;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.platform.services.IConfigHelper;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

/**
 * The config service implementation for Fabric.
 */
@Config(name = Constants.MOD_ID)
public class FabricConfigHelper implements ConfigData, IConfigHelper {

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public AquanautHelmet AQUANAUT_HELMET = new AquanautHelmet();

    public static final class AquanautHelmet implements ConfigData {

        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = AQUANAUT_HELMET_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 2)
        public int DURABILITY = AQUANAUT_HELMET_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int EFFECT_TIME_WITH_EFFICIENCY_0 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_0_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int EFFECT_TIME_WITH_EFFICIENCY_1 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_1_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int EFFECT_TIME_WITH_EFFICIENCY_2 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_2_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int EFFECT_TIME_WITH_EFFICIENCY_3 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_3_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int EFFECT_TIME_WITH_EFFICIENCY_4 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_4_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int EFFECT_TIME_WITH_EFFICIENCY_5 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_5_DEFAULT;

        private AquanautHelmet() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public BunnyEars BUNNY_EARS = new BunnyEars();

    public static final class BunnyEars implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = BUNNY_EARS_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = BUNNY_EARS_DURABILITY_DEFAULT;

        private BunnyEars() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public ChoppingHat CHOPPING_HAT = new ChoppingHat();

    public static final class ChoppingHat implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = CHOPPING_HAT_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = CHOPPING_HAT_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_0 = (int) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_1 = (int) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_2 = (int) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_3 = (int) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_4 = (int) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_5 = (int) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT * 100);

        private ChoppingHat() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public EnderHelmet ENDER_HELMET = new EnderHelmet();

    public static final class EnderHelmet implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = ENDER_HELMET_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = ENDER_HELMET_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean INTERDIMENSIONAL_ENABLED = ENDER_HELMET_INTERDIMENSIONAL_ENABLED_DEFAULT;

        private EnderHelmet() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public Halo HALO = new Halo();

    public static final class Halo implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = HALO_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = HALO_DURABILITY_DEFAULT;
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public LuckyHat LUCKY_HAT = new LuckyHat();

    public static final class LuckyHat implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = LUCKY_HAT_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = LUCKY_HAT_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean UNLUCK_ENABLED = LUCKY_HAT_UNLUCK_ENABLED_DEFAULT;

        private LuckyHat() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public MiningHat MINING_HAT = new MiningHat();

    public static final class MiningHat implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = MINING_HAT_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = MINING_HAT_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean NIGHT_VISION_ENABLED = MINING_HAT_NIGHT_VISION_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean MAKE_PIGLINS_NEUTRAL_ENABLED = MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_0 = (int) (MINING_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_1 = (int) (MINING_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_2 = (int) (MINING_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_3 = (int) (MINING_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_4 = (int) (MINING_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_5 = (int) (MINING_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT * 100);

        private MiningHat() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public MushroomHat MUSHROOM_HAT = new MushroomHat();

    public static final class MushroomHat implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = MUSHROOM_HAT_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = MUSHROOM_HAT_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int EAT_INTERVAL = MUSHROOM_HAT_EAT_INTERVAL_DEFAULT;

        private MushroomHat() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public PostmanHat POSTMAN_HAT = new PostmanHat();

    public static final class PostmanHat implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = POSTMAN_HAT_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = POSTMAN_HAT_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean HUNGER_ENABLED = POSTMAN_HAT_HUNGER_ENABLED_DEFAULT;

        private PostmanHat() {}
    }


    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public ShulkerHelmet SHULKER_HELMET = new ShulkerHelmet();

    public static final class ShulkerHelmet implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = SHULKER_HELMET_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = SHULKER_HELMET_DURABILITY_DEFAULT;

        private ShulkerHelmet() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public StockingCap STOCKING_CAP = new StockingCap();

    public static final class StockingCap implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = STOCKING_CAP_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = STOCKING_CAP_DURABILITY_DEFAULT;

        private StockingCap() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public StrawHat STRAW_HAT = new StrawHat();

    public static final class StrawHat implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = STRAW_HAT_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = STRAW_HAT_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_0 = (int) (STRAW_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_1 = (int) (STRAW_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_2 = (int) (STRAW_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_3 = (int) (STRAW_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_4 = (int) (STRAW_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SPEED_WITH_EFFICIENCY_5 = (int) (STRAW_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT * 100);

        private StrawHat() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public WingHelmet WING_HELMET = new WingHelmet();

    public static final class WingHelmet implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = WING_HELMET_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 3)
        public int DURABILITY = WING_HELMET_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean LEVITATION_ENABLED = WING_HELMET_LEVITATION_ENABLED_DEFAULT;

        private WingHelmet() {}
    }

    @Override
    public void init() {
        AutoConfig.register(FabricConfigHelper.class, Toml4jConfigSerializer::new);
    }

    private FabricConfigHelper getConfig() {
        return AutoConfig.getConfigHolder(FabricConfigHelper.class).getConfig();
    }

    private int getEnchantmentInteger(int enchantmentLevel, int config0, int config1, int config2, int config3, int config4, int config5) {
        if (enchantmentLevel < 0) {
            return config0;
        }
        return switch (enchantmentLevel) {
            case 0 -> config0;
            case 1 -> config1;
            case 2 -> config2;
            case 3 -> config3;
            case 4 -> config4;
            default -> config5;
        };
    }

    private double getDouble(int config) {
        return config / 100D;
    }

    private double getEnchantmentDouble(int enchantmentLevel, double config0, double config1, double config2, double config3, double config4, double config5) {
        if (enchantmentLevel < 0) {
            return config0;
        }
        return switch (enchantmentLevel) {
            case 0 -> config0;
            case 1 -> config1;
            case 2 -> config2;
            case 3 -> config3;
            case 4 -> config4;
            default -> config5;
        };
    }

    @Override
    public boolean isAquanautHelmetDamageEnabled() {
        return getConfig().AQUANAUT_HELMET.DAMAGE_ENABLED;
    }

    @Override
    public int getAquanautHelmetDurability() {
        return Math.clamp(getConfig().AQUANAUT_HELMET.DURABILITY, AQUANAUT_HELMET_DURABILITY_MIN, AQUANAUT_HELMET_DURABILITY_MAX);
    }

    @Override
    public int getAquanautHelmetEffectTimeWithEfficiency(int enchantmentLevel) {
        FabricConfigHelper config = getConfig();
        return getEnchantmentInteger(enchantmentLevel,
                Math.clamp(config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_0, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_0_MIN, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_0_MAX),
                Math.clamp(config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_1, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_1_MIN, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_1_MAX),
                Math.clamp(config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_2, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_2_MIN, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_2_MAX),
                Math.clamp(config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_3, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_3_MIN, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_3_MAX),
                Math.clamp(config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_4, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_4_MIN, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_4_MAX),
                Math.clamp(config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_5, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_5_MIN, AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_5_MAX)
        );
    }

    @Override
    public boolean isBunnyEarsDamageEnabled() {
        return getConfig().BUNNY_EARS.DAMAGE_ENABLED;
    }

    @Override
    public int getBunnyEarsDurability() {
        return Math.clamp(getConfig().getConfig().BUNNY_EARS.DURABILITY, BUNNY_EARS_DURABILITY_MIN, BUNNY_EARS_DURABILITY_MAX);
    }

    @Override
    public boolean isChoppingHatDamageEnabled() {
        return getConfig().CHOPPING_HAT.DAMAGE_ENABLED;
    }

    @Override
    public int getChoppingHatDurability() {
        return Math.clamp(getConfig().CHOPPING_HAT.DURABILITY, CHOPPING_HAT_DURABILITY_MIN, CHOPPING_HAT_DURABILITY_MAX);
    }

    @Override
    public double getChoppingHatSpeedWithEfficiency(int enchantmentLevel) {
        FabricConfigHelper config = getConfig();
        return getEnchantmentDouble(enchantmentLevel,
                Math.clamp(getDouble(config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_0), CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_MAX),
                Math.clamp(getDouble(config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_1), CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_MAX),
                Math.clamp(getDouble(config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_2), CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_MAX),
                Math.clamp(getDouble(config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_3), CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_MAX),
                Math.clamp(getDouble(config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_4), CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_MAX),
                Math.clamp(getDouble(config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_5), CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_MIN, CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_MAX)
        );
    }

    @Override
    public boolean isEnderHelmetDamageEnabled() {
        return getConfig().ENDER_HELMET.DAMAGE_ENABLED;
    }

    @Override
    public int getEnderHelmetDurability() {
        return Math.clamp(getConfig().ENDER_HELMET.DURABILITY, ENDER_HELMET_DURABILITY_MIN, ENDER_HELMET_DURABILITY_MAX);
    }

    @Override
    public boolean isEnderHelmetInterdimensionalEnabled() {
        return getConfig().ENDER_HELMET.INTERDIMENSIONAL_ENABLED;
    }

    @Override
    public boolean isHaloDamageEnabled() {
        return getConfig().HALO.DAMAGE_ENABLED;
    }

    @Override
    public int getHaloDurability() {
        return Math.clamp(getConfig().HALO.DURABILITY, HALO_DURABILITY_MIN, HALO_DURABILITY_MAX);
    }

    @Override
    public boolean isLuckyHatDamageEnabled() {
        return getConfig().LUCKY_HAT.DAMAGE_ENABLED;
    }

    @Override
    public int getLuckyHatDurability() {
        return Math.clamp(getConfig().LUCKY_HAT.DURABILITY, LUCKY_HAT_DURABILITY_MIN, LUCKY_HAT_DURABILITY_MAX);
    }

    @Override
    public boolean isLuckyHatUnluckEnabled() {
        return getConfig().LUCKY_HAT.UNLUCK_ENABLED;
    }

    @Override
    public boolean isMiningHatDamageEnabled() {
        return getConfig().MINING_HAT.DAMAGE_ENABLED;
    }

    @Override
    public int getMiningHatDurability() {
        return Math.clamp(getConfig().MINING_HAT.DURABILITY, MINING_HAT_DURABILITY_MIN, MINING_HAT_DURABILITY_MAX);
    }

    @Override
    public boolean isMiningHatNightVisionEnabled() {
        return getConfig().MINING_HAT.NIGHT_VISION_ENABLED;
    }

    @Override
    public boolean isMiningHatMakePiglinsNeutralEnabled() {
        return getConfig().MINING_HAT.MAKE_PIGLINS_NEUTRAL_ENABLED;
    }

    @Override
    public double getMiningHatSpeedWithEfficiency(int enchantmentLevel) {
        FabricConfigHelper config = getConfig();
        return getEnchantmentDouble(enchantmentLevel,
                Math.clamp(getDouble(config.MINING_HAT.SPEED_WITH_EFFICIENCY_0), MINING_HAT_SPEED_WITH_EFFICIENCY_0_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_0_MAX),
                Math.clamp(getDouble(config.MINING_HAT.SPEED_WITH_EFFICIENCY_1), MINING_HAT_SPEED_WITH_EFFICIENCY_1_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_1_MAX),
                Math.clamp(getDouble(config.MINING_HAT.SPEED_WITH_EFFICIENCY_2), MINING_HAT_SPEED_WITH_EFFICIENCY_2_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_2_MAX),
                Math.clamp(getDouble(config.MINING_HAT.SPEED_WITH_EFFICIENCY_3), MINING_HAT_SPEED_WITH_EFFICIENCY_3_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_3_MAX),
                Math.clamp(getDouble(config.MINING_HAT.SPEED_WITH_EFFICIENCY_4), MINING_HAT_SPEED_WITH_EFFICIENCY_4_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_4_MAX),
                Math.clamp(getDouble(config.MINING_HAT.SPEED_WITH_EFFICIENCY_5), MINING_HAT_SPEED_WITH_EFFICIENCY_5_MIN, MINING_HAT_SPEED_WITH_EFFICIENCY_5_MAX)
        );
    }

    @Override
    public boolean isMushroomHatDamageEnabled() {
        return getConfig().MUSHROOM_HAT.DAMAGE_ENABLED;
    }

    @Override
    public int getMushroomHatDurability() {
        return Math.clamp(getConfig().MUSHROOM_HAT.DURABILITY, MUSHROOM_HAT_DURABILITY_MIN, MUSHROOM_HAT_DURABILITY_MAX);
    }

    @Override
    public int getMushroomHatEatInterval() {
        return Math.clamp(getConfig().MUSHROOM_HAT.EAT_INTERVAL, MUSHROOM_HAT_EAT_INTERVAL_MIN, MUSHROOM_HAT_EAT_INTERVAL_MAX);
    }

    @Override
    public boolean isPostmanHatDamageEnabled() {
        return getConfig().POSTMAN_HAT.DAMAGE_ENABLED;
    }

    @Override
    public int getPostmanHatDurability() {
        return Math.clamp(getConfig().POSTMAN_HAT.DURABILITY, POSTMAN_HAT_DURABILITY_MIN, POSTMAN_HAT_DURABILITY_MAX);
    }

    @Override
    public boolean isPostmanHatHungerEnabled() {
        return getConfig().POSTMAN_HAT.HUNGER_ENABLED;
    }

    @Override
    public boolean isShulkerHelmetDamageEnabled() {
        return getConfig().SHULKER_HELMET.DAMAGE_ENABLED;
    }

    @Override
    public int getShulkerHelmetDurability() {
        return Math.clamp(getConfig().SHULKER_HELMET.DURABILITY, SHULKER_HELMET_DURABILITY_MIN, SHULKER_HELMET_DURABILITY_MAX);
    }

    @Override
    public boolean isStockingCapDamageEnabled() {
        return getConfig().STOCKING_CAP.DAMAGE_ENABLED;
    }

    @Override
    public int getStockingCapDurability() {
        return Math.clamp(getConfig().STOCKING_CAP.DURABILITY, STOCKING_CAP_DURABILITY_MIN, STOCKING_CAP_DURABILITY_MAX);
    }

    @Override
    public boolean isStrawHatDamageEnabled() {
        return getConfig().STRAW_HAT.DAMAGE_ENABLED;
    }

    @Override
    public int getStrawHatDurability() {
        return Math.clamp(getConfig().STRAW_HAT.DURABILITY, STRAW_HAT_DURABILITY_MIN, STRAW_HAT_DURABILITY_MAX);
    }

    @Override
    public double getStrawHatSpeedWithEfficiency(int enchantmentLevel) {
        FabricConfigHelper config = getConfig();
        return getEnchantmentDouble(enchantmentLevel,
                Math.clamp(getDouble(config.STRAW_HAT.SPEED_WITH_EFFICIENCY_0), STRAW_HAT_SPEED_WITH_EFFICIENCY_0_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_0_MAX),
                Math.clamp(getDouble(config.STRAW_HAT.SPEED_WITH_EFFICIENCY_1), STRAW_HAT_SPEED_WITH_EFFICIENCY_1_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_1_MAX),
                Math.clamp(getDouble(config.STRAW_HAT.SPEED_WITH_EFFICIENCY_2), STRAW_HAT_SPEED_WITH_EFFICIENCY_2_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_2_MAX),
                Math.clamp(getDouble(config.STRAW_HAT.SPEED_WITH_EFFICIENCY_3), STRAW_HAT_SPEED_WITH_EFFICIENCY_3_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_3_MAX),
                Math.clamp(getDouble(config.STRAW_HAT.SPEED_WITH_EFFICIENCY_4), STRAW_HAT_SPEED_WITH_EFFICIENCY_4_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_4_MAX),
                Math.clamp(getDouble(config.STRAW_HAT.SPEED_WITH_EFFICIENCY_5), STRAW_HAT_SPEED_WITH_EFFICIENCY_5_MIN, STRAW_HAT_SPEED_WITH_EFFICIENCY_5_MAX)
        );
    }

    @Override
    public boolean isWingHelmetDamageEnabled() {
        return getConfig().WING_HELMET.DAMAGE_ENABLED;
    }

    @Override
    public int getWingHelmetDurability() {
        return Math.clamp(getConfig().WING_HELMET.DURABILITY, WING_HELMET_DURABILITY_MIN, WING_HELMET_DURABILITY_MAX);
    }

    @Override
    public boolean isWingHelmetLevitationEnabled() {
        return getConfig().WING_HELMET.LEVITATION_ENABLED;
    }

}
