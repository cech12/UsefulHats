package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.init.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the EnderPearlItem class to add hat event handling.
 */
@Mixin(EnderpearlItem.class)
public class EnderPearlItemMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void dropAllDeathLootProxy(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        boolean cancel = ModItems.onRightClickItemEvent(level, player, interactionHand, player.getItemInHand(interactionHand));
        if (cancel) {
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide()));
        }
    }

}
