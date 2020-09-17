package cech12.usefulhats.item;

import cech12.usefulhats.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
    @OnlyIn(Dist.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        int value = (int) (this.getEnchantmentValue(stack, this.getSpeedConfig()) * 100);
        tooltip.add(new TranslationTextComponent("item.usefulhats.chopping_hat.desc.chopping_speed", value).mergeStyle(TextFormatting.BLUE));
    }

    @Override
    protected boolean isToolEffective(Set<ToolType> toolTypes, BlockState state) {
        return toolTypes.contains(ToolType.AXE) && (state.isToolEffective(ToolType.AXE)
                //in 1.16 there is a problem in checking the effective tool for axe tool type & vanilla blocks.
                //So, add a diamond axe speed check as work around.
                || (new ItemStack((Items.DIAMOND_AXE)).getDestroySpeed(state)) > 1.0);
    }
}
