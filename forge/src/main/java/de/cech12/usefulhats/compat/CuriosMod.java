package de.cech12.usefulhats.compat;

import de.cech12.usefulhats.Constants;
import de.cech12.usefulhats.client.compat.CurioRenderer;
import de.cech12.usefulhats.init.ModItems;
import de.cech12.usefulhats.item.AbstractHatItem;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.List;

public class CuriosMod {

    private CuriosMod() {}

    public static void addHeadSlot() {
        if (Services.CONFIG.isCuriosAddHeadSlot()) {
            InterModComms.sendTo(Constants.CURIOS_MOD_ID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
        }
    }

    public static void setupClient() {
        for (RegistryObject<Item> item : ModItems.ITEMS.getEntries()) {
            CuriosRendererRegistry.register(item.get(), CurioRenderer::getInstance);
        }
    }

    /**
     * equipment change event of curios mod
     */
    public static void onCuriosEquipmentChangeEvent(CurioChangeEvent event) {
        ModItems.onEquipmentChanged(event.getEntity(), event.getFrom(), event.getTo());
    }

    /**
     * Adds equipped hats from curios slots to the given list.
     * @param entity entity
     * @param stacks equipped hats
     */
    public static void addHatsToList(LivingEntity entity, List<ItemStack> stacks) {
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
