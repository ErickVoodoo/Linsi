package com.linzon.ru.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.linzon.ru.models.BasketItem;
import com.linzon.ru.models.OOffer;

import java.util.ArrayList;

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
        this.getWritableDatabase().execSQL("delete from " + BASKET);
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
        Cursor rows = getInstance().selectRows(OFFERS, null, "categoryId = '" + where + "'", null, "id");
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
        Cursor rows = getInstance().selectRows(BASKET, null, "status = '" + where + "'", null, "id");
        if (rows.moveToFirst()) {
            do {
                BasketItem offer = new BasketItem();
                offer.setId(rows.getString(rows.getColumnIndex("id")));
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

    public static void updateBasketCount(final String id, final String count) {
        ContentValues basketValues = DBHelper.setBasketContentValues(null, null, count, null, null, null, null, null);
        DBHelper.getInstance().updateRows(DBHelper.BASKET, basketValues, "id = '" + id + "'");
    }
    /*public static MainOffer getUserFromDatabase(String uid) {
        MainOffer model = new MainOffer();
        Cursor values = getInstance().selectRows(USERS, null, "uid='" + uid + "'", null, null);

        if(values.getCount() == 0) {
            return null;
        }
        if(values.moveToFirst()) {
            do {
                model.setId(values.getInt(values.getColumnIndex("id")));
                model.setUid(String.valueOf(values.getInt(values.getColumnIndex("uid"))));
                model.setNotification(values.getInt(values.getColumnIndex("notification")));
                model.setOnline(values.getInt(values.getColumnIndex("online")));
                model.setTime(values.getInt(values.getColumnIndex("time")));
            }
            while (values.moveToNext());
        }
        return model;
    }

    public static ArrayList<ChangesModel> getUserFriendsFromDatabase(String uid) {
        ArrayList<ChangesModel> model = new ArrayList<>();
        Cursor values = getInstance().selectRows(HISTORY_FRIENDS, null, "user_id='" + uid + "'", null, null);

        if(values.getCount() == 0) {
            return null;
        }
        if(values.moveToFirst()) {
            do {
                ChangesModel tempModel = new ChangesModel();
                tempModel.setId(values.getInt(values.getColumnIndex("id")));
                tempModel.setUser_id(values.getInt(values.getColumnIndex("user_id")));
                tempModel.setFriend_uid(values.getInt(values.getColumnIndex("friend_uid")));
                model.add(tempModel);
            }
            while (values.moveToNext());
        }
        return model;
    }*/
}
