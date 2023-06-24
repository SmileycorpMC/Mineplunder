package net.smileycorp.mineplunder.enchantments;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.smileycorp.mineplunder.api.capability.SpecialFire;
import net.smileycorp.mineplunder.init.MineplunderEnchantments;

public class SoulblazeEnchantment extends Enchantment {
    public SoulblazeEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public int getMinCost(int level) {
        return 30 + 20 * (level - 1);
    }

    public int getMaxCost(int p_45002_) {
        return super.getMinCost(p_45002_) + 50;
    }

    public int getMaxLevel() {
        return 1;
    };

    public boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) &! MineplunderEnchantments.isDotEnchantment(enchantment);
    }

    public void doPostAttack(LivingEntity user, Entity target, int level) {
        SpecialFire.setBurning(target, 80*level, SpecialFire.FireType.SOUL_FIRE);
    }

}
