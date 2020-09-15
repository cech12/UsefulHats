package cech12.usefulhats.item;

import cech12.usefulhats.config.Config;
import com.lazy.baubles.api.BaubleType;
import com.lazy.baubles.api.IBauble;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
    public BaubleType getBaubleType() {
        return BaubleType.HEAD;
    }

    @Override
    public void onWornTick(LivingEntity livingEntity) {
        if (Config.BAUBLES_ENABLED.getValue() && livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity;
            this.item.onArmorTick(this.stack, player.world, player);
        }
    }

    @Override
    public void onEquipped(LivingEntity livingEntity) {
        if (this.item instanceof IEquipmentChangeListener) {
            ((IEquipmentChangeListener) this.item).onEquippedHatItem(livingEntity, this.stack);
        }
    }

    @Override
    public void onUnequipped(LivingEntity livingEntity) {
        if (this.item instanceof IEquipmentChangeListener) {
            ((IEquipmentChangeListener) this.item).onUnequippedHatItem(livingEntity, this.stack);
        }
    }

    @Override
    public boolean canEquip(LivingEntity player) {
        return Config.BAUBLES_ENABLED.getValue();
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
