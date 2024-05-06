package de.cech12.usefulhats.compat;

import de.cech12.usefulhats.UsefulHatsEventUtils;
import de.cech12.usefulhats.item.AbstractHatItem;
import de.cech12.usefulhats.platform.Services;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TrinketsCompat {

    public static String MOD_ID = "trinkets";

    public static void register() {
        Trinket usefulHatsTrinket = new UsefulHatTrinket();
        Services.REGISTRY.getAllHatItems().forEach(item -> TrinketsApi.registerTrinket(item, usefulHatsTrinket));
    }

    public static void addEquippedHatsToList(LivingEntity entity, List<ItemStack> stacks) {
        TrinketsApi.getTrinketComponent(entity).ifPresent(trinketComponent -> trinketComponent.getAllEquipped()
                .forEach(slotReferenceItemStackTuple -> {
                    ItemStack stack = slotReferenceItemStackTuple.getB();
                    if (stack.getItem() instanceof AbstractHatItem && stacks.stream().noneMatch(s -> s.getItem() == stack.getItem())) {
                        stacks.add(stack);
                    }
                })
        );
    }

    private static class UsefulHatTrinket implements Trinket {

        @Override
        public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
            stack.getItem().inventoryTick(stack, entity.level(), entity, slot.index(), false);
        }

        @Override
        public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
            if (!entity.level().isClientSide()) {
                UsefulHatsEventUtils.onEquip(entity, stack);
            }
        }

        @Override
        public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
            if (!entity.level().isClientSide()) {
                UsefulHatsEventUtils.onUnequip(entity, stack);
            }
        }

    }

}
