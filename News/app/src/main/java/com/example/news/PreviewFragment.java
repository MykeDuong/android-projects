package com.example.news;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Minh Duong
 * COURSE: CSC 317
 * @description: This class represents the fragment of the app responsible for previewing
 *               and the news. It will show the source of the news, the preview content and a
 *               button that prompts the user to read the whole article, which it will
 *               transfer to another fragment to view the website.
 */
public class PreviewFragment extends Fragment {
    public NewsClass news;

    /**
     * This method is called when the View of the fragment is created. It will set the text
     * of the header (source) and the body text (preview content) approriately with the
     * NewsClass object passed as its parameter in the Bundle.
     * @param inflater  The Layout Inflater of the Fragment
     * @param container The ViewGroup containing the fragment view
     * @param savedInstanceState    The savedInstance of the fragment.
     * @return  The View of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_preview, container, false);

        Bundle args = getArguments();
        news = args.getParcelable("news");

        TextView header = rootView.findViewById(R.id.preview_header);
        TextView content = rootView.findViewById(R.id.preview_content);

        header.setText(news.getSource());
        content.setText(news.getPreview());

        Button read = rootView.findViewById(R.id.read_more_btn);
        read.setOnClickListener(new View.OnClickListener() {
            /**
             * This method handles the click event of the user. It will start
             * the WebViewFragment to show the website, using the URL from the NewsClass
             * Object.
             * @param v The View clicked.
             */
            @Override
            public void onClick(View v) {
                Fragment webViewFragment = new WebViewFragment();
                Bundle args = new Bundle();

                String url = news.getUrl();
                args.putString("url", url);

                webViewFragment.setArguments(args);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main_layout_container, webViewFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return rootView;
    }
}