package cech12.usefulhats.item;

import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class StrawHatItem extends AbstractMiningHatItem implements IBreakSpeedChanger, IUsefulHatModelOwner {

    public StrawHatItem() {
        super("straw_hat", HatArmorMaterial.STRAW, rawColorFromRGB(226,189,0));
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int value = (int) (this.getEnchantmentValue(stack) * 100);
        tooltip.add(new TranslationTextComponent("item.usefulhats.straw_hat.desc.digging_speed", value).applyTextStyle(TextFormatting.BLUE));
    }

    @Override
    public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack) {
        if ( event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.SHOVEL) && event.getState().isToolEffective(ToolType.SHOVEL)) {
            event.setNewSpeed(event.getOriginalSpeed() * (1.0F + this.getEnchantmentValue(headSlotItemStack)));
        }
    }

    @Override
    public void onBreakEvent(BlockEvent.BreakEvent event, ItemStack headSlotItemStack) {
        if ( event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.SHOVEL) && event.getState().isToolEffective(ToolType.SHOVEL)) {
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
        }
    }

}
