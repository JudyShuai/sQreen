package com.somoplay.screenshow.Useless;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.activity.BaseActivity;
import com.somoplay.screenshow.activity.DishDetailActivity;
import com.somoplay.screenshow.activity.MediaShowActivity;
import com.somoplay.screenshow.activity.OfficeShowActivity;
import com.somoplay.screenshow.activity.StoreIntroductionActivity;
import com.somoplay.screenshow.adapter.StoreDishAdapter;
import com.somoplay.screenshow.adapter.StoreTypeAdapter;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.StoreBranchDB;
import com.somoplay.screenshow.database.StoreDishDB;
import com.somoplay.screenshow.database.StoreTypeNameDB;
import com.somoplay.screenshow.model.StoreBranch;
import com.somoplay.screenshow.model.StoreDish;
import com.somoplay.screenshow.model.StoreDishList;
import com.somoplay.screenshow.model.StoreTypeName;
import com.somoplay.screenshow.model.StoreTypeNameList;

import java.util.ArrayList;


/**
 * Created by yaolu on 15-06-07.
 */
public class StoreShowActivity extends BaseActivity {

    private GridView gridView;
    private ListView leftlist,rightlist;

    private TextView title;
    private TextView storeTypeNameTv;
    private ImageView icon;
    private Button button_new;
    private Button button_sale;
    private Button button_specials;
    private Button button_introduction;
    private Button button_photos;

    private int storeId;
    private int firstStoreTypeId = 1;

    private AppController appController = AppController.getInstance();
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private StoreDishDB storeDishDB = new StoreDishDB(this);
    private StoreTypeNameDB storeTypeNameDB = new StoreTypeNameDB(this);

    private StoreTypeAdapter storeTypeLeftAdapter;
    private StoreTypeNameList storeTypeNameLeftList = new StoreTypeNameList();

    private StoreTypeAdapter storeTypeRightAdapter;
    private StoreTypeNameList storeTypeNameRightList  = new StoreTypeNameList();

    private StoreDishAdapter storeDishAdapter;
    private StoreDishList storeDishList = new StoreDishList();

    private StoreBranch storeBranch = new StoreBranch();
    private StoreBranchDB storeBranchDB = new StoreBranchDB(this);

    private ArrayList<StoreTypeName> storeTypeNames = new ArrayList< StoreTypeName>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_show);

        appController.setGeneralActivity(this);

        sharedPref = getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        String backgroundColor = "#" + sharedPref.getString(Constants.KEY_BACKGROUND_COLOR, "ffffff");
        storeId = sharedPref.getInt(Constants.KEY_STORE_ID, 1);
        editor = sharedPref.edit();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_STORE_SHOW_ACTIVITY);
        editor.commit();


        LinearLayout ll = (LinearLayout)findViewById(R.id.store_show_layout);
        ll.setBackgroundColor(Color.parseColor(backgroundColor));

        button_new = (Button)findViewById(R.id.buttonNew);
        button_sale = (Button)findViewById(R.id.buttonSale);
        button_specials = (Button)findViewById(R.id.buttonSpecials);
        button_introduction = (Button)findViewById(R.id.buttonIntroduction);
        button_photos = (Button)findViewById(R.id.buttonPhotos);

        title = (TextView) findViewById(R.id.item_text);
        icon = (ImageView) findViewById(R.id.item_image);

        String filePath = new String("file:///" + Constants.DEVICE_PATH_LOGO + "/" +
                storeBranchDB.getStoreBranchByStoreId(storeId).getLogoUrl());
        icon.setImageURI(Uri.parse(filePath));

        storeTypeNameTv = (TextView)findViewById(R.id.store_type_name);

        setText(title, true);
        setText(button_new, false);
        setText(button_sale, false);
        setText(button_specials, false);
        setText(button_introduction, false);
        setText(button_photos, false);
        setText(storeTypeNameTv,false);

        gridView = (GridView) findViewById(R.id.gridView1);
        storeDishAdapter= new StoreDishAdapter(appController,storeDishList.getStoreDishArray(),R.layout.grid_store_item );
        gridView.setAdapter(storeDishAdapter);
        storeTypeNames = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2, storeId);
        ArrayList<StoreDish> dataGridView = storeDishDB.getDishesByStoreTypeId(storeTypeNames.get(0).getId());
        storeDishList.updateStoreDishList2(dataGridView);
        afterGettingStoreDishData(storeDishList);

        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNew();
            }
        });

        button_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSale();
            }
        });

        button_specials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSpecials();
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
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoreDish storeDish = storeDishList.getStoreDishArray().get(position);

                int dishId = storeDish.getId();
                Intent intent = new Intent(StoreShowActivity.this, DishDetailActivity.class);
                intent.putExtra(Constants.DISH_ID, dishId);
                intent.putExtra(Constants.MEDIA_TYPE, Constants.DISH_EXTRA_PHOTO);
                intent.putExtra(Constants.KEY_IS_DISH, true);
                startActivity(intent);
            }
        });


        leftlist=(ListView)findViewById(R.id.leftlistview);
        for(int i=0; i<storeTypeNameLeftList.getStoreTypeNameArray().size();i++) {
            StoreTypeName toPrintStoreTypeName = storeTypeNameLeftList.getStoreTypeNameArray().get(i);
            System.out.println("The storeTypeName: id: "+ toPrintStoreTypeName.getId()+"/ name: "+toPrintStoreTypeName.getName()+
            "/ type: "+toPrintStoreTypeName.getType()+"/ sequence: "+toPrintStoreTypeName.getSequence());

        }
        storeTypeLeftAdapter = new StoreTypeAdapter(appController,storeTypeNameLeftList.getStoreTypeNameArray(),R.layout.menu_list_item);
        leftlist.setAdapter(storeTypeLeftAdapter);

        ArrayList <StoreTypeName> dataLeft = storeTypeNameDB.getStoreTypeNamesByTypeAndId(1 , storeId) ;
        storeTypeNameLeftList.updateStoreTypeNameList2(dataLeft);
        afterGettingLeftStoreType(storeTypeNameLeftList);

        leftlist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        clickNew();
                        break;
                    case 1:
                        clickSale();
                        break;
                    case 2:
                        clickSpecials();
                        break;
                    case 3:
                        clickIntroduction();
                        break;
                    case 4:
                        clickPhotos();
                        break;
                    default:
                        break;
                }
            }
        });


      /*  fillContentForListView(storeTypeRightAdapter, rightlist, R.id.rightlistview, storeTypeNameRightList,
                2, 1);*/

        rightlist=(ListView)findViewById(R.id.rightlistview);
        storeTypeRightAdapter = new StoreTypeAdapter(appController,storeTypeNameRightList.getStoreTypeNameArray(),R.layout.menu_list_item);
        rightlist.setAdapter(storeTypeRightAdapter);

        ArrayList <StoreTypeName> dataRight = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2 , storeId) ;
        storeTypeNameRightList.updateStoreTypeNameList2(dataRight);
        afterGettingRightStoreType(storeTypeNameRightList);

        rightlist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                StoreDishDB storeDishDB=new StoreDishDB(appController.getApplicationContext());
                ArrayList<StoreDish> dataRightList= storeDishDB.getDishesByStoreTypeId(storeTypeNames.get(position).getId());
                storeDishList.updateStoreDishList2(dataRightList);
                storeDishAdapter= new StoreDishAdapter(appController, storeDishList.getStoreDishArray(),R.layout.grid_store_item);
                gridView.setAdapter(storeDishAdapter);
                afterGettingStoreDishData(storeDishList);
            }
        });
    }


    public void afterGettingLeftStoreType( StoreTypeNameList storeTypeNameList ){
        storeTypeLeftAdapter.notifyDataSetChanged();
    }

    public void afterGettingRightStoreType( StoreTypeNameList storeTypeNameList ){
        storeTypeRightAdapter.notifyDataSetChanged();
    }

    public void afterGettingStoreDishData( StoreDishList storeDishList ){
        storeDishAdapter.notifyDataSetChanged();
    }


    @Override
    public void refreshActivityContent(){
        storeTypeNames = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2, storeId);
        ArrayList<StoreDish> dataGridView = storeDishDB.getDishesByStoreTypeId(storeTypeNames.get(0).getId());
        storeDishList.updateStoreDishList2(dataGridView);
        afterGettingStoreDishData(storeDishList);

        ArrayList <StoreTypeName> dataLeft = storeTypeNameDB.getStoreTypeNamesByTypeAndId(1, storeId) ;
        storeTypeNameLeftList.updateStoreTypeNameList2(dataLeft);
        afterGettingLeftStoreType(storeTypeNameLeftList);

        ArrayList <StoreTypeName> dataRight = storeTypeNameDB.getStoreTypeNamesByTypeAndId(2, storeId) ;
        storeTypeNameRightList.updateStoreTypeNameList2(dataRight);
        afterGettingRightStoreType(storeTypeNameRightList);
    }
    public void clickNew() {
        StoreDishList newStoreDishList = new StoreDishList();
        newStoreDishList.setStoreDishArray(storeDishDB.getSpecificDishesFromDB(Constants.KEY_IS_KEY_ONE, storeId));
        storeDishAdapter = new StoreDishAdapter(appController, newStoreDishList.getStoreDishArray(),R.layout.grid_store_item);
        gridView.setAdapter(storeDishAdapter);
    }
    public void clickSale(){
        StoreDishList onSaleStoreDishList = new StoreDishList();
        onSaleStoreDishList.setStoreDishArray(storeDishDB.getSpecificDishesFromDB(Constants.KEY_IS_KEY_TWO, storeId));
        storeDishAdapter= new StoreDishAdapter(appController, onSaleStoreDishList.getStoreDishArray(),R.layout.grid_store_item);
        gridView.setAdapter(storeDishAdapter);
    }
    public void clickSpecials() {
        StoreDishList specialStoreDishList = new StoreDishList();
        specialStoreDishList.setStoreDishArray(storeDishDB.getSpecificDishesFromDB(Constants.KEY_IS_KEY_THREE, storeId));
        storeDishAdapter = new StoreDishAdapter(appController, specialStoreDishList.getStoreDishArray(), R.layout.grid_store_item);
        gridView.setAdapter(storeDishAdapter);
    }
    public void clickIntroduction() {
        Intent intent = new Intent(StoreShowActivity.this, StoreIntroductionActivity.class);
        startActivity(intent);
    }
    public void clickPhotos() {
        Intent intent = new Intent(StoreShowActivity.this, MediaShowActivity.class);
        intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
        intent.putExtra(Constants.KEY_IS_STORE, true);
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_STORE_SHOW_ACTIVITY);
        editor.commit();
    }
    @Override
    public void onPause(){
        super.onPause();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
        editor.commit();
    }

    @Override
    public void goToPhotos() {

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

        else if(showContentType == 5){
            Intent intent = new Intent(this, OfficeShowActivity.class);
            startActivity(intent);
        }
    }





    public void setText(TextView tv, boolean isTitle){
        String fontColor = sharedPref.getString(Constants.KEY_FONT_COLOR, "#ffffff");
        String storeName = sharedPref.getString(Constants.KEY_STORE_NAME, "normal");
        int fontHeadSize = sharedPref.getInt(Constants.KEY_FONT_HEAD_SIZE, 30);
        int fontTextSize = sharedPref.getInt(Constants.KEY_FONT_TEXT_SIZE, 10);

        if (fontColor == ""){
            fontColor = "#ffffff";
        }else if(fontColor.charAt(0) != '#'){
            fontColor = "#" + fontColor;
        }

        tv.setTextColor(Color.parseColor(fontColor));
        if(isTitle){
            tv.setTextSize((float) fontHeadSize);
        }else{
            tv.setTextSize((float) fontTextSize);
        }

        Typeface typeFace=Typeface.create(storeName, Typeface.NORMAL);
        if(typeFace != null) {
            tv.setTypeface(typeFace);
        }
    }


//    public void fillContentForListView(StoreTypeAdapter typeAdapter, ListView lv, int resId,
//               StoreTypeNameList typeList, int storeId, int type){
//        lv = (ListView)findViewById(resId);
//        typeAdapter = new StoreTypeAdapter(this, typeList);
//        lv.setAdapter(typeAdapter);
//
//        ArrayList <StoreTypeName> data = storeTypeNameDB.getStoreTypeNamesByTypeAndId(type, storeId) ;
//        typeList.updateStoreTypeNameList2(data);
//        afterGettingRightStoreType(typeList);
//    }

}
