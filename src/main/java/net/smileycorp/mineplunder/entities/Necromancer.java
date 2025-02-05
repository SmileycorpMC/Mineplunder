package net.smileycorp.mineplunder.entities;

import com.google.common.collect.Lists;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.smileycorp.mineplunder.entities.ai.DispelMinionsGoal;
import net.smileycorp.mineplunder.entities.ai.NecrofireSpell;
import net.smileycorp.mineplunder.entities.ai.SummonSkelligerSpell;
import net.smileycorp.mineplunder.init.MineplunderEntities;
import net.smileycorp.mineplunder.init.MineplunderParticles;

import java.util.List;

public class Necromancer extends AbstractIllager {

    private static final EntityDataAccessor<Boolean> SPELLCASTING = SynchedEntityData.defineId(Necromancer.class, EntityDataSerializers.BOOLEAN);

    protected final List<NecromancerMinion> minions = Lists.newArrayList();

    public Necromancer(EntityType<? extends Necromancer> type, Level level) {
        super(type, level);
        xpReward = 10;
    }

    public boolean isAlliedTo(Entity entity) {
        if (entity == null) {
            return false;
        } else if (entity == this) {
            return true;
        } else if (super.isAlliedTo(entity)) {
            return true;
        } else if (entity instanceof TraceableEntity) {
            return this.isAlliedTo(((TraceableEntity)entity).getOwner());
        } else if (entity.getType().is(EntityTypeTags.RAIDERS)) {
            return this.getTeam() == null && entity.getTeam() == null;
        } else {
            return false;
        }
    }

    public Necromancer(Level level) {
        this(MineplunderEntities.NECROMANCER.get(), level);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SPELLCASTING, false);
    }

    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6D, 1.0D));
        goalSelector.addGoal(2, new AvoidEntityGoal<>(this, IronGolem.class, 8.0F, 0.6D, 1.0D));
        goalSelector.addGoal(3, new DispelMinionsGoal(this));
        goalSelector.addGoal(4, new NecrofireSpell(this));
        goalSelector.addGoal(5, new SummonSkelligerSpell(this));
        goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && isCastingSpell() && tickCount%3==0) {
            float f = this.yBodyRot * ((float)Math.PI / 180F) + Mth.cos((float)this.tickCount * 0.6662F) * 0.25F;
            float f1 = Mth.cos(f);
            float f2 = Mth.sin(f);
            level().addParticle(MineplunderParticles.NECROFLAME.get(), this.getX() + (double)f1 * 0.6D, this.getY() + 1.8D, this.getZ() + (double)f2 * 0.6D, 0, 0, 0);
            level().addParticle(MineplunderParticles.NECROFLAME.get(), this.getX() - (double)f1 * 0.6D, this.getY() + 1.8D, this.getZ() - (double)f2 * 0.6D, 0, 0, 0);
        }
    }

    public void die(DamageSource source) {
        super.die(source);
        for (NecromancerMinion minion : minions) minion.dispel();
    }

    public void summonMinion(NecromancerMinion minion) {
        minions.add(minion);
        minion.setOwner(this);
        minion.reanimate();
        minion.finalizeSpawn((ServerLevel)level(), level().getCurrentDifficultyAt(minion.getOnPos()), MobSpawnType.REINFORCEMENT, null, null);
        level().addFreshEntity(minion);
    }

    public List<NecromancerMinion> getMinions() {
        return minions;
    }

    @Override
    public void applyRaidBuffs(int wave, boolean p_37845_) {}

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.EVOKER_CELEBRATE;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.EVOKER_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.EVOKER_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_32654_) {
        return SoundEvents.EVOKER_HURT;
    }

    public void setSpellcasting(boolean spellcasting) {
        entityData.set(SPELLCASTING, spellcasting);
    }

    public boolean isCastingSpell() {
        return entityData.get(SPELLCASTING);
    }

    public AbstractIllager.IllagerArmPose getArmPose() {
        if (isCastingSpell()) return IllagerArmPose.SPELLCASTING;
        if (isCelebrating()) return IllagerArmPose.CELEBRATING;
        return IllagerArmPose.CROSSED;
    }

}
