package com.example.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minh Duong
 * COURSE: CSC 317
 * @description This file contains a custom Adapter that extends the BaseAdapter class.
 *              It is used to make a two-row list, with the source and the author
 *              of the News. It adapts the List of NewsClass objects into a ListView,
 *              using the row_layout.xml file as the View of a row.
 */
public class CustomNewsAdapter extends BaseAdapter {

    private List<NewsClass> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    /**
     * The constructor of the class. It will create an instance with the given context
     * and list of NewsClass objects.
     * @param context   The context of the application
     * @param listData  The list containing the News to be shown.
     */
    public CustomNewsAdapter(Context context, List<NewsClass> listData) {
        this.listData = listData;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * This method returns the size of the listData.
     * @return  The size of the news list.
     */
    @Override
    public int getCount() {
        return listData.size();
    }

    /**
     * This method returns item from the listData in the specific index.
     * @return  The Object in the given index.
     */
    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    /**
     * This method returns the ID (here, it is the index) of the Item specified
     * by the index. This method implements the BaseAdapter interface.
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * This method gets the views of the NewsClass object in the specific index.
     * It does so by using the row_layout.xml layout file.
     * @param position  The index of the object to be viewed.
     * @param convertView   The converted View
     * @param parent    The parent ViewGroup
     * @return  The View of the object in the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_layout, null);
            holder = new ViewHolder();

            holder.source = (TextView) convertView.findViewById(R.id.sourceTextView);
            holder.author = (TextView) convertView.findViewById(R.id.authorTextView);

            convertView.setTag(holder);
        } else { holder = (ViewHolder) convertView.getTag(); }

        String source = listData.get(position).getSource();
        String author = "(" + listData.get(position).getAuthor() + ")";
        holder.source.setText(source);
        holder.author.setText(author);

        return convertView;
    }

    /**
     * This class encapsulates (holds) the Views needed to show of the NewsClass object.
     */
    static class ViewHolder {
        TextView source;
        TextView author;
    }
}
