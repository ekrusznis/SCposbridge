package com.ek.posbridge.API.Retrofit.LocationCall;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("address_line_1")
    @Expose
    private String addressLine1;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("administrative_district_level_1")
    @Expose
    private String administrativeDistrictLevel1;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("country")
    @Expose
    private String country;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Address:\n");
        sb.append(new GsonBuilder().setPrettyPrinting().create().toJson(this));
        return sb.toString();
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAdministrativeDistrictLevel1() {
        return administrativeDistrictLevel1;
    }

    public void setAdministrativeDistrictLevel1(String administrativeDistrictLevel1) {
        this.administrativeDistrictLevel1 = administrativeDistrictLevel1;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}