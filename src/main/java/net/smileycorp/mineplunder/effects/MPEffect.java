package net.smileycorp.mineplunder.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class MPEffect extends MobEffect {

    public MPEffect(int colour) {
        super(MobEffectCategory.HARMFUL, colour);
    }
}
