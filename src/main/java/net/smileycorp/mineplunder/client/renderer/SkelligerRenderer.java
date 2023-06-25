package net.smileycorp.mineplunder.client.renderer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.client.renderer.layer.SkelligerEyesLayer;
import net.smileycorp.mineplunder.client.renderer.model.SkelligerModel;
import net.smileycorp.mineplunder.entities.Skelliger;

public class SkelligerRenderer extends HumanoidMobRenderer<Skelliger, SkelligerModel> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(Constants.loc("skelliger"), "default");

    public SkelligerRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SkelligerModel(ctx.bakeLayer(LAYER)), 0.5F);
        this.addLayer(new SkelligerEyesLayer(this));
    }

    public ResourceLocation getTextureLocation(Skelliger entity) {
        return Constants.loc("textures/entity/illager/skelliger.png");
    }

}
