package net.smileycorp.mineplunder.capability;

import net.minecraft.nbt.ByteTag;
import net.minecraft.world.entity.Entity;
import net.smileycorp.mineplunder.api.capability.SpecialFire;

public class InfernalSoulFire implements SpecialFire {

       @Override
       public FireType getType() {
           return FireType.SOUL_FIRE;
       }

       @Override
       public void setFireType(FireType type, Entity entity) {}

       @Override
       public ByteTag save() {
           return null;
       }

       @Override
       public void load(ByteTag tag) {}
   }