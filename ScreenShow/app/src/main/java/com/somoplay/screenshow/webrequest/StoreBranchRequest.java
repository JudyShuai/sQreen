package com.somoplay.screenshow.webrequest;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.somoplay.screenshow.activity.BaseActivity;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.StoreBranchDB;
import com.somoplay.screenshow.model.StoreBranch;
import com.somoplay.screenshow.util.LYDateString;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shaohua Mao on 2015-07-17.
 */
public class StoreBranchRequest {
    private Context context = null;
    private StoreBranch storeBranch;
    private StoreBranchDB storeBranchDB;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private String storeId;
    private String jsonString;
    private String updatedJsonString;
    private String lastUpdatedTs;

    private boolean isUpdate;

    public StoreBranchRequest(Context context) {
        super();
        this.context = context;
        storeBranchDB= new StoreBranchDB(context);
    }

    public StoreBranch generateObjectFromJson(String jsonString) {
        /*sharedPref  = AppController.getInstance().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        int screenId = sharedPref.getInt(Constants.KEY_SCREEN_ID, 1111111);*/
        StoreBranch newStoreBranch = new StoreBranch();

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject dataObj = new JSONObject(jsonObject.getString("data"));

            int storeId = dataObj.getInt("store_id");
            String updatedTs;
            if(dataObj.has("updated_ts")){
                updatedTs = dataObj.getString("updated_ts");
            }
            else {
                updatedTs = "2015-10-26 22:57:44.0";
            }

            String createdTs;
            if(dataObj.has("created_ts")){
                createdTs = dataObj.getString("created_ts");
            }
            else {
                createdTs = "2010-10-25 22:33:11.0";
            }
            int adminId = dataObj.getInt("admin_id");
            int typeId = dataObj.getInt("type");
            String storeName = dataObj.getString("store_name");
            String address = dataObj.getString("address");
            String city = dataObj.getString("city");
            String province = dataObj.getString("province");
            String postcode = dataObj.getString("postcode");
            String email = dataObj.getString("email");
            String phone0 = dataObj.getString("phone0");
            String phone1 = dataObj.getString("phone1");
            String phone2 = dataObj.getString("phone2");
            String website = dataObj.getString("website");
            String mediaUrl = dataObj.getString("media_url");
            String backGroundColor = dataObj.getString("background_color");
            String fontColor = dataObj.getString("font_color");
            String fontFamilyName = dataObj.getString("font_family_name");
            String fontName = dataObj.getString("font_name");
            int fontHeadSize = dataObj.getInt("font_head_size");
            int fontTextSize = dataObj.getInt("font_text_size");
            String logoUrl = dataObj.getString("logo_url");
            String info = dataObj.getString("info");
            String keyOneName = dataObj.getString("key_one_name");
            String keyTwoName = dataObj.getString("key_two_name");
            String keyThreeName = dataObj.getString("key_three_name");
            String infoOneName = dataObj.getString("key_three_name");
            String infoTwoName = dataObj.getString("key_three_name");
            String infoThreeName = dataObj.getString("key_three_name");

            StoreBranch storeBranch = new StoreBranch(storeName, storeId, LYDateString.stringToDate(updatedTs, 3),
                    LYDateString.stringToDate(createdTs, 3),adminId, typeId,  address,
                    city, province, postcode, email, phone0, phone1, phone2, website, mediaUrl, backGroundColor,
                    fontColor, fontFamilyName, fontName, fontHeadSize, fontTextSize, logoUrl, info, keyOneName,
                    keyTwoName, keyThreeName, infoOneName, infoTwoName, infoThreeName);

            newStoreBranch = storeBranch;

            SharedPreferences sharedPref =
                    AppController.getInstance().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString(Constants.KEY_BACKGROUND_COLOR, backGroundColor);

            editor.putString(Constants.KEY_FONT_COLOR, fontColor);
            editor.putString(Constants.KEY_STORE_NAME, storeName);
            editor.putString(Constants.KEY_FONT_NAME, fontName);
            editor.putInt(Constants.KEY_FONT_HEAD_SIZE, fontHeadSize);
            editor.putInt(Constants.KEY_FONT_TEXT_SIZE, fontTextSize);
            editor.commit();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return newStoreBranch;
    }

    public void sendStoreBranchRequest(final String id) {
        this.storeId = id;
        isUpdate = false;

        String searchUrl = "http://www.eadate.com:8080/screenshow/restful/store/search";
        StringRequest postReq = new StringRequest(Request.Method.POST, searchUrl,
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
                    System.out.println("+++++++++++++++++++Error_Right ["+error+"]");
                }
            }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("searchtype", "id_search");
                params.put("id", storeId);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReq);
    }

    class ParseJson extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if(isUpdate){
                storeBranch = generateObjectFromJson(updatedJsonString);
            }else{
                storeBranch = generateObjectFromJson(jsonString);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("News reader", "Feed downloaded");
            if(storeBranch != null){
                storeBranchDB.updateStoreBranch(storeBranch);
                new DownloadImages().execute();
            }
        }
    }

    public void saveToDatabase(StoreBranch storeBranch)
    {
        if(isUpdate){
            storeBranchDB.updateStoreBranch(storeBranch);

            sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
            editor = sharedPref.edit();
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            String newBranchUpdateTs = LYDateString.dateToString(currentDate, 3);
            editor.putString(Constants.KEY_BRANCH_UPDATE_TS, newBranchUpdateTs);
            editor.commit();

        }else {
            storeBranchDB.updateStoreBranch(storeBranch);

            sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
            int currentActivity = sharedPref.getInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
            if(currentActivity == Constants.CURRENT_INTRODUCTION_ACTIVITY){
                //AppController.getInstance().generalActivity.refreshActivityContent();
                BaseActivity baseActivity = AppController.getInstance().getGeneralActivity();
                if(baseActivity != null) {
                    baseActivity.refreshActivityContent();
                }
                Log.d("StoreBranch refresh ", "StoreBranch activity refreshed!");
            }
        }
    }

    public void storeBranchUpdateRequest(final String storeId ,final String lastUpdatedTs) {
        this.storeId = storeId;
        this.lastUpdatedTs = lastUpdatedTs;
        isUpdate = true;

        String searchUrl = "http://www.eadate.com:8080/screenshow/restful/store/search";
        StringRequest postReqToUpdateBranch = new StringRequest(Request.Method.POST, searchUrl,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    updatedJsonString = response;
                    new ParseJson().execute();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("+++++++++++++++++++Error_Right ["+error+"]");
                }
            }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("searchtype", "modifiedStoreSearch");
                params.put("id", storeId);
                params.put("updated_ts", lastUpdatedTs);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReqToUpdateBranch);
    }



    class DownloadImages extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            sendImageRequest(storeBranch);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Log.d("News reader", "Feed downloaded");
        }
    }



    public void sendImageRequest(final StoreBranch storeBranch){
        String picsName= storeBranch.getLogoUrl();
        String imageUrl = "http://eadate.com/screenshow/uploads/images/store/"+picsName;

        final String tempImageName = picsName;
        final StoreBranch branch = storeBranch;
        ImageRequest ir = new ImageRequest(imageUrl , new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response){
                SaveImages saveImages = new SaveImages(response, tempImageName, branch);
                saveImages.execute();
            }

        }, 0, 0, null,  new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("StoreBranch", "Missing one pic:" + storeBranch.getLogoUrl());
            }
        });
        AppController.getInstance().addToRequestQueue(ir);
    }


    class SaveImages extends AsyncTask<Void, Void, Void> {
        Bitmap image;
        String picsName;
        String imagePath;
        StoreBranch storeBranch;

        public SaveImages(Bitmap image, String picsName, StoreBranch storeBranch) {
            super();
            this.image = image;
            this.picsName = picsName;
            this.storeBranch = storeBranch;
        }

        @Override
        protected Void doInBackground(Void... params) {
            imagePath = saveImageInternalMemory(image, picsName);
            return null;
        }

        @Override protected void onPostExecute(Void result) {
            Log.d("News reader", "Feed downloaded");
            saveToDatabase(storeBranch);
        }
    }


    public String saveImageInternalMemory (Bitmap bitmapImage, String picsName){
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File logoDir = new File(directory, "logo");
        logoDir.mkdir();
        File mypath=new File(logoDir, picsName);

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
}
