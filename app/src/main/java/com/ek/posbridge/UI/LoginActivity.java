package com.ek.posbridge.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ek.posbridge.API.Retrofit.GetServerFromTeamName.GetURLFromTeamResponse;
import com.ek.posbridge.API.Retrofit.SellerCloudAPIClient;
import com.ek.posbridge.API.Webservices.Authenticate;
import com.ek.posbridge.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText teamNameEdittext;
    public static String teamName;
    EditText userNameEditText;
    public static String userName;
    EditText passwordEditText;
    public static String password;
    TextView forgotPasswordText;
    Button loginButton;
    SellerCloudAPIClient apiClient;
    public static String myURL;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private CheckBox saveLoginCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.apiClient = new SellerCloudAPIClient();

        teamNameEdittext = findViewById(R.id.teamNameEdittext);
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            teamNameEdittext.setText(loginPreferences.getString("teamname", ""));
            userNameEditText.setText(loginPreferences.getString("username", ""));
            passwordEditText.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);

        }


        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (teamNameEdittext.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext() , "Please use a TeamName", Toast.LENGTH_SHORT).show();
                }if (userNameEditText.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext() , "Please use a UserName", Toast.LENGTH_SHORT).show();
                }if (passwordEditText.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext() , "Please use a Password", Toast.LENGTH_SHORT).show();
                }

                else {

                    userName = userNameEditText.getText().toString();
                    password = passwordEditText.getText().toString();
                    teamName = teamNameEdittext.getText().toString();

                    Log.i("username on login = ", userName);
                    Log.i("password on login = ", password);
                    Log.i("teamName on login = ", teamName);


                    if (saveLoginCheckBox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("teamName", teamName);
                        loginPrefsEditor.putString("username", userName);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.apply();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                    //retrofit call to get URL from teamname

                    Call<GetURLFromTeamResponse> call = apiClient.getURLFromTeamCall(teamName);
                    call.enqueue(new Callback<GetURLFromTeamResponse>() {
                        @Override
                        public void onResponse(Call<GetURLFromTeamResponse> call, Response<GetURLFromTeamResponse> response) {
                            Log.d("TAG", response.code() + "");
                            String displayResponse = "";

                            GetURLFromTeamResponse resp = response.body();
                            assert resp != null;

                            GetURLFromTeamResponse url = resp;
                            String iD = url.getID();
                            String alphaServerUrl = url.getAlphaServerUrl();
                            String deltaServerUrl = url.getDeltaServerUrl();
                            String webServiceUrl = url.getWebServiceUrl();

                            displayResponse += "\n" + iD + " - id\n" +
                                    alphaServerUrl + " - Alpha URL\n" +
                                    deltaServerUrl + " - Delta URL\n" +
                                    webServiceUrl + " - WebService URL\n";

                            Log.i("RESPONSE", displayResponse);

                            myURL = webServiceUrl;
                            Log.i("webserviceurl - myURL = ", myURL);

                            //START AUTHENTICATION OF USER/PASS
                            Authenticate authenticate = new Authenticate();
                            authenticate.execute(userName, password);

                        }

                        @Override
                        public void onFailure(Call<GetURLFromTeamResponse> call, Throwable t) {

                            t.printStackTrace();
                            call.cancel();
                            Toast.makeText(getApplicationContext() , "Failed in Getting URL from TeamName", Toast.LENGTH_SHORT).show();

                        }
                    });

                    Intent intent = new Intent( LoginActivity.this, HomeActivity.class );
                    startActivity( intent );


                }

            }
        });



    }


}
