package net.smileycorp.mineplunder.entities.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.smileycorp.mineplunder.entities.Necromancer;
import net.smileycorp.mineplunder.entities.NecromancerMinion;

public class DispelMinionsGoal extends Goal {

    private final Necromancer entity;

    public DispelMinionsGoal(Necromancer entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return entity.getTarget() == null;
    }

    public void start() {
        for (NecromancerMinion minion : entity.getMinions()) {
            if (minion.getAnimState() != null || minion.getTarget() != null) continue;
            minion.dispel();
        }
    }

    public boolean canContinueToUse() {
        return false;
    }

}
