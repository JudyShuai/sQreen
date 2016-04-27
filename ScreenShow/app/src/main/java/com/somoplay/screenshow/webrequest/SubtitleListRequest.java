package com.somoplay.screenshow.webrequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.somoplay.screenshow.activity.BaseActivity;
import com.somoplay.screenshow.activity.DownloadActivity;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.SubtitleDB;
import com.somoplay.screenshow.model.Subtitle;
import com.somoplay.screenshow.model.SubtitleList;
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
public class SubtitleListRequest {

    private DownloadActivity downloadActivity = new DownloadActivity();
    private Context context = null;
    private SubtitleDB subtitleDB;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private MediaListRequest mediaListRequest;

    private String screenId;
    private String lastUpdatedTs;
    private String firstDownloadTs;

    public SubtitleListRequest(DownloadActivity downloadActivity, Context context){
        super();
        this.downloadActivity = downloadActivity;
        this.context = context;
        mediaListRequest = new MediaListRequest(downloadActivity, context);
        subtitleDB = new SubtitleDB(context);
    }

    public SubtitleList generateObjectFromJson(String jsonString) {

        SubtitleList subtitleList = new SubtitleList();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            String status = jsonObject.getString("status");
            String type = jsonObject.getString("type");
            String service = jsonObject.getString("service");

            ArrayList<Subtitle > dataList = new ArrayList< >();

            JSONArray dataArray = jsonObject.getJSONArray("data");
            int arrLength = dataArray.length();
            for (int i = 0; i < arrLength; i++) {
                JSONObject dataObjBIG = dataArray.getJSONObject(i);

                int screenSubId=dataObjBIG.getInt("screenSubtitle_id");
                boolean deleted=dataObjBIG.getInt("deleted")==1?true:false;
                int screenId=dataObjBIG.getInt("screen_id");
                String updatedTs = dataObjBIG.getString("updated_ts");
                String createdTs = dataObjBIG.getString("created_ts");
                int sequenceId=dataObjBIG.getInt("sequence_id");
                int duration=dataObjBIG.getInt("duration");

                JSONObject dataObj = dataObjBIG.getJSONObject("subtitle_data");

                int subtitleId=dataObj.getInt("subtitle_id");
                Boolean dataDeleted = dataObj.getInt("deleted")==1?true:false;
                String dataUpdatedTs = dataObj.getString("updated_ts");
                String dataCreatedTs = dataObj.getString("created_ts");
                int adminId=dataObj.getInt("admin_id");
                String startDate = dataObj.getString("start_date");
                String endDate = dataObj.getString("end_date");
                int showType = dataObj.getInt("show_type");
                int durationSec = dataObj.getInt("duration_sec");
                int repeatTime=dataObj.getInt("repeat_time");
                int fonts=dataObj.getInt("fonts");
                String color = dataObj.getString("color");
                int location=dataObj.getInt("location");
                int speed=dataObj.getInt("speed");
                String textContent=dataObj.getString("text_content");

                Subtitle subtitle = new Subtitle(screenSubId, deleted, screenId, LYDateString.stringToDate(updatedTs, 3),
                        LYDateString.stringToDate(createdTs, 3), sequenceId, duration, subtitleId, deleted,
                        LYDateString.stringToDate(dataUpdatedTs, 3), LYDateString.stringToDate(dataCreatedTs, 3),
                        adminId,LYDateString.stringToDate(startDate, 3), LYDateString.stringToDate(endDate, 3),
                        showType, durationSec, repeatTime, fonts, color, location,speed,textContent);
                dataList.add(subtitle);
            }

            subtitleList.setStatus(status);
            subtitleList.setService(service);
            subtitleList.setType(type);
            subtitleList.setSubtitleArray(dataList);

        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("Subtitle updated", new String(" There are " + subtitleList.getSubtitleArray().size() + " subtitles -- jason"));

        downloadActivity.fileSize=subtitleList.getSubtitleArray().size();
        return subtitleList;
    }

   /* public void sendStringRequest(final String screenId) {
        this.screenId = screenId;
        //String searchUrl = "http://localhost:8080/screenshow/restful/media/search";
        String searchUrl = "http://eadate.com:8080/screenshow/restful/screenSubtitleOne/search";
        StringRequest postReq = new StringRequest(Request.Method.POST, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        SubtitleList subtitleList=generateObjectFromJson(response);
                        Log.d("Subtitle updated","This is download, not update");
                       // System.out.println("subtitleList@@@@@@@="+subtitleList.toString());
                       // storeShowActivity.send;
                        for(Subtitle sub:subtitleList.getSubtitleArray()) {
                            subtitleDB.updateSubtitle(sub);
                        }
                        mediaListRequest.sendStringRequest(screenId);
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
                params.put("searchtype", "screenSearch");
                params.put("screen_id", screenId);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReq);
    }*/


    public void sendStringRequest(final String screenId , final String firstDownloadTs) {
        this.screenId = screenId;
        this.firstDownloadTs = firstDownloadTs;
        Log.d("Subtitle updated", "firstDownloadTs is" + firstDownloadTs);

        String searchUrl = "http://eadate.com:8080/screenshow/restful/screenSubtitleOne/search";
        StringRequest postReqToUpdateSubtitle = new StringRequest(Request.Method.POST, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SubtitleList subtitleList=generateObjectFromJson(response);
                        Log.d("Subtitle updated","This is download, not request");

                        if(subtitleList.getSubtitleArray().size() > 0) {
                            //downloadActivity.fileSize = subtitleList.getSubtitleArray().size();
                            downloadActivity.downLoadFileSize = 0;
                            Log.d("Subtitle updated", "found " + subtitleList.getSubtitleArray().size() + " subtitles from subtitleList!");
                            for (Subtitle sub : subtitleList.getSubtitleArray()) {
                                subtitleDB.updateSubtitle(sub);
                                //downloadActivity.downLoadFileSize = downloadActivity.downLoadFileSize +1;
                                //downloadActivity.sendMsg(1);
                                Log.d("sub from subList: ",sub.getTextContent());
                               /* String logString = new String(" new subtitle " +
                                        sub.getTextContent() + "");
                                Log.d("subtitle Updated", logString);*/
                            }
                            //mediaListRequest.sendStringRequest(screenId);

                            //refresh the activity
                            // int currentActivity = sharedPref.getInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
                            //if(currentActivity == Constants.CURRENT_MEDIA_SHOW_ACTIVITY){
                            //}
                            sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                            editor = sharedPref.edit();
                            Calendar calendar = Calendar.getInstance();
                            Date currentDate = calendar.getTime();
                            String newMediaUpdateTs = LYDateString.dateToString(currentDate, 3);
                            editor.putString(Constants.KEY_SUBTITLES_UPDATE_TS, newMediaUpdateTs);
                            editor.commit();
                        }

                        sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                        String newMediaUpdateTsFromSubReq = sharedPref.getString(Constants.KEY_MEDIALIST_UPDATE_TS, "2015-07-27 19:06:27.0");
                        //mediaListRequest.mediaListUpdateRequest(screenId, newMediaUpdateTsFromSubReq);

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
                params.put("searchtype", "modifiedScreenSearchAll");
                params.put("screen_id", screenId);
                params.put("updated_ts", firstDownloadTs);
                Log.d("Subtitle updated","downloadTs is : " + firstDownloadTs);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReqToUpdateSubtitle);
    }


    public void updateSubtitleRequest(final String screenId, final String lastUpdatedTs) {
        this.screenId = screenId;
        this.lastUpdatedTs = lastUpdatedTs;
        Log.d("Subtitle updated", "newUpdatedTs is" + lastUpdatedTs);

        String searchUrl = "http://eadate.com:8080/screenshow/restful/screenSubtitleOne/search";
        StringRequest postReqToUpdateSubtitle = new StringRequest(Request.Method.POST, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SubtitleList subtitleList=generateObjectFromJson(response);
                        Log.d("Subtitle updated","This is update, requesting");

                        if(subtitleList.getSubtitleArray().size() > 0) {
                            Log.d("Subtitle updated", "found "+subtitleList.getSubtitleArray().size()+ " subtitles from subtitleList!");
                            for (Subtitle sub : subtitleList.getSubtitleArray()) {
                                subtitleDB.updateSubtitle(sub);
                                Log.d("sub from subList: ",sub.getTextContent());
                               /* String logString = new String(" new subtitle " +
                                        sub.getTextContent() + "");
                                Log.d("subtitle Updated", logString);*/
                            }
                            sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                            editor = sharedPref.edit();
                            Calendar calendar = Calendar.getInstance();
                            Date currentDate = calendar.getTime();
                            String newMediaUpdateTs = LYDateString.dateToString(currentDate, 3);
                            editor.putString(Constants.KEY_SUBTITLES_UPDATE_TS, newMediaUpdateTs);
                            editor.commit();


                            //refresh the activity
                           // int currentActivity = sharedPref.getInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
                            //if(currentActivity == Constants.CURRENT_MEDIA_SHOW_ACTIVITY){
                            //}
                            BaseActivity baseActivity = AppController.getInstance().getGeneralActivity();
                            if(baseActivity != null) {
                                baseActivity.refreshActivityContent();
                            }
                            Log.d("Subtitle updated ", "advertisement activity refreshed!");
                        }
                            sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                            String newMediaUpdateTsFromSubReq = sharedPref.getString(Constants.KEY_MEDIALIST_UPDATE_TS, "2015-07-27 19:06:27.0");
                            mediaListRequest.mediaListUpdateRequest(screenId, newMediaUpdateTsFromSubReq);

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
                params.put("searchtype", "modifiedScreenSearchAll");
                params.put("screen_id", screenId);
                params.put("updated_ts", lastUpdatedTs);
                Log.d("Subtitle updated","newUpdatedTs is : " + lastUpdatedTs);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReqToUpdateSubtitle);
    }

}
