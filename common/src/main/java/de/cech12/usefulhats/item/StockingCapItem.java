package de.cech12.usefulhats.item;

import de.cech12.usefulhats.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class StockingCapItem extends AbstractHatItem {

    public StockingCapItem(String name) {
        super(name, HatArmorMaterials.STOCKING, Services.CONFIG::getStockingCapDurability, Services.CONFIG::isStockingCapDamageEnabled);
    }

    /**
     * Has no effect.
     */
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull TooltipDisplay display, @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag flagIn) {}

    @Override
    public void inventoryTick(@NotNull ItemStack stack, ServerLevel level, @NotNull Entity entity, EquipmentSlot slot) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity && level.random.nextInt(1000) == 0) {
            if (!Services.REGISTRY.getEquippedHatItemStacks(livingEntity).contains(stack)) return; //only one worn stack of this item should add its effect
            this.damageHatItemByOne(stack, livingEntity);
        }
    }
}
