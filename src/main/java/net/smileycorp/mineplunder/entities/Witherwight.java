package net.smileycorp.mineplunder.entities;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.smileycorp.mineplunder.MineplunderEntities;
import net.smileycorp.mineplunder.api.capability.SoulFire;

public class Witherwight extends AbstractSkeleton {

    private AbstractArrow capturedArrow;

    public Witherwight(EntityType<? extends Witherwight> type, Level level) {
        super(type, level);
    }

    public Witherwight(Level level) {
        super(MineplunderEntities.WITHERWIGHT.get(), level);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_34195_) {
        return SoundEvents.WITHER_SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_SKELETON_DEATH;
    }

    protected SoundEvent getStepSound() { return SoundEvents.WITHER_SKELETON_STEP; }

    protected void populateDefaultEquipmentSlots(RandomSource p_219154_, DifficultyInstance p_219155_) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    }

    protected float getStandingEyeHeight(Pose p_34186_, EntityDimensions p_34187_) {
        return 1.74F;
    }

    protected AbstractArrow getArrow(ItemStack p_34189_, float p_34190_) {
        AbstractArrow abstractarrow = super.getArrow(p_34189_, p_34190_);
        capturedArrow = abstractarrow;
        return abstractarrow;
    }

    public void performRangedAttack(LivingEntity p_32141_, float p_32142_) {
       super.performRangedAttack(p_32141_, p_32142_);
       if (capturedArrow != null) {
           SoulFire.setBurning(capturedArrow, 2000);
           capturedArrow = null;
       }
    }

}
