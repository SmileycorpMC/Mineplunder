package net.smileycorp.mineplunder.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class MineplunderEntityTags {

    public static final TagKey<EntityType<?>> NETHER_SKELETONS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:nether_skeletons"));

}
