package cech12.usefulhats.client;

import net.minecraft.client.renderer.entity.model.*;
import net.minecraft.entity.LivingEntity;

/**
 * Hat model class, which adds an hat flat addition (like straw hat for villagers) .
 * The hat is a half pixel higher than a normal helmet.
 */
public class UsefulHatModel<T extends LivingEntity> extends BipedModel<T> {

    private final RendererModel hatAddition;

    public UsefulHatModel(float scale) {
        this(scale, 64, 48);
    }

    public UsefulHatModel(float scale, int p_i51059_2_, int p_i51059_3_) {
        //override render hat models of BipedModel
        this.bipedHead = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.setTextureOffset(0, 0).addBox(-4.0F, -8.5F, -4.0F, 8, 8, 8, scale);
        this.bipedHeadwear = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear.setTextureOffset(32, 0).addBox(-4.0F, -8.5F, -4.0F, 8, 8, 8, scale + 0.5F);
        this.hatAddition = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.hatAddition.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hatAddition.setTextureOffset(0, 31).addBox(-8.0F, -8.0F, -5.5F - scale*2, 16, 16, 1, scale*2);
        this.hatAddition.rotateAngleX = (-(float)Math.PI / 2F);
        //add hat addition to head wear as child. So no extra calculations must be done.
        this.bipedHeadwear.addChild(this.hatAddition);
    }

}
