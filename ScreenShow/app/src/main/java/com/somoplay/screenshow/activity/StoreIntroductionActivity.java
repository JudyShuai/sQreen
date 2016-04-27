package com.somoplay.screenshow.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.StoreBranchDB;
import com.somoplay.screenshow.model.StoreBranch;

/**
 * Created by Shaohua Mao on 2015-07-18.
 */
public class StoreIntroductionActivity extends BaseActivity {
    private TextView title;
    private TextView content;
    private TextView topTitle;
    private ImageView icon;
    protected TextView companyText;
    private ImageView companyLogo;
    private StoreBranchDB storeBranchDB;
    private AppController mAppController = AppController.getInstance();
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private int storeId;
    protected String backgroundColor;
    protected String fontColor;
    private LinearLayout backgroundLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_introduction);
        mAppController.setGeneralActivity(this);
        storeBranchDB = new StoreBranchDB(this);

        sharedPref = getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        editor = sharedPref.edit();
        storeId = sharedPref.getInt(Constants.KEY_STORE_ID, 1);
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_INTRODUCTION_ACTIVITY);
        editor.commit();
/*
        backgroundColor = sharedPref.getString(Constants.KEY_BACKGROUND_COLOR, "#0f31e6");
        if (backgroundColor == ""){
            backgroundColor = "#0f31e6";
        }else if(backgroundColor.charAt(0) != '#'){
            backgroundColor = "#" + backgroundColor;
        }
        backgroundLayout = (LinearLayout)findViewById(R.id.store_introduction_Layout);
        backgroundLayout.setBackgroundColor(Color.parseColor(backgroundColor));

        fontColor = sharedPref.getString(Constants.KEY_FONT_COLOR, "#000000");

        if (fontColor == ""){
            fontColor = "#000000";
        }else if(fontColor.charAt(0) != '#'){
            fontColor = "#" + fontColor;
        }
*/

        fontColor = "#000000";

        title = (TextView) findViewById(R.id.store_introduction_title);
        content = (TextView) findViewById(R.id.store_introduction_content);
        topTitle = (TextView) findViewById(R.id.item_text);

        title.setTextColor(Color.parseColor(fontColor));
        content.setTextColor(Color.parseColor(fontColor));
        topTitle.setTextColor(Color.parseColor("#ffffff"));
        String fontName = sharedPref.getString(Constants.KEY_STORE_NAME, "normal");
        topTitle.setText(fontName);

        icon = (ImageView) findViewById(R.id.item_image);
        StoreBranch storeBranch = storeBranchDB.getStoreBranchByStoreId(storeId);
        companyText = (TextView) findViewById (R.id.company_text);
        companyText.setTextColor(Color.parseColor(fontColor));
        companyLogo= (ImageView) findViewById(R.id.company_logo);
        String filePathLogo = Constants.DEVICE_PATH_LOGO + "/logo.png";
        companyLogo.setImageURI(Uri.parse(filePathLogo));

        String filePath = new String("file:///" + Constants.DEVICE_PATH_LOGO + "/" +
                storeBranch.getLogoUrl());
        icon.setImageURI(Uri.parse(filePath));



        storeBranchDB = new StoreBranchDB(mAppController);
        refreshActivityContent();
        resetTime();
    }


    @Override
    public void refreshActivityContent(){
        String introductionContent = storeBranchDB.getStoreBranchByStoreId(storeId).getInfoThreeName();
        content.setText(introductionContent);
    }


    @Override
    public void goToPhotos() {
        Intent intent = new Intent(StoreIntroductionActivity.this, MediaShowActivity.class);
        intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
        intent.putExtra(Constants.KEY_IS_STORE, true);
        startActivity(intent);

    }

    @Override
    public void onResume(){
        super.onResume();
        resetTime();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_INTRODUCTION_ACTIVITY);
        editor.commit();
    }

    @Override
    public void onPause(){
        super.onPause();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
        editor.commit();
    }


    @Override
    public void changeCurrentActivity(){
        int showContentType = sharedPref.getInt(Constants.KEY_SHOW_CONTENT_TYPE, 0);

        if(showContentType == 1){
            Intent intent = new Intent(this, MediaShowActivity.class);
            intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, true);
            startActivity(intent);
        }

        else if(showContentType == 2){
            Intent intent = new Intent(this, MediaShowActivity.class);
            intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
            intent.putExtra(Constants.KEY_IS_USERPHOTO, true);
            startActivity(intent);
        }

        else if(showContentType == 4){

            //Intent intent = new Intent(this, StoreShowActivity.class);
            Intent intent = new Intent(this, new_StoreShowActivity.class);
            startActivity(intent);
        }

        else if(showContentType == 5){
            Intent intent = new Intent(this, OfficeShowActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                resetTime();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                finish();
                break;
            default:
                return false;
        }
        return true;
    }

}
