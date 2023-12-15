package invaders.bullet;

import invaders.GameObject;
import invaders.entities.Player;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

import java.io.File;
import java.util.List;

public class BulletPlayer extends Bullet {
    public BulletPlayer(GameObject player){
        super(player);
        this.width = 10;
        this.height = 20;
        this.image = new Image(new File("src/main/resources/bullet.png").toURI().toString(), width, height, true, true);
    }

    @Override
    public void move(){
        up();
    }

    @Override
    protected boolean isOutOfScreen() {
        return this.position.getY() <= 0;
    }
}
