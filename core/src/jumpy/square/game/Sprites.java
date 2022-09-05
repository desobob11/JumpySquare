package jumpy.square.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Enum for easily accessing image assets
 *
 */
public enum Sprites {

    SQUARE("square.jpg"),
    WALL("wall.jpg");

    private Texture texture;

    Sprites(String fileName) {
        this.texture = new Texture(fileName);
    }

    public Texture get() {
        return this.texture;
    }





}
