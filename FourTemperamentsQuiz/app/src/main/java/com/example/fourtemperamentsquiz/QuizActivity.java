/**
 * @author: Minh Duong
 * @description: This file contains the QuizActivity object
 *               of the app Four Temperaments Quiz, which is
 *               its quiz screen.
 */


package com.example.fourtemperamentsquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    public static final String RESULT = "com.example.Four_Temperaments_Quiz.RESULT";
    private String[] questions;
    private int cur;
    private int[] points;

    /**
     * This function is called when the class is created. It initializes
     * all class fields and call appropriate functions when an action is done.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Context ctx = getApplicationContext();

        questions = new String[12];
        cur = 0;
        points = new int[] {0,0,0,0};
        addQuestions(ctx);

        TextView questionView = (TextView) findViewById(R.id.question);
        questionView.setText(questions[0]);
        onClickNext(questionView);
    }

    /**
     * This function handles the next button when being pressed. It can update the
     * appropriate values, warn the user to choose one or start the next activity
     * (summary activity)
     * @param questionView
     */
    public void onClickNext(TextView questionView) {
        RadioGroup options = findViewById(R.id.options);
        TextView warning = (TextView) findViewById(R.id.answer_warning);
        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedRadioButtonId = options.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    RadioButton selectedId = findViewById(selectedRadioButtonId);
                    String selectedText = selectedId.getText().toString();
                    updatePoints(cur, selectedText);
                    warning.setText("");
                    if (cur >= 11) {
                        Intent intent = new Intent(QuizActivity.this,
                                                    SummaryActivity.class);
                        intent.putExtra(RESULT, points);
                        startActivity(intent);
                    } else {
                        cur++;
                        questionView.setText(questions[cur]);
                    }
                } else
                    // Warn user to choose an option if none is chosen.
                    warning.setText(getString(R.string.answer_warning));
            }
        });
    }

    /**
     * This function handles the next button when being pressed. It can update the
     * appropriate values, warn the user to choose one or start the next activity
     * (summary activity)
     * @param ctx
     */
    private void addQuestions(Context ctx) {
        try {
            selectQuestions(ctx, R.raw.choleric, 0);
            selectQuestions(ctx, R.raw.sanguine, 1);
            selectQuestions(ctx, R.raw.melancholic, 2);
            selectQuestions(ctx, R.raw.phlegmatic, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function selects 3 random questions from a file.
     * @param ctx
     * @param ResId
     */
    private void selectQuestions(Context ctx, int ResId, int ord) throws IOException {
        // Take all available questions in the file
        List<String> localQuestions = new ArrayList<String>();
        int count = 0;

        InputStream inputStream = ctx.getResources().openRawResource(ResId);
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line = bufferedReader.readLine();
        while (line != null) {
            localQuestions.add(line);
            count += 1;
            line = bufferedReader.readLine();
        }

        // Random & add to question array
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i < count; i++)
            list.add(i);
        Collections.shuffle(list);
        for (int i = 0; i<3; i++) {
            questions[i * 4 + ord] = localQuestions.get(list.get(i));
        }
    }

    /**
     * This function updates user's point appropriately with their answer.
     * @param i
     * @param selectedText
     */
    private void updatePoints(int i, String selectedText) {
        int category = i % 4;
        int reward = 0;
        if (selectedText.equals("Strongly Agree"))
            reward = 4;
        else if (selectedText.equals("Partially Agree"))
            reward = 3;
        else if (selectedText.equals("Neutral"))
            reward = 2;
        else if (selectedText.equals("Partially Disagree"))
            reward = 1;
        else if (selectedText.equals("Strongly Disagree"))
            reward = 0;
        this.points[category] += reward;
    }
}