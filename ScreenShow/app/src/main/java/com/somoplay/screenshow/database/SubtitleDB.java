package com.somoplay.screenshow.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.Subtitle;
import com.somoplay.screenshow.util.LYDateString;

import java.util.ArrayList;

/**
 * Created by JudyShuai on 15-06-13.
 */
public class SubtitleDB {
    // database object and database helper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private SharedPreferences sharedPref = AppController.getInstance().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
    private String currentDate;



    // constructor
    public SubtitleDB(Context context) {
        dbHelper = DBHelper.getInstance(context, Constants.DB_NAME,
                null, Constants.DB_VERSION);
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
    public ArrayList<Subtitle> getSubtitles(int screenId) {
        currentDate = sharedPref.getString(Constants.currentTime, "00000");

        currentDate = currentDate.replace(" ", "");
        String sDate = Constants.SUBTITLE_START_DATE.replaceAll(" ", "");
        String eDate = Constants.SUBTITLE_END_DATE.replace(" ", "");

        String where = Constants.SUBTITLE_DELETED + "= ?" + " AND " + Constants.SUBTITLE_DATA_DELETED + "= ?"
                + " AND "+ sDate + "<= ?" + " AND " + "? <= " + eDate + " AND "+ Constants.SUBTITLE_SCREEN_ID + "= ?";
        String[] whereArgs = { "0","0", currentDate,currentDate, screenId+""};

        this.openReadableDB();
        Cursor cursor = db.query(Constants.SUBTITLE_TABLE, null,
                where, whereArgs,
                null, null, null);
        ArrayList<Subtitle> Subtitles = new ArrayList<Subtitle>();
        while (cursor.moveToNext()) {
            Subtitles.add(getSubtitleFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return Subtitles;
    }

    public Subtitle getOneSubtitleByScreenSubtitleId(long id) {      //TO check if this data exists in DB table
        try {
            String where = Constants.SUBTITLE_SCREENSUBTITLE_ID + "= ?";
            String[] whereArgs = {String.valueOf(id)};  //Long.toString(id) may cause NPE

            // handle exceptions?
            this.openReadableDB();
            Cursor cursor = db.query(Constants.SUBTITLE_TABLE,
                    null, where, whereArgs, null, null, null);
            cursor.moveToFirst();
            Subtitle Subtitle = getSubtitleFromCursor(cursor);
            Log.d("Subtitle updated","get an existing subtitle"+ Subtitle + "from DB");
            if (cursor != null)
                cursor.close();
            this.closeDB();


            return Subtitle;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private static Subtitle getSubtitleFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Subtitle Subtitle = new Subtitle(
                        cursor.getInt(Constants.SUBTITLE_SCREENSUBTITLE_ID_COL),
                        cursor.getInt(Constants.SUBTITLE_DELETED_COL)==0?false:true,
                        cursor.getInt(Constants.SUBTITLE_SCREEN_ID_COL),
                        LYDateString.stringToDate(cursor.getString(Constants.SUBTITLE_UPDATED_TS_COL), 3),
                        LYDateString.stringToDate(cursor.getString(Constants.SUBTITLE_CREATED_TS_COL), 3),
                        cursor.getInt(Constants.SUBTITLE_SEQUENCE_ID_COL),
                        cursor.getInt(Constants.SUBTITLE_DURATION_COL),

                        cursor.getInt(Constants.SUBTITLE_ID_COL),
                        cursor.getInt(Constants.SUBTITLE_DATA_DELETED_COL)==0?false:true,
                        LYDateString.stringToDate(cursor.getString(Constants.SUBTITLE_DATA_UPDATED_TS_COL), 3),
                        LYDateString.stringToDate(cursor.getString(Constants.SUBTITLE_DATA_CREATED_TS_COL), 3),
                        cursor.getInt(Constants.SUBTITLE_ADMIN_ID_COL),
                        LYDateString.stringToDate(cursor.getString(Constants.SUBTITLE_START_DATE_COL), 3),
                        LYDateString.stringToDate(cursor.getString(Constants.SUBTITLE_END_DATE_COL), 3),
                        cursor.getInt(Constants.SUBTITLE_SHOW_TYPE_COL),
                        cursor.getInt(Constants.SUBTITLE_DURATION_SEC_COL),
                        cursor.getInt(Constants.SUBTITLE_REPEAT_TIME_COL),
                        cursor.getInt(Constants.SUBTITLE_FONTS_COL),
                        cursor.getString(Constants.SUBTITLE_COLOR_COL),
                        cursor.getInt(Constants.SUBTITLE_LOCATION_COL),
                        cursor.getInt(Constants.SUBTITLE_SPEED_COL),
                        cursor.getString(Constants.SUBTITLE_TEXT_CONTENT_COL)
                );
                Log.d("Subtitle updated","get subtitle"+Subtitle+"From cursor");
                return Subtitle;
            }
            catch(Exception e) {
                return null;
            }
        }
    }


    public long insertSubtitle(Subtitle subtitle) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.SUBTITLE_SCREENSUBTITLE_ID, subtitle.getScreenSubId());
        cv.put(Constants.SUBTITLE_DELETED, subtitle.isDeleted());
        cv.put(Constants.SUBTITLE_SCREEN_ID, subtitle.getScreenId());
        cv.put(Constants.SUBTITLE_UPDATED_TS, LYDateString.dateToString(subtitle.getUpdatedTs(), 3));
        cv.put(Constants.SUBTITLE_CREATED_TS, LYDateString.dateToString(subtitle.getCreatedTs(), 3));
        cv.put(Constants.SUBTITLE_SEQUENCE_ID, subtitle.getSequenceId());
        cv.put(Constants.SUBTITLE_DURATION, subtitle.getDuration());
        cv.put(Constants.SUBTITLE_ID, subtitle.getSubId());
        cv.put(Constants.SUBTITLE_DATA_DELETED, subtitle.isDataDeleted());
        cv.put(Constants.SUBTITLE_DATA_UPDATED_TS, LYDateString.dateToString(subtitle.getDataUpdatedTs(), 3));
        cv.put(Constants.SUBTITLE_DATA_CREATED_TS, LYDateString.dateToString(subtitle.getDataCreatedTs(), 3));
        cv.put(Constants.SUBTITLE_ADMIN_ID,subtitle.getAdminId());
        cv.put(Constants.SUBTITLE_START_DATE, LYDateString.dateToString(subtitle.getStartDate(), 3));
        cv.put(Constants.SUBTITLE_END_DATE, LYDateString.dateToString(subtitle.getEndDate(), 3));
        cv.put(Constants.SUBTITLE_SHOW_TYPE, subtitle.getShowType());
        cv.put(Constants.SUBTITLE_DURATION_SEC, subtitle.getDurationSec());
        cv.put(Constants.SUBTITLE_REPEAT_TIME, subtitle.getRepeatTime());
        cv.put(Constants.SUBTITLE_FONTS,subtitle.getFonts());
        cv.put(Constants.SUBTITLE_COLOR,subtitle.getColor());
        cv.put(Constants.SUBTITLE_LOCATION, subtitle.getLocation());
        cv.put(Constants.SUBTITLE_SPEED, subtitle.getSpeed());
        cv.put(Constants.SUBTITLE_TEXT_CONTENT, subtitle.getTextContent());


        long rowID;

        String where = Constants.SUBTITLE_SCREENSUBTITLE_ID+ "= ?";
        String[] whereArgs = { String.valueOf(subtitle.getScreenSubId())};

        Subtitle subtitleFromOriginalDB = getOneSubtitleByScreenSubtitleId(subtitle.getScreenSubId());
        if(subtitleFromOriginalDB == null){
            this.openWriteableDB();
            rowID = db.insert(Constants.SUBTITLE_TABLE, null, cv);
            Log.v("Subtitle updated","inserted "+ subtitle + "into row "+rowID);
            this.closeDB();
        }else{
            this.openWriteableDB();
            rowID = db.update(Constants.SUBTITLE_TABLE, cv, where, whereArgs);
            Log.v("Subtitle updated", subtitle+ "has been updated to the database in row "+ rowID);
            this.closeDB();
        }


        return rowID;
    }

    public int updateSubtitle(Subtitle subtitle) {
        ContentValues cv = new ContentValues();
        cv.put(Constants.SUBTITLE_SCREENSUBTITLE_ID, subtitle.getScreenSubId());
        cv.put(Constants.SUBTITLE_DELETED, subtitle.isDeleted());
        cv.put(Constants.SUBTITLE_SCREEN_ID, subtitle.getScreenId());
        cv.put(Constants.SUBTITLE_UPDATED_TS, LYDateString.dateToString(subtitle.getUpdatedTs(), 3));
        cv.put(Constants.SUBTITLE_CREATED_TS, LYDateString.dateToString(subtitle.getCreatedTs(), 3));
        cv.put(Constants.SUBTITLE_SEQUENCE_ID, subtitle.getSequenceId());
        cv.put(Constants.SUBTITLE_DURATION, subtitle.getDuration());

        cv.put(Constants.SUBTITLE_ID, subtitle.getSubId());
        cv.put(Constants.SUBTITLE_DATA_DELETED, subtitle.isDataDeleted());
        cv.put(Constants.SUBTITLE_DATA_UPDATED_TS, LYDateString.dateToString(subtitle.getDataUpdatedTs(), 3));
        cv.put(Constants.SUBTITLE_DATA_CREATED_TS, LYDateString.dateToString(subtitle.getDataCreatedTs(), 3));
        cv.put(Constants.SUBTITLE_ADMIN_ID, subtitle.getAdminId());
        cv.put(Constants.SUBTITLE_START_DATE, LYDateString.dateToString(subtitle.getStartDate(), 3));
        cv.put(Constants.SUBTITLE_END_DATE, LYDateString.dateToString(subtitle.getEndDate(), 3));
        cv.put(Constants.SUBTITLE_SHOW_TYPE, subtitle.getShowType());
        cv.put(Constants.SUBTITLE_DURATION_SEC, subtitle.getDurationSec());
        cv.put(Constants.SUBTITLE_REPEAT_TIME, subtitle.getRepeatTime());
        cv.put(Constants.SUBTITLE_FONTS, subtitle.getFonts());
        cv.put(Constants.SUBTITLE_COLOR, subtitle.getColor());
        cv.put(Constants.SUBTITLE_LOCATION, subtitle.getLocation());
        cv.put(Constants.SUBTITLE_SPEED, subtitle.getSpeed());
        cv.put(Constants.SUBTITLE_TEXT_CONTENT, subtitle.getTextContent());

        String where = Constants.SUBTITLE_SCREENSUBTITLE_ID+ "= ?";
        String[] whereArgs = { String.valueOf(subtitle.getScreenSubId())};

        Subtitle subtitleFromOriginalDB = getOneSubtitleByScreenSubtitleId(subtitle.getScreenSubId());
        int rowCount = 0;
        if(subtitleFromOriginalDB == null){
            insertSubtitle(subtitle);
        }else{
            this.openWriteableDB();
            rowCount = db.update(Constants.SUBTITLE_TABLE, cv, where, whereArgs);
            Log.d("Subtitle updated", subtitle+ "has been updated to the database in row "+ rowCount);
            this.closeDB();
        }

        return rowCount;
    }

    public int deleteSubtitle(long id) {
        String where = Constants.SUBTITLE_SCREENSUBTITLE_ID + "= ?" ;
        String[] whereArgs = { String.valueOf(id)};

        this.openWriteableDB();
        int rowCount = db.delete(Constants.SUBTITLE_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }
}
