package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.ServerConfig;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolActions;
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
        super(HatArmorMaterial.LUCKY, rawColorFromRGB(72, 242, 0), ServerConfig.LUCKY_HAT_DAMAGE_ENABLED);
        this.addAllowedEnchantment(Enchantments.FISHING_LUCK);
        this.addAllowedEnchantment(Enchantments.MOB_LOOTING);
    }

    private boolean isLuckOrUnluckCausedByOtherSource(Player player, ItemStack stack) {
        return this.isEffectCausedByOtherSource(player, MobEffects.LUCK, LUCK_DURATION, this.getLuckAmplifier(stack)) ||
                this.isEffectCausedByOtherSource(player, MobEffects.UNLUCK, UNLUCK_DURATION, UNLUCK_AMPLIFIER);
    }

    private boolean hasHatRelatedItemInHand(Player player) {
        //support both hands
        for (ItemStack item : player.getHandSlots()) {
            if (item.canPerformAction(ToolActions.AXE_DIG) || //Something like AXE_SWING would be better, but does not exist
                    item.getItem() instanceof FishingRodItem ||
                    item.canPerformAction(ToolActions.SWORD_SWEEP)) {
                return true;
            }
        }
        return false;
    }

    private int getEffectLevel(ItemStack stack) {
        //looting and luck of the sea raise the luck level
        return 1 + EnchantmentHelper.getTagEnchantmentLevel(Enchantments.MOB_LOOTING, stack) +
                EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FISHING_LUCK, stack);
    }

    private int getLuckAmplifier(ItemStack stack) {
        return this.getEffectLevel(stack) - 1;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("item.usefulhats.lucky_hat.desc.luck", UsefulHatsUtils.getRomanNumber(getEffectLevel(stack), false)).withStyle(ChatFormatting.BLUE));
        if (ServerConfig.LUCKY_HAT_UNLUCK_ENABLED.get()) {
            tooltip.add(Component.translatable("item.usefulhats.lucky_hat.desc.unluck").withStyle(ChatFormatting.RED));
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if (!world.isClientSide) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //when luck or unluck are caused by other source, do nothing
            if (this.isLuckOrUnluckCausedByOtherSource(player, stack)) return;
            //else add luck effect when correct item is in hand and unluck is not active
            int luckAmplifier = this.getLuckAmplifier(stack);
            if (this.hasHatRelatedItemInHand(player) && player.getEffect(MobEffects.UNLUCK) == null) {
                if (player.getEffect(MobEffects.LUCK) == null || player.tickCount % 19 == 0) {
                    this.addEffect(player, MobEffects.LUCK, LUCK_DURATION, luckAmplifier);
                }
            } else {
                this.removeEffect(player, MobEffects.LUCK, LUCK_DURATION, luckAmplifier);
            }
        }
    }

    @Override
    public void onItemFishedListener(ItemFishedEvent event, ItemStack headSlotItemStack) {
        Player player = event.getEntity();
        //when luck or unluck is active (potions), do nothing
        if (this.isLuckOrUnluckCausedByOtherSource(player, headSlotItemStack)) return;
        //damage item after fishing
        if (!event.isCanceled() && this.hasHatRelatedItemInHand(event.getEntity())) {
            this.damageHatItemByOne(headSlotItemStack, event.getEntity());
            if (ServerConfig.LUCKY_HAT_UNLUCK_ENABLED.get()) {
                this.removeEffect(player, MobEffects.LUCK, LUCK_DURATION, this.getLuckAmplifier(headSlotItemStack));
                this.addEffect(player, MobEffects.UNLUCK, UNLUCK_DURATION, UNLUCK_AMPLIFIER);
            }
        }
    }

    @Override
    public void onLivingDropsEvent(LivingDropsEvent event, Player dropReason, ItemStack headSlotItemStack) {
        //when luck or unluck is active (potions), do nothing
        if (this.isLuckOrUnluckCausedByOtherSource(dropReason, headSlotItemStack)) return;
        //damage item after killing a mob
        if (!event.isCanceled() && this.hasHatRelatedItemInHand(dropReason)) {
            this.damageHatItemByOne(headSlotItemStack, dropReason);
            if (ServerConfig.LUCKY_HAT_UNLUCK_ENABLED.get()) {
                this.removeEffect(dropReason, MobEffects.LUCK, LUCK_DURATION, this.getLuckAmplifier(headSlotItemStack));
                this.addEffect(dropReason, MobEffects.UNLUCK, UNLUCK_DURATION, UNLUCK_AMPLIFIER);
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        this.removeEffect(entity, MobEffects.LUCK, LUCK_DURATION, this.getLuckAmplifier(oldStack));
    }
}
