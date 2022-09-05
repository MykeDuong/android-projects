/**
 * @author: Minh Duong
 * @description: This file contains the SummaryActivity object
 *               of the app Four Temperaments Quiz, shows the user's result
 *               of the temperament quiz before it with detailed descriptions
 *               about the resulting temperament.
 */


package com.example.fourtemperamentsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    /**
     * This function is called when the class is created. It takes the integer array
     * from the intent and uses it to show the result to the user.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Context ctx = getApplicationContext();

        Intent intent = getIntent();
        int[] points = intent.getIntArrayExtra(QuizActivity.RESULT);
        List<Integer> maxIndex = maxIndex(points);

        String resultText = "";
        TextView result = (TextView) findViewById(R.id.summary_result);
        for (int i = 0; i < maxIndex.size(); i++) {
            resultText += result(maxIndex.get(i));
            if (i != maxIndex.size() - 1)
                resultText += "\n";
        }
        result.setText(resultText);

        String descriptionText = "";
        TextView description = (TextView) findViewById(R.id.temp_description);
        if (maxIndex.size() > 1)
            descriptionText += getString(R.string.congratulations, maxIndex.size());
        for (int i = 0; i < maxIndex.size(); i++) {
            descriptionText += getString(descriptionId(maxIndex.get(i)));
            if (i != maxIndex.size() - 1)
                descriptionText += "\n\n";
        }
        description.setText(descriptionText);
        showScore(points);
    }

    /**
     * This function return a list of largest integers given in an integer array.
     * @param arr
     * @return maxIndex
     */
    private List<Integer> maxIndex(int[] arr) {
        List<Integer> maxIndex = new ArrayList<Integer>();
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == max) {
                maxIndex.add(i);
            }
        }
        return maxIndex;
    }

    /**
     * This function returns the temperament in correspondance with its index value
     * 1: Choleric
     * 2: Sanguine
     * 3: Melancholic
     * 4: Phlegmatic
     * @param i
     */
    private String result(int i) {
        if (i == 0)
            return "Choleric";
        else if (i == 1)
            return "Sanguine";
        else if (i == 2)
            return "Melancholic";
        else if (i == 3)
            return "Phlegmatic";
        else
            return "Uncategorized";
    }

    /**
     * This function returns  the temperament description in correspondance with its index value
     * 1: Choleric
     * 2: Sanguine
     * 3: Melancholic
     * 4: Phlegmatic
     * @param i
     */
    private int descriptionId(int i) {
        if (i == 0)
            return R.string.choleric;
        else if (i == 1)
            return R.string.sanguine;
        else if (i == 2)
            return R.string.melancholic;
        else if (i == 3)
            return R.string.phlegmatic;
        else
            return -1;
    }

    /**
     * This function is used to show the score of the user.
     * @param points
     */
    private void showScore(int[] points) {
        TextView choleric = (TextView) findViewById(R.id.choleric_score);
        TextView sanguine = (TextView) findViewById(R.id.sanguine_score);
        TextView melancholic = (TextView) findViewById(R.id.melancholic_score);
        TextView phlegmatic = (TextView) findViewById(R.id.phlegmatic_score);

        choleric.setText(getString(R.string.choleric_score, points[0]));
        sanguine.setText(getString(R.string.sanguine_score, points[1]));
        melancholic.setText(getString(R.string.melancholic_score, points[2]));
        phlegmatic.setText(getString(R.string.phlegmatic_score, points[3]));
    }
}