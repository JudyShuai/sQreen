package com.somoplay.screenshow.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.CarAndHouseDB;
import com.somoplay.screenshow.database.MediaDB;
import com.somoplay.screenshow.database.StoreBranchDB;
import com.somoplay.screenshow.database.StoreDishDB;
import com.somoplay.screenshow.database.StoreMediaDB;
import com.somoplay.screenshow.database.StoreOfficeDB;
import com.somoplay.screenshow.database.StoreTypeNameDB;
import com.somoplay.screenshow.database.SubtitleDB;
import com.somoplay.screenshow.model.CarAndHouse;
import com.somoplay.screenshow.model.Media;
import com.somoplay.screenshow.model.StoreDish;
import com.somoplay.screenshow.model.StoreOffice;
import com.somoplay.screenshow.service.MediaListRequestService;
import com.somoplay.screenshow.webrequest.CarAndHouseRequest;
import com.somoplay.screenshow.webrequest.MediaListRequest;
import com.somoplay.screenshow.webrequest.StoreBranchRequest;
import com.somoplay.screenshow.webrequest.StoreDishRequest;
import com.somoplay.screenshow.webrequest.StoreMediaRequest;
import com.somoplay.screenshow.webrequest.StoreOfficeRequest;
import com.somoplay.screenshow.webrequest.StoreTypeNameRequest;
import com.somoplay.screenshow.webrequest.SubtitleListRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by JudyShuai on 15-06-21.
 */
public class DownloadActivity extends Activity {
    Button mShowScreenDetail;
    Button mDownloadAll;
    Button mStartService;
//    Button mSetting;
    Button mEnter;
    TextView mscreenNameText;
    //TextView mShowScreenInfo;

    private AppController appController;
    //private StoreOfficeDB storeOfficeDB;
    private StoreBranchDB storeBranchDB;
    private StoreTypeNameDB storeTypeNameDB;
    private CarAndHouseDB carAndHouseDB;
    //private StoreDishDB storeDishDB;
    private SubtitleDB subtitleDB;
    private MediaDB mediaDB;
    //private StoreMediaDB mStoreMediaDB;
    public ProgressBar progressBar;
    public TextView tv;

    //public StoreDishRequest storeDishRequest;
    private StoreMediaRequest mStoreMediaRequest;
    private StoreBranchRequest storeBranchRequest;
    private StoreTypeNameRequest storeTypeNameRequest;
    //private StoreOfficeRequest storeOfficeRequest;
    private CarAndHouseRequest carAndHouseRequest;
    private SubtitleListRequest subtitleListRequest;
    private MediaListRequest mediaListRequest;
    private SharedPreferences sharedPref;

    private String storeId;
    private int storeIdInt;
    private String screenId;
    private int showContentType;
    public boolean isDownload;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_download_data);

        //get current system time
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
        String currentDate = format.format(new java.util.Date());
        Log.d("*The current time is : ", currentDate);


        mShowScreenDetail=(Button)findViewById(R.id.detail_button);
        mDownloadAll=(Button)findViewById(R.id.download_button);
        mStartService = (Button)findViewById(R.id.update_button);
//        mSetting= (Button)findViewById(R.id.setting_button);
        mEnter = (Button)findViewById(R.id.enter_button);
        tv = (TextView) findViewById(R.id.download_Percent);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mscreenNameText=(TextView)findViewById(R.id.screeNameText);

        sharedPref  = AppController.getInstance().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        SharedPreferences.Editor editor= sharedPref.edit();
        editor.putString(Constants.currentTime, currentDate);
        editor.putString("Activity_Target", "Activity_Login");
        editor.commit();

        String getScreenName = sharedPref.getString(Constants.KEY_SCREEN_NAME, "screen_name");
        showContentType = sharedPref.getInt(Constants.KEY_SHOW_CONTENT_TYPE, 1);
        storeIdInt = sharedPref.getInt(Constants.KEY_STORE_ID, 1);
        storeId = Integer.toString(sharedPref.getInt(Constants.KEY_STORE_ID, 1));
        screenId = Integer.toString(sharedPref.getInt(Constants.KEY_SCREEN_ID, 1));

        mscreenNameText.setText(getScreenName);
        appController = AppController.getInstance();
        isDownload = false;

        storeBranchDB =new StoreBranchDB(appController);
        storeBranchRequest=new StoreBranchRequest(appController);

        subtitleDB =new SubtitleDB(appController);
        subtitleListRequest = new SubtitleListRequest(this,appController);

        mediaDB =new MediaDB(appController);
        mediaListRequest = new MediaListRequest(this,appController);

        storeTypeNameDB=new StoreTypeNameDB(appController);
        storeTypeNameRequest=new StoreTypeNameRequest(this, appController);

        //storeDishDB=new StoreDishDB(appController);
        //storeDishRequest=new StoreDishRequest(this, appController);

        //mStoreMediaDB = new StoreMediaDB(appController);
        mStoreMediaRequest  = new StoreMediaRequest(this, appController);

        //storeOfficeDB = new StoreOfficeDB(appController);
        //storeOfficeRequest = new StoreOfficeRequest(this, appController);

        carAndHouseDB = new CarAndHouseDB(appController);
        carAndHouseRequest = new CarAndHouseRequest(this, appController);

        mShowScreenDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DownloadActivity.this, ShowScreenInfoAcrivity.class);
                startActivity(intent);
            }
        });

        mDownloadAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                turnOffAllButtons();

                downloadData();

                //mEnter.setClickable(true);
            }
        });



        mStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(appController,"Start Service successfully!!!",Toast.LENGTH_SHORT).show();
                Intent service = new Intent(DownloadActivity.this, MediaListRequestService.class);
                startService(service);
            }
        });



//        mSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(DownloadActivity.this, SettingActivity.class);
//                startActivity(intent);
//            }
//        });



        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(showContentType == 1){
                    int screenId = sharedPref.getInt(Constants.KEY_SCREEN_ID, 0);
                    ArrayList<Media>  mediaList = mediaDB.getAdvertisementMedias(screenId);
                    if(mediaList.size() == 0){
                        new AlertDialog.Builder(DownloadActivity.this)
                                .setTitle("Oops!")
                                .setMessage("No content! Please download first or make sure have content inside")
                                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                    }else{
                        Intent intent = new Intent(DownloadActivity.this, MediaShowActivity.class);
                        intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, true);
                        startActivity(intent);
                    }
                }

                else if(showContentType == 2){
                    ArrayList<Media>  mediaList = mediaDB.getUserDrawingPhotos();
                    if(mediaList.size() == 0){
                        new AlertDialog.Builder(DownloadActivity.this)
                                .setTitle("Oops!")
                                .setMessage("No content! Please download first or make sure have content inside")
                                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                    }else{
                        Intent intent = new Intent(DownloadActivity.this, MediaShowActivity.class);
                        intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
                        intent.putExtra(Constants.KEY_IS_USERPHOTO, true);
                        startActivity(intent);
                    }
                }

//                else if(showContentType == 4){
//                    ArrayList<StoreDish>  dishes = storeDishDB.getStoreDishes();
//                    if(dishes.size() == 0){
//                        new AlertDialog.Builder(DownloadActivity.this)
//                                .setTitle("Oops!")
//                                .setMessage("No content! Please download first or make sure have content inside")
//                                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                    }
//                                }).show();
//                    }else{
//                        Intent intent = new Intent(DownloadActivity.this, RestaurantShowActivity.class);
//                        startActivity(intent);
//                    }
//                }
//
//                else if(showContentType == 5){
//                    ArrayList<StoreOffice>  storeOffices = storeOfficeDB.getStoreOffices();
//                    if(storeOffices.size() == 0){
//                        new AlertDialog.Builder(DownloadActivity.this)
//                                .setTitle("Oops!")
//                                .setMessage("No content! Please download first or make sure have content inside")
//                                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                    }
//                                }).show();
//                    }else{
//                        Intent intent = new Intent(DownloadActivity.this, OfficeShowActivity.class);
//                        startActivity(intent);
//                    }
//                }

                else if(showContentType == 6){
                    if (storeIdInt == 0) {
                        new AlertDialog.Builder(DownloadActivity.this)
                                .setTitle("Oops!")
                                .setMessage("No content to show!")
                                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                    }else {
                        ArrayList<CarAndHouse> carAndHouses = carAndHouseDB.getCarAndHouseesByStoreId(storeId);
                        if (carAndHouses.size() == 0) {
                            new AlertDialog.Builder(appController)
                                    .setTitle("Oops!")
                                    .setMessage("No content! Please download first or make sure have content inside")
                                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }).show();
                        } else {
                            Intent intent = new Intent(DownloadActivity.this, CarAndHouseActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    public void turnOnAllButtons(){
        mShowScreenDetail.setClickable(true);
        mDownloadAll.setClickable(true);
        mEnter.setClickable(true);
        mStartService.setClickable(true);
//        mSetting.setClickable(true);
        progressBar.setVisibility(View.GONE);
    }

    public void turnOffAllButtons(){
        mShowScreenDetail.setClickable(false);
        mDownloadAll.setClickable(false);
        mEnter.setClickable(false);
        mStartService.setClickable(false);
//        mSetting.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    public int fileSize;
    public int downLoadFileSize;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {//定义一个Handler，用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted())
            {
                progressBar.setMax(fileSize);
                switch (msg.what)
                {
                    case 1://download advertisement
                        downLoadFileSize = downLoadFileSize +1;
                        progressBar.setProgress(downLoadFileSize);
                        int result = downLoadFileSize * 100 / fileSize;
                        tv.setText("Download Ads Info:   " +downLoadFileSize);
                        break;
                    case 2:
                        Toast.makeText(appController, "文件下载完成, 请点击 Enter 使用!", Toast.LENGTH_SHORT).show();
                        break;
                    case 3://download media
                        downLoadFileSize = downLoadFileSize +1;
                        progressBar.setProgress(downLoadFileSize);
                        int result2 = downLoadFileSize * 100 / fileSize;
                        tv.setText("Download Photo Info:   " +result2+"%");
                        break;
                    case 4://download store media
                        downLoadFileSize = downLoadFileSize +1;
                        progressBar.setProgress(downLoadFileSize);
                        int result3 = downLoadFileSize * 100 / fileSize;
                        tv.setText("Download General Store Info:   " +result3+"%");
                        break;
                    case -1:
                        String error = msg.getData().getString("error");
                        Toast.makeText(appController, error, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };

    public void sendMsg(int flag) {
        Message msg = new Message();
        msg.what = flag;
        handler.sendMessage(msg);
    }

    public void downloadData() {

            isDownload = true;
            Toast.makeText(appController, "Downloading data, please wait for a few minutes, thanks!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            sendMsg(0);

            if (showContentType == 1) {
                //subtitleListRequest.sendStringRequest(screenId);
                downLoadFileSize = 0;
                subtitleListRequest.sendStringRequest(screenId, "2000-02-17 23:29:38.0");
                downLoadFileSize = 0;
                mediaListRequest.sendStringRequest(screenId);
            } else if (showContentType == 2) {
                downLoadFileSize = 0;
                mediaListRequest.sendStringRequest(screenId);
            }
            /*else if (showContentType == 4 || showContentType == 5) {
                if (storeIdInt == 0) {
                    new AlertDialog.Builder(this)
                            .setTitle("Oops!")
                            .setMessage("No content to show!")
                            .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else {
                    storeBranchRequest.sendStoreBranchRequest(storeId);
                    storeTypeNameRequest.sendMenuRequest(storeId);
                }
            }*/
            else if(showContentType == 6){
                if (storeIdInt == 0) {
                    new AlertDialog.Builder(this)
                            .setTitle("Oops!")
                            .setMessage("No content to show!")
                            .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }else {
                    fileSize=1;
                    downLoadFileSize = 0;
                    mStoreMediaRequest.sendStringRequest(storeId);
                    storeBranchRequest.sendStoreBranchRequest(storeId);
                    storeTypeNameRequest.sendMenuRequest(storeId);
                }
            }
    }



    @Override
    public void onPause(){
        super.onPause();
        if(isDownload == true){
            isDownload = false;
        }
    }

}
