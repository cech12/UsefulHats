package cech12.usefulhats.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemFishedEvent;

public interface IItemFishedListener {

    void onItemFishedListener(ItemFishedEvent event, ItemStack headSlotItemStack);

}
