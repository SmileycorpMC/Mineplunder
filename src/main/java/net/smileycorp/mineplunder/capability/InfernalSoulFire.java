package net.smileycorp.mineplunder.capability;

import net.minecraft.nbt.ByteTag;
import net.minecraft.world.entity.Entity;
import net.smileycorp.mineplunder.api.capability.SoulFire;

public class InfernalSoulFire implements SoulFire {

       @Override
       public boolean isAblaze() {
           return true;
       }

       @Override
       public void setSoulFire(boolean soulFire, Entity entity) {}

       @Override
       public ByteTag save() {
           return null;
       }

       @Override
       public void load(ByteTag tag) {}
   }