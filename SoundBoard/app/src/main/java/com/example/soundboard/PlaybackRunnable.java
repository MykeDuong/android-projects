/**
 * @author: Minh Duong
 * @description: This file contains the PlaybackRunnable object of the app
 *               Sound Board, which is used to play a sound given from the
 *               MainActivity. It will be used to play multiple sounds
 *               simultaneously. It also allows for delays before playing
 *               the sound.
 */

package com.example.soundboard;

import android.content.Context;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

public class PlaybackRunnable implements Runnable {
    Context context;
    AppCompatActivity invokerActivity = null;
    int res;
    int delay = 0;

    /**
     * The constructor method for the class. It takes the invoking activity, app context,
     * delay time and resource index as parameters.
     * @param activity      The invoking activity
     * @param context       The context of the app
     * @param delay         The delay time before playing the sound
     * @param res           The resource index of the sound file.
     */
    public PlaybackRunnable(AppCompatActivity activity, Context context, int delay, int res) {
        this.context = context;
        invokerActivity = activity;
        this.res = res;
        this.delay = delay;
    }

    /**
     * This method is use to run this runnable object. It will wait depending on the
     * delay field and play the audio by calling playAudio().
     */
    @Override
    public void run() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playAudio(res);
    }

    /**
     * This method is used to play the audio with the resource from the resource
     * id given as its parameter. It will use MediaPlayer to do so.
     * @param res   The resource of the sound file.
     */
    private void playAudio(int res) {
        MediaPlayer player = MediaPlayer.create(context,
                                                res);
        player.setLooping(false); // don’t loop
        player.setVolume(1, 1);

        player.start();
    }
}
