package com.somoplay.screenshow.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.adapter.StoreDishAdapter;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.StoreDishDB;
import com.somoplay.screenshow.database.StoreTypeNameDB;
import com.somoplay.screenshow.model.StoreDish;
import com.somoplay.screenshow.model.StoreMedia;
import com.somoplay.screenshow.model.StoreTypeName;

import java.util.ArrayList;

/**
 * Created by Shaohua on 8/26/2015.
 */
public class RestaurantShowActivity extends CommonShowActivity<StoreDish> {

    private StoreDishDB storeDishDB = new StoreDishDB(this);
    private StoreTypeNameDB storeTypeNameDB = new StoreTypeNameDB(this);
    private ArrayList<StoreTypeName> storeTypeNames = new ArrayList< StoreTypeName>();
    int firstStoreTypeId = 0;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appController.setGeneralActivity(this);
        currentActivity = Constants.CURRENT_STORE_SHOW_ACTIVITY;

        resetTime();
        sharedPreferences = this.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

//        startTimer();

        storeTypeNames = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2, storeId);
        firstStoreTypeId = storeTypeNames.get(0).getId();
        changeAdapterDataByType(firstStoreTypeId);

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
                resetTime();
                int itemIdDish = gridItemAdapter.getItem(position).getId();
                /*commonShowActivity.stopTimer();
                commonShowActivity.startTimer();
                Log.d("Stop Timer", "Timer reset  ");*/
                editor.putInt(Constants.DISH_ID, itemIdDish);
                editor.commit();
                Intent intent = new Intent(RestaurantShowActivity.this, DishDetailActivity.class);
                intent.putExtra(Constants.DISH_ID, itemIdDish);
                intent.putExtra(Constants.MEDIA_TYPE, Constants.DISH_EXTRA_PHOTO);
                startActivity(intent);
            }
        });


        leftlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                resetTime();
                TextView column = (TextView)view.findViewById(R.id.store_type_name);
               /* commonShowActivity.stopTimer();
                commonShowActivity.startTimer();
                Log.d("Stop Timer", "Timer reset  ");*/
                if(position < 5) {
                    String columeName = column.getText().toString();
                    switch (columeName) {
                        case "NEW":
                            changeAdapterDataByButton(Constants.KEY_IS_KEY_ONE);
                        /*timer.cancel();
                        startTimer();
                        Log.d("Stop Timer", "touched!  ");*/
                            break;
                        case "SALE":
                            changeAdapterDataByButton(Constants.KEY_IS_KEY_TWO);
                            break;
                        case "SPECIAL":
                            changeAdapterDataByButton(Constants.KEY_IS_KEY_THREE);
                            break;
                        case "INTRODUCTION":
                            clickIntroduction();
                            break;
                        case "PHOTOS":
                            clickPhotos();
                            break;
                        default:
                            break;
                    }
                }else {
                    changeAdapterDataByType(storeTypeNames.get(position - 5).getId());
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

    /*    rightlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                changeAdapterDataByType(storeTypeNames.get(position).getId());
            }
        });*/

    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                timer.cancel();
                startTimer();
                Log.d("Timer", "touched! down ");
                break;
            case MotionEvent.ACTION_MOVE:
                timer.cancel();
                startTimer();
                Log.d("Timer", "touched! move ");
                break;
            case MotionEvent.ACTION_UP:
                timer.cancel();
                startTimer();
                Log.d("Timer", "touched!  up");
                break;
            default:
                return false;
        }
        return true;
    }*/

    public void changeAdapterDataByButton(int whichButton){
        data = storeDishDB.getSpecificDishesFromDB(whichButton, storeId);
        while(data.remove(null));
        if(data.size() > 0) {
            gridItemAdapter = new StoreDishAdapter(appController, data, R.layout.grid_store_item);
            setGridViewAdatper();
        }
    }

    public void changeAdapterDataByType(int whichType){
        data = storeDishDB.getDishesByStoreTypeId(whichType);
        while(data.remove(null));
        if(data.size() > 0){
            gridItemAdapter = new StoreDishAdapter(appController, data, R.layout.grid_store_item);
            setGridViewAdatper();
        }
    }

   /* private void startTimer() {

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Intent intent = new Intent(RestaurantShowActivity.this, StoreIntroductionActivity.class);
                startActivity(intent);
                Log.d("Timer", "go to Photos Activity");

            }
        };

        timer = new Timer(true);
        int delay = 1000 * 1 * 20;      // 1 minute
        int interval = 1000 * 1 * 20;   // 1 minute
        timer.schedule(task, delay, interval);
        Log.d("Timer", "Timer started");

    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        Log.d("Timer", "Timer stopped!");
    }*/

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Timer", "Activity and Timer destroyed");
        stopTimer();
    }*/

    public void refreshDatas(){
        changeAdapterDataByType(1);
        refreshActivityContent();
    }

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
}
