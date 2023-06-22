package net.smileycorp.mineplunder.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Blaze;
import net.smileycorp.mineplunder.Constants;

public class InfernalSoulRenderer extends BlazeRenderer {

    public InfernalSoulRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    protected void scale(Blaze p_116460_, PoseStack p_116461_, float p_116462_) {
        p_116461_.scale(1.2F, 1.2F, 1.2F);
    }

    public ResourceLocation getTextureLocation(Blaze p_113908_) {
        return Constants.loc("textures/entity/nether_mob/infernal_soul.png");
    }

}
