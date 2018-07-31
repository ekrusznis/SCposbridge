package com.ek.posbridge.API.Webservices;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.ek.posbridge.Models.Product;
import com.ek.posbridge.Models.Products;
import com.ek.posbridge.UI.LoginActivity;
import com.ek.posbridge.UI.OrdersFragment;
import com.ek.posbridge.UI.ProductsFragment;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;

import java.util.ArrayList;
import java.util.List;

public class GetProducts extends AsyncTask<String, Void, Object> {

    Context context;
    String TAG = "response";
    String wsExt = "scservice.asmx?op=";
    String webserviceURL;
    String userURL;
    TextView totalText;
    TextView floatingCostView;
    ArrayList<Products> arrayList;

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

    private OrdersFragment fragment;
    private ProductsFragment productsFragment;

    public GetProducts(OrdersFragment fragment,TextView floatingCostView){
        this.fragment = fragment;
    }
    public GetProducts(ProductsFragment productsFragment, TextView totalText){
        this.productsFragment = productsFragment;
        this.totalText = totalText;

    }
    public List ProductListAdapter(ArrayList<Products> arrayList){
        return arrayList;
    }


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

//        String id = null;
        SoapObject response = null;


        //ARRAY IS ADDED AS OBJECT TO REQUEST!
        SoapObject idArray = new SoapObject(NAMESPACE, "id");
        idArray.addProperty("string", id[0]);

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("id", idArray); //put properties required here

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
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        Log.i(TAG, "onPostExecute");

        if (result instanceof SoapObject) {
            SoapObject response = (SoapObject) result;
            Log.i("SOAP OBJECT response: ", response.toString());

            int productsArraySize = response.getPropertyCount();

            for (int i = 0; i < productsArraySize; i++) {
                SoapObject productSoap = (SoapObject) response.getProperty(i);
                String sku = productSoap.getPrimitivePropertyAsString("ID");
                String upc = productSoap.getPropertyAsString("UPC");
                double siteprice = Integer.parseInt(productSoap.getPropertyAsString("SitePrice"));
                Log.i(TAG, "SKU = " + sku);
                Log.i(TAG, "UPC = " + upc);
                Log.i(TAG, "SitePrice = " + siteprice);

                Log.i("Start ArrayList<Product>", "productList");

                Product product = new Product(sku, upc, siteprice);
                product.setSku(sku);
                product.setUpc(upc);
                product.setPrice(siteprice);

                fragment.getProductArrayList().add(product);
                fragment.getAdapter().notifyDataSetChanged();

                Products products = new Products(sku, upc, siteprice);
                String priceStr = Double.toString(siteprice);
                Log.i("price string, getproducts", priceStr);
                productsFragment.getTotalText().setText(priceStr);
//
                productsFragment.getArrayList().add(products);
                productsFragment.getAdapter().notifyDataSetChanged();



                Log.i("NotifyDataSetChange ", "setting adapter");
//

            }

        }
    }

}
