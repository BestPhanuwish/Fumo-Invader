package invaders.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import invaders.ConfigReader;
import invaders.GameObject;
import invaders.bullet.Bullet;
import invaders.bullet.BulletEnemy;
import invaders.bullet.BulletPlayer;
import invaders.entities.Bunker;
import invaders.entities.Enemy;
import invaders.entities.Player;
import invaders.entities.builder.BunkerBuilder;
import invaders.entities.builder.Director;
import invaders.entities.builder.EnemyBuilder;
import invaders.rendering.Renderable;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine {

	private boolean gameIsOver = false;
	private String winner;

	private List<Renderable> renderables;
	private List<Bullet> bullets;
	private List<Enemy> enemies;
	private List<Bunker> bunkers;
	private Player player;
	private BulletPlayer playerBullet;

	private boolean left;
	private boolean right;

	private int width;
	private int height;

	private EnemyDirection enemyDirection = EnemyDirection.RIGHT;
	private int enemyIndex = 0;
	private boolean enemyTouchEdge = false;

	private final Random random = new Random();
	private int enemyShootCounter = 0;
	//
	private int enemyShootReadyTime;

	public GameEngine(ConfigReader config){
		// read the config here
		renderables = new ArrayList<Renderable>();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		bunkers = new ArrayList<Bunker>();

		// get player
		player = new Player(config);
		renderables.add(player);

		// get bunkers using builder pattern
		BunkerBuilder bunkerBuilder = new BunkerBuilder();
		Director bunkerDirector = new Director(bunkerBuilder);
		for (GameObject go: bunkerDirector.construct(config.getBunkers())) {
			Bunker bunker = (Bunker) go; // down casting to keep respective class
			bunkers.add(bunker);
		}
		renderables.addAll(bunkers);

		// get enemies using builder pattern
		EnemyBuilder enemyBuilder = new EnemyBuilder();
		Director enemyDirector = new Director(enemyBuilder);
		for (GameObject go: enemyDirector.construct(config.getEnemies())) {
			Enemy enemy = (Enemy) go; // down casting to keep respective class
			enemies.add(enemy);
		}
		renderables.addAll(enemies);

		// remembered screen size
		width = config.getGameWidth();
		height = config.getGameHeight();

		// generate enemy bullet interval
		enemyShootReadyTime = getRandomInterval();
	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		// if the game end then stop everything
		if (gameIsOver) { return; }

		// create local all object to use for collision detection
		List<GameObject> gameObjects = new ArrayList<GameObject>();
		gameObjects.add(player);
		gameObjects.addAll(bullets);
		gameObjects.addAll(bunkers);
		gameObjects.addAll(enemies);

		// update logic for every object in the game
		movePlayer();
		moveEnemy();
		for(Bullet bullet: bullets){
			bullet.update(gameObjects); // move and check collision
		}
		// check collision for enemy
		for (GameObject enemy: enemies) {
			if (enemy.isColliding(player)) {
				gameIsOver = true;
				winner = "Fumo";
				return;
			}
			for (GameObject bunker: bunkers) {
				if (enemy.isColliding(bunker)) {
					bunker.markDelete();
				}
			}
		}

		// enemy shoot at random
		enemyBulletHandle();

		// ensure that renderable foreground objects don't go off-screen
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= width) {
				ro.getPosition().setX(width-1-ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(1);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= height) {
				ro.getPosition().setY(height-1-ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(1);
			}
		}

		// if game object health is equal or less than 0 then prepare to remove it
		for (Bullet bullet: bullets) {
			if (!bullet.isAlive()) {
				bullet.markDelete();
			}
		}
		for (GameObject bunker: bunkers) {
			if (!bunker.isAlive()) {
				bunker.markDelete();
			}
		}
		for (GameObject enemy: enemies) {
			if (!enemy.isAlive()) {
				enemy.markDelete();
			}
		}

		// if it's player then the game end
		if (!player.isAlive()) {
			gameIsOver = true;
			winner = "Fumo";
			return;
		}

		// remove object from the list if it will be deleted
		bullets.removeIf(Bullet::willDelete);
		bunkers.removeIf(GameObject::willDelete);
		enemies.removeIf(GameObject::willDelete);

		// if there's no enemies on the screen then player win, game is over
		if (enemies.isEmpty()) {
			gameIsOver = true;
			winner = "Player";
		}
	}

	public boolean gameIsOver() {
		return gameIsOver;
	}

	public String winnerIs() {
		return winner;
	}

	public List<Renderable> getRenderables(){
		return renderables;
	}


	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}
	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		// the bullet from player can only exist one at a time
		if (playerBullet == null || playerBullet.willDelete()) {
			playerBullet = player.shoot();
			renderables.add(playerBullet);
			bullets.add(playerBullet);
			return true;
		}
		return false;
	}

	// personal function
	private int getRandomInterval() {
		// Generate a random integer between 30 and 120 (inclusive)
		// Which is between 0.5-2 second if it's 60 fps
		return random.nextInt(120 - 30 + 1) + 60;
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}

	private void moveEnemy(){
		// if the index reach the end of list then it reset to first enemy
		if (enemyIndex >= enemies.size()) {
			enemyIndex = 0;

			// if there's one of the monster hit the edge then it move the whole group down and change direction
			if (enemyTouchEdge) {
				for (GameObject enemy: enemies) {
					enemy.down();
					// if enemy goes pass bottom edge then the game also end
					if (enemy.getPosition().getY() >= height-1-enemy.getHeight()) {
						gameIsOver = true;
						winner = "Fumo";
					}
				}
				enemyDirection = (enemyDirection == EnemyDirection.RIGHT) ? EnemyDirection.LEFT : EnemyDirection.RIGHT;
				enemyTouchEdge = false;
			}
		}

		// move that enemy to respective side
		GameObject currentMovingEnemy = enemies.get(enemyIndex);
		if (enemyDirection == EnemyDirection.RIGHT) {
			currentMovingEnemy.right();
		} else {
			currentMovingEnemy.left();
		}

		// detect if one of the monster touch the edge
		if (
				(enemyDirection == EnemyDirection.RIGHT &&
				currentMovingEnemy.getPosition().getX() >= width-1-currentMovingEnemy.getWidth())
						||
				(enemyDirection == EnemyDirection.LEFT &&
				currentMovingEnemy.getPosition().getX() <= 1)
		) {
			enemyTouchEdge = true;
		}

		enemyIndex++;
	}

	private void enemyBulletHandle() {
		enemyShootCounter++;
		if (enemyShootCounter == enemyShootReadyTime) {
			// reset counter
			enemyShootCounter = 0;
			enemyShootReadyTime = getRandomInterval();

			// if there exist 3 enemy bullet in the game then it's skip and not shoot
			int count = 0;
			for (Bullet bullet: bullets) {
				if (bullet != playerBullet) {
					count++;
				}
			}
			if (count == 3) {
				return;
			}

			// pick random enemy to shoot the bullet
			int randomEnemyIndex = random.nextInt(enemies.size());
			Enemy enemy = enemies.get(randomEnemyIndex);
			BulletEnemy bulletEnemy = enemy.shoot();
			bullets.add(bulletEnemy);
			renderables.add(bulletEnemy);
		}
	}
}
