package cech12.usefulhats.item;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public interface IRightClickListener {

    void onRightClickItemEvent(PlayerInteractEvent.RightClickItem event, ItemStack headSlotItemStack);

}
