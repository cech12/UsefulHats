package cech12.usefulhats.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IEquipmentChangeListener {

    /**
     * Method that is called when the item was equipped.
     * @param entity involved entity
     * @param newStack equipped ItemStack. Some APIs (like Curios or Baubles) does not support the stack. For these this is ItemStack.EMPTY!
     */
    default void onEquippedHatItem(LivingEntity entity, ItemStack newStack) {
    }

    /**
     * Method that is called when the item was unequipped.
     * @param entity involved entity
     * @param oldStack unequipped ItemStack. Some APIs (like Curios or Baubles) does not support the stack. For these this is ItemStack.EMPTY!
     */
    default void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
    }

}
