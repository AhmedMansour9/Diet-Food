package com.example.shosho.dietfood.task;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;


import com.example.shosho.dietfood.SplashActivity;
import com.example.shosho.dietfood.common.Constants;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Represents an async task to request a checkout id from the server.
 */
public class CheckoutIdRequestAsyncTask extends AsyncTask<String, Void, String> {

    private CheckoutIdRequestListener listener;
    StringBuilder result = new StringBuilder();
   public static  String checkoutId;
    public CheckoutIdRequestAsyncTask(CheckoutIdRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 2) {
            return null;
        }

        String amount = params[0];
        String currency = params[1];

        return requestCheckoutId(amount, currency);
    }

    @Override
    protected void onPostExecute(String checkoutId) {
        if (listener != null) {
            listener.onCheckoutIdReceived(checkoutId);
        }
    }

    private String requestCheckoutId(String amount,
                                     String currency) {
       /* String urlString = Constants.BASE_URL + "/token?" +
                "amount=" + amount +
                "&currency=" + currency +
                "&paymentType=DB" +
                *//* store notificationUrl on your server to change it any time without updating the app *//*
                "&notificationUrl=http://52.59.56.185:80/notification";*/
        String urlString = Constants.BASE_URL + "/checkout?" +
                "amount=" + amount +
                "&userId=" + "8ac7a4c8686138d701686fad36ce11a4" +
                "&password=" + "kejWhw4yhN" +
                "&entityId=" +"8ac7a4c8686138d701686fad698011ae"
              + "&user_token=" + SplashActivity.Login;
        URL url;
        HttpURLConnection connection = null;
         checkoutId = null;

        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);
            connection.setRequestMethod("POST");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                String json = result.toString();
                JSONObject jObj = new JSONObject(json);
                checkoutId=jObj.getString("id");
                reader.close();
            }
            Log.d(Constants.LOG_TAG, "Checkout ID: " + checkoutId);
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, "Error: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return checkoutId;
    }
}