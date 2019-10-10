package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PostmanHatItem extends AbstractHatItem {

    public PostmanHatItem() {
        super("postman_hat", HatArmorMaterial.POSTMAN, rawColorFromRGB(2, 0, 145));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("You get " + Effects.SPEED.getDisplayName().getFormattedText() +
                " effect but also " + Effects.MINING_FATIGUE.getDisplayName().getFormattedText() + " and " +
                Effects.WEAKNESS.getDisplayName().getFormattedText() + " effect."));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        player.addPotionEffect(new EffectInstance(Effects.SPEED));
        player.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE));
        player.addPotionEffect(new EffectInstance(Effects.WEAKNESS));
    }

}
