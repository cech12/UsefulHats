package de.cech12.usefulhats.item;

import de.cech12.usefulhats.CommonLoader;
import de.cech12.usefulhats.UsefulHatsUtils;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
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

public class ShulkerHelmetItem extends AbstractHatItem implements IEquipmentChangeListener {

    private static final int LEVITATION_DURATION = 219;

    public ShulkerHelmetItem(String name) {
        super(name, HatArmorMaterials.SHULKER, Services.CONFIG::getShulkerHelmetDurability, Services.CONFIG::isShulkerHelmetDamageEnabled);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, display, tooltip, flagIn);
        int enchantmentLevel = CommonLoader.getEnchantmentLevel(stack, Enchantments.EFFICIENCY) + 1;
        tooltip.accept(Component.translatable("item.usefulhats.shulker_helmet.desc.levitation", UsefulHatsUtils.getRomanNumber(enchantmentLevel, false)).withStyle(ChatFormatting.BLUE));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, ServerLevel level, @NotNull Entity entity, EquipmentSlot slot) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(livingEntity).contains(stack)) return; //only one worn stack of this item should add its effect
            int levitationAmplifier = CommonLoader.getEnchantmentLevel(stack, Enchantments.EFFICIENCY);
            if (!this.isEffectCausedByOtherSource(livingEntity, MobEffects.LEVITATION, LEVITATION_DURATION, levitationAmplifier)) {
                if (livingEntity.getEffect(MobEffects.LEVITATION) == null || livingEntity.tickCount % 19 == 0) {
                    this.addEffect(livingEntity, MobEffects.LEVITATION, LEVITATION_DURATION, levitationAmplifier);
                }
                //calculate damage if levitation is caused by this item
                if (livingEntity.tickCount % 20 == 0) {
                    this.damageHatItemByOne(stack, livingEntity);
                }
            }
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        int levitationAmplifier = CommonLoader.getEnchantmentLevel(oldStack, Enchantments.EFFICIENCY);
        this.removeEffect(entity, MobEffects.LEVITATION, LEVITATION_DURATION, levitationAmplifier);
    }

}
