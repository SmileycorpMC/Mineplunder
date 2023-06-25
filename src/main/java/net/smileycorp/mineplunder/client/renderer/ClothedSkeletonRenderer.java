package net.smileycorp.mineplunder.client.renderer;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.smileycorp.mineplunder.client.renderer.layer.SkeletonClothingLayer;

public class ClothedSkeletonRenderer extends SkeletonRenderer {

    private final ResourceLocation texture;

    public ClothedSkeletonRenderer(EntityRendererProvider.Context ctx, ResourceLocation texture) {
        super(ctx, ModelLayers.STRAY, ModelLayers.STRAY_INNER_ARMOR, ModelLayers.STRAY_OUTER_ARMOR);
        this.addLayer(new SkeletonClothingLayer<>(this, ctx.getModelSet(),
                new ResourceLocation(texture.getNamespace(), texture.getPath().replace(".png", "_overlay.png"))));
        this.texture = texture;
    }

    public ResourceLocation getTextureLocation(AbstractSkeleton p_116049_) {
        return texture;
    }
}
