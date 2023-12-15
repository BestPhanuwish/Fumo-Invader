package invaders.bullet.strategy;

import invaders.GameObject;
import invaders.bullet.Bullet;

public interface BulletMove {
    public void setImage(Bullet bullet);
    public void move(Bullet bullet);
}
