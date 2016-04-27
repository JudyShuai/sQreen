package com.somoplay.screenshow.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.activity.ChooseScreenActivity;
import com.somoplay.screenshow.activity.DownloadActivity;
import com.somoplay.screenshow.activity.MediaShowActivity;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.DBHelper;
import com.somoplay.screenshow.database.MediaDB;
import com.somoplay.screenshow.database.SubtitleDB;
import com.somoplay.screenshow.videodownload.FileDownloadHelper;
import com.somoplay.screenshow.webrequest.ChooseScreenRequest;
import com.somoplay.screenshow.webrequest.MediaListRequest;
import com.somoplay.screenshow.webrequest.NewStoreMediaRequest;
import com.somoplay.screenshow.webrequest.StoreBranchRequest;
import com.somoplay.screenshow.webrequest.StoreTypeNameRequest;
import com.somoplay.screenshow.webrequest.SubtitleListRequest;

import java.util.Timer;
import java.util.TimerTask;

public class MediaListRequestService extends Service {

    private AppController appController;
    private Timer timer;
    private StoreBranchRequest storeBranchRequest;
    private StoreTypeNameRequest storeTypeNameRequest;
    private MediaListRequest mediaListRequest;
    private SubtitleListRequest subtitleListRequest;
    private ChooseScreenRequest chooseScreenRequest;
    private NewStoreMediaRequest mNewStoreMediaRequest;
    private ChooseScreenActivity chooseScreenActivity;
    private MediaDB mediaDB;
    private SubtitleDB subtitleDB;
    private FileDownloadHelper fileDownloadHelper = new FileDownloadHelper();
    private boolean firstRound = true;
    private String branchUpdateTs;
    private String storeTypeNameUpdateTs;
    private String subtitleUpdateTs;
    private String mediaListUpdateTs;
    private String storeMediaUpdateTs;
    SharedPreferences sharedPref;

    @Override
    public void onCreate() {
        Log.d("News reader", "Service created");
        appController = AppController.getInstance();
        DBHelper dbhelper = DBHelper.getInstance(appController, Constants.DB_NAME,
                null, Constants.DB_VERSION);

        mediaDB = new MediaDB(appController);
        subtitleDB = new SubtitleDB(appController);
        storeBranchRequest = new StoreBranchRequest( appController);
        storeTypeNameRequest = new StoreTypeNameRequest(new DownloadActivity(), appController);
        mNewStoreMediaRequest = new NewStoreMediaRequest(new DownloadActivity(), appController);
        mediaListRequest = new MediaListRequest(new DownloadActivity(), appController);
        subtitleListRequest = new SubtitleListRequest(new DownloadActivity(), appController);
        chooseScreenRequest = new ChooseScreenRequest(new ChooseScreenActivity());

        //initialize the first update time:

        startTimer();
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("News reader", "Service started");
        return START_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("News reader", "Service bound - not used!");
        return null;
    }
    
    @Override
    public void onDestroy() {
        Log.d("News reader", "Service destroyed");
        stopTimer();
    }
    
    private void startTimer() {

        TimerTask task = new TimerTask() {
            
            @Override
            public void run() {

                sharedPref = appController.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                String storeId = Integer.toString(sharedPref.getInt(Constants.KEY_STORE_ID, 1));
                String adminId = String.valueOf(sharedPref.getInt(Constants.KEY_ADMIN_ID,000) );

                int showContentType = sharedPref.getInt(Constants.KEY_SHOW_CONTENT_TYPE, 1);
                int screenIdInt = sharedPref.getInt(Constants.KEY_SCREEN_ID, 1);
                String screenId = Integer.toString(screenIdInt);


//                sharedPref = appController.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                branchUpdateTs = sharedPref.getString(Constants.KEY_BRANCH_UPDATE_TS, "2014-02-17 16:34:22.0");
                storeTypeNameUpdateTs = sharedPref.getString(Constants.KEY_STORE_TYPE_NAME_UPDATE_TS, "2014-02-18 22:57:44.0");
                subtitleUpdateTs = sharedPref.getString(Constants.KEY_SUBTITLES_UPDATE_TS, "2014-02-17 23:29:38.0");
                mediaListUpdateTs = sharedPref.getString(Constants.KEY_MEDIALIST_UPDATE_TS, "2014-11-27 12:12:12.0");
                storeMediaUpdateTs = sharedPref.getString(Constants.KEY_STORE_MEDIAS_UPDATE_TS, "1998-11-27 00:00:00.0");

                chooseScreenRequest.sendStringRequest(screenId, true);
               /* if(!firstRound){ //??????????????????????
                    chooseScreenRequest.sendStringRequest(screenId, true);
                }*/
                if(showContentType == 1){
                    Log.d("Subtitle updated", "service refreshed after 1 minute");
                    subtitleListRequest.updateSubtitleRequest(screenId, subtitleUpdateTs);
                    mediaListRequest.mediaListUpdateRequest(screenId, mediaListUpdateTs);
                }

                else if(showContentType == 2){
                    Log.d("mediaList Updated", "service refreshed after 1 minute");
                    mediaListRequest.mediaListUpdateRequest(screenId, mediaListUpdateTs);
                }

                /*else if(showContentType == 4 || showContentType == 5){
                    Log.d("storeTypeName Updated", "service refreshed after 1 minute");
                    storeBranchRequest.storeBranchUpdateRequest(storeId, branchUpdateTs);
                    storeTypeNameRequest.updateStoreTypeNameRequest(storeId, storeTypeNameUpdateTs);
                }*/

                else if(showContentType == 6){
                    Log.d("Store photos update", " service refreshed after 1 minute");
                    mNewStoreMediaRequest.mediaUpdateRequest(adminId, storeMediaUpdateTs);
                    storeBranchRequest.storeBranchUpdateRequest(storeId, branchUpdateTs);
                    storeTypeNameRequest.updateStoreTypeNameRequest(storeId, storeTypeNameUpdateTs);
                }
            }
        };

        timer = new Timer(true);
        int delay = 1000 * 1 * 60;      // 1 minute
        int interval = 1000 * 1 * 60;   // 1 minute
        timer.schedule(task, delay, interval);

        firstRound = false;
    }
    
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }






    private void sendNotification(String text) {
        // create the intent for the notification
        Intent notificationIntent = new Intent(this, MediaShowActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // create the pending intent
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, flags);

        // create the variables for the notification
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "Updated news feed is available";
        CharSequence contentTitle = getText(R.string.app_name);
        CharSequence contentText = text;

        // create the notification and set its data
        Notification notification =
                new NotificationCompat.Builder(this)
            .setSmallIcon(icon)
            .setTicker(tickerText)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build();

        // display the notification
        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        final int NOTIFICATION_ID = 1;
        manager.notify(NOTIFICATION_ID, notification);
    }



  /*  private void startTimer() {

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Log.d("media show", "mediaList request");
                MediaShowActivity mediaShowActivity = appController.getMediaShowActivity();
                if (flag == 1 || flag ==3) {

                    if(mediaShowActivity !=null)
                    {
                        if (flag == 1)
                        {
                            //从服务器获取数据并存到数据库
                            mediaListRequest.sendStringRequest("2015-6-21 12:12:12");
                            flag = 2;
                        }
                        else if (flag == 3){
                            Date date = new Date();
                            String dateString = LYDateString.dateToString(date,3);
                            mediaListRequest.sendStringRequest(dateString);
                            flag = 2;
                        }
                        Log.d("Timer:", "media service request");
                    }
                }
                else if (flag == 2) {
                    flag = 4;
                    Log.d("Timer:", "Media DatabaseRequest");
                    //need to change to get data based on the current date

                    //从数据库获取数据并存入内存
                    ArrayList<Media> data = mediaDB.getAdvertisementMedias();
                    if (data.size()>0)
                    {
                        ArrayList<HashMap<String, String>> newimageNamesArray = new ArrayList<>();

                        for(int i=0 ; i<data.size(); i++)
                        {
                            Media media = data.get(i);
                            HashMap<String, String> hashMapForMediaType = new HashMap<>();
                            hashMapForMediaType.put("url",media.getMediaUrl() );
                            hashMapForMediaType.put("type", String.valueOf(media.getMediaType()) );
                            hashMapForMediaType.put("duration_sec", String.valueOf(media.getDurationSec()) );//defined by media
                            hashMapForMediaType.put("duration", String.valueOf(media.getDuration()) );//defined by media screen one
                            newimageNamesArray.add(hashMapForMediaType);
                        }

                        mediaShowActivity.updateImages(newimageNamesArray);
                    }
                }

                else if (flag == 4){
                    subtitleListRequest.sendStringRequest("1");
                    flag = 5;
                    Log.d("Timer:", "subtitle service request");
                }else if (flag == 5) {
                    ArrayList<Subtitle> data = subtitleDB.getSubtitles();
                    int i=1;
                    if (data.size()>0) {
                        mediaShowActivity.updateTexts(data);
                        for(Subtitle sub:data) {
                            System.out.println(sub.toString());
                        }
                    }

                    flag = 3;
                    Log.d("Timer:", "Subtitle DatabaseRequest");
                }



                *//*
                RSSFeed newFeed = io.readFile();
                Log.d("News reader", "File read");

                // if new feed is newer than old feed
                if (newFeed.getPubDateMillis() > app.getFeedMillis()) {
                    Log.d("News reader", "Updated feed available.");

                    // update app object
                    app.setFeedMillis(newFeed.getPubDateMillis());

                    // display notification
                    sendNotification("Select to view updated feed.");

                    // send broadcast
                    Intent intent = new Intent(RSSFeed.NEW_FEED);
                    LocalService.this.sendBroadcast(intent);
                }
                else {
                    Log.d("News reader", "Updated feed NOT available.");
                }
                *//*
            }
        };

        timer = new Timer(true);
        int delay = 1000000 * 5 * 1;      // 5 second
        int interval = 1000 * 5 * 1;   // 1 minute
        timer.schedule(task, delay, interval);
    }*/
}