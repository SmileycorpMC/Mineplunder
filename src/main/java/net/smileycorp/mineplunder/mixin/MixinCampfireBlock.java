package net.smileycorp.mineplunder.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.smileycorp.mineplunder.MineplunderDamageSources;
import net.smileycorp.mineplunder.api.MineplunderBlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlock.class)
public abstract class MixinCampfireBlock extends Block {

    public MixinCampfireBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Shadow
    private static BooleanProperty LIT;

    @Shadow
    private int fireDamage;

    @Inject(at = @At("HEAD"), method = "entityInside", cancellable = true)
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo callback) {
        if (!state.is(MineplunderBlockTags.SOUL_CAMPFIRE)) return;
        callback.cancel();
        if (state.getValue(LIT)) {
            if (entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) return;
            entity.hurt(MineplunderDamageSources.soulFire(entity), fireDamage);
        }
    }

}
