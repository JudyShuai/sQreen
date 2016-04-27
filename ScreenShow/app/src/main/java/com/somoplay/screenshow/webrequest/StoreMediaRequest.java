package com.somoplay.screenshow.webrequest;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.somoplay.screenshow.activity.DownloadActivity;
import com.somoplay.screenshow.activity.ShowExampleActivity;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.MediaDB;
import com.somoplay.screenshow.model.Media;
import com.somoplay.screenshow.util.LYDateString;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shaohua Mao on 2015-07-23.
 */
public class StoreMediaRequest {

    private Context context=null;
    private DownloadActivity downloadActivity = null;
    private SubtitleListRequest subtitleListRequest;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private static final String url = "http://www.eadate.com:8080/screenshow/restful/media/search";
    private String jsonString;
    private String upDatedJsonString;
    private String elementId;
    private String lastUpdatedTs;
    private ArrayList<Media> mediaArrayList;
    private ArrayList<Media> updatedMediaArrayList;
    private MediaDB mediaDB;

    private final String TAG="AdministrateActivity";
    private boolean isUpdate;
    private int totalCount;
    private int counter;

    public StoreMediaRequest(DownloadActivity downloadActivity, Context context){
        super();
        this.downloadActivity = downloadActivity;
        this.context = context;
        subtitleListRequest = new SubtitleListRequest(downloadActivity, context);
        mediaDB=new MediaDB(downloadActivity);
    }



    public ArrayList<Media> generateMediasFromJsonArray(String jsonString) {
        ArrayList<Media> newMediaArray = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            int length = jsonArray.length();

            for (int i = 0; i < length; i++) {
                JSONObject dataObj = jsonArray.getJSONObject(i);
                int id = dataObj.getInt("media_id");
                Boolean dataDeleted = dataObj.getInt("deleted") == 1 ? true : false;
                String dataUpdatedTs = dataObj.getString("updated_ts");
                String dataCreatedTs = dataObj.getString("created_ts");
                String mediaName = dataObj.getString("media_name");
                int adminId = dataObj.getInt("admin_id");
                int userId = dataObj.getInt("user_id");
                String userName = dataObj.getString("user_name");
                String startDate;
                String endDate;
                if(dataObj.has("start_date")) {
                    startDate = dataObj.getString("start_date");
                }else {
                    startDate = "2014-03-10 12:00:00.0";
                }
                if(dataObj.has("end_date")) {
                    endDate = dataObj.getString("end_date");
                }else {
                    endDate = "2024-03-10 12:00:00.0";
                }
                int showType = dataObj.getInt("show_type");
                int statusB = dataObj.getInt("status");
                int mediaType = dataObj.getInt("media_type");
                int elementId = dataObj.getInt("element_id");
                String mediaUrl = dataObj.getString("media_url");
                int durationSec = dataObj.getInt("duration_sec");
                double version = dataObj.getDouble("version");

                int screenMediaId = 0;
                int screenId = 0;
                Boolean deleted = false;
                String updatedTs = "2000-01-01 00:00:00.0";
                String createdTs = "2000-01-01 00:00:00.0";
                int sequenceId = 0;
                int duration = 0;

                Media media = new Media(id, dataDeleted, LYDateString.stringToDate(dataUpdatedTs, 3),
                        LYDateString.stringToDate(dataCreatedTs, 3), mediaName, adminId, userId, userName,
                        LYDateString.stringToDate(startDate, 3),LYDateString.stringToDate(endDate, 3),
                        showType, statusB, mediaType, elementId, mediaUrl, durationSec, version, "",
                        screenMediaId, screenId, deleted, LYDateString.stringToDate(updatedTs, 3),
                        LYDateString.stringToDate(createdTs, 3), sequenceId, duration);

                newMediaArray.add(media);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, new String(" There are " + newMediaArray.size() + " pictures"));

        downloadActivity.fileSize = newMediaArray.size();
        return newMediaArray;
    }



    public void sendStringRequest(final String elementId) {
        this.elementId = elementId;
        isUpdate = false;

        StringRequest postReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        jsonString=response;
                        new ParseJson().execute();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("+++++++++++++++++++++++++++++++Error ["+error+"]");
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("searchtype", "searchByTypeAndElementId");
                params.put("pageIndex", "0");
                params.put("pageSize", "1000");
                params.put("media_type", "3");
                params.put("element_id", elementId);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReq);
    }



    class ParseJson extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if(isUpdate){
                updatedMediaArrayList = generateMediasFromJsonArray(upDatedJsonString);
            }else {
                mediaArrayList = generateMediasFromJsonArray(jsonString);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(TAG, new String(totalCount + " pictures have been saved." + mediaArrayList.get(totalCount).getMediaName()));
            new DownloadImages().execute();
        }
    }



    class DownloadImages extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if(isUpdate){
                sendImageRequest(updatedMediaArrayList.get(totalCount));
                //downloadActivity.fileSize=updatedMediaArrayList.size();
                //downloadActivity.downLoadFileSize=0;
            } else {
                sendImageRequest(mediaArrayList.get(totalCount));
               //downloadActivity.fileSize=mediaArrayList.size();
                //downloadActivity.downLoadFileSize=0;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(!isUpdate){
                if(downloadActivity != null){
                    downloadActivity.progressBar.setVisibility(View.VISIBLE);
                }
            }
        }
    }



    public void sendImageRequest(Media media){
        String picsName= media.getMediaUrl();
        String imageUrl = "http://eadate.com/screenshow/uploads/images/store/"+picsName;

        final String tempImageName = picsName;
        final Media tempMedia = media;
        ImageRequest ir = new ImageRequest(imageUrl , new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response){
                SaveImages saveImages = new SaveImages(response,tempImageName,tempMedia);
                saveImages.execute();
            }

        },  Constants.IMAGE_MAX_WIDTH, Constants.IMAGE_MAX_HEIGHT, Bitmap.Config.RGB_565,
                new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d(TAG, "Missing one pic:" + mediaArrayList.get(totalCount).getMediaName());
                if(totalCount < mediaArrayList.size() && downloadActivity.isDownload == true){
                    totalCount++;
                    String logString = new String(totalCount+" pictures have been saved." +
                            mediaArrayList.get(totalCount).getMediaName());
                    Log.d(TAG, logString);
                    new DownloadImages().execute();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(ir);
    }



    class SaveImages extends AsyncTask<Void, Void, Void> {
        Bitmap image;
        String picsName;
        String imagePath;
        Media media;

        public SaveImages(Bitmap image, String picsName, Media media) {
            super();
            this.image = image;
            this.picsName = picsName;
            this.media = media;
        }

        @Override
        protected Void doInBackground(Void... params) {
            imagePath = saveImageInternalMemory(image, picsName);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("News reader", "Feed downloaded");
            saveToDatabase(media, imagePath);
        }
    }



    public String saveImageInternalMemory (Bitmap bitmapImage, String picsName){
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File carAndHouseDir = new File(directory, "store");
        carAndHouseDir.mkdir();

        File mypath=new File(carAndHouseDir, picsName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public int getTotalCount(){
        return totalCount;
    }

    public void  saveToDatabase(Media media, String imagePath)
    {
        media.setMediaLocalUrl(imagePath);
        int size;

        if(isUpdate){
            mediaDB.updateMedia(media);
            //downloadActivity.downLoadFileSize = downloadActivity.downLoadFileSize +1;
            downloadActivity.sendMsg(4);
            size = updatedMediaArrayList.size();

            if(totalCount<size-1) {
                totalCount++;

                String logString = new String(totalCount+" pictures have been updated." +
                        updatedMediaArrayList.get(totalCount).getMediaUrl());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }else{
                totalCount = 0;

                if(size > 0){
                    int currentActivity = sharedPref.getInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);

                    sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                    boolean isAdvertisement = sharedPref.getBoolean(Constants.KEY_IS_ADVERTISEMENT, false);

                    if(currentActivity == Constants.CURRENT_MEDIA_SHOW_ACTIVITY && isAdvertisement == false){
                        AppController.getInstance().getGeneralActivity().refreshActivityContent();
                    }
                }

                /*String sutitleUpdateTs = sharedPref.getString(Constants.KEY_SUBTITLES_UPDATE_TS, "2015-02-17 23:29:38.0");
                subtitleListRequest.updateSubtitleRequest("1", sutitleUpdateTs);*/

            }

        }else{
            mediaDB.updateMedia(media);
            //downloadActivity.downLoadFileSize = downloadActivity.downLoadFileSize +1;
            downloadActivity.sendMsg(4);
            size = mediaArrayList.size();

            if(totalCount < size-1 && ((downloadActivity!= null && downloadActivity.isDownload == true) || ShowExampleActivity.isDownload == true))
            {
                totalCount++;
                String logString = new String(totalCount+" pictures have been saved." +
                        mediaArrayList.get(totalCount).getMediaUrl());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }
            else {
                if(downloadActivity != null){
                    downloadActivity.turnOnAllButtons();
                }
                downloadActivity.sendMsg(2);
                //Toast.makeText(context, "Data is already set up!", Toast.LENGTH_LONG).show();
                totalCount = 0;
            }
        }
    }



    public void mediaUpdateRequest(final String elementId, final String lastUpdatedTs) {
        this.elementId = elementId;
        this.lastUpdatedTs = lastUpdatedTs;
        isUpdate = true;

        StringRequest postReqToUpdateDish = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        upDatedJsonString = response;
                        new ParseJsonAndUpdateDB().execute();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error ["+error+"]");
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("searchtype", "searchByTypeAndElementId");
                params.put("pageIndex", "0");
                params.put("pageSize", "1000");
                params.put("media_type", "3");
                params.put("element_id", elementId);
                params.put("updated_ts", lastUpdatedTs);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReqToUpdateDish);
    }

    class ParseJsonAndUpdateDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            updatedMediaArrayList = generateMediasFromJsonArray(upDatedJsonString);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(updatedMediaArrayList.size()>0){
                sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                editor = sharedPref.edit();
                int showContentType = sharedPref.getInt(Constants.KEY_SHOW_CONTENT_TYPE, 1);
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();

                if(showContentType == 4){
                    String newStoreMediaUpdateTs = LYDateString.dateToString(currentDate, 3);
                    editor.putString(Constants.KEY_STORE_MEDIAS_UPDATE_TS, newStoreMediaUpdateTs);
                    editor.commit();
                }else if(showContentType == 5){
                    String newOfficeMediaUpdateTs = LYDateString.dateToString(currentDate, 3);
                    editor.putString(Constants.KEY_OFFICE_MEDIAS_UPDATE_TS, newOfficeMediaUpdateTs);
                    editor.commit();
                }

                String logString = new String(totalCount+" pictures have been updated." +
                        updatedMediaArrayList.get(totalCount).getMediaUrl());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }
        }
    }
}
