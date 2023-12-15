package invaders.entities;

import invaders.GameObject;
import invaders.bullet.BulletEnemy;
import invaders.bullet.BulletEnemyFactory;
import invaders.bullet.BulletPlayer;
import invaders.bullet.BulletPlayerFactory;
import invaders.bullet.strategy.BulletMoveFast;
import invaders.bullet.strategy.BulletMoveSlow;
import invaders.logic.Shootable;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;
import org.json.simple.JSONObject;

import java.io.File;

public class Enemy extends GameObject implements Shootable {

    private final String bulletType;
    private final BulletEnemyFactory bulletCreator = new BulletEnemyFactory();

    public Enemy(JSONObject object){
        this.bulletType = (String) object.get("projectile");
        String imgSource = "src/main/resources/enemy1.png";
        if (bulletType.equals("fast_straight")) {
            imgSource = "src/main/resources/enemy2.png";
        }

        JSONObject po = (JSONObject) object.get("position");
        double positionX = Long.valueOf((long) po.get("x")).doubleValue();
        double positionY = Long.valueOf((long) po.get("y")).doubleValue();
        this.position = new Vector2D(positionX, positionY);

        this.width = 32;
        this.height = 40;
        this.image = new Image(new File(imgSource).toURI().toString(), width, height, true, true);
        this.health = 1;
        this.speed = 5;
    }

    @Override
    public void down() {
        this.position.setY(this.position.getY() + (speed*4));
    }

    @Override
    public BulletEnemy shoot(){
        if (bulletType.equals("fast_straight")) {
            return bulletCreator.create(this, new BulletMoveFast());
        }
        return bulletCreator.create(this, new BulletMoveSlow());
    }

    public String getBulletType() {
        return bulletType;
    }

}