package net.smileycorp.mineplunder.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Level;
import net.smileycorp.mineplunder.api.MineplunderEntityTags;
import net.smileycorp.mineplunder.api.capability.SoulFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Arrow.class)
public abstract class MixinArrow extends AbstractArrow {

    protected MixinArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)V", cancellable = true)
    private void arrow(Level level, LivingEntity owner, CallbackInfo callback) {
        if (owner.getType().is(MineplunderEntityTags.NETHER_SKELETONS)) {
            SoulFire.setBurning(this, 2000);
        }
    }

}
