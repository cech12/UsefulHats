package de.cech12.usefulhats.mixin;

import de.cech12.usefulhats.UsefulHatsEventUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the LivingEntity class to add hat event handling.
 */
@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Shadow
    protected int useItemRemaining;

    @Inject(method = "dropAllDeathLoot", at = @At("RETURN"))
    public void dropAllDeathLootProxy(ServerLevel serverLevel, DamageSource damageSource, CallbackInfo ci) {
        UsefulHatsEventUtils.onLivingDiesBecauseOf(damageSource.getEntity());
    }

    @Inject(method = "startUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseDuration(Lnet/minecraft/world/entity/LivingEntity;)I", shift = At.Shift.BY, by = 2))
    public void startUsingItemProxy(InteractionHand interactionHand, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        useItemRemaining = UsefulHatsEventUtils.onLivingStartsUsingItem(entity, entity.getItemInHand(interactionHand), useItemRemaining);
    }

    @Inject(method = "equipmentHasChanged", at = @At("RETURN"))
    public void equipmentHasChangedProxy(ItemStack fromItemStack, ItemStack toItemStack, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue().equals(Boolean.TRUE)) {
            UsefulHatsEventUtils.onUnequip((LivingEntity) (Object) this, fromItemStack);
            UsefulHatsEventUtils.onEquip((LivingEntity) (Object) this, toItemStack);
        }
    }

    @Inject(method = "jumpFromGround", at = @At("RETURN"))
    public void jumpFromGroundProxy(CallbackInfo ci) {
        UsefulHatsEventUtils.onLivingJump((LivingEntity) (Object) this);
    }

}
