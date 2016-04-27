package com.somoplay.screenshow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.StoreBranch;
import com.somoplay.screenshow.util.LYDateString;

import java.util.Date;

/**
 * Created by Shaohua Mao on 2015-07-17.
 */
public class StoreBranchDB {
    // database object and database helper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public StoreBranchDB(Context context) {
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
    public StoreBranch getStoreBranchByStoreId(int storeId) {
        this.openReadableDB();
        String where = Constants.BRANCH_STORE_ID + "= ?";
        String[] whereArgs = { String.valueOf(storeId) };

        Cursor cursor = db.query(Constants.BRANCH_TABLE, null,
                where, whereArgs,
                null, null, null);

        boolean isarow = cursor.moveToFirst();
        StoreBranch storeBranch = getstoreBranchFromCursor(cursor);

        if (cursor != null)
            cursor.close();
        this.closeDB();
        return storeBranch;
    }

    private StoreBranch getstoreBranchFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                int storeId = cursor.getInt(Constants.BRANCH_STORE_ID_COL);
                Date updatedTs = LYDateString.stringToDate(cursor.getString(Constants.BRANCH_UPDATED_TS_COL), 3);
                Date createdTs = LYDateString.stringToDate(cursor.getString(Constants.BRANCH_CREATED_TS_COL), 3);
                int adminId = cursor.getInt(Constants.BRANCH_ADMIN_ID_COL);
                int type = cursor.getInt(Constants.BRANCH_TYPE_COL);
                String storeName =  cursor.getString(Constants.BRANCH_STORE_NAME_COL);
                String address = cursor.getString(Constants.BRANCH_ADDRESS_COL);
                String city = cursor.getString(Constants.BRANCH_CITY_COL);
                String province = cursor.getString(Constants.BRANCH_PROVINCE_COL);
                String postCode = cursor.getString(Constants.BRANCH_POSTCODE_COL);
                String email = cursor.getString(Constants.BRANCH_EMAIL_COL);
                String phone0 = cursor.getString(Constants.BRANCH_PHONE0_COL);
                String phone1 = cursor.getString(Constants.BRANCH_PHONE1_COL);
                String phone2 = cursor.getString(Constants.BRANCH_PHONE2_COL);
                String website = cursor.getString(Constants.BRANCH_WEBSITE_COL);
                String mediaUrl = cursor.getString(Constants.BRANCH_MEDIA_URL_COL);
                String backGroundColor = cursor.getString(Constants.BRANCH_BACKGROUND_COLOR_COL);
                String fontColor = cursor.getString(Constants.BRANCH_FONT_COLOR_COL);
                String fontFamilyName = cursor.getString(Constants.BRANCH_FONT_FAMILY_NAME_COL);
                String fontName = cursor.getString(Constants.BRANCH_FONT_NAME_COL);
                int fontHeadSize = cursor.getInt(Constants.BRANCH_FONT_HEAD_SIZE_COL);
                int fontTextSize = cursor.getInt(Constants.BRANCH_FONT_TEXT_SIZE_COL);
                String logoUrl = cursor.getString(Constants.BRANCH_LOGO_URL_COL);
                String info = cursor.getString(Constants.BRANCH_INFO_COL);
                String keyOneName = cursor.getString(Constants.BRANCH_KEY_ONE_NAME_COL);
                String keyTwoName = cursor.getString(Constants.BRANCH_KEY_TWO_NAME_COL);
                String keyThreeName = cursor.getString(Constants.BRANCH_KEY_THREE_NAME_COL);
                String infoOneName = cursor.getString(Constants.BRANCH_INFO_ONE_NAME_COL);
                String infoTwoName = cursor.getString(Constants.BRANCH_INFO_TWO_NAME_COL);
                String infoThreeName = cursor.getString(Constants.BRANCH_INFO_THREE_NAME_COL);
                //int screenId = cursor.getInt(Constants.BRANCH_SCREEN_ID_COL);

                StoreBranch storeBranch = new StoreBranch(storeName,storeId, updatedTs, createdTs,
                        adminId, type, address, city, province, postCode, email, phone0, phone1, phone2, website,
                        mediaUrl,backGroundColor, fontColor, fontFamilyName, fontName, fontHeadSize,
                        fontTextSize, logoUrl, info, keyOneName, keyTwoName, keyThreeName, infoOneName,
                        infoTwoName, infoThreeName);
                return storeBranch;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public long insertStoreBranch(StoreBranch storeBranch) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.BRANCH_STORE_ID, storeBranch.getStoreId());
        cv.put(Constants.BRANCH_UPDATED_TS, LYDateString.dateToString(storeBranch.getUpdatedTs(), 3));
        cv.put(Constants.BRANCH_CREATED_TS, LYDateString.dateToString(storeBranch.getCreatedTs(), 3));
        cv.put(Constants.BRANCH_ADMIN_ID, storeBranch.getAdminId());
        cv.put(Constants.BRANCH_TYPE, storeBranch.getTypeId());
        cv.put(Constants.BRANCH_STORE_NAME, storeBranch.getStoreName());
        cv.put(Constants.BRANCH_ADDRESS, storeBranch.getAddress());
        cv.put(Constants.BRANCH_CITY,storeBranch.getCity());
        cv.put(Constants.BRANCH_PROVINCE,storeBranch.getProvince());
        cv.put(Constants.BRANCH_POSTCODE,storeBranch.getPostcode());
        cv.put(Constants.BRANCH_EMAIL,storeBranch.getEmail());
        cv.put(Constants.BRANCH_PHONE0,storeBranch.getPhone0());
        cv.put(Constants.BRANCH_PHONE1,storeBranch.getPhone1());
        cv.put(Constants.BRANCH_PHONE2,storeBranch.getPhone2());
        cv.put(Constants.BRANCH_WEBSITE,storeBranch.getWebsite());
        cv.put(Constants.BRANCH_MEDIA_URL,storeBranch.getMediaUrl());
        cv.put(Constants.BRANCH_BACKGROUND_COLOR,storeBranch.getBackGroundColor());
        cv.put(Constants.BRANCH_FONT_COLOR,storeBranch.getFontColor());
        cv.put(Constants.BRANCH_FONT_FAMILY_NAME,storeBranch.getFontFamilyName());
        cv.put(Constants.BRANCH_FONT_NAME,storeBranch.getFontName());
        cv.put(Constants.BRANCH_FONT_HEAD_SIZE,storeBranch.getFontHeadSize());
        cv.put(Constants.BRANCH_FONT_TEXT_SIZE,storeBranch.getFontTextSize());
        cv.put(Constants.BRANCH_LOGO_URL,storeBranch.getLogoUrl());
        cv.put(Constants.BRANCH_INFO,storeBranch.getInfo());
        cv.put(Constants.BRANCH_KEY_ONE_NAME,storeBranch.getKeyOneName());
        cv.put(Constants.BRANCH_KEY_TWO_NAME,storeBranch.getKeyTwoName());
        cv.put(Constants.BRANCH_KEY_THREE_NAME,storeBranch.getKeyThreeName());
        cv.put(Constants.BRANCH_INFO_ONE_NAME,storeBranch.getInfoOneName());
        cv.put(Constants.BRANCH_INFO_TWO_NAME,storeBranch.getInfoTwoName());
        cv.put(Constants.BRANCH_INFO_THREE_NAME,storeBranch.getInfoThreeName());
//        cv.put(Constants.BRANCH_SCREEN_ID,storeBranch.getScreenId());

        long rowID;
        String where = Constants.BRANCH_STORE_ID + "= ?";
        String[] whereArgs = { String.valueOf(storeBranch.getStoreId()) };

        StoreBranch storeBranchFromOriginalDB = getStoreBranchByStoreId(storeBranch.getStoreId());

        if(storeBranchFromOriginalDB == null){
            this.openWriteableDB();
            rowID = db.insert(Constants.BRANCH_TABLE, null, cv);
            Log.v("insert sBranch to DB", "inserted "+ storeBranch + "into row "+rowID);
            this.closeDB();
        }else {
            this.openWriteableDB();
            rowID = db.update(Constants.BRANCH_TABLE, cv, where, whereArgs);
            Log.v("subtitle updated: ", storeBranch + "has been updated to the database in row " + rowID);
            this.closeDB();

        }


        return rowID;
    }

    public int updateStoreBranch(StoreBranch storeBranch) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.BRANCH_STORE_ID, storeBranch.getStoreId());
        cv.put(Constants.BRANCH_UPDATED_TS, LYDateString.dateToString(storeBranch.getUpdatedTs(), 3));
        cv.put(Constants.BRANCH_CREATED_TS, LYDateString.dateToString(storeBranch.getCreatedTs(), 3));
        cv.put(Constants.BRANCH_ADMIN_ID, storeBranch.getAdminId());
        cv.put(Constants.BRANCH_TYPE, storeBranch.getTypeId());
        cv.put(Constants.BRANCH_STORE_NAME, storeBranch.getStoreName());
        cv.put(Constants.BRANCH_ADDRESS, storeBranch.getAddress());
        cv.put(Constants.BRANCH_CITY,storeBranch.getCity());
        cv.put(Constants.BRANCH_PROVINCE,storeBranch.getProvince());
        cv.put(Constants.BRANCH_POSTCODE,storeBranch.getPostcode());
        cv.put(Constants.BRANCH_EMAIL,storeBranch.getEmail());
        cv.put(Constants.BRANCH_PHONE0,storeBranch.getPhone0());
        cv.put(Constants.BRANCH_PHONE1,storeBranch.getPhone1());
        cv.put(Constants.BRANCH_PHONE2,storeBranch.getPhone2());
        cv.put(Constants.BRANCH_WEBSITE,storeBranch.getWebsite());
        cv.put(Constants.BRANCH_MEDIA_URL,storeBranch.getMediaUrl());
        cv.put(Constants.BRANCH_BACKGROUND_COLOR,storeBranch.getBackGroundColor());
        cv.put(Constants.BRANCH_FONT_COLOR,storeBranch.getFontColor());
        cv.put(Constants.BRANCH_FONT_FAMILY_NAME,storeBranch.getFontFamilyName());
        cv.put(Constants.BRANCH_FONT_NAME,storeBranch.getFontName());
        cv.put(Constants.BRANCH_FONT_HEAD_SIZE,storeBranch.getFontHeadSize());
        cv.put(Constants.BRANCH_FONT_TEXT_SIZE,storeBranch.getFontTextSize());
        cv.put(Constants.BRANCH_LOGO_URL,storeBranch.getLogoUrl());
        cv.put(Constants.BRANCH_INFO,storeBranch.getInfo());
        cv.put(Constants.BRANCH_KEY_ONE_NAME,storeBranch.getKeyOneName());
        cv.put(Constants.BRANCH_KEY_TWO_NAME,storeBranch.getKeyTwoName());
        cv.put(Constants.BRANCH_KEY_THREE_NAME,storeBranch.getKeyThreeName());
        cv.put(Constants.BRANCH_INFO_ONE_NAME,storeBranch.getInfoOneName());
        cv.put(Constants.BRANCH_INFO_TWO_NAME,storeBranch.getInfoTwoName());
        cv.put(Constants.BRANCH_INFO_THREE_NAME,storeBranch.getInfoThreeName());
        //cv.put(Constants.BRANCH_SCREEN_ID,storeBranch.getScreenId());

        String where = Constants.BRANCH_STORE_ID + "= ?";
        String[] whereArgs = { String.valueOf(storeBranch.getStoreId()) };

        StoreBranch storeBranchFromOriginalDB = getStoreBranchByStoreId(storeBranch.getStoreId());
        int rowCount = 0;
        if(storeBranchFromOriginalDB == null){
            insertStoreBranch(storeBranch);
        }else{
            this.openWriteableDB();
            rowCount = db.update(Constants.BRANCH_TABLE, cv, where, whereArgs);
            this.closeDB();
        }

        return rowCount;
    }

    public int deleteStoreBranch() {
        this.openWriteableDB();
        int rowCount = db.delete(Constants.BRANCH_TABLE, null, null);
        this.closeDB();

        return rowCount;
    }
}
