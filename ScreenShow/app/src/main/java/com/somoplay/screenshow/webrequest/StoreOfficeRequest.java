package com.somoplay.screenshow.webrequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.somoplay.screenshow.activity.ChangeSampleProgressBar;
import com.somoplay.screenshow.activity.DownloadActivity;
import com.somoplay.screenshow.activity.ShowExampleActivity;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.StoreOfficeDB;
import com.somoplay.screenshow.model.StoreOffice;
import com.somoplay.screenshow.model.StoreOfficeList;
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
 * Created by yaolu on 15-06-11.
 */
public class StoreOfficeRequest {
    private ChangeSampleProgressBar changeSampleProgressBar;
    private Context context=null;
    private DownloadActivity downloadActivity = null;
    private StoreMediaRequest storeMediaRequest;
    private AppController appcontroler = AppController.getInstance();
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private static final String jsonUrl = "http://www.eadate.com:8080/screenshow/restful/office/search";
    private String storeId;
    private String storeTypeId;
    private String jsonString;
    private String upDatedJsonString;
    private StoreOfficeList storeOfficeList;
    private StoreOfficeList upDatedStoreOfficeList;
    private StoreOfficeDB storeOfficeDB = new StoreOfficeDB(AppController.getInstance());

    private ProgressDialog progressBar;
    private Handler progressBarHandler = new Handler();
    private int progressBarStatus = 0;

    private final String TAG = "Office Image";
    //private int totalCount = 75;
    private int totalCount = 0;
    private boolean isUpdate;
    private int totalOffice = 80;
    private int size;

    public StoreOfficeRequest(DownloadActivity downloadActivity, Context context) {
        super();
        this.downloadActivity = downloadActivity;
        storeMediaRequest = new StoreMediaRequest(downloadActivity, appcontroler);
        this.context=context;
    }

    public StoreOfficeList generateObjectFromJson(String jsonString) {

        StoreOfficeList storeOfficeList = new StoreOfficeList();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            String status = jsonObject.getString("status");
            String mainUrl = jsonObject.getString("main_url");
            String mainUrl2 = jsonObject.getString("main_url2");
            String type2 = jsonObject.getString("type");
            String service = jsonObject.getString("service");

            ArrayList<StoreOffice> dataList = new ArrayList< StoreOffice>();

            JSONArray dataArray = jsonObject.getJSONArray("data");
            int arrLength = dataArray.length();
            for (int i = 0; i < arrLength; i++) {
                JSONObject dataObj = dataArray.getJSONObject(i);
                int id = dataObj.getInt("office_id");
                boolean deleted = dataObj.getInt("deleted")==1?true:false;
                String updatedTs = dataObj.getString("updated_ts");
                String createdTs = dataObj.getString("created_ts");
                String name = dataObj.getString("name");
                int storeId= dataObj.getInt("store_id");
                int storeTypeId= dataObj.getInt("store_type_id");
                int sequence= dataObj.getInt("sequence");
                int type= dataObj.getInt("type");
                double rent = dataObj.getDouble("rent");
                double size=dataObj.getDouble("size");
                String unit=dataObj.getString("unit");
                String email = dataObj.getString("email");
                String phone0 = dataObj.getString("phone0");
                String phone1 = dataObj.getString("phone1");
                String phone2 = dataObj.getString("phone2");
                String website = dataObj.getString("website");
                String fax = dataObj.getString("fax");
                String mediaUrl=dataObj.getString("media_url");
                boolean isAdministration = dataObj.getInt("is_administration")==1?true:false;
                boolean isRent = dataObj.getInt("is_rent")==1?true:false;
                boolean isWc = dataObj.getInt("is_wc")==1?true:false;
                boolean isSale = dataObj.getInt("is_sale")==1?true:false;
                int salePercent = dataObj.getInt("sale_percent");
                String saleExpire;
                if(dataObj.has("sale_expire")){
                    saleExpire = dataObj.getString("sale_expire");
                }else{
                    saleExpire = "2000-01-01 22:57:44.0";
                }
                String info=dataObj.getString("info");

                StoreOffice storeOffice = new StoreOffice(id,deleted,LYDateString.stringToDate(updatedTs, 3),
                        LYDateString.stringToDate(createdTs, 3),name,storeId,storeTypeId,sequence,type,
                        rent, size, unit, email, phone0, phone1, phone2, website, fax, mediaUrl,
                        isAdministration, isRent, isWc, isSale, salePercent, LYDateString.stringToDate(saleExpire, 3), info, 9999);

                dataList.add(storeOffice);

            }
            storeOfficeList.setStatus(status);
            storeOfficeList.setMainUrl(mainUrl);
            storeOfficeList.setMainUrl2(mainUrl2);
            storeOfficeList.setType(type2);
            storeOfficeList.setService(service);
            storeOfficeList.setStoreOfficeArray(dataList);

        }catch (Exception e) {
            e.printStackTrace();
        }

        String logString = new String(" There are " + storeOfficeList.getStoreOfficeArray().size() + " pictures");
        Log.d(TAG, logString);

        return storeOfficeList;
    }


    private void cancelProgressBarAndTask() {

        new Thread() {
            @Override
            public void run() {

                try {
                    // code runs in a thread
                    downloadActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new ParseJson().cancel(true);
                            progressBar.dismiss();
                        }
                    });
                } catch (final Exception ex) {
                    Log.i("---","Exception in thread");
                }
            }
        }.start();
    }


    public void sendStringRequest(final String storeId) {
        this.storeId = storeId;
        totalCount = storeOfficeDB.getStoreOffices().size();
        isUpdate = false;
        size = 0;

        /*progressBar = new ProgressDialog(downloadActivity);
        progressBar.setMessage("Office Data Downloading..."+jsonString);
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.setCancelable(true);
        progressBar.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelProgressBarAndTask();
            }
        });
        progressBar.show();
        progressBarStatus = 0;

        new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {
                    double percentage=(double)getTotalCount()/totalOffice;
                    progressBarStatus = (int)(percentage*100+0.5);
                    if(size > 0){
                        totalOffice = size;
                    }

                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                progressBar.dismiss();
            }
        }).start();*/

        StringRequest postReq = new StringRequest(Request.Method.POST, jsonUrl,
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
                        System.out.println("Error ["+error+"]");
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("searchtype", "search_by_store_id");
                params.put("pageIndex", "0");
                params.put("pageSize", "1000");
                params.put("store_id", storeId);
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
            storeOfficeList=generateObjectFromJson(jsonString);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(totalCount < storeOfficeList.getStoreOfficeArray().size()){
                String logString = new String(totalCount+" pictures have been saved." +
                        storeOfficeList.getStoreOfficeArray().get(totalCount).getName());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }else {
                totalCount=0;
                if(changeSampleProgressBar != null){
                    changeSampleProgressBar.turnOffProgressBar();
                }

                storeMediaRequest.sendStringRequest(storeId);
            }
        }
    }

    class DownloadImages extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if(isUpdate){
                sendImageRequest(upDatedStoreOfficeList.getStoreOfficeArray().get(totalCount));
            }
            else{
                sendImageRequest(storeOfficeList.getStoreOfficeArray().get(totalCount));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(!isUpdate){
                if(downloadActivity != null){
                    downloadActivity.progressBar.setVisibility(View.VISIBLE);
                }else{
                    changeSampleProgressBar.turnOnProgressBar();
                }
            }
        }
    }

    public void sendImageRequest(StoreOffice storeOffice){
        String picsName= storeOffice.getMediaUrl();
        String url = "http://eadate.com/screenshow/uploads/images/office/"+picsName;

        final String tempImageName1 = picsName;
        final StoreOffice tempOffice = storeOffice;
        ImageRequest ir = new ImageRequest(url , new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response){
                SaveImages saveImages = new SaveImages(response,tempImageName1,tempOffice);
                saveImages.execute();
            }
        },  Constants.IMAGE_MAX_WIDTH, Constants.IMAGE_MAX_HEIGHT, Bitmap.Config.RGB_565,
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d(TAG, "Missing one pic:" + storeOfficeList.getStoreOfficeArray().get(totalCount).getName());
                if(totalCount < storeOfficeList.getStoreOfficeArray().size()){
                    totalCount++;
                    String logString = new String(totalCount+" pictures have been saved." +
                            storeOfficeList.getStoreOfficeArray().get(totalCount).getName());
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
        StoreOffice storeOffice;

        public SaveImages(Bitmap image, String picsName, StoreOffice storeOffice) {
            super();

            this.image = image;
            this.picsName = picsName;
            this.storeOffice = storeOffice;
        }

        @Override
        protected Void doInBackground(Void... params) {
            imagePath = saveImageInternalMemory(image, picsName);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            saveToDatabase(storeOffice,imagePath);
        }
    }

    public String saveImageInternalMemory (Bitmap bitmapImage, String picsName){
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File officeDir = new File(directory, "office");
        officeDir.mkdir();
        File mypath=new File(officeDir, picsName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
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

    public void  saveToDatabase(StoreOffice storeOffice, String imagePath)
    {
        int size;
        //storeOffice.setMediaUrl(imagePath);
        if(isUpdate){
            storeOfficeDB.updateStoreOffice(storeOffice);
            size = upDatedStoreOfficeList.getStoreOfficeArray().size();

            if(totalCount < size-1)
            {
                totalCount++;
                String logString = new String(totalCount+" pictures have been updated." +
                        upDatedStoreOfficeList.getStoreOfficeArray().get(totalCount).getName());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }else{
                totalCount=0;
                sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);

                int currentActivity = sharedPref.getInt(Constants.KEY_CURRENT_ACTIVITY, 0);
                if(currentActivity == Constants.CURRENT_OFFICE_SHOW_ACTIVITY){
                    AppController.getInstance().getGeneralActivity().refreshActivityContent();
                }

                String lastStoreMediaUpdateTs = sharedPref.getString(Constants.KEY_STORE_MEDIAS_UPDATE_TS, "2015-02-17 23:27:00.0");
                storeMediaRequest.mediaUpdateRequest(storeId, lastStoreMediaUpdateTs);
            }

        }else{
            storeOfficeDB.updateStoreOffice(storeOffice);
            size = storeOfficeList.getStoreOfficeArray().size();

            if(totalCount < size-1 && ((downloadActivity!= null && downloadActivity.isDownload == true) || ShowExampleActivity.isDownload == true)) {
                totalCount++;
                String logString = new String(totalCount+" pictures have been saved." +
                        storeOfficeList.getStoreOfficeArray().get(totalCount).getName());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            } else {
                totalCount=0;
                if(changeSampleProgressBar != null){
                    changeSampleProgressBar.turnOffProgressBar();
                }
                if((downloadActivity!= null && downloadActivity.isDownload == true) || ShowExampleActivity.isDownload == true){
                    storeMediaRequest.sendStringRequest(storeId);
                }
            }
        }
    }



    public void officeUpdateRequest(final String storeId, final String lastUpdateTs) {
        this.storeId = storeId;
        isUpdate = true;

        StringRequest postReqToUpdateOffice = new StringRequest(Request.Method.POST, jsonUrl,
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
                params.put("searchtype", "searchNewByStoreId");
                params.put("store_id", storeId);
                params.put("updated_ts", lastUpdateTs);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReqToUpdateOffice);
    }



    class ParseJsonAndUpdateDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            upDatedStoreOfficeList = generateObjectFromJson(upDatedJsonString);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(upDatedStoreOfficeList.getStoreOfficeArray().size() > 0) {
                sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                editor = sharedPref.edit();
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                String newOfficeUpdateTs = LYDateString.dateToString(currentDate, 3);
                editor.putString(Constants.KEY_OFFICE_UPDATE_TS, newOfficeUpdateTs);
                editor.commit();

                String logString = new String(totalCount + " pictures have been updated." +
                        upDatedStoreOfficeList.getStoreOfficeArray().get(totalCount).getName());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }
        }
    }


    public void setInterface(ChangeSampleProgressBar bar){
        this.changeSampleProgressBar = bar;
    }
}
