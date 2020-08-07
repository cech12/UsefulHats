package cech12.usefulhats.item;

import cech12.usefulhats.config.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class MiningHatItem extends AbstractMiningHatItem implements IBreakSpeedChanger, IUsefulHatModelOwner {

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
        //When Night Vision effect is disabled in config, do nothing.
        if (!Config.MINING_HAT_NIGHT_VISION_ENABLED.getValue()) return;
        //when night vision is active (potions), do nothing
        if (player.getActivePotionEffect(Effects.NIGHT_VISION) != null) return;
        //support both hands
        for (ItemStack item : player.getHeldEquipment()) {
            if (item.getToolTypes().contains(ToolType.PICKAXE) && world.getLight(player.func_233580_cy_()) < 8) { //getPosition - func_233580_cy_
                player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION));
                if (random.nextInt(20) == 0) {
                    this.damageHatItemByOne(stack, player);
                }
                return;
            }
        }
    }

    @Override
    public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.PICKAXE) && event.getState().isToolEffective(ToolType.PICKAXE)) {
            event.setNewSpeed(event.getOriginalSpeed() * (1.0F + (float) this.getEnchantmentValue(headSlotItemStack, this.getSpeedConfig())));
        }
    }

    @Override
    public void onBreakEvent(BlockEvent.BreakEvent event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.PICKAXE) && event.getState().isToolEffective(ToolType.PICKAXE)) {
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
        }
    }

}
