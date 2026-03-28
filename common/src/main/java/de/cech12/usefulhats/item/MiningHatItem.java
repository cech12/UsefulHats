package de.cech12.usefulhats.item;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

public class MiningHatItem extends AbstractMiningHatItem implements IEquipmentChangeListener {

    private static final int NIGHT_VISION_DURATION = 239;
    private static final int NIGHT_VISION_AMPLIFIER = 0;

    public MiningHatItem(String name) {
        super(name, HatArmorMaterials.MINING, Services.CONFIG::getMiningHatDurability, Services.CONFIG::isMiningHatDamageEnabled);
    }

    public static boolean isLightEnabled(LivingEntity entity) {
        return Services.CONFIG.isMiningHatNightVisionEnabled()
                && Services.REGISTRY.getEquippedHatItemStacks(entity).stream().anyMatch(stack -> stack.getItem() instanceof MiningHatItem)
                && Arrays.stream(InteractionHand.values()).map(entity::getItemInHand).anyMatch(Services.REGISTRY::isAxe)
                && entity.getEffect(MobEffects.NIGHT_VISION) == null
                && entity.level().getMaxLocalRawBrightness(entity.blockPosition()) < 8;
    }

    @Override
    protected double getSpeedConfig(int enchantmentLevel) {
        return Services.CONFIG.getMiningHatSpeedWithEfficiency(enchantmentLevel);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, display, tooltip, flagIn);
        int value = (int) (this.getEnchantmentDoubleValue(stack) * 100);
        tooltip.accept(Component.translatable("item.usefulhats.mining_hat.desc.mining_speed", value).withStyle(ChatFormatting.BLUE));
        if (Services.CONFIG.isMiningHatNightVisionEnabled()) {
            tooltip.accept(Component.translatable("item.usefulhats.mining_hat.desc.night_vision").withStyle(ChatFormatting.BLUE));
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, ServerLevel level, @NotNull Entity entity, EquipmentSlot slot) {
        if (!level.isClientSide() && entity instanceof LivingEntity livingEntity) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(livingEntity).contains(stack)) return; //only one worn stack of this item should add its effect
            //When Night Vision effect is disabled in config, do nothing.
            if (!Services.CONFIG.isMiningHatNightVisionEnabled()) return;
            //When Night Vision effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(livingEntity, MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER))
                return;
            //when holding a pickaxe or being in dark areas, add the night vision effect - else remove it
            if (Arrays.stream(InteractionHand.values()).map(livingEntity::getItemInHand).anyMatch(Services.REGISTRY::isPickaxe)
                    && livingEntity.level().getMaxLocalRawBrightness(livingEntity.blockPosition()) < 8) {
                if (livingEntity.getEffect(MobEffects.NIGHT_VISION) == null || livingEntity.tickCount % 19 == 0) {
                    this.addEffect(livingEntity, MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER);
                }
                if (livingEntity.tickCount % 20 == 0) {
                    this.damageHatItemByOne(stack, livingEntity);
                }
            } else {
                livingEntity.removeEffect(MobEffects.NIGHT_VISION);
            }
        }
    }

    @Override
    protected boolean isToolEffective(ItemStack tool, BlockState state) {
        return Services.REGISTRY.isPickaxe(tool) && state.is(BlockTags.MINEABLE_WITH_PICKAXE);
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        this.removeEffect(entity, MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER);
    }
}
