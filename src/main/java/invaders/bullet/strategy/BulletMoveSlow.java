package invaders.bullet.strategy;

import invaders.bullet.Bullet;

public class BulletMoveSlow implements BulletMove {
    public void setImage(Bullet bullet) {
        bullet.setImage("src/main/resources/bullet_slow_straight.png");
    }
    public void move(Bullet bullet) {
        bullet.down();
    }
}
