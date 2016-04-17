package com.linzon.ru.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.linzon.ru.common.Constants;
import com.linzon.ru.common.SharedProperty;
import com.linzon.ru.common.TimeCommon;
import com.linzon.ru.models.BasketItem;
import com.linzon.ru.models.OOffer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    private static Context context;

    public static void init(Context context) {
        DBHelper.context = context.getApplicationContext();
    }

    private static DBHelper instance;

    public static synchronized DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper(DBHelper.context);
        }
        return instance;
    }

    public static final String OFFERS = "offers";
    public static final String BASKET = "basket";

    private static final String DbName = "linzon";

    private static final int DataBaseVersion = 1;

    private DBHelper(Context context) {
        super(context, DbName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + OFFERS + " (" +
                        "id int PRIMARY KEY, " +
                        "price text, " +
                        "name text," +
                        "description text, " +
                        "picture text, " +
                        "currencyId text," +
                        "categoryId text," +
                        "vendor text," +
                        "param_BC text," +
                        "param_PWR text," +
                        "param_AX text," +
                        "param_CYL text," +
                        "param_COLOR text" +
                        ")");
        db.execSQL(
                "create table " + BASKET + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "offer_id text," +
                        "order_id text," +
                        "name text," +
                        "count text, " +
                        "price text, " +
                        "data text, " +
                        "status text, " +
                        "created_at text," +
                        "categoryId text," +
                        "ordered_at text" +
                        ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + OFFERS);
        db.execSQL("DROP TABLE IF EXISTS " + BASKET);
        onCreate(db);
    }

    public void dropDatabase() {
        this.getWritableDatabase().execSQL("delete from " + OFFERS);
    }

    public long insertRows(String TABLE_NAME, ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor selectRows(String TABLE_NAME, String[] columns, String whereClause, String groupBy, String orderBy) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME, columns, whereClause, null, groupBy, null, orderBy);
    }

    public long updateRows(String TABLE_NAME, ContentValues contentValues, String whereClause) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_NAME, contentValues, whereClause, null);
    }

    public long deleteRows(String TABLE_NAME, String whereClause) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, whereClause, null);
    }

    public static ContentValues setContentValues(
            String id,
            String price,
            String name,
            String description,
            String picture,
            String currencyId,
            String categoryId,
            String vendor,
            String param_BC,
            String param_PWR,
            String param_AX,
            String param_CYL,
            String param_COLOR) {
        ContentValues cv = new ContentValues();
        if(id != null)
            cv.put("id", id);
        if(price != null)
            cv.put("price", price);
        if(name != null)
            cv.put("name", name);
        if(description != null)
            cv.put("description", description);
        if(picture != null)
            cv.put("picture", picture);
        if(currencyId != null)
            cv.put("currencyId", currencyId);
        if(categoryId != null)
            cv.put("categoryId", categoryId);
        if(vendor != null)
            cv.put("vendor", vendor);
        if(param_BC != null)
            cv.put("param_BC", param_BC);
        if(param_PWR != null)
            cv.put("param_PWR", param_PWR);
        if(param_AX != null)
            cv.put("param_AX", param_AX);
        if(param_CYL != null)
            cv.put("param_CYL", param_CYL);
        if(param_BC != null)
            cv.put("param_COLOR", param_COLOR);
        return cv;
    }

    public static ContentValues setBasketContentValues(
            String offer_id,
            String order_id,
            String name,
            String count,
            String price,
            String data,
            String status,
            String created_at,
            String ordered_at) {
        ContentValues cv = new ContentValues();
        if(offer_id != null)
            cv.put("offer_id", offer_id);
        if(order_id != null)
            cv.put("order_id", order_id);
        if(price != null)
            cv.put("price", price);
        if(name != null)
            cv.put("name", name);
        if(count != null)
            cv.put("count", count);
        if(price != null)
            cv.put("price", price);
        if(data != null)
            cv.put("data", data);
        if(status != null)
            cv.put("status", status);
        if(created_at != null)
            cv.put("created_at", created_at);
        if(ordered_at != null)
            cv.put("ordered_at", ordered_at);
        return cv;
    }

    public static ArrayList<OOffer> getCategoryOffers(String where) {
        ArrayList<OOffer> arrayList = new ArrayList<>();
        Cursor rows = getInstance().selectRows(OFFERS, null, "categoryId = '" + where + "'", null, "name");
        if (rows.moveToFirst()) {
            do {
                OOffer offer = new OOffer();
                offer.setId(rows.getString(rows.getColumnIndex("id")));
                offer.setPrice(rows.getString(rows.getColumnIndex("price")));
                offer.setName(rows.getString(rows.getColumnIndex("name")));
                offer.setDescription(rows.getString(rows.getColumnIndex("description")));
                offer.setCategoryId(rows.getString(rows.getColumnIndex("categoryId")));
                offer.setVendor(rows.getString(rows.getColumnIndex("vendor")));
                offer.setPicture(rows.getString(rows.getColumnIndex("picture")));
                offer.setCurrencyId(rows.getString(rows.getColumnIndex("currencyId")));
                offer.setParam_AX(rows.getString(rows.getColumnIndex("param_AX")).split(","));
                offer.setParam_BC(rows.getString(rows.getColumnIndex("param_BC")).split(","));
                offer.setParam_CYL(rows.getString(rows.getColumnIndex("param_CYL")).split(","));
                offer.setParam_PWR(rows.getString(rows.getColumnIndex("param_PWR")).split(","));
                offer.setParam_COLOR(rows.getString(rows.getColumnIndex("param_COLOR")).split(","));
                arrayList.add(offer);
            }
            while (rows.moveToNext());
        }
        return arrayList;
    }

    public static OOffer getOfferInfo(String where) {
        OOffer offer = new OOffer();
        Cursor rows = getInstance().selectRows(OFFERS, null, "id = '" + where + "'", null, "id");
        if (rows.moveToFirst()) {
            do {
                offer.setId(rows.getString(rows.getColumnIndex("id")));
                offer.setPrice(rows.getString(rows.getColumnIndex("price")));
                offer.setName(rows.getString(rows.getColumnIndex("name")));
                offer.setCategoryId(rows.getString(rows.getColumnIndex("categoryId")));
                offer.setDescription(rows.getString(rows.getColumnIndex("description")));
                offer.setVendor(rows.getString(rows.getColumnIndex("vendor")));
                offer.setPicture(rows.getString(rows.getColumnIndex("picture")));
                offer.setParam_AX(rows.getString(rows.getColumnIndex("param_AX")).split(","));
                offer.setParam_BC(rows.getString(rows.getColumnIndex("param_BC")).split(","));
                offer.setParam_CYL(rows.getString(rows.getColumnIndex("param_CYL")).split(","));
                offer.setParam_PWR(rows.getString(rows.getColumnIndex("param_PWR")).split(","));
                offer.setParam_COLOR(rows.getString(rows.getColumnIndex("param_COLOR")).split(","));
            }
            while (rows.moveToNext());
        }
        return offer;
    }

    public static ArrayList<BasketItem> getBasketOffers(String where) {
        ArrayList<BasketItem> arrayList = new ArrayList<>();
        Cursor rows = getInstance().selectRows(BASKET, null, "status = '" + where + "'", null, "id DESC");
        if (rows.moveToFirst()) {
            do {
                BasketItem offer = new BasketItem();
                offer.setId(rows.getString(rows.getColumnIndex("id")));
                offer.setOrder_id(rows.getString(rows.getColumnIndex("order_id")));
                offer.setPrice(rows.getString(rows.getColumnIndex("price")));
                offer.setName(rows.getString(rows.getColumnIndex("name")));
                offer.setCount(rows.getString(rows.getColumnIndex("count")));
                offer.setOffer_id(rows.getString(rows.getColumnIndex("offer_id")));
                offer.setCreated_at(rows.getString(rows.getColumnIndex("created_at")));
                offer.setStatus(rows.getString(rows.getColumnIndex("status")));
                offer.setOrdered_at(rows.getString(rows.getColumnIndex("ordered_at")));
                offer.setData(rows.getString(rows.getColumnIndex("data")));
                arrayList.add(offer);
            }
            while (rows.moveToNext());
        }
        return arrayList;
    }

    public static BasketItem getBasketOffer(String where) {
        BasketItem basketItem = new BasketItem();
        Cursor rows = getInstance().selectRows(BASKET, null, "id = '" + where + "' AND status='open'", null, "id");
        if (rows.moveToFirst()) {
            do {
                basketItem.setId(rows.getString(rows.getColumnIndex("id")));
                basketItem.setPrice(rows.getString(rows.getColumnIndex("price")));
                basketItem.setName(rows.getString(rows.getColumnIndex("name")));
                basketItem.setCount(rows.getString(rows.getColumnIndex("count")));
                basketItem.setOffer_id(rows.getString(rows.getColumnIndex("offer_id")));
                basketItem.setCreated_at(rows.getString(rows.getColumnIndex("created_at")));
                basketItem.setStatus(rows.getString(rows.getColumnIndex("status")));
                basketItem.setOrdered_at(rows.getString(rows.getColumnIndex("ordered_at")));
                basketItem.setData(rows.getString(rows.getColumnIndex("data")));
            }
            while (rows.moveToNext());
        }
        return basketItem;
    }

    public static int getTotalPrice(String where) {
        int totalPrice = 0;
        Cursor rows = getInstance().selectRows(BASKET, null, "status = '" + where + "'", null, "id");
        if (rows.moveToFirst()) {
            do {
                totalPrice += Integer.parseInt(rows.getString(rows.getColumnIndex("price"))) * Integer.parseInt(rows.getString(rows.getColumnIndex("count")));
            }
            while (rows.moveToNext());
        }
        return totalPrice;
    }

    public static String[] getVendors() {
        Cursor rows = getInstance().selectRows(OFFERS, null, null, "vendor", "vendor");
        String[] result = new String[rows.getCount() + 1];
        int index = 0;
        result[index] = "Выберите";
        if (rows.moveToFirst()) {
            do {
                if(rows.getString(rows.getColumnIndex("vendor")) != null) {
                    index++;
                    result[index] = rows.getString(rows.getColumnIndex("vendor"));
                }
            }
            while (rows.moveToNext());
        }
        return result;
    }

    public static ArrayList<OOffer> getFilteredOrders(String brand, String vendor, String bc, String pwr, String color) {
        String whereString = "";
        ArrayList<OOffer> arrayList = new ArrayList<>();
        if(brand != null) {
            whereString += "name LIKE '%"+ brand.toLowerCase() + "%' and ";
        }
        if(vendor != null) {
            whereString += "vendor LIKE '%"+ vendor.toLowerCase() + "%' and ";
        }
        if(bc != null) {
            whereString += "param_BC LIKE '%"+ bc.toLowerCase() + "%' and ";
        }
        if(pwr != null) {
            whereString += "param_PWR LIKE '%"+ pwr.toLowerCase() + "%' and ";
        }
        if(color != null) {
            whereString += "param_COLOR LIKE '%"+ color.toLowerCase() + "%' and ";
        }

        whereString = whereString.substring(0, whereString.length() - 5);

        Cursor rows = getInstance().selectRows(OFFERS, null, whereString, null, "name COLLATE NOCASE");
        if (rows.moveToFirst()) {
            do {
                OOffer offer = new OOffer();
                offer.setId(rows.getString(rows.getColumnIndex("id")));
                offer.setPrice(rows.getString(rows.getColumnIndex("price")));
                offer.setName(rows.getString(rows.getColumnIndex("name")));
                offer.setDescription(rows.getString(rows.getColumnIndex("description")));
                offer.setCategoryId(rows.getString(rows.getColumnIndex("categoryId")));
                offer.setVendor(rows.getString(rows.getColumnIndex("vendor")));
                offer.setPicture(rows.getString(rows.getColumnIndex("picture")));
                offer.setCurrencyId(rows.getString(rows.getColumnIndex("currencyId")));
                offer.setParam_AX(rows.getString(rows.getColumnIndex("param_AX")).split(","));
                offer.setParam_BC(rows.getString(rows.getColumnIndex("param_BC")).split(","));
                offer.setParam_CYL(rows.getString(rows.getColumnIndex("param_CYL")).split(","));
                Log.e("PWR", rows.getString(rows.getColumnIndex("param_PWR")));
                offer.setParam_PWR(rows.getString(rows.getColumnIndex("param_PWR")).split(","));
                offer.setParam_COLOR(rows.getString(rows.getColumnIndex("param_COLOR")).split(","));
                arrayList.add(offer);
            }
            while (rows.moveToNext());
        }
        return arrayList;
    }

    public static void updateBasketCount(final String id, final String count) {
        ContentValues basketValues = DBHelper.setBasketContentValues(null, null, null, count, null, null, null, null, null);
        DBHelper.getInstance().updateRows(DBHelper.BASKET, basketValues, "id = '" + id + "'");
    }

    public static void deleteFromBasket(final String id) {
        DBHelper.getInstance().deleteRows(DBHelper.BASKET, "id = '" + id + "'");
    }

    public static void sendToArchive(String orderId) {
        String orderIdString = String.valueOf(Integer.parseInt(orderId.split(" ")[1].substring(0, orderId.split(" ")[1].length() - 1)) - 1);
        SharedProperty.getInstance().setValue(SharedProperty.LAST_ORDER, orderIdString);
        ContentValues basketValues = DBHelper.setBasketContentValues(null, orderIdString, null, null, null, null, Constants.STATUS_ARCHIVED, null, String.valueOf(TimeCommon.getUnixTime()));
        DBHelper.getInstance().updateRows(DBHelper.BASKET, basketValues, "status = 'open'");
    }

    public static void insertToBasket(ContentValues values, String count){
        Cursor row = getInstance().selectRows(BASKET, null,
                "offer_id = '" + values.get("offer_id") + (values.get("data") != null ? "' AND data='" + values.get("data") : "") + "'" +" AND status='open'", null, "id");
        if (row.moveToFirst()) {
            do {
                ContentValues newValues = new ContentValues();
                newValues.put("count", String.valueOf(Integer.parseInt(count) + Integer.parseInt(row.getString(row.getColumnIndex("count")))));
                DBHelper.getInstance().updateRows(DBHelper.BASKET, newValues, "id='" + row.getString(row.getColumnIndex("id")) + "'");
            }
            while (row.moveToNext());
        } else {
            DBHelper.getInstance().insertRows(DBHelper.BASKET, values);
        }
    }

    public static String createFinalJsonData() {
        JSONObject root = new JSONObject();
        JSONObject client = new JSONObject();
        JSONObject info = new JSONObject();
        JSONArray offers = new JSONArray();
        try {
            client.put("username", SharedProperty.getInstance().getValue(SharedProperty.USER_NAME).replaceAll("[-+.^:,&%!]", ""));
            client.put("email", SharedProperty.getInstance().getValue(SharedProperty.USER_EMAIL).replaceAll("[-+.^:,&%!]", ""));
            client.put("phone", SharedProperty.getInstance().getValue(SharedProperty.USER_PHONE).replaceAll("[-+.^:,&%!]", ""));
            client.put("city", SharedProperty.getInstance().getValue(SharedProperty.USER_CITY).replaceAll("[-+.^:,&%!]", ""));
            client.put("address", SharedProperty.getInstance().getValue(SharedProperty.USER_STREET).replaceAll("[-+.^:,&%!]", ""));
            root.put("client", client);

            ArrayList<BasketItem> offersList = getBasketOffers(Constants.STATUS_OPEN);
            for(int i = 0; i < offersList.size(); i++) {
                JSONObject offer = new JSONObject();
                if(offersList.get(i).getData() != null) {
                    JSONObject params = new JSONObject(offersList.get(i).getData());
                    params.remove("TYPE");
                    offer.put("params", params);
                }
                offer.put("id", offersList.get(i).getOffer_id());
                offer.put("price", Integer.parseInt(offersList.get(i).getPrice()) * Integer.parseInt(offersList.get(i).getCount()) );
                offer.put("count", offersList.get(i).getCount());
                offers.put(offer);
            }
            root.put("offers", offers);
            info.put("total", getTotalPrice(Constants.STATUS_OPEN) + Constants.DELIVER_PRICE);
            info.put("shop", "LP");
            info.put("timestamp", new Date());
            info.put("comment", SharedProperty.getInstance().getValue(SharedProperty.USER_COMMENT) != null ? SharedProperty.getInstance().getValue(SharedProperty.USER_COMMENT).replaceAll("[-+.^:,&%!]", "") : "");
            root.put("info", info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root.toString();
    }
}
