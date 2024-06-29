package de.cech12.usefulhats.compat;

import de.cech12.usefulhats.UsefulHatsEventUtils;
import de.cech12.usefulhats.item.AbstractHatItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.List;

public class CuriosContinuationCompat {

    private CuriosContinuationCompat() {}

    /**
     * equipment change event of curios mod
     */
    public static void onCuriosEquipmentChangeEvent(CurioChangeEvent event) {
        UsefulHatsEventUtils.onUnequip(event.getEntity(), event.getFrom());
        UsefulHatsEventUtils.onEquip(event.getEntity(), event.getTo());
    }

    /**
     * Adds equipped hats from curios slots to the given list.
     * @param entity entity
     * @param stacks equipped hats
     */
    public static void addEquippedHatsToList(LivingEntity entity, List<ItemStack> stacks) {
        //all curios slots that contain an AbstractHatItem
        CuriosApi.getCuriosHelper().getEquippedCurios(entity).ifPresent(itemHandler -> {
            int slots = itemHandler.getSlots();
            for (int i = 0; i < slots; i++) {
                ItemStack stack = itemHandler.getStackInSlot(i);
                if (!stack.isEmpty() && stack.getItem() instanceof AbstractHatItem && stacks.stream().noneMatch(s -> s.getItem() == stack.getItem())) {
                    stacks.add(stack);
                }
            }
        });
    }

}
