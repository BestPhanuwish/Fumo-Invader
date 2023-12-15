package invaders.entities;

import invaders.ConfigReader;
import invaders.GameObject;
import invaders.bullet.Bullet;
import invaders.bullet.BulletPlayer;
import invaders.bullet.BulletPlayerFactory;
import invaders.bullet.strategy.BulletMoveSlow;
import invaders.logic.Damagable;
import invaders.logic.Shootable;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;

public class Player extends GameObject implements Shootable {

    private final String color;
    private final BulletPlayerFactory bulletCreator = new BulletPlayerFactory();

    public Player(ConfigReader config){
        // different color, different character
        // art from: https://www.shrinemaiden.org/forum/index.php?topic=17950.0
        String imgSource = "src/main/resources/player.png";
        if (config.getPlayerColor().equals("YELLOW")) {
            imgSource = "src/main/resources/player_yellow.png";
        } else if (config.getPlayerColor().equals("GREEN")) {
            imgSource = "src/main/resources/player_green.png";
        }

        this.width = 25;
        this.height = 30;
        this.image = new Image(new File(imgSource).toURI().toString(), width, height, true, true);
        this.position = config.getPlayerPosition();
        this.health = config.getPlayerHealth();
        this.speed = config.getPlayerSpeed();
        this.color = config.getPlayerColor();
    }

    @Override
    public BulletPlayer shoot(){
        return bulletCreator.create(this, new BulletMoveSlow());
    }

    public String getColor() {
        return color;
    }

}
