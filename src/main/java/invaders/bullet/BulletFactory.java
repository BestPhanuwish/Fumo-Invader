package invaders.bullet;

import invaders.GameObject;
import invaders.bullet.strategy.BulletMove;

public interface BulletFactory {
    public Bullet create(GameObject owner, BulletMove moveStrategy);
}
