package net.smileycorp.mineplunder.client.renderer.model;

import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.smileycorp.mineplunder.entities.InfernalSoul;

public class InfernalSoulModel extends BlazeModel<InfernalSoul> {

    public InfernalSoulModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition part0 = partdefinition.addOrReplaceChild("part0", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, 1.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -2.0F, -3.0F));

        PartDefinition part1 = partdefinition.addOrReplaceChild("part1", CubeListBuilder.create().texOffs(0, 16).addBox(3.0F, 1.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, -2.0F, 1.0F));

        PartDefinition part2 = partdefinition.addOrReplaceChild("part2", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 1.0F, -3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -2.0F, 8.0F));

        PartDefinition part3 = partdefinition.addOrReplaceChild("part3", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 1.0F, 3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.0F, -10.0F));

        PartDefinition part4 = partdefinition.addOrReplaceChild("part4", CubeListBuilder.create().texOffs(20, 16).addBox(0.0F, 7.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 6.0F, -1.0F));

        PartDefinition part5 = partdefinition.addOrReplaceChild("part5", CubeListBuilder.create().texOffs(20, 16).addBox(-1.0F, 7.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 6.0F, -1.0F));

        PartDefinition part6 = partdefinition.addOrReplaceChild("part6", CubeListBuilder.create().texOffs(20, 16).addBox(-1.0F, 7.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 6.0F, 5.0F));

        PartDefinition part7 = partdefinition.addOrReplaceChild("part7", CubeListBuilder.create().texOffs(20, 16).addBox(-1.0F, 7.0F, -1.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 6.0F, -7.0F));

        PartDefinition part8 = partdefinition.addOrReplaceChild("part8", CubeListBuilder.create().texOffs(0, 16).addBox(1.0F, -20.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 16.0F, 2.0F));

        PartDefinition part9 = partdefinition.addOrReplaceChild("part9", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -20.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 16.0F, -4.0F));

        PartDefinition part10 = partdefinition.addOrReplaceChild("part10", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -20.0F, 1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 16.0F, 3.0F));

        PartDefinition part11 = partdefinition.addOrReplaceChild("part11", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -20.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 16.0F, -5.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 5.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

}
