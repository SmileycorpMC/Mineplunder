package net.smileycorp.mineplunder.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.smileycorp.mineplunder.api.capability.MineplunderCapabilities;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import net.smileycorp.mineplunder.capability.InfernalSoulFire;
import net.smileycorp.mineplunder.init.MineplunderEntities;

public class SmallSoulFireball extends Fireball {

    private final SpecialFire soulfire_capability = new InfernalSoulFire();

    public SmallSoulFireball(EntityType<? extends SmallSoulFireball> p_37364_, Level p_37365_) {
        super(p_37364_, p_37365_);
    }

    public SmallSoulFireball(Level p_37375_, LivingEntity p_37376_, double p_37377_, double p_37378_, double p_37379_) {
        super(MineplunderEntities.SMALL_SOUL_FIREBALL.get(), p_37376_, p_37377_, p_37378_, p_37379_, p_37375_);
    }

    public SmallSoulFireball(Level p_37367_, double p_37368_, double p_37369_, double p_37370_, double p_37371_, double p_37372_, double p_37373_) {
        super(MineplunderEntities.SMALL_SOUL_FIREBALL.get(), p_37368_, p_37369_, p_37370_, p_37371_, p_37372_, p_37373_, p_37367_);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == MineplunderCapabilities.SPECIAL_FIRE_CAPABILITY ? LazyOptional.of(() -> soulfire_capability).cast()
                : super.getCapability(cap, side);
    }


    protected void onHitEntity(EntityHitResult p_37386_) {
        super.onHitEntity(p_37386_);
        if (!this.level().isClientSide) {
            Entity entity = p_37386_.getEntity();
            Entity owner = this.getOwner();
            int i = entity.getRemainingFireTicks();
            SpecialFire.setBurning(entity, 100, SpecialFire.FireType.SOUL_FIRE);
            if (!entity.hurt(this.damageSources().fireball(this, owner), 5.0F)) {
                SpecialFire.setBurning(entity, 3, SpecialFire.FireType.SOUL_FIRE);
            } else if (owner instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)owner, entity);
            }

        }
    }

    protected void onHitBlock(BlockHitResult p_37384_) {
        super.onHitBlock(p_37384_);
        if (!this.level().isClientSide) {
            Entity entity = this.getOwner();
            if (!(entity instanceof Mob) || ForgeEventFactory.getMobGriefingEvent(this.level(), entity)) {
                BlockPos blockpos = p_37384_.getBlockPos().relative(p_37384_.getDirection());
                if (this.level().isEmptyBlock(blockpos)) {
                    this.level().setBlock(blockpos, Blocks.SOUL_FIRE.defaultBlockState(), 2);
                }
            }

        }
    }

    protected void onHit(HitResult p_37388_) {
        super.onHit(p_37388_);
        if (!this.level().isClientSide) {
            this.discard();
        }

    }

    public boolean isPickable() {
        return false;
    }

    public boolean hurt(DamageSource p_37381_, float p_37382_) {
        return false;
    }

}
