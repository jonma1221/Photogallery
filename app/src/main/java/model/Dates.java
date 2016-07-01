package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pixuredlinux3 on 6/30/16.
 */
public class Dates implements Parcelable{
    private String posted;
    private String taken;
    private Integer takengranularity;
    private String takenunknown;
    private String lastupdate;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The posted
     */
    public String getPosted() {
        return posted;
    }

    /**
     *
     * @param posted
     * The posted
     */
    public void setPosted(String posted) {
        this.posted = posted;
    }

    /**
     *
     * @return
     * The taken
     */
    public String getTaken() {
        return taken;
    }

    /**
     *
     * @param taken
     * The taken
     */
    public void setTaken(String taken) {
        this.taken = taken;
    }

    /**
     *
     * @return
     * The takengranularity
     */
    public Integer getTakengranularity() {
        return takengranularity;
    }

    /**
     *
     * @param takengranularity
     * The takengranularity
     */
    public void setTakengranularity(Integer takengranularity) {
        this.takengranularity = takengranularity;
    }

    /**
     *
     * @return
     * The takenunknown
     */
    public String getTakenunknown() {
        return takenunknown;
    }

    /**
     *
     * @param takenunknown
     * The takenunknown
     */
    public void setTakenunknown(String takenunknown) {
        this.takenunknown = takenunknown;
    }

    /**
     *
     * @return
     * The lastupdate
     */
    public String getLastupdate() {
        return lastupdate;
    }

    /**
     *
     * @param lastupdate
     * The lastupdate
     */
    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Dates(Parcel in){
        taken = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taken);
    }

    public static final Creator<Dates> CREATOR = new Creator<Dates>() {
        @Override
        public Dates createFromParcel(Parcel in) {
            return new Dates(in);
        }

        @Override
        public Dates[] newArray(int size) {
            return new Dates[size];
        }
    };
}
