package de.cech12.usefulhats.item;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class WingHelmetItem extends AbstractHatItem implements IEquipmentChangeListener {

    private static final int SLOW_FALLING_AMPLIFIER = 0;
    private static final int SLOW_FALLING_DURATION = 219;

    private static final int LEVITATION_AMPLIFIER = 2;
    private static final int LEVITATION_DURATION = 200;

    public WingHelmetItem() {
        super(HatArmorMaterial.WING, rawColorFromRGB(255, 255, 255), Services.CONFIG::isWingHelmetDamageEnabled);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("item.usefulhats.wing_helmet.desc.slow_falling").withStyle(ChatFormatting.BLUE));
        if (Services.CONFIG.isWingHelmetLevitationEnabled()) {
            tooltip.add(Component.translatable("item.usefulhats.wing_helmet.desc.scared").withStyle(ChatFormatting.RED));
        }
    }

    private boolean isPlayerFalling(Player player) {
        return !player.onGround() && player.getDeltaMovement().y() < 0 //not on ground & motion downwards
                && !player.getAbilities().flying && !player.isFallFlying() //not (elytra) flying
                && !player.isInWater() && !player.isInLava(); //not in fluid
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int slot, boolean selectedIndex) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //Sometimes the helmet is afraid of monsters and flies away
            boolean isLevitationFromOtherSource = this.isEffectCausedByOtherSource(player, MobEffects.LEVITATION, LEVITATION_DURATION, LEVITATION_AMPLIFIER);
            boolean isLevitationEffectActive = player.getEffect(MobEffects.LEVITATION) != null;
            if (!isLevitationEffectActive && Services.CONFIG.isWingHelmetLevitationEnabled()) {
                if (player.getLastDamageSource() != null && player.getLastDamageSource().getEntity() instanceof LivingEntity) {
                    if (level.random.nextInt(100) == 0) {
                        this.removeEffect(player, MobEffects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
                        this.addEffect(player, MobEffects.LEVITATION, LEVITATION_DURATION, LEVITATION_AMPLIFIER, true);
                        isLevitationEffectActive = true;
                    }
                }
            }
            //slow falling effect
            boolean isSlowFallingFromOtherSource = this.isEffectCausedByOtherSource(player, MobEffects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
            boolean isSlowFallingEffectActive = player.getEffect(MobEffects.SLOW_FALLING) != null;
            if (this.isPlayerFalling(player)) {
                if (!isLevitationEffectActive && !isSlowFallingFromOtherSource) {
                    //only add slow falling effect when no levitation effect an no slow falling effect is active (other sources like potions)
                    if (!isSlowFallingEffectActive || player.tickCount % 19 == 0) {
                        this.addEffect(player, MobEffects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
                        isSlowFallingEffectActive = true;
                    }
                }
            } else {
                //remove slow falling effect when on ground
                this.removeEffect(player, MobEffects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
            }
            //calculate damage if slow falling or levitation is caused by this item
            if ((isSlowFallingEffectActive && !isSlowFallingFromOtherSource) ||
                    (isLevitationEffectActive && !isLevitationFromOtherSource && Services.CONFIG.isWingHelmetLevitationEnabled())) {
                if (level.random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        this.removeEffect(entity, MobEffects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
        if (Services.CONFIG.isWingHelmetLevitationEnabled()) {
            this.removeEffect(entity, MobEffects.LEVITATION, LEVITATION_DURATION, LEVITATION_AMPLIFIER);
        }
    }

}
