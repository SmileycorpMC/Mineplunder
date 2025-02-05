package net.smileycorp.mineplunder.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
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
    
    @Override
    public boolean canUse() {
        if (!super.canUse()) return false;
        double dis = necromancer.distanceToSqr(necromancer.getTarget());
        return dis <= 16;
    }
    
    @Override
    public void start() {
        super.start();
        dir = DirectionUtils.getDirectionVecXZ(necromancer, necromancer.getTarget());
        radius = 1;
    }

    @Override
    public void tick() {
        super.tick();
        if (useTicks >= 20 && useTicks <= 23) {
            int count = radius * 8;
            for (int i = 0; i < count; i++) {
                double angle = i * 2 * Math.PI / count;
                Vec3 pos = new Vec3(radius * Math.cos(angle) + necromancer.position().x, necromancer.position().y,
                        radius * Math.sin(angle) + necromancer.position().z);
                NecrofireProjectile proj = new NecrofireProjectile(necromancer, pos);
                necromancer.level().addFreshEntity(proj);
            }
            radius++;
        }
    }

    @Override
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
