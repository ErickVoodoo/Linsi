package com.linzon.ru.database;

import android.os.AsyncTask;
import android.util.Log;

import com.linzon.ru.models.MainOffer;
import com.linzon.ru.models.OCategories;
import com.linzon.ru.models.OCategory;
import com.linzon.ru.models.OOffer;
import com.linzon.ru.models.OShop;
import com.linzon.ru.models.OShopOffers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Admin on 19.03.2016.
 */
public class DBAsync {
    public static abstract class CallBackGetCategory {
        public abstract void onSuccess(ArrayList<OOffer> success);

        public abstract void onError(String error);
    }

    public static void asyncGetOfferList(final Integer query, final CallBackGetCategory callback) {
        new AsyncTask<Integer, Void, ArrayList<OOffer>>() {
            @Override
            protected ArrayList<OOffer> doInBackground(Integer... params) {
                return DBHelper.getInstance().getCategoryOffers(String.valueOf(query));
            }

            @Override
            protected void onPostExecute(ArrayList<OOffer> result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }
}
