package cech12.item;

import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class StockingCapItem extends ArmorItem {

    private static final ResourceLocation REGISTRY_NAME = new ResourceLocation(UsefulHatsMod.MOD_ID, "stocking_cap");

    public StockingCapItem() {
        super(HatArmorMaterial.STOCKING, EquipmentSlotType.HEAD, (new Properties()).group(ItemGroup.COMBAT));
        this.setRegistryName(REGISTRY_NAME);
    }

}
