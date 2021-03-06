package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class PostmanHatItem extends AbstractHatItem implements IEquipmentChangeListener, IUsefulHatModelOwner {

    private static final int HUNGER_AMPLIFIER = 0;
    private static final int HUNGER_DURATION = 219;
    private static final int SPEED_DURATION = 219;

    public PostmanHatItem() {
        super("postman_hat", HatArmorMaterial.POSTMAN, rawColorFromRGB(57, 99, 150), Config.POSTMAN_HAT_ENABLED, Config.POSTMAN_HAT_DAMAGE_ENABLED);
        this.addAllowedEnchantment(Enchantments.EFFICIENCY);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) + 1;
        this.addTextLineToTooltip(tooltip, new TranslationTextComponent("item.usefulhats.postman_hat.desc.speed", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).mergeStyle(TextFormatting.BLUE));
        if (Config.POSTMAN_HAT_HUNGER_ENABLED.getValue()) {
            this.addTextLineToTooltip(tooltip, new TranslationTextComponent("item.usefulhats.postman_hat.desc.hunger").mergeStyle(TextFormatting.RED));
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isRemote) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            int speedAmplifier = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            //When Speed effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(player, Effects.SPEED, SPEED_DURATION, speedAmplifier)) return;
            // Speed of other sources will not be overridden here.
            if (player.isSprinting()) {
                if (player.getActivePotionEffect(Effects.SPEED) == null || player.ticksExisted % 19 == 0) {
                    this.addEffect(player, Effects.SPEED, SPEED_DURATION, speedAmplifier);
                }
                if (Config.POSTMAN_HAT_HUNGER_ENABLED.getValue()) {
                    if (!this.isEffectCausedByOtherSource(player, Effects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER)) {
                        if (player.getActivePotionEffect(Effects.HUNGER) == null || player.ticksExisted % 19 == 0) {
                            this.addEffect(player, Effects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER);
                        }
                    }
                }
                if (random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            } else {
                this.removeEffect(player, Effects.SPEED, SPEED_DURATION, speedAmplifier);
                if (Config.POSTMAN_HAT_HUNGER_ENABLED.getValue()) {
                    this.removeEffect(player, Effects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER);
                }
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        int speedAmplifier = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, oldStack);
        this.removeEffect(entity, Effects.SPEED, SPEED_DURATION, speedAmplifier);
        if (Config.POSTMAN_HAT_HUNGER_ENABLED.getValue()) {
            this.removeEffect(entity, Effects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER);
        }
    }

}
