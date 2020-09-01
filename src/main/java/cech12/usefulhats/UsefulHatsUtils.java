package cech12.usefulhats;

import cech12.usefulhats.compat.CuriosMod;
import cech12.usefulhats.item.AbstractHatItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
            CuriosAPI.getCuriosHandler(entity).ifPresent(handler -> {
                for (Map.Entry<String, CurioStackHandler> type : handler.getCurioMap().entrySet()) {
                    CurioStackHandler typeStacks = type.getValue();
                    if (typeStacks != null) {
                        for (int i = 0; i < typeStacks.getSlots(); i++) {
                            ItemStack stack = typeStacks.getStackInSlot(i);
                            if (stack.getItem() instanceof AbstractHatItem) {
                                stacks.add(stack);
                            }
                        }
                    }
                }
            });
        }
        return stacks;
    }

}
