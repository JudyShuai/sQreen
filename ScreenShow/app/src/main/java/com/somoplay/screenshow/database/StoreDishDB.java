package com.somoplay.screenshow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.StoreDish;
import com.somoplay.screenshow.util.LYDateString;

import java.util.ArrayList;

/**
 * Created by yaolu on 15-06-12.
 */
public class StoreDishDB {
    private int size;
    private int itemFinished;

    // database object and database helper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public StoreDishDB(Context context) {
        dbHelper = DBHelper.getInstance(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }
    private void openWritableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    public int getItemFinished() {
        return itemFinished;
    }

    public int getSize() {
        return size;
    }

    // public methods
    public ArrayList<StoreDish> getStoreDishes() {
        String where =  Constants.DISH_DELETED + "= ?";
        String[] whereArgs = {"0"};

        this.openReadableDB();
        Cursor cursor = db.query(Constants.DISH_TABLE, null,
                null, null,
                null, null, null);
        ArrayList<StoreDish> storeDishes = new ArrayList<StoreDish>();

        while (cursor.moveToNext()) {
            storeDishes.add(getStoreDishFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return storeDishes;
    }

    public ArrayList<StoreDish> getDishesByStoreTypeId(int typeId) {
        String where = Constants.DISH_STORE_TYPE_ID + "= ?" + " AND " + Constants.DISH_DELETED + "= ?";
        String[] whereArgs = { String.valueOf(typeId), "0"};
        this.openReadableDB();
        Cursor cursor = db.query(Constants.DISH_TABLE, null,
                where, whereArgs,
                null, null, null);

        ArrayList<StoreDish> StoreDishes = new ArrayList<StoreDish>();
        while (cursor.moveToNext()) {
            StoreDishes.add(getStoreDishFromCursor(cursor));
        }

        if (cursor != null)
            cursor.close();
        this.closeDB();
        return StoreDishes;
    }

    public ArrayList<StoreDish> getSpecificDishesFromDB(int whichKind, int storeId) {

        String where = "";

        if(whichKind == Constants.KEY_IS_KEY_ONE){
            where = Constants.DISH_IS_NEW + "= ?" +
                    " AND " + Constants.DISH_STORE_ID + "= ?" +
                    " AND " + Constants.DISH_DELETED + "= ?";
        }

        else if(whichKind == Constants.KEY_IS_KEY_TWO){
            where = Constants.DISH_IS_DEAL + "= ?" +
                    " AND " + Constants.DISH_STORE_ID + "= ?" +
                    " AND " + Constants.DISH_DELETED + "= ?";
        }

        else if(whichKind == Constants.KEY_IS_KEY_THREE){
            where = Constants.DISH_IS_SPECIALTY + "= ?" +
                    " AND " + Constants.DISH_STORE_ID + "= ?" +
                    " AND " + Constants.DISH_DELETED + "= ?";
        }

        String[] whereArgs = { "1", String.valueOf(storeId), "0"};
        this.openReadableDB();
        Cursor cursor = db.query(Constants.DISH_TABLE, null,
                where, whereArgs,
                null, null, null);

        ArrayList<StoreDish> StoreNewDishes = new ArrayList<StoreDish>();

        while (cursor.moveToNext()) {
            StoreNewDishes.add(getStoreDishFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return StoreNewDishes;
    }


    public StoreDish getStoreDishById(long id) {
        String where = Constants.DISH_ID + "= ?" +  " AND " + Constants.DISH_DELETED + "= ?";
        String[] whereArgs = { String.valueOf(id) , "0"};

        // handle exceptions?
        this.openReadableDB();
        Cursor cursor = db.query(Constants.DISH_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        StoreDish storeDish = getStoreDishFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return storeDish;
    }

    private static StoreDish getStoreDishFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                StoreDish storeDish = new StoreDish(

                        cursor.getInt(Constants.DISH_ID_COL),
                        cursor.getInt(Constants.DISH_DELETED_COL)==1?true:false,
                        LYDateString.stringToDate(cursor.getString(Constants.DISH_UPDATED_TS_COL), 3),
                        LYDateString.stringToDate(cursor.getString(Constants.DISH_CREATED_TS_COL), 3),
                        cursor.getString(Constants.DISH_NAME_COL),
                        cursor.getInt(Constants.DISH_STORE_ID_COL),
                        cursor.getInt(Constants.DISH_STORE_TYPE_ID_COL),
                        cursor.getString(Constants.DISH_LOCAL_ID_COL),
                        cursor.getInt(Constants.DISH_SEQUENCE_COL),
                        cursor.getInt(Constants.DISH_TYPE_COL),
                        cursor.getInt(Constants.DISH_IS_SPECIALTY_COL)==1?true:false,
                        cursor.getInt(Constants.DISH_IS_DEAL_COL)==1?true:false,
                        cursor.getInt(Constants.DISH_IS_NEW_COL)==1?true:false,
                        cursor.getDouble(Constants.DISH_PRICE_COL),
                        cursor.getDouble(Constants.DISH_SALE_PERCENT_COL),
                        LYDateString.stringToDate(cursor.getString(Constants.DISH_SALE_EXPIRE_COL), 3),
                        cursor.getString(Constants.DISH_MATERIALS_COL),
                        cursor.getString(Constants.DISH_MEDIA_URL_COL),
                        cursor.getString(Constants.DISH_NUTRITION_COL),
                        cursor.getString(Constants.DISH_INFO_COL),
                        cursor.getString(Constants.DISH_LOCAL_URL_COL),
                        cursor.getInt(Constants.DISH_SCREEN_ID_COL)

                );
                return storeDish;
            }
            catch(Exception e) {
                return null;
            }
        }
    }


    public long insertStoreDish(StoreDish storeDish) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.DISH_ID, storeDish.getId());
        cv.put(Constants.DISH_DELETED, storeDish.isDeleted());
        cv.put(Constants.DISH_UPDATED_TS,LYDateString.dateToString(storeDish.getUpdatedTs(), 3));
        cv.put(Constants.DISH_CREATED_TS,LYDateString.dateToString(storeDish.getCreatedTs(), 3));
        cv.put(Constants.DISH_NAME, storeDish.getName());
        cv.put(Constants.DISH_STORE_ID, storeDish.getStoreId());
        cv.put(Constants.DISH_STORE_TYPE_ID, storeDish.getStoreTypeId());
        cv.put(Constants.DISH_LOCAL_ID, storeDish.getLocalId());
        cv.put(Constants.DISH_SEQUENCE, storeDish.getSequence());
        cv.put(Constants.DISH_TYPE, storeDish.getType());
        cv.put(Constants.DISH_IS_SPECIALTY, storeDish.isSpecialty());
        cv.put(Constants.DISH_IS_DEAL, storeDish.isDeal());
        cv.put(Constants.DISH_IS_NEW, storeDish.isNew());
        cv.put(Constants.DISH_PRICE, storeDish.getPrice());
        cv.put(Constants.DISH_SALE_PERCENT, storeDish.getSalePercent());
        cv.put(Constants.DISH_SALE_EXPIRE, LYDateString.dateToString(storeDish.getSaleExpire(), 3));
        cv.put(Constants.DISH_MATERIALS, storeDish.getInfoOne());
        cv.put(Constants.DISH_MEDIA_URL, storeDish.getMediaUrl());
        cv.put(Constants.DISH_NUTRITION, storeDish.getInfoTwo());
        cv.put(Constants.DISH_INFO, storeDish.getInfoThree());
        cv.put(Constants.DISH_LOCAL_URL, storeDish.getMediaLocalUrl());
        cv.put(Constants.DISH_SCREEN_ID, storeDish.getScreenId());

        long rowID;

        String where = Constants.DISH_TABLE+ "= ?";
        String[] whereArgs = { String.valueOf(storeDish.getId())};

        StoreDish storedishFromOriginalDB = getStoreDishById(storeDish.getId());
        if(storedishFromOriginalDB == null){
            this.openWritableDB();
            rowID = db.insert(Constants.DISH_TABLE, null, cv);
            Log.v("inserted dish to DB","inserted "+ storeDish + "into row "+rowID);
            this.closeDB();
        }else {
            this.openWritableDB();
            rowID = db.update(Constants.DISH_TABLE, cv, where, whereArgs);
            Log.v("dish updated: ", storeDish + "has been updated to the database in row " + rowID);
            this.closeDB();
        }



        return rowID;
    }

    public int updateStoreDish(StoreDish storeDish) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.DISH_ID, storeDish.getId());
        cv.put(Constants.DISH_DELETED, storeDish.isDeleted());
        cv.put(Constants.DISH_UPDATED_TS,LYDateString.dateToString(storeDish.getUpdatedTs(), 3));
        cv.put(Constants.DISH_CREATED_TS,LYDateString.dateToString(storeDish.getCreatedTs(), 3));
        cv.put(Constants.DISH_NAME, storeDish.getName());
        cv.put(Constants.DISH_STORE_ID, storeDish.getStoreId());
        cv.put(Constants.DISH_STORE_TYPE_ID, storeDish.getStoreTypeId());
        cv.put(Constants.DISH_LOCAL_ID, storeDish.getLocalId());
        cv.put(Constants.DISH_SEQUENCE, storeDish.getSequence());
        cv.put(Constants.DISH_TYPE, storeDish.getType());
        cv.put(Constants.DISH_IS_SPECIALTY, storeDish.isSpecialty());
        cv.put(Constants.DISH_IS_DEAL, storeDish.isDeal());
        cv.put(Constants.DISH_IS_NEW, storeDish.isNew());
        cv.put(Constants.DISH_PRICE, storeDish.getPrice());
        cv.put(Constants.DISH_SALE_PERCENT, storeDish.getSalePercent());
        cv.put(Constants.DISH_SALE_EXPIRE, LYDateString.dateToString(storeDish.getSaleExpire(), 3));
        cv.put(Constants.DISH_MATERIALS, storeDish.getInfoOne());
        cv.put(Constants.DISH_MEDIA_URL, storeDish.getMediaUrl());
        cv.put(Constants.DISH_NUTRITION, storeDish.getInfoTwo());
        cv.put(Constants.DISH_INFO, storeDish.getInfoThree());
        cv.put(Constants.DISH_SCREEN_ID, storeDish.getScreenId());
        String where = Constants.DISH_ID + "= ?";
        String[] whereArgs = { String.valueOf(storeDish.getId()) };

        StoreDish storedishFromOriginalDB = getStoreDishById(storeDish.getId());
        int rowCount = 0;
        if(storedishFromOriginalDB == null){
            insertStoreDish(storeDish);
        }else{
            this.openWritableDB();
            rowCount = db.update(Constants.DISH_TABLE, cv, where, whereArgs);
            this.closeDB();
        }

        return rowCount;
    }

    public int deleteStoreDish(long id) {
        String where = Constants.DISH_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWritableDB();
        int rowCount = db.delete(Constants.DISH_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }
}
