package cech12.usefulhats.item;

import cech12.usefulhats.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ToolType;

import java.util.List;
import java.util.Set;

public class ChoppingHatItem extends AbstractMiningHatItem {

    public ChoppingHatItem() {
        super("chopping_hat", HatArmorMaterial.CHOPPING, rawColorFromRGB(91, 91, 91), Config.CHOPPING_HAT_ENABLED, Config.CHOPPING_HAT_DAMAGE_ENABLED);
    }

    @Override
    protected double[] getSpeedConfig() {
        double[] speedConfig = new double[6];
        speedConfig[0] = Config.CHOPPING_HAT_SPEED_WITH_EFFICIENCY_0.getValue();
        speedConfig[1] = Config.CHOPPING_HAT_SPEED_WITH_EFFICIENCY_1.getValue();
        speedConfig[2] = Config.CHOPPING_HAT_SPEED_WITH_EFFICIENCY_2.getValue();
        speedConfig[3] = Config.CHOPPING_HAT_SPEED_WITH_EFFICIENCY_3.getValue();
        speedConfig[4] = Config.CHOPPING_HAT_SPEED_WITH_EFFICIENCY_4.getValue();
        speedConfig[5] = Config.CHOPPING_HAT_SPEED_WITH_EFFICIENCY_5.getValue();
        return speedConfig;
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int value = (int) (this.getEnchantmentValue(stack, this.getSpeedConfig()) * 100);
        tooltip.add(new TranslationTextComponent("item.usefulhats.chopping_hat.desc.chopping_speed", value).applyTextStyle(TextFormatting.BLUE));
    }

    @Override
    protected boolean isToolEffective(Set<ToolType> toolTypes, BlockState state) {
        return toolTypes.contains(ToolType.AXE) && state.isToolEffective(ToolType.AXE);
    }
}
