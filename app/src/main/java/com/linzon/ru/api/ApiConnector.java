package com.linzon.ru.api;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.linzon.ru.common.Constants;
import com.linzon.ru.database.DBHelper;
import com.linzon.ru.models.MainOffer;
import com.linzon.ru.models.OCategories;
import com.linzon.ru.models.OCategory;
import com.linzon.ru.models.OOffer;
import com.linzon.ru.models.OShop;
import com.linzon.ru.models.OShopOffers;
import com.linzon.ru.models.PCategories;
import com.linzon.ru.models.PCategory;
import com.linzon.ru.models.POffer;
import com.linzon.ru.models.PShop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ApiConnector {
    public static abstract class CallbackGetOfferList {
        public abstract void onSuccess(String success);

        public abstract void onError(String error);
    }

    public static abstract class CallbackGetPriceList {
        public abstract void onSuccess(ArrayList<POffer> success);

        public abstract void onError(String error);
    }

    public static void asyncGetOfferList(final String query, final CallbackGetOfferList callback) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String result = "";
                String responseLine = "";
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(query).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Length", "0");
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        result += responseLine;
                    }
                    bufferedReader.close();

                    JSONObject rootObj = new JSONObject(result);
                    JSONObject shopObj = rootObj.getJSONObject("shop");
                    //JSONArray caregotiesArray = shopObj.getJSONObject("categories").getJSONArray("category");
                    JSONArray offersArray = shopObj.getJSONObject("offers").getJSONArray("offer");

                    /*ArrayList<OCategory> categoryArrayList = new ArrayList<>();

                    ArrayList<OOffer> offerArrayList = new ArrayList<>();

                    for (int i = 0; i < caregotiesArray.length(); i++) {
                        JSONObject currentCategory = caregotiesArray.getJSONObject(i);
                        OCategory category = new OCategory();
                        if (currentCategory.has("id"))
                            category.setId(currentCategory.getString("id"));
                        if (currentCategory.has("name"))
                            category.setName(currentCategory.getString("name"));
                        categoryArrayList.add(category);
                    }*/

                    for(int j = 0; j < offersArray.length(); j++) {
                        JSONObject currentOffer = offersArray.getJSONObject(j);
                        OOffer offer = new OOffer();
                        if(currentOffer.has("id")) {
                            offer.setId(currentOffer.getString("id"));
                        }
                        if(currentOffer.has("name")) {
                            offer.setName(currentOffer.getString("name"));
                        }
                        if(currentOffer.has("currencyId")) {
                            offer.setCurrencyId(currentOffer.getString("currencyId"));
                        }
                        if(currentOffer.has("categoryId")) {
                            offer.setCategoryId(currentOffer.getString("categoryId"));
                        }
                        if(currentOffer.has("picture")) {
                            offer.setPicture(currentOffer.getString("picture"));
                        }
                        if(currentOffer.has("description")) {
                            offer.setDescription(currentOffer.getString("description"));
                        }
                        if(currentOffer.has("price")) {
                            offer.setPrice(currentOffer.getString("price"));
                        }
                        if(currentOffer.has("vendor")) {
                            offer.setVendor(currentOffer.getString("vendor"));
                        }
                        if(currentOffer.has("param_AX")) {
                            String newString = currentOffer.getString("param_AX");
                            if(newString.substring(0, 1).equals("["))
                                newString = newString.substring(1, newString.length()).substring(0, newString.length() - 2).replace("\"", "");
                            offer.setParam_AX(newString.split(","));
                        }
                        if(currentOffer.has("param_BC")) {
                            String newString = currentOffer.getString("param_BC");
                            if(newString.substring(0, 1).equals("["))
                                newString = newString.substring(1, newString.length()).substring(0, newString.length() - 2).replace("\"", "");
                            offer.setParam_BC(newString.split(","));
                        }
                        if(currentOffer.has("param_COLOR")) {
                            String newString = currentOffer.getString("param_COLOR");
                            if(newString.substring(0, 1).equals("["))
                                newString = newString.substring(1, newString.length()).substring(0, newString.length() - 2).replace("\"", "");
                            offer.setParam_COLOR(newString.split(","));
                        }
                        if(currentOffer.has("param_CYL")) {
                            String newString = currentOffer.getString("param_CYL");
                            if(newString.substring(0, 1).equals("["))
                                newString = newString.substring(1, newString.length()).substring(0, newString.length() - 2).replace("\"", "");
                            offer.setParam_CYL(newString.split(","));
                        }
                        if(currentOffer.has("param_PWR")) {
                            String newString = currentOffer.getString("param_PWR");
                            if(newString.substring(0, 1).equals("["))
                                newString = newString.substring(1, newString.length()).substring(0, newString.length() - 2).replace("\"", "");
                            offer.setParam_PWR(newString.split(","));
                        }
                        //offerArrayList.add(offer);

                        String param_BC = Arrays.toString(offer.getParam_BC());
                        String param_AX = Arrays.toString(offer.getParam_AX());
                        String param_CYL = Arrays.toString(offer.getParam_CYL());
                        String param_PWR = Arrays.toString(offer.getParam_PWR());
                        String param_COLOR = Arrays.toString(offer.getParam_COLOR());

                        DBHelper.getInstance().insertRows(
                                DBHelper.OFFERS,
                                DBHelper.setContentValues(
                                        offer.getId(),
                                        offer.getPrice(),
                                        offer.getName(),
                                        offer.getDescription(),
                                        offer.getPicture(),
                                        offer.getCurrencyId(),
                                        offer.getCategoryId(),
                                        offer.getVendor(),
                                        param_BC.substring(1, param_BC.length() - 1),
                                        param_PWR.substring(1, param_PWR.length() - 1),
                                        param_AX.substring(1, param_AX.length() - 1),
                                        param_CYL.substring(1, param_CYL.length() - 1),
                                        param_COLOR.substring(1, param_COLOR.length() - 1))
                        );
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    callback.onError("Server error");
                }
                return "true";
            }

            @Override
            protected void onPostExecute(String result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }

    public static void asyncGetPrice(final String query, final CallbackGetPriceList callback) {
        new AsyncTask<String, Void, ArrayList<POffer>>() {
            @Override
            protected ArrayList<POffer> doInBackground(String... params) {
                Log.e("START_PARSING", "------------------------------");
                String result = "";
                String responseLine = "";
                ArrayList<POffer> offerArrayList = new ArrayList<>();

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(query).openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Length", "0");
                    connection.setConnectTimeout(10000);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((responseLine = bufferedReader.readLine()) != null) {
                        result += responseLine;
                    }
                    bufferedReader.close();

                    JSONObject rootObj = new JSONObject(result);
                    JSONObject shopObj = rootObj.getJSONObject("shop");
                    JSONArray offersArray = shopObj.getJSONObject("offers").getJSONArray("offer");

                    for(int j = 0; j < offersArray.length(); j++) {
                        JSONObject currentOffer = offersArray.getJSONObject(j);
                        POffer offer = new POffer();
                        if(currentOffer.has("id")) {
                            offer.setId(currentOffer.getString("id"));
                        }
                        if(currentOffer.has("name")) {
                            offer.setName(currentOffer.getString("name"));
                        }
                        if(currentOffer.has("picture")) {
                            offer.setPicture(currentOffer.getString("picture"));
                        }
                        if(currentOffer.has("price")) {
                            offer.setPrice(currentOffer.getString("price"));
                        }

                        offerArrayList.add(offer);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    callback.onError("Server error");
                }
                return offerArrayList;
            }

            @Override
            protected void onPostExecute(ArrayList<POffer> result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }
}
