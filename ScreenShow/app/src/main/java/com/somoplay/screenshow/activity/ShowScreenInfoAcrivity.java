package com.somoplay.screenshow.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;

public class ShowScreenInfoAcrivity extends Activity {

    TextView showIdText;
    TextView updatedTsText;
    TextView adminIdText;
    TextView showScreenNameText;
    TextView showGeneralStoreText;
    TextView showPhotosText;
    TextView showAdvertisementText;
    TextView showContentTypeText;
    TextView statusText;
    TextView touchTypeText;
    TextView layoutTypeText;
    TextView versionText;
    TextView ipAddressText;
    TextView infoText;
    TextView storeIdText;
    TextView storeNameText;
    TextView storeAddressText;

    String trans_show_restaurant;
    String trans_show_building;
    String trans_show_general_store;
    String trans_show_photos;
    String trans_show_advertisement;
    String trans_show_content_type;
    String trans_status;
    String trans_touch_type;
    String trans_layout_type;

    public SharedPreferences prefe;
    public SharedPreferences.Editor editor2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_screen_info);

        showIdText=(TextView)findViewById(R.id.idText);
        updatedTsText=(TextView)findViewById(R.id.updatedTsText);
        adminIdText=(TextView)findViewById(R.id.adminIdText);
        showScreenNameText=(TextView)findViewById(R.id.showScreenNameText);
        showGeneralStoreText=(TextView)findViewById(R.id.showGeneralStoreText);
        showPhotosText=(TextView)findViewById(R.id.showPhotosText);
        showAdvertisementText=(TextView)findViewById(R.id.showAdvertisementText);
        showContentTypeText=(TextView)findViewById(R.id.showContentTypeText);
        statusText=(TextView)findViewById(R.id.statusText);
        touchTypeText=(TextView)findViewById(R.id.touchTypeText);
        layoutTypeText=(TextView)findViewById(R.id.layoutTypeText);
        versionText=(TextView)findViewById(R.id.versionText);
        ipAddressText=(TextView)findViewById(R.id.ipAddressText);
        infoText=(TextView)findViewById(R.id.infoText);
        storeIdText=(TextView)findViewById(R.id.storeIdText);
        storeNameText=(TextView)findViewById(R.id.storeNameText);
        storeAddressText=(TextView)findViewById(R.id.storeAddressText);


        prefe = AppController.getInstance().getSharedPreferences(Constants.PREF_NAME,Constants.PRIVATE_MODE);
        editor2 = prefe.edit();
        int id=prefe.getInt(Constants.KEY_SCREEN_ID,10000000);
        String updatedTs=prefe.getString(Constants.KEY_UPDATED_TS, "updatedTs");
        int adminId=prefe.getInt(Constants.KEY_ADMIN_ID, 10000000);
        String getScreenName = prefe.getString(Constants.KEY_SCREEN_NAME, "screen_name");
        int showGeneralStore=prefe.getInt(Constants.KEY_SHOW_GENERAL_STORE,100000);
        int showPhotos=prefe.getInt(Constants.KEY_SHOW_PHOTOS,100000);
        int showAdvertisement=prefe.getInt(Constants.KEY_SHOW_ADVERTISEMENT,100000);
        int showContentType=prefe.getInt(Constants.KEY_SHOW_CONTENT_TYPE,100000);
        int status=prefe.getInt(Constants.KEY_STATUS,100000);
        int touchType=prefe.getInt(Constants.KEY_TOUCH_TYPE,100000);
        int layoutType=prefe.getInt(Constants.KEY_LAYOUT_TYPE,100000);
        String version=prefe.getString(Constants.KEY_VERSION, "0");
        String ipAddress=prefe.getString(Constants.KEY_IP_ADDRESS, "ipAddress");
        String info=prefe.getString(Constants.KEY_INFO,"info");
        int storeId=prefe.getInt(Constants.KEY_STORE_ID, 1000000);
        String storeName=prefe.getString(Constants.KEY_STORE_NAME, "storeName");
        String storeAddress=prefe.getString(Constants.KEY_STORE_ADDRESS,"storeAddress");


       /* switch (showRestaurant){
            case 0:
                trans_show_restaurant="disable";
                break;
            case 1:
                trans_show_restaurant="enable";
                break;
            default:
                trans_show_restaurant="error!";
        }

        switch (showBuilding){
            case 0:
                trans_show_building="disable";
                break;
            case 1:
                trans_show_building="enable";
                break;
            default:
                trans_show_building="error!";
        }*/

        switch (showGeneralStore){
            case 0:
                trans_show_general_store="disable";
                break;
            case 1:
                trans_show_general_store="enable";
                break;
            default:
                trans_show_general_store="error!";
        }
        switch (showPhotos){
            case 0:
                trans_show_photos="disable";
                break;
            case 1:
                trans_show_photos="enable";
                break;
            default:
                trans_show_photos="error!";
        }
        switch (showAdvertisement){
            case 0:
                trans_show_advertisement="disable";
                break;
            case 1:
                trans_show_advertisement="enable";
                break;
            default:
                trans_show_advertisement="error!";
        }
        switch (showContentType){
            case 0:
                trans_show_content_type="no content";
                break;
            case 1:
                trans_show_content_type="advertisement";
                break;
            case 2:
                trans_show_content_type="photos on";
                break;
            case 3:
                trans_show_content_type="photos off";
                break;
            case 4:
                trans_show_content_type="restaurant";
                break;
            case 5:
                trans_show_content_type="building";
                break;
            case 6:
                trans_show_content_type="general store";
                break;
            default:
                trans_show_content_type="error!";
        }
        switch (status){
            case 0:
                trans_status="working";
                break;
            case 1:
                trans_status="shutdown";
                break;
            case 2:
                trans_status="sleeping";
                break;
            case 3:
                trans_status="maintain";
                break;
            case 4:
                trans_status="broken";
                break;
            default:
                trans_status="error!";
        }
        switch (touchType){
            case 0:
                trans_touch_type="no touch screen";
                break;
            case 1:
                trans_touch_type="touch screen";
                break;
            default:
                trans_touch_type="error!";
        }
        switch (layoutType){
            case 0:
                trans_layout_type="landscape";
                break;
            case 1:
                trans_layout_type="portrait";
                break;
            default:
                trans_layout_type="error!";
        }


        showIdText.setText(Integer.toString(id));
        updatedTsText.setText(updatedTs);
        adminIdText.setText(Integer.toString(adminId));
        showScreenNameText.setText(getScreenName);
        showGeneralStoreText.setText(trans_show_general_store);
        showPhotosText.setText(trans_show_photos);
        showAdvertisementText.setText(trans_show_advertisement);
        showContentTypeText.setText(trans_show_content_type);
        statusText.setText(trans_status);
        touchTypeText.setText(trans_touch_type);
        layoutTypeText.setText(trans_layout_type);
        versionText.setText(String.valueOf(version));
        ipAddressText.setText(ipAddress);
        infoText.setText(info);
        storeIdText.setText(Integer.toString(storeId));
        storeNameText.setText(storeName);
        storeAddressText.setText(storeAddress);

        showScreenNameText.setText(getScreenName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_screen_info, menu);
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

}
