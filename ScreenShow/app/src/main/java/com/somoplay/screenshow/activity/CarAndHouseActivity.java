package com.somoplay.screenshow.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.adapter.CarAndHouseAdapter;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.CarAndHouseDB;
import com.somoplay.screenshow.database.StoreTypeNameDB;
import com.somoplay.screenshow.model.CarAndHouse;
import com.somoplay.screenshow.model.StoreTypeName;

import java.util.ArrayList;

public class CarAndHouseActivity extends CommonShowActivity<CarAndHouse> {

    private CarAndHouseDB carAndHouseDB = new CarAndHouseDB(this);
    private StoreTypeNameDB storeTypeNameDB = new StoreTypeNameDB(this);
    private ArrayList<StoreTypeName> storeTypeNames = new ArrayList< StoreTypeName>();
    private ArrayList<StoreTypeName> leftListNames = new ArrayList< StoreTypeName>();
    private int firstStoreTypeId = 0;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    //public CountDownTimer countDownTimer;
    /*public ScreenProtectorHandler mTimeHandler;
    public final int TIME_UP = 1;
    public Message msg;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appController.setGeneralActivity(this);
        currentActivity = Constants.CURRENT_CARANDHOUSE_ACTIVITY;
        pref = this.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString("Activity_Target", "Activity_Show");
        editor.commit();

        storeTypeNames = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2, storeId);
        leftListNames = storeTypeNameDB.getStoreTypeNamesByTypeAndId(1, storeId);

        // sorting by sequence;
        ArrayList<StoreTypeName> temp_left = storeTypeNameDB.getStoreTypeNamesByTypeAndId(1 , storeId) ;
        ArrayList<StoreTypeName> temp_right = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2 , storeId);
        for(StoreTypeName s : temp_left){
            int i = s.getSequence();
            leftListNames.set(i-1, s);
        }
        for(StoreTypeName s : temp_right){
            int i = s.getSequence();
            storeTypeNames.set(i-1, s);
        }


        firstStoreTypeId = storeTypeNames.get(0).getId();
        changeAdapterDataByType(firstStoreTypeId);
        //startTimer();

        resetTime();
        /*mTimeHandler = new ScreenProtectorHandler();
        msg = mTimeHandler.obtainMessage(TIME_UP);
        mTimeHandler.sendMessageDelayed(msg,5000);    //7 secs
        Log.d("Timer", "Timer start!");*/


        /*button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAdapterDataByButton(Constants.KEY_IS_KEY_ONE);
            }
        });

        button_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAdapterDataByButton(Constants.KEY_IS_KEY_TWO);
            }
        });

        button_specials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeAdapterDataByButton(Constants.KEY_IS_KEY_THREE);
            }
        });

        button_introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickIntroduction();
            }
        });

        button_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPhotos();
            }
        });*/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*stopTimer();
                startTimer();*/
                resetTime();
                int itemIdCarAndHouse = gridItemAdapter.getItem(position).getStoreItemId();
                editor.putInt(Constants.CARHOUSE_ID, itemIdCarAndHouse);
                editor.commit();
                Intent intent = new Intent(CarAndHouseActivity.this, CarAndHouseDetailActivity.class);
                intent.putExtra(Constants.CARHOUSE_ID, itemIdCarAndHouse);
                intent.putExtra(Constants.MEDIA_TYPE, Constants.CAR_AND_HOUSE_EXTRA_PHOTO);
                startActivity(intent);
            }
        });

        /*gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                resetTime();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });*/

        leftlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*stopTimer();
                startTimer();*/
                resetTime();
                if (position < leftListNames.size()) {
                    int functionId = leftListNames.get(position).getFunctionId();
                    switch (functionId) {

                        case 1:
                            changeAdapterDataByButton(Constants.KEY_IS_KEY_ONE);
                            break;

                        case 2:
                            changeAdapterDataByButton(Constants.KEY_IS_KEY_TWO);
                            break;

                        case 3:
                            changeAdapterDataByButton(Constants.KEY_IS_KEY_THREE);
                            break;

                        case 4:
                            clickIntroduction();
                            break;
                        case 5:
                            clickPhotos();


                    }
                } else {
                    changeAdapterDataByType(storeTypeNames.get(position - leftListNames.size()).getId());
                }
            }
        });

        /*leftlist.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                resetTime();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });*/


/*        rightlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                changeAdapterDataByType(storeTypeNames.get(position).getId());
            }
        });*/
    }

    /*public void startTimer() {

        //try countDownTimer:
        countDownTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                //System.out.printf("seconds remaining: %l", millisUntilFinished / 1000);
                Log.d("Timer", "onTick ");
            }

            public void onFinish() {
                Log.d("Timer", "onFinish, will go to Photo activity ");
                clickPhotos();
            }
        }.start();
        Log.d("Timer", "Timer start ");


        *//*timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                *//**//*Intent intent = new Intent(CommonShowActivity.this, MediaShowActivity.class);
                startActivity(intent);*//**//*
                clickPhotos();
                Log.d("Timer", "go to Photos Activity");

            }
        };

        int delay = 1000 * 1 * 20;      //  20 secs
        int interval = 1000 * 1 * 20;   //  20 secs
        timer.schedule(task, delay, interval);
        Log.d("Timer", "Timer started");*//*

    }*/

   /* public void stopTimer() {


        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
        Log.d("Timer", "Timer stop ");
        *//*if (timer != null) {
            timer.cancel();
        }
        Log.d("Timer", "Timer stopped!");*//*
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /*stopTimer();
                startTimer();*/
                resetTime();
                Log.d("Timer", "touched! down ");
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }
        return true;
    }


    public void changeAdapterDataByButton(int whichButton){
        data = carAndHouseDB.getSpecificCarAndHouseFromDB(whichButton, storeId);
        while(data.remove(null));
        if(data.size() > 0){
            gridItemAdapter = new CarAndHouseAdapter(appController, data, R.layout.grid_store_item);
            setGridViewAdatper();
        }
    }

    public void changeAdapterDataByType(int whichType){
        /*if(storeId == 3){
            whichType += 40;
        }else{
            whichType += 49;
        }*///?????????????????????
        data = carAndHouseDB.getCarAndHouseByStoreTypeId(whichType, storeId);
        while(data.remove(null));
        if(data.size() > 0){
            gridItemAdapter = new CarAndHouseAdapter(appController, data, R.layout.grid_store_item);
            setGridViewAdatper();
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
    }*/

    /*public void resetTime(){
        if (mTimeHandler != null){
            mTimeHandler.removeMessages(TIME_UP);
            msg = mTimeHandler.obtainMessage(TIME_UP);
            mTimeHandler.sendMessageDelayed(msg , 5000);   //7 secs
        }else{
            mTimeHandler = new ScreenProtectorHandler();
            mTimeHandler.removeMessages(TIME_UP);
            msg = mTimeHandler.obtainMessage(TIME_UP);
            mTimeHandler.sendMessageDelayed(msg , 5000);
        }
        Log.d("Timer", "Timer is reset");
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Timer", "Activity and Timer destroyed");
        mTimeHandler.removeMessages(TIME_UP);
        //stopTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetTime();
    }

    @Override
    public void goToPhotos() {
        super.goToPhotos();
    }
    /* public void refreshDatas(){
        changeAdapterDataByType(1);
        refreshActivityContent();
    }*/
}


