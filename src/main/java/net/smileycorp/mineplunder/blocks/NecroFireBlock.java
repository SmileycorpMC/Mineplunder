package net.smileycorp.mineplunder.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.portal.PortalShape;
import net.smileycorp.mineplunder.MineplunderDamageSources;
import net.smileycorp.mineplunder.api.capability.SpecialFire;

import java.util.Optional;

public class NecroFireBlock extends BaseFireBlock implements SimpleWaterloggedBlock {

    public NecroFireBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).replaceable()
                .noCollission().instabreak().sound(SoundType.WOOL)
                .pushReaction(PushReaction.DESTROY).lightLevel((state) ->7), 1f);
    }

    @Override
    protected boolean canBurn(BlockState p_49284_) {
        return false;
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        SpecialFire.setBurning(entity, entity.getRemainingFireTicks() + 1, SpecialFire.FireType.NECROFIRE);
        if (entity.getRemainingFireTicks() == 0) {
            SpecialFire.setBurning(entity, 160, SpecialFire.FireType.NECROFIRE);
        }

        entity.hurt(MineplunderDamageSources.necrofire(entity), 1);
        super.entityInside(state, level, pos, entity);
    }

    //necrofire can't create portals
    public void onPlace(BlockState p_49279_, Level p_49280_, BlockPos p_49281_, BlockState p_49282_, boolean p_49283_) {}

}
