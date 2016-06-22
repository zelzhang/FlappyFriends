package softwarestudio.course.finalproject.flappyfriends;

import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

import softwarestudio.course.finalproject.flappyfriends.Creature.Bird;
import softwarestudio.course.finalproject.flappyfriends.Creature.Pipe;
import softwarestudio.course.finalproject.flappyfriends.Creature.PipePair;
import softwarestudio.course.finalproject.flappyfriends.Receiver.ReceiveDataStorage;
import softwarestudio.course.finalproject.flappyfriends.ResourceManager.BirdManager;
import softwarestudio.course.finalproject.flappyfriends.ResourceManager.FontManager;
import softwarestudio.course.finalproject.flappyfriends.ResourceManager.ImageManager;
import softwarestudio.course.finalproject.flappyfriends.ResourceManager.PipeManager;
import softwarestudio.course.finalproject.flappyfriends.ResourceManager.SceneManager;
import softwarestudio.course.finalproject.flappyfriends.ResourceManager.SoundManager;

/**
 * Created by lusa on 2016/06/18.
 */
public class GameActivity extends SimpleBaseGameActivity {

    private static float CAMERA_HEIGHT = 800;
    private static float CAMERA_WIDTH = 485;

    private Camera mCamera = null;

    private SoundManager mSoundManager = null;
    private FontManager mFontManager = null;
    private ImageManager mImageManager = null;
    private PipeManager mPipeManager = null;
    private BirdManager mBirdManager = null;

    private SceneManager mSceneManager = null;
    private Scene mScene = null;
    private Background mBackGround = null;

    private Bird[] birds;
    private Pipe[] pipes;

    private static final float SCROLL_SPEED = 4.5f;	// game speed
    private int gamestate = R.integer.GAME_IDLE;

    private float mCurrentWorldPosition = 0;

    private int curScore = 0;

    @Override
    protected void onCreateResources() throws IOException {
        CAMERA_HEIGHT = 800;
        CAMERA_WIDTH = Utility.calculateScreenWidth(this, CAMERA_HEIGHT);

        mSoundManager = new SoundManager(this);
        mFontManager = new FontManager(this);
        mImageManager = new ImageManager(this);
        mPipeManager = new PipeManager(this, mImageManager, Utility.MAX_PIPEPAIRS);
        mBirdManager = new BirdManager(
                this,
                mImageManager,
                ReceiveDataStorage.getPlayerNum()
        );
    }

    @Override
    protected Scene onCreateScene() {

        mBackGround = new ParallaxBackground(82/255f, 190/255f, 206/255f) {

            private float prevX = 0;
            private float parallaxValueOffset = 0;

            @Override
            public void onUpdate(float pSecondsElapsed) {
                switch(gamestate){
                    case R.integer.GAME_OPRATE:
                        final float cameraCurrentX = mCurrentWorldPosition;//mCamera.getCenterX();

                        if (prevX != cameraCurrentX) {

                            parallaxValueOffset +=  cameraCurrentX - prevX;
                            this.setParallaxValue(parallaxValueOffset);
                            prevX = cameraCurrentX;
                        }
                        break;
                    default: break;
                }

                super.onUpdate(pSecondsElapsed);
            }
        };

        mSceneManager = new SceneManager(
                this,
                mFontManager,
                mImageManager,
                (ParallaxBackground) mBackGround
        );
        mScene = mSceneManager.buildScene();
        mPipeManager.AttachToScene(mScene);
        mBirdManager.AttachToScene(mScene);
        /*
        Pipe upper = new Pipe(82, 0, GameActivity.getCameraWidth()/2, GameActivity.getCameraHeight());
        Pipe lower = new Pipe(82, 0, GameActivity.getCameraWidth()/2, 0);
        PipePair pipePair = new PipePair(upper, lower);
        pipePair.randomPipePairHeight();
        Sprite[] temp = mImageManager.buildPipePairSprites(this, pipePair);
        temp[0].setX(upper.getX());
        temp[0].setY(upper.getY());
        temp[1].setX(lower.getX());
        temp[1].setY(lower.getY());
        mScene.attachChild(temp[0]);
        mScene.attachChild(temp[1]);
        */
        mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
                if (pSceneTouchEvent.isActionDown()) {
                    switch (gamestate) {
                        case R.integer.GAME_PREPARE:
                            // if only one player(non-multi-player mode)
                            // game starts as screen touched
                            // else starts after 3s
                            //if (birds != null && birds.length == 1)
                                ReceiveDataStorage.setGameActivation(true);
                            break;
                        case R.integer.GAME_OPRATE:
                            mBirdManager.SendCommand();
                            curScore++;
                            break;
                        default: break;
                    }
                }
                return false;
            }
        });
        return mScene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        CAMERA_HEIGHT = 800;
        CAMERA_WIDTH = Utility.calculateScreenWidth(this, CAMERA_HEIGHT);
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT) {

            @Override
            public void onUpdate(float pSecondsElapsed) {
                switch (gamestate) {
                    case R.integer.GAME_IDLE:
                        onIdle();
                        break;
                    case R.integer.GAME_PREPARE:
                        onPrepare();
                        break;
                    case R.integer.GAME_OPRATE:
                        onOperate();
                        break;
                    case R.integer.GAME_STOP:
                        onStop();
                        break;
                    default:
                        gamestate = R.integer.GAME_IDLE;
                        break;
                }
                super.onUpdate(pSecondsElapsed);
            }


            private void onIdle() {
                curScore = 0;
                gamestate = R.integer.GAME_PREPARE;
            }

            private void onPrepare() {
                mSoundManager.startAudio(R.raw.backgroundmusic);
                mPipeManager.setReadyPosition();
                mBirdManager.setReadyPosition();
                // if only one player(non-multi-player mode)
                // game starts as screen touched
                // else starts after 3s
                if (ReceiveDataStorage.getGameActivation())
                    gamestate = R.integer.GAME_OPRATE;
            }
            private void onOperate() {
                mSceneManager.setScoreBoard(curScore);
                mCurrentWorldPosition -= SCROLL_SPEED;
                mPipeManager.receiveCommand();
                mBirdManager.FetchCommand();
                mBirdManager.DetectBirdsOverBound();
                if (!ReceiveDataStorage.getGameActivation())
                    gamestate = R.integer.GAME_STOP;
            }
            private void onStop() {
                mSoundManager.startAudio(R.raw.gameover);
                mSoundManager.stopAudio(R.raw.backgroundmusic);
                gamestate = R.integer.GAME_IDLE;
            }
        };

        EngineOptions engineOptions = new EngineOptions(
                true,
                ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
                mCamera
        );

        return engineOptions;
    }

    @Override
    public void onPause() {
        super.onPause();
        mSoundManager.stopAudio(R.raw.backgroundmusic);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSoundManager.releaseAllAudio();
    }

    public static float getCameraHeight() {
        return CAMERA_HEIGHT;
    }

    public static float getCameraWidth() {
        return CAMERA_WIDTH;
    }
}
