/*
 * @author: Minh Duong
 * @description: This file contains the DragonbornDescriptionActivity
 *               object of the app ThemeNav. This activity contains a
 *               ListView of available character races that is playable
 *               on the game Skyrim, using an ArrayAdapter.
 */

package com.example.themenav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DragonbornDescriptionActivity extends AppCompatActivity {
    private String[] raceList;

    /**
     * This function is called when the class is created. It initializes the raceList
     * array, creates an ArrayAdapter and set such adapter for the list view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragonborn_description);

        raceList = new String[] {"Altmer", "Argonian", "Bosmer", "Breton", "Dunmer", "Imperial",
                                 "Khajiit", "Nord", "Vampire", "Werewolf"};

        // Get a reference to the ListView
        ListView shoppingListView = (ListView)findViewById(R.id.races_view);

        ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<String>(this, R.layout.race_row,
                R.id.race_row_item, raceList);

        shoppingListView.setAdapter(arrayAdapter);
    }
}