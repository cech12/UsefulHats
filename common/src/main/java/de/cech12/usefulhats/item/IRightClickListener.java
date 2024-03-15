package de.cech12.usefulhats.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IRightClickListener {

    boolean onRightClickItemEvent(Level level, Player player, ItemStack usedStack, InteractionHand hand, ItemStack headSlotItemStack);

}
