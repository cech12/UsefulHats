package cech12.usefulhats.item;

import cech12.usefulhats.config.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurio;

/**
 * This class is a capability for the Curios API.
 */
public class AbstractHatItemCurioCapability implements ICurio {

    private final ItemStack stack;

    public AbstractHatItemCurioCapability(ItemStack stack) {
        this.stack = stack;
    }

    //these methods should not be implemented because of the CurioChangeEvent
    //public void onEquip(String identifier, int index, LivingEntity livingEntity) {}
    //public void onUnequip(String identifier, int index, LivingEntity livingEntity) {}

    @Override
    public boolean canEquip(String identifier, LivingEntity livingEntity) {
        return Config.CURIOS_ENABLED.getValue();
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity) {
        if (identifier.equals("head") && livingEntity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) livingEntity;
            this.stack.getItem().onArmorTick(this.stack, player.world, player);
        }
    }


}
