package de.cech12.usefulhats.item;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.UsefulHatsTags;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class HatArmorMaterials {

    public static final Holder<ArmorMaterial> AQUANAUT = register("aquanaut_helmet", SoundEvents.ARMOR_EQUIP_TURTLE, () -> Ingredient.of(Items.TURTLE_SCUTE));
    public static final Holder<ArmorMaterial> BUNNY = register("bunny_ears", SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.RABBIT_HIDE, Items.CARROT));
    public static final Holder<ArmorMaterial> CHOPPING = register("chopping_hat", SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.RABBIT_HIDE));
    public static final Holder<ArmorMaterial> ENDER = register("ender_helmet", SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(Items.ENDER_PEARL), false);
    public static final Holder<ArmorMaterial> HALO = register("halo", SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(Items.GLOWSTONE_DUST), false);
    public static final Holder<ArmorMaterial> LUCKY = register("lucky_hat", SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.RABBIT_FOOT));
    public static final Holder<ArmorMaterial> MINING = register("mining_hat", SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(Items.GOLD_INGOT));
    public static final Holder<ArmorMaterial> MUSHROOM = register("mushroom_hat", SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(UsefulHatsTags.Items.MUSHROOM_CAPS));
    public static final Holder<ArmorMaterial> POSTMAN = register("postman_hat", SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.LAPIS_LAZULI));
    public static final Holder<ArmorMaterial> SHULKER = register("shulker_helmet", SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(Items.SHULKER_SHELL, Items.PHANTOM_MEMBRANE), false);
    public static final Holder<ArmorMaterial> STOCKING = register("stocking_cap", SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.STRING));
    public static final Holder<ArmorMaterial> STRAW = register("straw_hat", SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.WHEAT));
    public static final Holder<ArmorMaterial> WING = register("wing_helmet", SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(Items.FEATHER, Items.PHANTOM_MEMBRANE));

    private static Holder<ArmorMaterial> register(String name, Holder<SoundEvent> equipSound, Supplier<Ingredient> repairIngredients) {
        return register(name, equipSound, repairIngredients, true);
    }

    private static Holder<ArmorMaterial> register(String name, Holder<SoundEvent> equipSound, Supplier<Ingredient> repairIngredients, boolean withOverlay) {
        EnumMap<ArmorItem.Type, Integer> defenseMap = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            defenseMap.put(type, 0);
        }
        List<ArmorMaterial.Layer> layers = new ArrayList<>();
        layers.add(new ArmorMaterial.Layer(new ResourceLocation(Constants.MOD_ID, name), "", true));
        if (withOverlay) {
            layers.add(new ArmorMaterial.Layer(new ResourceLocation(Constants.MOD_ID, name), "_overlay", false));
        }
        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, new ResourceLocation(Constants.MOD_ID, name), new ArmorMaterial(defenseMap, 15, equipSound, repairIngredients, layers, 0.0F, 0.0F));
    }

}
