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
import com.somoplay.screenshow.activity.DownloadActivity;
import com.somoplay.screenshow.activity.ShowExampleActivity;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.CarAndHouseDB;
import com.somoplay.screenshow.model.CarAndHouse;
import com.somoplay.screenshow.model.CarAndHouseList;
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
 * Created by Shaohua on 8/24/2015.
 */
public class CarAndHouseRequest {
    private Context context = null;
    private DownloadActivity downloadActivity = null;
    private AppController appController = AppController.getInstance();
    private NewStoreMediaRequest newStoreMediaRequest;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private CarAndHouseList updatedCarAndHouseList;
    private CarAndHouseList carAndHouseList;
    private CarAndHouseDB carAndHouseDB;

    private ProgressDialog progressBar;
    private Handler progressBarHandler = new Handler();
    private int progressBarStatus = 0;

    private String lastUpdatedTs;
    private String storeId;
    private String jsonString;
    private String upDatedJsonString;
    private static final String url = "http://www.eadate.com:8080/screenshow/restful/storeitem/search";
    private final String TAG="StoreItemImage";
    private boolean isUpdate;
    private int totalCount = 0;
    private int totalItem = 60;
    private int size;
    private int adminId;
    private String adminIdString;
    //private int totalCount = 210;



    public CarAndHouseRequest(DownloadActivity downloadActivity, Context context){
        super();
        this.downloadActivity = downloadActivity;
        this.context = context;
        carAndHouseDB = new CarAndHouseDB(downloadActivity);
        //todo: test for new store media
        newStoreMediaRequest = new NewStoreMediaRequest(downloadActivity, appController);
        sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        adminId = sharedPref.getInt(Constants.KEY_ADMIN_ID, 1000000000);
        adminIdString  = String.valueOf(adminId);

        //storeMediaRequest = new StoreMediaRequest(downloadActivity, appController);
    }



    public CarAndHouseList generateObjectFromJson(String jsonString) {
        CarAndHouseList carAndHouseList = new CarAndHouseList();

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            String status = jsonObject.getString("status");
            String main_url1 = jsonObject.getString("main_url");
            String main_url2 = jsonObject.getString("main_url2");
            String type = jsonObject.getString("type");
            String service = jsonObject.getString("service");

            ArrayList<CarAndHouse> dataList = new ArrayList<CarAndHouse>();

            JSONArray dataArray = jsonObject.getJSONArray("data");
            int arrLength = dataArray.length();
            for (int i = 0; i < arrLength; i++) {
                JSONObject dataObj = dataArray.getJSONObject(i);
                int storeItemId = dataObj.getInt("storeItem_id");
                Boolean deleted = dataObj.getInt("deleted")==1?true:false;
                String updatedTs = dataObj.getString("updated_ts");
                String createdTs = dataObj.getString("created_ts");
                String name=dataObj.getString("name");
                int storeId = dataObj.getInt("store_id");
                int storeTypeId = dataObj.getInt("store_type_id");
                String localId=dataObj.getString("local_id");
                int sequence=dataObj.getInt("sequence");
                int storeType=dataObj.getInt("type");
                Boolean isKeyOne = dataObj.getInt("is_key_one")==1?true:false;
                Boolean isKeyTwo = dataObj.getInt("is_key_two")==1?true:false;
                Boolean isKeyThree = dataObj.getInt("is_key_three")==1?true:false;
                double price = dataObj.getDouble("price");
                double salePercent = dataObj.getDouble("sale_percent");
                String saleExpire;
                if(dataObj.has("sale_expire")){
                    saleExpire = dataObj.getString("sale_expire");
                }else{
                    saleExpire = "2000-01-01 22:57:44.0";
                }
                String infoOne = dataObj.getString("info_one");
                String infoTwo = dataObj.getString("info_two");
                String infoThree = dataObj.getString("info_three");
                String mediaUrl= dataObj.getString("media_url");

                CarAndHouse carAndHouse = new CarAndHouse(storeItemId,deleted, LYDateString.stringToDate(updatedTs, 3),
                        LYDateString.stringToDate(createdTs, 3), name,storeId,storeTypeId,localId,
                        sequence,storeType,isKeyOne,isKeyTwo,isKeyThree, price,salePercent,LYDateString.stringToDate(saleExpire,3),
                        infoOne,infoTwo,infoThree, mediaUrl, 9999);

                dataList.add(carAndHouse);
            }

            carAndHouseList.setStatus(status);
            carAndHouseList.setMainUrl(main_url1);
            carAndHouseList.setMainUrl2(main_url2);
            carAndHouseList.setService(service);
            carAndHouseList.setType(type);
            carAndHouseList.setCarAndHouseArray(dataList);

        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, new String(" There are " + carAndHouseList.getCarAndHouseArray().size() + " pictures"));
        return carAndHouseList;
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
        //totalCount = carAndHouseDB.getCarAndHouseesByStoreId(storeId).size();
        isUpdate = false;
        size = 0;

      /*  progressBar = new ProgressDialog(downloadActivity);
        progressBar.setMessage("Dishes Data Downloading..."+jsonString);
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
                    double percentage=(double)getTotalCount()/totalItem;
                    progressBarStatus = (int)(percentage*100+0.5);
                    if(size > 0){
                        totalItem = size;
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

        String searchUrl = "http://eadate.com:8080/screenshow/restful/storeitem/search";
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
                        System.out.println("+++++++++++++++++++++++++++++++Error ["+error+"]");
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
            carAndHouseList = generateObjectFromJson(jsonString);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
            //adminId = sharedPref.getInt(Constants.KEY_ADMIN_ID, 1000000000);
            //adminIdString = String.valueOf(adminId);
            if(totalCount < carAndHouseList.getCarAndHouseArray().size()){
                String logString = new String(totalCount+" pictures have been saved." +
                        carAndHouseList.getCarAndHouseArray().get(totalCount).getName());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }else {
              /*  totalCount=0;
                newStoreMediaRequest.sendStringRequest(adminIdString);*/
            }
        }
    }

    class DownloadImages extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if(isUpdate){
                sendImageRequest(updatedCarAndHouseList.getCarAndHouseArray().get(totalCount));
            } else {
                sendImageRequest(carAndHouseList.getCarAndHouseArray().get(totalCount));
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

    public void sendImageRequest(CarAndHouse storeDish){
        String picsName= storeDish.getMediaUrl();
        String imageUrl= "http://eadate.com/screenshow/uploads/images/storeitem/"+picsName;

        final String tempImageName1 = picsName;
        final CarAndHouse tempDish = storeDish;
        ImageRequest ir = new ImageRequest(imageUrl , new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response){
                //String name = tempImageName1;
                //String imgSaved = saveImageInternalMemory(response, name);
                //tempMedia.setMediaLocalUrl(imgSaved);
                //saveToDatabase(tempMedia);
                SaveImages saveImages = new SaveImages(response,tempImageName1,tempDish);
                saveImages.execute();
            }

        }, Constants.IMAGE_MAX_WIDTH, Constants.IMAGE_MAX_HEIGHT, Bitmap.Config.RGB_565,
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d(TAG, "Missing one pic:" + carAndHouseList.getCarAndHouseArray().get(totalCount).getName());
                        if(totalCount < carAndHouseList.getCarAndHouseArray().size()){
                            totalCount++;
                            String logString = new String(totalCount+" pictures have been saved." +
                                    carAndHouseList.getCarAndHouseArray().get(totalCount).getName());
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
        CarAndHouse carAndHouse;

        public SaveImages(Bitmap image, String picsName, CarAndHouse carAndHouse) {
            super();
            this.image = image;
            this.picsName = picsName;
            this.carAndHouse = carAndHouse;
        }

        @Override
        protected Void doInBackground(Void... params) {
            imagePath = saveImageInternalMemory(image, picsName);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            saveToDatabase(carAndHouse, imagePath);
        }
    }

    public String saveImageInternalMemory (Bitmap bitmapImage, String picsName){
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File carAndHouseDir = new File(directory, "storeitem");
        carAndHouseDir.mkdir();
        File mypath=new File(carAndHouseDir, picsName);

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

    public void  saveToDatabase(CarAndHouse carAndHouse, String imagePath)
    {
        if(isUpdate){
            carAndHouseDB.updateCarAndHouse(carAndHouse);

            size = updatedCarAndHouseList.getCarAndHouseArray().size();

            if(totalCount < size - 1) {
                totalCount ++ ;
                String logString = new String(totalCount+" pictures have been updated." +
                        updatedCarAndHouseList.getCarAndHouseArray().get(totalCount).getName());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }else{
                totalCount = 0;

                sharedPref = appController.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                String lastMediaUpdateTs = sharedPref.getString(Constants.KEY_STORE_MEDIAS_UPDATE_TS, "2015-02-17 22:57:44.0");
                int currentActivity = sharedPref.getInt(Constants.KEY_CURRENT_ACTIVITY, 0);
                if(currentActivity == Constants.CURRENT_STORE_SHOW_ACTIVITY){
                    AppController.getInstance().getGeneralActivity().refreshActivityContent();
                }

                newStoreMediaRequest.mediaUpdateRequest(adminIdString, lastMediaUpdateTs);
            }

        }
        else{
            carAndHouseDB.updateCarAndHouse(carAndHouse);
            size = carAndHouseList.getCarAndHouseArray().size();

            if(totalCount < size - 1 && ((downloadActivity!= null && downloadActivity.isDownload == true) || ShowExampleActivity.isDownload == true))
            {
                totalCount++;
                String logString = new String(totalCount+" pictures have been saved." +
                        carAndHouseList.getCarAndHouseArray().get(totalCount).getName());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            } else {
                totalCount=0;
                if((downloadActivity!= null && downloadActivity.isDownload == true) || ShowExampleActivity.isDownload == true){
                    newStoreMediaRequest.sendStringRequest(adminIdString);
                }
            }
        }
    }



/*
    public void carAndHouseUpdateRequest(final String storeId, final String lastUpdateTs) {
        this.storeId = storeId;
        this.lastUpdatedTs = lastUpdateTs;
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
                params.put("searchtype", "searchNewByStoreId");
                params.put("pageIndex", "0");
                params.put("pageSize", "1000");
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
        AppController.getInstance().addToRequestQueue(postReqToUpdateDish);
    }
*/



    class ParseJsonAndUpdateDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            updatedCarAndHouseList = generateObjectFromJson(upDatedJsonString);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(updatedCarAndHouseList.getCarAndHouseArray().size() > 0){
                sharedPref = appController.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                editor = sharedPref.edit();
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                String newDishUpdateTs = LYDateString.dateToString(currentDate, 3);
                editor.putString(Constants.KEY_DISH_UPDATE_TS, newDishUpdateTs);
                editor.commit();

                String logString = new String(totalCount+" pictures have been updated." +
                        updatedCarAndHouseList.getCarAndHouseArray().get(totalCount).getName());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }
        }
    }
}
