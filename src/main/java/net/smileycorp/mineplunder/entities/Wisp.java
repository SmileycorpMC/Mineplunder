package net.smileycorp.mineplunder.entities;

import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.smileycorp.mineplunder.MineplunderDamageSources;

public class Wisp extends AbstractHurtingProjectile {
    
    public Wisp(EntityType<? extends Wisp> type, Level level) {
        super(type, level);
    }
    
    @Override
    protected void onHitBlock(BlockHitResult result) {
        explode();
    }
    
    @Override
    protected void onHitEntity(EntityHitResult result) {
        explode();
    }
    
    private void explode() {
        if (level().isClientSide) {
            Explosion
            return;
        }
        for (Entity entity : level().getEntities(this, getBoundingBox().inflate(3)))
            entity.hurt(level().damageSources().magic(), 8f / distanceTo(entity));
    }
    
}
