package cech12.usefulhats.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

public interface IItemUseListener {

    void onItemUseEvent(LivingEntityUseItemEvent event, Player player, ItemStack headSlotItemStack);

}
