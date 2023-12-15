package invaders.bullet;

import invaders.GameObject;
import invaders.bullet.strategy.BulletMove;

public class BulletEnemyFactory implements BulletFactory {
    @Override
    public BulletEnemy create(GameObject enemy, BulletMove moveStrategy) {
        return new BulletEnemy(enemy, moveStrategy);
    }
}
