package invaders.entities.builder;

import invaders.GameObject;
import org.json.simple.JSONObject;

public interface Builder {
    public GameObject build(JSONObject obj);
}
