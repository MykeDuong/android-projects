/**
 * @author: Minh Duong
 * @description: This file contains the MainActivity object
 *               of the app Four Temperaments Quiz, which also
 *               serves as its welcome screen. It has a button
 *               for the user to start the quiz, which will
 *               start the QuizActivity activity.
 */

package com.example.fourtemperamentsquiz;

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
     * This function is called when the begin button is pressed.
     * It will start the QuizActivity activity.
     * @param view
     */
    public void onClickBegin(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }
}