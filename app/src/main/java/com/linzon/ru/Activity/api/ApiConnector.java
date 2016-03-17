package com.linzon.ru.Activity.api;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ApiConnector {
    public static abstract class CallbackGetOfferList {
        public abstract void onSuccess(String success);
        public abstract void onError(String error);
    }

    public static abstract class CallbackGetPriceList {
        public abstract void onSuccess(String success);
        public abstract void onError(String error);
    }

    public static void asyncGetOfferList(String query, final CallbackGetOfferList callback) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                String responseLine = "";

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("linzon.ru/json_price164.txt");
                String myUrl = builder.build().toString();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(myUrl).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Length", "0");
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        result += responseLine;
                    }
                    bufferedReader.close();

                    /*JSONObject jsonObject = new JSONObject(result);
                    JSONArray response = jsonObject.getJSONArray("response");
                    for (int i = 0; i < response.length(); i++) {
                        if (i != 0) {
                            JSONObject currLot = response.getJSONObject(i);
                            SearchModel tempModel = new SearchModel();
                            if (currLot.has("uid"))
                                tempModel.setUid(currLot.getInt("uid"));
                            if (currLot.has("first_name"))
                                tempModel.setFirst_name(currLot.getString("first_name"));
                            if (currLot.has("last_name"))
                                tempModel.setLast_name(currLot.getString("last_name"));
                            if (currLot.has("photo_100"))
                                tempModel.setPhoto_100(currLot.getString("photo_100"));
                            if (currLot.has("online"))
                                tempModel.setOnline(Integer.parseInt(currLot.getString("online")));

                            if (currLot.has("last_seen")) {
                                OShop tempLastSeen = new OShop();
                                JSONObject data = new JSONObject(currLot.getString("last_seen"));
                                if (data.has("platform"))
                                    tempLastSeen.setPlatform(Integer.parseInt(data.getString("platform")));
                                if (data.has("time"))
                                    tempLastSeen.setTime(Integer.parseInt(data.getString("time")));
                                tempModel.setLast_seen(tempLastSeen);
                            }
                            Array.add(tempModel);
                        }
                    }*/
                } catch (JSONException | ProtocolException | MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "Okay";
            }

            @Override
            protected void onPostExecute(String result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }
}
