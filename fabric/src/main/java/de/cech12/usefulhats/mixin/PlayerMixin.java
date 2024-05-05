package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.item.IBreakSpeedChanger;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the Player class to add hat event handling.
 */
@Mixin(Player.class)
public class PlayerMixin {

    @Inject(at = @At("RETURN"), method = "getDestroySpeed", cancellable = true)
    public void getDestroySpeedProxy(BlockState state, CallbackInfoReturnable<Float> cir) {
        Player self = (Player) (Object) this;
        for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(self)) {
            for (Item item : ModItems.ALL_HATS) {
                if (item instanceof IBreakSpeedChanger breakSpeedChanger && headSlotItemStack.getItem() == item) {
                    Float newSpeed = breakSpeedChanger.onBreakSpeedEvent(self, state, cir.getReturnValue(), headSlotItemStack);
                    if (newSpeed != null) {
                        cir.setReturnValue(newSpeed);
                    }
                }
            }
        }
    }

}
