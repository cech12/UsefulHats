package de.cech12.usefulhats.item;

import de.cech12.usefulhats.UsefulHatsTags;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.Calendar;
import java.util.EnumMap;

public class HatArmorMaterials {

    private static final boolean IS_CHRISTMAS = Calendar.getInstance().get(Calendar.MONTH) + 1 == 12;

    public static final ArmorMaterial AQUANAUT = register(SoundEvents.ARMOR_EQUIP_TURTLE, UsefulHatsTags.Items.REPAIRS_AQUANAUT_HELMET, HatArmorModels.AQUANAUT);
    public static final ArmorMaterial BUNNY = register(SoundEvents.ARMOR_EQUIP_LEATHER, UsefulHatsTags.Items.REPAIRS_BUNNY_EARS, HatArmorModels.BUNNY);
    public static final ArmorMaterial CHOPPING = register(SoundEvents.ARMOR_EQUIP_LEATHER, UsefulHatsTags.Items.REPAIRS_CHOPPING_HAT, HatArmorModels.CHOPPING);
    public static final ArmorMaterial ENDER = register(SoundEvents.ARMOR_EQUIP_IRON, UsefulHatsTags.Items.REPAIRS_ENDER_HELMET, HatArmorModels.ENDER);
    public static final ArmorMaterial HALO = register(SoundEvents.ARMOR_EQUIP_GOLD, UsefulHatsTags.Items.REPAIRS_HALO, HatArmorModels.HALO);
    public static final ArmorMaterial LUCKY = register(SoundEvents.ARMOR_EQUIP_LEATHER, UsefulHatsTags.Items.REPAIRS_LUCKY_HAT, HatArmorModels.LUCKY);
    public static final ArmorMaterial MINING = register(SoundEvents.ARMOR_EQUIP_GOLD, UsefulHatsTags.Items.REPAIRS_MINING_HELMET, HatArmorModels.MINING);
    public static final ArmorMaterial MUSHROOM = register(SoundEvents.ARMOR_EQUIP_LEATHER, UsefulHatsTags.Items.REPAIRS_MUSHROOM_HAT, HatArmorModels.MUSHROOM);
    public static final ArmorMaterial POSTMAN = register(SoundEvents.ARMOR_EQUIP_LEATHER, UsefulHatsTags.Items.REPAIRS_POSTMAN_HAT, HatArmorModels.POSTMAN);
    public static final ArmorMaterial SHULKER = register(SoundEvents.ARMOR_EQUIP_IRON, UsefulHatsTags.Items.REPAIRS_SHULKER_HELMET, HatArmorModels.SHULKER);
    public static final ArmorMaterial STOCKING = register(SoundEvents.ARMOR_EQUIP_LEATHER, UsefulHatsTags.Items.REPAIRS_STOCKING_CAP, IS_CHRISTMAS ? HatArmorModels.STOCKING_XMAS : HatArmorModels.STOCKING);
    public static final ArmorMaterial STRAW = register(SoundEvents.ARMOR_EQUIP_LEATHER, UsefulHatsTags.Items.REPAIRS_STRAW_HAT, HatArmorModels.STRAW);
    public static final ArmorMaterial WING = register(SoundEvents.ARMOR_EQUIP_IRON, UsefulHatsTags.Items.REPAIRS_WING_HELMET, HatArmorModels.WING);

    private static ArmorMaterial register(Holder<SoundEvent> equipSound, TagKey<Item> repairIngredient, ResourceLocation equipmentModel) {
        return new ArmorMaterial(5, new EnumMap<>(ArmorType.class) {{ put(ArmorType.HELMET, 0); }}, 15, equipSound, 0.0F, 0.0F, repairIngredient, equipmentModel);
    }

}
