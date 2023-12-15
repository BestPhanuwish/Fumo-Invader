package invaders.entities;

import javafx.scene.Node;
import invaders.rendering.Renderable;

public interface EntityView {
    Renderable getEntity();
    void update(double xViewportOffset, double yViewportOffset);

    boolean matchesEntity(Renderable entity);

    void markForDelete();

    Node getNode();

    boolean isMarkedForDelete();
}
