package com.linzon.ru.Activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.linzon.ru.Activity.models.MainOffer;
import com.linzon.ru.Activity.models.Profile.ChangesModel;

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

    public static final String USERS = "users";

    public static final String HISTORY_FRIENDS = "history_friends";

    public static final String HISTORY_SUBSCRIBERS = "history_subscribers";

    private static final String DbName = "vkcheck.db";

    private static final int DataBaseVersion = 4;

    private DBHelper(Context context) {
        super(context, DbName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USERS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, uid text(16), notification int(1), online int(1), time int (12))");
        db.execSQL("create table " + HISTORY_FRIENDS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id text(16), friend_uid text(16))");
        db.execSQL("create table " + HISTORY_SUBSCRIBERS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id text(16), friend_uid text(16))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_SUBSCRIBERS);
        onCreate(db);
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

    public static ContentValues setUserContentValues(String uid, int notification, int online, int time) {
        ContentValues cv = new ContentValues();
        cv.put("uid", uid);
        if(notification != -1)
            cv.put("notification", notification);
        if(online != -1)
            cv.put("online", online);
        if(time != -1)
            cv.put("time", time);
        return cv;
    }

    public static ContentValues setBoardContentValues(int user_id, int friend_uid) {
        ContentValues cv = new ContentValues();
        if(user_id != -1)
            cv.put("user_id", user_id);
        if(friend_uid != -1)
            cv.put("friend_uid", friend_uid);
        return cv;
    }

    public boolean checkIfUserExist(String uid) {
        Cursor cursor = this.selectRows(USERS, null, "uid='" + uid + "'", null, null);
        return !(cursor.getCount() == 0);
    }

    public static String getStringUsers(String where) {
        String result = "";
        Cursor rows = getInstance().selectRows(USERS, null, where, null, "id");
        if (rows.moveToFirst()) {
            do {
                int id = rows.getInt(rows.getColumnIndex("id"));
                int uid = rows.getInt(rows.getColumnIndex("uid"));

                result += String.valueOf(uid) + ",";
            }
            while (rows.moveToNext());
        }
        return (result.length() != 0) ?
            result.substring(0, result.length() - 1) : null;
    }

    public static MainOffer getUserFromDatabase(String uid) {
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
    }
}
