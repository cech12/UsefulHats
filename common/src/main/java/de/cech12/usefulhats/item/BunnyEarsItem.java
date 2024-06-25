package de.cech12.usefulhats.item;

import de.cech12.usefulhats.CommonLoader;
import de.cech12.usefulhats.UsefulHatsUtils;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BunnyEarsItem extends AbstractHatItem implements IEquipmentChangeListener, IItemUseListener, ILivingJumpListener {

    private static final int JUMP_BOOST_DURATION = 219;

    public BunnyEarsItem() {
        super(HatArmorMaterials.BUNNY, rawColorFromRGB(142, 120, 94), Services.CONFIG::getBunnyEarsDurability, Services.CONFIG::isBunnyEarsDamageEnabled);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        int enchantmentLevel = CommonLoader.getEnchantmentLevel(stack, Enchantments.EFFICIENCY) + 1;
        tooltip.add(Component.translatable("item.usefulhats.bunny_ears.desc.jump_boost", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("item.usefulhats.bunny_ears.desc.eating", enchantmentLevel + 1).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slot, boolean selectedIndex) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(livingEntity).contains(stack)) return; //only one worn stack of this item should add its effect
            int amplifier = CommonLoader.getEnchantmentLevel(stack, Enchantments.EFFICIENCY);
            if (!this.isEffectCausedByOtherSource(livingEntity, MobEffects.JUMP, JUMP_BOOST_DURATION, amplifier)) {
                if (livingEntity.getEffect(MobEffects.JUMP) == null || livingEntity.tickCount % 19 == 0) {
                    this.addEffect(livingEntity, MobEffects.JUMP, JUMP_BOOST_DURATION, amplifier);
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
        this.removeEffect(entity, MobEffects.JUMP, JUMP_BOOST_DURATION, amplifier);
    }

    @Override
    public void onLivingJumpEvent(LivingEntity jumpingEntity, ItemStack headSlotItemStack) {
        int amplifier = CommonLoader.getEnchantmentLevel(headSlotItemStack, Enchantments.EFFICIENCY);
        if (!this.isEffectCausedByOtherSource(jumpingEntity, MobEffects.JUMP, JUMP_BOOST_DURATION, amplifier)) {
            this.damageHatItemByOne(headSlotItemStack, jumpingEntity);
        }
    }
}
