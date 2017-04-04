package examples.imad.myblog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {

    EditText username, password;

    Button sign_in, register;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        username = (EditText) findViewById(R.id.etUsername);

        password = (EditText) findViewById(R.id.etPassword);

        sign_in = (Button) findViewById(R.id.btn_sign_in);

        register = (Button) findViewById(R.id.btn_register);

        sign_in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(isFormValid()) {

                    //perform sign in
                    performSignIn();

                }

            }

        });

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(isFormValid()) {

                    //perform registration
                    performRegistration();

                }

            }

        });

        //initialising progress dialog
        progressDialog = new ProgressDialog(this);

        progressDialog.setIndeterminate(true);

        progressDialog.setMessage("Please wait");

    }

    private Boolean isFormValid() {

        if(username.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Username cannot be left empty", Toast.LENGTH_LONG).show();
            return false;

        }

        if(password.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Password cannot be left empty", Toast.LENGTH_LONG).show();
            return false;

        }

        return true;

    }

    private void performSignIn() {

        showProgressDialog(true);

        ApiManager.getApiInterface().login(new AuthenticationRequest(username.getText().toString().trim(), password.getText().toString().trim()))
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                        showProgressDialog(false);

                        if(response.isSuccessful()) {

                            //showAlert("Welcome", response.body().getMessage());
                            navigateToArticleListActivity();

                        } else {

                            try {

                                String errorMessage = response.errorBody().string();

                                try {

                                    ErrorResponse errorResponse = new Gson().fromJson(errorMessage, ErrorResponse.class);
                                    showAlert("SignIn Failed", errorResponse.getError());

                                } catch (JsonSyntaxException jsonSyntaxException) {

                                    jsonSyntaxException.printStackTrace();
                                    showAlert("SignIn Failed", "Something went wrong1");

                                }

                            } catch (IOException e) {

                                e.printStackTrace();
                                showAlert("SignIn Failed", "Something went wrong2");

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {

                        showProgressDialog(false);
                        showAlert("SignIn Failed", "Something went wrong3");

                    }
                });

    }

    private void performRegistration() {

        showProgressDialog(true);

        ApiManager.getApiInterface().registration(new AuthenticationRequest(username.getText().toString().trim(), password.getText().toString().trim()))
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                        showProgressDialog(false);

                        if(response.isSuccessful()) {

                            showAlert("Welcome", response.body().getMessage());

                        } else {

                            try {

                                String errorMessage = response.errorBody().string();

                                try {

                                    ErrorResponse errorResponse = new Gson().fromJson(errorMessage, ErrorResponse.class);
                                    showAlert("Registration Failed", errorResponse.getError());

                                } catch (JsonSyntaxException jsonSyntaxException) {

                                    jsonSyntaxException.printStackTrace();
                                    showAlert("Registraton Failed", "Something went wrong1");

                                }

                            } catch (IOException e) {

                                e.printStackTrace();
                                showAlert("Registration Failed", "Something went wrong2");

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {

                        showProgressDialog(false);
                        //showAlert("SignIn Failed", "Something went wrong3");

                        navigateToArticleListActivity();

                    }
                });

    }

    private void showProgressDialog(Boolean shouldShould) {

        if(shouldShould) {

            progressDialog.show();

        } else {

            progressDialog.dismiss();

        }

    }

    private void showAlert(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        builder.show();

    }

    class SignInTask extends AsyncTask<String, Void, Boolean> {

        String mockUsername = "test";
        String mockPassword = "password";

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            showProgressDialog(true);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            super.onPostExecute(aBoolean);
            showProgressDialog(false);

            if(aBoolean) {

                showAlert("Welcome", "You have successfully signed in!");

            } else {

                showAlert("Failed", "Username/Password is incorrect");

            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            String username = strings[0];
            String password = strings[1];

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return username.contentEquals(mockUsername) && password.contentEquals(mockPassword);
        }

    }

    private void navigateToArticleListActivity() {

        Intent intent = new Intent(this, ArticleListActivity.class);

        startActivity(intent);

    }

}
