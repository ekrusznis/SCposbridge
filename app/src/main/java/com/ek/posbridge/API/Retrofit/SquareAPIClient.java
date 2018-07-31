package com.ek.posbridge.API.Retrofit;

import com.ek.posbridge.API.Retrofit.LocationCall.GetLocationResponse;
import com.ek.posbridge.API.Retrofit.TransactionCall.GetTransactionResponse;
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

public class SquareAPIClient {

    private String locID;
    private final int DEFAULT_TIMEOUT_CONNECT = 30 * 1000;
    private final int DEFAULT_TIMEOUT_READ = 45 * 1000;
    private final int DEFAULT_TIMEOUT_WRITE = 45 * 1000;
    private SquareAPIInterface api;
    private String SQUARE_ACCESS_TOKEN = "sq0atp-E96f4zyqIPw0FTmcxHOErQ";

    public SquareAPIClient(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + SQUARE_ACCESS_TOKEN)
//                        .header("Content-Type", "application/x-www-form-urlencoded");
                    .header("Content-Type", "application/json");
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

    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://connect.squareup.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build();

        this.api = retrofit.create(SquareAPIInterface.class);

    }

    public Call<GetLocationResponse> getLocation(){
        return api.getLocations();
    }

    public Call<GetTransactionResponse> getTransactions(){return api.getTransactions(locID);}

}