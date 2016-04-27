package com.somoplay.screenshow.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.CarAndHouseDB;
import com.somoplay.screenshow.database.StoreMediaDB;
import com.somoplay.screenshow.model.CarAndHouse;

/**
 * Created by JudyShuai on 15-08-25.
 */
public class CarAndHouseDetailActivity extends CommonItemDetailActivity {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private CarAndHouse carAndHouse;
    private CarAndHouseDB carAndHouseDB = new CarAndHouseDB(this);
    private StoreMediaDB storeMediaDB = new StoreMediaDB(this);
    int firstStoreTypeId = 0;
    private CommonItemDetailActivity commonItemDetailActivity = new CommonItemDetailActivity();



    public int itemId;
    public int mediaType = Constants.CAR_AND_HOUSE_EXTRA_PHOTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemId = getIntent().getIntExtra(Constants.CARHOUSE_ID, 0);
        carAndHouse = carAndHouseDB.getCarAndHouseById(itemId);

        setDetailPics(itemId);

        textName = carAndHouse.getName();

        price = String.valueOf(carAndHouse.getPrice());
        if(carAndHouse.getSalePercent() > 0){
            salePrice = String.valueOf(carAndHouse.getSalePercent() * carAndHouse.getPrice());
        }else{
            salePrice = "";
        }

        if(carAndHouse.getSaleExpire() == null){
            expirationDate = "2000-01-01 22:57:44.0";
        }else{
            expirationDate = carAndHouse.getSaleExpire().toString();
        }

        price = stringToDecimal(price);
        salePrice = stringToDecimal(salePrice);

        textContent = "";

        fillTextIntoTextViews();

        //imagelocalUrls.add(Constants.DEVICE_PATH_STOREITEM + "/" + carAndHouse.getMediaUrl());

        getExtraPics(mediaType, itemId, Constants.DEVICE_PATH_STOREITEM);

        if(carAndHouse.getInfoOne().length()>0)
        {
            textContent = textContent + "Info One: " + carAndHouse.getInfoOne();
        }
        if(carAndHouse.getInfoTwo().length()>0)
        {
            textContent = textContent + "\n\nInfo Two: " + carAndHouse.getInfoTwo();
        }
        if(carAndHouse.getInfoThree().length()>0)
        {
            textContent = textContent + "\n\nInfo Three: " + carAndHouse.getInfoThree();
        }

    }

}
