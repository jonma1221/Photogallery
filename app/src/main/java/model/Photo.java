package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pixuredlinux3 on 6/30/16.
 */
public class Photo implements Parcelable{
    private Owner owner;
    private Title title;
    private Dates date;

    public Title getTitle() {
        return title;
    }
    public void setTitle(Title title) {
        this.title = title;
    }
    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }
    public Dates getDate() {
        return date;
    }

    public void setDate(Dates date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Photo(Parcel in){
        owner = in.readParcelable(Owner.class.getClassLoader());
        title = in.readParcelable(Title.class.getClassLoader());
        date = in.readParcelable(Dates.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(owner, flags);
        dest.writeParcelable(title, flags);
        dest.writeParcelable(date,flags);
    }

    public static final Parcelable.Creator<GalleryItem> CREATOR = new Parcelable.Creator<GalleryItem>() {
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
