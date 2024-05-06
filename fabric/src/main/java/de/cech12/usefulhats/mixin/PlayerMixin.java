package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.UsefulHatsEventUtils;
import net.minecraft.world.entity.player.Player;
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
        cir.setReturnValue(UsefulHatsEventUtils.onBreakSpeedCalculation((Player) (Object) this, state, cir.getReturnValue()));
    }

}
