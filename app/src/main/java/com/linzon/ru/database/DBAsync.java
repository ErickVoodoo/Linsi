package com.linzon.ru.database;

import android.os.AsyncTask;

import com.linzon.ru.models.BasketItem;
import com.linzon.ru.models.OOffer;

import java.util.ArrayList;

public class DBAsync {
    public static abstract class CallbackGetCategory {
        public abstract void onSuccess(ArrayList<OOffer> success);

        public abstract void onError(String error);
    }

    public static abstract class CallbackGetOffer {
        public abstract void onSuccess(OOffer success);

        public abstract void onError(String error);
    }

    public static abstract class CallbackGetBasket {
        public abstract void onSuccess(ArrayList<BasketItem>  success);

        public abstract void onError(String error);
    }

    public static void asyncGetOfferList(final Integer query, final CallbackGetCategory callback) {
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

    public static void asyncGetOfferInfo(final Integer query, final CallbackGetOffer callback) {
        new AsyncTask<Integer, Void, OOffer>() {
            @Override
            protected OOffer doInBackground(Integer... params) {
                return DBHelper.getInstance().getOfferInfo(String.valueOf(query));
            }

            @Override
            protected void onPostExecute(OOffer result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }

    public static void asyncGetBasketList(final String query, final CallbackGetBasket callback) {
        new AsyncTask<String, Void, ArrayList<BasketItem>>() {
            @Override
            protected ArrayList<BasketItem> doInBackground(String... params) {
                return DBHelper.getInstance().getBasketOffers(query);
            }

            @Override
            protected void onPostExecute(ArrayList<BasketItem> result) {
                callback.onSuccess(result);
            }
        }.execute(query);
    }
}
