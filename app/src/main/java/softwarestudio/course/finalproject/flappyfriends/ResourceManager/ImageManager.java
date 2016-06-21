package softwarestudio.course.finalproject.flappyfriends.ResourceManager;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import softwarestudio.course.finalproject.flappyfriends.Utility;

import softwarestudio.course.finalproject.flappyfriends.Creature.Bird;
import softwarestudio.course.finalproject.flappyfriends.Creature.Pipe;
import softwarestudio.course.finalproject.flappyfriends.GameActivity;

/**
 * Created by lusa on 2016/06/19.
 */
public class ImageManager {

    private final static String IMAGE_ASSET_PATH = "img/";

    private Sprite mBackGroundSprite;
    private final static String BG_IMAGE_NAME = "background.png";
    private final static int BG_WIDTH = 480;
    private final static int BG_HEIGHT = 800;

    private final static String UPPERPIPE_IMAGE_NAME = "upperpipe.png";
    private final static String LOWERPIPE_IMAGE_NAME = "lowerpipe.png";

    private final static String BLUEBIRD_IMAGE_NAME = "bluebird.png";
    private final static String REDBIRD_IMAGE_NAME = "bluebird.png";
    private final static String YELLOWBIRD_IMAGE_NAME = "yellowbird.png";
    private final static int BIRDBITMAP_MAX_ROW = 3;
    private final static int BIRDBITMAP_MAX_COL = 3;
    private final static int BIRD_BITMAP_WIDTH = 220;
    private final static int BIRD_BITMAP_HEIGHT = 160;

    public ImageManager (SimpleBaseGameActivity context) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(IMAGE_ASSET_PATH);
        mBackGroundSprite = buildBackGroundSprite(context);
    }

    public Sprite buildBackGroundSprite(SimpleBaseGameActivity context) {
        if (context == null) return null;

        return new Sprite(
                0,
                0,
                buildITextureRegion(
                        context,
                        BG_IMAGE_NAME,
                        BG_WIDTH * 3 / 2,
                        BG_HEIGHT * 3 / 2,
                        TextureOptions.NEAREST_PREMULTIPLYALPHA
                ),
                context.getVertexBufferObjectManager()
        );
    }

    public AnimatedSprite buildAnimatedBirdSprite(
            SimpleBaseGameActivity context,
            int type
    ) {
        switch (type) {
            case Utility.YELLOW_BIRD_SPRITE:
                return buildAnimatedBirdSprite(
                        context,
                        YELLOWBIRD_IMAGE_NAME,
                        BIRDBITMAP_MAX_ROW,
                        BIRDBITMAP_MAX_COL
                );
            case Utility.RED_BIRD_SPRITE:
                return buildAnimatedBirdSprite(
                        context,
                        REDBIRD_IMAGE_NAME,
                        BIRDBITMAP_MAX_ROW,
                        BIRDBITMAP_MAX_COL
                );
            case Utility.BLUE_BIRD_SPRITE:
                return buildAnimatedBirdSprite(
                        context,
                        BLUEBIRD_IMAGE_NAME,
                        BIRDBITMAP_MAX_ROW,
                        BIRDBITMAP_MAX_COL
                );
            default:
                return buildAnimatedBirdSprite(
                        context,
                        BLUEBIRD_IMAGE_NAME,
                        BIRDBITMAP_MAX_ROW,
                        BIRDBITMAP_MAX_COL
                );
        }
    }

    public AnimatedSprite buildAnimatedBirdSprite(
            SimpleBaseGameActivity context,
            String bitmapname,
            int tileRow,
            int tileCol
    ) {
        BuildableBitmapTextureAtlas buildableBitmapTextureAtlas =
                new BuildableBitmapTextureAtlas(
                        context.getTextureManager(),
                        BIRD_BITMAP_WIDTH,
                        BIRD_BITMAP_HEIGHT,
                        TextureOptions.NEAREST
                );
        TiledTextureRegion tiledTextureRegion =
                BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                buildableBitmapTextureAtlas,
                context,
                bitmapname,
                tileCol,
                tileRow
        );

        try {
            buildableBitmapTextureAtlas.build(
                    new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0)
            );
            buildableBitmapTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            e.printStackTrace();
        }

        AnimatedSprite animatedSprite =
                new AnimatedSprite(
                        0,
                        0,
                        Bird.getBirdWith(),
                        Bird.getBirdWith(),
                        tiledTextureRegion,
                        context.getVertexBufferObjectManager()
                );
        //animatedSprite.animate(25);
        animatedSprite.setZIndex(2);

        return animatedSprite;
    }

    public Sprite[] buildPipePairSprites(SimpleBaseGameActivity context) {

        Sprite lowerPipeSprite = buildLowerPipeSprite(context);
        lowerPipeSprite.setZIndex(1);

        Sprite upperPipeSprite = buildUpperPipeSprite(context);
        upperPipeSprite.setZIndex(1);

        return new Sprite [] {
                upperPipeSprite,
                lowerPipeSprite
        };
    }

    public Sprite buildLowerPipeSprite(
            SimpleBaseGameActivity context
    ) {
        if (context == null)
            return null;

        Sprite pipeSprite = new Sprite(
                0,
                0,
                buildITextureRegion(
                        context,
                        LOWERPIPE_IMAGE_NAME,
                        (int)Pipe.getPipeWidth()+10,
                        (int)Pipe.getPipeHeight()+10,
                        TextureOptions.NEAREST_PREMULTIPLYALPHA
                ),
                context.getVertexBufferObjectManager()
        );
        pipeSprite.setZIndex(1);
        return pipeSprite;
    }

    public Sprite buildUpperPipeSprite(
            SimpleBaseGameActivity context
    ) {
        if (context == null)
            return null;

        Sprite pipeSprite = new Sprite(
                0,
                0,
                buildITextureRegion(
                        context,
                        UPPERPIPE_IMAGE_NAME,
                        (int)Pipe.getPipeWidth()+10,
                        (int)Pipe.getPipeHeight()+10,
                        TextureOptions.NEAREST_PREMULTIPLYALPHA
                ),
                context.getVertexBufferObjectManager()
        );
        pipeSprite.setZIndex(1);
        return pipeSprite;
    }

    private ITextureRegion buildITextureRegion(
            SimpleBaseGameActivity context,
            String imageName,
            int width,
            int height,
            TextureOptions textureOptions
    ) {
        if (context == null
                || imageName == null
                || textureOptions == null
                || width < 0 || height < 0)
            return null;

        BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(
                context.getTextureManager(),
                width,
                height,
                textureOptions
        );
        ITextureRegion iTextureRegion = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(
                        bitmapTextureAtlas,
                        context.getAssets(),
                        imageName,
                        0,
                        0
                );
        bitmapTextureAtlas.load();

        return iTextureRegion;
    }

    public void setSpritePosition(Sprite sprite, float x, float y) {
        if (sprite != null
                && x >= 0 && x <= GameActivity.getCameraWidth()
                && y >= 0 && y <= GameActivity.getCameraHeight()) {
            sprite.setX(x);
            sprite.setY(y);
        }
    }

    public void ceterSprite(Sprite sprite) {
        setSpritePosition(
                sprite,
                GameActivity.getCameraWidth() / 2,
                GameActivity.getCameraHeight() / 2
        );
    }

    public Sprite getBackGroundSprite () {
        return mBackGroundSprite;
    }
}
