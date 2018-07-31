package com.ek.posbridge.API.Retrofit.TransactionCall;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Refund {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("tender_id")
    @Expose
    private String tenderId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("amount_money")
    @Expose
    private AmountMoney__ amountMoney;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("processing_fee_money")
    @Expose
    private ProcessingFeeMoney_ processingFeeMoney;
    @SerializedName("additional_recipients")
    @Expose
    private List<AdditionalRecipient_> additionalRecipients = null;

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

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public AmountMoney__ getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(AmountMoney__ amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProcessingFeeMoney_ getProcessingFeeMoney() {
        return processingFeeMoney;
    }

    public void setProcessingFeeMoney(ProcessingFeeMoney_ processingFeeMoney) {
        this.processingFeeMoney = processingFeeMoney;
    }

    public List<AdditionalRecipient_> getAdditionalRecipients() {
        return additionalRecipients;
    }

    public void setAdditionalRecipients(List<AdditionalRecipient_> additionalRecipients) {
        this.additionalRecipients = additionalRecipients;
    }

}
