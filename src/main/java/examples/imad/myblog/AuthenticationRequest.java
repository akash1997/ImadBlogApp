package examples.imad.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AKASH on 01-04-2017.
 */

public class AuthenticationRequest {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    public AuthenticationRequest(String username, String password) {

        this.username = username;
        this.password = password;

    }

}
