package cech12.usefulhats.item;

import cech12.usefulhats.config.Config;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
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
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int value = (int) (this.getEnchantmentValue(stack, this.getSpeedConfig()) * 100);
        tooltip.add(new TranslatableComponent("item.usefulhats.chopping_hat.desc.chopping_speed", value).withStyle(ChatFormatting.BLUE));
    }

    @Override
    protected boolean isToolEffective(ItemStack tool, BlockState state) {
        return tool.canPerformAction(ToolActions.AXE_DIG) && state.is(BlockTags.MINEABLE_WITH_AXE);
    }
}
