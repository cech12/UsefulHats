package de.cech12.usefulhats.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IItemUseListener {

    int onItemUseEventStart(Player player, ItemStack usedStack, int actualDuration, ItemStack headSlotItemStack);

}
