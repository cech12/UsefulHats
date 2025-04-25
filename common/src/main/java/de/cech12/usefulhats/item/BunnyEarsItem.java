package de.cech12.usefulhats.item;

import de.cech12.usefulhats.CommonLoader;
import de.cech12.usefulhats.UsefulHatsUtils;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BunnyEarsItem extends AbstractHatItem implements IEquipmentChangeListener, IItemUseListener, ILivingJumpListener {

    private static final int JUMP_BOOST_DURATION = 219;

    public BunnyEarsItem(String name) {
        super(name, HatArmorMaterials.BUNNY, Services.CONFIG::getBunnyEarsDurability, Services.CONFIG::isBunnyEarsDamageEnabled);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, display, tooltip, flagIn);
        int enchantmentLevel = CommonLoader.getEnchantmentLevel(stack, Enchantments.EFFICIENCY) + 1;
        tooltip.accept(Component.translatable("item.usefulhats.bunny_ears.desc.jump_boost", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).withStyle(ChatFormatting.BLUE));
        tooltip.accept(Component.translatable("item.usefulhats.bunny_ears.desc.eating", enchantmentLevel + 1).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, ServerLevel level, @NotNull Entity entity, EquipmentSlot slot) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(livingEntity).contains(stack)) return; //only one worn stack of this item should add its effect
            int amplifier = CommonLoader.getEnchantmentLevel(stack, Enchantments.EFFICIENCY);
            if (!this.isEffectCausedByOtherSource(livingEntity, MobEffects.JUMP_BOOST, JUMP_BOOST_DURATION, amplifier)) {
                if (livingEntity.getEffect(MobEffects.JUMP_BOOST) == null || livingEntity.tickCount % 19 == 0) {
                    this.addEffect(livingEntity, MobEffects.JUMP_BOOST, JUMP_BOOST_DURATION, amplifier);
                }
            }
        }
    }

    @Override
    public int onItemUseEventStart(LivingEntity entity, ItemStack usedStack, int actualDuration, ItemStack headSlotItemStack) {
        if (usedStack.has(DataComponents.FOOD)) {
            int amplifier = CommonLoader.getEnchantmentLevel(headSlotItemStack, Enchantments.EFFICIENCY) + 2;
            this.damageHatItemByOne(headSlotItemStack, entity);
            return actualDuration / amplifier;
        }
        return actualDuration;
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        int amplifier = CommonLoader.getEnchantmentLevel(oldStack, Enchantments.EFFICIENCY);
        this.removeEffect(entity, MobEffects.JUMP_BOOST, JUMP_BOOST_DURATION, amplifier);
    }

    @Override
    public void onLivingJumpEvent(LivingEntity jumpingEntity, ItemStack headSlotItemStack) {
        int amplifier = CommonLoader.getEnchantmentLevel(headSlotItemStack, Enchantments.EFFICIENCY);
        if (!this.isEffectCausedByOtherSource(jumpingEntity, MobEffects.JUMP_BOOST, JUMP_BOOST_DURATION, amplifier)) {
            this.damageHatItemByOne(headSlotItemStack, jumpingEntity);
        }
    }
}
