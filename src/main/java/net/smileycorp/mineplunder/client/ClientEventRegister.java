package net.smileycorp.mineplunder.client;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.client.renderer.ClothedSkeletonRenderer;
import net.smileycorp.mineplunder.client.renderer.InfernalSoulRenderer;
import net.smileycorp.mineplunder.client.renderer.SkelligerRenderer;
import net.smileycorp.mineplunder.client.renderer.model.SkelligerModel;
import net.smileycorp.mineplunder.init.MineplunderEntities;

@Mod.EventBusSubscriber(modid = Constants.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventRegister {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MineplunderEntities.INFERNAL_SOUL.get(), InfernalSoulRenderer::new);
        event.registerEntityRenderer(MineplunderEntities.WITHERWIGHT.get(),
                ctx -> new ClothedSkeletonRenderer(ctx, Constants.loc("textures/entity/nether_mob/witherwight.png")));
        event.registerEntityRenderer(MineplunderEntities.SKELLIGER.get(), SkelligerRenderer::new);
        event.registerEntityRenderer(MineplunderEntities.SMALL_SOUL_FIREBALL.get(),
                ctx -> new ThrownItemRenderer<>(ctx, 0.75F, true));
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SkelligerRenderer.LAYER, () -> SkelligerModel.createBodyLayer());
    }

}
