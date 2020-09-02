package cech12.usefulhats;

import cech12.usefulhats.compat.CuriosMod;
import cech12.usefulhats.item.AbstractHatItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.LinkedList;
import java.util.List;

public class UsefulHatsUtils {

    private UsefulHatsUtils() {}

    public static String getRomanNumber(int number, boolean withOne) {
        String romanNumber = "?";
        switch (number) {
            case 1 : if (withOne) romanNumber = "I"; else return ""; break;
            case 2 : romanNumber = "II"; break;
            case 3 : romanNumber = "III"; break;
            case 4 : romanNumber = "IV"; break;
            case 5 : romanNumber = "V"; break;
            case 6 : romanNumber = "VI"; break;
            case 7 : romanNumber = "VII"; break;
            case 8 : romanNumber = "VIII"; break;
            case 9 : romanNumber = "IX"; break;
            case 10 : romanNumber = "X"; break;
        }
        return " " + romanNumber;
    }

    /**
     * Get all equipped head slot item stacks. Some APIs like Curios enables to have more
     * than one head slot.
     * @param entity entity
     * @return List of all equipped head slot item stacks
     */
    public static List<ItemStack> getHeadSlotItemStacks(LivingEntity entity) {
        List<ItemStack> stacks = new LinkedList<>();
        //vanilla head slot
        stacks.add(entity.getItemStackFromSlot(EquipmentSlotType.HEAD));
        if (CuriosMod.isLoaded()) {
            //all curios slots that contain an AbstractHatItem
            CuriosApi.getCuriosHelper().getEquippedCurios(entity).ifPresent(itemHandler -> {
                int slots = itemHandler.getSlots();
                for (int i = 0; i < slots; i++) {
                    ItemStack stack = itemHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.getItem() instanceof AbstractHatItem) {
                        stacks.add(stack);
                    }
                }
            });
        }
        return stacks;
    }

}
