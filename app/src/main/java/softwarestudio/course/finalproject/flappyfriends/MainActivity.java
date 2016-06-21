package softwarestudio.course.finalproject.flappyfriends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import softwarestudio.course.finalproject.flappyfriends.ResourceManager.SoundManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SoundManager soundManager = new SoundManager(this);
        //soundManager.startAudio(R.raw.backgroundmusic);

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
