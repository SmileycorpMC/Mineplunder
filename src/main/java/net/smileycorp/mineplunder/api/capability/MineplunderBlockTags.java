package net.smileycorp.mineplunder.api.capability;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MineplunderBlockTags {

    public static final TagKey<Block> SOUL_FIRE = TagKey.create(Registries.BLOCK, new ResourceLocation("forge:fires/soul"));

    public static final TagKey<Block> SOUL_CAMPFIRE = TagKey.create(Registries.BLOCK, new ResourceLocation("forge:campfires/soul"));

}
