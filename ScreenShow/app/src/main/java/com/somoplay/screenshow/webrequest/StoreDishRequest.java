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
import com.somoplay.screenshow.activity.ChangeSampleProgressBar;
import com.somoplay.screenshow.activity.DownloadActivity;
import com.somoplay.screenshow.activity.ShowExampleActivity;
import com.somoplay.screenshow.Useless.StoreShowActivity;
import com.somoplay.screenshow.activity.new_StoreShowActivity;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.StoreDishDB;
import com.somoplay.screenshow.model.StoreDish;
import com.somoplay.screenshow.model.StoreDishList;
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
 * Created by yaolu on 15-06-08.
 */
public class StoreDishRequest {

    private ChangeSampleProgressBar changeSampleProgressBar;
    private Context context = null;
    private DownloadActivity downloadActivity = null;
    private AppController appController = AppController.getInstance();
    private StoreMediaRequest storeMediaRequest;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private StoreShowActivity storeShowActivity = new StoreShowActivity();
    private new_StoreShowActivity mNewStoreShowActivity = new new_StoreShowActivity();

    private StoreDishList updatedStoreDishList;
    private StoreDishList storeDishList;
    private StoreDishDB storeDishDB;

    /*private ProgressDialog progressBar;
    private Handler progressBarHandler = new Handler();
    private int progressBarStatus = 0;*/

    private String lastUpdatedTs;
    private String storeId;
    private String jsonString;
    private String upDatedJsonString;
    private static final String url = "http://www.eadate.com:8080/screenshow/restful/dish/search";
    private final String TAG="DishImage";
    private boolean isUpdate;
    private int totalCount = 0;
    private int totalDish = 220;
    private int size;
    //private int totalCount = 210;



    public StoreDishRequest(DownloadActivity downloadActivity, Context context){
        super();
        this.downloadActivity = downloadActivity;
        this.context = context;
        storeDishDB=new StoreDishDB(downloadActivity);
        storeMediaRequest = new StoreMediaRequest(downloadActivity, appController);
    }



    public StoreDishList generateObjectFromJson(String jsonString) {
        StoreDishList storeDishList = new StoreDishList();

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            String status = jsonObject.getString("status");
            String main_url1 = jsonObject.getString("main_url");
            String main_url2 = jsonObject.getString("main_url2");
            String type = jsonObject.getString("type");
            String service = jsonObject.getString("service");

            ArrayList<StoreDish> dataList = new ArrayList< StoreDish>();

            JSONArray dataArray = jsonObject.getJSONArray("data");
            int arrLength = dataArray.length();
            for (int i = 0; i < arrLength; i++) {
                JSONObject dataObj = dataArray.getJSONObject(i);
                int dishId = dataObj.getInt("dish_id");
                Boolean deleted = dataObj.getInt("deleted")==1?true:false;
                String updatedTs = dataObj.getString("updated_ts");
                String createdTs = dataObj.getString("created_ts");
                String name=dataObj.getString("name");
                int storeId = dataObj.getInt("store_id");
                int storeTypeId = dataObj.getInt("store_type_id");
                String localId=dataObj.getString("local_id");
                int sequence=dataObj.getInt("sequence");
                int storeType=dataObj.getInt("type");
                Boolean isSpecialty = dataObj.getInt("is_specialty")==1?true:false;
                Boolean isDeal = dataObj.getInt("is_deal")==1?true:false;
                Boolean isNew = dataObj.getInt("is_new")==1?true:false;
                double price = dataObj.getDouble("price");
                double salePercent = dataObj.getDouble("sale_percent");
                String saleExpire;
                if(dataObj.has("sale_expire")){
                    saleExpire = dataObj.getString("sale_expire");
                }else{
                    saleExpire = "2000-01-01 22:57:44.0";
                }
                String materials = dataObj.getString("materials");
                String mediaUrl = dataObj.getString("media_url");
                String nutrition = dataObj.getString("nutrition");
                String info = dataObj.getString("info");

                StoreDish storeDish = new StoreDish(dishId,deleted,LYDateString.stringToDate(updatedTs, 3),
                        LYDateString.stringToDate(createdTs, 3), name,storeId,storeTypeId,localId,
                        sequence,storeType,isSpecialty,isDeal,isNew, price,salePercent,LYDateString.stringToDate(saleExpire,3),
                        materials,mediaUrl,nutrition, info,"", 99999);

                dataList.add(storeDish);
            }

            storeDishList.setStatus(status);
            storeDishList.setMainUrl(main_url1);
            storeDishList.setMainUrl2(main_url2);
            storeDishList.setService(service);
            storeDishList.setType(type);
            storeDishList.setStoreDishArray(dataList);

        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, new String(" There are " + storeDishList.getStoreDishArray().size() + " pictures"));
        return storeDishList;
    }


 /*   private void cancelProgressBarAndTask() {

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
    }*/


    public void sendStringRequest(final String storeId) {
        this.storeId = storeId;
        totalCount = storeDishDB.getStoreDishes().size();
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
                    double percentage=(double)getTotalCount()/totalDish;
                    progressBarStatus = (int)(percentage*100+0.5);
                    if(size > 0){
                        totalDish = size;
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

        String searchUrl = "http://eadate.com:8080/screenshow/restful/dish/search";
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
            storeDishList = generateObjectFromJson(jsonString);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(totalCount < storeDishList.getStoreDishArray().size()){
                String logString = new String(totalCount+" pictures have been saved." +
                        storeDishList.getStoreDishArray().get(totalCount).getName());
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
                sendImageRequest(updatedStoreDishList.getStoreDishArray().get(totalCount));
            } else {
                sendImageRequest(storeDishList.getStoreDishArray().get(totalCount));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
           if(!isUpdate){
               if(downloadActivity != null){
                   Toast.makeText(appController, "Downloading data, please wait for a few minutes, thanks!", Toast.LENGTH_SHORT).show();
                   downloadActivity.progressBar.setVisibility(View.VISIBLE);
               }else{
                   changeSampleProgressBar.turnOnProgressBar();
               }
           }
        }
    }

    public void sendImageRequest(StoreDish storeDish){
        String picsName= storeDish.getMediaUrl();
        String imageUrl= "http://eadate.com/screenshow/uploads/images/dish/"+picsName;

        final String tempImageName1 = picsName;
        final StoreDish tempDish = storeDish;
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
                Log.d(TAG, "Missing one pic:" + storeDishList.getStoreDishArray().get(totalCount).getName());
                if(totalCount < storeDishList.getStoreDishArray().size()){
                    totalCount++;
                    String logString = new String(totalCount+" pictures have been saved." +
                            storeDishList.getStoreDishArray().get(totalCount).getName());
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
        StoreDish storeDish;

        public SaveImages(Bitmap image, String picsName, StoreDish storeDish) {
            super();
            this.image = image;
            this.picsName = picsName;
            this.storeDish = storeDish;
        }

        @Override
        protected Void doInBackground(Void... params) {
            imagePath = saveImageInternalMemory(image, picsName);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            saveToDatabase(storeDish, imagePath);
        }
    }

    public String saveImageInternalMemory (Bitmap bitmapImage, String picsName){
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File dishDir = new File(directory, "dish");
        dishDir.mkdir();
        File mypath=new File(dishDir, picsName);

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

    public void  saveToDatabase(StoreDish storeDish, String imagePath)
    {
        storeDish.setMediaLocalUrl(imagePath);
        if(isUpdate){
            storeDishDB.updateStoreDish(storeDish);

            size = updatedStoreDishList.getStoreDishArray().size();

            if(totalCount < size - 1) {
                totalCount ++ ;
                String logString = new String(totalCount+" pictures have been updated." +
                        updatedStoreDishList.getStoreDishArray().get(totalCount).getName());
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

                storeMediaRequest.mediaUpdateRequest(storeId, lastMediaUpdateTs);
            }

        }else{
            storeDishDB.updateStoreDish(storeDish);
            size = storeDishList.getStoreDishArray().size();

            if(totalCount < size - 1 && ((downloadActivity!= null && downloadActivity.isDownload == true) || ShowExampleActivity.isDownload == true))
            {
                totalCount++;
                String logString = new String(totalCount+" pictures have been saved." +
                        storeDishList.getStoreDishArray().get(totalCount).getName());
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



    public void dishUpdateRequest(final String storeId, final String lastUpdateTs) {
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



    class ParseJsonAndUpdateDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            updatedStoreDishList = generateObjectFromJson(upDatedJsonString);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(updatedStoreDishList.getStoreDishArray().size() > 0){
                sharedPref = appController.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                editor = sharedPref.edit();
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                String newDishUpdateTs = LYDateString.dateToString(currentDate, 3);
                editor.putString(Constants.KEY_DISH_UPDATE_TS, newDishUpdateTs);
                editor.commit();

                String logString = new String(totalCount+" pictures have been updated." +
                        updatedStoreDishList.getStoreDishArray().get(totalCount).getName());
                Log.d(TAG, logString);
                new DownloadImages().execute();
            }
        }
    }

    public void setInterface(ChangeSampleProgressBar bar){
        this.changeSampleProgressBar = bar;
    }

}
