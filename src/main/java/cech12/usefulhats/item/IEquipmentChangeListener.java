package cech12.usefulhats.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface IEquipmentChangeListener {

    //void onEquippedHatItem(LivingEntity entity, ItemStack newStack);

    void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack);

}
