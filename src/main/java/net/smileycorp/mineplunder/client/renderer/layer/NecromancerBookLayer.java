package net.smileycorp.mineplunder.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.client.renderer.NecromancerRenderer;
import net.smileycorp.mineplunder.entities.Necromancer;

public class NecromancerBookLayer extends RenderLayer<Necromancer, IllagerModel<Necromancer>> {

    private final BookModel model;

    public NecromancerBookLayer(NecromancerRenderer parent, EntityModelSet modelSet) {
        super(parent);
        model = new BookModel(modelSet.bakeLayer(ModelLayers.BOOK));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int partialTicks, Necromancer necromancer, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        int i = Math.floorDiv(necromancer.tickCount % 120, 60);
        ResourceLocation texture = Constants.loc("textures/entity/illager/necromancer_book_"+i+".png");
        VertexConsumer vertexconsumer = buffers.getBuffer(RenderType.entityCutoutNoCull(texture));
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.rotateAround(Axis.YP.rotationDegrees(90), 0, 0, 0);
        poseStack.rotateAround(Axis.ZP.rotationDegrees(180), 0, 0, 0);
        poseStack.translate(-0.15, -1.4, 0.634);
        model.setupAnim(0, 0, 0, 0);
        model.renderToBuffer(poseStack, vertexconsumer, partialTicks, LivingEntityRenderer.getOverlayCoords(necromancer, 0.0F), 1f, 1f, 1f, 1.F);
        poseStack.popPose();
    }
}
