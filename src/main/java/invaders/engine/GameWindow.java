package invaders.engine;

import java.util.List;
import java.util.ArrayList;

import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameWindow {
	private final int width;
    private final int height;
	private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews;
    private Renderable background;

    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    private static final double VIEWPORT_MARGIN = 280.0;

	public GameWindow(GameEngine model, int width, int height){
		this.width = width;
        this.height = height;
        this.model = model;
        pane = new Pane();
        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(model, pane);

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        entityViews = new ArrayList<EntityView>();

    }

	public void run() {
         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));

         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
    }

    private void draw(){
        model.update();
        if (model.gameIsOver()) {
            Label label = new Label(String.format("Gameover: %s win", model.winnerIs()));
            label.setTextFill(Paint.valueOf("WHITE"));
            label.setLayoutY(height/2);
            label.setLayoutX(width/2 - 50);
            pane.getChildren().add(label);
        }

        List<Renderable> renderables = model.getRenderables();
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    if (entity.willDelete()) {
                        view.markForDelete();
                    }
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        List<EntityView> itemsToRemove = new ArrayList<>();
        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                itemsToRemove.add(entityView);
                pane.getChildren().remove(entityView.getNode()); // remove from game
            }
        }
        entityViews.removeAll(itemsToRemove); // remove from entityViews list
        for (EntityView entityView : itemsToRemove) {
            model.getRenderables().remove(entityView.getEntity()); // remove from renderables list in GameEngine
        }
    }

	public Scene getScene() {
        return scene;
    }
}
