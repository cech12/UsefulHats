package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsTags;
import cech12.usefulhats.config.Config;
import cech12.usefulhats.config.ConfigType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public enum HatArmorMaterial implements IArmorMaterial {

    AQUANAUT("usefulhats:aquanaut", Config.AQUANAUT_HELMET_DURABILITY, 15, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, () -> {
        return Ingredient.fromItems(Items.SCUTE);
    }),
    BUNNY("usefulhats:blank", Config.BUNNY_EARS_DURABILITY, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.RABBIT_HIDE, Items.CARROT);
    }),
    CHOPPING("usefulhats:chopping", Config.CHOPPING_HAT_DURABILITY, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.RABBIT_HIDE);
    }),
    HALO("usefulhats:halo", Config.HALO_DURABILITY, 15, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, () -> {
        return Ingredient.fromItems(Items.GLOWSTONE_DUST);
    }),
    LUCKY("usefulhats:blank", Config.LUCKY_HAT_DURABILITY, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.RABBIT_FOOT);
    }),
    MINING("usefulhats:blank", Config.MINING_HAT_DURABILITY, 15, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, () -> {
        return Ingredient.fromItems(Items.GOLD_INGOT);
    }),
    MUSHROOM("usefulhats:blank", Config.MUSHROOM_HAT_DURABILITY, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromTag(UsefulHatsTags.Items.MUSHROOM_CAPS);
    }),
    POSTMAN("usefulhats:blank", Config.POSTMAN_HAT_DURABILITY, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.LAPIS_LAZULI);
    }),
    SHULKER("usefulhats:shulker", Config.SHULKER_HELMET_DURABILITY, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> {
        return Ingredient.fromItems(Items.SHULKER_SHELL, Items.PHANTOM_MEMBRANE);
    }),
    STOCKING("usefulhats:stocking", Config.STOCKING_CAP_DURABILITY, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.STRING);
    }),
    STRAW("usefulhats:blank", Config.STRAW_HAT_DURABILITY, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.WHEAT);
    }),
    WING("usefulhats:wing", Config.WING_HELMET_DURABILITY, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> {
        return Ingredient.fromItems(Items.FEATHER, Items.PHANTOM_MEMBRANE);
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
    public int getDurability(@Nonnull EquipmentSlotType slotIn) {
        return this.durabilityConfig.getValue();
    }

    @Override
    public int getDamageReductionAmount(@Nonnull EquipmentSlotType slotIn) {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    @Nonnull
    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    @Nonnull
    public Ingredient getRepairMaterial() {
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

}
