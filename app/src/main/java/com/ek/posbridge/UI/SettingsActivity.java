package com.ek.posbridge.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ek.posbridge.API.Retrofit.LocationCall.LocationID;
import com.ek.posbridge.R;
import com.ek.posbridge.API.Retrofit.SquareAPIClient;
import com.ek.posbridge.API.Retrofit.LocationCall.Address;
import com.ek.posbridge.API.Retrofit.LocationCall.GetLocationResponse;
import com.ek.posbridge.API.Retrofit.LocationCall.Location;
import com.ek.posbridge.API.Retrofit.TransactionCall.GetTransactionResponse;
import com.ek.posbridge.API.Retrofit.TransactionCall.Refund;
import com.ek.posbridge.API.Retrofit.TransactionCall.Tender;
import com.ek.posbridge.API.Retrofit.TransactionCall.Transaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    private Button getTransactionsButton;
    private Button getLocationButton;
    private Toolbar toolbar;
    SquareAPIClient apiClient;
    public TextView locationIDView;
    public static String locID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.apiClient = new SquareAPIClient();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);

        locationIDView = findViewById(R.id.locationIDView);


        getLocationButton = findViewById(R.id.getLocationButton);
        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Call<GetLocationResponse> call = apiClient.getLocation();

                call.enqueue(new Callback<GetLocationResponse>() {
                    @Override
                    public void onResponse(Call<GetLocationResponse> call, Response<GetLocationResponse> response) {
                        Log.d("TAG",response.code()+"");
                        String displayResponse = "";

                        GetLocationResponse resp = response.body();
                        assert resp != null;

                        Location loc = resp.getLocations().get(0);
                        String id = loc.id;
                        String name = loc.name;
                        Address address = loc.address;
                        String timezone = loc.timezone;
                        String status = loc.status;
                        String createdAt = loc.createdAt;
                        String merchantId = loc.merchantId;
                        String country = loc.country;
                        String languageCode = loc.languageCode;
                        String currency = loc.currency;
                        String phoneNumber = loc.phoneNumber;
                        String businessName = loc.businessName;

                        displayResponse += "\n"+ id + " - id\n" + name + " - name\n" + address + " - address\n" +
                                timezone + " - timezone\n" + status + " - status\n" + createdAt + " - createdAt\n" +
                                merchantId + " - merchantId\n" + country + " - country\n" + languageCode + " - languageCode\n" +
                                currency + " - currency\n" + phoneNumber + " - phoneNumber\n" + businessName + " - businessName\n" ;

                        Log.d("RESPONSE", displayResponse);
                        locationIDView.setText(id);
                        locID = locationIDView.getText().toString();

                        }


                    @Override
                    public void onFailure(Call<GetLocationResponse> call, Throwable t) {

                        t.printStackTrace();
                        call.cancel();
                    }
                });

            }
        });


        getTransactionsButton = findViewById(R.id.getTransactionsButton);
        getTransactionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<GetTransactionResponse> call = apiClient.getTransactions();
                call.enqueue(new Callback<GetTransactionResponse>() {
                    @Override
                    public void onResponse(Call<GetTransactionResponse> call, Response<GetTransactionResponse> response) {
                        Log.d("TAG", response.code() + "");
                        String displayResponse = "";

                        GetTransactionResponse resp = response.body();

                        assert resp != null;
                        Transaction trans = resp.getTransactions().get(0);
                        String id = trans.id;

                        String locationId = trans.locationId;
                        String createdAt = trans.createdAt;
                        Tender tenders = (Tender) trans.tenders;
                        Refund refunds = (Refund) trans.refunds;
                        String referenceId = trans.referenceId;
                        String product = trans.product;

                        displayResponse += "\n" + id + " - id\n" + locationId + " - location_id\n" + createdAt + " - created_at\n" +
                                tenders + " - tenders\n" + refunds + " - refunds\n" + referenceId + " - reference_id\n" + product + " - product\n";

                        Log.d("RESPONSE", displayResponse);

                        LocationID locID = new LocationID();
                        locID.setLocID(locationIDView.getText().toString());

                    }

                    @Override
                    public void onFailure(Call<GetTransactionResponse> call, Throwable t) {
                        Log.d("onFailure-", "call cancelled");
                        t.printStackTrace();

                        call.cancel();

                    }




                });
            }

        });
    }

}
