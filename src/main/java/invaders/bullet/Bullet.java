package invaders.bullet;

import invaders.GameObject;
import invaders.bullet.strategy.BulletMove;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;

import java.util.List;

public abstract class Bullet extends GameObject {

    private final GameObject owner;

    public Bullet(GameObject bulletOwner){
        this.owner = bulletOwner;
        this.position = new Vector2D(
                bulletOwner.getPosition().getX() + (bulletOwner.getWidth()-this.width)/2,
                bulletOwner.getPosition().getY()
        );
        this.health = 1;
        this.speed = 2;
    }

    protected GameObject getOwner() {
        return owner;
    }

    protected void bulletColliderDetect(List<GameObject> gameObjects) {
        for (GameObject go: gameObjects) {
            // the bullet can't hit the bullet itself
            // the bullet can't hit owner that had the same type
            // the bullet need to collide with object
            if (!go.equals(this) && go.getClass() != this.getOwner().getClass() && this.isColliding(go)) {
                this.takeDamage(1);
                go.takeDamage(1);
            }
        }
    }

    public void update(List<GameObject> gameObjects){
        this.move();

        if (isOutOfScreen()) {
            this.markDelete();
        }

        this.bulletColliderDetect(gameObjects);
    }

    // abstract method need implementation depend on Bullet class
    public abstract void move();
    protected abstract boolean isOutOfScreen();
}
