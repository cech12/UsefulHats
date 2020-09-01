package cech12.usefulhats.item;

import cech12.usefulhats.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;
import java.util.Set;

public class StrawHatItem extends AbstractMiningHatItem implements IBreakSpeedChanger, IUsefulHatModelOwner {

    public StrawHatItem() {
        super("straw_hat", HatArmorMaterial.STRAW, rawColorFromRGB(226,189,0), Config.STRAW_HAT_ENABLED, Config.STRAW_HAT_DAMAGE_ENABLED);
    }

    private double[] getSpeedConfig() {
        double[] speedConfig = new double[6];
        speedConfig[0] = Config.STRAW_HAT_SPEED_WITH_EFFICIENCY_0.getValue();
        speedConfig[1] = Config.STRAW_HAT_SPEED_WITH_EFFICIENCY_1.getValue();
        speedConfig[2] = Config.STRAW_HAT_SPEED_WITH_EFFICIENCY_2.getValue();
        speedConfig[3] = Config.STRAW_HAT_SPEED_WITH_EFFICIENCY_3.getValue();
        speedConfig[4] = Config.STRAW_HAT_SPEED_WITH_EFFICIENCY_4.getValue();
        speedConfig[5] = Config.STRAW_HAT_SPEED_WITH_EFFICIENCY_5.getValue();
        return speedConfig;
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int value = (int) (this.getEnchantmentValue(stack, this.getSpeedConfig()) * 100);
        //tooltip.add(new TranslationTextComponent("item.usefulhats.straw_hat.desc.digging_speed", value).applyTextStyle(TextFormatting.BLUE));
        tooltip.add(new TranslationTextComponent("item.usefulhats.straw_hat.desc.digging_speed", value).func_240699_a_(TextFormatting.BLUE));
    }

    private boolean isToolEffective(Set<ToolType> toolTypes, BlockState state) {
        return (toolTypes.contains(ToolType.SHOVEL) && state.isToolEffective(ToolType.SHOVEL))
                || (toolTypes.contains(ToolType.HOE) && state.isToolEffective(ToolType.HOE));
    }

    @Override
    public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && this.isToolEffective(event.getPlayer().getHeldItemMainhand().getToolTypes(), event.getState())) {
            //use getNewSpeed() instead of getOriginalSpeed() to support other mods that are changing the break speed with this event.
            event.setNewSpeed((1.0F + (float) this.getEnchantmentValue(headSlotItemStack, this.getSpeedConfig())) * event.getNewSpeed());
        }
    }

    @Override
    public void onBreakEvent(BlockEvent.BreakEvent event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && this.isToolEffective(event.getPlayer().getHeldItemMainhand().getToolTypes(), event.getState())) {
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
        }
    }

}
