package cech12.usefulhats.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PostmanHatItem extends AbstractHatItem {

    public PostmanHatItem() {
        super("postman_hat", HatArmorMaterial.POSTMAN, rawColorFromRGB(57, 99, 150));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("While sprinting you get " + Effects.SPEED.getDisplayName().getFormattedText() +
                " effect but also " + Effects.HUNGER.getDisplayName().getFormattedText() + " effect."));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (player.isSprinting()) {
            player.addPotionEffect(new EffectInstance(Effects.SPEED));
            player.addPotionEffect(new EffectInstance(Effects.HUNGER));
        }
    }

}
