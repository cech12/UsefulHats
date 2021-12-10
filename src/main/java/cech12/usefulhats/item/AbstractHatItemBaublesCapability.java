package cech12.usefulhats.item;

import cech12.usefulhats.config.ServerConfig;
import lazy.baubles.api.bauble.BaubleType;
import lazy.baubles.api.bauble.IBauble;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * This class is a capability for the Baubles API.
 */
public class AbstractHatItemBaublesCapability implements IBauble {

    private final Item item;
    private final ItemStack stack;

    public AbstractHatItemBaublesCapability(ItemStack stack) {
        this.item = stack.getItem();
        this.stack = stack;
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.HEAD;
    }

    @Override
    public void onWornTick(LivingEntity livingEntity, ItemStack stack) {
        if (ServerConfig.BAUBLES_ENABLED.get() && livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            this.item.onArmorTick(this.stack, player.level, player);
        }
    }

    @Override
    public void onEquipped(LivingEntity livingEntity, ItemStack stack) {
        if (this.item instanceof IEquipmentChangeListener) {
            ((IEquipmentChangeListener) this.item).onEquippedHatItem(livingEntity, this.stack);
        }
    }

    @Override
    public void onUnequipped(LivingEntity livingEntity, ItemStack stack) {
        if (this.item instanceof IEquipmentChangeListener) {
            ((IEquipmentChangeListener) this.item).onUnequippedHatItem(livingEntity, this.stack);
        }
    }

    @Override
    public boolean canEquip(LivingEntity player) {
        return ServerConfig.BAUBLES_ENABLED.get();
    }

    @Override
    public boolean canUnequip(LivingEntity player) {
        return true;
    }

    @Override
    public boolean willAutoSync(LivingEntity player) {
        return true;
    }
}
