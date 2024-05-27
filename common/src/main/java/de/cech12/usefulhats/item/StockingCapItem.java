package de.cech12.usefulhats.item;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StockingCapItem extends AbstractHatItem {

    public StockingCapItem() {
        super(HatArmorMaterials.STOCKING, rawColorFromRGB(204, 0, 23), Services.CONFIG::getStockingCapDurability, Services.CONFIG::isStockingCapDamageEnabled);
    }

    @Override
    public boolean hasChristmasVariant() {
        return true;
    }

    /**
     * Has no effect.
     */
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {}

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slot, boolean selectedIndex) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity && level.random.nextInt(1000) == 0) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(livingEntity).contains(stack)) return; //only one worn stack of this item should add its effect
            this.damageHatItemByOne(stack, livingEntity);
        }
    }
}
