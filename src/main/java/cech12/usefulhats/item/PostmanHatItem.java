package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class PostmanHatItem extends AbstractHatItem implements IUsefulHatModelOwner {

    public PostmanHatItem() {
        super("postman_hat", HatArmorMaterial.POSTMAN, rawColorFromRGB(57, 99, 150));
        this.addAllowedEnchantment(Enchantments.EFFICIENCY);
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) + 1;
        tooltip.add(new TranslationTextComponent("item.usefulhats.postman_hat.desc.speed", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).applyTextStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("item.usefulhats.postman_hat.desc.hunger").applyTextStyle(TextFormatting.RED));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        // Speed of other sources will not be overridden here.
        if (player.isSprinting() && player.getActivePotionEffect(Effects.SPEED) == null) {
            player.addPotionEffect(new EffectInstance(Effects.SPEED, 0, EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack), false, false, true));
            player.addPotionEffect(new EffectInstance(Effects.HUNGER));
            if (random.nextInt(20) == 0) {
                this.damageHatItemByOne(stack, player);
            }
        }
    }

}
