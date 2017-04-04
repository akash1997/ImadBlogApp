package examples.imad.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AKASH on 01-04-2017.
 */

public class MessageResponse {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }
}
