package invaders.entities.builder;

import invaders.GameObject;
import invaders.entities.Enemy;
import invaders.entities.Player;
import org.json.simple.JSONObject;

public class EnemyBuilder implements Builder {
    @Override
    public Enemy build(JSONObject obj) {
        return new Enemy(obj);
    }
}
