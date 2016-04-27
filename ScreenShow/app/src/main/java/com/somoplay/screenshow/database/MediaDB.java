package com.somoplay.screenshow.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.Media;
import com.somoplay.screenshow.util.LYDateString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yaolu on 15-06-02.
 */
public class MediaDB {
    private static final String TAG = MediaDB.class.getSimpleName();
    // database object and database helper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private SharedPreferences sharedPref = AppController.getInstance().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
    private String screenId;
    private String currentDate;

    // constructor
    public MediaDB(Context context) {
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

    private Date convertTime(String s){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }


    public ArrayList<Media> getAdvertisementMedias(int screenId) {
        currentDate = sharedPref.getString(Constants.currentTime, "00000");
        currentDate = currentDate.replace(" ", "");
        String sDate = Constants.MEDIA_START_DATE.replaceAll(" ", "");
        String eDate = Constants.MEDIA_END_DATE.replace(" ", "");

            String where = Constants.MEDIA_TYPE + "< ?" + " AND " + Constants.MEDIA_DELETED + "= ?" + " AND "
                    + Constants.MEDIA_DATA_DELETED + "= ?" + " AND " + sDate + "<= ?"
                    + " AND " + "? <= " + eDate + " AND " + Constants.MEDIA_SCREEN_ID +"= ?";
        /*String where = Constants.MEDIA_TYPE + "< ?" + " AND " + Constants.MEDIA_DELETED + "= ?"+ " AND "
                + Constants.MEDIA_DATA_DELETED + "= ?"
                + " AND " + "? <= " + Constants.MEDIA_END_DATE ;*/
            String[] whereArgs = {String.valueOf(Constants.PHOTO_SHOW), "0", "0", currentDate, currentDate, screenId+""};
            //String where = Constants.MEDIA_TYPE + "= ?" + " OR " + Constants.MEDIA_TYPE + "= ?";
            //String[] whereArgs = { Long.toString(Constants.ADS_IMAGE) , Long.toString(Constants.ADS_VIDEO)};

            // handle exceptions?
            this.openReadableDB();
            Cursor cursor = db.query(Constants.MEDIA_TABLE,
                    null, where, whereArgs, null, null, null);
            cursor.moveToFirst();
            ArrayList<Media> Medias = new ArrayList<Media>();
            while (!cursor.isAfterLast()) {
                Media tempMedia = getMediaFromCursor(cursor);
                if (tempMedia.isDeleted() == false) {
                    Medias.add(tempMedia);
                }
                cursor.moveToNext();
            }
            if (cursor != null)
                cursor.close();
            this.closeDB();

        return Medias;
    }



    public ArrayList<Media> getUserDrawingPhotos() {
        //get screen id
        sharedPref  = AppController.getInstance().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        String getScreenName = sharedPref.getString(Constants.KEY_SCREEN_NAME, "screen_name");
        screenId = String.valueOf(sharedPref.getInt(Constants.KEY_SCREEN_ID, 1111111));
//        String where = "";
//        String[] whereArgs = {""};

//        String where = Constants.MEDIA_TYPE + "= ?" + " AND " + Constants.MEDIA_DELETED + "= ?"+ " AND " +Constants.MEDIA_STATUS+ "= ?";
//        String[] whereArgs = { Long.toString(Constants.PHOTO_SHOW) , "0", "1"};

        // just try




       // if(screenId == "4"){

          /*  String where = Constants.MEDIA_TYPE + "= ?" + " AND " + Constants.MEDIA_DATA_DELETED + "= ?"+ " AND " +Constants.MEDIA_STATUS+ "= ?" + " AND " + Constants.MEDIA_SCREEN_ID + "= ?";
            String[] whereArgs = { Long.toString(Constants.PHOTO_SHOW) , "0", "0", screenId };*/
            // status, deleted--> datadeleted has problems
        //conditions for screen 7 have problems
        //get current system time
            currentDate = sharedPref.getString(Constants.currentTime, "00000");

            String  where = Constants.MEDIA_TYPE + "= ?" + " AND " + Constants.MEDIA_DATA_DELETED + "= ?"+
                            " AND " + Constants.MEDIA_DELETED + "= ?"+ " AND " +Constants.MEDIA_STATUS+ "= ?" +
                            " AND " + Constants.MEDIA_SCREEN_ID + "= ?" + " AND " +
                            Constants.MEDIA_START_DATE + "<= ?" + " AND " + "? <= " + Constants.MEDIA_END_DATE ;
            String[] whereArgs = {String.valueOf(Constants.PHOTO_SHOW) , "0" , "0" ,"1", screenId, currentDate, currentDate };


        // handle exceptions?
        this.openReadableDB();
        Cursor cursor = db.query(Constants.MEDIA_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        ArrayList<Media> Medias = new ArrayList<Media>();
        while (!cursor.isAfterLast()) {
            Medias.add(getMediaFromCursor(cursor));
            cursor.moveToNext();
        }

        if (cursor != null)
            cursor.close();
        this.closeDB();

        return Medias;
    }



    public Media getOneMediaByScreenMediaId(long id) {
        currentDate = sharedPref.getString(Constants.currentTime, "00000");

        String where = Constants.MEDIA_SCREEN_MEDIA_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        // handle exceptions?
        this.openReadableDB();
        Cursor cursor = db.query(Constants.MEDIA_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        Media Media = getMediaFromCursor(cursor);
        Log.d("Media updated","get one existing media"+ Media + "from DB");
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return Media;
    }



    public ArrayList<Media> getAllMedias() {
        // handle exceptions?
        this.openReadableDB();
        Cursor cursor = db.query(Constants.MEDIA_TABLE,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        ArrayList<Media> Medias = new ArrayList<Media>();
        while (!cursor.isAfterLast()) {
            Medias.add(getMediaFromCursor(cursor));
            cursor.moveToNext();
        }

        if (cursor != null)
            cursor.close();
        this.closeDB();

        return Medias;
    }



    public ArrayList<Media> getMediaByMediaTypeAndElementId(long mediaType, long elementId) {
        currentDate = sharedPref.getString(Constants.currentTime, "00000");
        String where = Constants.MEDIA_TYPE + "= ?" + " AND " + Constants.MEDIA_ELEMENT_ID + "= ?"
                + " AND " + Constants.MEDIA_DELETED + "= ?" + " AND " + Constants.MEDIA_DATA_DELETED + "= ?" + " AND " +
                Constants.MEDIA_START_DATE + "<= ?" + " AND " + "? <= " + Constants.MEDIA_END_DATE;
        String[] whereArgs = { String.valueOf(mediaType) , String.valueOf(elementId), "0","0", currentDate, currentDate};

        // handle exceptions?
        this.openReadableDB();
        Cursor cursor = db.query(Constants.MEDIA_TABLE,
                null, where, whereArgs, null, null, null);

        cursor.moveToFirst();
        ArrayList<Media> Medias = new ArrayList<Media>();
        while (!cursor.isAfterLast()) {
            Medias.add(getMediaFromCursor(cursor));
            cursor.moveToNext();
        }

        if (cursor != null)
            cursor.close();
        this.closeDB();

        return Medias;
    }




    private static Media getMediaFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Media Media = new Media(
                        cursor.getInt(Constants.MEDIA_ID_COL),
                        cursor.getInt(Constants.MEDIA_DATA_DELETED_COL)==0?false:true,
                        LYDateString.stringToDate(cursor.getString(Constants.MEDIA_DATA_UPDATED_TS_COL),3),
                        LYDateString.stringToDate(cursor.getString(Constants.MEDIA_DATA_CREATED_TS_COL), 3),
                        cursor.getString(Constants.MEDIA_NAME_COL),
                        cursor.getInt(Constants.MEDIA_ADMIN_ID_COL),
                        cursor.getInt(Constants.MEDIA_USER_ID_COL) ,
                        cursor.getString(Constants.MEDIA_USER_NAME_COL),
                        LYDateString.stringToDate(cursor.getString(Constants.MEDIA_START_DATE_COL), 3),
                        LYDateString.stringToDate(cursor.getString(Constants.MEDIA_END_DATE_COL), 3),
                        cursor.getInt(Constants.MEDIA_SHOW_TYPE_COL),
                        cursor.getInt(Constants.MEDIA_STATUS_COL) ,
                        cursor.getInt(Constants.MEDIA_TYPE_COL) ,
                        cursor.getInt(Constants.MEDIA_ELEMENT_ID_COL),
                        cursor.getString(Constants.MEDIA_URL_COL),
                        cursor.getInt(Constants.MEDIA_DURATION_SEC_COL),
                        cursor.getDouble(Constants.MEDIA_VERSION_COL),
                        cursor.getString(Constants.MEDIA_LOCAL_URL_COL),
                        cursor.getInt(Constants.MEDIA_SCREEN_MEDIA_ID_COL) ,
                        cursor.getInt(Constants.MEDIA_SCREEN_ID_COL) ,
                        cursor.getInt(Constants.MEDIA_DELETED_COL)==0?false:true,
                        LYDateString.stringToDate(cursor.getString(Constants.MEDIA_UPDATED_TS_COL), 3),
                        LYDateString.stringToDate(cursor.getString(Constants.MEDIA_CREATED_TS_COL), 3),
                        cursor.getInt(Constants.MEDIA_SEQUENCE_ID_COL),
                        cursor.getInt(Constants.MEDIA_DURATION_COL)
                );
                Log.d("Media updated","get media "+ Media +"From cursor");
                return Media;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public long insertMedia(Media media) {
        ContentValues cv = new ContentValues();

        cv.put(Constants.MEDIA_ID, media.getId());
        cv.put(Constants.MEDIA_DATA_DELETED, media.isDataDeleted());
        cv.put(Constants.MEDIA_DATA_UPDATED_TS, LYDateString.dateToString(media.getDataUpdatedTs(), 3));
        cv.put(Constants.MEDIA_DATA_CREATED_TS, LYDateString.dateToString(media.getDataCreatedTs(), 3));
        cv.put(Constants.MEDIA_NAME, media.getMediaName());
        cv.put(Constants.MEDIA_ADMIN_ID, media.getAdminId());
        cv.put(Constants.MEDIA_USER_ID, media.getUserId());
        cv.put(Constants.MEDIA_USER_NAME, media.getUserName());
        cv.put(Constants.MEDIA_START_DATE, LYDateString.dateToString(media.getStartDate(), 3));
        cv.put(Constants.MEDIA_END_DATE, LYDateString.dateToString(media.getEndDate(), 3));
        cv.put(Constants.MEDIA_SHOW_TYPE, media.getShowType());
        cv.put(Constants.MEDIA_STATUS, media.getStatus());
        cv.put(Constants.MEDIA_TYPE, media.getMediaType());
        cv.put(Constants.MEDIA_ELEMENT_ID, media.getElementId());
        cv.put(Constants.MEDIA_URL, media.getMediaUrl());
        cv.put(Constants.MEDIA_DURATION_SEC, media.getDurationSec());
        cv.put(Constants.MEDIA_VERSION, media.getVersion());
        cv.put(Constants.MEDIA_LOCAL_URL, media.getMediaLocalUrl());
        cv.put(Constants.MEDIA_SCREEN_MEDIA_ID, media.getScreenMediaId());
        cv.put(Constants.MEDIA_SCREEN_ID, media.getScreenId());
        cv.put(Constants.MEDIA_DELETED, media.isDeleted());
        cv.put(Constants.MEDIA_UPDATED_TS, LYDateString.dateToString(media.getUpdatedTs(), 3));
        cv.put(Constants.MEDIA_CREATED_TS, LYDateString.dateToString(media.getCreatedTs(), 3));
        cv.put(Constants.MEDIA_SEQUENCE_ID, media.getSequenceId());
        cv.put(Constants.MEDIA_DURATION, media.getDuration());

        String where = Constants.MEDIA_SCREEN_MEDIA_ID + "= ?";
        String[] whereArgs = { String.valueOf(media.getScreenMediaId()) };

        Media mediaFromOriginalDB = getOneMediaByScreenMediaId(media.getScreenMediaId());

        long rowID = 0;

        if(mediaFromOriginalDB == null) {
            this.openWriteableDB();
            rowID = db.insert(Constants.MEDIA_TABLE, null, cv);
            Log.v("media inserted db: ", media + "has been inserted to the database in row " + rowID);
            this.closeDB();
        }else
        {
            this.openWriteableDB();
            rowID = db.update(Constants.MEDIA_TABLE, cv, where, whereArgs);
            Log.v("media updated db: ", media + "has been updated to the database in row " + rowID);
            this.closeDB();
        }

        return rowID;
    }

    public int updateMedia(Media media) {
        ContentValues cv = new ContentValues();

        cv.put(Constants.MEDIA_ID, media.getId());
        cv.put(Constants.MEDIA_DATA_DELETED, media.isDataDeleted());
        cv.put(Constants.MEDIA_DATA_UPDATED_TS, LYDateString.dateToString(media.getDataUpdatedTs(), 3));
        cv.put(Constants.MEDIA_DATA_CREATED_TS, LYDateString.dateToString(media.getDataCreatedTs(), 3));
        cv.put(Constants.MEDIA_NAME, media.getMediaName());
        cv.put(Constants.MEDIA_ADMIN_ID, media.getAdminId());
        cv.put(Constants.MEDIA_USER_ID, media.getUserId());
        cv.put(Constants.MEDIA_USER_NAME, media.getUserName());
        cv.put(Constants.MEDIA_START_DATE, LYDateString.dateToString(media.getStartDate(), 3));
        cv.put(Constants.MEDIA_END_DATE, LYDateString.dateToString(media.getEndDate(), 3));
        cv.put(Constants.MEDIA_SHOW_TYPE, media.getShowType());
        cv.put(Constants.MEDIA_STATUS, media.getStatus());
        cv.put(Constants.MEDIA_TYPE, media.getMediaType());
        cv.put(Constants.MEDIA_ELEMENT_ID, media.getElementId());
        cv.put(Constants.MEDIA_URL, media.getMediaUrl());
        cv.put(Constants.MEDIA_DURATION_SEC, media.getDurationSec());
        cv.put(Constants.MEDIA_VERSION, media.getVersion());
        cv.put(Constants.MEDIA_LOCAL_URL, media.getMediaLocalUrl());
        cv.put(Constants.MEDIA_SCREEN_MEDIA_ID, media.getScreenMediaId());
        cv.put(Constants.MEDIA_SCREEN_ID, media.getScreenId());
        cv.put(Constants.MEDIA_DELETED, media.isDeleted());
        cv.put(Constants.MEDIA_UPDATED_TS, LYDateString.dateToString(media.getUpdatedTs(), 3));
        cv.put(Constants.MEDIA_CREATED_TS, LYDateString.dateToString(media.getCreatedTs(), 3));
        cv.put(Constants.MEDIA_SEQUENCE_ID, media.getSequenceId());
        cv.put(Constants.MEDIA_DURATION, media.getDuration());

        String where = Constants.MEDIA_SCREEN_MEDIA_ID + "= ?";
        String[] whereArgs = { String.valueOf(media.getScreenMediaId()) };

        Media mediaFromOriginalDB = getOneMediaByScreenMediaId(media.getScreenMediaId());
        int rowCount = 0;
        if(mediaFromOriginalDB == null){
            insertMedia(media);
        }else{
            this.openWriteableDB();
            rowCount = db.update(Constants.MEDIA_TABLE, cv, where, whereArgs);
            Log.v("mediaList updated: ", media + "has been updated to the database in row " + rowCount);
            this.closeDB();
        }

        return rowCount;
    }

    public int deleteMedia(long id) {
        String where = Constants.MEDIA_SCREEN_MEDIA_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(Constants.MEDIA_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

}
