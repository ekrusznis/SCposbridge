package com.ek.posbridge.API.Webservices;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.ek.posbridge.Models.TempArrayModel;
import com.ek.posbridge.UI.LoginActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ListOrders extends AsyncTask<String, Void, Object> {

    Context context;
    String TAG = "response";
    String wsExt = "OrderCreationService.asmx?op=";
    String webserviceURL;
    String userURL;
    String startDate = "2018-07-22";
    String endDate = "2018-07-26";

    String NAMESPACE = "http://api.sellercloud.com/";
    String SOAP_ACTION = "http://api.sellercloud.com/ListOrders";
    String METHOD_NAME = "ListOrders";

    Integer companyID = 163;
    Integer pageNumber = 1;
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

        Date c = Calendar.getInstance().getTime();
        System.out.println("DIB - Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        LocalDate date = LocalDate.now().minusDays(4);

        String stringDate = date.toString();

        Log.i("DIB - formattedDate =", formattedDate);
        Log.i("DIB - stringDate =", stringDate);

        SoapObject response = null;

        Log.i("Start Of Request.", "addProperty");
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("CompanyID", companyID);
        request.addProperty("startDate", startDate);
        request.addProperty("EndDate", endDate);
        request.addProperty("PageNumber", pageNumber);

        Log.i("Start Of envelope.", "adding request to envelope");

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.headerOut = new Element[1];
        envelope.headerOut[0] = buildAuthHeader(USERNAME, PASSWORD);
        envelope.dotNet = true;

        Log.i("Start Of httpTransport.", webserviceURL);

        HttpTransportSE httpTransport = new HttpTransportSE(webserviceURL);

        try {
            Log.i("Start Of try/catch.", "call");

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


        try {
            if (result instanceof SoapObject) {
                SoapObject response = (SoapObject) result;
                Log.i("SoapObject response: ", response.toString());

                int productsArraySize = response.getPropertyCount();
                Log.i("RESPONSE = ", response.toString());

               for (int i = 0; i < productsArraySize; i++) {
                    SoapObject productSoap = (SoapObject) response.getProperty(i);
                    String orderid = productSoap.getPrimitivePropertyAsString("OrderID");
                   Log.i(TAG, "orderid = " + orderid);
                   String timeoforder = productSoap.getPrimitivePropertyAsString("TimeOfOrder");
                   Log.i(TAG, "timeoforder = " + timeoforder);
                   String orderstatus = productSoap.getPrimitivePropertyAsString("OrderStatus");
                   Log.i(TAG, "orderstatus = " + orderstatus);
                   String ordersource = productSoap.getPrimitivePropertyAsString("OrderSource");
                   Log.i(TAG, "ordersource = " + ordersource);


                   //Reformat the time from String to Date so it can be added to TempArrayModel class
                   String dateString = timeoforder;
                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                   Date convertedDate = new Date();
                   try {
                       convertedDate = dateFormat.parse(dateString);
                   } catch (ParseException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
                   }
                   System.out.println(convertedDate);


                   TempArrayModel tempArrayModel = new TempArrayModel(orderid, convertedDate, orderstatus, ordersource);
                    tempArrayModel.setOrderid(productSoap.getPrimitivePropertyAsString("OrderID"));
                    tempArrayModel.setTimeoforder(convertedDate);
                    tempArrayModel.setOrderstatus(productSoap.getPrimitivePropertyAsString("OrderStatus"));
                    tempArrayModel.setOrdersource(productSoap.getPrimitivePropertyAsString("OrderSource"));

                    ArrayList<TempArrayModel> tempList = new ArrayList<TempArrayModel>();
                    tempList.add(tempArrayModel);
                    tempList.size();

                   int  num = Collections.frequency(tempList, orderid);
                   Log.i("FREQUENCY OF ORDERID = ", String.valueOf(num));






               }
            }
        }catch (Exception e){
            System.out.println("Error " + e.getMessage());

        }



    }
}

