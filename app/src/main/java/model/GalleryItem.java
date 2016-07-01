package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pixuredlinux3 on 6/29/16.
 */
public class GalleryItem implements Parcelable{
    private String id;
    private String owner;
    private String title;
    private String url_s;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getUrl_s() {
        return url_s;
    }
    public void setUrl_s(String url_s) {
        this.url_s = url_s;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public GalleryItem(Parcel in){
        id = in.readString();
        owner = in.readString();
        title = in.readString();
        url_s = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(owner);
        dest.writeString(title);
        dest.writeString(url_s);
    }

    public static final Creator<GalleryItem> CREATOR = new Creator<GalleryItem>() {
        @Override
        public GalleryItem createFromParcel(Parcel in) {
            return new GalleryItem(in);
        }

        @Override
        public GalleryItem[] newArray(int size) {
            return new GalleryItem[size];
        }
    };
}
