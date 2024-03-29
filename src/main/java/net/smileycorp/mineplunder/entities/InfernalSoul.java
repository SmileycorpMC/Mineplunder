package net.smileycorp.mineplunder.entities;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.smileycorp.mineplunder.api.capability.MineplunderCapabilities;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import net.smileycorp.mineplunder.capability.InfernalSoulFire;
import net.smileycorp.mineplunder.init.MineplunderEntities;
import net.smileycorp.mineplunder.init.MineplunderItems;

public class InfernalSoul extends Blaze {

    private final SpecialFire soulfire_capability = new InfernalSoulFire();

    public InfernalSoul(EntityType<? extends InfernalSoul> type, Level level) {
        super(type, level);
    }

    public InfernalSoul(Level level) {
        this(MineplunderEntities.INFERNAL_SOUL.get(), level);
    }

    protected float getStandingEyeHeight(Pose p_34186_, EntityDimensions p_34187_) {
        return 1.3F;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(4, new InfernalSoulAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public void aiStep() {
        if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }

        if (this.level().isClientSide) {
            if (this.random.nextInt(24) == 0 && !this.isSilent()) {
                this.level().playLocalSound(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D, SoundEvents.BLAZE_BURN, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
            }

            for(int i = 0; i < 3; ++i) {
                this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getRandomX(0.8D), this.getRandomY(), this.getRandomZ(0.8D), 0.0D, 0.05D, 0.0D);
            }
        }
        super.aiStep();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Blaze.createAttributes().add(Attributes.MAX_HEALTH, 35);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == MineplunderCapabilities.SPECIAL_FIRE_CAPABILITY ? LazyOptional.of(() -> soulfire_capability).cast()
                : super.getCapability(cap, side);
    }

    public static class InfernalSoulAttackGoal extends BlazeAttackGoal {

        public InfernalSoulAttackGoal(InfernalSoul entity) {
            super(entity);
        }

        public void tick() {
            --this.attackTime;
            LivingEntity livingentity = this.blaze.getTarget();
            if (livingentity != null) {
                boolean flag = this.blaze.getSensing().hasLineOfSight(livingentity);
                if (flag) {
                    this.lastSeen = 0;
                } else {
                    ++this.lastSeen;
                }

                double d0 = this.blaze.distanceToSqr(livingentity);
                if (d0 < 4.0D) {
                    if (!flag) {
                        return;
                    }

                    if (this.attackTime <= 0) {
                        this.attackTime = 20;
                        this.blaze.doHurtTarget(livingentity);
                    }

                    this.blaze.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0D);
                } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
                    double d1 = livingentity.getX() - this.blaze.getX();
                    double d2 = livingentity.getY(0.5D) - this.blaze.getY(0.3D);
                    double d3 = livingentity.getZ() - this.blaze.getZ();
                    if (this.attackTime <= 0) {
                        ++this.attackStep;
                        if (this.attackStep == 1) {
                            this.attackTime = 60;
                            this.blaze.setCharged(true);
                        } else if (this.attackStep <= 4) {
                            this.attackTime = 6;
                        } else {
                            this.attackTime = 100;
                            this.attackStep = 0;
                            this.blaze.setCharged(false);
                        }

                        if (this.attackStep > 1) {
                            double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;
                            if (!this.blaze.isSilent()) {
                                this.blaze.level().levelEvent((Player)null, 1018, this.blaze.blockPosition(), 0);
                            }

                            for(int i = 0; i < 5; ++i) {
                                SmallSoulFireball smallfireball = new SmallSoulFireball(this.blaze.level(), this.blaze, this.blaze.getRandom().triangle(d1, 2.297D * d4), d2, this.blaze.getRandom().triangle(d3, 2.297D * d4));
                                smallfireball.setPos(smallfireball.getX(), this.blaze.getY(0.3D) + 0.5D, smallfireball.getZ());
                                smallfireball.setItem(new ItemStack(MineplunderItems.SOUL_CHARGE.get()));
                                this.blaze.level().addFreshEntity(smallfireball);
                            }
                        }
                    }

                    this.blaze.getLookControl().setLookAt(livingentity, 10.0F, 10.0F);
                } else if (this.lastSeen < 5) {
                    this.blaze.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), 1.0D);
                }

                super.tick();
            }
        }
    }
}
