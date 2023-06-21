package net.smileycorp.mineplunder.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Level;
import net.smileycorp.mineplunder.api.capability.SoulFire;
import net.smileycorp.mineplunder.init.MineplunderEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow public abstract int getRemainingFireTicks();

    @Shadow public abstract void setRemainingFireTicks(int p_20269_);

    @Shadow public abstract DamageSources damageSources();

    @Shadow public abstract boolean hurt(DamageSource p_19946_, float p_19947_);

    @Shadow private Level level;

    @Inject(at = @At("TAIL"), method = "setRemainingFireTicks", cancellable = true)
    public void setRemainingFireTicks(int ticks, CallbackInfo callback) {
       if (getRemainingFireTicks() <= 0 &! level.isClientSide) SoulFire.setSoulFire((Entity)(Object)this, false);
    }

    @Inject(at = @At("HEAD"), method = "setSecondsOnFire", cancellable = true)
    public void setSecondsOnFire(int seconds, CallbackInfo callback) {
        callback.cancel();
        int i = seconds * 20;
        if ((Object) this instanceof LivingEntity) {
            i = ProtectionEnchantment.getFireAfterDampener((LivingEntity) (Object) this, i);
        }
        if (this.getRemainingFireTicks() < i) {
            this.setRemainingFireTicks(i);
        }
    }

    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public boolean hurt(Entity instance, DamageSource source, float amount) {
        if (SoulFire.isAblaze((Entity)(Object)this)) {
            if (source == damageSources().onFire()) {
                source = net.smileycorp.mineplunder.DamageSources.soulFire((Entity)(Object)this);
                if (amount == 1) amount = 2;
            }
        }
        return hurt(source, amount);
    }

    @Inject(at = @At("HEAD"), method = "isFreezing", cancellable = true)
    public void isFreezing(CallbackInfoReturnable<Boolean> callback) {
        if (!((Object)this instanceof LivingEntity)) return;
        if (!((LivingEntity)(Object)this).hasEffect(MineplunderEffects.FROSTBITE.get())) return;
        callback.setReturnValue(true);
        callback.cancel();
    }

}
