package de.cech12.usefulhats.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nonnull;

public class UsefulHatItemExtension implements IClientItemExtensions {

    public static final UsefulHatItemExtension INSTANCE = new UsefulHatItemExtension();

    private UsefulHatItemExtension() {
    }

    @Override
    @Nonnull
    public HumanoidModel<?> getHumanoidArmorModel(@Nonnull LivingEntity livingEntity, @Nonnull ItemStack itemStack, @Nonnull EquipmentSlot equipmentSlot, @Nonnull HumanoidModel<?> original) {
        return UsefulHatsNeoForgeClientEvents.usefulHatModel;
    }
}
