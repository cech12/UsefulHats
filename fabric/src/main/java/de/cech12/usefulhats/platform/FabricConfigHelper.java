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
        @ConfigEntry.BoundedDiscrete(min = AQUANAUT_HELMET_DURABILITY_MIN, max = AQUANAUT_HELMET_DURABILITY_MAX)
        public long DURABILITY = AQUANAUT_HELMET_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_0_MIN, max = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_0_MAX)
        public long EFFECT_TIME_WITH_EFFICIENCY_0 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_0_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_1_MIN, max = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_1_MAX)
        public long EFFECT_TIME_WITH_EFFICIENCY_1 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_1_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_2_MIN, max = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_2_MAX)
        public long EFFECT_TIME_WITH_EFFICIENCY_2 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_2_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_3_MIN, max = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_3_MAX)
        public long EFFECT_TIME_WITH_EFFICIENCY_3 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_3_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_4_MIN, max = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_4_MAX)
        public long EFFECT_TIME_WITH_EFFICIENCY_4 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_4_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_5_MIN, max = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_5_MAX)
        public long EFFECT_TIME_WITH_EFFICIENCY_5 = AQUANAUT_HELMET_EFFECT_TIME_WITH_EFFICIENCY_5_DEFAULT;

        private AquanautHelmet() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public BunnyEars BUNNY_EARS = new BunnyEars();

    public static final class BunnyEars implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = BUNNY_EARS_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = BUNNY_EARS_DURABILITY_MIN, max = BUNNY_EARS_DURABILITY_MAX)
        public long DURABILITY = BUNNY_EARS_DURABILITY_DEFAULT;

        private BunnyEars() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public ChoppingHat CHOPPING_HAT = new ChoppingHat();

    public static final class ChoppingHat implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = CHOPPING_HAT_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = CHOPPING_HAT_DURABILITY_MIN, max = CHOPPING_HAT_DURABILITY_MAX)
        public long DURABILITY = CHOPPING_HAT_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_MIN * 100), max = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_0 = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_MIN * 100), max = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_1 = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_MIN * 100), max = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_2 = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_MIN * 100), max = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_3 = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_MIN * 100), max = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_4 = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_MIN * 100), max = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_5 = (long) (CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT * 100);

        private ChoppingHat() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public EnderHelmet ENDER_HELMET = new EnderHelmet();

    public static final class EnderHelmet implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = ENDER_HELMET_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = ENDER_HELMET_DURABILITY_MIN, max = ENDER_HELMET_DURABILITY_MAX)
        public long DURABILITY = ENDER_HELMET_DURABILITY_DEFAULT;
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
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = HALO_DURABILITY_MIN, max = HALO_DURABILITY_MAX)
        public long DURABILITY = HALO_DURABILITY_DEFAULT;
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public LuckyHat LUCKY_HAT = new LuckyHat();

    public static final class LuckyHat implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = LUCKY_HAT_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = LUCKY_HAT_DURABILITY_MIN, max = LUCKY_HAT_DURABILITY_MAX)
        public long DURABILITY = LUCKY_HAT_DURABILITY_DEFAULT;
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
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = MINING_HAT_DURABILITY_MIN, max = MINING_HAT_DURABILITY_MAX)
        public long DURABILITY = MINING_HAT_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean NIGHT_VISION_ENABLED = MINING_HAT_NIGHT_VISION_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean MAKE_PIGLINS_NEUTRAL_ENABLED = MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_0_MIN * 100), max = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_0_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_0 = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_1_MIN * 100), max = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_1_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_1 = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_2_MIN * 100), max = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_2_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_2 = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_3_MIN * 100), max = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_3_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_3 = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_4_MIN * 100), max = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_4_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_4 = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_5_MIN * 100), max = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_5_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_5 = (long) (MINING_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT * 100);

        private MiningHat() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public MushroomHat MUSHROOM_HAT = new MushroomHat();

    public static final class MushroomHat implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = MUSHROOM_HAT_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = MUSHROOM_HAT_DURABILITY_MIN, max = MUSHROOM_HAT_DURABILITY_MAX)
        public long DURABILITY = MUSHROOM_HAT_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = MUSHROOM_HAT_EAT_INTERVAL_MIN, max = MUSHROOM_HAT_EAT_INTERVAL_MAX)
        public long EAT_INTERVAL = MUSHROOM_HAT_EAT_INTERVAL_DEFAULT;

        private MushroomHat() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public PostmanHat POSTMAN_HAT = new PostmanHat();

    public static final class PostmanHat implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = POSTMAN_HAT_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = POSTMAN_HAT_DURABILITY_MIN, max = POSTMAN_HAT_DURABILITY_MAX)
        public long DURABILITY = POSTMAN_HAT_DURABILITY_DEFAULT;
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
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = SHULKER_HELMET_DURABILITY_MIN, max = SHULKER_HELMET_DURABILITY_MAX)
        public long DURABILITY = SHULKER_HELMET_DURABILITY_DEFAULT;

        private ShulkerHelmet() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public StockingCap STOCKING_CAP = new StockingCap();

    public static final class StockingCap implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = STOCKING_CAP_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = STOCKING_CAP_DURABILITY_MIN, max = STOCKING_CAP_DURABILITY_MAX)
        public long DURABILITY = STOCKING_CAP_DURABILITY_DEFAULT;

        private StockingCap() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public StrawHat STRAW_HAT = new StrawHat();

    public static final class StrawHat implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = STRAW_HAT_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = STRAW_HAT_DURABILITY_MIN, max = STRAW_HAT_DURABILITY_MAX)
        public long DURABILITY = STRAW_HAT_DURABILITY_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_0_MIN * 100), max = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_0_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_0 = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_0_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_1_MIN * 100), max = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_1_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_1 = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_1_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_2_MIN * 100), max = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_2_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_2 = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_2_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_3_MIN * 100), max = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_3_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_3 = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_3_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_4_MIN * 100), max = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_4_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_4 = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_4_DEFAULT * 100);
        @ConfigEntry.Gui.Tooltip(count = 4)
        @ConfigEntry.BoundedDiscrete(min = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_5_MIN * 100), max = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_5_MAX * 100))
        public long SPEED_WITH_EFFICIENCY_5 = (long) (STRAW_HAT_SPEED_WITH_EFFICIENCY_5_DEFAULT * 100);

        private StrawHat() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public WingHelmet WING_HELMET = new WingHelmet();

    public static final class WingHelmet implements ConfigData {
        @ConfigEntry.Gui.Tooltip(count = 3)
        public boolean DAMAGE_ENABLED = WING_HELMET_DAMAGE_ENABLED_DEFAULT;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = WING_HELMET_DURABILITY_MIN, max = WING_HELMET_DURABILITY_MAX)
        public long DURABILITY = WING_HELMET_DURABILITY_DEFAULT;
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

    private int getInteger(long config) {
        return (int) config;
    }

    private int getEnchantmentInteger(int enchantmentLevel, long config0, long config1, long config2, long config3, long config4, long config5) {
        if (enchantmentLevel < 0) {
            return getInteger(config0);
        }
        return getInteger(switch (enchantmentLevel) {
            case 0 -> config0;
            case 1 -> config1;
            case 2 -> config2;
            case 3 -> config3;
            case 4 -> config4;
            default -> config5;
        });
    }

    private double getDouble(long config) {
        return config / 100D;
    }

    private double getEnchantmentDouble(int enchantmentLevel, long config0, long config1, long config2, long config3, long config4, long config5) {
        if (enchantmentLevel < 0) {
            return getDouble(config0);
        }
        return getDouble(switch (enchantmentLevel) {
            case 0 -> config0;
            case 1 -> config1;
            case 2 -> config2;
            case 3 -> config3;
            case 4 -> config4;
            default -> config5;
        });
    }

    @Override
    public boolean isAquanautHelmetDamageEnabled() {
        return getConfig().AQUANAUT_HELMET.DAMAGE_ENABLED;
    }

    @Override
    public int getAquanautHelmetDurability() {
        return getInteger(getConfig().AQUANAUT_HELMET.DURABILITY);
    }

    @Override
    public int getAquanautHelmetEffectTimeWithEfficiency(int enchantmentLevel) {
        FabricConfigHelper config = getConfig();
        return getEnchantmentInteger(enchantmentLevel,
                config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_0,
                config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_1,
                config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_2,
                config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_3,
                config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_4,
                config.AQUANAUT_HELMET.EFFECT_TIME_WITH_EFFICIENCY_5
        );
    }

    @Override
    public boolean isBunnyEarsDamageEnabled() {
        return getConfig().BUNNY_EARS.DAMAGE_ENABLED;
    }

    @Override
    public int getBunnyEarsDurability() {
        return getInteger(getConfig().getConfig().BUNNY_EARS.DURABILITY);
    }

    @Override
    public boolean isChoppingHatDamageEnabled() {
        return getConfig().CHOPPING_HAT.DAMAGE_ENABLED;
    }

    @Override
    public int getChoppingHatDurability() {
        return getInteger(getConfig().CHOPPING_HAT.DURABILITY);
    }

    @Override
    public double getChoppingHatSpeedWithEfficiency(int enchantmentLevel) {
        FabricConfigHelper config = getConfig();
        return getEnchantmentDouble(enchantmentLevel,
                config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_0,
                config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_1,
                config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_2,
                config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_3,
                config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_4,
                config.CHOPPING_HAT.SPEED_WITH_EFFICIENCY_5
        );
    }

    @Override
    public boolean isEnderHelmetDamageEnabled() {
        return getConfig().ENDER_HELMET.DAMAGE_ENABLED;
    }

    @Override
    public int getEnderHelmetDurability() {
        return getInteger(getConfig().ENDER_HELMET.DURABILITY);
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
        return getInteger(getConfig().HALO.DURABILITY);
    }

    @Override
    public boolean isLuckyHatDamageEnabled() {
        return getConfig().LUCKY_HAT.DAMAGE_ENABLED;
    }

    @Override
    public int getLuckyHatDurability() {
        return getInteger(getConfig().LUCKY_HAT.DURABILITY);
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
        return getInteger(getConfig().MINING_HAT.DURABILITY);
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
                config.MINING_HAT.SPEED_WITH_EFFICIENCY_0,
                config.MINING_HAT.SPEED_WITH_EFFICIENCY_1,
                config.MINING_HAT.SPEED_WITH_EFFICIENCY_2,
                config.MINING_HAT.SPEED_WITH_EFFICIENCY_3,
                config.MINING_HAT.SPEED_WITH_EFFICIENCY_4,
                config.MINING_HAT.SPEED_WITH_EFFICIENCY_5
        );
    }

    @Override
    public boolean isMushroomHatDamageEnabled() {
        return getConfig().MUSHROOM_HAT.DAMAGE_ENABLED;
    }

    @Override
    public int getMushroomHatDurability() {
        return getInteger(getConfig().MUSHROOM_HAT.DURABILITY);
    }

    @Override
    public int getMushroomHatEatInterval() {
        return getInteger(getConfig().MUSHROOM_HAT.EAT_INTERVAL);
    }

    @Override
    public boolean isPostmanHatDamageEnabled() {
        return getConfig().POSTMAN_HAT.DAMAGE_ENABLED;
    }

    @Override
    public int getPostmanHatDurability() {
        return getInteger(getConfig().POSTMAN_HAT.DURABILITY);
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
        return getInteger(getConfig().SHULKER_HELMET.DURABILITY);
    }

    @Override
    public boolean isStockingCapDamageEnabled() {
        return getConfig().STOCKING_CAP.DAMAGE_ENABLED;
    }

    @Override
    public int getStockingCapDurability() {
        return getInteger(getConfig().STOCKING_CAP.DURABILITY);
    }

    @Override
    public boolean isStrawHatDamageEnabled() {
        return getConfig().STRAW_HAT.DAMAGE_ENABLED;
    }

    @Override
    public int getStrawHatDurability() {
        return getInteger(getConfig().STRAW_HAT.DURABILITY);
    }

    @Override
    public double getStrawHatSpeedWithEfficiency(int enchantmentLevel) {
        FabricConfigHelper config = getConfig();
        return getEnchantmentDouble(enchantmentLevel,
                config.STRAW_HAT.SPEED_WITH_EFFICIENCY_0,
                config.STRAW_HAT.SPEED_WITH_EFFICIENCY_1,
                config.STRAW_HAT.SPEED_WITH_EFFICIENCY_2,
                config.STRAW_HAT.SPEED_WITH_EFFICIENCY_3,
                config.STRAW_HAT.SPEED_WITH_EFFICIENCY_4,
                config.STRAW_HAT.SPEED_WITH_EFFICIENCY_5
        );
    }

    @Override
    public boolean isWingHelmetDamageEnabled() {
        return getConfig().WING_HELMET.DAMAGE_ENABLED;
    }

    @Override
    public int getWingHelmetDurability() {
        return getInteger(getConfig().WING_HELMET.DURABILITY);
    }

    @Override
    public boolean isWingHelmetLevitationEnabled() {
        return getConfig().WING_HELMET.LEVITATION_ENABLED;
    }

}
