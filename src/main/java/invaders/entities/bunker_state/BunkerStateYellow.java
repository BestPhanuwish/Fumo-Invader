package invaders.entities.bunker_state;

import invaders.entities.Bunker;

public class BunkerStateYellow implements BunkerState {
    @Override
    public void chageColor(Bunker bunker) {
        bunker.setImage("src/main/resources/bunker_red.png");
        bunker.setColor("RED");
        bunker.setState(new BunkerStateRed());
    }
}
