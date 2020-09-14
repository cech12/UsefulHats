package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class WingHelmetItem extends AbstractHatItem implements IEquipmentChangeListener {

    private static final int SLOW_FALLING_AMPLIFIER = 0;
    private static final int SLOW_FALLING_DURATION = 219;

    private static final int LEVITATION_AMPLIFIER = 2;
    private static final int LEVITATION_DURATION = 200;

    public WingHelmetItem() {
        super("wing_helmet", HatArmorMaterial.WING, rawColorFromRGB(255, 255, 255), Config.WING_HELMET_ENABLED, Config.WING_HELMET_DAMAGE_ENABLED);
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        tooltip.add(new TranslationTextComponent("item.usefulhats.wing_helmet.desc.slow_falling").mergeStyle(TextFormatting.BLUE));
        if (Config.WING_HELMET_LEVITATION_ENABLED.getValue()) {
            tooltip.add(new TranslationTextComponent("item.usefulhats.wing_helmet.desc.scared").mergeStyle(TextFormatting.RED));
        }
    }

    private boolean isPlayerFalling(PlayerEntity player) {
        return !player.isOnGround() && player.getMotion().getY() < 0 //not on ground & motion downwards
                && !player.abilities.isFlying && !player.isElytraFlying() //not (elytra) flying
                && !player.isInWater() && !player.isInLava(); //not in fluid
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isRemote) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //Sometimes the helmet is afraid of monsters and flies away
            boolean isLevitationFromOtherSource = this.isEffectCausedByOtherSource(player, Effects.LEVITATION, LEVITATION_DURATION, LEVITATION_AMPLIFIER);
            boolean isLevitationEffectActive = player.getActivePotionEffect(Effects.LEVITATION) != null;
            if (!isLevitationEffectActive && Config.WING_HELMET_LEVITATION_ENABLED.getValue()) {
                if (player.getLastDamageSource() != null && player.getLastDamageSource().getTrueSource() instanceof LivingEntity) {
                    if (random.nextInt(100) == 0) {
                        this.removeEffect(player, Effects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
                        this.addEffect(player, Effects.LEVITATION, LEVITATION_DURATION, LEVITATION_AMPLIFIER, true);
                        isLevitationEffectActive = true;
                    }
                }
            }
            //slow falling effect
            boolean isSlowFallingFromOtherSource = this.isEffectCausedByOtherSource(player, Effects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
            boolean isSlowFallingEffectActive = player.getActivePotionEffect(Effects.SLOW_FALLING) != null;
            if (this.isPlayerFalling(player)) {
                if (!isLevitationEffectActive && !isSlowFallingFromOtherSource) {
                    //only add slow falling effect when no levitation effect an no slow falling effect is active (other sources like potions)
                    if (!isSlowFallingEffectActive || player.ticksExisted % 19 == 0) {
                        this.addEffect(player, Effects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
                        isSlowFallingEffectActive = true;
                    }
                }
            } else {
                //remove slow falling effect when on ground
                this.removeEffect(player, Effects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
            }
            //calculate damage if slow falling or levitation is caused by this item
            if ((isSlowFallingEffectActive && !isSlowFallingFromOtherSource) ||
                    (isLevitationEffectActive && !isLevitationFromOtherSource && Config.WING_HELMET_LEVITATION_ENABLED.getValue())) {
                if (random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        this.removeEffect(entity, Effects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
        if (Config.WING_HELMET_LEVITATION_ENABLED.getValue()) {
            this.removeEffect(entity, Effects.LEVITATION, LEVITATION_DURATION, LEVITATION_AMPLIFIER);
        }
    }

}
