package cech12.usefulhats.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum HatArmorMaterial implements IArmorMaterial {

    AQUANAUT("usefulhats:aquanaut", 600, 15, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, () -> {
        return Ingredient.fromItems(Items.SCUTE);
    }),
    CHOPPING("usefulhats:chopping", 300, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.RABBIT_HIDE);
    }),
    HALO("usefulhats:halo", 600, 15, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, () -> {
        return Ingredient.fromItems(Items.GLOWSTONE_DUST);
    }),
    LUCKY("usefulhats:blank", 300, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.GLOWSTONE_DUST);
    }),
    MINING("usefulhats:blank", 450, 15, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, () -> {
        return Ingredient.fromItems(Items.GOLD_INGOT);
    }),
    POSTMAN("usefulhats:blank", 600, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.LAPIS_LAZULI);
    }),
    STOCKING("usefulhats:stocking", 600, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.STRING);
    }),
    STRAW("usefulhats:blank", 300, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.WHEAT);
    }),
    WING("usefulhats:wing", 600, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> {
        return Ingredient.fromItems(Items.FEATHER);
    });

    private final String name;
    private final int durability;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final Supplier<Ingredient> repairMaterial;

    HatArmorMaterial(String nameIn, int durability, int enchantabilityIn, SoundEvent equipSoundIn, Supplier<Ingredient> repairMaterialSupplier) {
        this.name = nameIn;
        this.durability = durability;
        this.enchantability = enchantabilityIn;
        this.soundEvent = equipSoundIn;
        this.repairMaterial = repairMaterialSupplier;
    }

    public int getDurability(EquipmentSlotType slotIn) {
        return this.durability;
    }

    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return 0;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.get();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return 0.0F;
    }

}
