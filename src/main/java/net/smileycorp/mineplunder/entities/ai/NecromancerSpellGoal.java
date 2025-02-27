package net.smileycorp.mineplunder.entities.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.smileycorp.mineplunder.entities.Necromancer;

import java.util.EnumSet;

public abstract class NecromancerSpellGoal extends Goal {

    protected final Necromancer necromancer;
    protected int useTicks;

    public NecromancerSpellGoal(Necromancer necromancer) {
        setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.necromancer = necromancer;
    }

    public boolean isInterruptable() {
        return false;
    }

    public boolean canUse() {
        if (necromancer.getTarget() == null) return false;
        return !necromancer.isCastingSpell() &! necromancer.getTarget().isDeadOrDying();
    }

    public boolean canContinueToUse() {
        return necromancer.getTarget() != null && useTicks > 0;
    }


    public void start() {
        super.start();
        useTicks = getSpellLength();
        necromancer.setSpellcasting(true);
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public abstract int getSpellLength();

    public void stop() {
        super.stop();
        necromancer.setSpellcasting(false);
        useTicks = 0;
    }

    public void tick() {
        if (necromancer.getTarget() != null) {
            necromancer.getLookControl().setLookAt(necromancer.getTarget(), necromancer.getMaxHeadYRot(), necromancer.getMaxHeadXRot());
        }
        useTicks--;
    }

}
