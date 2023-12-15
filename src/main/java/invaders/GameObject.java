package invaders;

import invaders.logic.Damagable;
import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

import java.io.File;

public abstract class GameObject implements Moveable, Damagable, Renderable, Collider {
    // variable
    protected Vector2D position;
    protected double health;
    protected double speed;

    protected double width;
    protected double height;
    protected Image image;
    private boolean delete = false;

    // delete method
    @Override
    public boolean willDelete() {
        return this.delete;
    }

    @Override
    public void markDelete() {
        this.delete = true;
    }

    // implemented method
    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public void up() {
        this.position.setY(this.position.getY() - speed);
    }

    @Override
    public void down() {
        this.position.setY(this.position.getY() + speed);
    }

    @Override
    public void left() { this.position.setX(this.position.getX() - speed); }

    @Override
    public void right() { this.position.setX(this.position.getX() + speed); }

    @Override
    public Image getImage() { return this.image; }

    @Override
    public void setImage(String path) {
        image = new Image(new File(path).toURI().toString(), width, height, false, true);;
    }

    @Override
    public double getWidth() { return width; }

    @Override
    public double getHeight() { return height; }

    @Override
    public Vector2D getPosition() { return position; }

    @Override
    public Layer getLayer() { return Layer.FOREGROUND; }
}
