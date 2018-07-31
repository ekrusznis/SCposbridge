package com.ek.posbridge.API.Webservices;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.ek.posbridge.UI.LoginActivity;

import org.kxml2.kdom.Element;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static android.content.ContentValues.TAG;

public class CreateNewOrderWS extends AsyncTask<String, Void, String>{


    String TAG = "response";
    String wsExt = "OrderCreationService.asmx?op=";
    String webserviceURL;
    String userURL;

         //String URL = "https://tt.ws.sellercloud.com/OrderCreationService.asmx?op=";
        String NAMESPACE = "http://api.sellercloud.com/";
        String SOAP_ACTION = "http://api.sellercloud.com/CreateNewOrder";
        String METHOD_NAME = "CreateNewOrder";
    String USERNAME = LoginActivity.userName;
    String PASSWORD = LoginActivity.password;
        String TEST_SKU = "0000123";
        String TEST_SKU_NAME = "Polar ZipStream 24oz Insulated Bike / Cycling Sport Water Bottle Zip Stream";
        String TEST_SKU_PRICE = "9.99";
        Boolean ValidateDeviceID = true;
        String APP_NAME= "POS BRIDGE";


    private final int INDEX_AUTH_HEADER_USERNAME = 0;
    private final int INDEX_AUTH_HEADER_PASSWORD = 1;
    private final int INDEX_AUTH_HEADER_VALIDATE_DEVICE_ID = 5;
    private final int INDEX_AUTH_HEADER_APP_NAME = 2;

    private final String KEY_AuthHeader = "AuthHeader";
    private final String KEY_AuthHeader_UserName = "UserName";
    private final String KEY_AuthHeader_Password = "Password";
    private final String KEY_AuthHeader_ValidateDeviceID = "ValidateDeviceID";
    private final String KEY_AuthHeader_ApplicationVersion = "ApplicationVersion";
    private final String KEY_AuthHeader_ApplicationName = "ApplicationName";




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
    protected void onPreExecute(){
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
        protected String doInBackground(String... strings) {
            String result = "";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            SoapObject order = new SoapObject(NAMESPACE, "NewOrderInfo");

            order.addProperty("OrderCreationSourceApplication", "PointOfSale");
            order.addProperty("CompanyID", 163);
            order.addProperty("OrderSource", "Local_Store");
            order.addProperty("OrderDate", "2018-07-19");
            order.addProperty("CustomerFirstName", "POS");
            order.addProperty("CustomerLastName", "CASH TRANSACTION");
            order.addProperty("CustomerEmail", "Eric@posbridge.com");
            order.addProperty("ShippingStatus", "FullyShipped");
            order.addProperty("PaymentStatus", "Charged");

            SoapObject items = new SoapObject(NAMESPACE, "Items");

            SoapObject orderItemDetails = new SoapObject(NAMESPACE, "OrderItemDetails");
            orderItemDetails.addProperty("SKU", TEST_SKU);
            orderItemDetails.addProperty("ItemName", TEST_SKU_NAME);
            orderItemDetails.addProperty("Qty", 1);
            orderItemDetails.addProperty("UnitPrice", TEST_SKU_PRICE);

            items.addSoapObject(orderItemDetails);
            order.addSoapObject(items);

            request.addProperty("order", order); //put properties required here

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.headerOut = new Element[1];
            envelope.headerOut[0] = buildAuthHeader(USERNAME, PASSWORD);
            envelope.dotNet = true;

            HttpTransportSE httpTransport = new HttpTransportSE(webserviceURL);

            try {
                httpTransport.call(NAMESPACE + METHOD_NAME, envelope, null);
                SoapPrimitive soapPrimitive = (SoapPrimitive)envelope.getResponse();
                result = soapPrimitive.toString();
                System.out.println("DONE");

            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

    @Override
    protected void onPostExecute(String s){

    }

}
