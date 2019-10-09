package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractHatItem extends ArmorItem implements IDyeableArmorItem {

    private final ResourceLocation resourceLocation;
    private final int initColor;

    public AbstractHatItem(String name, HatArmorMaterial material, int initColor) {
        super(material, EquipmentSlotType.HEAD, (new Properties()).group(ItemGroup.COMBAT));
        this.resourceLocation = new ResourceLocation(UsefulHatsMod.MOD_ID, name);
        this.setRegistryName(this.resourceLocation);
        this.initColor = initColor;
    }

    protected static int rawColorFromRGB(int red, int green, int blue) {
        int rgb = Math.max(Math.min(0xFF, red), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, green), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, blue), 0);
        return rgb;
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : this.initColor;
    }

}
