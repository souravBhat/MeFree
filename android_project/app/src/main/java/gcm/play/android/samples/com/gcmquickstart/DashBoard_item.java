package gcm.play.android.samples.com.gcmquickstart;

/**
 * Created by ASUS on 23/11/2015.
 */
public class DashBoard_item {
    int imageId;
    String title;

    public DashBoard_item(String name, int icon)
    {
        imageId=icon;
        title=name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
