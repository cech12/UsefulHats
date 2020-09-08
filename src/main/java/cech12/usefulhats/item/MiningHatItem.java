package cech12.usefulhats.item;

import cech12.usefulhats.UsefulHatsUtils;
import cech12.usefulhats.config.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class MiningHatItem extends AbstractMiningHatItem implements IBreakSpeedChanger, IEquipmentChangeListener, IUsefulHatModelOwner {

    private static final int NIGHT_VISION_DURATION = 239;
    private static final int NIGHT_VISION_AMPLIFIER = 0;

    public MiningHatItem() {
        super("mining_hat", HatArmorMaterial.MINING, rawColorFromRGB(255, 216, 0), Config.MINING_HAT_ENABLED, Config.MINING_HAT_DAMAGE_ENABLED);
    }

    private double[] getSpeedConfig() {
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
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int value = (int) (this.getEnchantmentValue(stack, this.getSpeedConfig()) * 100);
        //tooltip.add(new TranslationTextComponent("item.usefulhats.mining_hat.desc.mining_speed", value).applyTextStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("item.usefulhats.mining_hat.desc.mining_speed", value).func_240699_a_(TextFormatting.BLUE));
        if (Config.MINING_HAT_NIGHT_VISION_ENABLED.getValue()) {
            //tooltip.add(new TranslationTextComponent("item.usefulhats.mining_hat.desc.night_vision").applyTextStyle(TextFormatting.BLUE));
            tooltip.add(new TranslationTextComponent("item.usefulhats.mining_hat.desc.night_vision").func_240699_a_(TextFormatting.BLUE));
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isRemote) {
            if (!UsefulHatsUtils.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            //When Night Vision effect is disabled in config, do nothing.
            if (!Config.MINING_HAT_NIGHT_VISION_ENABLED.getValue()) return;
            //When Night Vision effect is caused by another source, do nothing
            if (this.isEffectCausedByOtherSource(player, Effects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER))
                return;
            boolean isNightVisionActive = player.getActivePotionEffect(Effects.NIGHT_VISION) != null;
            //support both hands
            for (ItemStack item : player.getHeldEquipment()) {
                if (item.getToolTypes().contains(ToolType.PICKAXE) && world.getLight(player.func_233580_cy_()) < 8) { //getPosition - func_233580_cy_
                    if (!isNightVisionActive || player.ticksExisted % 19 == 0) {
                        this.addEffect(player, Effects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER);
                    }
                    if (random.nextInt(20) == 0) {
                        this.damageHatItemByOne(stack, player);
                    }
                    return;
                }
            }
            //if not holding a pickaxe or not being in dark areas, remove the night vision effect
            player.removePotionEffect(Effects.NIGHT_VISION);
        }
    }

    @Override
    public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.PICKAXE) && event.getState().isToolEffective(ToolType.PICKAXE)) {
            //use getNewSpeed() instead of getOriginalSpeed() to support other mods that are changing the break speed with this event.
            event.setNewSpeed((1.0F + (float) this.getEnchantmentValue(headSlotItemStack, this.getSpeedConfig())) * event.getNewSpeed());
        }
    }

    @Override
    public void onBreakEvent(BlockEvent.BreakEvent event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.PICKAXE) && event.getState().isToolEffective(ToolType.PICKAXE)) {
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
        }
    }

    @Override
    public void onUnequippedHatItem(LivingEntity entity, ItemStack oldStack) {
        // disable effects when hat is removed from slot
        this.removeEffect(entity, Effects.NIGHT_VISION, NIGHT_VISION_DURATION, NIGHT_VISION_AMPLIFIER);
    }
}
