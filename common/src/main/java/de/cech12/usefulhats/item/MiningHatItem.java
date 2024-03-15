package de.cech12.usefulhats.item;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
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
import java.util.stream.StreamSupport;

public class MiningHatItem extends AbstractMiningHatItem implements IEquipmentChangeListener, IUsefulHatModelOwner {

    private static final int NIGHT_VISION_DURATION = 239;
    private static final int NIGHT_VISION_AMPLIFIER = 0;

    public MiningHatItem() {
        super(HatArmorMaterial.MINING, rawColorFromRGB(255, 216, 0), Services.CONFIG::isMiningHatDamageEnabled);
    }

    public static boolean isLightEnabled(Player player) {
        return Services.CONFIG.isMiningHatNightVisionEnabled()
                && Services.REGISTRY.getEquippedHatItemStacks(player).stream().anyMatch(stack -> stack.getItem() instanceof MiningHatItem)
                && StreamSupport.stream(player.getHandSlots().spliterator(), false).anyMatch(Services.REGISTRY::isAxe)
                && player.getEffect(MobEffects.NIGHT_VISION) == null
                && player.level().getMaxLocalRawBrightness(player.blockPosition()) < 8;
    }

    @Override
    protected double getSpeedConfig(int enchantmentLevel) {
        return Services.CONFIG.getMiningHatSpeedWithEfficiency(enchantmentLevel);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int value = (int) (this.getEnchantmentDoubleValue(stack) * 100);
        tooltip.add(Component.translatable("item.usefulhats.mining_hat.desc.mining_speed", value).withStyle(ChatFormatting.BLUE));
        if (Services.CONFIG.isMiningHatNightVisionEnabled()) {
            if (Services.PLATFORM.isModLoaded(Constants.LUCENT_MOD_ID)) {
                tooltip.add(Component.translatable("item.usefulhats.mining_hat.desc.lucent").withStyle(ChatFormatting.BLUE));
            } else {
                tooltip.add(Component.translatable("item.usefulhats.mining_hat.desc.night_vision").withStyle(ChatFormatting.BLUE));
            }
        }
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int slot, boolean selectedIndex) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //When Night Vision effect is disabled in config, do nothing.
            if (!Services.CONFIG.isMiningHatNightVisionEnabled()) return;
            //lucent mod replaces night vision effect
            if (Services.PLATFORM.isModLoaded(Constants.LUCENT_MOD_ID)) {
                if (isLightEnabled(player) && player.tickCount % 20 == 0) {
                    this.damageHatItemByOne(stack, player);
                }
                return;
            }
            //When Night Vision effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(player, MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER))
                return;
            //when holding a pickaxe or being in dark areas, add the night vision effect - else remove it
            if (StreamSupport.stream(player.getHandSlots().spliterator(), false).anyMatch(Services.REGISTRY::isPickaxe)
                    && player.level().getMaxLocalRawBrightness(player.blockPosition()) < 8) {
                if (player.getEffect(MobEffects.NIGHT_VISION) == null || player.tickCount % 19 == 0) {
                    this.addEffect(player, MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER);
                }
                if (player.tickCount % 20 == 0) {
                    this.damageHatItemByOne(stack, player);
                }
            } else {
                player.removeEffect(MobEffects.NIGHT_VISION);
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

    //@Override //not vanilla but overrides interface method of Forge & Neoforge
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return Services.CONFIG.isMiningHatMakePiglinsNeutralEnabled();
    }
}
