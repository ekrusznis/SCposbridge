package com.ek.posbridge.API.Retrofit.TransactionCall;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tender {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("amount_money")
    @Expose
    private AmountMoney amountMoney;
    @SerializedName("processing_fee_money")
    @Expose
    private ProcessingFeeMoney processingFeeMoney;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("card_details")
    @Expose
    private CardDetails cardDetails;
    @SerializedName("additional_recipients")
    @Expose
    private List<AdditionalRecipient> additionalRecipients = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AmountMoney getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(AmountMoney amountMoney) {
        this.amountMoney = amountMoney;
    }

    public ProcessingFeeMoney getProcessingFeeMoney() {
        return processingFeeMoney;
    }

    public void setProcessingFeeMoney(ProcessingFeeMoney processingFeeMoney) {
        this.processingFeeMoney = processingFeeMoney;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CardDetails getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
    }

    public List<AdditionalRecipient> getAdditionalRecipients() {
        return additionalRecipients;
    }

    public void setAdditionalRecipients(List<AdditionalRecipient> additionalRecipients) {
        this.additionalRecipients = additionalRecipients;
    }

}
