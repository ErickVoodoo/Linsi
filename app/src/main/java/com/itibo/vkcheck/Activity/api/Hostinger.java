package com.itibo.vkcheck.Activity.api;

import android.net.Uri;
import android.os.AsyncTask;

import com.itibo.vkcheck.Activity.models.HostingerModel;
import com.itibo.vkcheck.Activity.models.PostModel.HistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by erick on 22.10.15.
 */
public class Hostinger {

    public static abstract class CallbackHistory {
        public abstract void onSuccess(ArrayList<HistoryModel> model);

        public abstract void onError(String error);
    }

    public static abstract class CallbackResponse {
        public abstract void onSuccess(HostingerModel model);

        public abstract void onError(String error);
    }


    public static void asyncGetHistory(final String query, final CallbackHistory callback) {
        new AsyncTask<String, Void, ArrayList<HistoryModel>>() {
            @Override
            protected ArrayList<HistoryModel> doInBackground(String... params) {
                String resultString = "";
                String responseLine = "";
                ArrayList<HistoryModel> returnModel = new ArrayList<HistoryModel>();

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("vkcheck.hol.es")
                        .appendPath("main")
                        .appendPath("getUserHistory");
                String myUrl = builder.build().toString();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(myUrl).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Length", String.valueOf(("secret=mozolevski999&uid" + "=" + query).length()));
                    connection.setDoOutput(true);
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("secret=mozolevski999&uid" + "=" + query);
                    outputStream.flush();
                    outputStream.close();
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        resultString += responseLine;
                    }
                    bufferedReader.close();

                    JSONObject jsonObject = new JSONObject(resultString);
                    String notes = jsonObject.getString("message");
                    JSONArray response = new JSONArray(notes);

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject currLot = response.getJSONObject(i);
                        HistoryModel tempModel = new HistoryModel();
                        if (currLot.has("time"))
                            tempModel.setTime(Integer.parseInt(currLot.getString("time")));
                        if (currLot.has("type"))
                            tempModel.setType(Integer.parseInt(currLot.getString("type")));
                        if (currLot.has("device"))
                            tempModel.setDevice(Integer.parseInt(currLot.getString("device")));

                        returnModel.add(tempModel);
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                return returnModel;
            }

            @Override
            protected void onPostExecute(ArrayList<HistoryModel> result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }

    public static void asyncAddUser(final String query, final CallbackResponse callback) {
        new AsyncTask<String, Void, HostingerModel>() {
            @Override
            protected HostingerModel doInBackground(String... params) {
                String resultString = "";
                String responseLine = "";
                HostingerModel hostingerModel = new HostingerModel();

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("vkcheck.hol.es")
                        .appendPath("main")
                        .appendPath("addUser");
                String myUrl = builder.build().toString();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(myUrl).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Length", String.valueOf(("secret=mozolevski999&uid" + "=" + query).length()));
                    connection.setDoOutput(true);
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("secret=mozolevski999&uid" + "=" + query);
                    outputStream.flush();
                    outputStream.close();
                    connection.setConnectTimeout(10000);

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        resultString += responseLine;
                    }
                    bufferedReader.close();

                    JSONObject jsonObject = new JSONObject(resultString);

                    if (jsonObject.has("status"))
                        hostingerModel.setStatus(jsonObject.getString("status"));
                    if (jsonObject.has("code"))
                        hostingerModel.setCode(jsonObject.getString("code"));
                    if (jsonObject.has("message"))
                        hostingerModel.setMessage(jsonObject.getString("message"));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return hostingerModel;
            }

            @Override
            protected void onPostExecute(HostingerModel result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }

    public static void asyncRemoveUser(final String query, final CallbackResponse callback) {
        new AsyncTask<String, Void, HostingerModel>() {
            @Override
            protected HostingerModel doInBackground(String... params) {
                String resultString = "";
                String responseLine = "";
                HostingerModel hostingerModel = new HostingerModel();

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("vkcheck.hol.es")
                        .appendPath("main")
                        .appendPath("removeUser");
                String myUrl = builder.build().toString();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(myUrl).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Length", String.valueOf(("secret=mozolevski999&uid" + "=" + query).length()));
                    connection.setDoOutput(true);
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("secret=mozolevski999&uid" + "=" + query);
                    outputStream.flush();
                    outputStream.close();
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        resultString += responseLine;
                    }
                    bufferedReader.close();

                    JSONObject jsonObject = new JSONObject(resultString);

                    if (jsonObject.has("status"))
                        hostingerModel.setStatus(jsonObject.getString("status"));
                    if (jsonObject.has("code"))
                        hostingerModel.setCode(jsonObject.getString("code"));
                    if (jsonObject.has("message"))
                        hostingerModel.setMessage(jsonObject.getString("message"));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return hostingerModel;
            }

            @Override
            protected void onPostExecute(HostingerModel result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }

    public static void asyncGetUser(final String query, final CallbackResponse callback) {
        new AsyncTask<String, Void, HostingerModel>() {
            @Override
            protected HostingerModel doInBackground(String... params) {
                String responseLine = "";
                String resultString = "";
                HostingerModel hostingerModel = new HostingerModel();

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("vkcheck.hol.es")
                        .appendPath("main")
                        .appendPath("getUser");
                String myUrl = builder.build().toString();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(myUrl).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Length", String.valueOf(("secret=mozolevski999&uid" + "=" + query).length()));
                    connection.setDoOutput(true);
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes("secret=mozolevski999&uid" + "=" + query);
                    outputStream.flush();
                    outputStream.close();
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        resultString += responseLine;
                    }
                    bufferedReader.close();

                    JSONObject jsonObject = new JSONObject(resultString);

                    if (jsonObject.has("status"))
                        hostingerModel.setStatus(jsonObject.getString("status"));
                    if (jsonObject.has("code"))
                        hostingerModel.setCode(jsonObject.getString("code"));
                    if (jsonObject.has("message"))
                        hostingerModel.setMessage(jsonObject.getString("message"));

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return hostingerModel;
            }

            @Override
            protected void onPostExecute (HostingerModel result){
                callback.onSuccess(result);
            }
        }.execute(query);
    }
}
