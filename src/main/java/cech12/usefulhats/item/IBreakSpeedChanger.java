package cech12.usefulhats.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

public interface IBreakSpeedChanger {

    void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack);

    void onBreakEvent(BlockEvent.BreakEvent event, ItemStack headSlotItemStack);

}
