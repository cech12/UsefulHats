package cech12.usefulhats.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StockingCapItem extends AbstractHatItem {

    public StockingCapItem() {
        super("stocking_cap", HatArmorMaterial.STOCKING, rawColorFromRGB(204, 0, 23));
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (random.nextInt(1000) == 0) {
            this.damageHatItemByOne(stack, player);
        }
    }
}
