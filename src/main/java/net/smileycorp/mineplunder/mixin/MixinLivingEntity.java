package net.smileycorp.mineplunder.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.smileycorp.mineplunder.init.MineplunderEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", ordinal = 0))
    public boolean hurt(LivingEntity entity, DamageSource source, float amount) {
        if (entity.hasEffect(MineplunderEffects.FROSTBITE.get())) return false;
        return entity.hurt(source, amount);
    }

}
