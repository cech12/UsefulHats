package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class ShulkerHelmetItem extends AbstractHatItem implements IEquipmentChangeListener {

    private static final int LEVITATION_DURATION = 219;

    public ShulkerHelmetItem() {
        super("shulker_helmet", HatArmorMaterial.SHULKER, rawColorFromRGB(150, 105, 150), Config.SHULKER_HELMET_ENABLED, Config.SHULKER_HELMET_DAMAGE_ENABLED);
        this.addAllowedEnchantment(Enchantments.EFFICIENCY);
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) + 1;
        tooltip.add(new TranslationTextComponent("item.usefulhats.shulker_helmet.desc.levitation", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).mergeStyle(TextFormatting.BLUE));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isRemote) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            int levitationAmplifier = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            if (!this.isEffectCausedByOtherSource(player, Effects.LEVITATION, LEVITATION_DURATION, levitationAmplifier)) {
                if (player.getActivePotionEffect(Effects.LEVITATION) == null || player.ticksExisted % 19 == 0) {
                    this.addEffect(player, Effects.LEVITATION, LEVITATION_DURATION, levitationAmplifier);
                }
                //calculate damage if levitation is caused by this item
                if (random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        int levitationAmplifier = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, oldStack);
        this.removeEffect(entity, Effects.LEVITATION, LEVITATION_DURATION, levitationAmplifier);
    }

}
