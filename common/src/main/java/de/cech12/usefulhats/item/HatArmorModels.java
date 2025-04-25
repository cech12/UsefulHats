package de.cech12.usefulhats.item;

import de.cech12.usefulhats.Constants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class HatArmorModels {

    public static final ResourceKey<EquipmentAsset> AQUANAUT = createId("aquanaut_helmet");
    public static final ResourceKey<EquipmentAsset> BUNNY = createId("bunny_ears");
    public static final ResourceKey<EquipmentAsset> CHOPPING = createId("chopping_hat");
    public static final ResourceKey<EquipmentAsset> ENDER = createId("ender_helmet");
    public static final ResourceKey<EquipmentAsset> HALO = createId("halo");
    public static final ResourceKey<EquipmentAsset> LUCKY = createId("lucky_hat");
    public static final ResourceKey<EquipmentAsset> MINING = createId("mining_hat");
    public static final ResourceKey<EquipmentAsset> MUSHROOM = createId("mushroom_hat");
    public static final ResourceKey<EquipmentAsset> POSTMAN = createId("postman_hat");
    public static final ResourceKey<EquipmentAsset> SHULKER = createId("shulker_helmet");
    public static final ResourceKey<EquipmentAsset> STOCKING = createId("stocking_cap");
    public static final ResourceKey<EquipmentAsset> STOCKING_XMAS = createId("stocking_cap_xmas");
    public static final ResourceKey<EquipmentAsset> STRAW = createId("straw_hat");
    public static final ResourceKey<EquipmentAsset> WING = createId("wing_helmet");

    private static ResourceKey<EquipmentAsset> createId(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, Constants.id(name));
    }

}
