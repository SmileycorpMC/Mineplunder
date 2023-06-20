package net.smileycorp.mineplunder.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class MineplunderDamageTags {

    public static final TagKey<DamageType> SOUL_DAMAGE = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("mineplunder:is_soul_damage"));

}
