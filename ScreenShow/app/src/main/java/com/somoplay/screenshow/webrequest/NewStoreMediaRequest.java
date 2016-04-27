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
import com.somoplay.screenshow.database.StoreMediaDB;
import com.somoplay.screenshow.model.StoreMedia;
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
 * Created by ann on 2015-11-24.
 */
public class NewStoreMediaRequest {
    private Context context=null;
    private DownloadActivity downloadActivity = null;
    private SubtitleListRequest subtitleListRequest;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private static final String url = "http://www.eadate.com:8080/screenshow/restful/media/search";
    private String jsonString;
    private String upDatedJsonString;
    //private String elementId;
    private String adminId;
    //private String lastUpdatedTs;
    private String lastUpdatedTs = "1982-11-27 12:12:12";
    private ArrayList<StoreMedia> mediaArrayList;
    private ArrayList<StoreMedia> updatedMediaArrayList;
    private MediaDB mediaDB;
    private StoreMediaDB storeMediaDB;

    private final String TAG="AdministrateActivity";
    private boolean isUpdate;
    private int totalCount;
    //private int counter;

    public NewStoreMediaRequest(DownloadActivity downloadActivity, Context context){
        super();
        this.downloadActivity = downloadActivity;
        this.context = context;
        subtitleListRequest = new SubtitleListRequest(downloadActivity, context);
        mediaDB=new MediaDB(downloadActivity);
        storeMediaDB = new StoreMediaDB(downloadActivity);
    }



    public ArrayList<StoreMedia> generateMediasFromJsonArray(String jsonString) {
        ArrayList<StoreMedia> newMediaArray = new ArrayList<>();
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
                int showType = dataObj.getInt("show_type");
                int status = dataObj.getInt("status");
                int mediaType = dataObj.getInt("media_type");
                int elementId = dataObj.getInt("element_id");
                String mediaUrl = dataObj.getString("media_url");
                int durationSec = dataObj.getInt("duration_sec");
                double version = dataObj.getDouble("version");

                StoreMedia storeMedia = new StoreMedia(version, durationSec, id, dataDeleted, LYDateString.stringToDate(dataUpdatedTs, 3),
                        LYDateString.stringToDate(dataCreatedTs, 3), adminId, userId, userName,  mediaName, showType, status, mediaType,
                        elementId, mediaUrl);

                newMediaArray.add(storeMedia);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, " There are " + newMediaArray.size() + " pictures");

        return newMediaArray;
    }



    public void sendStringRequest(final String adminId) {
        this.adminId = adminId;
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
                params.put("searchtype", "searchNewMediaForStoreByAdminId");
                params.put("updated_ts", "1982-11-27 12:12:12");
                params.put("admin_id", adminId);

                /*params.put("searchtype", "searchByTypeAndElementId");
                params.put("pageIndex", "0");
                params.put("pageSize", "1000");
                params.put("media_type", "3");
                params.put("element_id", elementId);*/
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
            Log.d(TAG, totalCount + " pictures have been saved." + mediaArrayList.get(totalCount).getMediaName());
            new DownloadImages().execute();
        }
    }



    class DownloadImages extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if(isUpdate){
                sendImageRequest(updatedMediaArrayList.get(totalCount));
            } else {
                sendImageRequest(mediaArrayList.get(totalCount));
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



    public void sendImageRequest(StoreMedia storeMedia){
        String picsName = storeMedia.getMediaUrl();
        String imageUrl = "";

        if(storeMedia.getMediaType() == 3){
            imageUrl = "http://eadate.com/screenshow/uploads/images/store/"+picsName;
        }else if(storeMedia.getMediaType() == 4){
            imageUrl = "http://eadate.com/screenshow/uploads/images/storeitem/"+picsName;
        }

        final String tempImageName = picsName;
        final StoreMedia tempMedia = storeMedia;
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
                        if(totalCount < mediaArrayList.size() && downloadActivity.isDownload){
                            Log.d(TAG, "Missing one pic:" + mediaArrayList.get(totalCount).getMediaName());
                            totalCount++;
                            String logString = totalCount + " pictures have been saved." + mediaArrayList.get(totalCount).getMediaName();
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
        StoreMedia media;

        public SaveImages(Bitmap image, String picsName, StoreMedia media) {
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

    public void  saveToDatabase(StoreMedia media, String imagePath)
    {
        int size;

        if(isUpdate){
            storeMediaDB.updateStoreMedia(media);
            size = updatedMediaArrayList.size();

            if(totalCount<size-1) {
                totalCount++;

                String logString = totalCount + " pictures have been updated." +
                        updatedMediaArrayList.get(totalCount).getMediaUrl();
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }else{
                totalCount = 0;

                if(size > 0){
                    int currentActivity = sharedPref.getInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);

                    sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                    boolean isAdvertisement = sharedPref.getBoolean(Constants.KEY_IS_ADVERTISEMENT, false);

                    if(currentActivity == Constants.CURRENT_MEDIA_SHOW_ACTIVITY && !isAdvertisement){
                        AppController.getInstance().getGeneralActivity().refreshActivityContent();
                    }
                }

                /*String sutitleUpdateTs = sharedPref.getString(Constants.KEY_SUBTITLES_UPDATE_TS, "2015-02-17 23:29:38.0");
                subtitleListRequest.updateSubtitleRequest("1", sutitleUpdateTs);*/

            }

        }else{
            storeMediaDB.updateStoreMedia(media);
            size = mediaArrayList.size();

            if(totalCount < size-1 && ((downloadActivity!= null && downloadActivity.isDownload) || ShowExampleActivity.isDownload))
            {
                totalCount++;
                String logString = totalCount + " pictures have been saved." +
                        mediaArrayList.get(totalCount).getMediaUrl();
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }
            else {
                if(downloadActivity != null){
                    downloadActivity.turnOnAllButtons();
                }

                Toast.makeText(context, "Data is already set up!", Toast.LENGTH_LONG).show();
                totalCount = 0;
            }
        }
    }



    public void mediaUpdateRequest(final String adminId, final String lastUpdatedTs) {
        this.adminId = adminId;
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
                params.put("searchtype", "searchNewMediaForStoreByAdminId");
                params.put("updated_ts", lastUpdatedTs);
                params.put("admin_id", adminId);
                /*params.put("searchtype", "searchByTypeAndElementId");
                params.put("pageIndex", "0");
                params.put("pageSize", "1000");
                params.put("media_type", "3");
                params.put("element_id", elementId);
                //params.put("updated_ts", lastUpdatedTs);*/
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

/*                if(showContentType == 4){
                    String newStoreMediaUpdateTs = LYDateString.dateToString(currentDate, 3);
                    editor.putString(Constants.KEY_STORE_MEDIAS_UPDATE_TS, newStoreMediaUpdateTs);
                    editor.commit();
                }else if(showContentType == 5){
                    String newOfficeMediaUpdateTs = LYDateString.dateToString(currentDate, 3);
                    editor.putString(Constants.KEY_OFFICE_MEDIAS_UPDATE_TS, newOfficeMediaUpdateTs);
                    editor.commit();
                }*/
                if(showContentType == 6){
                    String newStoreMediaUpdateTs = LYDateString.dateToString(currentDate, 3);
                    editor.putString(Constants.KEY_STORE_MEDIAS_UPDATE_TS, newStoreMediaUpdateTs);
                    editor.commit();
                }

                //String logString = totalCount + " pictures have been updated." + updatedMediaArrayList.get(totalCount).getMediaUrl();
                //Log.d(TAG, logString);
                new DownloadImages().execute();
            }
        }
    }
}

