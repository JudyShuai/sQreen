package com.somoplay.screenshow.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.CarAndHouseDB;
import com.somoplay.screenshow.database.MediaDB;
import com.somoplay.screenshow.database.StoreBranchDB;
import com.somoplay.screenshow.database.SubtitleDB;
import com.somoplay.screenshow.model.CarAndHouse;
import com.somoplay.screenshow.model.Media;
import com.somoplay.screenshow.webrequest.StoreBranchRequest;
import com.somoplay.screenshow.webrequest.SubtitleListRequest;

import java.util.ArrayList;

/**
 * Created by yaolu on 15-06-20.
 */
public class StartLoadingActivity extends Activity {

    private boolean firstTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_loading);
        //getActionBar().hide();
        firstTime =true;
        new Handler().postDelayed(new Runnable(){
            Intent intent;
            @Override
            public void run(){





                SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                String activityTarget = prefs.getString("Activity_Target", null);
                if (activityTarget !=null && activityTarget.equals( "Activity_Show"))
                {
//                    String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
//                    int idName = prefs.getInt("idName", 0); //0 is the default value.
                    SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE).edit();
                    editor.putString("Activity_Enter_Method", "Automatic");
                    editor.commit();

                    goToActivity();
                }
                else
                {
                    SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE).edit();
                    editor.putString("Activity_Enter_Method", "Menual");
                    editor.commit();

                    intent = new Intent (StartLoadingActivity.this, LoginActivity.class);
                    startActivity(intent);
                   // finish();
                }

            }
        }, 1000);
    }

    @Override
    public void onResume(){
        super.onResume();

        if(firstTime ==true) {
            firstTime = false;
        }
        else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Intent intent = new Intent(StartLoadingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            }, 1);
        }

    }

    private void goToActivity()
    {

        SharedPreferences sharedPref = getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);

        MediaDB mediaDB =new MediaDB(AppController.getInstance());
        CarAndHouseDB carAndHouseDB = new CarAndHouseDB(AppController.getInstance());
        int storeIdInt = sharedPref.getInt(Constants.KEY_STORE_ID, 1);
        String storeId = Integer.toString(sharedPref.getInt(Constants.KEY_STORE_ID, 1));

        int showContentType = sharedPref.getInt(Constants.KEY_SHOW_CONTENT_TYPE, 1);
        if(showContentType == 1){
            int screenId = sharedPref.getInt(Constants.KEY_SCREEN_ID, 0);
            ArrayList<Media> mediaList = mediaDB.getAdvertisementMedias(screenId);
            if(mediaList.size() == 0){
                new AlertDialog.Builder(StartLoadingActivity.this)
                        .setTitle("Oops!")
                        .setMessage("No content! Please download first or make sure have content inside")
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }else{
                Intent intent = new Intent(StartLoadingActivity.this, MediaShowActivity.class);
                intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, true);
                startActivity(intent);
            }
        }

        else if(showContentType == 2){
            ArrayList<Media>  mediaList = mediaDB.getUserDrawingPhotos();
            if(mediaList.size() == 0){
                new AlertDialog.Builder(AppController.getInstance())
                        .setTitle("Oops!")
                        .setMessage("No content! Please download first or make sure have content inside")
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }else{
                Intent intent = new Intent(StartLoadingActivity.this, MediaShowActivity.class);
                intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
                intent.putExtra(Constants.KEY_IS_USERPHOTO, true);
                startActivity(intent);
            }
        }
        else if(showContentType == 6){
            if (storeIdInt == 0) {
                new AlertDialog.Builder(AppController.getInstance())
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
                    new AlertDialog.Builder(AppController.getInstance())
                            .setTitle("Oops!")
                            .setMessage("No content! Please download first or make sure have content inside")
                            .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                } else {
                    Intent intent = new Intent(StartLoadingActivity.this, CarAndHouseActivity.class);
                    startActivity(intent);
                }
            }
        }
    }


}
