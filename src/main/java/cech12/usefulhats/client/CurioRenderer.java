package cech12.usefulhats.client;

import cech12.usefulhats.item.IUsefulHatModelOwner;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.Map;

// see https://github.com/TheIllusiveC4/Curios/wiki/1.16.5-to-1.17:-Updates-and-Changes#rendering-system
public class CurioRenderer implements ICurioRenderer {

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();

    private static CurioRenderer instance;

    private HumanoidModel<LivingEntity> model;

    private CurioRenderer() {
    }

    public static CurioRenderer getInstance() {
        if (instance == null) {
            instance = new CurioRenderer();
        }
        return instance;
    }

    @OnlyIn(Dist.CLIENT)
    private HumanoidModel<LivingEntity> getModel(ItemStack stack) {
        if (stack.getItem() instanceof IUsefulHatModelOwner) {
            return UsefulHatLayers.usefulHatModel;
        } else {
            if (model == null) {
                model = new HumanoidModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
            }
            return model;
        }
    }

    @OnlyIn(Dist.CLIENT)
    private ResourceLocation getTexture(ItemStack stack, String type) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String locationString;
        if (item instanceof IUsefulHatModelOwner) {
            locationString = item.getArmorTexture(stack, stack.getEntityRepresentation(), stack.getEquipmentSlot(), type);
        } else {
            //mostly copied from BipedArmorLayer class
            String texture = item.getMaterial().getName();
            String domain = "minecraft";
            int idx = texture.indexOf(':');
            if (idx != -1) {
                domain = texture.substring(0, idx);
                texture = texture.substring(idx + 1);
            }
            locationString = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, 1, type == null ? "" : String.format("_%s", type));
        }
        ResourceLocation location = ARMOR_TEXTURE_RES_MAP.get(locationString);
        if (location == null && locationString != null) {
            location = new ResourceLocation(locationString);
            ARMOR_TEXTURE_RES_MAP.put(locationString, location);
        }
        return location;
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Item item = stack.getItem();
        HumanoidModel<LivingEntity> model = this.getModel(stack);
        model.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.prepareMobModel(slotContext.entity(), limbSwing, limbSwingAmount, partialTicks);
        ICurioRenderer.followBodyRotations(slotContext.entity(), model);
        //mostly copied from UsefulHatLayer
        model.copyPropertiesTo(model);
        boolean flag1 = stack.hasFoil();
        int i = ((net.minecraft.world.item.DyeableLeatherItem)item).getColor(stack);
        float f = (float)(i >> 16 & 255) / 255.0F;
        float f1 = (float)(i >> 8 & 255) / 255.0F;
        float f2 = (float)(i & 255) / 255.0F;
        this.renderLayer(matrixStack, renderTypeBuffer, light, flag1, model, f, f1, f2, this.getTexture(stack, null));
        this.renderLayer(matrixStack, renderTypeBuffer, light, flag1, model, 1.0F, 1.0F, 1.0F, this.getTexture(stack, "overlay"));
    }

    @OnlyIn(Dist.CLIENT)
    private void renderLayer(PoseStack p_241738_1_, MultiBufferSource p_241738_2_, int p_241738_3_, boolean p_241738_5_, HumanoidModel<LivingEntity> p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource) {
        //mostly copied from UsefulHatLayer
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(p_241738_2_, p_241738_6_.renderType(armorResource), false, p_241738_5_);
        p_241738_6_.renderToBuffer(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
    }

}
