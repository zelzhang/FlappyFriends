package softwarestudio.course.finalproject.flappyfriends.Creature;

import softwarestudio.course.finalproject.flappyfriends.GameActivity;

/**
 * Created by lusa on 2016/06/18.
 */
public class Pipe {
    private final static float PIPE_WIDTH = 120;
    private final static float PIPE_HEIGHT = 738;

    private float x;
    private float y;

    public Pipe(){
        x = 0;
        y = 0;
    };
    public Pipe(float x , float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }

    public static float getPipeWidth() {
        return PIPE_WIDTH;
    }
    public static float getPipeHeight() {
        return PIPE_HEIGHT;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
}
