package invaders;

import invaders.physics.Vector2D;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigReader {

    private long game_size_x;
    private long game_size_y;

    private Vector2D player_position;
    private long player_live;
    private long player_speed;
    private String player_color;

    private JSONArray bunkers;
    private JSONArray enemies;

    public ConfigReader() {
        JSONParser parser = new JSONParser();

        try {
            // parsing file
            String filePath = "src/main/resources/config.json";
            Object obj = parser.parse(new FileReader(filePath));

            // type cast it to JSON object
            JSONObject jo = (JSONObject) obj;

            // get the screen size
            JSONObject go = (JSONObject) jo.get("Game");
            JSONObject so = (JSONObject) go.get("size");
            this.game_size_x = (long) so.get("x");
            this.game_size_y = (long) so.get("y");

            // get player data
            JSONObject po = (JSONObject) jo.get("Player");
            JSONObject ppo = (JSONObject) po.get("position");
            this.player_position = new Vector2D(
                    Long.valueOf((long) ppo.get("x")).doubleValue(),
                    Long.valueOf((long) ppo.get("y")).doubleValue()
            );
            this.player_live = (long) po.get("lives");
            this.player_speed = (long) po.get("speed");
            this.player_color = (String) po.get("colour");

            // get bunkers data
            this.bunkers = (JSONArray) jo.get("Bunkers");

            // get enemies data
            this.enemies = (JSONArray) jo.get("Enemies");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getGameWidth() {
        Long gameSizeX = Long.valueOf(game_size_x);
        return gameSizeX.intValue();
    }

    public int getGameHeight() {
        Long gameSizeY = Long.valueOf(game_size_y);
        return gameSizeY.intValue();
    }

    public Vector2D getPlayerPosition() {
        return player_position;
    }

    public double getPlayerHealth() {
        Long health = Long.valueOf(player_live);
        return health.doubleValue();
    }

    public double getPlayerSpeed() {
        Long speed = Long.valueOf(player_speed);
        return speed.doubleValue();
    }

    public String getPlayerColor() {
        return player_color.toUpperCase();
    }

    public JSONArray getBunkers() {
        return bunkers;
    }

    public JSONArray getEnemies() {
        return enemies;
    }

}
