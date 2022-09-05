package com.example.news;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Minh Duong
 * COURSE: CSC 317
 * @description: This class represents the fragment of the app responsible for searching
 *               and showing the news. It will utilize an AsyncTask to search for the information
 *               using the News API. When clicking on a item of the ListView containing the
 *               results, this fragment will start another fragment containing the preview of such
 *               piece of news. Also, when the user navigates back to this fragment, it will
 *               automatically search the news again with the prior keyword.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    private static final String API_KEY = "4c08b89cc1a74bbd8f554d71684f5916";
    private List<NewsClass> newsList;
    private boolean searched = false;
    private String searchTerm;

    /**
     * This method will be called when the Views are created. It will search for the news again
     * when the user returns to it (the fragment still keeps the prior search term.)
     * @param inflater  The layout inflater of the fragment
     * @param container The ViewGroup containing the fragment
     * @param savedInstanceState    The saved state of the fragment.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        Button search = (Button) rootView.findViewById(R.id.search_button);
        search.setOnClickListener(this);

        if (searched == true) {
            search();
        }

        ListView lv = rootView.findViewById(R.id.news_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment previewFragment = new PreviewFragment();
                Bundle args = new Bundle();
                NewsClass news = (NewsClass) parent.getAdapter().getItem(position);
                args.putParcelable("news", news);

                previewFragment.setArguments(args);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main_layout_container, previewFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return rootView;
    }

    /**
     * This method handles the click event from the user when they click the search button.
     * It will take the search term from the EditText and search it by calling the
     * search() method.
     * @param view  The clicked button
     */
    @Override
    public void onClick(View view) {
        EditText termView = (EditText) getView().findViewById(R.id.search_term);
        searchTerm = termView.getText().toString();
        if (searchTerm.equals("")) { return; }

        search();
    }

    /**
     * This method handles the search function of the fragment. It will create an NewsAsyncTask
     * and execute it, as well as put searched = true for automatic search after the user
     * navigates back to this fragment.
     */
    private void search() {
        searched = true;
        NewsAsyncTask task = new NewsAsyncTask();
        String url = getURL(searchTerm);
        task.execute(url);
    }

    /**
     * This method is used to get JSON String from the URL String.
     * @param src   The URL String
     * @return  The JSON in String format
     * @throws IOException
     */
    private String getJsonFromServer(String src) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(src);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; " +
                    "Intel Mac OS X 10_14_6)AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/85.0.4183.121 Safari/537.36 OPR/71.0.3770.284");
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) { buffer.append(line+"\n"); }
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
     * This method is used to get the URL from the search term by concatenation using
     * the getDate() function to find the date, the API_KEY, and the searched term.
     * @param searchTerm    The String used to search for the news.
     * @return  String  The URL String of the News API.
     */
    private String getURL(String searchTerm) {
        String term = searchTerm.replaceAll("\\s+","-");

        String url = "https://newsapi.org/v2/everything?sortBy=publishedAt&q=" +
                term +
                "&from=" +
                getDate() +
                "&apiKey=" +
                API_KEY;

        return url;
    }

    /**
     * This function is used to get the date of 1 week prior to present.
     * @return  String  The String representation (YYYY_MM_DD) of 1 week prior to present.
     */
    private String getDate() {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        return formatter.format(date);
    }

    /**
     * This function is used to convert a JSONArray of articles, taken from the News API, into
     * a List of NewsClass (an object representing a piece of news).
     * @param articles  The JSONArray containing the articles.
     * @return
     * @throws JSONException
     */
    private List<NewsClass> toNewsList(JSONArray articles) throws JSONException {
        List<NewsClass> newsList = new ArrayList<NewsClass>();
        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);
            String source = article.getJSONObject("source").getString("name");
            String author = article.getString("author");
            String preview = article.getString("content");
            String url = article.getString("url");
            newsList.add(new NewsClass(source, author, preview, url));
        }
        return newsList;
    }

    /**
     * @author Minh Duong
     * @Description This class helps search for news on another thread. When completed, it will
     * set the ListView with the Adapter to fill in the results. It takes a String
     * argument, which is the URL of the News API to fetch information.
     */
    private class NewsAsyncTask extends AsyncTask<String, Integer, Integer> {
        /**
         * This method declares the actions that the AsyncTask will do in the background.
         * It fetches the data from the News API and put it into newsList.
         * @param strs  strs[0] is the URL of the News API
         * @return  1
         */
        protected Integer doInBackground(String... strs) {
            String jsonString = null;
            try { jsonString = getJsonFromServer(strs[0]); }
            catch (IOException e) {e.printStackTrace();}

            JSONObject json = null;
            try { json = new JSONObject(jsonString); }
            catch (JSONException e) {e.printStackTrace();}

            JSONArray articles = null;
            try {
                articles = json.getJSONArray("articles");
                newsList = toNewsList(articles);
            }
            catch (JSONException e) {e.printStackTrace();}
            return 1;
        }

        /**
         * This method will be called when doInBackground() is done. It will set the adapter for
         * the newsListView to show the result from the search.
         * @param i
         */
        protected void onPostExecute(Integer i) {
            CustomNewsAdapter ad = new CustomNewsAdapter(getActivity(), newsList);
            ListView newsListView = (ListView) getView().findViewById(R.id.news_list);
            newsListView.setAdapter(ad);
        }
    }
}