package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class LuckyHatItem extends AbstractHatItem implements IItemFishedListener, ILivingDropsListener, IEquipmentChangeListener, IUsefulHatModelOwner {

    private static final int LUCK_DURATION = 219;
    private static final int UNLUCK_AMPLIFIER = 0;
    private static final int UNLUCK_DURATION = 200;

    public LuckyHatItem() {
        super("lucky_hat", HatArmorMaterial.LUCKY, rawColorFromRGB(72, 242, 0), Config.LUCKY_HAT_ENABLED, Config.LUCKY_HAT_DAMAGE_ENABLED);
        this.addAllowedEnchantment(Enchantments.LUCK_OF_THE_SEA);
        this.addAllowedEnchantment(Enchantments.LOOTING);
    }

    private boolean isLuckOrUnluckCausedByOtherSource(PlayerEntity player, ItemStack stack) {
        return this.isEffectCausedByOtherSource(player, Effects.LUCK, LUCK_DURATION, this.getLuckAmplifier(stack)) ||
                this.isEffectCausedByOtherSource(player, Effects.UNLUCK, UNLUCK_DURATION, UNLUCK_AMPLIFIER);
    }

    private boolean hasHatRelatedItemInHand(PlayerEntity player) {
        //support both hands
        for (ItemStack item : player.getHeldEquipment()) {
            if (item.getToolTypes().contains(ToolType.AXE) ||
                    item.getItem() instanceof FishingRodItem ||
                    item.getItem() instanceof SwordItem) {
                return true;
            }
        }
        return false;
    }

    private int getEffectLevel(ItemStack stack) {
        //looting and luck of the sea raise the luck level
        return 1 + EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, stack) +
                EnchantmentHelper.getEnchantmentLevel(Enchantments.LUCK_OF_THE_SEA, stack);
    }

    private int getLuckAmplifier(ItemStack stack) {
        return this.getEffectLevel(stack) - 1;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.usefulhats.lucky_hat.desc.luck", UsefulHatsUtils.getRomanNumber(getEffectLevel(stack), false)).applyTextStyle(TextFormatting.BLUE));
        if (Config.LUCKY_HAT_UNLUCK_ENABLED.getValue()) {
            tooltip.add(new TranslationTextComponent("item.usefulhats.lucky_hat.desc.unluck").applyTextStyle(TextFormatting.RED));
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isRemote) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //when luck or unluck are caused by other source, do nothing
            if (this.isLuckOrUnluckCausedByOtherSource(player, stack)) return;
            //else add luck effect when correct item is in hand and unluck is not active
            int luckAmplifier = this.getLuckAmplifier(stack);
            if (this.hasHatRelatedItemInHand(player) && player.getActivePotionEffect(Effects.UNLUCK) == null) {
                if (player.getActivePotionEffect(Effects.LUCK) == null || player.ticksExisted % 19 == 0) {
                    this.addEffect(player, Effects.LUCK, LUCK_DURATION, luckAmplifier);
                }
            } else {
                this.removeEffect(player, Effects.LUCK, LUCK_DURATION, luckAmplifier);
            }
        }
    }

    @Override
    public void onItemFishedListener(ItemFishedEvent event, ItemStack headSlotItemStack) {
        PlayerEntity player = event.getPlayer();
        //when luck or unluck is active (potions), do nothing
        if (this.isLuckOrUnluckCausedByOtherSource(player, headSlotItemStack)) return;
        //damage item after fishing
        if (!event.isCanceled() && this.hasHatRelatedItemInHand(event.getPlayer())) {
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
            if (Config.LUCKY_HAT_UNLUCK_ENABLED.getValue()) {
                this.removeEffect(player, Effects.LUCK, LUCK_DURATION, this.getLuckAmplifier(headSlotItemStack));
                this.addEffect(player, Effects.UNLUCK, UNLUCK_DURATION, UNLUCK_AMPLIFIER);
            }
        }
    }

    @Override
    public void onLivingDropsEvent(LivingDropsEvent event, PlayerEntity dropReason, ItemStack headSlotItemStack) {
        //when luck or unluck is active (potions), do nothing
        if (this.isLuckOrUnluckCausedByOtherSource(dropReason, headSlotItemStack)) return;
        //damage item after killing a mob
        if (!event.isCanceled() && this.hasHatRelatedItemInHand(dropReason)) {
            this.damageHatItemByOne(headSlotItemStack, dropReason);
            if (Config.LUCKY_HAT_UNLUCK_ENABLED.getValue()) {
                this.removeEffect(dropReason, Effects.LUCK, LUCK_DURATION, this.getLuckAmplifier(headSlotItemStack));
                this.addEffect(dropReason, Effects.UNLUCK, UNLUCK_DURATION, UNLUCK_AMPLIFIER);
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        this.removeEffect(entity, Effects.LUCK, LUCK_DURATION, this.getLuckAmplifier(oldStack));
    }
}
