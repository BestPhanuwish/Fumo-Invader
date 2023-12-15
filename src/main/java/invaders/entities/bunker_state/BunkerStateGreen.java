package invaders.entities.bunker_state;

import invaders.entities.Bunker;

public class BunkerStateGreen implements BunkerState {
    @Override
    public void chageColor(Bunker bunker) {
        bunker.setImage("src/main/resources/bunker_yellow.png");
        bunker.setColor("YELLOW");
        bunker.setState(new BunkerStateYellow());
    }
}
