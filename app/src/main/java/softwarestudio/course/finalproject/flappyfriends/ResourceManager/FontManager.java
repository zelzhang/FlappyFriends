package softwarestudio.course.finalproject.flappyfriends.ResourceManager;

import android.graphics.Color;
import android.graphics.Typeface;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.SimpleBaseGameActivity;

/**
 * There is only one font, 04b_19, to be selected.
 */
public class FontManager {

    //private final static String FONT_BUILD_PATH = "Font/";
    private final static String FONT_FILE_NAME = "DefaultWordFont.ttf";
    private static Font mFont = null;

    public FontManager (SimpleBaseGameActivity context) {
        if (context != null) {
            //FontFactory.setAssetBasePath(FONT_BUILD_PATH);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), FONT_FILE_NAME);
            ITexture mFontTexture= new BitmapTextureAtlas(
                    context.getTextureManager(),
                    256,
                    256,
                    TextureOptions.BILINEAR
            );
            mFont = new StrokeFont(
                    context.getFontManager(),
                    mFontTexture,
                    typeface,
                    60,
                    true,
                    Color.WHITE,
                    2,
                    Color.BLACK
            );
            mFont.load();
        } else
            throw new IllegalArgumentException("Empty SimpleBaseGameActivity");
    }

    public Font getFont() {
        return mFont;
    }
}
