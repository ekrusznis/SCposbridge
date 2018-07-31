package com.ek.posbridge.API.Webservices;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.ek.posbridge.Models.Products;
import com.ek.posbridge.UI.LoginActivity;
import com.ek.posbridge.UI.ProductsFragment;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;

import static android.content.ContentValues.TAG;

public class GetAllProductsWS extends AsyncTask<String, Void, Object> {

    String TAG = "response";
    String wsExt = "scservice.asmx?op=";
    String webserviceURL;
    String userURL;


    Context context;
    //String URL = "https://tt.ws.sellercloud.com/scservice.asmx?op=";
    String NAMESPACE = "http://api.sellercloud.com/";
    String SOAP_ACTION = "http://api.sellercloud.com/GetAllProducts";
    String METHOD_NAME = "GetAllProducts";

    Integer COMPANY_ID = 163;
    Integer PAGE_NUMBER = 1;
    String USERNAME = LoginActivity.userName;
    String PASSWORD = LoginActivity.password;
    Boolean ValidateDeviceID = true;
    String APP_NAME= "POS BRIDGE";

    private final int INDEX_AUTH_HEADER_USERNAME = 0;
    private final int INDEX_AUTH_HEADER_PASSWORD = 1;

    private final String KEY_AuthHeader = "AuthHeader";
    private final String KEY_AuthHeader_UserName = "UserName";
    private final String KEY_AuthHeader_Password = "Password";


    private ProductsFragment fragment;
    public GetAllProductsWS(ProductsFragment fragment){
        this.fragment = fragment;
    }


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
    protected SoapObject doInBackground(String... strings) {
        SoapObject response = null;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

//        SoapObject getAllProducts = new SoapObject(NAMESPACE, "GetAllProducts");
        request.addProperty("CompanyID", COMPANY_ID);
        request.addProperty("PageNumber", PAGE_NUMBER);

//        request.addProperty("GetAllProducts", getAllProducts); //put properties required here

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
            Log.d("RESPONSE", result.toString());
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
        super.onPostExecute((SoapObject) result);
        Log.i(TAG, "onPostExecute");

        if(result instanceof SoapObject) {
            SoapObject response = (SoapObject) result;
            Log.i("SOAP OBJECT response: ", response.toString());

            int productsArraySize = response.getPropertyCount();

            for (int i = 0; i < productsArraySize; i++) {
                SoapObject productSoap = (SoapObject) response.getProperty(i);
                String sku = productSoap.getPrimitivePropertyAsString("ID");
                String upc = productSoap.getPrimitivePropertyAsString("UPC");
                String name = productSoap.getPrimitivePropertyAsString("ProductName");
                Double siteprice = Double.valueOf(productSoap.getPrimitivePropertyAsString("SitePrice"));
                Log.i(TAG, "\n"+"SKU = " + sku);
                Log.i(TAG, "\n"+"UPC = " + upc);
                Log.i(TAG, "\n"+"ProductName = " + name);
                Log.i(TAG, "\n"+"SitePrice = " + siteprice);


                Log.i("Start ArrayList<Products>", "productsList");

                //add returned values to products model class
                Products products = new Products(sku, upc, siteprice);
                products.setSku(sku);
                products.setUpc(upc);
                products.setPrice(siteprice);

                fragment.getProductArrayList().add(products);
                fragment.getAdapter().notifyDataSetChanged();

                Log.i("NotifyDataSetChange ", "setting adapter");


            }
        }


    }

}