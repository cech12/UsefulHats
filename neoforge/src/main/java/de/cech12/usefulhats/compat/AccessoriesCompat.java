package de.cech12.usefulhats.compat;

import de.cech12.usefulhats.UsefulHatsEventUtils;
import de.cech12.usefulhats.item.AbstractHatItem;
import de.cech12.usefulhats.platform.Services;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AccessoriesCompat {

    public static String MOD_ID = "accessories";

    public static void register() {
        Accessory usefulHatsAccessory = new UsefulHatAccessory();
        Services.REGISTRY.getAllHatItems().forEach(item -> AccessoriesAPI.registerAccessory(item, usefulHatsAccessory));
    }

    public static void addEquippedHatsToList(LivingEntity entity, List<ItemStack> stacks) {
        AccessoriesCapability capability = AccessoriesCapability.get(entity);
        if (capability == null) {
            return;
        }
        for (SlotEntryReference slotEntryReference : capability.getAllEquipped()) {
            ItemStack stack = slotEntryReference.stack();
            if (stack.getItem() instanceof AbstractHatItem && stacks.stream().noneMatch(s -> s.getItem() == stack.getItem())) {
                stacks.add(stack);
            }
        }
    }

    private static class UsefulHatAccessory implements Accessory {

        @Override
        public void tick(ItemStack stack, SlotReference reference) {
            stack.getItem().inventoryTick(stack, reference.entity().level(), reference.entity(), reference.slot(), false);
        }

        @Override
        public void onEquip(ItemStack stack, SlotReference reference) {
            if (!reference.entity().level().isClientSide()) {
                UsefulHatsEventUtils.onEquip(reference.entity(), stack);
            }
        }

        @Override
        public void onUnequip(ItemStack stack, SlotReference reference) {
            if (!reference.entity().level().isClientSide()) {
                UsefulHatsEventUtils.onUnequip(reference.entity(), stack);
            }
        }

    }

}
