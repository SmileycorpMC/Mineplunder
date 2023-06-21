package net.smileycorp.mineplunder.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.SpawnEggItem;
import net.smileycorp.mineplunder.Constants;

public class InfernalSoulRenderer extends BlazeRenderer {

    public InfernalSoulRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    public void render(Blaze p_115455_, float p_115456_, float p_115457_, PoseStack poseStack, MultiBufferSource buffers, int p_115460_) {
        poseStack.pushPose();
        poseStack.scale(1.1f, 1.1f, 1.1f);
        super.render(p_115455_, p_115456_, p_115457_, poseStack, buffers, p_115460_);
        poseStack.popPose();
    }

    public ResourceLocation getTextureLocation(Blaze p_113908_) {
        return Constants.loc("textures/entity/nether_mob/infernal_soul.png");
    }

}
