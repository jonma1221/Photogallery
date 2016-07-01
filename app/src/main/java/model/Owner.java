package model;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

import java.util.HashMap;

public class Owner implements Parcelable{

    private String nsid;
    private String username;
    private String realname;
    private String location;
    private String iconserver;
    private Integer iconfarm;
    private String pathAlias;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The nsid
     */
    public String getNsid() {
        return nsid;
    }

    /**
     *
     * @param nsid
     * The nsid
     */
    public void setNsid(String nsid) {
        this.nsid = nsid;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     *
     * @param realname
     * The realname
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     *
     * @return
     * The location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The iconserver
     */
    public String getIconserver() {
        return iconserver;
    }

    /**
     *
     * @param iconserver
     * The iconserver
     */
    public void setIconserver(String iconserver) {
        this.iconserver = iconserver;
    }

    /**
     *
     * @return
     * The iconfarm
     */
    public Integer getIconfarm() {
        return iconfarm;
    }

    /**
     *
     * @param iconfarm
     * The iconfarm
     */
    public void setIconfarm(Integer iconfarm) {
        this.iconfarm = iconfarm;
    }

    /**
     *
     * @return
     * The pathAlias
     */
    public String getPathAlias() {
        return pathAlias;
    }

    /**
     *
     * @param pathAlias
     * The path_alias
     */
    public void setPathAlias(String pathAlias) {
        this.pathAlias = pathAlias;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Owner(Parcel in){
        nsid = in.readString();
        username = in.readString();
        location = in.readString();
        iconserver = in.readString();
        iconfarm = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nsid);
        dest.writeString(username);
        //dest.writeString(realname);
        dest.writeString(location);
        dest.writeString(iconserver);
        dest.writeInt(iconfarm);
        //dest.writeString(pathAlias);
    }

    public static final Creator<Owner> CREATOR = new Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel in) {
            return new Owner(in);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };
}