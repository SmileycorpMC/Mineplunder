package net.smileycorp.mineplunder.events;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.smileycorp.mineplunder.Constants;
import net.smileycorp.mineplunder.Mineplunder;
import net.smileycorp.mineplunder.MineplunderEnchantments;
import net.smileycorp.mineplunder.api.MineplunderDamageTags;
import net.smileycorp.mineplunder.api.capability.SoulFire;

@EventBusSubscriber(modid= Constants.MODID)
public class EnchantmentEvents {

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide || entity instanceof Player) return;
        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        if (attacker instanceof LivingEntity) {
            int level = EnchantmentHelper.getEnchantmentLevel(MineplunderEnchantments.SOULBLAZE.get(), (LivingEntity)attacker);
            if (level > 0) SoulFire.setBurning(entity, 80*level);
        }
    }

    @SubscribeEvent
    public static void onAttack(AttackEntityEvent event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        if (player.level().isClientSide || target == null) return;
        int level = EnchantmentHelper.getEnchantmentLevel(MineplunderEnchantments.SOULBLAZE.get(), player);
        if (level > 0) SoulFire.setBurning(target, 80*level);
    }

}
