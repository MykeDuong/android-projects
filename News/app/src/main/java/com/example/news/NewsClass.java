package com.example.news;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author Minh Duong
 * COURSE: CSC 317
 * @description: This class represents a piece of news, or an article, taken from the News API.
 *               It implements the Parcelable interface to be passable between fragments (by
 *               being put into Bundles). It contains the source name, the author, the preview
 *               content and the url of the articles, with accessor and setter methods for
 *               getting/setting the fields.
 */
public class NewsClass implements Parcelable {
    private String source;
    private String author;
    private String preview;
    private String url;

    /**
     * The constructor for the class NewsClass. It takes the String arguments and set the
     * fields of the instance accordingly.
     * @param source    The source of the article
     * @param author    The author of the article
     * @param preview   The preview content of the article
     * @param url       The url of the article
     */
    protected NewsClass(String source, String author, String preview, String url) {
        this.source = source;
        this.author = author;
        this.preview = preview;
        this.url = url;
    }

    /**
     * The constructor of the class NewsClass, taking the data from the Parcel object in and
     * setting the fields accordingly.
     * @param in
     */
    protected NewsClass(Parcel in) {
        source = in.readString();
        author = in.readString();
        preview = in.readString();
        url = in.readString();
    }

    /**
     * The Creator of the class NewsClass, implementing from the Parcelable interface.
     */
    public static final Creator<NewsClass> CREATOR = new Creator<NewsClass>() {
        /**
         * This method creates an instance of NewClass, using the Parcel argument in.
         * @param in
         * @return
         */
        @Override
        public NewsClass createFromParcel(Parcel in) { return new NewsClass(in); }

        /**
         * This method creates an array of NewsClass with the given size.
         * @param size  The size of the array.
         * @return  AN array of NewsClass
         */
        @Override
        public NewsClass[] newArray(int size) { return new NewsClass[size]; }
    };

    /**
     * This method is used to get the source of the instance.
     * @return  The source of the instance
     */
    public String getSource() { return source; }

    /**
     * This method is used to get the author of the instance.
     * @return  The author of the instance
     */
    public String getAuthor() { return author; }

    /**
     * This method is used to get the preview content of the instance.
     * @return  The preview content of the instance
     */
    public String getPreview() { return preview; }

    /**
     * This method is used to get the url of the instance.
     * @return  The url of the instance
     */
    public String getUrl() { return url; }

    /**
     * This method is used to set the source of the instance
     * @param source    The new source of the instance.
     */
    public void setSource(String source) { this.source = source; }

    /**
     * This method is used to set the author of the instance
     * @param author    The new author of the instance.
     */
    public void setAuthor(String author) { this.author = author; }

    /**
     * This method is used to set the preview content of the instance
     * @param preview    The new preview content of the instance.
     */
    public void setPreview(String preview) { this.preview = preview; }

    /**
     * This method is used to set the url of the instance
     * @param url    The new url of the instance.
     */
    public void setUrl(String url) { this.url = url; }

    /**
     * This method implements the Parcelable interface. It returns 0.
     * @return 0
     */
    @Override
    public int describeContents() { return 0; }

    /**
     * This method writes the object to the parcel passed as its argument.
     * @param parcel    The Parcel to be written to.
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(source);
        parcel.writeString(author);
        parcel.writeString(preview);
        parcel.writeString(url);
    }
}
