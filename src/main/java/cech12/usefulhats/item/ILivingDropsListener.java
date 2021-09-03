package cech12.usefulhats.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public interface ILivingDropsListener {

    void onLivingDropsEvent(LivingDropsEvent event, Player dropReason, ItemStack headSlotItemStack);

}
