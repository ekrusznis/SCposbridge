package com.ek.posbridge.API.Retrofit.LocationCall;


import java.io.Serializable;

public class LocationID implements Serializable {

    private static final long serialVersionUIOD = 1L;

    public String getLocID() {
        return locID;
    }

    public void setLocID(String locID) {
        this.locID = locID;
    }

    private String locID;

    @Override
    public String toString(){
        return locID;
    }

}
