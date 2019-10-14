package cech12.usefulhats.item;

import net.minecraft.client.util.ITooltipFlag;
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
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!player.onGround && !player.isInWater()) {
            player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING));
        }
    }

}
