package invaders.bullet;

import invaders.GameObject;
import invaders.bullet.BulletFactory;
import invaders.bullet.strategy.BulletMove;
import invaders.entities.Player;

public class BulletPlayerFactory implements BulletFactory {
    @Override
    public BulletPlayer create(GameObject player, BulletMove moveStrategy) {
        return new BulletPlayer(player);
    }
}
