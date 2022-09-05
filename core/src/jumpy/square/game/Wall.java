package jumpy.square.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

/**
 * Class for the Wall object
 *
 * These are the barriers that the square has to jump through
 *
 */
public class Wall {

    // Hitboxes and sprites
    private Rectangle[] boxes;
    private Sprite[] sprites;

    // This is the hitbox that represents the gap between top and bottom
    private Rectangle hit_box;

    // some numeric constants
    private float WIDTH = 75;
    private float SPEED = 5;
    private int GAP_HEIGHT = 125;

    // Some boolean parameters set to default values
    private boolean can_move = false;
    private boolean has_hit_box = true;

    /**
     * Constructor numba one
     *
     * @param spawn_x - X value to spawn walls on
     *
     * Walls will all spawn on this x coord (usually offscreen)
     * Walls will begin moving from this spawn point once the wall ahead
     *                has crossed half of the screen
     *
     */
    public Wall(float spawn_x) {
        // Instantiate some 'tuples' for top and bottoms
        this.sprites = new Sprite[2];
        this.boxes = new Rectangle[2];

        this.sprites[0] = new Sprite(Sprites.WALL.get());
        this.sprites[1] = new Sprite(Sprites.WALL.get());

        // set up everything else
        randomize(spawn_x);




    }

    /**
     * Copycat constructor
     *
     * @param copy - Wall object to copy
     */
    public Wall(Wall copy) {
        this.sprites = new Sprite[2];
        this.boxes = new Rectangle[2];
        for (int i = 0; i < 2; ++i) {
            this.sprites[i] = new Sprite(copy.sprites[i]);
            this.boxes[i] = new Rectangle(copy.boxes[i]);
        }
        this.hit_box = new Rectangle(copy.hit_box);
    }

    /**
     * Returns a copy of the top and bottom boxes of a wall
     *
     * @return - copy of rectangles
     */
    public Rectangle[] get_boxes() {
        Rectangle[] copy_boxes = new Rectangle[2];
        copy_boxes[0] = new Rectangle(boxes[0]);
        copy_boxes[1] = new Rectangle(boxes[1]);
        return copy_boxes;

    }

    /**
     * Return just the hit_box rectangle
     *
     * @return - rectangle
     */
    public Rectangle get_hit_box() {
        return this.hit_box;
    }

    /**
     * Toggle whether a Wall can move
     *
     * @param bool
     */
    public void set_can_move(boolean bool) {
        can_move = bool;
    }

    /**
     * Getter for move status
     *
     * @return
     */
    public boolean get_can_move() {
        return can_move;
    }

    /**
     * Randomize shape of a Wall object
     *
     * @param spawn_x - x-value to spawn on
     */
    private void randomize(float spawn_x) {
        Random ran = new Random();

        // Set y values for the top and bottom walls
        // gap_top is also the value where the top wall should begin
        // vice versa for gap_bottom and the bottom wall
        int gap_top = ran.nextInt(GAP_HEIGHT, (int) Game.WIN_HEIGHT - GAP_HEIGHT);
        int gap_bottom = gap_top - GAP_HEIGHT;

        // set the sizes and positions
        this.boxes[0] = new Rectangle(spawn_x, gap_top, WIDTH, Game.WIN_HEIGHT - gap_top);
        this.boxes[1] = new Rectangle(spawn_x, 0, WIDTH, gap_bottom);

        // setup the hit box
        this.hit_box = new Rectangle();
        this.hit_box.setPosition(boxes[0].getX() + (boxes[0].getWidth() / 2), boxes[1].getHeight());
        this.hit_box.setSize(20, GAP_HEIGHT);

        // Set up the sprites
        this.sprites[0].setPosition(boxes[0].getX(), boxes[0].getY());
        this.sprites[1].setPosition(boxes[1].getX(), boxes[1].getY());

        this.sprites[0].setSize(boxes[0].getWidth(), boxes[0].getHeight());
        this.sprites[1].setSize(boxes[1].getWidth(), boxes[1].getHeight());
    }

    /**
     * Return x value of Wall, tbh no clue why this is here
     * but i guess its being used
     *
     * @return
     */
    public float get_x() {
        return boxes[0].getX();
    }

    /**
     * Render the wall sprites
     *
     * @param batch
     */
    public void draw (SpriteBatch batch) {
        for (Sprite i : this.sprites) {
            i.draw(batch);
        }
    }

    /**
     * Dispose of the sprites
     *
     */
    public void dispose() {
        for (Sprite i : this.sprites) {
            i.getTexture().dispose();
        }
    }

    /**
     * Set hit_box to off if the square
     * has successfully passed through the loop
     *
     */
    public void drop_hit_box() {
        this.has_hit_box = false;
    }

    /**
     * Return hit_box status of a Wall
     *
     * @return
     */
    public boolean has_hit_box() {
        return has_hit_box;
    }

    /**
     * Move the Wall object left across the screen
     *
     */
    public void move() {
        for (Rectangle box : boxes) {
            box.setX(box.getX() - SPEED);
        }
        for (Sprite sprite : sprites) {
            sprite.setX(sprite.getX() - SPEED);
        }
        hit_box.setX(hit_box.getX() - SPEED);
    }





}
