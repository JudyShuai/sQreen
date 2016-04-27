package com.somoplay.screenshow.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.adapter.CommonAdapter;
import com.somoplay.screenshow.adapter.StoreTypeAdapter;
import com.somoplay.screenshow.adapter.new_StoreTypeAdapter;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.customizedview.LYMarqueeViewThree;
import com.somoplay.screenshow.customizedview.MyGridView;
import com.somoplay.screenshow.database.StoreBranchDB;
import com.somoplay.screenshow.database.StoreTypeNameDB;
import com.somoplay.screenshow.model.StoreBranch;
import com.somoplay.screenshow.model.StoreTypeName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaohua on 8/26/2015.
 */
public class CommonShowActivity<T> extends BaseActivity {

    private static final String TAG = LYMarqueeViewThree.class.getSimpleName();
    protected MyGridView gridView;
    protected ListView leftlist;

    protected TextView title;
    protected ImageView icon;
    protected TextView companyText;
    private ImageView companyLogo;
    protected Button button_new;
    protected Button button_sale;
    protected Button button_specials;
    protected Button button_introduction;
    protected Button button_photos;
    protected String fontColor;

    protected int storeId;
    protected int currentActivity;
    /*public final int TIME_UP = 1;
    public ScreenProtectorHandler mTimeHandler;
    public Message msg;*/

    protected AppController appController = AppController.getInstance();
    protected SharedPreferences sharedPref;
    protected SharedPreferences.Editor editor;

    protected List<T> data;
    protected ArrayList<StoreTypeName> leftMenu;
    protected ArrayList<StoreTypeName> rightMenu;

    protected StoreTypeAdapter leftMenuAdapter;
    protected StoreTypeAdapter rightMenuAdapter;
    protected new_StoreTypeAdapter DataAdapter;
    protected CommonAdapter<T> gridItemAdapter;

    protected StoreBranchDB storeBranchDB;
    protected StoreTypeNameDB storeTypeNameDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_store_show);
        //setContentView(R.layout.store_show);

        storeTypeNameDB = new StoreTypeNameDB(this);
        storeBranchDB = new StoreBranchDB(this);

        appController.setGeneralActivity(this);

        sharedPref = getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        fontColor = sharedPref.getString(Constants.KEY_FONT_COLOR, "#ffffff");

        if (fontColor == ""){
            fontColor = "#ffffff";
        }else if(fontColor.charAt(0) != '#'){
            fontColor = "#" + fontColor;
        }

        /*
        String backgroundColor = sharedPref.getString(Constants.KEY_BACKGROUND_COLOR, "#0f31e6");
        if (backgroundColor == ""){
            backgroundColor = "#ffffff";
        }else if(backgroundColor.charAt(0) != '#'){
            backgroundColor = "#" + backgroundColor;
        }*/

        storeId = sharedPref.getInt(Constants.KEY_STORE_ID, 1);
        editor = sharedPref.edit();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_STORE_SHOW_ACTIVITY);
        editor.commit();

        LinearLayout ll = (LinearLayout)findViewById(R.id.new_store_show_layout);
        //ll.setBackgroundColor(Color.parseColor(backgroundColor));

        button_new = (Button)findViewById(R.id.new_buttonNew);
        button_sale = (Button)findViewById(R.id.new_buttonSale);
        button_specials = (Button)findViewById(R.id.new_buttonSpecials);
        button_introduction = (Button)findViewById(R.id.new_buttonIntroduction);
        button_photos = (Button)findViewById(R.id.new_buttonPhotos);

        title = (TextView) findViewById(R.id.new_item_text);
        icon = (ImageView) findViewById(R.id.new_item_image);
        companyText = (TextView) findViewById (R.id.new_company_text);
        companyText.setTextColor(Color.parseColor(fontColor));
        StoreBranch storeBranch = storeBranchDB.getStoreBranchByStoreId(storeId);

        if(storeBranch != null){
            String filePath = new String("file:///" + Constants.DEVICE_PATH_LOGO + "/" +
                    storeBranch.getLogoUrl());
            icon.setImageURI(Uri.parse(filePath));
        }

        companyLogo= (ImageView) findViewById(R.id.new_company_logo);
        String filePathLogo = Constants.DEVICE_PATH_LOGO + "/logo.png";
        companyLogo.setImageURI(Uri.parse(filePathLogo));


        setText(title, true);
        setText(button_new, false);
        setText(button_sale, false);
        setText(button_specials, false);
        setText(button_introduction, false);
        setText(button_photos, false);

        gridView = (MyGridView) findViewById(R.id.new_gridView1);

        leftlist = (ListView)findViewById(R.id.new_listview);
        leftMenu = storeTypeNameDB.getStoreTypeNamesByTypeAndId(1 , storeId) ;
        rightMenu = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2 , storeId) ;

        // sorting by sequence;
        ArrayList<StoreTypeName> temp_left = storeTypeNameDB.getStoreTypeNamesByTypeAndId(1 , storeId) ;
        ArrayList<StoreTypeName> temp_right = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2 , storeId);
        for(StoreTypeName s : temp_left){
            int i = s.getSequence();
            leftMenu.set(i-1, s);
        }
        for(StoreTypeName s : temp_right){
            int i = s.getSequence();
            rightMenu.set(i-1, s);
        }


        ArrayList<StoreTypeName> allData = new ArrayList<>();
        allData.addAll(leftMenu);
        allData.addAll(rightMenu);

       // leftMenuAdapter = new StoreTypeAdapter(appController, allData, R.layout.menu_list_item);
       // leftlist.setAdapter(leftMenuAdapter);
        DataAdapter = new new_StoreTypeAdapter(appController, allData, R.layout.menu_list_item);
        leftlist.setAdapter(DataAdapter);
        afterGettingLeftStoreType();

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                resetTime();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        leftlist.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                resetTime();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        /**
        rightlist=(ListView)findViewById(R.id.rightlistview);
        rightMenu = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2 , storeId) ;
        rightMenuAdapter = new StoreTypeAdapter(appController, rightMenu, R.layout.menu_list_item);
        rightlist.setAdapter(rightMenuAdapter);
        afterGettingRightStoreType();
         **/
    }

    public void clickIntroduction() {
        Intent intent = new Intent(CommonShowActivity.this, StoreIntroductionActivity.class);
        startActivity(intent);
    }

    public void clickPhotos() {
        Intent intent = new Intent(CommonShowActivity.this, MediaShowActivity.class);
        intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
        intent.putExtra(Constants.KEY_IS_STORE, true);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, currentActivity);
        editor.commit();
    }

    @Override
    public void onPause(){
       /* Log.d(TAG, "this will shown when common show acticity is paused");
        BaseActivity baseActivity = AppController.getInstance().getGeneralActivity();
        if(baseActivity != null){
            baseActivity = null;
        }*/
        super.onPause();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
        editor.commit();
    }

    @Override
    public void refreshActivityContent(){
        ArrayList <StoreTypeName> dataLeft = storeTypeNameDB.getStoreTypeNamesByTypeAndId(1, storeId) ;
        //afterGettingLeftStoreType();

        ArrayList <StoreTypeName> dataRight = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2, storeId) ;
        //afterGettingRightStoreType();
    }

    @Override
    public void goToPhotos() {
        Intent intent = new Intent(CommonShowActivity.this, MediaShowActivity.class);
        intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
        intent.putExtra(Constants.KEY_IS_STORE, true);
        startActivity(intent);

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
            Intent intent = new Intent(this, RestaurantShowActivity.class);
            startActivity(intent);
        }

        else if(showContentType == 5){
            Intent intent = new Intent(this, OfficeShowActivity.class);
            startActivity(intent);
        }

        else if(showContentType == 6){
            Intent intent = new Intent(this, CarAndHouseActivity.class);
            startActivity(intent);
        }
    }


    /*public class ScreenProtectorHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case TIME_UP:
                    *//*Intent intent = new Intent(CarAndHouseActivity.this, MediaShowActivity.class);
                    intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
                    intent.putExtra(Constants.KEY_IS_STORE, true);     //当10秒到达后，作相应的操作。
                    startActivity(intent);*//*
                    clickPhotos();

                    Log.i("Timer", "Time's up, go to photos activity");
                    break;
            }
        }
    }

    public void resetTime(){
        if (mTimeHandler != null){
            mTimeHandler.removeMessages(TIME_UP);
            msg = mTimeHandler.obtainMessage(TIME_UP);
            mTimeHandler.sendMessageDelayed(msg , 8000);   //8 secs
        }else{
            mTimeHandler = new ScreenProtectorHandler();
            mTimeHandler.removeMessages(TIME_UP);
            msg = mTimeHandler.obtainMessage(TIME_UP);
            mTimeHandler.sendMessageDelayed(msg , 8000);
        }
        Log.d("Timer", "Timer is reset");
    }*/

    public void setText(TextView tv, boolean isTitle){

        String fontName = sharedPref.getString(Constants.KEY_STORE_NAME, "normal");
        int fontHeadSize = sharedPref.getInt(Constants.KEY_FONT_HEAD_SIZE, 30);
        int fontTextSize = sharedPref.getInt(Constants.KEY_FONT_TEXT_SIZE, 12);
        //int fontTextSize = 13;


        if(isTitle){
            tv.setTextSize((float) 30);
            tv.setText(fontName);
            tv.setTextColor(Color.WHITE);
        }else{
            tv.setTextSize((float) fontTextSize);
            if(fontColor != "#ffffff") {
                tv.setTextColor(Color.parseColor(fontColor));
            }else {
                tv.setTextColor(Color.parseColor("#000000"));
            }
        }

        /*Typeface typeFace=Typeface.create(fontName, Typeface.NORMAL);
        if(typeFace != null) {
            tv.setTypeface(typeFace);
        }*/
    }


    public void afterGettingLeftStoreType(){
        //leftMenuAdapter.notifyDataSetChanged();
        DataAdapter.notifyDataSetInvalidated();
    }

    public void afterGettingRightStoreType(){
        rightMenuAdapter.notifyDataSetChanged();
    }

    public void afterGettingStoreItemsData(){
        gridItemAdapter.notifyDataSetChanged();
    }

    public void setGridViewAdatper(){
        gridView.setAdapter(gridItemAdapter);
        afterGettingStoreItemsData();
    }
}
