package cech12.usefulhats.client;

import net.minecraft.client.renderer.entity.model.*;
import net.minecraft.entity.Entity;

/**
 * Hat model class, which adds an hat flat addition (like straw hat for villagers) .
 * The hat is a half pixel higher than a normal helmet.
 */
public class UsefulHatModel<T extends Entity> extends EntityModel<T> implements IHasHead, IHeadToggle {

    protected final RendererModel innerHat;
    protected final RendererModel outerHat;
    protected final RendererModel hatAddition;

    public UsefulHatModel(float scale) {
        this(scale, 64, 48);
    }

    public UsefulHatModel(float scale, int p_i51059_2_, int p_i51059_3_) {
        this.innerHat = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.innerHat.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.innerHat.setTextureOffset(0, 0).addBox(-4.0F, -8.5F, -4.0F, 8, 8, 8, scale);
        this.outerHat = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.outerHat.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.outerHat.setTextureOffset(32, 0).addBox(-4.0F, -8.5F, -4.0F, 8, 8, 8, scale + 0.5F);
        this.innerHat.addChild(this.outerHat);
        this.hatAddition = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
        this.hatAddition.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hatAddition.setTextureOffset(0, 31).addBox(-8.0F, -8.0F, -5.5F - scale*2, 16, 16, 1, scale*2);
        this.hatAddition.rotateAngleX = (-(float)Math.PI / 2F);
        this.outerHat.addChild(this.hatAddition);
    }

    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.innerHat.render(scale);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.innerHat.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.innerHat.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.innerHat.rotateAngleZ = 0.0F;
    }

    public RendererModel func_205072_a() {
        return this.innerHat;
    }

    public void func_217146_a(boolean p_217146_1_) {
        this.innerHat.showModel = p_217146_1_;
        this.outerHat.showModel = p_217146_1_;
        this.hatAddition.showModel = p_217146_1_;
    }
}
