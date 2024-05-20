package de.cech12.usefulhats.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ILivingDropsListener {

    void onLivingDropsEvent(LivingEntity dropReason, ItemStack headSlotItemStack);

}
