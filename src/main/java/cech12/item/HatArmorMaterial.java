package cech12.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum HatArmorMaterial implements IArmorMaterial {

    MINING("usefulhats:mining", 7, 2, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, () -> {
        return Ingredient.fromItems(Items.GOLD_INGOT);
    }),
    STOCKING("usefulhats:stocking", 5, 1, 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> {
        return Ingredient.fromItems(Items.STRING);
    });

    private final String name;
    private final int maxDamageFactor;
    private final int damageReductionAmount;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final LazyLoadBase<Ingredient> repairMaterial;

    HatArmorMaterial(String nameIn, int maxDamageFactorIn, int damageReductionAmountIn, int enchantabilityIn, SoundEvent equipSoundIn, Supplier<Ingredient> repairMaterialSupplier) {
        this.name = nameIn;
        this.maxDamageFactor = maxDamageFactorIn;
        this.damageReductionAmount = damageReductionAmountIn;
        this.enchantability = enchantabilityIn;
        this.soundEvent = equipSoundIn;
        this.repairMaterial = new LazyLoadBase<>(repairMaterialSupplier);
    }

    public int getDurability(EquipmentSlotType slotIn) {
        return 11 * this.maxDamageFactor;
    }

    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.damageReductionAmount;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return 0.0F;
    }

}
