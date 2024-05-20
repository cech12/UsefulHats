package de.cech12.usefulhats.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IItemUseListener {

    int onItemUseEventStart(LivingEntity entity, ItemStack usedStack, int actualDuration, ItemStack headSlotItemStack);

}
