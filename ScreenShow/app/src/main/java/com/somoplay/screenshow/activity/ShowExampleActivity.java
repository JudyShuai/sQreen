package com.somoplay.screenshow.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.CarAndHouseDB;
import com.somoplay.screenshow.database.MediaDB;
import com.somoplay.screenshow.database.StoreBranchDB;
import com.somoplay.screenshow.database.StoreDishDB;
import com.somoplay.screenshow.database.StoreOfficeDB;
import com.somoplay.screenshow.database.StoreTypeNameDB;
import com.somoplay.screenshow.database.SubtitleDB;
import com.somoplay.screenshow.model.Media;
import com.somoplay.screenshow.model.StoreDish;
import com.somoplay.screenshow.model.StoreOffice;
import com.somoplay.screenshow.webrequest.CarAndHouseRequest;
import com.somoplay.screenshow.webrequest.MediaListRequest;
import com.somoplay.screenshow.webrequest.StoreBranchRequest;
import com.somoplay.screenshow.webrequest.StoreDishRequest;
import com.somoplay.screenshow.webrequest.StoreOfficeRequest;
import com.somoplay.screenshow.webrequest.StoreTypeNameRequest;
import com.somoplay.screenshow.webrequest.SubtitleListRequest;

import java.util.ArrayList;

/**
 * Created by Shaohua on 8/6/2015.
 */
public class ShowExampleActivity extends Activity implements ChangeSampleProgressBar {

    public static boolean isDownload = false;

    private Button mRestaurantButton;
    private Button mBuildingButton;
    private Button mAdvertisementButton;
    private Button mPhotoShowButton;
    private String backgroundRgbColor,textRgbColor;
    private ChangeSampleProgressBar changeSampleProgressBar;

    private LinearLayout linearLayout;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;


    private AppController appController;
    private StoreOfficeDB storeOfficeDB;
    private StoreBranchDB storeBranchDB;
    private StoreTypeNameDB storeTypeNameDB;
    private CarAndHouseDB carAndHouseDB;
    private StoreDishDB storeDishDB;
    private SubtitleDB subtitleDB;
    private MediaDB mediaDB;

    private StoreDishRequest storeDishRequest;
    private StoreBranchRequest storeBranchRequest;
    private StoreTypeNameRequest storeTypeNameRequest;
    private StoreOfficeRequest storeOfficeRequest;
    private CarAndHouseRequest carAndHouseRequest;
    private SubtitleListRequest subtitleListRequest;
    private MediaListRequest mediaListRequest;

    private String storeId;
    private String screenId;
    private int showContentType;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_example);

        mRestaurantButton = (Button) findViewById(R.id.restaurant);
        mBuildingButton = (Button) findViewById(R.id.building);
        mAdvertisementButton = (Button) findViewById(R.id.advertisement);
        mPhotoShowButton = (Button) findViewById(R.id.photoShow);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        pref = this.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        backgroundRgbColor = pref.getString(Constants.KEY_RGBCOLOR_BACKGROUND, null);
        textRgbColor = pref.getString(Constants.KEY_RGBCOLOR_TEXT, null);

        appController = AppController.getInstance();

        storeBranchDB =new StoreBranchDB(appController);
        storeBranchRequest=new StoreBranchRequest(appController);

        subtitleDB =new SubtitleDB(appController);
        subtitleListRequest = new SubtitleListRequest(null, appController);

        mediaDB =new MediaDB(appController);
        mediaListRequest = new MediaListRequest(null,appController);

        storeTypeNameDB=new StoreTypeNameDB(appController);
        storeTypeNameRequest=new StoreTypeNameRequest(null, appController);

        storeDishDB=new StoreDishDB(appController);
        storeDishRequest=new StoreDishRequest(null, appController);
        storeTypeNameRequest.storeDishRequest.setInterface(this);
        storeTypeNameRequest.storeOfficeRequest.setInterface(this);

        storeOfficeDB = new StoreOfficeDB(appController);
        storeOfficeRequest = new StoreOfficeRequest(null, appController);

        carAndHouseDB = new CarAndHouseDB(appController);
        carAndHouseRequest = new CarAndHouseRequest(null, appController);

        if(backgroundRgbColor!=null){
            linearLayout=(LinearLayout)findViewById(R.id.backgroundLayout);
            linearLayout.setBackgroundColor(Color.parseColor(this.backgroundRgbColor));

        }

        if(textRgbColor!=null){
            mRestaurantButton.setTextColor(Color.parseColor(this.textRgbColor));
            mBuildingButton.setTextColor(Color.parseColor(this.textRgbColor));
            mAdvertisementButton.setTextColor(Color.parseColor(this.textRgbColor));
            mPhotoShowButton.setTextColor(Color.parseColor(this.textRgbColor));

        }



        mRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt(Constants.KEY_SHOW_CONTENT_TYPE, 4);
                editor.putInt(Constants.KEY_STORE_ID, 1);
                editor.commit();
                storeId = "1";

                ArrayList<StoreDish>  dishes = storeDishDB.getStoreDishes();
                if(dishes.size() < Constants.DISHES_AMOUNT){
                    downloadData();
                }else{
                    Intent intent = new Intent(ShowExampleActivity.this, RestaurantShowActivity.class);
                    startActivity(intent);
                }
            }
        });

        mBuildingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt(Constants.KEY_SHOW_CONTENT_TYPE, 5);
                editor.putInt(Constants.KEY_STORE_ID, 2);
                editor.commit();
                storeId = "2";

                ArrayList<StoreOffice>  storeOffices = storeOfficeDB.getStoreOffices();
                if(storeOffices.size() < Constants.OFFICES_AMOUNT){
                    downloadData();
                }else{
                    Intent intent = new Intent(ShowExampleActivity.this, OfficeShowActivity.class);
                    startActivity(intent);
                }
            }
        });

        mAdvertisementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt(Constants.KEY_SHOW_CONTENT_TYPE, 1);
                editor.commit();

                ArrayList<Media> mediaList = mediaDB.getAdvertisementMedias(5);
                if(mediaList.size() < Constants.ADS_MEDIA_AMOUNT){
                    downloadData();
                }else{
                    Intent intent = new Intent(ShowExampleActivity.this, MediaShowActivity.class);
                    intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, true);
                    startActivity(intent);
                }

            }
        });

        mPhotoShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt(Constants.KEY_SHOW_CONTENT_TYPE, 2);
                editor.commit();

                ArrayList<Media>  mediaList = mediaDB.getUserDrawingPhotos();
                if(mediaList.size() < Constants.USER_PHOTO_MEDIA_AMOUNT){
                    downloadData();
                }else {
                    Intent intent = new Intent(ShowExampleActivity.this, MediaShowActivity.class);
                    intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
                    intent.putExtra(Constants.KEY_IS_USERPHOTO, true);
                    startActivity(intent);
                }
            }
        });
    }



    public void downloadData(){

        isDownload = true;

        if(showContentType == 1){
            subtitleListRequest.sendStringRequest("1","2000-02-17 23:29:38.0");
        }

        else if(showContentType == 2){
            mediaListRequest.sendStringRequest("1");
        }

        else if(showContentType == 4 || showContentType == 5 ){
            storeBranchRequest.sendStoreBranchRequest(storeId);
            storeTypeNameRequest.sendMenuRequest(storeId);
        }

        else{
            storeBranchRequest.sendStoreBranchRequest(storeId);
            storeTypeNameRequest.sendMenuRequest(storeId);
        }
    }

    @Override
    public void turnOnProgressBar() {
        Toast.makeText(appController, "Downloading data, please wait for a few minutes, thanks!", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void turnOffProgressBar() {
        progressBar.setVisibility(View.GONE);
    }



    @Override
    public void onPause(){
        super.onPause();
        if(isDownload == true){
            isDownload = false;
        }
    }
}
