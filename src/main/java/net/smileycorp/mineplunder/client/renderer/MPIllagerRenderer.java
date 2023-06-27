package net.smileycorp.mineplunder.client.renderer;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.client.renderer.layer.NecromancerBookLayer;
import net.smileycorp.mineplunder.client.renderer.layer.NecromancerEyesLayer;
import net.smileycorp.mineplunder.entities.Necromancer;

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
