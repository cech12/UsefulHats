package de.cech12.usefulhats.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ILivingJumpListener {

    void onLivingJumpEvent(LivingEntity jumpingEntity, ItemStack headSlotItemStack);

}
