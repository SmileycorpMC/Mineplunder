package net.smileycorp.mineplunder.entities;

import com.google.common.base.Predicates;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.smileycorp.atlas.api.util.DirectionUtils;
import net.smileycorp.mineplunder.api.capability.MineplunderCapabilities;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import net.smileycorp.mineplunder.capability.InfernalSoulFire;
import net.smileycorp.mineplunder.init.MineplunderEntities;
import net.smileycorp.mineplunder.init.MineplunderItems;

public class InfernalSoul extends Blaze {

    private final SpecialFire cap = new InfernalSoulFire();

    public InfernalSoul(EntityType<? extends InfernalSoul> type, Level level) {
        super(type, level);
    }

    public InfernalSoul(Level level) {
        this(MineplunderEntities.INFERNAL_SOUL.get(), level);
    }

    protected float getStandingEyeHeight(Pose p_34186_, EntityDimensions dimensions) {
        return 1.3f;
    }

    protected void registerGoals() {
        goalSelector.addGoal(4, new InfernalSoulAttackGoal(this));
        goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1, 0));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public void aiStep() {
        if (!onGround() && getDeltaMovement().y < 0) setDeltaMovement(getDeltaMovement().multiply(1, 0.6, 1));
        if (tickCount % 5 == 0) for(Entity entity : level ().getEntities(this, getBoundingBox().inflate(0.5), Predicates
                .not(Entity::fireImmune)))
            SpecialFire.setBurning(entity, 60, SpecialFire.FireType.SOUL_FIRE);
        if (level().isClientSide) {
            if (random.nextInt(24) == 0 && !isSilent()) level().playLocalSound(getX() + 0.5, getY() + 0.5, getZ() + 0.5,
                    SoundEvents.BLAZE_BURN, getSoundSource(), 1 + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
            for(int i = 0; i < 3; ++i) level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, getRandomX(0.8), getRandomY(), getRandomZ(0.8),
                    0, 0, 0);
        }
        super.aiStep();
    }
    
    @Override
    public boolean isOnFire() {
        return getTarget() != null;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Blaze.createAttributes().add(Attributes.MAX_HEALTH, 35);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == MineplunderCapabilities.SPECIAL_FIRE_CAPABILITY ? LazyOptional.of(() -> this.cap).cast()
                : super.getCapability(cap, side);
    }

    public static class InfernalSoulAttackGoal extends BlazeAttackGoal {

        public InfernalSoulAttackGoal(InfernalSoul entity) {
            super(entity);
        }
        
        @Override
        public void start() {
            this.attackStep = 60;
        }
        
        @Override
        public void tick() {
            if (attackTime > 0) attackTime--;
            LivingEntity target = blaze.getTarget();
            if (target == null) return;
            if (target.isDeadOrDying()) return;
            boolean canSeeTarget = blaze.getSensing().hasLineOfSight(target);
            if (canSeeTarget) lastSeen = 0;
            else lastSeen++;
            if (lastSeen > 5) {
                blaze.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1);
                return;
            }
            blaze.getLookControl().setLookAt(target);
            if (attackTime > 0 || (attackStep >= 60 &! canSeeTarget)) return;
            if (attackStep == 60) blaze.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER);
            if (attackStep <= 40 && attackStep % 10 == 0) {
                blaze.playSound(SoundEvents.DRAGON_FIREBALL_EXPLODE);
                for (int i = 0; i < 5; ++i) {
                    RandomSource rand = blaze.getRandom();
                    Vec3 dir = DirectionUtils.getDirectionVec(blaze.getEyePosition(), blaze.getTarget().getEyePosition());
                    SmallSoulFireball fireball = new SmallSoulFireball(blaze.level(), blaze, dir.x * (rand.nextFloat() * 0.25),
                            dir.y * (rand.nextFloat() * 0.25), dir.z * (rand.nextFloat() * 0.25));
                    fireball.setItem(new ItemStack(MineplunderItems.SOUL_CHARGE.get()));
                    fireball.setPos(blaze.getX(), blaze.getY() + blaze.getEyeHeight(), blaze.getZ());
                    blaze.level().addFreshEntity(fireball);
                }
            }
            if (attackStep-- < 0) {
                attackStep = 60;
                attackTime = 120;
            }
        }
    }
}
