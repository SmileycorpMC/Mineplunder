package net.smileycorp.mineplunder.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.entities.FakeBlock;

public class WispRenderer<T extends Entity> extends EntityRenderer<T> {

    private final ModelPart modelPart;
    
    public WispRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.5F;
        modelPart = ctx.bakeLayer(LAYER);
    }

    public void render(T p_114634_, float p_114635_, float p_114636_, PoseStack p_114637_, MultiBufferSource p_114638_, int p_114639_) {
        modelPart.render(p_114637_, p_114638_.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(p_114634_))), p_114639_);
    }

    public ResourceLocation getTextureLocation(T p_114632_) {
        return Constants.loc("textures/entity/illager/wisp.png");
    }

}
