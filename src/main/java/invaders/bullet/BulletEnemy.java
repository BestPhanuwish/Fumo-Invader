package invaders.bullet;

import invaders.GameObject;
import invaders.bullet.strategy.BulletMove;
import invaders.bullet.strategy.BulletMoveFast;
import invaders.bullet.strategy.BulletMoveSlow;
import invaders.entities.Enemy;
import javafx.scene.image.Image;

import java.io.File;
import java.util.List;

public class BulletEnemy extends Bullet {

    private final BulletMove moveStrategy;

    public BulletEnemy(GameObject enemy, BulletMove moveStrategy){
        super(enemy);
        this.width = 10;
        this.height = 15;
        this.moveStrategy = moveStrategy;
        this.moveStrategy.setImage(this);
    }

    @Override
    public void move(){
        moveStrategy.move(this);
    }

    @Override
    protected boolean isOutOfScreen() {
        return this.position.getY() >= 800-1-height;
    }
}
