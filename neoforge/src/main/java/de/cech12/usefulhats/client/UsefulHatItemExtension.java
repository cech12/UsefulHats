package de.cech12.usefulhats.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class UsefulHatItemExtension implements IClientItemExtensions {

    public static final UsefulHatItemExtension INSTANCE = new UsefulHatItemExtension();

    private UsefulHatItemExtension() {
    }

    @Override
    @NotNull
    public Model getHumanoidArmorModel(@NotNull ItemStack itemStack, @NotNull EquipmentModel.LayerType layerType, @NotNull Model original) {
        if (original instanceof HumanoidModel<?> humanoidModel) {
            ((HumanoidModel<HumanoidRenderState>) humanoidModel).copyPropertiesTo(UsefulHatsNeoForgeClientEvents.usefulHatModel);
        }
        return UsefulHatsNeoForgeClientEvents.usefulHatModel;
    }

    @Override
    public int getDefaultDyeColor(@NotNull ItemStack stack) {
        return AbstractUsefulHatsRenderer.getDefaultColor(stack);
    }

}
