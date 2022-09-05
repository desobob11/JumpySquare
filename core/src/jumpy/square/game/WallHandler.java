package jumpy.square.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;

/**
 * Object that handles Wall behaviour as a unit
 *
 */
public class WallHandler {
    private Wall[] walls;

    // some constants
    private short NUM_WALLS = 4;
    private float X_BOUND = -100;
    private float WALL_SPAWN_X = 800;

    // iterator for indexing Wall objects in the wall array
    private short iter = 0;

    // some booleans for game status
    //TODO: Could pass these in as params from Game probably
    private boolean game_started = false;
    private boolean game_done = false;

    /**
     * Constructor
     *
     */
    public WallHandler() {
        // make an array
        this.walls = new Wall[NUM_WALLS];

        // load the wall objects in the array
        load_walls();
    }

    /**
     * Return a copy of this WallHandler's wall array
     *
     * @return
     */
    public Wall[] get_walls() {
        Wall[] copy_walls = new Wall[NUM_WALLS];

        for (int i = 0; i < NUM_WALLS; ++i) {
            copy_walls[i] = new Wall(this.walls[i]);
        }
        return copy_walls;
    }

    /**
     * Instantiates each wall in the array
     *
     */
    private void load_walls() {
        for (int i = 0; i < NUM_WALLS; ++i) {
            walls[i] = new Wall(WALL_SPAWN_X);
        }
    }

    /**
     * If a wall has gone of the screen, reload the Wall
     *
     */
    private void check_bounds() {
        for (int i = 0; i < NUM_WALLS; ++i) {
            if (walls[i].get_x() <= X_BOUND) {
                walls[i] = new Wall(WALL_SPAWN_X);
            }
        }
    }

    /**
     * Activates a wall / lets it move
     *
     */
    private void activate_wall() {
        // kick off the first wall as active (iter == 0)
        if (!game_started) {
            walls[iter].set_can_move(true);
        }
        else {
            /*
                afterwards, once the array walls[iter] has crossed half of the screen,
                set the next wall as movable, so wall[iter + 1]
             */
            if (walls[iter].get_x() <= Game.WIN_WIDTH / 2 && walls[iter].get_can_move()) {
                increment_iter();
                walls[iter].set_can_move(true);
            }
        }
    }

    /**
     * Checks to see if the square has passed a wall
     *
     * @param square
     * @return - passed or not
     */
    public boolean check_passed(Square square) {
        Rectangle sq_box = square.get_box();
        for (Wall wall : walls) {
            if (wall.get_hit_box().overlaps(sq_box) && wall.has_hit_box()) {
                wall.drop_hit_box();
                return true;
            }
        }
        return false;
    }

    /**
        Move each wall if its movable
     */
    private void move_walls() {
        for (Wall wall : walls) {
            if (wall.get_can_move()) {
                wall.move();
            }
        }
    }

    /**
     * Render walls using SpriteBatch
     *
     * @param batch
     */
    private void draw_walls(SpriteBatch batch) {
        for (Wall wall : walls) {
            wall.draw(batch);
        }
    }

    /**
     * Handle most of these individual methods
     * in a process in the method
     *
     * @param batch
     * @param game_status
     */
    public void process_walls(SpriteBatch batch, boolean game_status) {
        draw_walls(batch);
        activate_wall();
        if (!game_done) {
            move_walls();
        }
        check_bounds();
        check_game_done(game_status);
        deactivate_walls();
    }

    /**
     * Dispose of sprites
     */
    public void dispose_all() {
        for (Wall wall : walls) {
            wall.dispose();
        }
    }

    /**
     * Increase iter by one, and set back to 0 when done iterating through array
     *
     */
    private void increment_iter() {
        ++iter;
        if (iter == NUM_WALLS) {
            iter = 0;
        }
    }

    /**
     * Set all walls to not moveable
     *
     */
    private void deactivate_walls() {
        if (game_done) {
            for (Wall wall : walls) {
                wall.set_can_move(false);
            }
        }
    }

    /**
     * Pretty self-explanatory
     * @param game_status
     */
    private void check_game_done(boolean game_status) {
        game_done = game_status;
    }

}





