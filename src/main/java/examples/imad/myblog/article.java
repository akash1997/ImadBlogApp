package examples.imad.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AKASH on 03-04-2017.
 */

public class article {

    @SerializedName("id")
    Integer id;

    @SerializedName("heading")
    String heading;

    @SerializedName("title")
    String title;

    @SerializedName("date")
    String date;

    @SerializedName("content")
    String content;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() { return content; }

    public String getHeading() { return heading; }
}
