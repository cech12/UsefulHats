package cech12.usefulhats.client;

import cech12.usefulhats.item.AbstractHatItem;
import cech12.usefulhats.item.IUsefulHatModelOwner;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

/**
 * Colorable hat layer which adds the {@link UsefulHatModel} to rendering.
 *
 * Textures for these hats must lie in textures/models/usefulhats/
 * with names: HATNMAE.png or HATNAME_overlay.png
 */
public class UsefulHatLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends BipedArmorLayer<T, M, A> {

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();

    private final UsefulHatModel<T> hatModel = new UsefulHatModel<>(0.5F);

    public UsefulHatLayer(IEntityRenderer<T, M> renderer) {
        super(renderer, null, null);
    }

    public ResourceLocation getArmorResource(ItemStack hatItemStack, String type) {
        String texture = hatItemStack.getItem().getRegistryName().getPath();
        String domain = hatItemStack.getItem().getRegistryName().getNamespace();
        String s1 = String.format("%s:textures/models/usefulhats/%s%s.png", domain, texture, type == null ? "" : String.format("_%s", type));
        ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
        }
        return resourcelocation;
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack hatItemStack = entityIn.getItemStackFromSlot(EquipmentSlotType.HEAD);
        Item hatItem = hatItemStack.getItem();
        if (hatItem instanceof AbstractHatItem && hatItem instanceof IUsefulHatModelOwner) {
            GlStateManager.scalef(1.01F, 1.01F, 1.01F);
            if (entityIn.isSneaking()) {
                GlStateManager.translated(0, 0.250D, 0);
            }

            this.bindTexture(this.getArmorResource(hatItemStack, null));
            if (hatItem instanceof IDyeableArmorItem) {
                int i = ((IDyeableArmorItem)hatItem).getColor(hatItemStack);
                float f = (float)(i >> 16 & 255) / 255.0F;
                float f1 = (float)(i >> 8 & 255) / 255.0F;
                float f2 = (float)(i & 255) / 255.0F;
                GlStateManager.color4f(f, f1, f2, 1.0F);
                this.hatModel.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                this.bindTexture(this.getArmorResource(hatItemStack, "overlay"));
            }
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hatModel.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

            if (hatItemStack.hasEffect()) {
                func_215338_a(this::bindTexture, entityIn, this.hatModel, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }
}
