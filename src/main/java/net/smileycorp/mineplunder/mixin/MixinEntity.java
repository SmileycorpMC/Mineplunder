package net.smileycorp.mineplunder.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Level;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
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

    @Shadow public abstract void setRemainingFireTicks(int p_20269_);

    @Shadow public abstract DamageSources damageSources();

    @Shadow public abstract boolean hurt(DamageSource p_19946_, float p_19947_);

    @Shadow private Level level;

    @Shadow public abstract int getRemainingFireTicks();

    @Inject(at = @At("TAIL"), method = "setRemainingFireTicks", cancellable = true)
    public void setRemainingFireTicks(int ticks, CallbackInfo callback) {
       if (this.getRemainingFireTicks() <= 0 &! level.isClientSide) SpecialFire.setFireType((Entity)(Object)this, null);
    }

    @Inject(at = @At("HEAD"), method = "clearFire", cancellable = true)
    public void clearFire(CallbackInfo callback) {
        SpecialFire.FireType type = SpecialFire.getFireType((Entity) (Object) this);
        if (!(type == null || type.canBeExtinguished())) callback.cancel();
    }

    @Inject(at = @At("HEAD"), method = "setSecondsOnFire", cancellable = true)
    public void setSecondsOnFire(int seconds, CallbackInfo callback) {
        callback.cancel();
        int i = seconds * 20;
        SpecialFire.FireType type = SpecialFire.getFireType((Entity) (Object) this);
        if ((Object) this instanceof LivingEntity && (type == null || type.canBeExtinguished())) {
            i = ProtectionEnchantment.getFireAfterDampener((LivingEntity) (Object) this, i);
        }
        if (this.getRemainingFireTicks() < i) {
            this.setRemainingFireTicks(i);
        }
    }

    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    public boolean hurt(Entity entity, DamageSource source, float amount) {
        SpecialFire.FireType type = SpecialFire.getFireType(entity);
        if (type != null && source == damageSources().onFire()) {
            SpecialFire.FireDamageGetter getter = new SpecialFire.FireDamageGetter(source, amount, entity);
            type.accept(getter);
            source = getter.getSource();
            amount = getter.getAmount();
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

    @Inject(at = @At("HEAD"), method = "getTicksFrozen", cancellable = true)
    public void getTicksFrozen(CallbackInfoReturnable<Integer> callback) {
        if (!((Object)this instanceof LivingEntity)) return;
        if (!((LivingEntity)(Object)this).hasEffect(MineplunderEffects.FROSTBITE.get())) return;
        callback.setReturnValue(500);
        callback.cancel();
    }

}
