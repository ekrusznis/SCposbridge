package com.ek.posbridge.API.Retrofit;

import android.util.Log;

import com.ek.posbridge.API.Retrofit.GetServerFromTeamName.GetURLFromTeamResponse;
import com.ek.posbridge.UI.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ek.posbridge.UI.LoginActivity.teamName;

public class SellerCloudAPIClient {

    private final int DEFAULT_TIMEOUT_CONNECT = 30 * 1000;
    private final int DEFAULT_TIMEOUT_READ = 45 * 1000;
    private final int DEFAULT_TIMEOUT_WRITE = 45 * 1000;
    private SellerCloudAPIInterface api;


    public SellerCloudAPIClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder();
//                        .header("Authorization", "Bearer " + SQUARE_ACCESS_TOKEN)
//                        .header("Content-Type", "application/x-www-form-urlencoded");
//                       .header("Content-Type", "application/json");
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        })
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT_READ, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_WRITE, TimeUnit.MILLISECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .create();

        //https://api.skustack.com/api/server-by-team?team=teatime

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.skustack.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        this.api = retrofit.create(SellerCloudAPIInterface.class);

    }

    public Call<GetURLFromTeamResponse> getURLFromTeamCall(String teamName){
        return api.getServerURL(teamName);
    }

}
