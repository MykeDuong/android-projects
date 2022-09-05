package com.example.sketcher;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Minh Duong
 * COURSE: CSC 317
 * @description This Fragment shows the Drawing component of the Application. It contains buttons
 *              to change the behavior of the drawing, clear the Canvas, share the image, and a
 *              DrawingView to draw.
 */
public class DrawingFragment extends Fragment implements View.OnClickListener{
    DrawingView dv = null;

    /**
     * This method will be called when the View is created. It will add a DrawingView to the
     * View, and set onClick to Buttons on the View.
     * @param inflater  The LayoutInflater of the class.
     * @param container The View containing the View of this Fragment.
     * @param savedInstanceState    The saved instance of the class.
     * @return  The View of the Fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drawing, container, false);

        dv = new DrawingView(getActivity(), null);
        LinearLayout draw = rootView.findViewById(R.id.draw);
        draw.addView(dv);
        setButtonsOnClick(rootView);

        return rootView;
    }

    /**
     * This method handles clicks on the Buttons of the Fragment.
     * @param v The View clicked..
     */
    @Override
    public void onClick(View v) { //check for what button is pressed
        switch (v.getId()) {
            case R.id.red_button:
                dv.changeColor(getResources().getColor(R.color.red));
                break;
            case R.id.green_button:
                dv.changeColor(getResources().getColor(R.color.green));
                break;
            case R.id.blue_button:
                dv.changeColor(getResources().getColor(R.color.blue));
                break;
            case R.id.black_button:
                dv.changeColor(getResources().getColor(R.color.black));
                break;
            case R.id.small_button:
                dv.changeWidthValue(5.0f);
                break;
            case R.id.medium_button:
                dv.changeWidthValue(15.0f);
                break;
            case R.id.large_button:
                dv.changeWidthValue(25.0f);
                break;
            case R.id.clear_button:
                dv.startNew();
                break;
            case R.id.share_button:
                shareClicked();
                break;
            default:
                break;
        }
    }

    /**
     * This function is called when the user presses the share button. It will make a bitmap,
     * which is then written to a file, and it will create a ContactFragment to show available
     * contacts for the user to choose and send the image.
     */
    public void shareClicked() {
        Bitmap bitmap = dv.getBitmap();

        File file = null;
        try {
            file = createImageFile();
            OutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            String filename = file.getName();

            Fragment previewFragment = new ContactFragment();
            Bundle args = new Bundle();
            Uri uri = FileProvider.getUriForFile(getActivity(),
                                         BuildConfig.APPLICATION_ID + ".provider",
                                                 file);
            args.putParcelable("fileURI", uri);
            previewFragment.setArguments(args);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.main_layout_container, previewFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        catch (Exception e) { e.printStackTrace(); }
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
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    /**
     * This method is used to set all necessary buttons to be on clicked.
     * @param rootView  The View of the Fragment.
     */
    private void setButtonsOnClick(View rootView) {
        setButtonClick((Button) rootView.findViewById(R.id.red_button));
        setButtonClick((Button) rootView.findViewById(R.id.green_button));
        setButtonClick((Button) rootView.findViewById(R.id.blue_button));
        setButtonClick((Button) rootView.findViewById(R.id.black_button));
        setButtonClick((Button) rootView.findViewById(R.id.small_button));
        setButtonClick((Button) rootView.findViewById(R.id.medium_button));
        setButtonClick((Button) rootView.findViewById(R.id.large_button));
        setButtonClick((Button) rootView.findViewById(R.id.clear_button));
        setButtonClick((Button) rootView.findViewById(R.id.share_button));
    }

    /**
     * This method is used to set the specified button to be listened to click events.
     * @param b The button to be clicked.
     */
    private void setButtonClick(Button b) {
        b.setOnClickListener(this);
    }
}