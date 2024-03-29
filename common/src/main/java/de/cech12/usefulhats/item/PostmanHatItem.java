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

public class PostmanHatItem extends AbstractHatItem implements IEquipmentChangeListener, IUsefulHatModelOwner {

    private static final int HUNGER_AMPLIFIER = 0;
    private static final int HUNGER_DURATION = 219;
    private static final int SPEED_DURATION = 219;

    public PostmanHatItem() {
        super(HatArmorMaterial.POSTMAN, rawColorFromRGB(57, 99, 150), Services.CONFIG::isPostmanHatDamageEnabled);
        this.addAllowedEnchantment(Enchantments.BLOCK_EFFICIENCY);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int enchantmentLevel = Services.PLATFORM.getEnchantmentLevel(stack, Enchantments.BLOCK_EFFICIENCY) + 1;
        tooltip.add(Component.translatable("item.usefulhats.postman_hat.desc.speed", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).withStyle(ChatFormatting.BLUE));
        if (Services.CONFIG.isPostmanHatHungerEnabled()) {
            tooltip.add(Component.translatable("item.usefulhats.postman_hat.desc.hunger").withStyle(ChatFormatting.RED));
        }
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int slot, boolean selectedIndex) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            int speedAmplifier = Services.PLATFORM.getEnchantmentLevel(stack, Enchantments.BLOCK_EFFICIENCY);
            //When Speed effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(player, MobEffects.MOVEMENT_SPEED, SPEED_DURATION, speedAmplifier)) return;
            // Speed of other sources will not be overridden here.
            if (player.isSprinting()) {
                if (player.getEffect(MobEffects.MOVEMENT_SPEED) == null || player.tickCount % 19 == 0) {
                    this.addEffect(player, MobEffects.MOVEMENT_SPEED, SPEED_DURATION, speedAmplifier);
                }
                if (Services.CONFIG.isPostmanHatHungerEnabled()) {
                    if (!this.isEffectCausedByOtherSource(player, MobEffects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER)) {
                        if (player.getEffect(MobEffects.HUNGER) == null || player.tickCount % 19 == 0) {
                            this.addEffect(player, MobEffects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER);
                        }
                    }
                }
                if (level.random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            } else {
                this.removeEffect(player, MobEffects.MOVEMENT_SPEED, SPEED_DURATION, speedAmplifier);
                if (Services.CONFIG.isPostmanHatHungerEnabled()) {
                    this.removeEffect(player, MobEffects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER);
                }
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        int speedAmplifier = Services.PLATFORM.getEnchantmentLevel(oldStack, Enchantments.BLOCK_EFFICIENCY);
        this.removeEffect(entity, MobEffects.MOVEMENT_SPEED, SPEED_DURATION, speedAmplifier);
        if (Services.CONFIG.isPostmanHatHungerEnabled()) {
            this.removeEffect(entity, MobEffects.HUNGER, HUNGER_DURATION, HUNGER_AMPLIFIER);
        }
    }

}
