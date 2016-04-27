package com.somoplay.screenshow.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.StoreMedia;
import com.somoplay.screenshow.util.LYDateString;

import java.util.ArrayList;

/**
 * Created by JudyShuai on 15-11-30.
 */
public class StoreMediaDB {
    private static final String TAG = MediaDB.class.getSimpleName();
    // database object and database helper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private SharedPreferences sharedPref = AppController.getInstance().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
    private String screenId;
    private String currentDate;





    // constructor
    public StoreMediaDB(Context context) {
        dbHelper = DBHelper.getInstance(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }
    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }



    private void closeDB() {
        if (db != null)
            db.close();
    }

    private StoreMedia getOneMediaByMediaId(int id) {
        currentDate = sharedPref.getString(Constants.currentTime, "00000");

        String where = Constants.STORE_MEDIA_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        // handle exceptions?
        this.openReadableDB();
        Cursor cursor = db.query(Constants.STORE_MEDIA_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        StoreMedia Media = getStoreMediaFromCursor(cursor);
        Log.d("Store Media updated", "get one existing media" + Media + "from DB");
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return Media;
    }

    public ArrayList<StoreMedia> getStoreMediaByMediaTypeAndElementId(long mediaType, long elementId) {
        currentDate = sharedPref.getString(Constants.currentTime, "00000");
        String where = Constants.STORE_MEDIA_TYPE + "= ?" + " AND " + Constants.STORE_MEDIA_ELEMENT_ID + "= ?"
                + " AND " + Constants.STORE_MEDIA_DATA_DELETED + "= ?";
        String[] whereArgs = { String.valueOf(mediaType) , String.valueOf(elementId), "0"};

        // handle exceptions?
        this.openReadableDB();
        Cursor cursor = db.query(Constants.STORE_MEDIA_TABLE,
                null, where, whereArgs, null, null, null);

        cursor.moveToFirst();
        ArrayList<StoreMedia> Medias = new ArrayList<StoreMedia>();
        while (!cursor.isAfterLast()) {
            Medias.add(getStoreMediaFromCursor(cursor));
            cursor.moveToNext();
        }

        if (cursor != null)
            cursor.close();
        this.closeDB();

        return Medias;
    }

    private static StoreMedia getStoreMediaFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                StoreMedia Media = new StoreMedia(
                        cursor.getDouble(Constants.STORE_MEDIA_VERSION_COL),
                        cursor.getInt(Constants.STORE_MEDIA_DURATION_SEC_COL),
                        cursor.getInt(Constants.STORE_MEDIA_ID_COL),
                        cursor.getInt(Constants.STORE_MEDIA_DATA_DELETED_COL) != 0,
                        LYDateString.stringToDate(cursor.getString(Constants.STORE_MEDIA_DATA_UPDATED_TS_COL), 3),
                        LYDateString.stringToDate(cursor.getString(Constants.STORE_MEDIA_DATA_CREATED_TS_COL), 3),
                        cursor.getInt(Constants.STORE_MEDIA_ADMIN_ID_COL),
                        cursor.getInt(Constants.STORE_MEDIA_USER_ID_COL) ,
                        cursor.getString(Constants.STORE_MEDIA_USER_NAME_COL),
                        cursor.getString(Constants.STORE_MEDIA_NAME_COL),
                        cursor.getInt(Constants.STORE_MEDIA_SHOW_TYPE_COL),
                        cursor.getInt(Constants.STORE_MEDIA_STATUS_COL) ,
                        cursor.getInt(Constants.STORE_MEDIA_TYPE_COL) ,
                        cursor.getInt(Constants.STORE_MEDIA_ELEMENT_ID_COL),
                        cursor.getString(Constants.STORE_MEDIA_URL_COL)
                );
                Log.d("store Media updated","get media "+ Media +"From cursor");
                return Media;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    private long insertStoreMedia(StoreMedia media) {
        ContentValues cv = new ContentValues();

        cv.put(Constants.STORE_MEDIA_ID, media.getId());
        cv.put(Constants.STORE_MEDIA_DATA_DELETED, media.isDataDeleted());
        cv.put(Constants.STORE_MEDIA_DATA_UPDATED_TS, LYDateString.dateToString(media.getDataUpdatedTs(), 3));
        cv.put(Constants.STORE_MEDIA_DATA_CREATED_TS, LYDateString.dateToString(media.getDataCreatedTs(), 3));
        cv.put(Constants.STORE_MEDIA_NAME, media.getMediaName());
        cv.put(Constants.STORE_MEDIA_ADMIN_ID, media.getAdminId());
        cv.put(Constants.STORE_MEDIA_USER_ID, media.getUserId());
        cv.put(Constants.STORE_MEDIA_USER_NAME, media.getUserName());
        cv.put(Constants.STORE_MEDIA_SHOW_TYPE, media.getShowType());
        cv.put(Constants.STORE_MEDIA_STATUS, media.getStatus());
        cv.put(Constants.STORE_MEDIA_TYPE, media.getMediaType());
        cv.put(Constants.STORE_MEDIA_ELEMENT_ID, media.getElementId());
        cv.put(Constants.STORE_MEDIA_URL, media.getMediaUrl());
        cv.put(Constants.STORE_MEDIA_DURATION_SEC, media.getDurationSec());
        cv.put(Constants.STORE_MEDIA_VERSION, media.getVersion());

        String where = Constants.STORE_MEDIA_ID + "= ?";
        String[] whereArgs = { String.valueOf(media.getId()) };

        StoreMedia mediaFromOriginalDB = getOneMediaByMediaId(media.getId());

        long rowID = 0;

        if(mediaFromOriginalDB == null) {
            this.openWriteableDB();
            rowID = db.insert(Constants.STORE_MEDIA_TABLE, null, cv);
            Log.v(TAG, media + "has been inserted to the database in row " + rowID);
            this.closeDB();
        }else
        {
            this.openWriteableDB();
            rowID = db.update(Constants.STORE_MEDIA_TABLE, cv, where, whereArgs);
            Log.v(TAG, media + "has been updated to the database in row " + rowID);
            this.closeDB();
        }

        return rowID;
    }

    public int updateStoreMedia(StoreMedia media) {
        ContentValues cv = new ContentValues();

        cv.put(Constants.STORE_MEDIA_ID, media.getId());
        cv.put(Constants.STORE_MEDIA_DATA_DELETED, media.isDataDeleted());
        cv.put(Constants.STORE_MEDIA_DATA_UPDATED_TS, LYDateString.dateToString(media.getDataUpdatedTs(), 3));
        cv.put(Constants.STORE_MEDIA_DATA_CREATED_TS, LYDateString.dateToString(media.getDataCreatedTs(), 3));
        cv.put(Constants.STORE_MEDIA_NAME, media.getMediaName());
        cv.put(Constants.STORE_MEDIA_ADMIN_ID, media.getAdminId());
        cv.put(Constants.STORE_MEDIA_USER_ID, media.getUserId());
        cv.put(Constants.STORE_MEDIA_USER_NAME, media.getUserName());
        cv.put(Constants.STORE_MEDIA_SHOW_TYPE, media.getShowType());
        cv.put(Constants.STORE_MEDIA_STATUS, media.getStatus());
        cv.put(Constants.STORE_MEDIA_TYPE, media.getMediaType());
        cv.put(Constants.STORE_MEDIA_ELEMENT_ID, media.getElementId());
        cv.put(Constants.STORE_MEDIA_URL, media.getMediaUrl());
        cv.put(Constants.STORE_MEDIA_DURATION_SEC, media.getDurationSec());
        cv.put(Constants.STORE_MEDIA_VERSION, media.getVersion());

        String where = Constants.STORE_MEDIA_ID + "= ?";
        String[] whereArgs = { String.valueOf(media.getId()) };

        StoreMedia mediaFromOriginalDB = getOneMediaByMediaId(media.getId());
        int rowCount = 0;
        if(mediaFromOriginalDB == null){
            insertStoreMedia(media);
        }else{
            this.openWriteableDB();
            rowCount = db.update(Constants.STORE_MEDIA_TABLE, cv, where, whereArgs);
            Log.v("mediaList updated: ", media + "has been updated to the database in row " + rowCount);
            this.closeDB();
        }

        return rowCount;
    }

    public int deleteStoreMedia(long id) {
        String where = Constants.STORE_MEDIA_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(Constants.STORE_MEDIA_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

}
