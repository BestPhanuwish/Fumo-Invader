package invaders.bullet.strategy;

import invaders.bullet.Bullet;

public class BulletMoveFast implements BulletMove {
    public void setImage(Bullet bullet) {
        bullet.setImage("src/main/resources/bullet_fast_straight.png");
    }
    public void move(Bullet bullet) {
        bullet.down();
        bullet.down();
        // since the fast projectile move double faster than slow projectile
        // we can simply just call move down 2 times
    }
}
