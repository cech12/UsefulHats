package cech12.usefulhats.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.client.model.HumanoidModel;

/**
 * Hat model class, which adds an hat flat addition (like straw hat for villagers) .
 * The hat is a half pixel higher than a normal helmet.
 */
public class UsefulHatModel<T extends LivingEntity> extends HumanoidModel<T> {

    public UsefulHatModel() {
        this(0.5F);
    }

    private UsefulHatModel(float modelSize) {
        this(modelSize, 0.0F, 64, 58);
    }

    private UsefulHatModel(float scale, float p_i1149_2_, int textureWidthIn, int textureHeightIn) {
        super(RenderType::entityTranslucent, scale, p_i1149_2_, 64, 32);
        float scaleWithOffset = scale + 0.01F; //against texture flickering (outer skin layer)
        this.texWidth = textureWidthIn;
        this.texHeight = textureHeightIn;
        //override render hat models of BipedModel
        this.head = new ModelPart(this, 0, 0);
        this.head.addBox(-4.0F, -8.5F, -4.0F, 8, 8, 8, scaleWithOffset);
        this.head.setPos(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.hat = new ModelPart(this, 32, 0);
        this.hat.addBox(-4.0F, -8.5F, -4.0F, 8, 8, 8, scaleWithOffset + 0.5F);
        this.hat.setPos(0.0F, 0.0F + p_i1149_2_, 0.0F);
        //add flat hat addition
        ModelPart hatAddition = new ModelPart(this, 0, 31);
        hatAddition.addBox(-8.0F, -8.0F, -5.5F - scaleWithOffset*2, 16, 16, 1, scaleWithOffset*2);
        hatAddition.setPos(0.0F, 0.0F + p_i1149_2_, 0.0F);
        hatAddition.xRot = (-(float)Math.PI / 2F);
        //add hat addition to head wear as child. So no extra calculations must be done.
        this.hat.addChild(hatAddition);
        //add top hat addition
        ModelPart topHatAddition = new ModelPart(this, 0, 48);
        topHatAddition.addBox(-4.0F, -10.5F - scaleWithOffset*2, -4.0F, 8, 2, 8, scaleWithOffset);
        topHatAddition.setPos(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.head.addChild(topHatAddition);
        ModelPart outerTopHatAddition = new ModelPart(this, 32, 48);
        outerTopHatAddition.addBox(-4.0F, -10.5F - (scaleWithOffset + 0.5F)*2, -4.0F, 8, 2, 8, scaleWithOffset + 0.5F);
        outerTopHatAddition.setPos(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.hat.addChild(outerTopHatAddition);
        //add vertical flat box for bunny ears or similar
        ModelPart verticalHatAddition = new ModelPart(this, 34, 31);
        verticalHatAddition.addBox(-4.5F, -16.0F - scale, 0.0F, 9, 8, 1, scale);
        verticalHatAddition.setPos(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.hat.addChild(verticalHatAddition);
        //disable all render models of biped model except the hat (because it is overridden with own model)
        this.setAllVisible(false);
        this.head.visible = true;
        this.hat.visible = true;
    }

}
