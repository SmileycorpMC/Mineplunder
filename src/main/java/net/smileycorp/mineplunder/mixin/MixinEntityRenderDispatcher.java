package net.smileycorp.mineplunder.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.smileycorp.mineplunder.api.capability.SoulFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class MixinEntityRenderDispatcher {

    @Shadow public Camera camera;

    @Inject(at = @At("HEAD"), method = "renderFlame", cancellable = true)
    private void renderFlame(PoseStack poseStack, MultiBufferSource buffers, Entity entity, CallbackInfo callback) {
        if (!SoulFire.isBurning(entity)) return;
        callback.cancel();
        TextureAtlasSprite textureatlassprite = getTexture(new ResourceLocation("blocks/soul_fire_0"));
        TextureAtlasSprite textureatlassprite1 = getTexture(new ResourceLocation("blocks/soul_fire_1"));
        poseStack.pushPose();
        float f = entity.getBbWidth() * 1.4F;
        poseStack.scale(f, f, f);
        float f1 = 0.5F;
        float f2 = 0.0F;
        float f3 = entity.getBbHeight() / f;
        float f4 = 0.0F;
        poseStack.mulPose(Axis.YP.rotationDegrees(-camera.getYRot()));
        poseStack.translate(0.0F, 0.0F, -0.3F + (float)((int)f3) * 0.02F);
        float f5 = 0.0F;
        int i = 0;
        VertexConsumer vertexconsumer = buffers.getBuffer(Sheets.cutoutBlockSheet());

        for(PoseStack.Pose posestack$pose = poseStack.last(); f3 > 0.0F; ++i) {
            TextureAtlasSprite textureatlassprite2 = i % 2 == 0 ? textureatlassprite : textureatlassprite1;
            float f6 = textureatlassprite2.getU0();
            float f7 = textureatlassprite2.getV0();
            float f8 = textureatlassprite2.getU1();
            float f9 = textureatlassprite2.getV1();
            if (i / 2 % 2 == 0) {
                float f10 = f8;
                f8 = f6;
                f6 = f10;
            }
            fireVertex(posestack$pose, vertexconsumer, f1 - 0.0F, 0.0F - f4, f5, f8, f9);
            fireVertex(posestack$pose, vertexconsumer, -f1 - 0.0F, 0.0F - f4, f5, f6, f9);
            fireVertex(posestack$pose, vertexconsumer, -f1 - 0.0F, 1.4F - f4, f5, f6, f7);
            fireVertex(posestack$pose, vertexconsumer, f1 - 0.0F, 1.4F - f4, f5, f8, f7);
            f3 -= 0.45F;
            f4 -= 0.45F;
            f1 *= 0.9F;
            f5 += 0.03F;
        }

        poseStack.popPose();
    }

    private TextureAtlasSprite getTexture(ResourceLocation loc) {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                .apply(loc);
    }

    @Shadow
    protected static void fireVertex(PoseStack.Pose p_114415_, VertexConsumer p_114416_, float p_114417_, float p_114418_, float p_114419_, float p_114420_, float p_114421_) {
    }

}
