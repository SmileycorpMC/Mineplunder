package net.smileycorp.mineplunder.events;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.api.MineplunderDamageTags;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import net.smileycorp.mineplunder.entities.InfernalSoul;
import net.smileycorp.mineplunder.entities.Witherwight;

@EventBusSubscriber(modid= Constants.MODID)
public class TweakEvents {

	@SubscribeEvent
	public static void spawnEntity(MobSpawnEvent.FinalizeSpawn event) {
		LivingEntity entity = event.getEntity();
		Level level = entity.level();
		if(!level.isClientSide) {
			if (entity.getType() == EntityType.SKELETON && level.dimensionType().ultraWarm() &&
					(event.getSpawnType() == MobSpawnType.NATURAL || event.getSpawnType() == MobSpawnType.SPAWNER
							|| event.getSpawnType() == MobSpawnType.STRUCTURE)) {
				Vec3 pos = entity.position();
				Witherwight newentity = new Witherwight(level);
				newentity.setPos(pos.x, pos.y, pos.z);
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					newentity.setItemSlot(slot, entity.getItemBySlot(slot));
				}
				if (entity.hasCustomName()) newentity.setCustomName(entity.getCustomName());
				level.addFreshEntity(newentity);
				if (entity.getRandom().nextFloat() < 0.05F) {
					newentity.setLeftHanded(true);
				} else {
					newentity.setLeftHanded(false);
				}
				newentity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
				event.setSpawnCancelled(true);
			}
			if (entity.getType() == EntityType.BLAZE && (event.getSpawnType() == MobSpawnType.NATURAL
					|| event.getSpawnType() == MobSpawnType.STRUCTURE) && entity.getRandom().nextFloat() < 0.1f) {
				Vec3 pos = entity.position();
				InfernalSoul newentity = new InfernalSoul(level);
				newentity.setPos(pos.x, pos.y, pos.z);
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					newentity.setItemSlot(slot, entity.getItemBySlot(slot));
				}
				if (entity.hasCustomName()) newentity.setCustomName(entity.getCustomName());
				level.addFreshEntity(newentity);
				if (entity.getRandom().nextFloat() < 0.05F) {
					newentity.setLeftHanded(true);
				} else {
					newentity.setLeftHanded(false);
				}
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
		if (level.dimensionType().ultraWarm()) SpecialFire.setBurning(entity, 100, SpecialFire.FireType.SOUL_FIRE);
	}

	@SubscribeEvent
	public static void onDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		Level level = entity.level();
		if(!level.isClientSide) {
			if ((event.getSource().is(MineplunderDamageTags.SOUL_DAMAGE) || SpecialFire.getFireType(entity) == SpecialFire.FireType.SOUL_FIRE)
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
				SpecialFire.FireType type = SpecialFire.getFireType(entity);
				level.setBlock(pos, (type == null ? Blocks.FIRE : type.getBlock()).defaultBlockState(), 2);
			}
		} if (hit instanceof EntityHitResult) {
			Entity hitEntity = ((EntityHitResult) hit).getEntity();
			SpecialFire.FireType type = SpecialFire.getFireType(entity);
			if (type != null) SpecialFire.setFireType(hitEntity, type);
		}
	}

}
