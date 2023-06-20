package net.smileycorp.mineplunder.events;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.MineplunderEnchantments;
import net.smileycorp.mineplunder.api.MineplunderBlockTags;
import net.smileycorp.mineplunder.api.MineplunderDamageTags;
import net.smileycorp.mineplunder.api.MineplunderEntityTags;
import net.smileycorp.mineplunder.api.capability.SoulFire;

@EventBusSubscriber(modid= Constants.MODID)
public class TweakEvents {

	@SubscribeEvent
	public static void spawnEntity(MobSpawnEvent.FinalizeSpawn event) {
		LivingEntity entity = event.getEntity();
		Level level = entity.level();
		if(!level.isClientSide) {
			if (entity.getType() == EntityType.SKELETON) {
				Vec3 pos = entity.position();
				WitherSkeleton newentity = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
				newentity.setPos(pos.x, pos.y, pos.z);
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					newentity.setItemSlot(slot, entity.getItemBySlot(slot));
				}
				if (entity.hasCustomName()) newentity.setCustomName(entity.getCustomName());
				level.addFreshEntity(newentity);
				event.setSpawnCancelled(true);
			}
		}
	}

	@SubscribeEvent
	public static void livingUpdate(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		Level level = entity.level();
		if(level.isClientSide) return;
		if (!entity.getType().is(EntityTypeTags.SKELETONS) || entity.isOnFire() || entity.fireImmune()) return;
		if (level.dimensionType().ultraWarm()) SoulFire.setBurning(entity, 100);
	}

	@SubscribeEvent
	public static void onHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity.level().isClientSide || entity instanceof Player) return;
		DamageSource source = event.getSource();
		Entity attacker = source.getDirectEntity();
		if (attacker instanceof Projectile && SoulFire.isAblaze(attacker)) {
			SoulFire.setBurning(entity, 100);
		}
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		Level level = entity.level();
		if(!level.isClientSide) {
			if ((event.getSource().is(MineplunderDamageTags.SOUL_DAMAGE) || SoulFire.isAblaze(entity))
					&& entity.getType().is(EntityTypeTags.SKELETONS)) {
				Vec3 pos = entity.position();
				WitherSkeleton newentity = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
				newentity.setPos(pos.x, pos.y, pos.z);
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					newentity.setItemSlot(slot, entity.getItemBySlot(slot));
				}
				if (entity.hasCustomName()) newentity.setCustomName(entity.getCustomName());
				level.addFreshEntity(newentity);
				entity.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	@SubscribeEvent
	public static void arrowCollide(ProjectileImpactEvent event) {
		Projectile entity = event.getProjectile();
		Level level = entity.level();
		if(level.isClientSide |! entity.isAddedToWorld()) return;
		if (!event.getEntity().isOnFire()) return;
		HitResult hit = event.getRayTraceResult();
		if (hit instanceof BlockHitResult) {
			BlockHitResult blockHit = (BlockHitResult) hit;
			BlockPos pos = blockHit.getBlockPos().relative(blockHit.getDirection());
			if (level.getBlockState(pos).isAir()) {
				level.setBlockAndUpdate(pos, (SoulFire.isAblaze(entity) ? Blocks.SOUL_FIRE : Blocks.FIRE).defaultBlockState());
			}
		}
	}

}
