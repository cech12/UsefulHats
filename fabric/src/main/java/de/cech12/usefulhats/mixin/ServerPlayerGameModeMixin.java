package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.item.IBreakSpeedChanger;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the ServerPlayerGameMode class to add hat event handling.
 */
@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

    @Shadow
    protected ServerLevel level;

    @Final
    @Shadow
    protected ServerPlayer player;

    @Inject(at = @At("HEAD"), method = "destroyBlock")
    public void destroyBlockProxy(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = level.getBlockState(blockPos);
        for (ItemStack headSlotItemStack : Services.REGISTRY.getEquippedHatItemStacks(player)) {
            for (Item item : ModItems.ALL_HATS) {
                if (item instanceof IBreakSpeedChanger breakSpeedChanger && headSlotItemStack.getItem() == item) {
                    breakSpeedChanger.onBreakEvent(player, state, headSlotItemStack);
                }
            }
        }
    }

}
