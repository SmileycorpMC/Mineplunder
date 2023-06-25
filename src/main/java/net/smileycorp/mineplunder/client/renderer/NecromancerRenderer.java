package net.smileycorp.mineplunder.client.renderer;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.client.renderer.layer.NecromancerBookLayer;
import net.smileycorp.mineplunder.client.renderer.layer.NecromancerEyesLayer;
import net.smileycorp.mineplunder.entities.Necromancer;

public class NecromancerRenderer extends IllagerRenderer<Necromancer> {

    public NecromancerRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new IllagerModel<Necromancer>(ctx.bakeLayer(ModelLayers.EVOKER)), 0.5F);
        addLayer(new NecromancerEyesLayer(this));
        addLayer(new NecromancerBookLayer(this, ctx.getModelSet()));
        model.getHat().visible = true;
    }

    public ResourceLocation getTextureLocation(Necromancer entity) {
        return Constants.loc("textures/entity/illager/necromancer.png");
    }
}
