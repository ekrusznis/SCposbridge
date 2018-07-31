package com.ek.posbridge.API.Webservices;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ek.posbridge.UI.LoginActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;

import static android.content.ContentValues.TAG;

public class CustomerGet extends AsyncTask<String, Void, Object> {

    Context context;
    String URL = "https://tt.ws.sellercloud.com/OrderCreationService.asmx?op=";
    String NAMESPACE = "http://api.sellercloud.com/";
    String SOAP_ACTION = "http://api.sellercloud.com/Customer_Get";
    String METHOD_NAME = "Customer_Get";

    String CustomerUserName = "eric@sellercloud.com";
    Integer COMPANY_ID = 163;
    String USERNAME = LoginActivity.userName;
    String PASSWORD = LoginActivity.password;
    public String ID;
    private final int INDEX_AUTH_HEADER_USERNAME = 0;
    private final int INDEX_AUTH_HEADER_PASSWORD = 1;

    private final String KEY_AuthHeader = "AuthHeader";
    private final String KEY_AuthHeader_UserName = "UserName";
    private final String KEY_AuthHeader_Password = "Password";

    private Element buildAuthHeader(String USERNAME, String PASSWORD){
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
    protected SoapObject doInBackground(String... strings) {

        String string = null;
        SoapObject response = null;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty("UserName", CustomerUserName); //put properties required here

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.headerOut = new Element[1];
        envelope.headerOut[0] = buildAuthHeader(USERNAME, PASSWORD);
        envelope.dotNet = true;

        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        try {
            httpTransport.call(NAMESPACE + METHOD_NAME, envelope, null);
            response = (SoapObject) envelope.getResponse();
            StringBuffer result;
            result = new StringBuffer(response.toString());
            Log.d("RESPONSE", String.valueOf(result));
//            result = soapObject.toString();
            System.out.println("DONE");


        } catch (Exception e) {
            Log.e(TAG, "Exception");
            e.printStackTrace();
        }

        return response;

    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        Log.i(TAG, "onPostExecute");

        if(result instanceof SoapObject){
            SoapObject response = (SoapObject)result;
            Log.i("SOAP OBJECT response: ", response.toString());

            int productsArraySize = response.getPropertyCount();

            for(int i = 0; i < productsArraySize; i++){
                SoapObject productSoap = (SoapObject) response.getProperty(i);
                String FirstName = productSoap.getPrimitivePropertyAsString("FirstName");
                String LastName = productSoap.getPrimitivePropertyAsString("LastName");
                String Email = productSoap.getPrimitivePropertyAsString("Email");
                String UserType = productSoap.getPrimitivePropertyAsString("UserType");

                Log.i(TAG, "FirstName = "+ FirstName);
                Log.i(TAG, "LastName = "+ LastName);
                Log.i(TAG, "Email = "+ Email);
                Log.i(TAG, "UserType = "+ UserType);


            }


        }



    }
}



