/**
 * @author: Minh Duong
 * @description: This file contains the MainActivity object
 *               of the app Four Temperaments Quiz, which also
 *               serves as its UI. It has a collage where user
 *               can click on one of the 4 partial image and
 *               they will be guided to the Camera activity.
 *               The app will capture the picture taken and put
 *               it in the collage. Additionally, this activity
 *               also provides a button which user can click to
 *               share their collage.
 */

package com.example.collagemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView currentView;
    private File photoFile;
    private LinearLayout collage;

    /**
     * This function is called when the class is created.
     * Aside from using save instance state and set content
     * view as default, it also initializes the collage object,
     * which is the linear layout that encapsulates the 4 images.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        collage = findViewById(R.id.collage);
    }

    /**
     * This function is called when one of the four partial images is pressed.
     * It leads the user to the camera activity (default, of the device), with the
     * photoFile and photoURI being the file and URI of the to-be-captured picture.
     * @param view
     */
    public void imagePressed(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        currentView = (ImageView) view;
        // Create the File where the photo should go
        photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // Continue if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * This function is called after the camera activity is done and the picture
     * is taken. It will crop the picture (the two side if horizontal, the top and bottom
     * if vertical) to make the picture become a square, and set it in place of the current
     * partial image of the collage.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

            // Crop the bitmap to be squared, depending on if it is horizontal or vertical
            Bitmap finalBmp;
            if (bitmap.getWidth() >= bitmap.getHeight()){
                finalBmp = Bitmap.createBitmap(
                        bitmap,
                        bitmap.getWidth()/2 - bitmap.getHeight()/2,
                        0,
                        bitmap.getHeight(),
                        bitmap.getHeight()
                );
            } else {
                finalBmp = Bitmap.createBitmap(
                        bitmap,
                        0,
                        bitmap.getHeight()/2 - bitmap.getWidth()/2,
                        bitmap.getWidth(),
                        bitmap.getWidth()
                );
            }
            currentView.setImageBitmap(finalBmp);
        }
    }

    /**
     * This function is called when the user presses the share button. It will make a bitmap,
     * which is then written to a file, and it will implicit intent for the user to choose
     * the sending app and eventually send the collage.
     * @param view
     * @throws FileNotFoundException
     */
    public void shareClicked(View view) throws FileNotFoundException {
        Bitmap bitmap = Bitmap.createBitmap(
                collage.getWidth(), collage.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        collage.draw(canvas);

        File file = null;
        try {
            file = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            OutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            Uri uri = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    file);
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            // The uri should be the uri of the screenshot image
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/png");
            startActivity(intent);
        }
    }

    /**
     * This function is used to create a image file in the form of a JPEG. It also
     * uses the current date to create a unique file name each time.
     * @return image
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
}