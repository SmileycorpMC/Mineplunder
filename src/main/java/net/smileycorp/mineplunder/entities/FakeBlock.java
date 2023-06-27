package net.smileycorp.mineplunder.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface FakeBlock {

    BlockState getBlockState();

    BlockPos getStartPos();

}
