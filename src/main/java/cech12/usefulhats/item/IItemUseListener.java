package cech12.usefulhats.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

public interface IItemUseListener {

    void onItemUseEvent(LivingEntityUseItemEvent event, PlayerEntity player, ItemStack headSlotItemStack);

}
