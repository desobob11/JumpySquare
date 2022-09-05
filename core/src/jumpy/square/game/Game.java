package jumpy.square.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


/**
 * Main game object
 *
 */
public class Game extends ApplicationAdapter {
	// some shape and objects
	private SpriteBatch batch;
	private Square square;
	private WallHandler walls;
	private Texter text;

	//some string constants
	private final String PRESS_ENTER =  "'ENTER' TO PLAY";
	private final String GAME_OVER = "GAME OVER";

	// window width and height parameters
	public static final float WIN_WIDTH = 600;
	public static final float WIN_HEIGHT = 600;

	// scores
	private short top_score = 0;
	private short score = 0;

	// game status trackers
	private boolean game_done = false;
	private boolean game_start = false;

	/**
	 * Start the game if player presses enter on title screen
	 */
	private void start() {
		if (!game_start && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			game_start = true;
		}
	}

	/**
	 * Restart game if player clicks enter after losing
	 *
	 */
	private void restart() {
			square = new Square();
			walls = new WallHandler();
			game_done = false;
			game_start = false;
			score = 0;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		square = new Square();
		walls = new WallHandler();
		text = new Texter();

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		if (!game_start) {
			square.draw(batch);
			start();
			text.write_middle_center(PRESS_ENTER, batch);
		}
		else {
			square.play();
			walls.process_walls(batch, game_done);
			square.draw(batch);

			if (!game_done) {
				game_done = square.is_colliding(walls);
				if (walls.check_passed(square)) {
					++score;
				}
			}

			if (game_done) {
				text.write_middle_center(GAME_OVER, batch);
				if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
					game_done = false;
					game_start = false;
					top_score = score;
					restart();
				}
			}
			text.write_top_center(String.valueOf(score), batch);
			text.write_top_right(String.valueOf(top_score), batch);

		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		square.dispose();
		walls.dispose_all();
		text.dispose();
	}
}
