package cech12.usefulhats.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nullable;
import java.util.List;

public class StrawHatItem extends AbstractHatItem implements IBreakSpeedChanger {

    public StrawHatItem() {
        super("straw_hat", HatArmorMaterial.STRAW, rawColorFromRGB(226,189,0));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("Digging with a shovel is 20% faster. (stackable with enchantments and potions)"));
    }

    @Override
    public void onBreakSpeedEvent(PlayerEvent.BreakSpeed event, ItemStack headSlotItemStack) {
        if ( event.getPlayer().getHeldItemMainhand().getToolTypes().contains(ToolType.SHOVEL) && event.getState().isToolEffective(ToolType.SHOVEL)) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.2F);
            this.damageHatItemByOne(headSlotItemStack, event.getPlayer());
        }
    }

}
