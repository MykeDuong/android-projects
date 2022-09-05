/*
 * @author: Minh Duong
 * @description: This file contains the MainActivity object
 *               of the app ThemeCode, which shows the UI of the app when run.
 */

package com.example.themexml;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}