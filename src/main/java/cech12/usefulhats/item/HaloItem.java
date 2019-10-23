package cech12.usefulhats.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.List;

public class HaloItem extends AbstractHatItem implements IVisibilityChanger {

    public HaloItem() {
        super("halo", HatArmorMaterial.HALO, rawColorFromRGB(57, 99, 150));
    }

    @Override
    public void onItemToolTipEvent(ItemStack stack, List<ITextComponent> tooltip) {
        super.onItemToolTipEvent(stack, tooltip);
        tooltip.add(new StringTextComponent("No entity attacks you.").applyTextStyle(TextFormatting.BLUE));
        tooltip.add(new StringTextComponent("But beware of the nether.").applyTextStyle(TextFormatting.RED));
    }

    @Override
    public void onVisibilityEvent(PlayerEvent.Visibility event, ItemStack headSlotItemStack) {
        PlayerEntity player = event.getPlayer();
        System.out.println("-------------------------------------------------------------");
        if (player.dimension == DimensionType.THE_NETHER) {
            //bad thing
            System.out.println("bad things");
        } else {
            //good things
            System.out.println("good things");
            //event.modifyVisibility(-1);
        }
    }
}
