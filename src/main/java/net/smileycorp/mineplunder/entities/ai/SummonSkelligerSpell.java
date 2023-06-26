package net.smileycorp.mineplunder.entities.ai;

import net.minecraft.world.phys.Vec3;
import net.smileycorp.atlas.api.util.DirectionUtils;
import net.smileycorp.mineplunder.entities.Necromancer;
import net.smileycorp.mineplunder.entities.Skelliger;

public class SummonSkelligerSpell extends NecromancerSpellGoal {

    public SummonSkelligerSpell(Necromancer necromancer) {
        super(necromancer);
    }

    public void start() {
        super.start();
        if (necromancer.level().isClientSide) return;
        for (int i = 0; i < 3; i++) {
            Skelliger skelliger = new Skelliger(necromancer.level());
            Vec3 dir = DirectionUtils.getRandomDirectionVecXZ(necromancer.getRandom());
            skelliger.setPos(DirectionUtils.getClosestLoadedPos(necromancer.level(), necromancer.position(), dir, 2.5));
            necromancer.summonMinion(skelliger);
        }
    }

    @Override
    public int getSpellLength() {
        return 60;
    }

}
