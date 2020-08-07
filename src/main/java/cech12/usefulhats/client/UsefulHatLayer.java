package cech12.usefulhats.client;

import cech12.usefulhats.item.AbstractHatItem;
import cech12.usefulhats.item.IUsefulHatModelOwner;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Useful hat layer which adds the {@link UsefulHatModel} to rendering.
 *
 * Textures for these hats must lie in textures/models/usefulhats/
 * with names: HATNAME.png or HATNAME_overlay.png
 */
public class UsefulHatLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends BipedArmorLayer<T, M, A> {

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();

    public UsefulHatLayer(IEntityRenderer<T, M> renderer, A hatModel) {
        super(renderer, hatModel, hatModel);
    }

    @Override
    @Nonnull
    public ResourceLocation getArmorResource(@Nonnull Entity entity, ItemStack stack, @Nonnull EquipmentSlotType slot, @Nullable String type) {
        //texture location is another for this model (only for hats)
        if (slot == EquipmentSlotType.HEAD) {
            ResourceLocation resourceLocation = stack.getItem().getRegistryName();
            if (resourceLocation != null) {
                String texture = resourceLocation.getPath();
                String domain = resourceLocation.getNamespace();
                String s1 = String.format("%s:textures/models/usefulhats/%s%s.png", domain, texture, type == null ? "" : String.format("_%s", type));
                ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);
                if (resourcelocation == null) {
                    resourcelocation = new ResourceLocation(s1);
                    ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
                }
                return resourcelocation;
            }
        }
        //to avoid errors in texture finding use super method
        return super.getArmorResource(entity, stack, slot, type);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int packedLightIn, T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack hatItemStack = entityIn.getItemStackFromSlot(EquipmentSlotType.HEAD);
        Item hatItem = hatItemStack.getItem();
        if (hatItem instanceof AbstractHatItem && hatItem instanceof IUsefulHatModelOwner) {
            //super method makes its job good.
            super.render(matrixStackIn, bufferIn, packedLightIn, entityIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
            //reset color for later renderings (avoid violet book in inventory for enchanted hats) (not necessary for 1.15)
            //GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    protected void setModelSlotVisible(@Nonnull A model, @Nonnull EquipmentSlotType slotIn) {
        //disable all render models of biped model except the hat (because it is overridden with own model)
        //this.setModelVisible(model);
        model.setVisible(false);
        if (slotIn == EquipmentSlotType.HEAD && model instanceof UsefulHatModel) {
            model.bipedHead.showModel = true;
            model.bipedHeadwear.showModel = true;
        }
    }
}
