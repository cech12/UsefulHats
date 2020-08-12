package cech12.usefulhats.client;

import cech12.usefulhats.item.AbstractHatItem;
import cech12.usefulhats.item.IUsefulHatModelOwner;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
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
    private final A hatModel;

    public UsefulHatLayer(IEntityRenderer<T, M> renderer, A hatModel) {
        super(renderer, hatModel, hatModel);
        this.hatModel = hatModel;
    }

    @Override
    @Nonnull
    public ResourceLocation getArmorResource(@Nonnull Entity entity, @Nonnull ItemStack stack, @Nonnull EquipmentSlotType slot, @Nullable String type) {
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
        //copied from super class - only HEAD slot
        ItemStack itemstack = entityIn.getItemStackFromSlot(EquipmentSlotType.HEAD);
        Item hatItem = itemstack.getItem();
        if (hatItem instanceof AbstractHatItem && hatItem instanceof IUsefulHatModelOwner) { //difference in checking the item
            ArmorItem armoritem = (ArmorItem) hatItem;
            if (armoritem.getEquipmentSlot() == EquipmentSlotType.HEAD) {
                A model = getArmorModelHook(entityIn, itemstack, EquipmentSlotType.HEAD, this.hatModel);
                this.getEntityModel().setModelAttributes(model);
                this.setModelSlotVisible(model, EquipmentSlotType.HEAD);
                boolean flag1 = itemstack.hasEffect();
                int i = ((net.minecraft.item.IDyeableArmorItem)armoritem).getColor(itemstack);
                float f = (float)(i >> 16 & 255) / 255.0F;
                float f1 = (float)(i >> 8 & 255) / 255.0F;
                float f2 = (float)(i & 255) / 255.0F;
                this.func_241738_a_(matrixStackIn, bufferIn, packedLightIn, flag1, model, f, f1, f2, this.getArmorResource(entityIn, itemstack, EquipmentSlotType.HEAD, null));
                this.func_241738_a_(matrixStackIn, bufferIn, packedLightIn, flag1, model, 1.0F, 1.0F, 1.0F, this.getArmorResource(entityIn, itemstack, EquipmentSlotType.HEAD, "overlay"));
            }
        }
    }

    private void func_241738_a_(MatrixStack p_241738_1_, IRenderTypeBuffer p_241738_2_, int p_241738_3_, boolean p_241738_5_, A p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource) {
        //copied from super class
        //important difference "p_241738_6_.getRenderType(armorResource)" [before it was armor_cutout_no_cull]
        IVertexBuilder ivertexbuilder = ItemRenderer.func_239386_a_(p_241738_2_, p_241738_6_.getRenderType(armorResource), false, p_241738_5_);
        p_241738_6_.render(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
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
