package cech12.usefulhats.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public interface ILivingDropsListener {

    void onLivingDropsEvent(LivingDropsEvent event, PlayerEntity dropReason, ItemStack headSlotItemStack);

}
