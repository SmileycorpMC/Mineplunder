package net.smileycorp.mineplunder;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public class MineplunderDamageSources {

    public static final ResourceKey<DamageType> SOUL_FIRE = ResourceKey.create(Registries.DAMAGE_TYPE, Constants.loc("soul_fire"));
    public static final ResourceKey<DamageType> NECROFIRE = ResourceKey.create(Registries.DAMAGE_TYPE, Constants.loc("necrofire"));

    public static DamageSource soulFire(Entity entity) {
        return entity.damageSources().source(SOUL_FIRE);
    }

    public static DamageSource necrofire(Entity entity) {
        return entity.damageSources().source(NECROFIRE);
    }

}
