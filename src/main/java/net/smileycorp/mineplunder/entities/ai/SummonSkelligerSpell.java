package net.smileycorp.mineplunder.entities.ai;

import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.Vec3;
import net.smileycorp.atlas.api.util.DirectionUtils;
import net.smileycorp.mineplunder.entities.Necromancer;
import net.smileycorp.mineplunder.entities.Skelliger;

public class SummonSkelligerSpell extends NecromancerSpellGoal {

    private final TargetingConditions targeting = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight()
            .ignoreInvisibilityTesting().selector(e->((Skelliger)e).getOwner() == necromancer);

    public SummonSkelligerSpell(Necromancer necromancer) {
        super(necromancer);
    }

    public boolean canUse() {
        return super.canUse() && necromancer.getRandom().nextInt(8) + 1 >
                necromancer.level().getNearbyEntities(Skelliger.class, targeting, necromancer, necromancer.getBoundingBox().inflate(16.0D)).size();
    }

    public void start() {
        super.start();
        if (necromancer.level().isClientSide) return;
    }

    public void tick() {
        super.tick();
        if (useTicks == 5) for (int i = 0; i <= necromancer.getRandom().nextInt(4); i++) {
            Skelliger skelliger = new Skelliger(necromancer.level());
            Vec3 dir = DirectionUtils.getRandomDirectionVecXZ(necromancer.getRandom());
            skelliger.setPos(DirectionUtils.getClosestLoadedPos(necromancer.level(), necromancer.position(), dir, 2.5));
            necromancer.summonMinion(skelliger);
        }
    }

    @Override
    public int getSpellLength() {
        return 40;
    }

}
