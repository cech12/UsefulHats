package de.cech12.usefulhats.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class UsefulHatItemExtension implements IClientItemExtensions {

    public static final UsefulHatItemExtension INSTANCE = new UsefulHatItemExtension();

    private UsefulHatItemExtension() {
    }

    @Override
    @NotNull
    public HumanoidModel<?> getHumanoidArmorModel(@NotNull LivingEntity livingEntity, @NotNull ItemStack itemStack, @NotNull EquipmentSlot equipmentSlot, @NotNull HumanoidModel<?> original) {
        return UsefulHatsNeoForgeClientEvents.usefulHatModel;
    }
}
