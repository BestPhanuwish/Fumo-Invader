package invaders.entities.builder;

import invaders.GameObject;
import invaders.rendering.Renderable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Director {
    private final Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public List<GameObject> construct(JSONArray entityArray) {
        List<GameObject> entities = new ArrayList<GameObject>();
        for (Object obj : entityArray) {
            JSONObject jsonObject = (JSONObject) obj;
            entities.add(builder.build(jsonObject));
        }
        return entities;
    }
}
