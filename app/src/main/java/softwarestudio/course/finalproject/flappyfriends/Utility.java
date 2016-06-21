package softwarestudio.course.finalproject.flappyfriends;

import android.app.Activity;
import android.util.DisplayMetrics;

import softwarestudio.course.finalproject.flappyfriends.Creature.PipePair;

/**
 * Created by lusa on 2016/06/18.
 */
public class Utility {

    public final static int MAX_PIPEPAIRS = 4;
    public final static float SPAWN_UPPERBOUND = 700 - PipePair.ENTRANCE_HEIGHT / 2;
    public final static float SPAWN_LOWERBOUND = 100 + PipePair.ENTRANCE_HEIGHT / 2;

    public final static int TARGET_HOST = 0;
    public final static int TARGET_PEERA = 1;
    public final static int TARGET_NULL = Utility.MAX_PLAYERS;

    public final static int MAX_PLAYERS = 2;
    public final static float FLOOR_HEIGHT = 76;
    public final static int YELLOW_BIRD_SPRITE = 0;
    public final static int BLUE_BIRD_SPRITE = 1;
    public final static int RED_BIRD_SPRITE = 2;

    public static float calculateScreenWidth(Activity context, float windowHeight){
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int realHeight = dm.heightPixels;
        final int realWidth = dm.widthPixels;
        float ratio = (float)realWidth / (float)realHeight;
        return windowHeight * ratio;
    }

    public static float randomSpwanPostion() {
        float spawn = (float)Math.random() * GameActivity.getCameraHeight();
        if (spawn < SPAWN_LOWERBOUND)
            spawn = SPAWN_LOWERBOUND;
        if (spawn > SPAWN_UPPERBOUND)
            spawn = SPAWN_UPPERBOUND;
        return  spawn;
    }
}
