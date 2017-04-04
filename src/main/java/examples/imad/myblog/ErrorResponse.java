package examples.imad.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AKASH on 01-04-2017.
 */

public class ErrorResponse {

    @SerializedName("error")
    String error;

    public String getError() {
        return error;
    }
}
