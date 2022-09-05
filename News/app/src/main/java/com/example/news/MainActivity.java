package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Minh Duong
 * COURSE: CSC 317
 * @description: This class represents the main activity of the class, which is the base and
 *               the container for the fragments showing the UI. It will start the
 *               SearchFragment fragment.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method is called when the activity is created. It will create a SearchFragment
     * and show it in the layout container.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment searchFragment = new SearchFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_layout_container,
                            searchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}