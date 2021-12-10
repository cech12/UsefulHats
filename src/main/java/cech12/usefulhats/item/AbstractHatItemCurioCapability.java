package cech12.usefulhats.item;

import cech12.usefulhats.config.ServerConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
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
    public boolean canEquip(SlotContext slotContext) {
        return ServerConfig.CURIOS_ENABLED.get();
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void curioTick(SlotContext slotContext) {
        if (ServerConfig.CURIOS_ENABLED.get() && slotContext.entity() instanceof Player player) {
            this.stack.getItem().onArmorTick(this.stack, player.level, player);
        }
    }

}
