package net.smileycorp.mineplunder.events;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.smileycorp.mineplunder.Constants;

@EventBusSubscriber(modid= Constants.MODID)
public class EnchantmentEvents {

    /*@SubscribeEvent
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
    }*/

}
