package de.cech12.usefulhats.item;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class StockingCapItem extends AbstractHatItem implements IUsefulHatModelOwner {

    public StockingCapItem() {
        super(HatArmorMaterial.STOCKING, rawColorFromRGB(204, 0, 23), Services.CONFIG::isStockingCapDamageEnabled);
    }

    @Override
    public boolean hasChristmasVariant() {
        return true;
    }

    /**
     * Has no effect.
     */
    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flagIn) {}

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, Level level, @Nonnull Entity entity, int slot, boolean selectedIndex) {
        if (!level.isClientSide && entity instanceof Player player && level.random.nextInt(1000) == 0) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(player).contains(stack)) return; //only one worn stack of this item should add its effect
            this.damageHatItemByOne(stack, player);
        }
    }
}
