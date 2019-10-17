package cech12.usefulhats.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class WingHelmetItem extends AbstractHatItem {

    public WingHelmetItem() {
        super("wing_helmet", HatArmorMaterial.WING, rawColorFromRGB(255, 255, 255));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("While falling you get " + Effects.SLOW_FALLING.getDisplayName().getFormattedText() + " effect."));
        tooltip.add(new StringTextComponent("Sometimes the helmet is afraid of monsters and flies away."));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!player.onGround && !player.isInWater()) {
            if (player.getActivePotionEffect(Effects.LEVITATION) == null) {
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

}
