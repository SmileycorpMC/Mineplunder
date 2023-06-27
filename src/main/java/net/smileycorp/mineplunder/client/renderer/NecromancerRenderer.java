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

public class NecromancerRenderer extends MPIllagerRenderer<Necromancer> {

    public NecromancerRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, Constants.loc("textures/entity/illager/necromancer.png"));
        addLayer(new NecromancerEyesLayer(this));
        addLayer(new NecromancerBookLayer(this, ctx.getModelSet()));
    }

}
