package net.smileycorp.mineplunder.api.capability;

import net.minecraft.nbt.ByteTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.util.LazyOptional;
import net.smileycorp.mineplunder.MineplunderDamageSources;
import net.smileycorp.mineplunder.init.MineplunderBlocks;

import java.util.function.Consumer;
import java.util.function.Supplier;


public interface SpecialFire {

    FireType getType();

    void setFireType(FireType type, Entity entity);

    ByteTag save();

    void load(ByteTag tag);

    static void setFireType(Entity entity, FireType type) {
        if (entity == null) return;
        LazyOptional<SpecialFire> cap = entity.getCapability(MineplunderCapabilities.SPECIAL_FIRE_CAPABILITY);
        if (!cap.isPresent()) return;
        cap.resolve().get().setFireType(type, entity);
    }

    static void setBurning(Entity entity, int ticks, FireType type) {
       setFireType(entity, type);
       entity.setRemainingFireTicks(ticks);
    }

    static FireType getFireType(Entity entity) {
        if (entity == null |! entity.isOnFire()) return null;
        LazyOptional<SpecialFire> cap = entity.getCapability(MineplunderCapabilities.SPECIAL_FIRE_CAPABILITY);
        if (cap.isPresent()) return cap.resolve().get().getType();
        return null;
    }

    enum FireType {

        SOUL_FIRE("minecraft:block/soul_fire", true, (getter)->{
            getter.source = MineplunderDamageSources.soulFire(getter.entity);
            getter.damage *= 2;
        }, () -> Blocks.SOUL_FIRE),
        NECROFIRE("mineplunder:block/necrofire", false, (getter)->{
            getter.source = MineplunderDamageSources.necrofire(getter.entity);
        }, MineplunderBlocks.NECROFIRE::get);

        private final String texture;
        private final boolean canBeExtinguished;
        private final Consumer<FireDamageGetter> consumer;
        private final Supplier<Block> block;

        FireType(String texture, boolean canBeExtinguished, Consumer<FireDamageGetter> consumer, Supplier<Block> block) {
            this.texture = texture;
            this.canBeExtinguished = canBeExtinguished;
            this.consumer = consumer;
            this.block = block;
        }

        public ResourceLocation getTexture(int number) {
            return new ResourceLocation(texture + "_" + number);
        }

        public boolean canBeExtinguished() {
            return canBeExtinguished;
        }

        public void accept(FireDamageGetter getter) {
            consumer.accept(getter);
        }

        public Block getBlock() {
            return block.get();
        }

        public byte getID() {
            return (byte)(ordinal() + 1);
        }

        public static FireType get(byte i) {
            if (i <= 0 || i > values().length-1) return null;
            return values()[i-1];
        }

    }

    class FireDamageGetter {

        private DamageSource source;
        private float damage;

        private Entity entity;

        public FireDamageGetter(DamageSource source, float damage, Entity entity) {
            this.source = source;
            this.damage = damage;
            this.entity = entity;
        }

        public DamageSource getSource() {
            return source;
        }

        public float getAmount() {
            return damage;
        }

    }



}
