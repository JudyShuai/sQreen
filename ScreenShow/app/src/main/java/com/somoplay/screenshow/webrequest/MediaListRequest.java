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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.somoplay.screenshow.activity.BaseActivity;
import com.somoplay.screenshow.activity.DownloadActivity;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.MediaDB;
import com.somoplay.screenshow.model.Media;
import com.somoplay.screenshow.model.MediaList;
import com.somoplay.screenshow.util.LYDateString;
import com.somoplay.screenshow.videodownload.FileDownloadHelper;

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
public class MediaListRequest {

    private Context context = null;
    private MediaList mediaList;
    private String jsonString;
    private MediaDB mediaDB;
    private ArrayList<Media> allMedias;
    private DownloadActivity downloadActivity;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private ProgressDialog progressBar;
    private Handler progressBarHandler = new Handler();
    private int progressBarStatus = 0;

    private String screenId;
    private boolean isUpdate;
    private int totalMedias;
    private int currentMedia;
    public final String TAG = "MedialistRequest";

    private int totalMediaList = 10;
    private Media tempMedia;

    private FileDownloadHelper fileDownloadHelper = new FileDownloadHelper();


    public MediaListRequest(DownloadActivity downloadActivity,Context context) {
        super();
        this.context = context;
        this.downloadActivity = downloadActivity;
        mediaDB = new MediaDB(context);
    }



    public MediaList generateObjectFromJson(String jsonString) {
        MediaList mediaList = new MediaList();

        try{
            JSONObject jsonObject = new JSONObject(jsonString);

            String status = jsonObject.getString("status");
            String type = jsonObject.getString("type");
            String service = jsonObject.getString("service");
            String adsImageUrl = jsonObject.getString("advertiasement_image_url");
            String adsImageUrl2 = jsonObject.getString("advertiasement_image_url2");
            String adsVideoUrl1 = jsonObject.getString("advertiasement_video_url1");
            String adsVideoUrl2 = jsonObject.getString("advertiasement_video_url2");
            String photoShowImageUrl1 = jsonObject.getString("photoshow_image1");
            String photoShowImageUrl2 = jsonObject.getString("photoshow_image2");
            String storeImageUrl1 = jsonObject.getString("store_image1");
            String storeImageUrl2 = jsonObject.getString("store_image2");
            String officeImageUrl1 = jsonObject.getString("office_image1");
            String officeImageUrl2 = jsonObject.getString("office_image2");
            String dishImageUrl1 = jsonObject.getString("dish_image1");
            String dishImageUrl2 = jsonObject.getString("dish_image2");
            String typeImageUrl1 = jsonObject.getString("type_image1");
            String typeImageUrl2 = jsonObject.getString("type_image2");

            ArrayList<Media > dataList = new ArrayList< Media>();

            JSONArray dataArray = jsonObject.getJSONArray("data");
            int arrLength = dataArray.length();

            for (int i = 0; i < arrLength; i++) {
                JSONObject outerObj = dataArray.getJSONObject(i);
                JSONObject dataObj = outerObj.getJSONObject("Media Data");

                int id = dataObj.getInt("media_id");
                Boolean dataDeleted = dataObj.getInt("deleted") == 1 ? true : false;
                String dataUpdatedTs = dataObj.getString("updated_ts");
                String dataCreatedTs = dataObj.getString("created_ts");
                String mediaName = dataObj.getString("media_name");
                int adminId = dataObj.getInt("admin_id");
                int userId = dataObj.getInt("user_id");
                String userName = dataObj.getString("user_name");
                String startDate;
                if( dataObj.has("start_date") ){
                    startDate = dataObj.getString("start_date");
                }
                else {
                    startDate = "1981-11-11 11:11:11";
                }

                String endDate;
                if( dataObj.has("end_date") ){
                    endDate = dataObj.getString("end_date");
                }
                else {
                    endDate = "1981-11-11 11:11:11";
                }

                int showType = dataObj.getInt("show_type") ;
                int statusB = dataObj.getInt("status") ;
                int mediaType = dataObj.getInt("media_type") ;
                int elementId = dataObj.getInt("element_id");
                String mediaUrl = dataObj.getString("media_url");
                int durationSec = dataObj.getInt("duration_sec");
                double version = dataObj.getDouble("version");

                int screenMediaId = outerObj.getInt("screenMedia_id");
                int screenId = outerObj.getInt("screen_id");
                Boolean deleted = outerObj.getInt("deleted") == 1 ? true : false;
                String updatedTs = outerObj.getString("updated_ts");
                String createdTs = outerObj.getString("created_ts");
                int sequenceId = outerObj.getInt("sequence_id");
                int duration = outerObj.getInt("duration");

                Media media = new Media(id, dataDeleted, LYDateString.stringToDate(dataUpdatedTs, 3),
                        LYDateString.stringToDate(dataCreatedTs, 3), mediaName, adminId, userId, userName,
                        LYDateString.stringToDate(startDate, 3),LYDateString.stringToDate(endDate, 3),
                        showType, statusB, mediaType, elementId, mediaUrl, durationSec, version, "",
                        screenMediaId, screenId, deleted, LYDateString.stringToDate(updatedTs, 3),
                        LYDateString.stringToDate(createdTs, 3), sequenceId, duration);
                dataList.add(media);
            }

            mediaList.setStatus(status);
            mediaList.setService(service);
            mediaList.setType(type);
            mediaList.setMediaArray(dataList);
            mediaList.setAdsImageUrl(adsImageUrl);
            mediaList.setAdsImageUrl2(adsImageUrl2);
            mediaList.setAdsVideoUrl1(adsVideoUrl1);
            mediaList.setAdsVideoUrl2(adsVideoUrl2);
            mediaList.setPhotoShowImageUrl1(photoShowImageUrl1);
            mediaList.setPhotoShowImageUrl2(photoShowImageUrl2);
            mediaList.setStoreImageUrl1(storeImageUrl1);
            mediaList.setStoreImageUrl2(storeImageUrl2);
            mediaList.setOfficeImageUrl1(officeImageUrl1);
            mediaList.setOfficeImageUrl2(officeImageUrl2);
            mediaList.setDishImageUrl1(dishImageUrl1);
            mediaList.setDishImageUrl2(dishImageUrl2);
            mediaList.setTypeImageUrl1(typeImageUrl1);
            mediaList.setTypeImageUrl2(typeImageUrl2);

        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, new String(" There are " + mediaList.getMediaArray().size() + " pictures"));

        downloadActivity.fileSize = mediaList.getMediaArray().size() +1;
        return mediaList;
    }
    //just used for testing get database data
   /* public void sendStringRequest1() {
        ArrayList<Media> data=mediaDB.getMedias() ;
        mediaList = new MediaList();
        mediaList.setMediaArray(data);

        new DownloadMedia().execute();
    }*/



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



    public void sendStringRequest(final String screenId) {
        this.screenId = screenId;
        currentMedia = 0;
        isUpdate = false;

        totalMedias = 0;

       /* progressBar = new ProgressDialog(downloadActivity);
        progressBar.setMessage(jsonString);
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
                    double percentage=(double)(currentMedia + 1)/totalMediaList;
                    progressBarStatus = (int)(percentage*100+0.5);
                    if(totalMedias > 0){
                        totalMediaList = totalMedias;
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

        String searchUrl = "http://www.eadate.com:8080/screenshow/restful/screenMediaOne/search";
        StringRequest postReq = new StringRequest(Request.Method.POST, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        jsonString = response;
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
                params.put("searchtype", "newScreenSearch");
                params.put("screen_id", screenId);
                //params.put("media_type", "7");  //
                params.put("updated_ts", "1982-1-28 12:12:12");//"1982-11-27 12:12:12"
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
            mediaList=generateObjectFromJson(jsonString);
            //downloadActivity.downLoadFileSize=0;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("News reader", "Media:Feed downloaded");
            if(currentMedia < mediaList.getMediaArray().size()){
                if(mediaList.getMediaArray().size()>0)
                {
                    if(isUpdate){
                        sharedPref = AppController.getInstance().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                        editor = sharedPref.edit();
                        Calendar calendar = Calendar.getInstance();
                        Date currentDate = calendar.getTime();
                        String newMediaListUpdateTs = LYDateString.dateToString(currentDate, 3);
                        editor.putString(Constants.KEY_MEDIALIST_UPDATE_TS, newMediaListUpdateTs);
                        editor.commit();
                    }
                    new DownloadMedia().execute();
                }
            }
        }
    }


    class DownloadMedia extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            sendMediaRequests(mediaList);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("News reader", "Feed downloaded");

        }
    }

    public void sendMediaRequest(Media media, String videoUrl) {
        String name = media.getMediaUrl();
        this.tempMedia = media;
        String url = videoUrl + name;

        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File filePath = null;

        //0-ad_image,1-ad_video, 2-photo_show, 3-store, 5-office, 6-dish, 7-storetype
        AppController appController = AppController.getInstance();
        //String path = appController.TARGET_BASE_PATH;
        if (media.getMediaType()==1){
            DownloadVedio downloadVedio = new DownloadVedio(url,name,media,appController.TARGET_BASE_PATH);
            downloadVedio.execute();
        }
        else {
            if(media.getMediaType() == 0){
                url = "http://eadate.com/screenshow/uploads/images/advertisement/image/" + name;
                filePath = new File(directory, "advertisement");
            }else if(media.getMediaType() == 2){
                url = "http://eadate.com/screenshow/uploads/images/photoshow/" + name;
                filePath = new File(directory, "photoshow");
            } else if(media.getMediaType() == 3){
                url = "http://eadate.com/screenshow/uploads/images/store/" + name;
                filePath = new File(directory, "store");
            }else if(media.getMediaType() == 5){
                url = "http://eadate.com/screenshow/uploads/images/office/" + name;
                filePath = new File(directory, "office");
            }else if(media.getMediaType() == 6){
                url = "http://eadate.com/screenshow/uploads/images/dish/" + name;
                filePath = new File(directory, "dish");
            }else if(media.getMediaType() == 7){
                url = "http://eadate.com/screenshow/uploads/images/type/" + name;
                filePath = new File(directory, "type");
            }
            filePath.mkdir();

            if(filePath != null) {
                Log.d("Download Image", "Start Downloaded image:"+name);
                DownloadVedio downloadVedio = new DownloadVedio(url, name, media, filePath.getAbsolutePath()+"/");
                downloadVedio.execute();
            }

//            final String tempImageName1 = name;
//            final Media tempMedia = media;
//            ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
//                @Override
//                public void onResponse(Bitmap response) {
//                    SaveImages saveImages = new SaveImages(response, tempImageName1, tempMedia);
//                    saveImages.execute();
//                }
//            },  Constants.IMAGE_MAX_WIDTH, Constants.IMAGE_MAX_HEIGHT, Bitmap.Config.RGB_565,
//            new Response.ErrorListener(){
//                @Override
//                public void onErrorResponse(VolleyError error){
//                    Log.d("Medialist", "Missing one pic:" + mediaList.getMediaArray().get(currentMedia).getMediaName());
//                    if(currentMedia < mediaList.getMediaArray().size()){
//                        currentMedia++;
//                        getTheNextMediaImgOrVid(currentMedia);
//                    }
//                }
//            });
//            AppController.getInstance().addToRequestQueue(ir);
        }
    }



    class DownloadVedio extends AsyncTask<Void, Void, Void> {
        String downloadUrl;
        String vedioName;
        Media media;
        String vedioPath;
        String externalPath;

        DownloadVedio(String url,String name,Media media,String externalPath){
            super();
            this.downloadUrl = url;
            this.vedioName =name;
            this.media=media;
            this.externalPath = externalPath;
        }

        @Override
        protected Void doInBackground(Void... params) {

            //String url = "http://eadate.com/screenshow/uploads/images/advertisement/video/video1.mp4";

            File file = new File(externalPath,vedioName);
            if (!file.exists()) {
                fileDownloadHelper.newDownloadFile(downloadUrl, externalPath+vedioName);
            }



//            OutputStream output=null;
//            InputStream input=null;
//            try {
//                URL url=new URL(downloadUrl);
//                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
//
//
//               // ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
//                // path to /data/data/yourapp/app_data/vedioDir
//                //File directory = cw.getDir("vedioDir", Context.MODE_WORLD_WRITEABLE);
//                //vedioPath=directory.getAbsolutePath();
//
//                System.out.println("@@@@@@vedioPath=" + vedioPath);
//
//                String newFileName = this.externalPath + this.vedioName;
//                File vedioFile=new File(newFileName);
//                input=conn.getInputStream();
//
//
//
//
//                vedioFile.createNewFile();
//                output=new FileOutputStream(newFileName);
//
//                //vedioFile.setReadable(true,false);
//
//
//                //yao save to external
//
//
//                //output=new FileOutputStream(vedioFile);//old save to internal
//
//                byte[] buffer=new byte[4*1024];
//                while(input.read(buffer)!=-1){
//                    output.write(buffer);
//                }
//                output.flush();
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }finally{
//                try {
//                    input.close();
//                    output.close();
//                    output = null;
//                    System.out.println("@@@@@@@download success vedioName= "+vedioName);
//                } catch (IOException e) {
//                    System.out.println("fail");
//                    e.printStackTrace();
//                }
//            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            Log.d("Download Media", "After Media downloaded");
            saveToDatabase(media, vedioPath);
        }
    }

    class SaveImages extends AsyncTask<Void, Void, Void> {
        Bitmap image;
        String imageName;
        String imagePath;
        Media media;

        public SaveImages(Bitmap image, String imageName, Media media) {
            super();
            this.image = image;
            this.imageName = imageName;
            this.media = media;
        }

        @Override
        protected Void doInBackground(Void... params) {
            imagePath = saveImageInternalMemory(image, imageName, media);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("News reader", "Feed downloaded");
            saveToDatabase(media, imagePath);
        }
    }

    public String saveImageInternalMemory (Bitmap bitmapImage, String imageName, Media media){
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File filePath = null;

        if(tempMedia.getMediaType() == 0){
            filePath = new File(directory, "advertisement");
        }else if(tempMedia.getMediaType() == 2){
            filePath = new File(directory, "photoshow");
        } else if(tempMedia.getMediaType() == 3){
            filePath = new File(directory, "store");
        }else if(tempMedia.getMediaType() == 5){
            filePath = new File(directory, "office");
        }else if(tempMedia.getMediaType() == 6){
            filePath = new File(directory, "dish");
        }else if(tempMedia.getMediaType() == 7){
            filePath = new File(directory, "type");
        }
        filePath.mkdir();

        File myFile = new File(filePath, imageName);
        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(myFile);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("success: ", directory.getAbsolutePath()+"");
        return directory.getAbsolutePath();
    }

    public void sendMediaRequests(MediaList mediaList)
    {
        allMedias = mediaList.getMediaArray();
        totalMedias = allMedias.size();
        currentMedia = 0;
        getTheNextMediaImgOrVid(currentMedia);
    }


    public void saveToDatabase(Media media, String mediaPath)
    {
        media.setMediaLocalUrl(mediaPath);

        if(isUpdate){
            Log.d("Download Media", "update database");
            mediaDB.updateMedia(media);
            //downloadActivity.downLoadFileSize = downloadActivity.downLoadFileSize +1;
            //downloadActivity.sendMsg(3);

//            String logString = currentMedia + " pictures have been updated." + mediaList.getMediaArray().get(currentMedia).getMediaUrl();
//            Log.d(TAG, logString);

            if(currentMedia < totalMedias - 1 ){
                getTheNextMediaImgOrVid(currentMedia);
                currentMedia++;
            }
            else{
                currentMedia = 0;

                int currentActivity = sharedPref.getInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
                if(currentActivity == Constants.CURRENT_MEDIA_SHOW_ACTIVITY){
                    AppController.getInstance().generalActivity.refreshActivityContent();
                    Log.d(TAG, "content updated");
                }
            }
        }else{
            mediaDB.updateMedia(media);
            //downloadActivity.downLoadFileSize = downloadActivity.downLoadFileSize +1;
            downloadActivity.sendMsg(3);
            Log.d(TAG, "screen id +++++++++++++++++++++++++++++++++++++++++++++++++" + String.valueOf(media.getScreenId()));
            downloadActivity.progressBar.setVisibility(View.VISIBLE);

            if(currentMedia < totalMedias ){
                getTheNextMediaImgOrVid(currentMedia);

                String logString = new String(currentMedia+" pictures have been saved." +
                        mediaList.getMediaArray().get(currentMedia).getMediaUrl());
                Log.d(TAG, logString);

                currentMedia++;
            }
            else{
                downloadActivity.turnOnAllButtons();
                downloadActivity.sendMsg(2);
                //Toast.makeText(context, "数据下载完成， 请再次点击 Enter 使用!", Toast.LENGTH_LONG).show();
                currentMedia = 0;
            }
        }
    }

    public void getTheNextMediaImgOrVid(int position){
        Media media = allMedias.get(position);
        if (media.getMediaType() == 1){
            sendMediaRequest(media, mediaList.getAdsVideoUrl1());
        }
        else{
            sendMediaRequest(media, mediaList.getAdsImageUrl());
        }
    }



    public void mediaListUpdateRequest(final String screenId,final String lastUpdateTs) {
        this.screenId = screenId;
        isUpdate = true;

        String searchUrl = "http://www.eadate.com:8080/screenshow/restful/screenMediaOne/search";
        StringRequest postReqToUpdateMediaList = new StringRequest(Request.Method.POST, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MediaList mediaList = generateObjectFromJson(response);

                        if(mediaList.getMediaArray().size() > 0) {
                            Log.d("mediaList updated: ", "found new media from the request!");
                            for (Media med : mediaList.getMediaArray()) {
                                mediaDB.updateMedia(med);

                                if (med.getMediaType() == 1){
                                    sendMediaRequest(med, mediaList.getAdsVideoUrl1());
                                } else{
                                    sendMediaRequest(med, mediaList.getAdsImageUrl());
                                }

                                Log.d("MEDIA Update", "medias have been updated." + med.getMediaName());
                            }

                            sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                            editor = sharedPref.edit();
                            Calendar calendar = Calendar.getInstance();
                            Date currentDate = calendar.getTime();
                            String newSubtitleUpdateTs = LYDateString.dateToString(currentDate, 3);
                            editor.putString(Constants.KEY_MEDIALIST_UPDATE_TS, newSubtitleUpdateTs);
                            editor.commit();

                            BaseActivity baseActivity = AppController.getInstance().getGeneralActivity();
                            if(baseActivity != null) {
                                baseActivity.refreshActivityContent();
                                Log.d("mediaList updated: ", "advertisement activity refreshed!");
                            }


                        }
//                        AppController.getInstance().getGeneralActivity().refreshActivityContent();

                        /*System.out.println(response);
                        jsonString = response;
                        new ParseJson().execute();*/
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
                params.put("searchtype", "newScreenSearch");
                params.put("screen_id", screenId);
                params.put("media_type", "7");
                params.put("updated_ts", lastUpdateTs);//"1982-11-27 12:12:12"
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postReqToUpdateMediaList);
    }
}
