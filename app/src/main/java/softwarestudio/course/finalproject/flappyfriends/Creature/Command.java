package softwarestudio.course.finalproject.flappyfriends.Creature;

import softwarestudio.course.finalproject.flappyfriends.Utility;

/**
 * Created by lusa on 2016/06/21.
 */
public class Command {

    private int target = Utility.TARGET_HOST;

    public Command(int target) {
        setCommandTarget(target);
    }

    public void setCommandTarget(int target) {
        if (target < Utility.MAX_PLAYERS)
            this.target = target;
        else
            this.target = Utility.TARGET_NULL;
    }

    public int getCommandTarget() { return target; }
}
