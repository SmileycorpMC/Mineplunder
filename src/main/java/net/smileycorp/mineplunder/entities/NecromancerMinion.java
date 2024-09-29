package net.smileycorp.mineplunder.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.smileycorp.mineplunder.init.MineplunderParticles;

public abstract class NecromancerMinion extends Monster implements TraceableEntity {

    private static final EntityDataAccessor<Byte> ANIM_STATE = SynchedEntityData.defineId(Necromancer.class, EntityDataSerializers.BYTE);

    protected Necromancer owner;
    protected int animTime;

    protected void registerGoals() {
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public NecromancerMinion(EntityType<? extends NecromancerMinion> type, Level level) {
        super(type, level);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIM_STATE, (byte)0);
    }

    protected boolean isImmobile() {
        return super.isImmobile() && getAnimState() == AnimState.NONE;
    }

    public boolean isAlive() {
        return getAnimState() == AnimState.NONE && super.isAlive();
    }

    public Necromancer getOwner() {
        return owner;
    }

    public void setOwner(Necromancer entity) {
        owner = entity;
    }

    public void aiStep() {
        if (animTime > 0 &! level().isClientSide) {
            animTime--;
            if (animTime <= 0) {
                if(getAnimState() == AnimState.DISPELLING) discard();
                setAnimState(AnimState.NONE);
            }
        }
        if (level().isClientSide && getAnimState() == AnimState.NONE) {
            if (tickCount % 4 == 0) level().addParticle(MineplunderParticles.NECROFLAME.get(),
                    this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0, 0.0D);
        }
        super.aiStep();
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public void reanimate() {
        animTime = getReanimationTime();
        setAnimState(AnimState.REANIMATING);
    }

    public abstract int getReanimationTime();

    public void dispel() {
        animTime = getDispelTime();
        setAnimState(AnimState.DISPELLING);
        goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (getAnimState() != AnimState.NONE) return false;
        return super.hurt(source, amount);
    }

    public abstract int getDispelTime();

    protected abstract SoundEvent getStepSound();

    protected void playStepSound(BlockPos p_32159_, BlockState p_32160_) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    public AnimState getAnimState() {
        return AnimState.values()[entityData.get(ANIM_STATE)];
    }

    public void setAnimState(AnimState state) {
        entityData.set(ANIM_STATE,(byte)(state == null ? 0 : state.ordinal()));
    }

    public enum AnimState {
        NONE,
        REANIMATING,
        DISPELLING;
    }

}
