package de.cech12.usefulhats.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class UsefulHatItemExtension implements IClientItemExtensions {

    public static final UsefulHatItemExtension INSTANCE = new UsefulHatItemExtension();

    private UsefulHatItemExtension() {
    }

    @Override
    @NotNull
    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        return UsefulHatsForgeClientEvents.usefulHatModel;
    }
}
