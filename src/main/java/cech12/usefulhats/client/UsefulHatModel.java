package cech12.usefulhats.client;

import net.minecraft.client.renderer.entity.model.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;

/**
 * Hat model class, which adds an hat flat addition (like straw hat for villagers) .
 * The hat is a half pixel higher than a normal helmet.
 */
public class UsefulHatModel<T extends LivingEntity> extends BipedModel<T> {

    public UsefulHatModel() {
        this(0.5F);
    }

    private UsefulHatModel(float modelSize) {
        this(modelSize, 0.0F, 64, 58);
    }

    private UsefulHatModel(float scale, float p_i1149_2_, int textureWidthIn, int textureHeightIn) {
        super(scale, p_i1149_2_, 64, 32);
        float scaleWithOffset = scale + 0.01F; //against texture flickering (outer skin layer)
        this.textureWidth = textureWidthIn;
        this.textureHeight = textureHeightIn;
        //override render hat models of BipedModel
        this.bipedHead = new RendererModel(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.5F, -4.0F, 8, 8, 8, scaleWithOffset);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.bipedHeadwear = new RendererModel(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.5F, -4.0F, 8, 8, 8, scaleWithOffset + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        //add flat hat addition
        RendererModel hatAddition = new RendererModel(this, 0, 31);
        hatAddition.addBox(-8.0F, -8.0F, -5.5F - scaleWithOffset*2, 16, 16, 1, scaleWithOffset*2);
        hatAddition.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        hatAddition.rotateAngleX = (-(float)Math.PI / 2F);
        //add hat addition to head wear as child. So no extra calculations must be done.
        this.bipedHeadwear.addChild(hatAddition);
        //add top hat addition
        RendererModel topHatAddition = new RendererModel(this, 0, 48);
        topHatAddition.addBox(-4.0F, -10.5F - scaleWithOffset*2, -4.0F, 8, 2, 8, scaleWithOffset);
        topHatAddition.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.bipedHead.addChild(topHatAddition);
        RendererModel outerTopHatAddition = new RendererModel(this, 32, 48);
        outerTopHatAddition.addBox(-4.0F, -10.5F - (scaleWithOffset + 0.5F)*2, -4.0F, 8, 2, 8, scaleWithOffset + 0.5F);
        outerTopHatAddition.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.bipedHeadwear.addChild(outerTopHatAddition);
        //disable all render models of biped model except the hat (because it is overridden with own model)
        this.setVisible(false);
        this.bipedHead.showModel = true;
        this.bipedHeadwear.showModel = true;
    }


    /**
     * Model explicit for armor stand, because of other rotation angles.
     */
    public static class ArmorStandModel extends UsefulHatModel<ArmorStandEntity> {

        public ArmorStandModel() {
            super();
        }

        @Override
        public void setRotationAngles(ArmorStandEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
            this.bipedHead.rotateAngleX = ((float)Math.PI / 180F) * entityIn.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = ((float)Math.PI / 180F) * entityIn.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = ((float)Math.PI / 180F) * entityIn.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
            this.bipedBody.rotateAngleX = ((float)Math.PI / 180F) * entityIn.getBodyRotation().getX();
            this.bipedBody.rotateAngleY = ((float)Math.PI / 180F) * entityIn.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ = ((float)Math.PI / 180F) * entityIn.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX = ((float)Math.PI / 180F) * entityIn.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY = ((float)Math.PI / 180F) * entityIn.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ = ((float)Math.PI / 180F) * entityIn.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX = ((float)Math.PI / 180F) * entityIn.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY = ((float)Math.PI / 180F) * entityIn.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ = ((float)Math.PI / 180F) * entityIn.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX = ((float)Math.PI / 180F) * entityIn.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY = ((float)Math.PI / 180F) * entityIn.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ = ((float)Math.PI / 180F) * entityIn.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
            this.bipedRightLeg.rotateAngleX = ((float)Math.PI / 180F) * entityIn.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 180F) * entityIn.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ = ((float)Math.PI / 180F) * entityIn.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
            this.bipedHeadwear.copyModelAngles(this.bipedHead);
        }
    }

}
