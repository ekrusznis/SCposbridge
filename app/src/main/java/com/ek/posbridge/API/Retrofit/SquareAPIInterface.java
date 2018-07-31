package com.ek.posbridge.API.Retrofit;

import com.ek.posbridge.API.Retrofit.LocationCall.GetLocationResponse;
import com.ek.posbridge.API.Retrofit.TransactionCall.GetTransactionResponse;
import com.ek.posbridge.UI.SettingsActivity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SquareAPIInterface {

    String locID = SettingsActivity.locID;

    @GET("/v2/locations")
    Call<GetLocationResponse> getLocations();

    //LOCID IS NOT BEING SEEN FROM SETTINGSACTIVITY
    @GET("/v2/locations/{locID}/transactions")
    Call<GetTransactionResponse> getTransactions(@Path("locID") String locID);

}
