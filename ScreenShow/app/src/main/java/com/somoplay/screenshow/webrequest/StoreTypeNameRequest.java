package com.somoplay.screenshow.webrequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.somoplay.screenshow.activity.CarAndHouseActivity;
import com.somoplay.screenshow.activity.DownloadActivity;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.StoreTypeNameDB;
import com.somoplay.screenshow.model.StoreTypeName;
import com.somoplay.screenshow.model.StoreTypeNameList;
import com.somoplay.screenshow.util.LYDateString;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaolu on 15-06-08.
 */
public class StoreTypeNameRequest {

    private Context context = null;
    private StoreTypeNameList storeTypeNameList;
    private StoreTypeNameList updatedStoreTypeNameList;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    public StoreDishRequest storeDishRequest;
    public StoreOfficeRequest storeOfficeRequest;
    private CarAndHouseRequest carAndHouseRequest;
    private DownloadActivity downloadActivity;
    private CarAndHouseActivity carAndHouseActivity;

    private String jsonString;
    private String updatedJsonString;
    private StoreTypeNameDB storeTypeNameDB;
    private boolean isUpdate;
    private String storeId;
    private int counter;

    public StoreTypeNameRequest(DownloadActivity downloadActivity, Context context) {
        super();
        this.context = context;
        this.downloadActivity = downloadActivity;
        storeDishRequest = new StoreDishRequest(downloadActivity, context);
        storeOfficeRequest = new StoreOfficeRequest(downloadActivity, context);
        carAndHouseRequest = new CarAndHouseRequest(downloadActivity, context);
        storeTypeNameDB= new StoreTypeNameDB(context);
    }

    public StoreTypeNameList generateObjectFromJson(String jsonString) {


        StoreTypeNameList storeTypeNameList = new StoreTypeNameList();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            String status = jsonObject.getString("status");
            String mainUrl = jsonObject.getString("main_url");
            String mainUrl2 = jsonObject.getString("main_url2");
            String type = jsonObject.getString("type");
            String service = jsonObject.getString("service");

            ArrayList<StoreTypeName> dataList = new ArrayList<StoreTypeName>();
            ArrayList<StoreTypeName> leftList = new ArrayList<>();
            ArrayList<StoreTypeName> rightList = new ArrayList<>();

            JSONArray dataArray = jsonObject.getJSONArray("data");
            int arrLength = dataArray.length();
            for (int i = 0; i < arrLength; i++) {
                JSONObject dataObj = dataArray.getJSONObject(i);
                int typeId = dataObj.getInt("type_id");
                boolean deleted = dataObj.getInt("deleted")==1?true:false;
                String updatedTs = dataObj.getString("updated_ts");
                String createdTs = dataObj.getString("created_ts");
                String name = dataObj.getString("name");
                int storeId = dataObj.getInt("store_id");
                int functionId = dataObj.getInt("function_id");
                int type1 = dataObj.getInt("type");
                int sequence = dataObj.getInt("sequence");
                String mediaUrl = dataObj.getString("media_url");

                StoreTypeName storeTypeName = new StoreTypeName(typeId,deleted,LYDateString.stringToDate(updatedTs, 3),
                        LYDateString.stringToDate(createdTs, 3), name,storeId,functionId, type1, sequence, mediaUrl);

                //add storeTypeNames to array by type and sequence;
                if(type1 == 1){
                    leftList.add(storeTypeName);
                }else if(type1 == 2){
                    rightList.add(storeTypeName);
                }

            }

            storeTypeNameList.setStatus(status);
            storeTypeNameList.setMainUrl(mainUrl);
            storeTypeNameList.setMainUrl2(mainUrl2);
            storeTypeNameList.setType(type);
            storeTypeNameList.setService(service);

            dataList.addAll(leftList);
            dataList.addAll(rightList);

            storeTypeNameList.setStoreTypeNameArray(dataList);


        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("Subtitle", new String(" There are " + storeTypeNameList.getStoreTypeNameArray().size() + " type names pic"));

        return storeTypeNameList;
    }



    public void sendMenuRequest(final String storeId) {
        this.storeId = storeId;
        isUpdate = false;

        String searchUrl = "http://eadate.com:8080/screenshow/restful/storetype/search";
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
                    System.out.println("+++++++++++++++++++++++++++Error++++++++++++++++++++ ["+error+"]");
                }
            }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("searchtype", "search_by_store_id");
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
            if(isUpdate){
                updatedStoreTypeNameList = generateObjectFromJson(updatedJsonString);
            }else{
                storeTypeNameList = generateObjectFromJson(jsonString);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
            editor = sharedPref.edit();
            int showContentType = sharedPref.getInt(Constants.KEY_SHOW_CONTENT_TYPE, 1);

            if(isUpdate){
                int size=updatedStoreTypeNameList.getStoreTypeNameArray().size();

                for(int i=0;i<size;i++) {
                    saveToDatabase(updatedStoreTypeNameList.getStoreTypeNameArray().get(i));
                }

                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();

                if(size > 0){
                    if(showContentType == 5){
                        String newOfficeTypeNameUpdateTs = LYDateString.dateToString(currentDate, 3);
                        editor.putString(Constants.KEY_OFFICE_TYPE_NAME_UPDATE_TS, newOfficeTypeNameUpdateTs);
                        editor.commit();

                        String dishTypeNameUpdateTs = sharedPref.getString(Constants.KEY_OFFICE_UPDATE_TS, "2015-06-17 23:57:44.0");
                        storeOfficeRequest.officeUpdateRequest(storeId, dishTypeNameUpdateTs);
                    }
                    else if(showContentType == 4){
                        String newStoreTypeNameUpdateTs = LYDateString.dateToString(currentDate, 3);
                        editor.putString(Constants.KEY_STORE_TYPE_NAME_UPDATE_TS, newStoreTypeNameUpdateTs);
                        editor.commit();

                        String dishTypeNameUpdateTs = sharedPref.getString(Constants.KEY_DISH_UPDATE_TS, "2015-06-17 23:57:44.0");
                        storeDishRequest.dishUpdateRequest(storeId, dishTypeNameUpdateTs);
                    }
                }
            }
            else{
                int size = storeTypeNameList.getStoreTypeNameArray().size();
                for(int i=0;i<size;i++) {
                    saveToDatabase(storeTypeNameList.getStoreTypeNameArray().get(i));
                }

                if(showContentType == 5){
                    storeOfficeRequest.sendStringRequest(storeId);
                }else if(showContentType == 4) {
                    storeDishRequest.sendStringRequest(storeId);
                }else if(showContentType == 6) {
                    carAndHouseRequest.sendStringRequest(storeId);
                }
            }
        }
    }



    public void saveToDatabase(StoreTypeName storeTypeName)
    {
        if(isUpdate){
            storeTypeNameDB.updateStoreTypeName(storeTypeName);
            Log.d("TypeName: ", "pic store type name " + storeTypeName.getName() + " is updated");
        }else{
            storeTypeNameDB.updateStoreTypeName(storeTypeName);
            Log.d("TypeName: ", "pic store type name " + storeTypeName.getName() + " is saved" );
        }
    }



    public void updateStoreTypeNameRequest(final String storeId, final String lastUpdateTs) {
        this.storeId = storeId;
        isUpdate = true;

        String searchUrl = "http://eadate.com:8080/screenshow/restful/storetype/search";
        StringRequest postReqToUpdateTypeName = new StringRequest(Request.Method.POST, searchUrl,
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
                params.put("searchtype", "searchNewByStoreId");
                params.put("store_id", storeId);
                params.put("updated_ts", lastUpdateTs);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReqToUpdateTypeName);
    }



/*    public void sendLeftMenuRequest(final String type, final String storeId) {
        isUpdate = false;
        String searchUrl = "http://eadate.com:8080/screenshow/restful/storetype/search";
        StringRequest postReq = new StringRequest(Request.Method.POST, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("!!!!start printing response: "+response+" printing end!!!!!!!");
                        jsonString=response;
                        System.out.println("++++++++Printing jsonString: "+jsonString);
                        new ParseJson().execute();
                        System.out.println("++++++++ finish ParseJason ++++++++++++ ");

//                        StoreTypeNameList storeTypeNameList=generateObjectFromJson(response);
//                        storeShowActivity.afterGettingLeftStoreType(storeTypeNameList);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("+++++++++++++++++++++++++++Error++++++++++++++++++++ ["+error+"]");
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("searchtype", "searchByStoreIdAndType");
                params.put("store_id", storeId);
                params.put("type", type);
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

    public void sendRightMenuRequest(final String type, final String storeId) {
        isUpdate = false;
        //String searchUrl = "http://localhost:8080/screenshow/restful/media/search";
        String searchUrl = "http://eadate.com:8080/screenshow/restful/storetype/search";
        StringRequest postReq = new StringRequest(Request.Method.POST, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("!!!!start printing response_Right : "+response+" Right:printing end!!!!!!!");
                        jsonString=response;
                        System.out.println("++++++++Printing jsonString_Right "+jsonString);
                        new ParseJson().execute();
                        System.out.println("++++++++end jsonString_Right +++++++++");
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
                params.put("searchtype", "searchByStoreIdAndType");
                params.put("store_id", storeId);
                params.put("type", type);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReq);
    }*/



    //    StoreTypeName storeTypeName;
//    private StoreShowActivity storeShowActivity = null;
//    private String type;
//
//    public StoreTypeNameRequest(StoreShowActivity storeShowActivity, String type) {
//        super();
//        this.storeShowActivity = storeShowActivity;
//        this.type = type;
//    }
}
