package de.cech12.usefulhats.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public interface IBreakSpeedChanger {

    float onBreakSpeedEvent(Player player, BlockState blockState, float actualSpeed, ItemStack headSlotItemStack);

    void onBreakEvent(Player entity, BlockState blockState, ItemStack headSlotItemStack);

}
