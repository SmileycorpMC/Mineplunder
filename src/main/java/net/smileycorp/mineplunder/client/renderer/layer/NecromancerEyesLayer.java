package net.smileycorp.mineplunder.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.entities.Necromancer;

public class NecromancerEyesLayer extends EyesLayer<Necromancer, IllagerModel<Necromancer>> {
    private static final RenderType EYES = RenderType.eyes(Constants.loc("textures/entity/illager/necromancer_eyes.png"));

    public NecromancerEyesLayer(RenderLayerParent<Necromancer, IllagerModel<Necromancer>> parent) {
        super(parent);
    }

    public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, Necromancer p_116986_, float p_116987_, float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
        if (p_116986_.isCastingSpell())
            super.render(p_116983_, p_116984_, p_116985_, p_116986_, p_116987_, p_116988_, p_116989_, p_116990_, p_116991_, p_116992_);
    }

    public RenderType renderType() {
        return EYES;
    }
}
