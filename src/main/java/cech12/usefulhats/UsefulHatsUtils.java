package cech12.usefulhats;

import cech12.usefulhats.compat.BaublesMod;
import cech12.usefulhats.compat.CuriosMod;
import cech12.usefulhats.item.AbstractHatItem;
import com.lazy.baubles.api.BaublesAPI;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
     * Get all equipped item stacks of hat items of this mod. Some APIs like Curios enables to have more than one slot.
     * If two hats of the same item are worn, only one is in the list to avoid effect stacking.
     * @param entity entity
     * @return List of all equipped item stacks of hat items of this mod.
     */
    public static List<ItemStack> getEquippedHatItemStacks(LivingEntity entity) {
        List<ItemStack> stacks = new LinkedList<>();
        //vanilla head slot
        ItemStack headItemStack = entity.getItemStackFromSlot(EquipmentSlotType.HEAD);
        if (headItemStack.getItem() instanceof AbstractHatItem) {
            stacks.add(headItemStack);
        }
        if (CuriosMod.isLoaded()) {
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
        if (BaublesMod.isLoaded() && entity instanceof PlayerEntity) {
            //all baubles slots that contain an AbstractHatItem
            BaublesAPI.getBaublesHandler((PlayerEntity)entity).ifPresent(itemHandler -> {
                int slots = itemHandler.getSlots();
                for (int i = 0; i < slots; i++) {
                    ItemStack stack = itemHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.getItem() instanceof AbstractHatItem && stacks.stream().noneMatch(s -> s.getItem() == stack.getItem())) {
                        stacks.add(stack);
                    }
                }
            });
        }
        return stacks;
    }

}
