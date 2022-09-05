/**
 * @author: Minh Duong
 * COURSE: CSC 317 - Spring 2022
 * @description: This file contains the Image Fetcher Service of the app Flickr Viewer, which will
 *               downloads the pictures for when the main activity starts again, the viewed pictures
 *               will be updated. It will check for new images from Flickr API for each 10 seconds,
 *               and will be destroyed when the Main Activity starts again.
 */

package com.example.flickrviewer;

import static com.example.flickrviewer.MainActivity.getBitmapFromURL;
import static com.example.flickrviewer.MainActivity.getJsonFromServer;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageFetcherService extends IntentService {
    private Bitmap image0;
    private Bitmap image1;

    private JSONObject info0;
    private JSONObject info1;

    private Map<String, Object> message;

    private boolean running;
    private String url;

    Handler handler;

    /**
     * The constructor for the ImageFetcherService class. It will call the
     * super() constructor.
     */
    public ImageFetcherService() {
        super("ImageFetcherService");
    }

    /**
     * This method will be called when the service is created. It creates a handler
     * for interactions with the main activity, set the URL of the Flickr API, and
     * make a map for message.
     */
    @Override
    public void onCreate() {
        handler = new Handler();
        running = true;
        url =   "https://www.flickr.com/services/rest/?method=flickr.photos.getRecent" +
                "&api_key=c68268937007bf1cc8ac4be2d67276b0" +
                "&extras=original_format,date_upload,url_c,tags&per_page=2&page=1" +
                "&format=json&nojsoncallback=1";
        message = new HashMap<String, Object>();
        super.onCreate();
    }

    /**
     * This method is used to handle intent. It will send the message to update pictures of the
     * main activity after each 10 seconds of its life time.
     * @param intent    The intent that starts the service.
     */
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null) {return;}

        Messenger messenger = (Messenger) bundle.get("MESSENGER");

        while (running) {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {e.printStackTrace();}
            updatePictures(url);
            Message msg = Message.obtain();
            msg.obj = message;
            try {
                messenger.send(msg);
            } catch (RemoteException e) {e.printStackTrace();}
        }
    }

    /**
     * This method is called when the service is destroyed. It puts running to false.
     */
    @Override
    public void onDestroy() {
        running = false;
        System.out.println("Service Destroyed");
    }

    /**
     * This method is used to get the pictures and their metadata from the Flickr API.
     * If running is false, the message key will have the value of false, and thus
     * the main activity will not update the picture.
     * @param   src The Flickr API
     */
    protected void updatePictures(String src) {
        if (running == false) {
            message.put("update", false);
            return;
        }

        String jsonString = null;
        try {
            jsonString = getJsonFromServer(src);
        } catch (IOException e) {e.printStackTrace();}

        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
        } catch (JSONException e) {e.printStackTrace(); }

        getInfo(json);
        updateMessageSent();
    }

    /**
     * This method will get the metadata of the files and the images from the Flickr API
     * and sets the fields of this object appropriately
     * @param json  The JSON object containing the information of the images.
     */
    private void getInfo(JSONObject json) {
        JSONArray urls = null;
        try {
            JSONArray photos = json.getJSONObject("photos").getJSONArray("photo");
            info0 = photos.getJSONObject(0);
            info1 = photos.getJSONObject(1);
            try {
                String url0 = info0.getString("url_c");
                image0 = getBitmapFromURL(url0);
            } catch (JSONException e) {
                e.printStackTrace(); image0 = null;
            }
            try {
                String url1 = info1.getString("url_c");
                image1 = getBitmapFromURL(url1);
            } catch (JSONException e) {
                e.printStackTrace(); image1 = null;
            }
        } catch (JSONException e) {e.printStackTrace();}
    }

    /**
     * This method will update the message sent to the main activity, if the
     * images should be updated.
     */
    private void updateMessageSent() {
        message.put("update", true);
        message.put("image0", image0);
        message.put("image1", image1);
        message.put("info0", info0);
        message.put("info1", info1);
    }
}