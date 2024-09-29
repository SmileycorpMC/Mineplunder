package net.smileycorp.mineplunder.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.smileycorp.atlas.api.util.DirectionUtils;
import net.smileycorp.mineplunder.entities.NecrofireProjectile;
import net.smileycorp.mineplunder.entities.Necromancer;

public class NecrofireSpell extends NecromancerSpellGoal {

    protected Vec3 dir;
    protected int radius;

    public NecrofireSpell(Necromancer necromancer) {
        super(necromancer);
    }

    public void start() {
        super.start();
        dir = DirectionUtils.getDirectionVecXZ(necromancer, necromancer.getTarget());
        radius = 1;
    }

    public void tick() {
        super.tick();
        if (useTicks >= 20 && useTicks <= 35) {
            radius++;
            BlockPos pos = DirectionUtils.getClosestLoadedPos(necromancer.level(), necromancer.blockPosition(), dir, radius);
            NecrofireProjectile proj = new NecrofireProjectile(necromancer, pos);
            necromancer.level().addFreshEntity(proj);
        }
    }

    public void stop() {
        super.stop();
        dir = null;
        radius = 0;
    }

    @Override
    public int getSpellLength() {
        return 40;
    }

}
