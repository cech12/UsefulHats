package de.cech12.usefulhats.compat;

import de.cech12.usefulhats.UsefulHatsEventUtils;
import de.cech12.usefulhats.item.AbstractHatItem;
import de.cech12.usefulhats.platform.Services;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import tld.unknown.baubles.BaublesHolderAttachment;
import tld.unknown.baubles.Registries;
import tld.unknown.baubles.api.BaubleType;
import tld.unknown.baubles.api.BaublesData;
import tld.unknown.baubles.api.IBauble;

import java.util.List;

public class Baubles2Compat {

    public static void register(RegisterCapabilitiesEvent event) {
        IBauble usefulHatsBauble = new UsefulHatBauble();
        Services.REGISTRY.getAllHatItems().forEach(item -> {
            event.registerItem(BaublesData.CapabilitiesAttachments.CAPABILITY_BAUBLE, (stack, ctx) -> usefulHatsBauble, item);
        });
    }

    public static void addEquippedHatsToList(LivingEntity entity, List<ItemStack> stacks) {
        if (entity.hasData(Registries.ATTACHMENT_BAUBLES)) {
            BaublesHolderAttachment holder = entity.getData(Registries.ATTACHMENT_BAUBLES);
            for (ItemStack stack : holder.getAllSlots()) {
                if (stack.getItem() instanceof AbstractHatItem && stacks.stream().noneMatch(s -> s.getItem() == stack.getItem())) {
                    stacks.add(stack);
                }
            }
        }
    }

    public static class UsefulHatBauble implements IBauble {

        @Override
        public void onWornTick(BaubleType type, ItemStack stack, Player player) {
            stack.getItem().inventoryTick(stack, player.level(), player, type.ordinal(), false);
        }

        @Override
        public void onEquipped(BaubleType type, ItemStack stack, Player player) {
            if (!player.level().isClientSide()) {
                UsefulHatsEventUtils.onEquip(player, stack);
            }
        }

        @Override
        public void onUnequipped(BaubleType type, ItemStack stack, Player player) {
            if (!player.level().isClientSide()) {
                UsefulHatsEventUtils.onUnequip(player, stack);
            }
        }

    }

}
