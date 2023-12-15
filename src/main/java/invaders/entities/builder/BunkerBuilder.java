package invaders.entities.builder;

import invaders.GameObject;
import invaders.entities.Bunker;
import invaders.entities.Enemy;
import org.json.simple.JSONObject;

public class BunkerBuilder implements Builder {
    @Override
    public Bunker build(JSONObject obj) {
        return new Bunker(obj);
    }
}
