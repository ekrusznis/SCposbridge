package com.ek.posbridge.API.Retrofit.GetServerFromTeamName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetURLFromTeamResponse {


    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("AlphaServerUrl")
    @Expose
    private String alphaServerUrl;
    @SerializedName("DeltaServerUrl")
    @Expose
    private String deltaServerUrl;
    @SerializedName("WebServiceUrl")
    @Expose
    private String webServiceUrl;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getAlphaServerUrl() {
        return alphaServerUrl;
    }

    public void setAlphaServerUrl(String alphaServerUrl) {
        this.alphaServerUrl = alphaServerUrl;
    }

    public String getDeltaServerUrl() {
        return deltaServerUrl;
    }

    public void setDeltaServerUrl(String deltaServerUrl) {
        this.deltaServerUrl = deltaServerUrl;
    }

    public String getWebServiceUrl() {
        return webServiceUrl;
    }

    public void setWebServiceUrl(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

}
