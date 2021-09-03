package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class MiningHatItem extends AbstractMiningHatItem implements IEquipmentChangeListener, IUsefulHatModelOwner {

    private static final int NIGHT_VISION_DURATION = 239;
    private static final int NIGHT_VISION_AMPLIFIER = 0;

    public MiningHatItem() {
        super("mining_hat", HatArmorMaterial.MINING, rawColorFromRGB(255, 216, 0), Config.MINING_HAT_ENABLED, Config.MINING_HAT_DAMAGE_ENABLED);
    }

    @Override
    protected double[] getSpeedConfig() {
        double[] speedConfig = new double[6];
        speedConfig[0] = Config.MINING_HAT_SPEED_WITH_EFFICIENCY_0.getValue();
        speedConfig[1] = Config.MINING_HAT_SPEED_WITH_EFFICIENCY_1.getValue();
        speedConfig[2] = Config.MINING_HAT_SPEED_WITH_EFFICIENCY_2.getValue();
        speedConfig[3] = Config.MINING_HAT_SPEED_WITH_EFFICIENCY_3.getValue();
        speedConfig[4] = Config.MINING_HAT_SPEED_WITH_EFFICIENCY_4.getValue();
        speedConfig[5] = Config.MINING_HAT_SPEED_WITH_EFFICIENCY_5.getValue();
        return speedConfig;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int value = (int) (this.getEnchantmentValue(stack, this.getSpeedConfig()) * 100);
        tooltip.add(new TranslationTextComponent("item.usefulhats.mining_hat.desc.mining_speed", value).withStyle(TextFormatting.BLUE));
        if (Config.MINING_HAT_NIGHT_VISION_ENABLED.getValue()) {
            tooltip.add(new TranslationTextComponent("item.usefulhats.mining_hat.desc.night_vision").withStyle(TextFormatting.BLUE));
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClientSide) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //When Night Vision effect is disabled in config, do nothing.
            if (!Config.MINING_HAT_NIGHT_VISION_ENABLED.getValue()) return;
            //When Night Vision effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(player, Effects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER))
                return;
            boolean isNightVisionActive = player.getEffect(Effects.NIGHT_VISION) != null;
            //support both hands
            for (ItemStack item : player.getHandSlots()) {
                if (item.getToolTypes().contains(ToolType.PICKAXE) && world.getMaxLocalRawBrightness(player.blockPosition()) < 8) {
                    if (!isNightVisionActive || player.tickCount % 19 == 0) {
                        this.addEffect(player, Effects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER);
                    }
                    if (random.nextInt(20) == 0) {
                        this.damageHatItemByOne(stack, player);
                    }
                    return;
                }
            }
            //if not holding a pickaxe or not being in dark areas, remove the night vision effect
            player.removeEffect(Effects.NIGHT_VISION);
        }
    }

    @Override
    protected boolean isToolEffective(Set<ToolType> toolTypes, BlockState state) {
        return toolTypes.contains(ToolType.PICKAXE) && state.isToolEffective(ToolType.PICKAXE);
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        this.removeEffect(entity, Effects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return Config.MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED.getValue();
    }
}
