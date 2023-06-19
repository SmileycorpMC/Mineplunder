package net.smileycorp.mineplunder.common.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.smileycorp.mineplunder.api.capability.SoulFire;
import net.smileycorp.mineplunder.common.Constants;
import net.smileycorp.mineplunder.common.DamageSources;

@EventBusSubscriber(modid= Constants.MODID)
public class TweakEvents {

	@SubscribeEvent
	public static void livingUpdate(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		Level level = entity.level();
		if(level.isClientSide) return;
		if (entity instanceof AbstractSkeleton &! entity.isOnFire() &! entity.fireImmune()) SoulFire.setBurning(entity, 100);
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		Level level = entity.level();
		if(!level.isClientSide) {
			if (event.getSource().is(DamageSources.SOUL_FIRE) && entity instanceof AbstractSkeleton) {
				BlockPos pos = entity.blockPosition();
				WitherSkeleton newentity = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
				newentity.setPos(pos.getX(), pos.getY(), pos.getZ());
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					newentity.setItemSlot(slot, entity.getItemBySlot(slot));
				}
				if (entity.hasCustomName()) newentity.setCustomName(entity.getCustomName());
				level.addFreshEntity(newentity);
			}
		}
	}

	@SubscribeEvent
	public static void arrowCollide(ProjectileImpactEvent event) {
		Projectile entity = event.getProjectile();
		Level level = entity.level();
		if(level.isClientSide) {
			if(entity.isAddedToWorld()) {
				if (event.getEntity().isOnFire()) {
					HitResult hit = event.getRayTraceResult();
					if (hit instanceof BlockHitResult) {
						BlockHitResult blockHit = (BlockHitResult) hit;
						BlockPos pos = blockHit.getBlockPos().relative(blockHit.getDirection());
						if (level.getBlockState(pos).isAir()) {
							level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
						}
					}
				}
			}
		}
	}

}
