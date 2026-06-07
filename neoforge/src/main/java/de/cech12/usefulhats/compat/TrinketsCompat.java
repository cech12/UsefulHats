package de.cech12.usefulhats.compat;

import de.cech12.usefulhats.UsefulHatsEventUtils;
import de.cech12.usefulhats.item.AbstractHatItem;
import de.cech12.usefulhats.platform.Services;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TrinketsCompat {

    public static final String MOD_ID = "trinkets_updated";

    public static void register() {
        TrinketCallback usefulHatsTrinket = new UsefulHatTrinket();
        Services.REGISTRY.getAllHatItems().forEach(item -> TrinketCallback.setCallback(item, usefulHatsTrinket));
    }

    public static void addEquippedHatsToList(LivingEntity entity, List<ItemStack> stacks) {
        TrinketsApi.getAttachment(entity).getAllEquipped()
                .forEach(slotReferenceItemStackTuple -> {
                    ItemStack stack = slotReferenceItemStackTuple.getB();
                    if (stack.getItem() instanceof AbstractHatItem && stacks.stream().noneMatch(s -> s.getItem() == stack.getItem())) {
                        stacks.add(stack);
                    }
                });
    }

    private static class UsefulHatTrinket implements TrinketCallback {

        @Override
        public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
            if (!entity.level().isClientSide()) {
                stack.getItem().inventoryTick(stack, (ServerLevel) entity.level(), entity, EquipmentSlot.HEAD);
            }
        }

        @Override
        public void onEquip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
            if (!entity.level().isClientSide()) {
                UsefulHatsEventUtils.onEquip(entity, stack);
            }
        }

        @Override
        public void onUnequip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
            if (!entity.level().isClientSide()) {
                UsefulHatsEventUtils.onUnequip(entity, stack);
            }
        }

    }

}
