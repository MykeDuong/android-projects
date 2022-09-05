/*
 * @author: Minh Duong
 * @description: This file contains the MainActivity object
 *               of the app ThemeCode. It contains all of the
 *               contents required to show the user interface
 *               of the app (except for pictures, which is in
 *               the drawable folder), and shows those contents
 *               when the app runs.
 */

package com.example.themecode;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * This function is called when the class is created. It contains
     * all of the necessary contents (except for pictures) and shows the user
     * interface when the app runs.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();

        // Scroll view
        ScrollView scroll = new ScrollView(context);
        scroll.setLayoutParams(new
                ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT));

        //Linear Layout
        LinearLayout linear = new LinearLayout(context);
        linear.setLayoutParams(new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.setBackgroundColor(Color.parseColor("#F5EEDC"));

        //Linear Layout to Scroll view
        scroll.addView(linear);
        setContentView(scroll);

        // Logo
        ImageView skyrimLogo = new ImageView(this);
        LinearLayout.LayoutParams skyrimLogoLp = new
                LinearLayout.LayoutParams(px(context, 400), px(context, 250));
        skyrimLogoLp.gravity = Gravity.CENTER_HORIZONTAL;
        skyrimLogo.setPadding(0, 100, 0, 0);
        skyrimLogo.setLayoutParams(skyrimLogoLp);
        skyrimLogo.setImageResource(R.drawable.skyrim_logo);
        linear.addView(skyrimLogo);

        // Heading
        TextView heading = new TextView(getApplicationContext());
        LinearLayout.LayoutParams headingLp = new
                LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        heading.setLayoutParams(headingLp);
        heading.setGravity(Gravity.CENTER_HORIZONTAL);
        heading.setPadding(70, 50, 70, 0);
        heading.setText("SKYRIM");
        heading.setTextColor(Color.parseColor("#694E4E"));
        heading.setTextSize(50);
        linear.addView(heading);

        // Intro
        TextView skyrimIntro = new TextView(getApplicationContext());
        LinearLayout.LayoutParams skyrimIntroLp = new
                LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        skyrimIntro.setLayoutParams(skyrimIntroLp);
        skyrimIntro.setPadding(80, 30, 80, 0);
        skyrimIntro.setText("The Elder Scrolls V: Skyrim is an open-world action role-playing" +
                            "video game developed by Bethesda Game Studios. It is the fifth main" +
                            "installment in the Elder Scrolls series.");
        skyrimIntro.setTextColor(Color.parseColor("#886F6F"));
        skyrimIntro.setTextSize(23);
        linear.addView(skyrimIntro);

        // Dragonborn image
        ImageView dragonborn = new ImageView(this);
        LinearLayout.LayoutParams dragonbornLp = new LinearLayout.LayoutParams(px(context, 296),
                px(context, 166));
        dragonbornLp.setMargins(0, 30, 0, 00);
        dragonbornLp.gravity = Gravity.CENTER_HORIZONTAL;
        dragonborn.setLayoutParams(dragonbornLp);
        dragonborn.setPadding(10, 10, 10, 10);
        dragonborn.setBackgroundColor(Color.parseColor("#BF8B67"));
        dragonborn.setImageResource(R.drawable.skyrim_dragonborn);
        linear.addView(dragonborn);

        //Description
        TextView skyrimDescription = new TextView(getApplicationContext());
        LinearLayout.LayoutParams skyrimDescriptionLp = new
                LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        skyrimDescription.setLayoutParams(skyrimDescriptionLp);
        skyrimDescription.setPadding(80, 30, 80, 0);
        skyrimDescription.setText("Its main story focuses on the player's character, the " +
                                  "Dragonborn, on their quest to defeat Alduin the World-Eater," +
                                  "a dragon who is prophesied to destroy the world. Over the" +
                                  "course of the game, the player completes quests and develops" +
                                  "the character by improving skills.");
        skyrimDescription.setTextColor(Color.parseColor("#886F6F"));
        skyrimDescription.setTextSize(23);
        linear.addView(skyrimDescription);

        //Landscape picture
        ImageView landscape = new ImageView(this);
        LinearLayout.LayoutParams landscapeLp = new LinearLayout.LayoutParams(px(context, 296),
                px(context, 166));
        landscapeLp.setMargins(0, 30, 0, 00);
        landscapeLp.gravity = Gravity.CENTER_HORIZONTAL;
        landscape.setLayoutParams(landscapeLp);
        landscape.setPadding(10, 10, 10, 10);
        landscape.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        landscape.setImageResource(R.drawable.skyrim_landscape);
        linear.addView(landscape);

        //Skyrim (region) description
        TextView skyrimLand = new TextView(getApplicationContext());
        LinearLayout.LayoutParams skyrimLandLp = new
                LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        skyrimLand.setLayoutParams(skyrimLandLp);
        skyrimLand.setPadding(80, 30, 80, 0);
        skyrimLand.setText("As the name suggests, the game takes place in Skyrim, a cold," +
                           "mountainous region in the northernmost of the continent. The" +
                           "landscape is filled with cities and towns, as well as forts," +
                           "camps, and ruins, many of which are hostile to the player.");
        skyrimLand.setTextColor(Color.parseColor("#886F6F"));
        skyrimLand.setTextSize(23);
        linear.addView(skyrimLand);

        //DLC image
        ImageView dlc = new ImageView(this);
        LinearLayout.LayoutParams dlcLp = new LinearLayout.LayoutParams(px(context, 296),
                px(context, 148));
        dlcLp.setMargins(0, 30, 0, 00);
        dlcLp.gravity = Gravity.CENTER_HORIZONTAL;
        dlc.setLayoutParams(dlcLp);
        dlc.setPadding(10, 10, 10, 10);
        dlc.setBackgroundColor(Color.parseColor("#C0D8C0"));
        dlc.setImageResource(R.drawable.skyrim_dawnguard);
        linear.addView(dlc);

        // DLC description
        TextView skyrimDlc = new TextView(getApplicationContext());
        LinearLayout.LayoutParams skyrimDlcLp = new
                LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        skyrimDlc.setLayoutParams(skyrimDlcLp);
        skyrimDlc.setPadding(80, 30, 80, 0);
        skyrimDlc.setText("Skyrim also received 3 Downloadable Contents (DLCs), which further" +
                          "expand the game. Dawnguard saw an overhaul to the vampire and" +
                          "werewolf systems, Hearthfire introduced an all-new home-building" +
                          "mechanic, and Dragonborn returned player to a place from one of" +
                          "the game's predecessor.");
        skyrimDlc.setTextColor(Color.parseColor("#886F6F"));
        skyrimDlc.setTextSize(23);
        linear.addView(skyrimDlc);

    }

    /**
     * This function converts values from dp to px. It helps changing the sizes and paddings of
     * objects when the app runs in different devices
     * @param context
     * @param dp            The dp value
     * @return px           The px value
     */
    private static int px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale + 0.5f);
        return px;
    }
}