package jumpy.square.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


/**
 * Class for easily writing text to the screen
 *
 * TBH probably overkill...
 */
public class Texter {
    /**
     * BitmapFont member object
     * and position offsets
     *
     */
    private BitmapFont text;
    private short CENTER_OFFSET = 50;
    private short TOP_OFFSET = 50;
    private short VERTICAL_TOP_OFFSET = 250;
    private short RIGHT_OFFSET = 100;

    public Texter() {
        this.text = new BitmapFont();
    }

    /**
     * Write text in the top center
     *
     * @param str
     * @param batch
     */
    public void write_top_center(String str, SpriteBatch batch) {
        text.draw(batch, str, (Game.WIN_WIDTH / 2) - CENTER_OFFSET, Game.WIN_HEIGHT - TOP_OFFSET);
    }

    /**
     * Write text in the middle center
     *
     * @param str
     * @param batch
     */
    public void write_middle_center(String str, SpriteBatch batch) {
        text.draw(batch, str, (Game.WIN_WIDTH / 2) - CENTER_OFFSET, Game.WIN_HEIGHT - VERTICAL_TOP_OFFSET);
    }

    /**
     * Write text in the top right
     *
     * @param str
     * @param batch
     */
    public void write_top_right(String str, SpriteBatch batch) {
        text.draw(batch, str, Game.WIN_WIDTH - RIGHT_OFFSET, Game.WIN_HEIGHT - TOP_OFFSET);
    }

    /**
     * Dispose of the font
     *
     */
    public void dispose() {
        text.dispose();
    }




}
