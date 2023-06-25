package net.smileycorp.mineplunder.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import org.jetbrains.annotations.Nullable;

public class NecroFlameProvider implements ParticleProvider.Sprite {

    @Nullable
    @Override
    public TextureSheetParticle createParticle(ParticleOptions p_273550_, ClientLevel p_273071_, double p_273160_, double p_273576_, double p_272710_, double p_273652_, double p_273457_, double p_272840_) {
        return new NecroflameParticle(p_273071_, p_273160_, p_273576_, p_272710_, p_273652_, p_273457_, p_272840_);
    }

}
