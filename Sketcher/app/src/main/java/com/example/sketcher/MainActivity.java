package com.example.sketcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

/**
 * @author Minh Duong
 * COURSE: CSC 317
 * @description: This class represents the main activity of the class, which is the base and
 *               the container for the fragments showing the UI. It will start the
 *               DrawingFragment fragment.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method is called when the activity is created. It will create a DrawingFragment
     * and show it in the layout container.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment searchFragment = new DrawingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_layout_container,
                searchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}