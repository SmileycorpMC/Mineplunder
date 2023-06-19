package net.smileycorp.mineplunder.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.smileycorp.mineplunder.api.capability.SoulFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow public abstract boolean isSpectator();

    @Shadow public abstract int getRemainingFireTicks();

    @Shadow public abstract void setRemainingFireTicks(int p_20269_);

    @Inject(at = @At("HEAD"), method = "displayFireAnimation", cancellable = true)
    public void displayFireAnimation(CallbackInfoReturnable<Boolean> callback) {
        if (!isSpectator() && SoulFire.isBurning((Entity)(Object)this)) {
            callback.setReturnValue(true);
            callback.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "clearFire", cancellable = true)
    public void clearFire(CallbackInfo callback) {
       if (SoulFire.isBurning(((Entity)(Object)this))) {
           SoulFire.extinguish((Entity)(Object)this);
        }
    }

    @Inject(at = @At("HEAD"), method = "setRemainingFireTicks", cancellable = true)
    public void setRemainingFireTicks(int ticks, CallbackInfo callback) {
        if (SoulFire.isBurning(((Entity)(Object)this))) {
            callback.cancel();
            SoulFire.setBurning((Entity)(Object)this, ticks);
        }
    }

    @Inject(at = @At("HEAD"), method = "getRemainingFireTicks", cancellable = true)
    public void getRemainingFireTicks(CallbackInfoReturnable<Integer> callback) {
        if (SoulFire.isBurning(((Entity)(Object)this))) {
            callback.cancel();
            callback.setReturnValue(SoulFire.getBurningTicks((Entity)(Object)this));
        }
    }

    @Inject(at = @At("HEAD"), method = "setSecondsOnFire", cancellable = true)
    public void setSecondsOnFire(int seconds, CallbackInfo callback) {
        if (SoulFire.isBurning(((Entity)(Object)this))) {
            callback.cancel();
            int i = seconds * 20;
            if ((Object) this instanceof LivingEntity) {
                i = ProtectionEnchantment.getFireAfterDampener((LivingEntity) (Object) this, i);
            }
            if (this.getRemainingFireTicks() < i) {
                this.setRemainingFireTicks(i);
            }
        }
    }

}
