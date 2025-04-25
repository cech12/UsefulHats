package de.cech12.usefulhats.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class UsefulHatItemExtension implements IClientItemExtensions {

    public static final UsefulHatItemExtension INSTANCE = new UsefulHatItemExtension();

    private UsefulHatItemExtension() {
    }

    @Override
    @NotNull
    public Model getHumanoidArmorModel(@NotNull ItemStack itemStack, @NotNull EquipmentClientInfo.LayerType layerType, @NotNull Model original) {
        if (original instanceof HumanoidModel<?> humanoidModel) {
            ((HumanoidModel<HumanoidRenderState>) humanoidModel).copyPropertiesTo(UsefulHatsNeoForgeClientEvents.usefulHatModel);
        }
        return UsefulHatsNeoForgeClientEvents.usefulHatModel;
    }

}
