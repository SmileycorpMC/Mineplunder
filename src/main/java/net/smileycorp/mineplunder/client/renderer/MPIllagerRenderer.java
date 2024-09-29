package net.smileycorp.mineplunder.client.renderer;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;

public class MPIllagerRenderer<T extends AbstractIllager> extends IllagerRenderer<T> {

    private final ResourceLocation texture;

    public MPIllagerRenderer(EntityRendererProvider.Context ctx, ResourceLocation texture) {
        super(ctx, new IllagerModel<T>(ctx.bakeLayer(ModelLayers.PILLAGER)), 0.5F);
        model.getHat().visible = true;
        addLayer(new ItemInHandLayer<>(this, ctx.getItemInHandRenderer()));
        this.texture = texture;
    }

    public ResourceLocation getTextureLocation(T entity) {
        return texture;
    }

}
