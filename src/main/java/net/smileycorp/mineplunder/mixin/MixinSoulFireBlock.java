package net.smileycorp.mineplunder.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.smileycorp.mineplunder.MineplunderDamageSources;
import net.smileycorp.mineplunder.api.MineplunderBlockTags;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoulFireBlock.class)
public abstract class MixinSoulFireBlock extends BaseFireBlock {

    public MixinSoulFireBlock(Properties p_49241_, float p_49242_) {
        super(p_49241_, p_49242_);
    }

    @Inject(at = @At("HEAD"), method = "canSurvive", cancellable = true)
    public void canSurvive(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> callback) {
        for(Direction direction : Direction.values()) {
            if (level.getBlockState(pos).isFlammable(level, pos.relative(direction), direction.getOpposite())) {
                callback.cancel();
                callback.setReturnValue(true);
            }
        }
    }

}
