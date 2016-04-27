package com.somoplay.screenshow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.StoreOffice;
import com.somoplay.screenshow.util.LYDateString;

import java.util.ArrayList;

/**
 * Created by JudyShuai on 15-06-13.
 */
public class StoreOfficeDB {
    // database object and database helper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public StoreOfficeDB(Context context) {
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
    public ArrayList<StoreOffice> getStoreOffices() {
        String where = Constants.STORE_OFFICE_DELETED + "= ?";
        String[] whereArgs = { "0"};

        this.openReadableDB();
        Cursor cursor = db.query(Constants.STORE_OFFICE_TABLE, null,
                null, null,
                null, null, null);
        ArrayList<StoreOffice> StoreOffice = new ArrayList<StoreOffice>();
        while (cursor.moveToNext()) {
            StoreOffice.add(getStoreOfficeFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return StoreOffice;
    }

    public ArrayList<StoreOffice> getStoreOfficeByStoreTypedId(long storeTypedId) {
        String where = Constants.STORE_OFFICE_STORE_TYPE_ID + "= ?" + " AND " + Constants.STORE_OFFICE_DELETED + "= ?";
        String[] whereArgs = { String.valueOf(storeTypedId), "0"};

        // handle exceptions?
        ArrayList<StoreOffice> storeOfficesArray = new ArrayList<>();
        this.openReadableDB();
        Cursor cursor = db.query(Constants.STORE_OFFICE_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        storeOfficesArray.add(getStoreOfficeFromCursor(cursor));
        while (cursor.moveToNext()) {
            storeOfficesArray.add(getStoreOfficeFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return storeOfficesArray;
    }

    private static StoreOffice getStoreOfficeFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                StoreOffice StoreOffice = new StoreOffice(
                        cursor.getInt(Constants.STORE_OFFICE_ID_COL),
                        cursor.getInt(Constants.STORE_OFFICE_DELETED_COL)==1?true:false,
                        LYDateString.stringToDate(cursor.getString(Constants.STORE_OFFICE_UPDATED_TS_COL), 3),
                        LYDateString.stringToDate(cursor.getString(Constants.STORE_OFFICE_CREATED_TS_COL), 3),
                        cursor.getString(Constants.STORE_OFFICE_NAME_COL),
                        cursor.getInt(Constants.STORE_OFFICE_STORE_ID_COL),
                        cursor.getInt(Constants.STORE_OFFICE_STORE_TYPE_ID_COL),
                        cursor.getInt(Constants.STORE_OFFICE_SEQUENCE_COL),
                        cursor.getInt(Constants.STORE_OFFICE_TYPE_COL),
                        cursor.getDouble(Constants.STORE_OFFICE_RENT_COL),
                        cursor.getDouble(Constants.STORE_OFFICE_SIZE_COL),
                        cursor.getString(Constants.STORE_OFFICE_UNIT_COL),
                        cursor.getString(Constants.STORE_OFFICE_EMAIL_COL),
                        cursor.getString(Constants.STORE_OFFICE_PHONE0_COL),
                        cursor.getString(Constants.STORE_OFFICE_PHONE1_COL),
                        cursor.getString(Constants.STORE_OFFICE_PHONE2_COL),
                        cursor.getString(Constants.STORE_OFFICE_WEBSITE_COL),
                        cursor.getString(Constants.STORE_OFFICE_FAX_COL),
                        cursor.getString(Constants.STORE_OFFICE_MEDIA_URL_COL),
                        cursor.getInt(Constants.STORE_OFFICE_IS_ADMINISTRATION_COL)==1?true:false,
                        cursor.getInt(Constants.STORE_OFFICE_IS_RENT_COL)==1?true:false,
                        cursor.getInt(Constants.STORE_OFFICE_IS_WC_COL)==1?true:false,
                        cursor.getInt(Constants.STORE_OFFICE_IS_SALE_COL)==1?true:false,
                        cursor.getInt(Constants.STORE_OFFICE_SALE_PERCENT_COL),
                        LYDateString.stringToDate(cursor.getString(Constants.STORE_OFFICE_SALE_EXPIRE_COL), 3),
                        cursor.getString(Constants.STORE_OFFICE_INFO_COL),
                        cursor.getInt(Constants.STORE_OFFICE_SCREEN_ID_COL)
                );
                return StoreOffice;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public long insertStoreOffice(StoreOffice storeOffice) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.STORE_OFFICE_ID,storeOffice.getId());
        cv.put(Constants.STORE_OFFICE_DELETED, storeOffice.isDeleted());
        cv.put(Constants.STORE_OFFICE_UPDATED_TS, LYDateString.dateToString(storeOffice.getUpdatedTs(), 3));
        cv.put(Constants.STORE_OFFICE_CREATED_TS, LYDateString.dateToString(storeOffice.getCreatedTs(), 3));
        cv.put(Constants.STORE_OFFICE_NAME, storeOffice.getName());
        cv.put(Constants.STORE_OFFICE_STORE_ID, storeOffice.getStoreId());
        cv.put(Constants.STORE_OFFICE_STORE_TYPE_ID, storeOffice.getStoreTypeId());
        cv.put(Constants.STORE_OFFICE_SEQUENCE, storeOffice.getSequence());
        cv.put(Constants.STORE_OFFICE_TYPE,storeOffice.getType());
        cv.put(Constants.STORE_OFFICE_RENT,storeOffice.getRent());
        cv.put(Constants.STORE_OFFICE_SIZE,storeOffice.getSize());
        cv.put(Constants.STORE_OFFICE_UNIT,storeOffice.getUnit());
        cv.put(Constants.STORE_OFFICE_EMAIL,storeOffice.getEmail());
        cv.put(Constants.STORE_OFFICE_PHONE0,storeOffice.getPhone0());
        cv.put(Constants.STORE_OFFICE_PHONE1,storeOffice.getPhone1());
        cv.put(Constants.STORE_OFFICE_PHONE2,storeOffice.getPhone2());
        cv.put(Constants.STORE_OFFICE_WEBSITE,storeOffice.getInfoOne());
        cv.put(Constants.STORE_OFFICE_FAX,storeOffice.getInfoTwo());
        cv.put(Constants.STORE_OFFICE_MEDIA_URL,storeOffice.getMediaUrl());
        cv.put(Constants.STORE_OFFICE_IS_ADMINISTRATION, storeOffice.getIsAdministration());
        cv.put(Constants.STORE_OFFICE_IS_RENT, storeOffice.getIsRent());
        cv.put(Constants.STORE_OFFICE_IS_WC, storeOffice.getIsWc());
        cv.put(Constants.STORE_OFFICE_IS_SALE, storeOffice.getIsSale());
        cv.put(Constants.STORE_OFFICE_SALE_PERCENT, storeOffice.getSalePercent());
        cv.put(Constants.STORE_OFFICE_SALE_EXPIRE, LYDateString.dateToString(storeOffice.getSaleExpire(), 3));
        cv.put(Constants.STORE_OFFICE_INFO,storeOffice.getInfoThree());
        cv.put(Constants.STORE_OFFICE_SCREEN_ID,storeOffice.getScreenId());

        long rowID;
        String where = Constants.STORE_OFFICE_ID+ "= ?";
        String[] whereArgs = { String.valueOf(storeOffice.getId()) };

        StoreOffice storeOfficeFromOriginalDB = getStoreOfficeById(storeOffice.getId());
        if(storeOfficeFromOriginalDB == null) {
            this.openWriteableDB();
            rowID = db.insert(Constants.STORE_OFFICE_TABLE, null, cv);
            Log.v("inserted office to DB","inserted "+ storeOffice + "into row "+rowID);
            this.closeDB();
        }else {
            this.openWriteableDB();
            rowID = db.update(Constants.STORE_OFFICE_TABLE, cv, where, whereArgs);
            Log.v("office updated: ", storeOffice + "has been updated to the database in row " + rowID);
            this.closeDB();
        }

        return rowID;
    }

    public int updateStoreOffice(StoreOffice storeOffice) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.STORE_OFFICE_ID, storeOffice.getId());
        cv.put(Constants.STORE_OFFICE_DELETED, storeOffice.isDeleted());
        cv.put(Constants.STORE_OFFICE_UPDATED_TS, LYDateString.dateToString(storeOffice.getUpdatedTs(), 3));
        cv.put(Constants.STORE_OFFICE_CREATED_TS, LYDateString.dateToString(storeOffice.getCreatedTs(), 3));
        cv.put(Constants.STORE_OFFICE_NAME, storeOffice.getName());
        cv.put(Constants.STORE_OFFICE_STORE_ID, storeOffice.getStoreId());
        cv.put(Constants.STORE_OFFICE_STORE_TYPE_ID, storeOffice.getStoreTypeId());
        cv.put(Constants.STORE_OFFICE_SEQUENCE, storeOffice.getSequence());
        cv.put(Constants.STORE_OFFICE_TYPE, storeOffice.getType());
        cv.put(Constants.STORE_OFFICE_RENT, storeOffice.getRent());
        cv.put(Constants.STORE_OFFICE_SIZE, storeOffice.getSize());
        cv.put(Constants.STORE_OFFICE_UNIT, storeOffice.getUnit());
        cv.put(Constants.STORE_OFFICE_EMAIL,storeOffice.getEmail());
        cv.put(Constants.STORE_OFFICE_PHONE0,storeOffice.getPhone0());
        cv.put(Constants.STORE_OFFICE_PHONE1,storeOffice.getPhone1());
        cv.put(Constants.STORE_OFFICE_PHONE2,storeOffice.getPhone2());
        cv.put(Constants.STORE_OFFICE_WEBSITE,storeOffice.getInfoOne());
        cv.put(Constants.STORE_OFFICE_FAX,storeOffice.getInfoTwo());
        cv.put(Constants.STORE_OFFICE_MEDIA_URL, storeOffice.getMediaUrl());
        cv.put(Constants.STORE_OFFICE_IS_ADMINISTRATION, storeOffice.getIsAdministration());
        cv.put(Constants.STORE_OFFICE_IS_RENT, storeOffice.getIsRent());
        cv.put(Constants.STORE_OFFICE_IS_WC, storeOffice.getIsWc());
        cv.put(Constants.STORE_OFFICE_IS_SALE, storeOffice.getIsSale());
        cv.put(Constants.STORE_OFFICE_SALE_PERCENT, storeOffice.getSalePercent());
        cv.put(Constants.STORE_OFFICE_SALE_EXPIRE, LYDateString.dateToString(storeOffice.getSaleExpire(), 3));
        cv.put(Constants.STORE_OFFICE_INFO,storeOffice.getInfoThree());
        cv.put(Constants.STORE_OFFICE_SCREEN_ID,storeOffice.getScreenId());

        String where = Constants.STORE_OFFICE_ID+ "= ?";
        String[] whereArgs = { String.valueOf(storeOffice.getId()) };

        StoreOffice storeOfficeFromOriginalDB = getStoreOfficeById(storeOffice.getId());
        int rowCount = 0;
        if(storeOfficeFromOriginalDB == null){
            insertStoreOffice(storeOffice);
        }else{
            this.openWriteableDB();
            rowCount = db.update(Constants.STORE_OFFICE_TABLE, cv, where, whereArgs);
            this.closeDB();
        }

        return rowCount;
    }

    public int deleteStoreOffice(long id) {
        String where = Constants.STORE_OFFICE_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(Constants.STORE_OFFICE_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public StoreOffice getStoreOfficeById(long id) {
        String where = Constants.STORE_OFFICE_ID + "= ?" + " AND " + Constants.STORE_OFFICE_DELETED + "= ?";
        String[] whereArgs = { String.valueOf(id), "0"};

        // handle exceptions?
        this.openReadableDB();
        Cursor cursor = db.query(Constants.STORE_OFFICE_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        StoreOffice storeOffice = getStoreOfficeFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return storeOffice;
    }

    public ArrayList<StoreOffice> getSpecificOfficesFromDB(int whichButton) {
        String where = "";

        if(whichButton == Constants.KEY_IS_KEY_ONE){
            where = Constants.STORE_OFFICE_IS_SALE + "= ?" +
                    " AND " + Constants.STORE_OFFICE_DELETED + "= ?";
        }

        else if(whichButton == Constants.KEY_IS_KEY_TWO){
            where = Constants.STORE_OFFICE_IS_ADMINISTRATION + "= ?" +
                    " AND " + Constants.STORE_OFFICE_DELETED + "= ?";
        }

        else if(whichButton == Constants.KEY_IS_KEY_THREE){
            where = Constants.STORE_OFFICE_IS_RENT + "= ?" +
                    " AND " + Constants.STORE_OFFICE_DELETED + "= ?";
        }

        String[] whereArgs = { String.valueOf(1), "0"};
        this.openReadableDB();
        Cursor cursor = db.query(Constants.STORE_OFFICE_TABLE, null,
                where, whereArgs,
                null, null, null);

        ArrayList<StoreOffice> specificOffices = new ArrayList<StoreOffice>();

        while (cursor.moveToNext()) {
            specificOffices.add(getStoreOfficeFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return specificOffices;
    }
}
