package com.ek.posbridge.API.Retrofit.TransactionCall;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("location_id")
    @Expose
    public String locationId;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("tenders")
    @Expose
    public List<Tender> tenders = null;
    @SerializedName("refunds")
    @Expose
    public List<Refund> refunds = null;
    @SerializedName("reference_id")
    @Expose
    public String referenceId;
    @SerializedName("product")
    @Expose
    public String product;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Tender> getTenders() {
        return tenders;
    }

    public void setTenders(List<Tender> tenders) {
        this.tenders = tenders;
    }

    public List<Refund> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

}
