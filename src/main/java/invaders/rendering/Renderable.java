package invaders.rendering;

import invaders.physics.Vector2D;
import javafx.scene.image.Image;

import java.io.File;

/**
 * Represents something that can be rendered
 */
public interface Renderable {

    public Image getImage();
    public void setImage(String path);

    public double getWidth();
    public double getHeight();

    public Vector2D getPosition();

    public Renderable.Layer getLayer();

    public boolean willDelete();

    public void markDelete();

    /**
     * The set of available layers
     */
    public static enum Layer {
        BACKGROUND, FOREGROUND, EFFECT
    }
}
