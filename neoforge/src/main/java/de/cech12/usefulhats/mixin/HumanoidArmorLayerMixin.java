package de.cech12.usefulhats.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.cech12.usefulhats.item.AbstractHatItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Mixin for the HumanoidArmorLayer class to change the default color from the vanilla leather brown to the hats default color.
 */
@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {

    @ModifyVariable(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V", at = @At("STORE"), ordinal = 1)
    public <T extends LivingEntity, M extends HumanoidModel<T>> int changeColor(int i, PoseStack p_117119_, MultiBufferSource p_117120_, T entity, EquipmentSlot slot, int p_117123_, M p_117124_) {
        ItemStack itemstack = entity.getItemBySlot(slot);
        if (itemstack.getItem() instanceof AbstractHatItem hatItem) {
            return itemstack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(itemstack, hatItem.getDefaultColor()) : hatItem.getDefaultColor();
        }
        return i;
    }

}
