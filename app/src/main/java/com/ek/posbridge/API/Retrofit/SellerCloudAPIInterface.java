package com.ek.posbridge.API.Retrofit;

import android.util.Log;

import com.ek.posbridge.API.Retrofit.GetServerFromTeamName.GetURLFromTeamResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SellerCloudAPIInterface {


    @GET("/api/server-by-team")
    Call<GetURLFromTeamResponse> getServerURL(@Query("team") String teamName);


}
