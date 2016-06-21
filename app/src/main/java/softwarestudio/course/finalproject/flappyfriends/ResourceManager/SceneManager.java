package softwarestudio.course.finalproject.flappyfriends.ResourceManager;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.align.HorizontalAlign;

import softwarestudio.course.finalproject.flappyfriends.Creature.Bird;
import softwarestudio.course.finalproject.flappyfriends.Creature.Pipe;
import softwarestudio.course.finalproject.flappyfriends.GameActivity;

/**
 * Created by lusa on 2016/06/18.
 */
public class SceneManager {

    private static SimpleBaseGameActivity mContext = null;
    private static FontManager mFontManager = null;
    private static ImageManager mImgaeManager = null;
    private static ParallaxBackground mParallaxBackground = null;

    private Text testCounterTitle = null;
    private Text testCounter = null;

    private Bird[] birds;
    private Pipe[] pipes;

    public SceneManager(
            SimpleBaseGameActivity context,
            FontManager fontManager,
            ImageManager imageManager,
            ParallaxBackground parallaxBackground
    ) {
        mContext = context;
        mFontManager = fontManager;
        mImgaeManager = imageManager;
        mParallaxBackground = parallaxBackground;
    }

    public void setCreatureList(Bird[] birds, Pipe[] pipes) {
        this.birds = birds;
        this.pipes = pipes;
    }

    public Scene buildScene() {

        Scene scene = new Scene();

        mImgaeManager.ceterSprite(mImgaeManager.getBackGroundSprite());
        mParallaxBackground.attachParallaxEntity(
                new ParallaxBackground.ParallaxEntity(1, mImgaeManager.getBackGroundSprite()));
        scene.setBackground(mParallaxBackground);
        scene.setBackgroundEnabled(true);

        testCounterTitle = new Text(
                0,
                0,
                mFontManager.getFont(),
                "Counter",
                new TextOptions(HorizontalAlign.CENTER),
                mContext.getVertexBufferObjectManager()
        );
        setTextPosition(
                testCounterTitle,
                GameActivity.getCameraWidth() / 2,
                GameActivity.getCameraHeight() * 9 / 10
        );
        testCounterTitle.setZIndex(3);
        scene.attachChild(testCounterTitle);

        testCounter = new Text(
                0,
                0,
                mFontManager.getFont(),
                "      ",
                new TextOptions(HorizontalAlign.CENTER),
                mContext.getVertexBufferObjectManager()
        );
        centerText(testCounter);
        testCounter.setZIndex(3);
        scene.attachChild(testCounter);

        if (birds != null && birds.length > 0) {
            for (int i=0; i<birds.length; i++) {
                // add bird into scene
            };
        }

        if (pipes != null && pipes.length > 0) {
            for (int i=0; i<pipes.length; i++) {
                // add pipe into scene
            }
        }

        return scene;
    }

    /*
    public static void centerSprite(Sprite sprite){
        sprite.setX(GameActivity.getCameraWidth() / 2);
        sprite.setY(GameActivity.getCameraHeight() / 2);
    }
    */

    public void setScoreBoard(int score) {
        setTextContent(testCounter, ""+score);
    }

    private void setTextContent(Text text, String content) {
        if (text != null && content != null) {
            text.setText(content);
        }
    }

    private void setTextPosition(Text text, float x, float y) {
        if (text != null
                && x >= 0 && x <= GameActivity.getCameraWidth()
                && y >= 0 && y <= GameActivity.getCameraHeight()) {
            text.setX(x);
            text.setY(y);
        }
    }

    private void centerText(Text text){
        if (text != null) {
            setTextPosition(
                    text,
                    GameActivity.getCameraWidth() / 2,
                    GameActivity.getCameraHeight() / 2
            );
        }
    }
}
