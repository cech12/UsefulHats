package cech12.usefulhats.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public interface IVisibilityChanger {

    void onVisibilityEvent(PlayerEvent.Visibility event, ItemStack headSlotItemStack);

}
