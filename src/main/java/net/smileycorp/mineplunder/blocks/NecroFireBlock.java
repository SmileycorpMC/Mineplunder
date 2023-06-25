package net.smileycorp.mineplunder.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.smileycorp.mineplunder.MineplunderDamageSources;
import net.smileycorp.mineplunder.api.capability.SpecialFire;

import javax.annotation.Nullable;

public class NecroFireBlock extends BaseFireBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public NecroFireBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GREEN).replaceable()
                .noCollission().instabreak().sound(SoundType.WOOL)
                .pushReaction(PushReaction.DESTROY).lightLevel((state) ->7), 1f);
        this.registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    protected boolean canBurn(BlockState p_49284_) {
        return false;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_49163_) {
        FluidState fluidstate = p_49163_.getLevel().getFluidState(p_49163_.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49180_) {
        p_49180_.add(WATERLOGGED);
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        SpecialFire.setBurning(entity, entity.getRemainingFireTicks() + 1, SpecialFire.FireType.NECROFIRE);
        if (entity.getRemainingFireTicks() == 0) {
            SpecialFire.setBurning(entity, 160, SpecialFire.FireType.NECROFIRE);
        }
        entity.hurt(MineplunderDamageSources.necrofire(entity), 1);
    }

    //necrofire can't create portals
    public void onPlace(BlockState p_49279_, Level p_49280_, BlockPos p_49281_, BlockState p_49282_, boolean p_49283_) {}

}
