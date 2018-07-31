package com.ek.posbridge.API.Retrofit.TransactionCall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card {

    @SerializedName("card_brand")
    @Expose
    private String cardBrand;
    @SerializedName("last_4")
    @Expose
    private String last4;

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

}
