package net.smileycorp.mineplunder.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.smileycorp.mineplunder.init.MineplunderEntities;

import javax.annotation.Nullable;

public class Skelliger extends NecromancerMinion implements RangedAttackMob {

    public Skelliger(Level level) {
        this(MineplunderEntities.SKELLIGER.get(), level);
    }

    public Skelliger(EntityType<? extends Skelliger> type, Level level) {
        super(type, level);
    }

    protected void populateDefaultEquipmentSlots(RandomSource p_219154_, DifficultyInstance p_219155_) {
        switch (random.nextInt(6)) {
            case 0:
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_AXE));
                break;
            case 1:
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_HOE));
                break;
            case 2:
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
                break;
            case 3:
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
                break;
            case 4:
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
                break;
            case 5:
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BONE));
                break;
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_32146_, DifficultyInstance p_32147_, MobSpawnType p_32148_, @Nullable SpawnGroupData p_32149_, @Nullable CompoundTag p_32150_) {
        p_32149_ = super.finalizeSpawn(p_32146_, p_32147_, p_32148_, p_32149_, p_32150_);
        RandomSource randomsource = p_32146_.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, p_32147_);
        this.populateDefaultEquipmentEnchantments(randomsource, p_32147_);
        this.setCanPickUpLoot(randomsource.nextFloat() < 0.55F * p_32147_.getSpecialMultiplier());
        return p_32149_;
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

    @Override
    public int getReanimationTime() {
        return 60;
    }

    @Override
    public int getDispelTime() {
        return 60;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    @Override
    public void performRangedAttack(LivingEntity p_33317_, float p_33318_) {}
}
