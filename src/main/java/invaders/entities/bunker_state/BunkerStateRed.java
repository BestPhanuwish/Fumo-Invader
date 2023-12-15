package invaders.entities.bunker_state;

import invaders.entities.Bunker;

public class BunkerStateRed implements BunkerState {
    @Override
    public void chageColor(Bunker bunker) {
        bunker.setColor("BLACK");
    }
}
