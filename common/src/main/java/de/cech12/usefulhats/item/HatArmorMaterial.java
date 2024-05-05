package de.cech12.usefulhats.item;

import de.cech12.usefulhats.UsefulHatsTags;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum HatArmorMaterial implements ArmorMaterial {

    AQUANAUT("usefulhats:aquanaut_helmet", Services.CONFIG::getAquanautHelmetDurability, 15, SoundEvents.ARMOR_EQUIP_TURTLE, () -> Ingredient.of(Items.SCUTE)),
    BUNNY("usefulhats:bunny_ears", Services.CONFIG::getBunnyEarsDurability, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.RABBIT_HIDE, Items.CARROT)),
    CHOPPING("usefulhats:chopping", Services.CONFIG::getChoppingHatDurability, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.RABBIT_HIDE)),
    ENDER("usefulhats:ender", Services.CONFIG::getEnderHelmetDurability, 15, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(Items.ENDER_PEARL)),
    HALO("usefulhats:halo", Services.CONFIG::getHaloDurability, 15, SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(Items.GLOWSTONE_DUST)),
    LUCKY("usefulhats:lucky_hat", Services.CONFIG::getLuckyHatDurability, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.RABBIT_FOOT)),
    MINING("usefulhats:mining_hat", Services.CONFIG::getMiningHatDurability, 15, SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(Items.GOLD_INGOT)),
    MUSHROOM("usefulhats:mushroom_hat", Services.CONFIG::getMushroomHatDurability, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(UsefulHatsTags.Items.MUSHROOM_CAPS)),
    POSTMAN("usefulhats:postman_hat", Services.CONFIG::getPostmanHatDurability, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.LAPIS_LAZULI)),
    SHULKER("usefulhats:shulker", Services.CONFIG::getShulkerHelmetDurability, 15, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(Items.SHULKER_SHELL, Items.PHANTOM_MEMBRANE)),
    STOCKING("usefulhats:stocking_cap", Services.CONFIG::getStockingCapDurability, 25, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.STRING)),
    STRAW("usefulhats:straw_hat", Services.CONFIG::getStrawHatDurability, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.WHEAT)),
    WING("usefulhats:wing", Services.CONFIG::getWingHelmetDurability, 15, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(Items.FEATHER, Items.PHANTOM_MEMBRANE));

    private final String name;
    private final Supplier<Integer> durabilityConfig;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final Supplier<Ingredient> repairMaterial;

    HatArmorMaterial(String nameIn, Supplier<Integer> durabilityConfig, int enchantabilityIn, SoundEvent equipSoundIn, Supplier<Ingredient> repairMaterialSupplier) {
        this.name = nameIn;
        this.durabilityConfig = durabilityConfig;
        this.enchantability = enchantabilityIn;
        this.soundEvent = equipSoundIn;
        this.repairMaterial = repairMaterialSupplier;
    }

    @Override
    public int getDurabilityForType(@Nonnull ArmorItem.Type type) {
        return this.durabilityConfig.get();
    }

    @Override
    public int getDefenseForType(@Nonnull ArmorItem.Type type) {
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
