package cech12.usefulhats.item;

import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class ChoppingHatItem extends AbstractMiningHatItem implements IBreakSpeedChanger {

    public ChoppingHatItem() {
        super("chopping_hat", HatArmorMaterial.CHOPPING, rawColorFromRGB(91, 91, 91));
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int value = (int) (this.getEnchantmentValue(stack) * 100);
        tooltip.add(new StringTextComponent("Chopping with an axe is " + value + "% faster. (stackable with enchantments and potions)").applyTextStyle(TextFormatting.BLUE));
    }

    @Override
    public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack) {
        if (event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.AXE) && event.getState().isToolEffective(ToolType.AXE)) {
            event.setNewSpeed(event.getOriginalSpeed() * (1.0F + this.getEnchantmentValue(headSlotItemStack)));
        }
    }

    @Override
    public void onBreakEvent(BlockEvent.BreakEvent event, ItemStack headSlotItemStack) {
        if (event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.AXE) && event.getState().isToolEffective(ToolType.AXE)) {
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
        }
    }
}
