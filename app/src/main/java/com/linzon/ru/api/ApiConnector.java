package com.linzon.ru.api;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.linzon.ru.common.Constants;
import com.linzon.ru.models.MainOffer;
import com.linzon.ru.models.OCategories;
import com.linzon.ru.models.OCategory;
import com.linzon.ru.models.OOffer;
import com.linzon.ru.models.OShop;
import com.linzon.ru.models.OShopOffers;
import com.linzon.ru.models.PCategories;
import com.linzon.ru.models.PCategory;
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
                String myUrl = Constants.STATIC_APP;

                MainOffer mainOffer = new MainOffer();

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

                    JSONObject rootObj = new JSONObject(result);
                    JSONObject shopObj = rootObj.getJSONObject("shop");
                    JSONArray caregotiesArray = shopObj.getJSONObject("categories").getJSONArray("category");
                    JSONArray offersArray = shopObj.getJSONObject("offers").getJSONArray("offer");

                    OShop shop = new OShop();
                    OCategories categories = new OCategories();
                    ArrayList<OCategory> categoryArrayList = new ArrayList<>();

                    OShopOffers shopOffers = new OShopOffers();
                    ArrayList<OOffer> offerArrayList = new ArrayList<>();

                    for (int i = 0; i < caregotiesArray.length(); i++) {
                        JSONObject currentCategory = caregotiesArray.getJSONObject(i);
                        OCategory category = new OCategory();
                        if (currentCategory.has("id"))
                            category.setId(currentCategory.getString("id"));
                        if (currentCategory.has("name"))
                            category.setName(currentCategory.getString("name"));
                        categoryArrayList.add(category);
                    }

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
                        offerArrayList.add(offer);
                    }
                    categories.setCategory(categoryArrayList);
                    shopOffers.setOffer(offerArrayList);

                    shop.setCategories(categories);
                    shop.setOffers(shopOffers);
                    mainOffer.setShop(shop);
                    Log.e("FINISH", "TODO");
                } catch (IOException | JSONException e) {
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
