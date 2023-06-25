package net.smileycorp.mineplunder.entities;

import com.google.common.collect.Lists;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.level.Level;
import net.smileycorp.mineplunder.init.MineplunderEntities;
import net.smileycorp.mineplunder.init.MineplunderParticles;

import java.util.List;

public class Necromancer extends AbstractIllager {

    private static final EntityDataAccessor<Boolean> SPELLCASTING = SynchedEntityData.defineId(Necromancer.class, EntityDataSerializers.BOOLEAN);

    protected final List<NecromancerMinion> minions = Lists.newArrayList();

    public Necromancer(EntityType<? extends Necromancer> type, Level level) {
        super(type, level);
    }

    public Necromancer(Level level) {
        this(MineplunderEntities.NECROMANCER.get(), level);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPELLCASTING, false);
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
    }

    @Override
    public void applyRaidBuffs(int wave, boolean p_37845_) {}

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.EVOKER_CELEBRATE;
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
