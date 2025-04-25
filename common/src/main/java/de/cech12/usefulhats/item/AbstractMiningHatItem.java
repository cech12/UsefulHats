package de.cech12.usefulhats.item;

import de.cech12.usefulhats.CommonLoader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public abstract class AbstractMiningHatItem extends AbstractHatItem implements IBreakSpeedChanger {

    AbstractMiningHatItem(String name, ArmorMaterial material, Supplier<Integer> durabilityConfig, Supplier<Boolean> enabledDamageConfig) {
        super(name, material, durabilityConfig, enabledDamageConfig);
    }

    protected double getEnchantmentDoubleValue(final ItemStack stack) {
        return getSpeedConfig(CommonLoader.getEnchantmentLevel(stack, Enchantments.EFFICIENCY));
    }

    protected abstract double getSpeedConfig(int enchantmentLevel);

    protected abstract boolean isToolEffective(ItemStack tool, BlockState state);

    @Override
    public float onBreakSpeedEvent(Player player, BlockState blockState, float actualSpeed, ItemStack headSlotItemStack) {
        if (this.isToolEffective(player.getMainHandItem(), blockState)) {
            return (1.0F + (float) this.getEnchantmentDoubleValue(headSlotItemStack)) * actualSpeed;
        }
        return actualSpeed;
    }

    @Override
    public void onBreakEvent(Player player, BlockState blockState, ItemStack headSlotItemStack) {
        if (this.isToolEffective(player.getMainHandItem(), blockState)) {
            this.damageHatItemByOne(headSlotItemStack, player);
        }
    }

}
