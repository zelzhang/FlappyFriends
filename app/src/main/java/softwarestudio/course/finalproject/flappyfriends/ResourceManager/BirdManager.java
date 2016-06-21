package softwarestudio.course.finalproject.flappyfriends.ResourceManager;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.util.ArrayList;
import java.util.List;

import softwarestudio.course.finalproject.flappyfriends.Creature.Bird;
import softwarestudio.course.finalproject.flappyfriends.Creature.Command;
import softwarestudio.course.finalproject.flappyfriends.GameActivity;
import softwarestudio.course.finalproject.flappyfriends.Receiver.ReceiveDataStorage;
import softwarestudio.course.finalproject.flappyfriends.Utility;

/**
 * Created by lusa on 2016/06/21.
 */
public class BirdManager {

    private final static String LOG_TAG = BirdManager.class.getSimpleName();

    public final static int MAX_BIRDS = 2;

    // parameter used by game host
    private static List<Command> commands;

    private static class BirdSprite{

        Bird bird;

        AnimatedSprite animatedSprite;

        public BirdSprite(
                Bird bird,
                AnimatedSprite animatedSprite
        ) throws IllegalArgumentException{
            if (animatedSprite == null)
                throw new IllegalArgumentException("Null Sprite");
            this.bird = bird;
            this.animatedSprite = animatedSprite;
            setBirdSpritePosition();
        }

        public void modifyBird(Bird bird) {
            if (bird == null) return;
            if (this.bird == null)
                this.bird = bird;
            else
                this.bird.ReplaceData(bird);
            setBirdSpritePosition();
        }

        public void StopAnimation() {
            if (animatedSprite.isAnimationRunning())
                animatedSprite.stopAnimation();
        }

        public void AnimationOn() {
            if (!animatedSprite.isAnimationRunning())
                animatedSprite.animate(30);
        }

        public void setBirdSpritePosition() {
            if (animatedSprite == null || bird == null)
                return;
            animatedSprite.setX(bird.getX());
            animatedSprite.setY(bird.getY());
            animatedSprite.setRotation(bird.getAngle());
        }

        public void moveoutofRightBound() {
            bird.setX(
                    GameActivity.getCameraWidth() + Bird.getBirdWith() * 2);
            setBirdSpritePosition();
        }

        public void BirdJump() {
            bird.BirdJump();
            setBirdSpritePosition();
        }

        public void move() {
            bird.move();
            setBirdSpritePosition();
        }

        public void setX(float x) {
            bird.setX(x);
            setBirdSpritePosition();
        }

        public void setY(float y) {
            bird.setY(y);
            setBirdSpritePosition();
        }

        public void setAngle(float angle) {
            bird.setAngle(angle);
            setBirdSpritePosition();
        }

        public void setSpeed(float speed) {
            bird.setSpeed(speed);
        }

        public boolean outofVerticalBound() {
            return bird.outofVerticalBound();
        }

        public boolean outofLowerBound() {
            return bird.outofLowerBound();
        }

        public boolean outofUpperBound() {
            return bird.outofLowerBound();
        }

        //public Bird getBird() { return bird; }
        public AnimatedSprite getAnimatedSprite() { return animatedSprite; }
    }

    private List<BirdSprite> birdSprites;

    /**
     * Build array list of birds. Initial new array list
     * Number of bird is determined by "playernum"
     * Animated spite returns form {@link ImageManager}
     * @param context
     * @param imageManager
     * @param playernum
     * @throws IllegalArgumentException
     */
    public BirdManager(
            SimpleBaseGameActivity context,
            ImageManager imageManager,
            int playernum
    ) throws IllegalArgumentException{
        if (context == null || imageManager == null)
            throw new IllegalArgumentException("Null Argument");
        if (playernum <= 0)
            throw new IllegalArgumentException("Illegal Player Number");

        birdSprites = new ArrayList<>();

        for (int i=0; i<playernum; i++) {
            AnimatedSprite animatedSprite =
                    imageManager.buildAnimatedBirdSprite(
                            context,
                            i
                    );
            BirdSprite birdSprite = new BirdSprite(
                    new Bird(),
                    animatedSprite
            );
            birdSprite.moveoutofRightBound();
            birdSprites.add(birdSprite);
        }
    }

    /**
     * Attach all bird sprites to scene
     * @param scene
     */
    public void AttachToScene(Scene scene) {
        if (scene == null) return;

        int size = birdSprites.size();
        for (int i=0; i<size; i++) {
            scene.attachChild(birdSprites.get(i).getAnimatedSprite());
        }
    }

    public void setReadyPosition() {
        float start = GameActivity.getCameraWidth() / 2;
        int size = birdSprites.size();
        for (int i=0; i<size; i++) {
            BirdSprite cur = birdSprites.get(i);
            cur.setX(start);
            cur.setY(GameActivity.getCameraHeight() / 2);
            cur.setAngle(0);
            cur.setSpeed(0);
            start -= Bird.getBirdWith() * 4 / 3;
        }
    }

    public void SendCommand() {
        ReceiveDataStorage.addCommandToCommandQueue(
                new Command(ReceiveDataStorage.getPlayerLabel())
        );
    }

    private void FetchBirdData(List<Bird> newdata) {
        if (newdata == null || newdata.size() == 0)
            return;
        int originsize = birdSprites.size();
        int newsize = newdata.size();
        newsize = newsize > Utility.MAX_PLAYERS ?
                Utility.MAX_PLAYERS : newsize;
        int size = newsize > originsize ?
                originsize : newsize;

        for (int i=0; i<size; i++) {
            BirdSprite cur = birdSprites.get(i);
            cur.modifyBird(newdata.get(i));
        }
    }

    public void FetchCommand() {
        if (ReceiveDataStorage.getPlayerLabel()
                == Utility.TARGET_HOST) {
            if (!ReceiveDataStorage.getConnection()) {
                ReceiveDataStorage.FetchCommandQueueToCommandList();
            }
            commands = ReceiveDataStorage.getCommandListCopyAndClearOrigin();
            if (commands.size() > 0)
                ExecuteCommands();
            moveBirds();
        } else if (ReceiveDataStorage.getPlayerLabel() > Utility.TARGET_HOST) {
            // As a multi-player participant
            List<Bird> newdata = ReceiveDataStorage.getBirds();
            FetchBirdData(newdata);
        }
    }

    private void ExecuteCommands() {
        if (commands == null && commands.size() <= 0)
            return;
        int size = commands.size();
        for (int i=0; i<size; i++) {
            int target = commands.get(i).getCommandTarget();
            if (target < Utility.TARGET_NULL)
                birdSprites.get(target).BirdJump();
        }
    }

    private void moveBirds() {
        int size = birdSprites.size();
        for (int i=0; i<size; i++)
            birdSprites.get(i).move();
    }

    public void DetectBirdsOverBound() {
        if (birdSprites == null || birdSprites.size() == 0)
            ReceiveDataStorage.setGameActivation(false);
        else {
            int size = birdSprites.size();
            boolean check = true;
            for (int i=0; i<size && check; i++) {
                check &= birdSprites.get(i).outofVerticalBound();
            }
            ReceiveDataStorage.setGameActivation(!check);
        }
    }
}
