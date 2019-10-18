package cech12.usefulhats.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
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
        this.addAllowedEnchantment(Enchantments.EFFICIENCY);
    }

    private String getEnchantmentRomanNumber(ItemStack stack) {
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        switch (enchantmentLevel) {
            case 1 : return " II";
            case 2 : return " III";
            case 3 : return " IV";
            case 4 : return " V";
            case 5 : return " VI";
        }
        return "";
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("While sprinting you get " + Effects.SPEED.getDisplayName().getFormattedText() +
                getEnchantmentRomanNumber(stack) + " effect but also " + Effects.HUNGER.getDisplayName().getFormattedText() + " effect."));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (player.isSprinting()) {
            player.addPotionEffect(new EffectInstance(Effects.SPEED, 0, EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack), false, false, true));
            player.addPotionEffect(new EffectInstance(Effects.HUNGER));
            if (random.nextInt(20) == 0) {
                this.damageHatItemByOne(stack, player);
            }
        }
    }

}
