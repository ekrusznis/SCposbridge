package com.ek.posbridge.API.Webservices;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.ek.posbridge.Models.MatrixChildModel;
import com.ek.posbridge.Models.Product;
import com.ek.posbridge.UI.LoginActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;

public class ProductMatrix_GetMatrixChildren extends AsyncTask<String, Void, Object> {
    Context context;
    String TAG = "response";
    String wsExt = "scservice.asmx?op=";
    String webserviceURL;
    String userURL;

    String NAMESPACE = "http://api.sellercloud.com/";
    String SOAP_ACTION = "http://api.sellercloud.com/GetProducts";
    String METHOD_NAME = "GetProducts";

    String USERNAME = LoginActivity.userName;
    String PASSWORD = LoginActivity.password;

    private final int INDEX_AUTH_HEADER_USERNAME = 0;
    private final int INDEX_AUTH_HEADER_PASSWORD = 1;

    private final String KEY_AuthHeader = "AuthHeader";
    private final String KEY_AuthHeader_UserName = "UserName";
    private final String KEY_AuthHeader_Password = "Password";

    private Element buildAuthHeader(String USERNAME, String PASSWORD) {
        Element header = new Element().createElement(NAMESPACE, KEY_AuthHeader);

        Element usernameElement = new Element().createElement(NAMESPACE, KEY_AuthHeader_UserName);
        usernameElement.addChild(org.kxml2.kdom.Node.TEXT, USERNAME);
        header.addChild(INDEX_AUTH_HEADER_USERNAME, org.kxml2.kdom.Node.ELEMENT, usernameElement);

        Element passElement = new Element().createElement(NAMESPACE, KEY_AuthHeader_Password);
        passElement.addChild(org.kxml2.kdom.Node.TEXT, PASSWORD);
        header.addChild(INDEX_AUTH_HEADER_PASSWORD, org.kxml2.kdom.Node.ELEMENT, passElement);

        return header;

    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG, "onPreExecute");

        //BELOW IS BUILDING THE URL FOR WS CALL
        Log.i("Authenticate myURL = ", LoginActivity.myURL);
        userURL = LoginActivity.myURL.substring(8);
        Log.i("trimmed userURL", userURL);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(userURL)
                .appendEncodedPath(wsExt);
        String userServerURL = builder.build().toString();

        Log.i("userServerURL = ", userServerURL);
        webserviceURL = userServerURL;


    }

    @Override
    protected SoapObject doInBackground(String... id) {

        String productid = "0000123";
        SoapObject response = null;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("ProductID", productid); //put properties required here

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.headerOut = new Element[1];
        envelope.headerOut[0] = buildAuthHeader(USERNAME, PASSWORD);
        envelope.dotNet = true;

        HttpTransportSE httpTransport = new HttpTransportSE(webserviceURL);

        try {
            httpTransport.call(NAMESPACE + METHOD_NAME, envelope, null);
            response = (SoapObject) envelope.getResponse();
            StringBuffer result;
            result = new StringBuffer(response.toString());
            Log.d("RESPONSE", String.valueOf(result));
            System.out.println("DONE");


        } catch (Exception e) {
            Log.e(TAG, "Exception");
            e.printStackTrace();

        }

        return response;
    }


        @Override
        protected void onPostExecute (Object result){
            super.onPostExecute(result);
            Log.i(TAG, "onPostExecute");

            if (result instanceof SoapObject) {
                SoapObject response = (SoapObject) result;
                Log.i("SOAP OBJECT response: ", response.toString());

                int productsArraySize = response.getPropertyCount();

                for (int i = 0; i < productsArraySize; i++) {
                    SoapObject productSoap = (SoapObject) response.getProperty(i);
                    String sku = productSoap.getPrimitivePropertyAsString("string");
                    Log.i(TAG, "ProductID = " + sku);

                    Log.i("ProductMatrix_getmatrixchildren - ", "response");

                    MatrixChildModel children = new MatrixChildModel(sku);
                    children.setSku(sku);

                    Log.i("NotifyDataSetChange ", "setting adapter");
//



            }
        }

    }
}