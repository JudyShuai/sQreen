package com.somoplay.screenshow.activity;

import android.os.Bundle;

import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.StoreOfficeDB;
import com.somoplay.screenshow.model.StoreOffice;

/**
 * Created by Shaohua on 8/26/2015.
 */
public class OfficeDetailActivity extends CommonItemDetailActivity {
    private StoreOffice storeOffice;
    private StoreOfficeDB storeOfficeDB = new StoreOfficeDB(this);

    public int itemId;
    public int mediaType = Constants.OFFICE_EXTRA_PHOTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemId = getIntent().getIntExtra(Constants.STORE_OFFICE_ID, 0);
        storeOffice = storeOfficeDB.getStoreOfficeById(itemId);

        setDetailPics(itemId);

        textName = storeOffice.getName();
        price = String.valueOf(storeOffice.getRent());
        salePrice = String.valueOf(storeOffice.getSalePercent() * storeOffice.getRent());
        expirationDate = storeOffice.getSaleExpire().toString();
        textContent = "";

        fillTextIntoTextViews();

        //imagelocalUrls.add(Constants.DEVICE_PATH_OFFICE + "/" + storeOffice.getMediaUrl());

        getExtraPics(mediaType, itemId, Constants.DEVICE_PATH_OFFICE);


        if(storeOffice.getInfoOne().length()>0)
        {
            textContent = textContent + "Info One: " + storeOffice.getInfoOne();
        }
        if(storeOffice.getInfoTwo().length()>0)
        {
            textContent = textContent + "\n\nInfo Two: " + storeOffice.getInfoTwo();
        }
        if(storeOffice.getInfoThree().length()>0)
        {
            textContent = textContent + "\n\nInfo Three: " + storeOffice.getInfoThree();
        }

    }
}
