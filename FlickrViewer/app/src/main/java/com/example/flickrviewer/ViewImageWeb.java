/**
 * @author: Minh Duong
 * COURSE: CSC 317 - Spring 2022
 * @description: This file contains the ViewImageWeb activity of the app Flickr Viewer. It will
 *               show the website provided by the intent to the screen.
 */

package com.example.flickrviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class ViewImageWeb extends AppCompatActivity {

    /**
     * This method is called when the activity is created. It will get the url from the intent,
     * and set the WebView to the website provided.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.WEB_URL);

        Context context = getApplicationContext();
        WebView myWebView = new WebView(context);
        setContentView(myWebView);
        myWebView.loadUrl(url);
    }
}