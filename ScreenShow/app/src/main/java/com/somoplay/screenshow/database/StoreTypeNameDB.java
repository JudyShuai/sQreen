package com.somoplay.screenshow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.StoreTypeName;
import com.somoplay.screenshow.util.LYDateString;

import java.util.ArrayList;

/**
 * Created by JudyShuai on 15-06-13.
 */
public class StoreTypeNameDB {
    // database object and database helper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public StoreTypeNameDB(Context context) {
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

    // public methods
    public ArrayList<StoreTypeName> getStoreTypeNamesByType(long type) {
        String where = Constants.STORE_TYPE_NAME_TYPE + "= ?" + " AND " + Constants.STORE_TYPE_NAME_DELETED + "= ?";
        String[] whereArgs = { String.valueOf(type) , "0"};

        this.openReadableDB();
        Cursor cursor = db.query(Constants.STORE_TYPE_NAME_TABLE, null,
                where, whereArgs,
                null, null, null);
        ArrayList<StoreTypeName> StoreTypeNames = new ArrayList<StoreTypeName>();
        while (cursor.moveToNext()) {
            StoreTypeNames.add(getStoreTypeNameFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return StoreTypeNames;
    }

    // public methods
    public ArrayList<StoreTypeName> getStoreTypeNamesByTypeAndId(long type, long id) {
        String where = Constants.STORE_TYPE_NAME_TYPE + "= ?" + " AND " + Constants.STORE_TYPE_NAME_STORE_ID + "= ?"
                + " AND " + Constants.STORE_TYPE_NAME_DELETED + "= ?";
        String[] whereArgs = { String.valueOf(type) , String.valueOf(id) , "0"};

        this.openReadableDB();
        Cursor cursor = db.query(Constants.STORE_TYPE_NAME_TABLE, null,
                where, whereArgs,
                null, null, null);

        ArrayList<StoreTypeName> StoreTypeNames = new ArrayList<StoreTypeName>();
        while (cursor.moveToNext()) {
            StoreTypeNames.add(getStoreTypeNameFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return StoreTypeNames;
    }

    public StoreTypeName getStoreTypeName(long id) {
        //String where = Constants.STORE_TYPE_NAME_ID + "= ?" + " AND " + Constants.STORE_TYPE_NAME_DELETED + "= ?";
        String where = Constants.STORE_TYPE_NAME_ID + "= ?";
        String[] whereArgs = { String.valueOf(id)};

        // handle exceptions?
        this.openReadableDB();
        Cursor cursor = db.query(Constants.STORE_TYPE_NAME_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        StoreTypeName storeTypeName = getStoreTypeNameFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return storeTypeName;
    }

    private static StoreTypeName getStoreTypeNameFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                StoreTypeName StoreTypeName = new StoreTypeName(
                    cursor.getInt(Constants.STORE_TYPE_NAME_ID_COL),
                    cursor.getInt(Constants.STORE_TYPE_NAME_DELETED_COL)==0 ? false : true,
                    LYDateString.stringToDate(cursor.getString(Constants.STORE_TYPE_NAME_UPDATED_TS_COL), 3),
                    LYDateString.stringToDate(cursor.getString(Constants.STORE_TYPE_NAME_CREATED_TS_COL), 3),
                    cursor.getString(Constants.STORE_TYPE_NAME_NAME_COL),
                    cursor.getInt(Constants.STORE_TYPE_NAME_STORE_ID_COL),
                    cursor.getInt(Constants.STORE_TYPE_NAME_FUNCTION_ID_COL),
                    cursor.getInt(Constants.STORE_TYPE_NAME_TYPE_COL),
                    cursor.getInt(Constants.STORE_TYPE_NAME_SEQUENCE_COL),
                    cursor.getString(Constants.STORE_TYPE_NAME_MEDIA_URL_COL)
                        //cursor.getInt(Constants.STORE_TYPE_NAME_SCREEN_ID_COL)

                );
                return StoreTypeName;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public long  insertStoreTypeName(StoreTypeName storeTypeName) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.STORE_TYPE_NAME_ID,storeTypeName.getId());
        cv.put(Constants.STORE_TYPE_NAME_DELETED, storeTypeName.isDeleted());
        cv.put(Constants.STORE_TYPE_NAME_UPDATED_TS, LYDateString.dateToString(storeTypeName.getUpdatedTs(), 3));
        cv.put(Constants.STORE_TYPE_NAME_CREATED_TS, LYDateString.dateToString(storeTypeName.getCreatedTs(), 3));
        cv.put(Constants.STORE_TYPE_NAME_NAME, storeTypeName.getName());
        cv.put(Constants.STORE_TYPE_NAME_STORE_ID, storeTypeName.getStoreId());
        cv.put(Constants.STORE_TYPE_NAME_FUNCTION_ID, storeTypeName.getFunctionId());
        cv.put(Constants.STORE_TYPE_NAME_TYPE, storeTypeName.getType());
        cv.put(Constants.STORE_TYPE_NAME_SEQUENCE, storeTypeName.getSequence());
        cv.put(Constants.STORE_TYPE_NAME_MEDIA_URL,storeTypeName.getMediaUrl());
        //cv.put(Constants.STORE_TYPE_NAME_SCREEN_ID,storeTypeName.getScreenId());

        long rowID;
        String where = Constants.STORE_TYPE_NAME_ID + "= ?";
        String[] whereArgs = { String.valueOf(storeTypeName.getId()) };

        StoreTypeName storeTypeNameFromOriginalDB = getStoreTypeName(storeTypeName.getId());

        if(storeTypeNameFromOriginalDB == null) {
            this.openWriteableDB();
            rowID = db.insert(Constants.STORE_TYPE_NAME_TABLE, null, cv);
            Log.v("inserted TypeName to DB","inserted "+ storeTypeName + "into row "+rowID);
            this.closeDB();
        }else {
            this.openWriteableDB();
            rowID = db.update(Constants.STORE_TYPE_NAME_TABLE, cv, where, whereArgs);
            Log.v("storeTypeName updated: ", storeTypeName + "has been updated to the database in row " + rowID);
            this.closeDB();
        }

        return rowID;
    }

    public int updateStoreTypeName(StoreTypeName storeTypeName) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.STORE_TYPE_NAME_ID, storeTypeName.getId());
        cv.put(Constants.STORE_TYPE_NAME_DELETED, storeTypeName.isDeleted());
        cv.put(Constants.STORE_TYPE_NAME_UPDATED_TS, LYDateString.dateToString(storeTypeName.getUpdatedTs(), 3));
        cv.put(Constants.STORE_TYPE_NAME_CREATED_TS, LYDateString.dateToString(storeTypeName.getCreatedTs(), 3));
        cv.put(Constants.STORE_TYPE_NAME_NAME, storeTypeName.getName());
        cv.put(Constants.STORE_TYPE_NAME_STORE_ID, storeTypeName.getStoreId());
        cv.put(Constants.STORE_TYPE_NAME_FUNCTION_ID, storeTypeName.getFunctionId());
        cv.put(Constants.STORE_TYPE_NAME_TYPE, storeTypeName.getType());
        cv.put(Constants.STORE_TYPE_NAME_SEQUENCE,storeTypeName.getSequence());
        cv.put(Constants.STORE_TYPE_NAME_MEDIA_URL, storeTypeName.getMediaUrl());
        //cv.put(Constants.STORE_TYPE_NAME_SCREEN_ID,storeTypeName.getScreenId());

        String where = Constants.STORE_TYPE_NAME_ID + "= ?";
        String[] whereArgs = { String.valueOf(storeTypeName.getId()) };

        StoreTypeName storeTypeNameFromOriginalDB = getStoreTypeName(storeTypeName.getId());
        int rowCount = 0;
        if(storeTypeNameFromOriginalDB == null){
            insertStoreTypeName(storeTypeName);
        }else{
            this.openWriteableDB();
            rowCount = db.update(Constants.STORE_TYPE_NAME_TABLE, cv, where, whereArgs);
            this.closeDB();
        }

        return rowCount;
    }

    public int deleteStoreTypeName(long id) {
        String where = Constants.STORE_TYPE_NAME_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(Constants.STORE_TYPE_NAME_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }
}
