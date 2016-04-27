package com.somoplay.screenshow.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.somoplay.screenshow.app.Constants;

/**
 * Created by Shaohua Mao on 2015-07-09.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static DBHelper mInstance = null;

    public DBHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBHelper getInstance(Context context, String databaseName,
                                       SQLiteDatabase.CursorFactory factory, int version) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DBHelper(context, databaseName,factory,version);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables
        db.execSQL(Constants.CREATE_STORE_TYPE_NAME_TABLE);
        db.execSQL(Constants.CREATE_DISH_TABLE);
        db.execSQL(Constants.CREATE_MEDIA_TABLE);
        db.execSQL(Constants.CREATE_STORE_OFFICE_TABLE);
        db.execSQL(Constants.CREATE_SUBTITLE_TABLE);
        db.execSQL(Constants.CREATE_BRANCH_TABLE);
        db.execSQL(Constants.CREATE_CARHOUSE_TABLE);
        db.execSQL(Constants.CREATE_STORE_MEDIA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {

        Log.d("StoreTypeName list", "Upgrading db from version "
                + oldVersion + " to " + newVersion);
        Log.d("StoreTypeName", "Deleting all data!");
        db.execSQL(Constants.DROP_STORE_TYPE_NAME_TABLE);
        db.execSQL(Constants.DROP_DISH_TABLE);
        db.execSQL(Constants.DROP_MEDIA_TABLE);
        db.execSQL(Constants.DROP_STORE_OFFICE_TABLE);
        db.execSQL(Constants.DROP_SUBTITLE_TABLE);
        db.execSQL(Constants.DROP_BRANCH_TABLE);
        db.execSQL(Constants.DROP_CARHOUSE_TABLE);
        db.execSQL(Constants.DROP_STORE_MEDIA_TABLE);
        onCreate(db);
    }
}
