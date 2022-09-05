package jumpy.square.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;


/**
 * Class to represent the player character /  Square
 *
 */
public class Square {
    // sprite and hitbox
    private Rectangle box;
    private Sprite sprite;

    // some size and position and jump constants
    private float SPAWN_X = Game.WIN_WIDTH / 3;
    private float SPAWN_Y = Game.WIN_HEIGHT / 2;
    private float JUMP_HEIGHT = 50;

    // long that will store system time in milliseconds
    private long last_jump_time;

    private boolean game_started = false;

    /**
     * Constructor
     */
    public Square() {
        // set up sprites and rectangle
        this.sprite = new Sprite(Sprites.SQUARE.get());
        this.sprite.setPosition(SPAWN_X, SPAWN_Y);
        this.box = new Rectangle(SPAWN_X, SPAWN_Y, 50, 50);
    }

    /**
     * Adjust height of Square with each iteration
     *
     * Essentially sets the Square's y-coordinate
     * as a quadratic function of time-elapsed since last jump and
     * height of square when jumping again
     *
     */
    public void quadratic_height() {
        // get current time and time since last jump in millisecond
        long current_time = System.currentTimeMillis();
        long delta_time = current_time - last_jump_time;

        // divide the difference in time by 100, or else y would decrease too quickly
        delta_time /= 100;

        // a and c parameters of a quadratic function
        float a = 1f;
        float c = box.getY();

        // calculate y as a function of delta time and square height
        double y = (-1 * a * Math.pow(delta_time, 2)) + c;

        // make sure square doesn't fall off of the screen
        if (check_lower_bound()) {
            box.setPosition(box.getX(), (float) y);
            sprite.setPosition(sprite.getX(), (float) y);
        }

    }

    /**
     * Render using SpriteBatch
     * @param batch
     */
    public void draw (SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    /**
     * Dispose of sprite
     *
     */
    public void dispose() {
        this.sprite.getTexture().dispose();
    }

    /**
     * Increases height of bird after jumping,
     * accounting for bounds
     *
     */
    private void update() {
        if (box.getY() + box.getHeight() + JUMP_HEIGHT <= Game.WIN_HEIGHT) {
            box.setPosition(sprite.getX(), sprite.getY());
        }
        else {
            box.setPosition(sprite.getX(), Game.WIN_HEIGHT - box.getHeight());
        }
    }


    private boolean check_upper_bound() {
        if (box.getY() + box.getHeight() <= Game.WIN_HEIGHT) {
            return true;
        }
        return false;
    }

    private boolean check_lower_bound() {
        if (box.getY() >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Let Square jump if you click spacebar
     *
     */
    public void jump() {
        if (check_upper_bound()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                this.sprite.translateY(JUMP_HEIGHT);
                update();
                this.last_jump_time = System.currentTimeMillis();
            }
        }
    }

    /**
     * Check to see if square collides with a wall
     *
     * @param handler
     * @return
     */
    public boolean is_colliding(WallHandler handler) {
        Wall[] walls = handler.get_walls();

        for (Wall wall : walls) {
            for (Rectangle rect_box : wall.get_boxes()) {
                if (box.overlaps(rect_box)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Getter for hitbox
     *
     * @return
     */
    public Rectangle get_box() {
        return new Rectangle(box);
    }

    /**
     * Call multiple methods here as one process
     */
    public void play() {
        if (! game_started) {
            last_jump_time = System.currentTimeMillis();
            game_started = true;
        }
        jump();
        quadratic_height();
    }




}
