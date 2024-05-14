package de.cech12.usefulhats.item;

import de.cech12.usefulhats.UsefulHatsUtils;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BunnyEarsItem extends AbstractHatItem implements IEquipmentChangeListener, IItemUseListener, ILivingJumpListener, IUsefulHatModelOwner {

    private static final int JUMP_BOOST_DURATION = 219;

    public BunnyEarsItem() {
        super(HatArmorMaterial.BUNNY, rawColorFromRGB(142, 120, 94), Services.CONFIG::isBunnyEarsDamageEnabled);
        this.addAllowedEnchantment(Enchantments.BLOCK_EFFICIENCY);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int enchantmentLevel = Services.PLATFORM.getEnchantmentLevel(stack, Enchantments.BLOCK_EFFICIENCY) + 1;
        tooltip.add(Component.translatable("item.usefulhats.bunny_ears.desc.jump_boost", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("item.usefulhats.bunny_ears.desc.eating", enchantmentLevel + 1).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int slot, boolean selectedIndex) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(livingEntity).contains(stack)) return; //only one worn stack of this item should add its effect
            int amplifier = Services.PLATFORM.getEnchantmentLevel(stack, Enchantments.BLOCK_EFFICIENCY);
            if (!this.isEffectCausedByOtherSource(livingEntity, MobEffects.JUMP, JUMP_BOOST_DURATION, amplifier)) {
                if (livingEntity.getEffect(MobEffects.JUMP) == null || livingEntity.tickCount % 19 == 0) {
                    this.addEffect(livingEntity, MobEffects.JUMP, JUMP_BOOST_DURATION, amplifier);
                }
            }
        }
    }

    @Override
    public Integer onItemUseEventStart(Player player, ItemStack usedStack, int actualDuration, ItemStack headSlotItemStack) {
        if (usedStack.getItem().isEdible()) {
            int amplifier = Services.PLATFORM.getEnchantmentLevel(headSlotItemStack, Enchantments.BLOCK_EFFICIENCY) + 2;
            this.damageHatItemByOne(headSlotItemStack, player);
            return actualDuration / amplifier;
        }
        return null;
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        int amplifier = Services.PLATFORM.getEnchantmentLevel(oldStack, Enchantments.BLOCK_EFFICIENCY);
        this.removeEffect(entity, MobEffects.JUMP, JUMP_BOOST_DURATION, amplifier);
    }

    @Override
    public void onLivingJumpEvent(LivingEntity jumpingEntity, ItemStack headSlotItemStack) {
        this.damageHatItemByOne(headSlotItemStack, jumpingEntity);
    }
}
