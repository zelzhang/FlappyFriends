package softwarestudio.course.finalproject.flappyfriends.ResourceManager;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import softwarestudio.course.finalproject.flappyfriends.R;

/**
 * Created by lusa on 2016/06/18.
 */
public class SoundManager {

    private final static String LOG_TAG = SoundManager.class.getSimpleName();

    private static MediaPlayer soundEffect_dead = null;
    private static MediaPlayer soundEffect_score = null;
    private static MediaPlayer music_background = null;

    public SoundManager(Context context)
        throws IllegalArgumentException{
        if (context != null) {
            if (soundEffect_dead == null) {
                try {
                    soundEffect_dead = MediaPlayer.create(context, R.raw.gameover);
                } catch (RuntimeException e) {
                    Log.e(LOG_TAG, "create failed - " + e.toString());
                }
            }
            if (soundEffect_score == null) {
                try {
                    soundEffect_score = MediaPlayer.create(context, R.raw.score);
                } catch (RuntimeException e) {
                    Log.e(LOG_TAG, e.toString());
                }
            }
            if (music_background == null) {
                try {
                    music_background = MediaPlayer.create(context, R.raw.backgroundmusic);
                    music_background.setLooping(true);
                } catch (RuntimeException e) {
                    Log.e(LOG_TAG, e.toString());
                }
            }
        } else
            throw new IllegalArgumentException("Empty Context");
    }

    public void startAudio(int id) {
        switch (id) {
            case R.raw.gameover:
                //startAudio(soundEffect_dead);
                soundEffect_dead.start();
                break;
            case R.raw.score:
                startAudio(soundEffect_score);
                break;
            case R.raw.backgroundmusic:
                startAudio(music_background);
                break;
            default:
                throw new IllegalArgumentException("Audio Start : Media Not Found");
        }
    }

    public void stopAudio(int id)
        throws IllegalArgumentException{
        switch (id) {
            case R.raw.gameover:
                stopAudio(soundEffect_dead);
                break;
            case R.raw.score:
                stopAudio(soundEffect_score);
                break;
            case R.raw.backgroundmusic:
                stopAudio(music_background);
                break;
            default:
                throw new IllegalArgumentException("Audio Stop : Media Not Found");
        }
    }

    public void releaseAllAudio() {
        releaseAudio(soundEffect_dead);
        releaseAudio(soundEffect_score);
        releaseAudio(music_background);
    }

    private void startAudio(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null
                && !mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.start();
            } catch (RuntimeException e) {
                Log.e(LOG_TAG, "audio start failed - " + e.toString());
            }
        }
    }

    private void stopAudio(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null
                && mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.pause();
            } catch (RuntimeException e) {
                Log.e(LOG_TAG, "audio stop failed - " + e.toString());
            }
        }
    }

    private void releaseAudio(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (RuntimeException e) {
                Log.e(LOG_TAG, "audio release failed - " + e.toString());
            }
        }
    }
}
