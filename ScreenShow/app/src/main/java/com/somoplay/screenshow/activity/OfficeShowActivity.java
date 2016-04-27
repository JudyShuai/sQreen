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
import com.somoplay.screenshow.adapter.OfficeAdapter;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.StoreOfficeDB;
import com.somoplay.screenshow.database.StoreTypeNameDB;
import com.somoplay.screenshow.model.StoreOffice;
import com.somoplay.screenshow.model.StoreTypeName;

import java.util.ArrayList;

/**
 * Created by Shaohua Mao on 2015-07-14.
 */
public class OfficeShowActivity extends CommonShowActivity<StoreOffice> {
    private StoreOfficeDB storeOfficeDB = new StoreOfficeDB(this);
    private StoreTypeNameDB storeTypeNameDB = new StoreTypeNameDB(this);
    private ArrayList<StoreTypeName> storeTypeNames = new ArrayList< StoreTypeName>();
    int firstStoreTypeId = 0;
    private SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resetTime();
        appController.setGeneralActivity(this);
        currentActivity = Constants.CURRENT_OFFICE_SHOW_ACTIVITY;
        storeTypeNames = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2, storeId);
        firstStoreTypeId = storeTypeNames.get(0).getId();
        changeAdapterDataByType(firstStoreTypeId);
        sharedPreferences = this.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

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
                int itemIdOffice = gridItemAdapter.getItem(position).getId();
                editor.putInt(Constants.STORE_OFFICE_ID, itemIdOffice);
                editor.commit();

                /*commonShowActivity.stopTimer();
                commonShowActivity.startTimer();
                Log.d("Stop Timer", "Timer reset  ");*/

                Intent intent = new Intent(OfficeShowActivity.this, OfficeDetailActivity.class);
                intent.putExtra(Constants.STORE_OFFICE_ID, itemIdOffice);
                intent.putExtra(Constants.MEDIA_TYPE, Constants.DISH_EXTRA_PHOTO);
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
                resetTime();
                TextView column = (TextView) view.findViewById(R.id.store_type_name);

                /*commonShowActivity.stopTimer();
                commonShowActivity.startTimer();
                Log.d("Stop Timer", "Timer reset  ");*/

                if (position < 5) {
                    String columeName = column.getText().toString();
                    switch (columeName) {
                        case "ADMINISTRATION":
                            changeAdapterDataByButton(Constants.KEY_IS_KEY_TWO);
                            break;
                        case "RENT":
                            changeAdapterDataByButton(Constants.KEY_IS_KEY_THREE);
                            break;
                        case "SALE":
                            changeAdapterDataByButton(Constants.KEY_IS_KEY_ONE);
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
                } else {
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

/*        rightlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                changeAdapterDataByType(storeTypeNames.get(position).getId());
            }
        });*/

    }

    public void changeAdapterDataByButton(int whichButton){
        data = storeOfficeDB.getSpecificOfficesFromDB(whichButton);
        while(data.remove(null));
        if(data.size() > 0) {
            gridItemAdapter = new OfficeAdapter(appController, data, R.layout.grid_store_item);
            setGridViewAdatper();
        }
    }

    public void changeAdapterDataByType(int whichType){
        data = storeOfficeDB.getStoreOfficeByStoreTypedId(whichType);
        while(data.remove(null));
        if(data.size() > 0) {
            gridItemAdapter = new OfficeAdapter(appController, data, R.layout.grid_store_item);
            setGridViewAdatper();
        }
    }

   /* public void refreshDatas(){
        changeAdapterDataByType(firstStoreTypeId);
        refreshActivityContent();
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
}
