package com.ek.posbridge.API.Retrofit.TransactionCall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardDetails {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("card")
    @Expose
    private Card card;
    @SerializedName("entry_method")
    @Expose
    private String entryMethod;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getEntryMethod() {
        return entryMethod;
    }

    public void setEntryMethod(String entryMethod) {
        this.entryMethod = entryMethod;
    }

}
