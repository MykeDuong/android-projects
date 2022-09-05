/**
 * @author: Minh Duong
 * COURSE: CSC 317 - Spring 2022
 * @description: This file contains the Main Activity of the app Flickr Viewer, which serves as
 *               its UI. It will fetch the images from Flickr using Flickr API when the user presses
 *               the back, next or reset button (which means going to the previous page, next page
 *               or return to the first page, respectively). Moreover, it will start a new
 *               activity with webview when the user clicks on the images, and when it is stopped (
 *               but not destroyed), it will start the ImageFetcherService, which will update the
 *               images for each 10 seconds on the background).
 */

package com.example.flickrviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String WEB_URL = "com.example.FlickrViewer.URL";
    private JSONObject info0;
    private JSONObject info1;

    private Bitmap image0;
    private Bitmap image1;

    private boolean update;
    private int page;

    /**
     * This method is called when the Main Activity is created. It will initializes the Views
     * in the activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        page = 1;
        updatePictures();
    }

    /**
     * This method is called when the Main Activity starts. It will stop (destroy) the Image
     * Fetcher Service if it is running, and update the images of the Main Activity if necessary.
     * Also, it will restart the page to 1.
     */
    protected void onStart() {
        page = 1;
        Intent stop = new Intent(this, ImageFetcherService.class);
        stopService(stop);

        if (update == true) {
            System.out.println("update");
            updatePictures();
        }
        super.onStart();
    }

    /**
     * This method is called when the Main Activity stops (not viewed by the user.) It will
     * start the ImageFetcherService, which will update the images in the background each 10
     * seconds. This service will be destroyed when the Main Activity starts again. Also,
     * it will handle the Message (information) provided by the service to update the Main Activity
     * appropriately.
     */
    protected void onStop() {
        super.onStop();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Map<String, Object> obtained = (Map<String, Object>) msg.obj;
                update = (boolean) obtained.get("update");
                if (update == true) {
                    image0 = (Bitmap) obtained.get("image0");
                    image1 = (Bitmap) obtained.get("image1");
                    info0 = (JSONObject) obtained.get("info0");
                    info1 = (JSONObject) obtained.get("info1");
                }
            }
        };
        update = false;
        Intent intent = new Intent(this, ImageFetcherService.class);
        intent.putExtra("MESSENGER", new Messenger(handler));
        startService(intent);
    }

    /**
     * This method is called when the first image is clicked. It will call imageOnClick() method.
     * @param view
     * @throws JSONException
     */
    public void imageOnClick0(View view) throws JSONException {
        imageOnClick(info0);
    }

    /**
     * This method is called when the second image is clicked. It will call imageOnClick() method.
     * @param view
     * @throws JSONException
     */
    public void imageOnClick1(View view) throws JSONException {
        imageOnClick(info1);
    }

    /**
     * This method is called when one of the two images is clicked. It will start a new activity
     * that contains a webview showing the Flickr webpage of the clicked image.
     * @param info  The JSONObject in accordance with the image clicked
     * @throws JSONException
     */
    public void imageOnClick(JSONObject info) throws JSONException {
        String owner = info.getString("owner");
        String id = info.getString("id");
        String url = "https://flickr.com/photos/" + owner + "/" + id;

        Intent intent = new Intent(this, ViewImageWeb.class);
        intent.putExtra(WEB_URL, url);
        startActivity(intent);
    }

    /**
     * This method is called when the next button is clicked. It increments the page and update the
     * pictures.
     * @param view  The next Button
     */
    public void onClickNext(View view) {
        page ++;
        updatePictures();
    }

    /**
     * This method is called when the back button is clicked. It decrements the page and update the
     * pictures.
     * @param view  The back Button
     */
    public void onClickPrevious(View view) {
        if (page == 1) {
            System.out.println("Front page already");
            return;
        }
        page --;
        updatePictures();
    }

    /**
     * This method is called when the reset button is clicked. It sets the page to 1 and update the
     * pictures.
     * @param view  The Reset Button
     */
    public void onClickReset(View view) {
        page = 1;
        updatePictures();
    }

    /**
     * This method is used to get a Bitmap from the URL String
     * @param src   The URL String
     * @return  The Bitmap from the URL.
     */
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            System.out.println("start to conenect");
            connection.connect();
            System.out.println("Connected!");
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is used to get JSON String from the URL String.
     * @param src   The URL String
     * @return  The JSON in String format
     * @throws IOException
     */
    public static String getJsonFromServer(String src) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(src);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }
            return buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {connection.disconnect();}
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {e.printStackTrace();}
        }
        return null;
    }

    /**
     * This method will create an instance of the ImageAsyncTask to update the images
     * with its metadata when called.
     */
    private void updatePictures() {
        ImageAsyncTask iat = new ImageAsyncTask();
        String url = "https://www.flickr.com/services/rest/?method=flickr.photos.getRecent" +
                "&api_key=c68268937007bf1cc8ac4be2d67276b0" +
                "&extras=original_format,date_upload,url_c,tags&per_page=2&page=" +
                page +
                "&format=json&nojsoncallback=1";
        iat.execute(url);
    }

    /**
     * This method is used to convert from UNIX timestamp format to the String printed to the
     * screen
     * @param unixDate  The UNIX timestamps
     * @return  The String of the date in yyyy-MM-dd HH:mm:ss form.
     */
    private String convertDate(String unixDate) {
        long dv = Long.valueOf(unixDate) * 1000;
        Date df = new java.util.Date(dv);
        String vv = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(df);
        return vv;
    }

    /**
     * This method is used to update the dates of the images.
     * @param textTag   The TextView of the date
     * @param info      The JSON containing the date.
     */
    private void updateDate(TextView textDate, JSONObject info) {
        String date = "";
        try {
            date = info.getString("dateupload");
        } catch (JSONException e) {e.printStackTrace();}
        textDate.setText(convertDate(date));
    }

    /**
     * This method is used to update the original format of the images.
     * @param textTag   The TextView of the format
     * @param info      The JSON containing the format.
     */
    private void updateFormat(TextView textFile, JSONObject info) {
        try {
            String file = info.getString("originalformat");
            textFile.setText(file);
        } catch (JSONException e) {
            e.printStackTrace();
            textFile.setText("Unspe-cified");
        }
    }

    /**
     * This method is used to update the tags of the images.
     * @param textTag   The TextView of the tags
     * @param info      The JSON containing the tags.
     */
    private void updateTag(TextView textTag, JSONObject info) {
        try {
            String file = info.getString("tags");
            textTag.setText(file);
        } catch (JSONException e) {
            e.printStackTrace();
            textTag.setText("None");
        }
    }

    /**
     * This method is used to update the ImageViews with appropriate metadata on the TextViews.
     * It will be called when the images with the new metadata is ready.
     */
    private void updateView() {
        ImageView firstImage = findViewById(R.id.image0);
        if (image0 == null)
            firstImage.setImageDrawable(getResources().getDrawable(R.drawable.flickr));
        else
            firstImage.setImageBitmap(image0);

        TextView textDate0 = findViewById(R.id.textDate0);
        updateDate(textDate0, info0);

        TextView textFile0 = findViewById(R.id.textFile0);
        updateFormat(textFile0, info0);

        TextView textTag0 = findViewById(R.id.textTag0);
        updateTag(textTag0, info0);

        ImageView secondImage = findViewById(R.id.image1);
        if (image1 == null)
            secondImage.setImageDrawable(getResources().getDrawable(R.drawable.flickr));
        else
            secondImage.setImageBitmap(image1);

        TextView textDate1 = findViewById(R.id.textDate1);
        updateDate(textDate1, info1);

        TextView textFile1 = findViewById(R.id.textFile1);
        updateFormat(textFile1, info1);

        TextView textTag1 = findViewById(R.id.textTag1);
        updateTag(textTag1, info1);
    }

    /**
     * @author: Minh Duong
     * @description: This class is a AsyncTask that helps downloads the images with their
     * metadata and update the ImageViews of the class appropriately.
     */
    private class ImageAsyncTask extends AsyncTask<String, Integer, Integer> {
        /**
         * This method declares the actions that the AsyncTask will do in the background
         * @param strs  strs[0] is the URL of the Flickr API
         * @return  1
         */
        protected Integer doInBackground(String... strs) {
            String jsonString = null;
            try {
                jsonString = getJsonFromServer(strs[0]);
            } catch (IOException e) {e.printStackTrace();}

            JSONObject json = null;
            try {
                json = new JSONObject(jsonString);
            } catch (JSONException e) {e.printStackTrace();}

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
            return 1;
        }

        /**
         * This method will be called when doInBackground() is done. It will call a method
         * of the Main Activity to update the Views accordingly.
         * @param i
         */
        protected void onPostExecute(Integer i) {
            updateView();
        }
    }

}