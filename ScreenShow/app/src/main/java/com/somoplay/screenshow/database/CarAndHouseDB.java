package com.somoplay.screenshow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.CarAndHouse;
import com.somoplay.screenshow.util.LYDateString;

import java.util.ArrayList;

/**
 * Created by JudyShuai on 15-08-24.
 */
public class CarAndHouseDB {
    private int size;
    private int itemFinished;

    // database object and database helper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public CarAndHouseDB(Context context) {
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
    public ArrayList<CarAndHouse> getCarAndHousees() {
        String where =  Constants.CARHOUSE_DELETED + "= ?";
        String[] whereArgs = {"0"};

        this.openReadableDB();
        Cursor cursor = db.query(Constants.CARHOUSE_TABLE, null,
                null, null,
                null, null, null);
        ArrayList<CarAndHouse> CarAndHousees = new ArrayList<CarAndHouse>();

        while (cursor.moveToNext()) {
            CarAndHousees.add(getCarAndHouseFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return CarAndHousees;
    }



    public ArrayList<CarAndHouse> getCarAndHouseesByStoreId(String storeId) {
        String where = Constants.CARHOUSE_STORE_ID + "= ?" + Constants.CARHOUSE_DELETED + "= ?";
        String[] whereArgs = {storeId,"0"};

        this.openReadableDB();
        Cursor cursor = db.query(Constants.CARHOUSE_TABLE, null,
                null, null,
                null, null, null);
        ArrayList<CarAndHouse> CarAndHousees = new ArrayList<CarAndHouse>();

        while (cursor.moveToNext()) {
            CarAndHousees.add(getCarAndHouseFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return CarAndHousees;
    }



    public ArrayList<CarAndHouse> getCarAndHouseByStoreTypeId(long typeId, int storeId) {
        String where = Constants.CARHOUSE_STORE_TYPE_ID + "= ?" +
                " AND " + Constants.CARHOUSE_STORE_ID + "= ?" +
                " AND " + Constants.CARHOUSE_DELETED + "= ?";
        String[] whereArgs = { String.valueOf(typeId), String.valueOf(storeId), "0"};
        this.openReadableDB();
        Cursor cursor = db.query(Constants.CARHOUSE_TABLE, null,
                where, whereArgs,
                null, null, null);

        ArrayList<CarAndHouse> CarAndHousees = new ArrayList<CarAndHouse>();
        while (cursor.moveToNext()) {
            CarAndHousees.add(getCarAndHouseFromCursor(cursor));
        }

        if (cursor != null)
            cursor.close();
        this.closeDB();
        return CarAndHousees;
    }



    public ArrayList<CarAndHouse> getSpecificCarAndHouseFromDB(int whichKind, int storeId) {

        String where = "";

        if(whichKind == Constants.KEY_IS_KEY_ONE){
            where = Constants.CARHOUSE_IS_KEY_ONE + "= ?" +
                    " AND " + Constants.CARHOUSE_STORE_ID + "= ?" +
                    " AND " + Constants.CARHOUSE_DELETED + "= ?";
        }

        else if(whichKind == Constants.KEY_IS_KEY_TWO){
            where = Constants.CARHOUSE_IS_KEY_TWO + "= ?" +
                    " AND " + Constants.CARHOUSE_STORE_ID + "= ?" +
                    " AND " + Constants.CARHOUSE_DELETED + "= ?";
        }

        else if(whichKind == Constants.KEY_IS_KEY_THREE){
            where = Constants.CARHOUSE_IS_KEY_THREE + "= ?" +
                    " AND " + Constants.CARHOUSE_STORE_ID + "= ?" +
                    " AND " + Constants.CARHOUSE_DELETED + "= ?";
        }

        String[] whereArgs = { "1", String.valueOf(storeId), "0"};
        this.openReadableDB();
        Cursor cursor = db.query(Constants.CARHOUSE_TABLE, null,
                where, whereArgs,
                null, null, null);

        ArrayList<CarAndHouse> carAndHouses = new ArrayList<>();

        while (cursor.moveToNext()) {
            carAndHouses.add(getCarAndHouseFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return carAndHouses;
    }


    public CarAndHouse getCarAndHouseById(long id) {
        String where = Constants.CARHOUSE_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        // handle exceptions?
        this.openReadableDB();
        Cursor cursor = db.query(Constants.CARHOUSE_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        CarAndHouse CarAndHouse = getCarAndHouseFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return CarAndHouse;
    }

    private static CarAndHouse getCarAndHouseFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                CarAndHouse CarAndHouse = new CarAndHouse(

                        cursor.getInt(Constants.CARHOUSE_ID_COL),
                        cursor.getInt(Constants.CARHOUSE_DELETED_COL)==1?true:false,
                        LYDateString.stringToDate(cursor.getString(Constants.CARHOUSE_UPDATED_TS_COL), 3),
                        LYDateString.stringToDate(cursor.getString(Constants.CARHOUSE_CREATED_TS_COL), 3),
                        cursor.getString(Constants.CARHOUSE_NAME_COL),
                        cursor.getInt(Constants.CARHOUSE_STORE_ID_COL),
                        cursor.getInt(Constants.CARHOUSE_STORE_TYPE_ID_COL),
                        cursor.getString(Constants.CARHOUSE_LOCAL_ID_COL),
                        cursor.getInt(Constants.CARHOUSE_SEQUENCE_COL),
                        cursor.getInt(Constants.CARHOUSE_TYPE_COL),
                        cursor.getInt(Constants.CARHOUSE_IS_KEY_ONE_COL)==1?true:false,
                        cursor.getInt(Constants.CARHOUSE_IS_KEY_TWO_COL)==1?true:false,
                        cursor.getInt(Constants.CARHOUSE_IS_KEY_THREE_COL)==1?true:false,
                        cursor.getDouble(Constants.CARHOUSE_PRICE_COL),
                        cursor.getDouble(Constants.CARHOUSE_SALE_PERCENT_COL),
                        LYDateString.stringToDate(cursor.getString(Constants.CARHOUSE_SALE_EXPIRE_COL), 3),
                        cursor.getString(Constants.CARHOUSE_INFO_ONE_COL),
                        cursor.getString(Constants.CARHOUSE_INFO_TWO_COL),
                        cursor.getString(Constants.CARHOUSE_INFO_THREE_COL),
                        cursor.getString(Constants.CARHOUSE_MEDIA_URL_COL),
                        cursor.getInt(Constants.CARHOUSE_SCREEN_ID_COL)
                );
                return CarAndHouse;
            }
            catch(Exception e) {
                return null;
            }
        }
    }


    public long insertCarAndHouse(CarAndHouse carAndHouse) {
        ContentValues cv = new ContentValues();

        cv.put(Constants.CARHOUSE_ID, carAndHouse.getStoreItemId());
        cv.put(Constants.CARHOUSE_DELETED, carAndHouse.isDeleted());
        cv.put(Constants.CARHOUSE_UPDATED_TS, LYDateString.dateToString(carAndHouse.getUpdatedTs(), 3));
        cv.put(Constants.CARHOUSE_CREATED_TS, LYDateString.dateToString(carAndHouse.getCreatedTs(), 3));
        cv.put(Constants.CARHOUSE_NAME, carAndHouse.getName());
        cv.put(Constants.CARHOUSE_STORE_ID, carAndHouse.getStoreId());
        cv.put(Constants.CARHOUSE_STORE_TYPE_ID, carAndHouse.getStoreTypeId());
        cv.put(Constants.CARHOUSE_LOCAL_ID, carAndHouse.getLocalId());
        cv.put(Constants.CARHOUSE_SEQUENCE, carAndHouse.getSequence());
        cv.put(Constants.CARHOUSE_TYPE, carAndHouse.getType());
        cv.put(Constants.CARHOUSE_IS_KEY_ONE, carAndHouse.isKeyOne());
        cv.put(Constants.CARHOUSE_IS_KEY_TWO, carAndHouse.isKeyTwo());
        cv.put(Constants.CARHOUSE_IS_KEY_THREE, carAndHouse.isKeyThree());
        cv.put(Constants.CARHOUSE_PRICE, carAndHouse.getPrice());
        cv.put(Constants.CARHOUSE_SALE_PERCENT, carAndHouse.getSalePercent());
        cv.put(Constants.CARHOUSE_SALE_EXPIRE, LYDateString.dateToString(carAndHouse.getSaleExpire(), 3));
        cv.put(Constants.CARHOUSE_INFO_ONE, carAndHouse.getInfoOne());
        cv.put(Constants.CARHOUSE_INFO_TWO, carAndHouse.getInfoTwo());
        cv.put(Constants.CARHOUSE_INFO_THREE, carAndHouse.getInfoThree());
        cv.put(Constants.CARHOUSE_MEDIA_URL, carAndHouse.getMediaUrl());
        cv.put(Constants.CARHOUSE_SCREEN_ID, carAndHouse.getScreenId());

        //putInfoIntoTable(cv, carAndHouse);

        long rowID;
        String where = Constants.CARHOUSE_ID + "= ?";
        String[] whereArgs = { String.valueOf(carAndHouse.getStoreItemId())};

        CarAndHouse carAndHouseFromOriginalDB = getCarAndHouseById(carAndHouse.getStoreItemId());
        if(carAndHouseFromOriginalDB == null){
            this.openWritableDB();
            rowID = db.insert(Constants.CARHOUSE_TABLE, null, cv);
            Log.v("inserted carAndHouse DB","inserted "+ carAndHouse + "into row "+rowID);
            this.closeDB();
        }else {
            this.openWritableDB();
            rowID = db.update(Constants.CARHOUSE_TABLE, cv, where, whereArgs);
            Log.v("CarAndHouse updated: ", carAndHouse + "has been updated to the database in row " + rowID);
            this.closeDB();
        }

        return rowID;
    }
    
    

    public int updateCarAndHouse(CarAndHouse carAndHouse) {
        ContentValues cv = new ContentValues();
        
        //putInfoIntoTable(cv, carAndHouse);

        cv.put(Constants.CARHOUSE_ID, carAndHouse.getStoreItemId());
        cv.put(Constants.CARHOUSE_DELETED, carAndHouse.isDeleted());
        cv.put(Constants.CARHOUSE_UPDATED_TS, LYDateString.dateToString(carAndHouse.getUpdatedTs(), 3));
        cv.put(Constants.CARHOUSE_CREATED_TS, LYDateString.dateToString(carAndHouse.getCreatedTs(), 3));
        cv.put(Constants.CARHOUSE_NAME, carAndHouse.getName());
        cv.put(Constants.CARHOUSE_STORE_ID, carAndHouse.getStoreId());
        cv.put(Constants.CARHOUSE_STORE_TYPE_ID, carAndHouse.getStoreTypeId());
        cv.put(Constants.CARHOUSE_LOCAL_ID, carAndHouse.getLocalId());
        cv.put(Constants.CARHOUSE_SEQUENCE, carAndHouse.getSequence());
        cv.put(Constants.CARHOUSE_TYPE, carAndHouse.getType());
        cv.put(Constants.CARHOUSE_IS_KEY_ONE, carAndHouse.isKeyOne());
        cv.put(Constants.CARHOUSE_IS_KEY_TWO, carAndHouse.isKeyTwo());
        cv.put(Constants.CARHOUSE_IS_KEY_THREE, carAndHouse.isKeyThree());
        cv.put(Constants.CARHOUSE_PRICE, carAndHouse.getPrice());
        cv.put(Constants.CARHOUSE_SALE_PERCENT, carAndHouse.getSalePercent());
        cv.put(Constants.CARHOUSE_SALE_EXPIRE, LYDateString.dateToString(carAndHouse.getSaleExpire(), 3));
        cv.put(Constants.CARHOUSE_INFO_ONE, carAndHouse.getInfoOne());
        cv.put(Constants.CARHOUSE_INFO_TWO, carAndHouse.getInfoTwo());
        cv.put(Constants.CARHOUSE_INFO_THREE, carAndHouse.getInfoThree());
        cv.put(Constants.CARHOUSE_MEDIA_URL, carAndHouse.getMediaUrl());
        cv.put(Constants.CARHOUSE_SCREEN_ID, carAndHouse.getScreenId());
        
        String where = Constants.CARHOUSE_ID + "= ?";
        String[] whereArgs = { String.valueOf(carAndHouse.getStoreItemId()) };

        CarAndHouse CarAndHouseFromOriginalDB = getCarAndHouseById(carAndHouse.getStoreItemId());
        int rowCount = 0;
        if(CarAndHouseFromOriginalDB == null){
            insertCarAndHouse(carAndHouse);
        }else{
            this.openWritableDB();
            rowCount = db.update(Constants.CARHOUSE_TABLE, cv, where, whereArgs);
            this.closeDB();
        }

        return rowCount;
    }
    
    

    public int deleteCarAndHouse(long id) {
        String where = Constants.CARHOUSE_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWritableDB();
        int rowCount = db.delete(Constants.CARHOUSE_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }
    
    
    
    /*public void putInfoIntoTable(ContentValues cv, CarAndHouse carAndHouse){
        cv.put(Constants.CARHOUSE_ID, carAndHouse.getStoreItemId());
        cv.put(Constants.CARHOUSE_DELETED, carAndHouse.isDeleted());
        cv.put(Constants.CARHOUSE_UPDATED_TS, LYDateString.dateToString(carAndHouse.getUpdatedTs(), 3));
        cv.put(Constants.CARHOUSE_CREATED_TS, LYDateString.dateToString(carAndHouse.getCreatedTs(), 3));
        cv.put(Constants.CARHOUSE_NAME, carAndHouse.getName());
        cv.put(Constants.CARHOUSE_STORE_ID, carAndHouse.getStoreId());
        cv.put(Constants.CARHOUSE_STORE_TYPE_ID, carAndHouse.getStoreTypeId());
        cv.put(Constants.CARHOUSE_LOCAL_ID, carAndHouse.getLocalId());
        cv.put(Constants.CARHOUSE_SEQUENCE, carAndHouse.getSequence());
        cv.put(Constants.CARHOUSE_TYPE, carAndHouse.getType());
        cv.put(Constants.CARHOUSE_IS_KEY_ONE, carAndHouse.isKeyOne());
        cv.put(Constants.CARHOUSE_IS_KEY_TWO, carAndHouse.isKeyTwo());
        cv.put(Constants.CARHOUSE_IS_KEY_THREE, carAndHouse.isKeyThree());
        cv.put(Constants.CARHOUSE_PRICE, carAndHouse.getPrice());
        cv.put(Constants.CARHOUSE_SALE_PERCENT, carAndHouse.getSalePercent());
        cv.put(Constants.CARHOUSE_SALE_EXPIRE, LYDateString.dateToString(carAndHouse.getSaleExpire(), 3));
        cv.put(Constants.CARHOUSE_INFO_ONE, carAndHouse.getInfoOne());
        cv.put(Constants.CARHOUSE_INFO_TWO, carAndHouse.getInfoTwo());
        cv.put(Constants.CARHOUSE_INFO_THREE, carAndHouse.getInfoThree());
        cv.put(Constants.CARHOUSE_MEDIA_URL, carAndHouse.getMediaUrl());
        cv.put(Constants.CARHOUSE_SCREEN_ID, carAndHouse.getScreenId());
    }*/
}
