package cech12.usefulhats.item;

import cech12.usefulhats.config.ServerConfig;
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

public class StrawHatItem extends AbstractMiningHatItem implements IBreakSpeedChanger, IUsefulHatModelOwner {

    public StrawHatItem() {
        super("straw_hat", HatArmorMaterial.STRAW, rawColorFromRGB(226,189,0), ServerConfig.STRAW_HAT_DAMAGE_ENABLED);
    }

    @Override
    protected double[] getSpeedConfig() {
        double[] speedConfig = new double[6];
        speedConfig[0] = ServerConfig.STRAW_HAT_SPEED_WITH_EFFICIENCY_0.get();
        speedConfig[1] = ServerConfig.STRAW_HAT_SPEED_WITH_EFFICIENCY_1.get();
        speedConfig[2] = ServerConfig.STRAW_HAT_SPEED_WITH_EFFICIENCY_2.get();
        speedConfig[3] = ServerConfig.STRAW_HAT_SPEED_WITH_EFFICIENCY_3.get();
        speedConfig[4] = ServerConfig.STRAW_HAT_SPEED_WITH_EFFICIENCY_4.get();
        speedConfig[5] = ServerConfig.STRAW_HAT_SPEED_WITH_EFFICIENCY_5.get();
        return speedConfig;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int value = (int) (this.getEnchantmentValue(stack, this.getSpeedConfig()) * 100);
        tooltip.add(new TranslatableComponent("item.usefulhats.straw_hat.desc.digging_speed", value).withStyle(ChatFormatting.BLUE));
    }

    @Override
    protected boolean isToolEffective(ItemStack tool, BlockState state) {
        return tool.canPerformAction(ToolActions.SHOVEL_DIG) && state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                || tool.canPerformAction(ToolActions.HOE_DIG) && state.is(BlockTags.MINEABLE_WITH_HOE);
    }

}
