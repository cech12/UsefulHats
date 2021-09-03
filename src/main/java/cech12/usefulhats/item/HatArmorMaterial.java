package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsTags;
import cech12.usefulhats.config.Config;
import cech12.usefulhats.config.ConfigType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum HatArmorMaterial implements ArmorMaterial {

    AQUANAUT("usefulhats:blank", Config.AQUANAUT_HELMET_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_TURTLE, () -> {
        return Ingredient.of(Items.SCUTE);
    }),
    BUNNY("usefulhats:blank", Config.BUNNY_EARS_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.of(Items.RABBIT_HIDE, Items.CARROT);
    }),
    CHOPPING("usefulhats:chopping", Config.CHOPPING_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.of(Items.RABBIT_HIDE);
    }),
    ENDER("usefulhats:ender", Config.ENDER_HELMET_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_IRON, () -> {
        return Ingredient.of(Items.ENDER_PEARL);
    }),
    HALO("usefulhats:halo", Config.HALO_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_GOLD, () -> {
        return Ingredient.of(Items.GLOWSTONE_DUST);
    }),
    LUCKY("usefulhats:blank", Config.LUCKY_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.of(Items.RABBIT_FOOT);
    }),
    MINING("usefulhats:blank", Config.MINING_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_GOLD, () -> {
        return Ingredient.of(Items.GOLD_INGOT);
    }),
    MUSHROOM("usefulhats:blank", Config.MUSHROOM_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.of(UsefulHatsTags.Items.MUSHROOM_CAPS);
    }),
    POSTMAN("usefulhats:blank", Config.POSTMAN_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.of(Items.LAPIS_LAZULI);
    }),
    SHULKER("usefulhats:shulker", Config.SHULKER_HELMET_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_IRON, () -> {
        return Ingredient.of(Items.SHULKER_SHELL, Items.PHANTOM_MEMBRANE);
    }),
    STOCKING("usefulhats:blank", Config.STOCKING_CAP_DURABILITY, 25, SoundEvents.ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.of(Items.STRING);
    }),
    STRAW("usefulhats:blank", Config.STRAW_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.of(Items.WHEAT);
    }),
    WING("usefulhats:wing", Config.WING_HELMET_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_IRON, () -> {
        return Ingredient.of(Items.FEATHER, Items.PHANTOM_MEMBRANE);
    });

    private final String name;
    private final ConfigType.Integer durabilityConfig;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final Supplier<Ingredient> repairMaterial;

    HatArmorMaterial(String nameIn, ConfigType.Integer durabilityConfig, int enchantabilityIn, SoundEvent equipSoundIn, Supplier<Ingredient> repairMaterialSupplier) {
        this.name = nameIn;
        this.durabilityConfig = durabilityConfig;
        this.enchantability = enchantabilityIn;
        this.soundEvent = equipSoundIn;
        this.repairMaterial = repairMaterialSupplier;
    }

    @Override
    public int getDurabilityForSlot(@Nonnull EquipmentSlot slotIn) {
        return this.durabilityConfig.getValue();
    }

    @Override
    public int getDefenseForSlot(@Nonnull EquipmentSlot slotIn) {
        return 0;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    @Nonnull
    public SoundEvent getEquipSound() {
        return this.soundEvent;
    }

    @Override
    @Nonnull
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    @Override
    @Nonnull
    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return 0.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }

}
