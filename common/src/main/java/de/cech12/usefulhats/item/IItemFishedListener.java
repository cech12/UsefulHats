package de.cech12.usefulhats.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IItemFishedListener {

    void onItemFishedListener(Player player, ItemStack headSlotItemStack);

}
