package de.cech12.usefulhats.client;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.client.model.HumanoidModel;

/**
 * Hat model class, which adds an hat flat addition (like straw hat for villagers) .
 * The hat is a half pixel higher than a normal helmet.
 */
public class UsefulHatModel<T extends LivingEntity> extends HumanoidModel<T> {

    public static LayerDefinition createLayer(CubeDeformation deform, float offset) {
        MeshDefinition mesh = HumanoidModel.createMesh(deform, 0.0F);
        CubeDeformation scale = deform.extend(0.01F); //against texture flickering (outer skin layer)
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.5F, -4.0F, 8.0F, 8.0F, 8.0F, scale), PartPose.offset(0.0F, 0.0F + offset, 0.0F));
        root.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.5F, -4.0F, 8.0F, 8.0F, 8.0F, scale.extend(0.5F)), PartPose.offset(0.0F, 0.0F + offset, 0.0F));
        PartDefinition headPart = root.getChild("head");
        PartDefinition hatPart = root.getChild("hat");
        hatPart.addOrReplaceChild("hatAddition", CubeListBuilder.create().texOffs(0, 31).mirror().addBox(-8.0F, -8.0F, -5.5F - scale.growZ*2, 16.0F, 16.0F, 1.0F, scale.extend(scale.growX, scale.growY, scale.growZ)), PartPose.offsetAndRotation(0.0F, 0.0F + offset, 0.0F, -(float)Math.PI / 2.0F, 0.0F, 0.0F));
        headPart.addOrReplaceChild("topHatAddition", CubeListBuilder.create().texOffs(0, 48).addBox(-4.0F, -10.5F - scale.growY*2, -4.0F, 8.0F, 2.0F, 8.0F, scale), PartPose.offset(0.0F, 0.0F + offset, 0.0F));
        hatPart.addOrReplaceChild("outerTopHatAddition", CubeListBuilder.create().texOffs(32, 48).addBox(-4.0F, -10.5F - (scale.growZ + 0.5F)*2, -4.0F, 8.0F, 2.0F, 8.0F, scale.extend(0.5F)), PartPose.offset(0.0F, 0.0F + offset, 0.0F));
        hatPart.addOrReplaceChild("verticalHatAddition", CubeListBuilder.create().texOffs(34, 31).addBox(-4.5F, -16.0F - deform.growY, 0.0F, 9.0F, 8.0F, 1.0F, deform), PartPose.offset(0.0F, 0.0F + offset, 0.0F));
        return LayerDefinition.create(mesh, 64, 58);
    }

    public UsefulHatModel(ModelPart modelPart) {
        super(modelPart, RenderType::entityTranslucent);
        //disable all render models of biped model except the hat (because it is overridden with own model)
        this.setAllVisible(false);
        this.head.visible = true;
        this.hat.visible = true;
    }

}
