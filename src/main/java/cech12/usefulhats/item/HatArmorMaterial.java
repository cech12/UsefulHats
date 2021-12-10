package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsTags;
import cech12.usefulhats.config.ServerConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum HatArmorMaterial implements ArmorMaterial {

    AQUANAUT("usefulhats:blank", ServerConfig.AQUANAUT_HELMET_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_TURTLE, () -> Ingredient.of(Items.SCUTE)),
    BUNNY("usefulhats:blank", ServerConfig.BUNNY_EARS_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.RABBIT_HIDE, Items.CARROT)),
    CHOPPING("usefulhats:chopping", ServerConfig.CHOPPING_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.RABBIT_HIDE)),
    ENDER("usefulhats:ender", ServerConfig.ENDER_HELMET_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(Items.ENDER_PEARL)),
    HALO("usefulhats:halo", ServerConfig.HALO_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(Items.GLOWSTONE_DUST)),
    LUCKY("usefulhats:blank", ServerConfig.LUCKY_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.RABBIT_FOOT)),
    MINING("usefulhats:blank", ServerConfig.MINING_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(Items.GOLD_INGOT)),
    MUSHROOM("usefulhats:blank", ServerConfig.MUSHROOM_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(UsefulHatsTags.Items.MUSHROOM_CAPS)),
    POSTMAN("usefulhats:blank", ServerConfig.POSTMAN_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.LAPIS_LAZULI)),
    SHULKER("usefulhats:shulker", ServerConfig.SHULKER_HELMET_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(Items.SHULKER_SHELL, Items.PHANTOM_MEMBRANE)),
    STOCKING("usefulhats:blank", ServerConfig.STOCKING_CAP_DURABILITY, 25, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.STRING)),
    STRAW("usefulhats:blank", ServerConfig.STRAW_HAT_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.WHEAT)),
    WING("usefulhats:wing", ServerConfig.WING_HELMET_DURABILITY, 15, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(Items.FEATHER, Items.PHANTOM_MEMBRANE));

    private final String name;
    private final ForgeConfigSpec.IntValue durabilityConfig;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final Supplier<Ingredient> repairMaterial;

    HatArmorMaterial(String nameIn, ForgeConfigSpec.IntValue durabilityConfig, int enchantabilityIn, SoundEvent equipSoundIn, Supplier<Ingredient> repairMaterialSupplier) {
        this.name = nameIn;
        this.durabilityConfig = durabilityConfig;
        this.enchantability = enchantabilityIn;
        this.soundEvent = equipSoundIn;
        this.repairMaterial = repairMaterialSupplier;
    }

    @Override
    public int getDurabilityForSlot(@Nonnull EquipmentSlot slotIn) {
        return this.durabilityConfig.get();
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
