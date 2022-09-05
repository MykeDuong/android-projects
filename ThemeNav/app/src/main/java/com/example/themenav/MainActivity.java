/*
 * @author: Minh Duong
 * @description: This file contains the MainActivity object
 *               of the app ThemeNav. Aside from the onCreate()
 *               method, it also provides two functions for
 *               two buttons that will start their respective
 *               activities.
 */

package com.example.themenav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    /**
     * This function is called when the class is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This function is called when the race details button is pressed.
     * It will start the DragonbornDescriptionActivity activity.
     * @param view
     */
    public void DragonbornButtonPressed(View view) {
        Intent intent = new Intent(this, DragonbornDescriptionActivity.class);
        startActivity(intent);
    }


    /**
     * This function is called when the dlc details button is pressed.
     * It will start the DlcDescriptionActivity activity.
     * @param view
     */
    public void dlcButtonPressed(View view) {
        Intent intent = new Intent(this, DlcDescriptionActivity.class);
        startActivity(intent);
    }
}