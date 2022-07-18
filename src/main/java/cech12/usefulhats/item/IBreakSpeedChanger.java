package cech12.usefulhats.item;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;

public interface IBreakSpeedChanger {

    void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack);

    void onBreakEvent(BlockEvent.BreakEvent event, ItemStack headSlotItemStack);

}
