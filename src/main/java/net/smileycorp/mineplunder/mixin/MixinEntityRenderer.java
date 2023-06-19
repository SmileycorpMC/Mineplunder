package net.smileycorp.mineplunder.mixin;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.smileycorp.mineplunder.api.capability.SoulFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

    @Inject(at = @At("HEAD"), method = "getBlockLightLevel", cancellable = true)
    protected void getBlockLightLevel(Entity entity, BlockPos pos, CallbackInfoReturnable<Integer> callback) {
       if (SoulFire.isBurning(entity)) {
           callback.setReturnValue(15);
           callback.cancel();
       }
    }

}
