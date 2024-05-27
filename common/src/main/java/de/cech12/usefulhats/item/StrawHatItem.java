package de.cech12.usefulhats.item;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.List;

public class StrawHatItem extends AbstractMiningHatItem implements IBreakSpeedChanger {

    public StrawHatItem() {
        super(HatArmorMaterials.STRAW, rawColorFromRGB(226,189,0), Services.CONFIG::getStrawHatDurability, Services.CONFIG::isStrawHatDamageEnabled);
    }

    @Override
    protected double getSpeedConfig(int enchantmentLevel) {
        return Services.CONFIG.getStrawHatSpeedWithEfficiency(enchantmentLevel);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull Item.TooltipContext context, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        int value = (int) (this.getEnchantmentDoubleValue(stack) * 100);
        tooltip.add(Component.translatable("item.usefulhats.straw_hat.desc.digging_speed", value).withStyle(ChatFormatting.BLUE));
    }

    @Override
    protected boolean isToolEffective(ItemStack tool, BlockState state) {
        return Services.REGISTRY.isShovel(tool) && state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                || Services.REGISTRY.isHoe(tool) && state.is(BlockTags.MINEABLE_WITH_HOE);
    }

}
