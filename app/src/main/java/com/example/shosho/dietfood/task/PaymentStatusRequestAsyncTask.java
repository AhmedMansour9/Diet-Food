package com.example.shosho.dietfood.task;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;


import com.example.shosho.dietfood.common.Constants;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Represents an async task to request a payment status from the server.
 */
public class PaymentStatusRequestAsyncTask extends AsyncTask<String, Void, String> {

    private PaymentStatusRequestListener listener;
    StringBuilder result = new StringBuilder();

    public PaymentStatusRequestAsyncTask(PaymentStatusRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 1) {
            return null;
        }

        String resourcePath = params[0];

        if (resourcePath != null) {
            return requestPaymentStatus(resourcePath);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String paymentStatus) {
        if (listener != null) {
            if (paymentStatus == null) {
                listener.onErrorOccurred();

                return;
            }

            listener.onPaymentStatusReceived(paymentStatus);
        }
    }

    private String requestPaymentStatus(String resourcePath) {
        if (resourcePath == null) {
            return null;
        }
        URL url;
        String urlString;
        HttpURLConnection connection = null;
        String paymentStatus = null;

        try {
            urlString = Constants.BASE_URL + "/paymentId?" +
                    "id=" + CheckoutIdRequestAsyncTask.checkoutId+
                    "&userId=" + "8ac7a4c8686138d701686fad36ce11a4" +
                    "&password=" + "kejWhw4yhN" +
                    "&entityId=" +"8ac7a4c8686138d701686fad698011ae";
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);
//            JsonReader jsonReader = new JsonReader(
//                    new InputStreamReader(connection.getInputStream()));
//            jsonReader.beginObject();
//            while (jsonReader.hasNext()) {
//                if (jsonReader.nextName().equals("result")) {
//                    paymentStatus = jsonReader.nextString();
//                } else {
//                    jsonReader.skipValue();
//                }
//            }

//            jsonReader.endObject();
//            jsonReader.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            String json = result.toString();
            JSONObject jObj = new JSONObject(json);
            JSONObject a=jObj.getJSONObject("result");
                paymentStatus=a.getString("code");
            reader.close();
        }


        Log.d(Constants.LOG_TAG, "Status: " + paymentStatus);
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, "Error: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return paymentStatus;
    }
}
