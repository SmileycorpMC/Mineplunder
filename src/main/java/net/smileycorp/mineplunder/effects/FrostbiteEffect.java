package net.smileycorp.mineplunder.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FrostbiteEffect extends MobEffect {

    public FrostbiteEffect() {
        super(MobEffectCategory.HARMFUL, 0x80E5EF);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.hurt(entity.damageSources().freeze(), 1 + (amplifier*0.5f));
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % (40/(amplifier+1))==0;
    }


}
