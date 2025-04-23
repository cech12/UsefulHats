package de.cech12.usefulhats.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class UsefulHatItemExtension implements IClientItemExtensions {

    public static final UsefulHatItemExtension INSTANCE = new UsefulHatItemExtension();

    private UsefulHatItemExtension() {
    }

    @Override
    @NotNull
    public HumanoidModel<?> getHumanoidArmorModel(LivingEntityRenderState state, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        if (original instanceof HumanoidModel<?> humanoidModel) {
            ((HumanoidModel<HumanoidRenderState>) humanoidModel).copyPropertiesTo(UsefulHatsForgeClientEvents.usefulHatModel);
        }
        return UsefulHatsForgeClientEvents.usefulHatModel;
    }
}
