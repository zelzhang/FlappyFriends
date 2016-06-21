package softwarestudio.course.finalproject.flappyfriends.Creature;

import org.andengine.entity.scene.background.ParallaxBackground;

import softwarestudio.course.finalproject.flappyfriends.GameActivity;

/**
 * Created by lusa on 2016/06/19.
 */
public class PipePair {

    private Pipe upperPipe;
    private Pipe lowerPipe;

    private float alignX;
    private float spawnPoint;
    public final static float ENTRANCE_HEIGHT = 120;

    public PipePair(Pipe upperPipe, Pipe lowerPipe)
        throws IllegalArgumentException{
        if (upperPipe == null || lowerPipe == null)
            throw new IllegalArgumentException("Null Argument");
        this.upperPipe = upperPipe;
        this.lowerPipe = lowerPipe;
    }

    public void ReplaceData(PipePair pipePair) {
        if (pipePair == null) return;
        if (pipePair.getUpperPipe() == null
                || pipePair.getLowerPipe() == null)
            return;

        alignX = pipePair.getAlignX();
        spawnPoint = pipePair.getSpawnPoint();
    }

    public void setSpawnPoint(float spawnPoint) {
        if (upperPipe == null || lowerPipe == null)
            return;
        this.spawnPoint = spawnPoint;
        setPipePairY();
    }

    public void setAlignX(float alignX) {
        if (upperPipe == null || lowerPipe == null)
            return;
        this.alignX = alignX;
        AlignOnX();
    }

    public void AlignOnX() {
        if (upperPipe == null || lowerPipe == null)
            return;
        upperPipe.setX(alignX);
        lowerPipe.setX(alignX);
    }

    public void setPipePairY() {
        if (upperPipe == null || lowerPipe == null)
            return;
        upperPipe.setY(
                spawnPoint
                        + ENTRANCE_HEIGHT / 2
                        + Pipe.getPipeHeight() / 2
        );
        lowerPipe.setY(
                spawnPoint
                - ENTRANCE_HEIGHT / 2
                - Pipe.getPipeHeight() / 2
        );
    }

    public void setUpperPipe(Pipe upperPipe) {
        if (upperPipe == null)
            throw new IllegalArgumentException("NULL PIPE");
        this.upperPipe = upperPipe;
    }
    public void setLowerPipe(Pipe lowerPipe) {
        if (lowerPipe == null)
            throw new IllegalArgumentException("NULL PIPE");
        this.lowerPipe = lowerPipe;
    }

    public Pipe getUpperPipe() {
        return upperPipe;
    }
    public Pipe getLowerPipe() {
        return lowerPipe;
    }
    public float getSpawnPoint() { return spawnPoint; }
    public float getAlignX() { return alignX; }
}
