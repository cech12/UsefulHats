package de.cech12.usefulhats.item;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChoppingHatItem extends AbstractMiningHatItem {

    public ChoppingHatItem() {
        super(HatArmorMaterials.CHOPPING, rawColorFromRGB(91, 91, 91), Services.CONFIG::getChoppingHatDurability, Services.CONFIG::isChoppingHatDamageEnabled);
    }

    @Override
    protected double getSpeedConfig(int enchantmentLevel) {
        return Services.CONFIG.getChoppingHatSpeedWithEfficiency(enchantmentLevel);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, tooltip, flagIn);
        int value = (int) (this.getEnchantmentDoubleValue(stack) * 100);
        tooltip.add(Component.translatable("item.usefulhats.chopping_hat.desc.chopping_speed", value).withStyle(ChatFormatting.BLUE));
    }

    @Override
    protected boolean isToolEffective(ItemStack tool, BlockState state) {
        return Services.REGISTRY.isAxe(tool) && state.is(BlockTags.MINEABLE_WITH_AXE);
    }
}
