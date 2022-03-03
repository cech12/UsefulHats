package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.ServerConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MiningHatItem extends AbstractMiningHatItem implements IEquipmentChangeListener, IUsefulHatModelOwner {

    private static final int NIGHT_VISION_DURATION = 239;
    private static final int NIGHT_VISION_AMPLIFIER = 0;

    public MiningHatItem() {
        super(HatArmorMaterial.MINING, rawColorFromRGB(255, 216, 0), ServerConfig.MINING_HAT_DAMAGE_ENABLED);
    }

    @Override
    protected double[] getSpeedConfig() {
        double[] speedConfig = new double[6];
        speedConfig[0] = ServerConfig.MINING_HAT_SPEED_WITH_EFFICIENCY_0.get();
        speedConfig[1] = ServerConfig.MINING_HAT_SPEED_WITH_EFFICIENCY_1.get();
        speedConfig[2] = ServerConfig.MINING_HAT_SPEED_WITH_EFFICIENCY_2.get();
        speedConfig[3] = ServerConfig.MINING_HAT_SPEED_WITH_EFFICIENCY_3.get();
        speedConfig[4] = ServerConfig.MINING_HAT_SPEED_WITH_EFFICIENCY_4.get();
        speedConfig[5] = ServerConfig.MINING_HAT_SPEED_WITH_EFFICIENCY_5.get();
        return speedConfig;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int value = (int) (this.getEnchantmentValue(stack, this.getSpeedConfig()) * 100);
        tooltip.add(new TranslatableComponent("item.usefulhats.mining_hat.desc.mining_speed", value).withStyle(ChatFormatting.BLUE));
        if (ServerConfig.MINING_HAT_NIGHT_VISION_ENABLED.get()) {
            tooltip.add(new TranslatableComponent("item.usefulhats.mining_hat.desc.night_vision").withStyle(ChatFormatting.BLUE));
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //When Night Vision effect is disabled in config, do nothing.
            if (!ServerConfig.MINING_HAT_NIGHT_VISION_ENABLED.get()) return;
            //When Night Vision effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(player, MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER))
                return;
            boolean isNightVisionActive = player.getEffect(MobEffects.NIGHT_VISION) != null;
            //support both hands
            for (ItemStack item : player.getHandSlots()) {
                if (item.canPerformAction(ToolActions.PICKAXE_DIG) && level.getMaxLocalRawBrightness(player.blockPosition()) < 8) {
                    if (!isNightVisionActive || player.tickCount % 19 == 0) {
                        this.addEffect(player, MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER);
                    }
                    if (level.random.nextInt(20) == 0) {
                        this.damageHatItemByOne(stack, player);
                    }
                    return;
                }
            }
            //if not holding a pickaxe or not being in dark areas, remove the night vision effect
            player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    @Override
    protected boolean isToolEffective(ItemStack tool, BlockState state) {
        return tool.canPerformAction(ToolActions.PICKAXE_DIG) && state.is(BlockTags.MINEABLE_WITH_PICKAXE);
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        this.removeEffect(entity, MobEffects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return ServerConfig.MINING_HAT_MAKE_PIGLINS_NEUTRAL_ENABLED.get();
    }
}
