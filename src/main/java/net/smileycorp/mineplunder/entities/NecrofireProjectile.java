package net.smileycorp.mineplunder.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.smileycorp.mineplunder.api.capability.MineplunderCapabilities;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import net.smileycorp.mineplunder.capability.InfernalSoulFire;
import net.smileycorp.mineplunder.init.MineplunderBlocks;
import net.smileycorp.mineplunder.init.MineplunderEntities;

public class NecrofireProjectile extends AbstractHurtingProjectile implements FakeBlock {

    protected BlockPos startPos = new BlockPos(0, 0, 0);

    public NecrofireProjectile(EntityType<? extends NecrofireProjectile> type, Level level) {
        super(type, level);
    }

    public NecrofireProjectile(Entity owner, BlockPos pos) {
       this(MineplunderEntities.NECROFIRE.get(), owner.level());
       setOwner(owner);
       setPos(pos.getCenter());
       startPos = pos;
    }

    protected void onHitEntity(EntityHitResult p_37386_) {
        super.onHitEntity(p_37386_);
        if (!this.level().isClientSide) {
            Entity entity = p_37386_.getEntity();
            Entity owner = this.getOwner();
            if (!owner.isAlliedTo(entity)) SpecialFire.setBurning(entity, 60, SpecialFire.FireType.NECROFIRE);
        }
    }

    public void tick() {
        super.tick();
        setDeltaMovement(0, 0.5*Math.sin(tickCount/2), 0);
        if (this.tickCount >= 40) discard();
    }

    public boolean isPickable() {
        return false;
    }

    public boolean hurt(DamageSource p_37381_, float p_37382_) {
        return false;
    }

    @Override
    public BlockState getBlockState() {
        return MineplunderBlocks.NECROFIRE.get().defaultBlockState();
    }

    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public BlockPos getStartPos() {
        return startPos;
    }

}
