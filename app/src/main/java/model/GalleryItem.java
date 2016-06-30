package model;

/**
 * Created by pixuredlinux3 on 6/29/16.
 */
public class GalleryItem {
    private String title;
    private String id;
    private String url_s;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl_s() {
        return url_s;
    }

    public void setUrl_s(String url_s) {
        this.url_s = url_s;
    }

    public String getTitle() {

        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
