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
import com.somoplay.screenshow.R;
import com.somoplay.screenshow.activity.ChooseScreenActivity;
import com.somoplay.screenshow.adapter.ChooseScreenAdapter;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.ChooseScreen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JudyShuai on 15-07-05.
 */
public class ChooseScreenRequest {


    private Context context=null;
    private ChooseScreenActivity chooseScreenActivity= null;

    private static final String url = "http://eadate.com:8080/screenshow/restful/screen/search";
    private String jsonString;

    private int screenId;
    private boolean deleted;
    private String updatedTs;
    private String createdTs;
    private String screenName;
    private int adminId;
    private int storeId;
    private int showRestaurant;
    private int showBuilding;
    private int showGeneralStore;
    private int showPhotos;
    private int showAdvertisement;
    private int showContentType;
    private int status;
    private int touchType;
    private int layoutType;
    private String version;
    private String ipAddress;
    private String info;
    private String storeName;
    private String storeAddress;

    private String adminIdRequest;
    private boolean isScreen;
    private int previousShowType;
    private int currentActivity;

    ArrayList<ChooseScreen> showListArray = new ArrayList<ChooseScreen>();

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }



    public ChooseScreenRequest(ChooseScreenActivity chooseScreenActivity){
        super();
        this.chooseScreenActivity=chooseScreenActivity;
    }

    public ArrayList<ChooseScreen> generateObjectFromJson(String jsonString) {

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            int arrLength = dataArray.length();
            for (int i = 0; i < arrLength; i++) {
                JSONObject dataObj = dataArray.getJSONObject(i);
                screenId=dataObj.getInt("id");
                deleted = dataObj.getInt("deleted")==1?true:false;
                updatedTs=dataObj.getString("updated_ts");
                if(dataObj.has("created_ts")){
                    createdTs=dataObj.getString("created_ts");
                }else{
                    createdTs = "2000-01-01 22:57:44.0";
                }

                adminId=dataObj.getInt("admin_id");
                screenName =dataObj.getString("name");
                showRestaurant=dataObj.getInt("show_restaurant");
                showBuilding=dataObj.getInt("show_building");
                showGeneralStore=dataObj.getInt("show_general_store");
                showPhotos=dataObj.getInt("show_photos");
                showAdvertisement=dataObj.getInt("show_advertisemnet");
                showContentType=dataObj.getInt("show_content_type");
                status=dataObj.getInt("status");
                touchType=dataObj.getInt("touch_type");
                layoutType=dataObj.getInt("layout_type");
                version=dataObj.getString("version");
                ipAddress=dataObj.getString("ip_address");
                info=dataObj.getString("info");
                storeId = dataObj.getInt("store_id");
                storeName = dataObj.getString("store_name");
                storeAddress=dataObj.getString("store_address");

                ChooseScreen newData = new ChooseScreen(screenId, deleted, updatedTs, createdTs,
                        screenName,adminId,storeId,showRestaurant, showBuilding,showGeneralStore,showPhotos,
                        showAdvertisement, showContentType,status,touchType, layoutType,version,
                        ipAddress,info, storeName, storeAddress);

                showListArray.add(newData);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return showListArray;
    }

    public ArrayList<ChooseScreen> getShowListArray(){
        return showListArray;
    }

    public void sendStringRequest(final String adminIdRequest, final boolean isScreen) {
        this.isScreen = isScreen;
        this.adminIdRequest = adminIdRequest;

        String searchUrl = "http://eadate.com:8080/screenshow/restful/screen/search";
        StringRequest postReq = new StringRequest(Request.Method.POST, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
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
                if (adminIdRequest  == "0") {
                    params.put("searchtype", "searchPhotoShowScreens");
                }
                else {
                    if(!isScreen){
                        params.put("searchtype", "searchByAdminId");
                        params.put("admin_id", adminIdRequest);
                    }
                    else {
                        params.put("searchtype", "id_search");
                        params.put("id", adminIdRequest);
                    }
                    params.put("pageIndex", "0");
                    params.put("pageSize", "100");
                }
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

        //        StoreTypeName storeTypeName;
        @Override
        protected Void doInBackground(Void... params) {
            if(!isScreen){
                generateObjectFromJson(jsonString);
            }
            else{
                generateScreenInfoFromJson(jsonString);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(chooseScreenActivity.showList != null){
                chooseScreenActivity.chooseScreenList.setChooseScreenArray(showListArray);
                chooseScreenActivity.chooseScreenAdapter= new ChooseScreenAdapter(AppController.getInstance(),
                        showListArray, R.layout.list_item);
                chooseScreenActivity.showList.setAdapter(chooseScreenActivity.chooseScreenAdapter);
                sendLogoImageRequest();
            }
        }
    }

    public void generateScreenInfoFromJson(String jsonString) {
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject dataObj = jsonObject.getJSONObject("data");
            showContentType=dataObj.getInt("show_content_type");
            JSONObject storeObj = dataObj.getJSONObject("store");
            storeId = storeObj.getInt("store_id");
            storeName = storeObj.getString("store_name");
            storeAddress=storeObj.getString("store_address");

            SharedPreferences sharedPref =
                    AppController.getInstance().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putInt(Constants.KEY_SHOW_CONTENT_TYPE, showContentType);
            editor.putInt(Constants.KEY_STORE_ID, storeId);
            editor.putString(Constants.KEY_STORE_NAME, storeName);
            editor.putString(Constants.KEY_STORE_ADDRESS, storeAddress);
            editor.commit();

            currentActivity = sharedPref.getInt(Constants.KEY_CURRENT_ACTIVITY, 1);
            previousShowType = sharedPref.getInt(Constants.KEY_PREVIOUS_CONTENT_TYPE, 1);

            if(showContentType != previousShowType){
                if(currentActivity != Constants.CURRENT_NO_NEED_TO_UPDATE){
                    AppController.getInstance().getGeneralActivity().changeCurrentActivity();
                }

                editor.putInt(Constants.KEY_PREVIOUS_CONTENT_TYPE, showContentType);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendLogoImageRequest(){
        final String imageUrl= "http://eadate.com/screenshow/uploads/images/logo/logo.png";
        ImageRequest ir = new ImageRequest(imageUrl , new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response){
                saveImageInternalMemory(response);
            }
        }, Constants.IMAGE_MAX_WIDTH, Constants.IMAGE_MAX_HEIGHT, Bitmap.Config.RGB_565,
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d("logo pic", "Missing logo pic!!!!");
                    }
                });
        AppController.getInstance().addToRequestQueue(ir);
    }

    public String saveImageInternalMemory (Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(AppController.getInstance());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File dishDir = new File(directory, "logo");
        dishDir.mkdir();
        File mypath=new File(dishDir, "logo.png");

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
}
