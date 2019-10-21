package cech12.usefulhats.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class WingHelmetItem extends AbstractHatItem {

    public WingHelmetItem() {
        super("wing_helmet", HatArmorMaterial.WING, rawColorFromRGB(255, 255, 255));
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        tooltip.add(new StringTextComponent("While falling you get " + Effects.SLOW_FALLING.getDisplayName().getFormattedText() + " effect.").applyTextStyle(TextFormatting.BLUE));
        tooltip.add(new StringTextComponent("Sometimes the helmet is afraid of monsters and flies away.").applyTextStyle(TextFormatting.RED));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!player.onGround && !player.isInWater()) {
            //only add slow falling effect when no levitation effect an no slow falling effect is active (other sources like potions)
            if (player.getActivePotionEffect(Effects.LEVITATION) == null && player.getActivePotionEffect(Effects.SLOW_FALLING) == null) {
                player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING));
                if (random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            }
            //Sometimes the helmet is afraid of monsters and flies away
            if (player.getLastDamageSource() != null && player.getLastDamageSource().getTrueSource() instanceof LivingEntity) {
                if (random.nextInt(100) == 0) {
                    player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 200, 2));
                }
            }
        }
    }

    @Override
    protected void onItemRemoved(ItemStack stack, PlayerEntity player) {
        EffectInstance levitation = player.getActivePotionEffect(Effects.LEVITATION);
        if (levitation != null && levitation.getDuration() <= 200) {
            player.removePotionEffect(Effects.LEVITATION);
        }
    }
}
