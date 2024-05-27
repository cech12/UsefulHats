package de.cech12.usefulhats.item;

import de.cech12.usefulhats.UsefulHatsUtils;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;

public class LuckyHatItem extends AbstractHatItem implements IItemFishedListener, ILivingDropsListener, IEquipmentChangeListener {

    private static final int LUCK_DURATION = 219;
    private static final int UNLUCK_AMPLIFIER = 0;
    private static final int UNLUCK_DURATION = 200;

    public LuckyHatItem() {
        super(HatArmorMaterials.LUCKY, rawColorFromRGB(72, 242, 0), Services.CONFIG::getLuckyHatDurability, Services.CONFIG::isLuckyHatDamageEnabled);
    }

    private boolean isLuckOrUnluckCausedByOtherSource(LivingEntity entity, ItemStack stack) {
        return this.isEffectCausedByOtherSource(entity, MobEffects.LUCK, LUCK_DURATION, this.getLuckAmplifier(stack)) ||
                this.isEffectCausedByOtherSource(entity, MobEffects.UNLUCK, UNLUCK_DURATION, UNLUCK_AMPLIFIER);
    }

    private int getEffectLevel(ItemStack stack) {
        return 1 + Services.PLATFORM.getEnchantmentLevel(stack, Enchantments.EFFICIENCY);
    }

    private int getLuckAmplifier(ItemStack stack) {
        return this.getEffectLevel(stack) - 1;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull Item.TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        tooltip.add(Component.translatable("item.usefulhats.lucky_hat.desc.luck", UsefulHatsUtils.getRomanNumber(getEffectLevel(stack), false)).withStyle(ChatFormatting.BLUE));
        if (Services.CONFIG.isLuckyHatUnluckEnabled()) {
            tooltip.add(Component.translatable("item.usefulhats.lucky_hat.desc.unluck").withStyle(ChatFormatting.RED));
        }
    }

    public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int slot, boolean selectedIndex) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //when luck or unluck are caused by other source, do nothing
            if (this.isLuckOrUnluckCausedByOtherSource(player, stack)) return;
            //else add luck effect when correct item is in hand and unluck is not active
            int luckAmplifier = this.getLuckAmplifier(stack);
            if (player.getEffect(MobEffects.UNLUCK) == null) {
                if (player.getEffect(MobEffects.LUCK) == null || player.tickCount % 19 == 0) {
                    this.addEffect(player, MobEffects.LUCK, LUCK_DURATION, luckAmplifier);
                }
            } else {
                this.removeEffect(player, MobEffects.LUCK, LUCK_DURATION, luckAmplifier);
            }
        }
    }

    @Override
    public void onItemFishedListener(Player player, ItemStack headSlotItemStack) {
        //when luck or unluck is active (potions), do nothing
        if (this.isLuckOrUnluckCausedByOtherSource(player, headSlotItemStack)) return;
        //damage item after fishing
        this.damageHatItemByOne(headSlotItemStack, player);
        if (Services.CONFIG.isLuckyHatUnluckEnabled()) {
            this.removeEffect(player, MobEffects.LUCK, LUCK_DURATION, this.getLuckAmplifier(headSlotItemStack));
            this.addEffect(player, MobEffects.UNLUCK, UNLUCK_DURATION, UNLUCK_AMPLIFIER);
        }
    }

    @Override
    public void onLivingDropsEvent(LivingEntity dropReason, ItemStack headSlotItemStack) {
        //when luck or unluck is active (potions), do nothing
        if (this.isLuckOrUnluckCausedByOtherSource(dropReason, headSlotItemStack)) return;
        //damage item after killing a mob
        this.damageHatItemByOne(headSlotItemStack, dropReason);
        if (Services.CONFIG.isLuckyHatUnluckEnabled()) {
            this.removeEffect(dropReason, MobEffects.LUCK, LUCK_DURATION, this.getLuckAmplifier(headSlotItemStack));
            this.addEffect(dropReason, MobEffects.UNLUCK, UNLUCK_DURATION, UNLUCK_AMPLIFIER);
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        this.removeEffect(entity, MobEffects.LUCK, LUCK_DURATION, this.getLuckAmplifier(oldStack));
    }
}
