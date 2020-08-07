package cech12.usefulhats.item;

import cech12.usefulhats.config.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;

import java.util.List;

public class WingHelmetItem extends AbstractHatItem implements IEquipmentChangeListener {

    private static final int LEVITATION_AMPLIFIER = 2;
    private static final int LEVITATION_DURATION = 200;

    public WingHelmetItem() {
        super("wing_helmet", HatArmorMaterial.WING, rawColorFromRGB(255, 255, 255), Config.WING_HELMET_ENABLED, Config.WING_HELMET_DAMAGE_ENABLED);
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        //tooltip.add(new TranslationTextComponent("item.usefulhats.wing_helmet.desc.slow_falling").applyTextStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("item.usefulhats.wing_helmet.desc.slow_falling").func_240699_a_(TextFormatting.BLUE));
        if (Config.WING_HELMET_LEVITATION_ENABLED.getValue()) {
            //tooltip.add(new TranslationTextComponent("item.usefulhats.wing_helmet.desc.scared").applyTextStyle(TextFormatting.RED));
            tooltip.add(new TranslationTextComponent("item.usefulhats.wing_helmet.desc.scared").func_240699_a_(TextFormatting.RED));
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!player.func_233570_aj_() && !player.isInWater()) { //onGround - func_233570_aj_
            //only add slow falling effect when no levitation effect an no slow falling effect is active (other sources like potions)
            if (player.getActivePotionEffect(Effects.LEVITATION) == null && player.getActivePotionEffect(Effects.SLOW_FALLING) == null) {
                player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING));
                if (random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            }
            //Sometimes the helmet is afraid of monsters and flies away
            if (Config.WING_HELMET_LEVITATION_ENABLED.getValue()) {
                if (player.getLastDamageSource() != null && player.getLastDamageSource().getTrueSource() instanceof LivingEntity) {
                    if (random.nextInt(100) == 0) {
                        player.addPotionEffect(new EffectInstance(Effects.LEVITATION, LEVITATION_DURATION, LEVITATION_AMPLIFIER));
                    }
                }
            }
        }
    }

    @Override
    public void onEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            // disable effects when hat is removed from slot
            ItemStack oldItemStack = event.getFrom();
            ItemStack newItemStack = event.getTo();
            if (Config.WING_HELMET_LEVITATION_ENABLED.getValue()) {
                if (oldItemStack.getItem() == this && newItemStack.getItem() != this) {
                    EffectInstance levitationEffect = player.getActivePotionEffect(Effects.LEVITATION);
                    if (levitationEffect != null && levitationEffect.getDuration() <= LEVITATION_DURATION && levitationEffect.getAmplifier() == LEVITATION_AMPLIFIER) {
                        player.removePotionEffect(Effects.LEVITATION);
                    }
                }
            }
        }
    }

}
