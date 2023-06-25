package net.smileycorp.mineplunder.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.smileycorp.mineplunder.init.MineplunderEntities;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Skelliger extends AbstractSkeleton implements TraceableEntity {

    protected Entity owner;

    protected int spawnTicks;

    public Skelliger(Level level) {
        this(MineplunderEntities.SKELLIGER.get(), level);
    }

    public Skelliger(EntityType<? extends Skelliger> type, Level level) {
        super(type, level);
    }

    protected void populateDefaultEquipmentSlots(RandomSource p_219154_, DifficultyInstance p_219155_) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_33579_) {
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }


    public void aiStep() {
        if (level().isClientSide) {
           level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0, 0.0D);
        }
        super.aiStep();
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return owner;
    }

    public void setOwner(Entity entity) {

    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (spawnTicks > 0) return false;
        return super.hurt(source, amount);
    }
}
