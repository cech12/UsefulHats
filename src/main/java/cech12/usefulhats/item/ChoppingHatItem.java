package cech12.usefulhats.item;

import cech12.usefulhats.config.ConfigHandler;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class ChoppingHatItem extends AbstractMiningHatItem implements IBreakSpeedChanger {

    public ChoppingHatItem() {
        super("chopping_hat", HatArmorMaterial.CHOPPING, rawColorFromRGB(91, 91, 91), ConfigHandler.CHOPPING_HAT_ENABLED, ConfigHandler.CHOPPING_HAT_DAMAGE_ENABLED);
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        int value = (int) (this.getEnchantmentValue(stack) * 100);
        tooltip.add(new TranslationTextComponent("item.usefulhats.chopping_hat.desc.chopping_speed", value).applyTextStyle(TextFormatting.BLUE));
    }

    @Override
    public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.AXE) && event.getState().isToolEffective(ToolType.AXE)) {
            event.setNewSpeed(event.getOriginalSpeed() * (1.0F + this.getEnchantmentValue(headSlotItemStack)));
        }
    }

    @Override
    public void onBreakEvent(BlockEvent.BreakEvent event, ItemStack headSlotItemStack) {
        if (!event.isCanceled() && event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.AXE) && event.getState().isToolEffective(ToolType.AXE)) {
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
        }
    }
}
