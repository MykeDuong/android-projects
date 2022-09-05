/*
 * @author: Minh Duong
 * @description: This file contains the DragonbornDescriptionActivity
 *               object of the app ThemeNav. This activity contains a
 *               ListView of DLC pictures and descriptions of the game
 *               Skyrim, using a SimpleAdapter.
 */


package com.example.themenav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DlcDescriptionActivity extends AppCompatActivity {
    //DLC descriptions
    String[] dlc;

    //Id of images
    int[] dlcImages;

    /**
     * This function is called when the class is created. It initializes the dlc
     * and dlcImages arrays, create a SimpleAdapter using Hashmap and set such
     * adapter for list view.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlc_description);

        dlc = new String[] {getString(R.string.dawnguard_detail),
                            getString(R.string.hearthfire_detail),
                            getString(R.string.dragonborn_detail),
                            getString(R.string.anni_detail)};

        dlcImages = new int[]{R.drawable.dawnguard_details, R.drawable.hearthfire_details,
                              R.drawable.dragonborn_details, R.drawable.anniversary_details};

        List<HashMap<String, String>> dlcList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < dlcImages.length; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("dlc_row_image", Integer.toString(dlcImages[i]));
            hm.put("dlc_row_text", dlc[i]);
            dlcList.add(hm);
        }

        String[] from = {"dlc_row_image", "dlc_row_text"};
        int[] to = {R.id.dlc_row_image, R.id.dlc_row_text};
        SimpleAdapter simpleAdapter =
                new SimpleAdapter(getBaseContext(), dlcList,
                        R.layout.dlc_row, from, to);
        ListView dlcView = (ListView) findViewById(R.id.dlc_view);
        dlcView.setAdapter(simpleAdapter);
    }
}