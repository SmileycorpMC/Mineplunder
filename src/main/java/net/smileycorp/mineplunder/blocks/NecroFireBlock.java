package net.smileycorp.mineplunder.blocks;

import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class NecroFireBlock extends BaseFireBlock {

    public NecroFireBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).replaceable()
                .noCollission().instabreak().sound(SoundType.WOOL)
                .pushReaction(PushReaction.DESTROY).lightLevel((state) ->7), 1f);
    }

    @Override
    protected boolean canBurn(BlockState p_49284_) {
        return false;
    }

}
