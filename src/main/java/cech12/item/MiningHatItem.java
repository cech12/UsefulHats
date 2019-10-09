package cech12.item;

import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;

public class MiningHatItem extends ArmorItem {

    private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(UsefulHatsMod.MOD_ID, "mining_hat");

    public MiningHatItem() {
        super(HatArmorMaterial.MINING, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT));
        this.setRegistryName(REGISTRY_NAME);
    }

}
