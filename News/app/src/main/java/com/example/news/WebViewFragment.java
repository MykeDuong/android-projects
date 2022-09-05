package com.example.news;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * @author Minh Duong
 * COURSE: CSC 317
 * @description: This class represents the fragment of the app responsible for viewing
 *               the website. It will take the URL String from the args Bundle, and load
 *               the WebView with it.
 */
public class WebViewFragment extends Fragment {
    private String url;

    /**
     * This method is called when the View of the fragment is created. It will take the URL
     * String from the args Bundle, and load the WebView with it.
     * @param inflater  The Layout Inflater of the Fragment
     * @param container The ViewGroup containing the fragment view
     * @param savedInstanceState    The savedInstance of the fragment.
     * @return  The View of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_web_view, container, false);

        Bundle args = getArguments();
        url = args.getString("url");

        Context context = getActivity();
        WebView webView = rootView.findViewById(R.id.web_view);
        webView.loadUrl(url);

        return rootView;
    }



}