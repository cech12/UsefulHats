package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public abstract class AbstractHatItem extends ArmorItem implements IDyeableArmorItem {

    private final ResourceLocation resourceLocation;
    private final int initColor;

    private ArrayList<Enchantment> allowedEnchantments = new ArrayList<>();
    private ArrayList<Enchantment> allowedAdditionalBookEnchantments = new ArrayList<>();

    public AbstractHatItem(String name, HatArmorMaterial material, int initColor) {
        super(material, EquipmentSlotType.HEAD, (new Properties()).group(ItemGroup.COMBAT));
        this.resourceLocation = new ResourceLocation(UsefulHatsMod.MOD_ID, name);
        this.setRegistryName(this.resourceLocation);
        this.initColor = initColor;
        this.allowedEnchantments.add(Enchantments.MENDING);
        this.allowedEnchantments.add(Enchantments.UNBREAKING);
        this.allowedAdditionalBookEnchantments.add(Enchantments.BINDING_CURSE);
        this.allowedAdditionalBookEnchantments.add(Enchantments.VANISHING_CURSE);
    }

    protected static int rawColorFromRGB(int red, int green, int blue) {
        int rgb = Math.max(Math.min(0xFF, red), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, green), 0);
        rgb = (rgb << 8) + Math.max(Math.min(0xFF, blue), 0);
        return rgb;
    }

    /**
     * Add an allowed enchantment to this item.
     * Standard allowed enchantments are MENDING and UNBREAKING.
     * @param enchantment enchantment to add
     */
    protected void addAllowedEnchantment(Enchantment enchantment) {
        this.allowedEnchantments.add(enchantment);
    }

    /**
     * Add an allowed additional book enchantment to this item.
     * Standard allowed additional book enchantments are BINDING_CURSE and VANISHING_CURSE.
     * @param enchantment enchantment to add
     */
    protected void addAllowedAdditionalBookEnchantment(Enchantment enchantment) {
        this.allowedAdditionalBookEnchantments.add(enchantment);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : this.initColor;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        ArrayList<Enchantment> allowedE = new ArrayList<>(allowedEnchantments);
        allowedE.addAll(allowedAdditionalBookEnchantments);
        for (Enchantment enchantment : EnchantmentHelper.getEnchantments(book).keySet()) {
            if (!allowedE.contains(enchantment)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return this.allowedEnchantments.contains(enchantment);
    }
}
