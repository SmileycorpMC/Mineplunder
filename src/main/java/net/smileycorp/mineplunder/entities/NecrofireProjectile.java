package net.smileycorp.mineplunder.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import net.smileycorp.mineplunder.init.MineplunderBlocks;
import net.smileycorp.mineplunder.init.MineplunderEntities;

public class NecrofireProjectile extends AbstractHurtingProjectile implements FakeBlock {

    protected BlockPos startPos = new BlockPos(0, 0, 0);

    public NecrofireProjectile(EntityType<? extends NecrofireProjectile> type, Level level) {
        super(type, level);
    }

    public NecrofireProjectile(Entity owner, Vec3 pos) {
       this(MineplunderEntities.NECROFIRE.get(), owner.level());
       setOwner(owner);
       setPos(pos);
       startPos = BlockPos.containing(pos);
    }

    protected void onHitEntity(EntityHitResult p_37386_) {
        super.onHitEntity(p_37386_);
        if (!this.level().isClientSide) {
            Entity entity = p_37386_.getEntity();
            Entity owner = this.getOwner();
            if (!owner.isAlliedTo(entity)) {
                SpecialFire.setBurning(entity, 60, SpecialFire.FireType.NECROFIRE);
                entity.push(0, 1, 0);
            }
        }
    }

    public void tick() {
        super.tick();
        setDeltaMovement(0, 0.5*Math.sin(tickCount/2), 0);
        if (this.tickCount >= 10) discard();
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
