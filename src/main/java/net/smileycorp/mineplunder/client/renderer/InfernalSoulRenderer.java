package net.smileycorp.mineplunder.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.client.renderer.model.InfernalSoulModel;
import net.smileycorp.mineplunder.entities.InfernalSoul;

public class InfernalSoulRenderer extends MobRenderer<InfernalSoul, InfernalSoulModel> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(Constants.loc("infernal_soul"), "default");

    public InfernalSoulRenderer(EntityRendererProvider.Context p_173933_) {
        super(p_173933_, new InfernalSoulModel(p_173933_.bakeLayer(LAYER)), 0.5F);
    }

    protected int getBlockLightLevel(InfernalSoul p_113910_, BlockPos p_113911_) {
        return 15;
    }

    @Override
    protected void scale(InfernalSoul p_116460_, PoseStack p_116461_, float p_116462_) {
        p_116461_.scale(1.3F, 1.3F, 1.3F);
    }

    public ResourceLocation getTextureLocation(InfernalSoul p_113908_) {
        return Constants.loc("textures/entity/nether_mob/infernal_soul.png");
    }

}
