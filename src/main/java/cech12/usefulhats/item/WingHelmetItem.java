package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        this.addTextLineToTooltip(tooltip, new TranslationTextComponent("item.usefulhats.wing_helmet.desc.slow_falling").withStyle(TextFormatting.BLUE));
        if (Config.WING_HELMET_LEVITATION_ENABLED.getValue()) {
            this.addTextLineToTooltip(tooltip, new TranslationTextComponent("item.usefulhats.wing_helmet.desc.scared").withStyle(TextFormatting.RED));
        }
    }

    private boolean isPlayerFalling(PlayerEntity player) {
        return !player.isOnGround() && player.getDeltaMovement().y() < 0 //not on ground & motion downwards
                && !player.abilities.flying && !player.isFallFlying() //not (elytra) flying
                && !player.isInWater() && !player.isInLava(); //not in fluid
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClientSide) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //Sometimes the helmet is afraid of monsters and flies away
            boolean isLevitationFromOtherSource = this.isEffectCausedByOtherSource(player, Effects.LEVITATION, LEVITATION_DURATION, LEVITATION_AMPLIFIER);
            boolean isLevitationEffectActive = player.getEffect(Effects.LEVITATION) != null;
            if (!isLevitationEffectActive && Config.WING_HELMET_LEVITATION_ENABLED.getValue()) {
                if (player.getLastDamageSource() != null && player.getLastDamageSource().getEntity() instanceof LivingEntity) {
                    if (random.nextInt(100) == 0) {
                        this.removeEffect(player, Effects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
                        this.addEffect(player, Effects.LEVITATION, LEVITATION_DURATION, LEVITATION_AMPLIFIER, true);
                        isLevitationEffectActive = true;
                    }
                }
            }
            //slow falling effect
            boolean isSlowFallingFromOtherSource = this.isEffectCausedByOtherSource(player, Effects.SLOW_FALLING, SLOW_FALLING_DURATION, SLOW_FALLING_AMPLIFIER);
            boolean isSlowFallingEffectActive = player.getEffect(Effects.SLOW_FALLING) != null;
            if (this.isPlayerFalling(player)) {
                if (!isLevitationEffectActive && !isSlowFallingFromOtherSource) {
                    //only add slow falling effect when no levitation effect an no slow falling effect is active (other sources like potions)
                    if (!isSlowFallingEffectActive || player.tickCount % 19 == 0) {
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
