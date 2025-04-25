package de.cech12.usefulhats.item;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ChoppingHatItem extends AbstractMiningHatItem {

    public ChoppingHatItem(String name) {
        super(name, HatArmorMaterials.CHOPPING, Services.CONFIG::getChoppingHatDurability, Services.CONFIG::isChoppingHatDamageEnabled);
    }

    @Override
    protected double getSpeedConfig(int enchantmentLevel) {
        return Services.CONFIG.getChoppingHatSpeedWithEfficiency(enchantmentLevel);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, context, display, tooltip, flagIn);
        int value = (int) (this.getEnchantmentDoubleValue(stack) * 100);
        tooltip.accept(Component.translatable("item.usefulhats.chopping_hat.desc.chopping_speed", value).withStyle(ChatFormatting.BLUE));
    }

    @Override
    protected boolean isToolEffective(ItemStack tool, BlockState state) {
        return Services.REGISTRY.isAxe(tool) && state.is(BlockTags.MINEABLE_WITH_AXE);
    }
}
