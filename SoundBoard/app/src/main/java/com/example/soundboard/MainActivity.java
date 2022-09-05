/**
 * @author: Minh Duong
 * @description: This file contains the MainActivity object
 *               of the app SoundBoard, which is also the main
 *               only activity of the app. It declares the behaviors
 *               of the buttons on the screen, and use a runnable object
 *               (PlaybackRunnable) to run the sounds simultaneously.
 */

package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Context context;
    int timeSleep;

    /**
     * This method is called when the activity is created. It initializes
     * the fields of the activity, which is also the initial state of it.
     * (e.g timeSleep = 0)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        timeSleep = 0;
    }

    /**
     * This method is called when the first sound button is clicked. It
     * call the playback function below, which will create a runnable object
     * to play the sound.
     * @param view
     */
    public void onClickButton1(View view) {
        playback(R.raw.arrow);
    }

    /**
     * This method is called when the second sound button is clicked. It
     * call the playback function below, which will create a runnable object
     * to play the sound.
     * @param view
     */
    public void onClickButton2(View view) {
        playback(R.raw.hammer);
    }

    /**
     * This method is called when the third sound button is clicked. It
     * call the playback function below, which will create a runnable object
     * to play the sound.
     * @param view
     */
    public void onClickButton3(View view) {
        playback(R.raw.nope);
    }

    /**
     * This method is called when the fourth sound button is clicked. It
     * call the playback function below, which will create a runnable object
     * to play the sound.
     * @param view
     */
    public void onClickButton4(View view) {
        playback(R.raw.sweetroll);
    }

    /**
     * This method is used to play the sound effect, by creating a new
     * PlaybackRunnable object (given in this project), which will then
     * play the sound in a new thread.
     * @param res
     */
    public void playback(int res) {
        PlaybackRunnable pr = new PlaybackRunnable(this,
                                                    context,
                                                    timeSleep,
                                                    res);
        (new Thread(pr)).start();
    }

    /**
     * This method is called when the toggle delay button is clicked.
     * It will change the color of the button accordingly (red - yellow -
     * green), and set the timeSleep appropriately (0 - 1500 - 3000).
     * @param view
     */
    public void onToggleDelay(View view) {
        if (timeSleep == 0) {
            timeSleep = 1500;
            view.setBackgroundColor(getColor(R.color.semi_trans_yellow));
        }
        else if (timeSleep == 1500) {
            timeSleep = 3000;
            view.setBackgroundColor(getColor(R.color.semi_trans_green));
        } else {
            timeSleep = 0;
            view.setBackgroundColor(getColor(R.color.semi_trans_red));
        }
    }
}