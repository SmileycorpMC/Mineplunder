package net.smileycorp.mineplunder.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.client.particle.NecroFlameProvider;
import net.smileycorp.mineplunder.client.renderer.*;
import net.smileycorp.mineplunder.client.renderer.model.InfernalSoulModel;
import net.smileycorp.mineplunder.client.renderer.model.SkelligerModel;
import net.smileycorp.mineplunder.entities.Necromancer;
import net.smileycorp.mineplunder.init.MineplunderBlocks;
import net.smileycorp.mineplunder.init.MineplunderEntities;
import net.smileycorp.mineplunder.init.MineplunderParticles;

@Mod.EventBusSubscriber(modid = Constants.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventRegister {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(MineplunderBlocks.NECROFIRE.get(), RenderType.cutoutMipped());
        });
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MineplunderEntities.INFERNAL_SOUL.get(), InfernalSoulRenderer::new);
        event.registerEntityRenderer(MineplunderEntities.WITHERWIGHT.get(),
                ctx -> new ClothedSkeletonRenderer(ctx, Constants.loc("textures/entity/nether_mob/witherwight.png")));
        event.registerEntityRenderer(MineplunderEntities.NECROMANCER.get(), NecromancerRenderer::new);
        event.registerEntityRenderer(MineplunderEntities.SKELLIGER.get(), SkelligerRenderer::new);
        event.registerEntityRenderer(MineplunderEntities.MARAUDER.get(), ctx ->
                new MPIllagerRenderer<>(ctx, Constants.loc("textures/entity/illager/marauder.png")));
        event.registerEntityRenderer(MineplunderEntities.SMALL_SOUL_FIREBALL.get(),
                ctx -> new ThrownItemRenderer<>(ctx, 0.75F, true));
        event.registerEntityRenderer(MineplunderEntities.NECROFIRE.get(), FakeBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SkelligerRenderer.LAYER, SkelligerModel::createBodyLayer);
        event.registerLayerDefinition(InfernalSoulRenderer.LAYER, InfernalSoulModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSprite(MineplunderParticles.NECROFLAME.get(), new NecroFlameProvider());
    }


}
