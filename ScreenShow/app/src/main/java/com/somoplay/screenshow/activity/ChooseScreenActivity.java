package com.somoplay.screenshow.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.adapter.ChooseScreenAdapter;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.ChooseScreen;
import com.somoplay.screenshow.model.ChooseScreenList;
import com.somoplay.screenshow.webrequest.ChooseScreenRequest;

import java.util.ArrayList;

public class ChooseScreenActivity extends Activity {


    //    public ChooseScreenActivity chooseScreenActivity= new ChooseScreenActivity();
    public ChooseScreenRequest chooseScreenRequest = new ChooseScreenRequest(this);
    public ChooseScreenAdapter chooseScreenAdapter;
    public ChooseScreenList chooseScreenList= new ChooseScreenList();
    //    public ChooseScreenActivity chooseScreenActivity = null;
    ArrayList<ChooseScreen> dataUse;

    public ListView showList;

    private int screenId;
    private boolean deleted;
    private String updatedTs;
    private String ceatedTs;
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
//    private ImageView icon;

    private String storeName;
    private String storeAddress;

    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    private AppController appController = AppController.getInstance();

// public ArrayList<ChooseScreenList> customArray = new ArrayList<ChooseScreenList>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_screen);
        showList= (ListView)findViewById( R.id.showListView );

        SharedPreferences sharedPreferences= AppController.getInstance().
                getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        adminId=sharedPreferences.getInt(Constants.KEY_ADMIN_ID, 1000000000);
        String adminIdRequest=String.valueOf(adminId);
        chooseScreenRequest.sendStringRequest(adminIdRequest, false);

//        icon = (ImageView) findViewById(R.id.item_image);
//        String filePath = Constants.DEVICE_PATH_LOGO + "/logo.png";
//        icon.setImageURI(Uri.parse(filePath));

        showList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseScreen chooseScreen = chooseScreenList.getChooseScreenArray().get(position);

                screenId = chooseScreen.getId();
                deleted = chooseScreen.isDeleted();
                updatedTs=chooseScreen.getUpdatedTs();
                ceatedTs=chooseScreen.getCreatedTs();
                screenName=chooseScreen.getScreenName();
                adminId=chooseScreen.getAdminId();
                storeId=chooseScreen.getStoreId();
                showRestaurant=chooseScreen.getShowRestaurant();
                showBuilding=chooseScreen.getShowBuilding();
                showGeneralStore=chooseScreen.getShowGeneralStore();
                showPhotos=chooseScreen.getShowPhotos();
                showAdvertisement = chooseScreen.getShowAdvertisement();
                showContentType=chooseScreen.getShowContentType();
                status=chooseScreen.getStatus();
                touchType=chooseScreen.getTouchType();
                layoutType=chooseScreen.getLayoutType();
                version=chooseScreen.getVersion();
                ipAddress=chooseScreen.getIpAddress();
                info=chooseScreen.getInfo();

                storeName=chooseScreen.getStoreName();
                storeAddress=chooseScreen.getStoreAddress();

                Toast unsavedToast = Toast.makeText(getApplicationContext(),
                        "Info has been saved!", Toast.LENGTH_SHORT);
                unsavedToast.show();

                SharedPreferences pref=AppController.getInstance().
                        getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                SharedPreferences.Editor editor= pref.edit();
                editor.putInt(Constants.KEY_SCREEN_ID, screenId);
                editor.putBoolean(Constants.KEY_IS_SCREEN_DELETED, deleted);
                editor.putString(Constants.KEY_UPDATED_TS, updatedTs);
                editor.putString(Constants.KEY_SCREEN_NAME, screenName);
                editor.putInt(Constants.KEY_ADMIN_ID, adminId);
                editor.putInt(Constants.KEY_STORE_ID, storeId);
                editor.putInt(Constants.KEY_SHOW_RESTAURANT, showRestaurant);
                editor.putInt(Constants.KEY_SHOW_BUILDING,showBuilding);
                editor.putInt(Constants.KEY_SHOW_GENERAL_STORE,showGeneralStore);
                editor.putInt(Constants.KEY_SHOW_PHOTOS,showPhotos);
                editor.putInt(Constants.KEY_SHOW_ADVERTISEMENT,showAdvertisement);
                editor.putInt(Constants.KEY_SHOW_CONTENT_TYPE,showContentType);
                editor.putInt(Constants.KEY_PREVIOUS_CONTENT_TYPE,showContentType);
                editor.putInt(Constants.KEY_STATUS,status);
                editor.putInt(Constants.KEY_TOUCH_TYPE, touchType);
                editor.putInt(Constants.KEY_LAYOUT_TYPE, layoutType);
                editor.putString(Constants.KEY_VERSION, version);
                editor.putString(Constants.KEY_IP_ADDRESS, ipAddress);
                editor.putString(Constants.KEY_INFO, info);
                editor.putString(Constants.KEY_STORE_NAME, storeName);
                editor.putString(Constants.KEY_STORE_ADDRESS, storeAddress);

                editor.putString("Activity_Target", "Activity_Login");

                editor.commit();





                Intent toDownloadActivity=new Intent(ChooseScreenActivity.this, DownloadActivity.class);
                startActivity(toDownloadActivity);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        icon = (ImageView) findViewById(R.id.item_image);
//        String filePath = Constants.DEVICE_PATH_LOGO + "/logo.png";
//        icon.setImageURI(Uri.parse(filePath));
    }

}
