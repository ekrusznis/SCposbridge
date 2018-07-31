package com.ek.posbridge.API.Retrofit.TransactionCall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdditionalRecipient_ {

    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("amount_money")
    @Expose
    private AmountMoney___ amountMoney;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AmountMoney___ getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(AmountMoney___ amountMoney) {
        this.amountMoney = amountMoney;
    }

}
