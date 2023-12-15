package invaders.entities;

import invaders.GameObject;
import invaders.entities.bunker_state.BunkerState;
import invaders.entities.bunker_state.BunkerStateGreen;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;
import org.json.simple.JSONObject;

import java.io.File;

public class Bunker extends GameObject {

    private BunkerState state;
    private String color;

    public Bunker(JSONObject object){
        JSONObject po = (JSONObject) object.get("position");
        double positionX = Long.valueOf((long) po.get("x")).doubleValue();
        double positionY = Long.valueOf((long) po.get("y")).doubleValue();
        this.position = new Vector2D(positionX, positionY);

        JSONObject so = (JSONObject) object.get("size");
        this.width = Long.valueOf((long) so.get("x")).doubleValue();
        this.height = Long.valueOf((long) so.get("y")).doubleValue();

        this.image = new Image(new File("src/main/resources/bunker_green.png").toURI().toString(), width, height, false, true);
        this.health = 3;
        this.speed = 0;

        this.state = new BunkerStateGreen();
        this.color = "GREEN";
    }

    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
        state.chageColor(this);
    }

    public void setColor(String newColor) {
        color = newColor;
    }

    public void setState(BunkerState state) {
        this.state = state;
    }


}