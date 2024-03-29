package net.smileycorp.mineplunder.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.smileycorp.mineplunder.MineplunderDamageSources;
import net.smileycorp.mineplunder.api.MineplunderBlockTags;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseFireBlock.class)
public abstract class MixinBaseFireBlock extends Block {

    public MixinBaseFireBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Shadow
    private float fireDamage;

    @Inject(at = @At("HEAD"), method = "entityInside", cancellable = true)
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo callback) {
        if (!state.is(MineplunderBlockTags.SOUL_FIRE)) return;
        callback.cancel();
        if (!entity.fireImmune()) {
            if (entity.getRemainingFireTicks() == 0) {
                SpecialFire.setBurning(entity,8, SpecialFire.FireType.SOUL_FIRE);
            } else {
                SpecialFire.setBurning(entity, entity.getRemainingFireTicks() + 1, SpecialFire.FireType.SOUL_FIRE);
            }
        }
        entity.hurt(MineplunderDamageSources.soulFire(entity), this.fireDamage);
    }

}
