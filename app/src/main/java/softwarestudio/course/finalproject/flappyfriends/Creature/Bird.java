package softwarestudio.course.finalproject.flappyfriends.Creature;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import softwarestudio.course.finalproject.flappyfriends.GameActivity;
import softwarestudio.course.finalproject.flappyfriends.Utility;

/**
 * Created by lusa on 2016/06/18.
 */
public class Bird {

    private final static float BIRD_WIDTH = 71;
    private final static float BIRD_HEIGHT = 51;


    // negative : counter-clockwise
    // positive : clockwise
    private final static float MIN_ANGLE = -20;
    private final static float MAX_ANGLE = 90;
    public final static float GRAVITY_DRAG_TORQUE = 4.0f;
    public final static float FLAP_DRAG_TORQUE = -15.0f;

    public final static float MIN_Y_SPEED = -12.0f;
    public final static float GRAVITY_ACCEL = -0.04f;
    public final static float FLAP_ACCEL = 6f;

    private float x;
    private float y;
    private float angle;
    private float y_speed;
    private float y_accel;

    public Bird () {
        x = 0;
        y = 0;
        angle = 0;
        y_speed = 0;
        y_accel = 0;
    }
    public Bird (float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.y_speed = 0;
        this.y_accel = 0;
    }

    public void ReplaceData(Bird bird) {
        if (bird == null) return;
        x = bird.getX();
        y = bird.getY();
        setAngle(bird.getAngle());
        setSpeed(bird.getSpeend());
        setAccel(bird.getAccel());
    }

    public boolean outofLowerBound() {
        if (y < Utility.FLOOR_HEIGHT + BIRD_WIDTH / 2)
            return true;
        return false;
    }

    public boolean outofUpperBound() {
        if (y > GameActivity.getCameraHeight() - BIRD_HEIGHT / 2)
            return true;
        return false;
    }

    public boolean outofVerticalBound() {
        return outofLowerBound()
                || outofUpperBound();
    }

    public void BirdJump() {
        if (!outofVerticalBound()) {
            setSpeed(FLAP_ACCEL);
            setAccel(0);
        }
    }

    public void move() {
        if (!outofVerticalBound()) {
            if (y_speed > -1 * FLAP_ACCEL)
                setAngle(angle + FLAP_DRAG_TORQUE);
            else
                setAngle(angle + GRAVITY_DRAG_TORQUE);
            setY(y + y_speed);
            y_accel += GRAVITY_ACCEL;
            setSpeed(y_speed + y_accel);
        }
    }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setAngle(float angle) {
        if (angle > MAX_ANGLE)
            this.angle = MAX_ANGLE;
        else if (angle < MIN_ANGLE)
            this.angle = MIN_ANGLE;
        else
            this.angle = angle;
    }

    public void setSpeed(float y_speed) {
        this.y_speed = Math.max(y_speed, MIN_Y_SPEED);
    }

    public void setAccel(float y_accel) {
        this.y_accel = y_accel;
    }

    public static float getBirdWith() { return BIRD_WIDTH; }
    public static float getBirdHeight() { return BIRD_HEIGHT; }
    public float getX() { return x; }
    public float getY() { return y; }
    public float getAngle() { return angle; }
    public float getSpeend() { return y_speed; }
    public float getAccel() { return y_accel; }
}
