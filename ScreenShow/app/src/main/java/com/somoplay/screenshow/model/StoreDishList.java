package com.somoplay.screenshow.model;

import com.somoplay.screenshow.database.StoreDishDB;

import java.util.ArrayList;

/**
 * Created by yaolu on 15-06-08.
 */
public class StoreDishList {
    private String status;
    private String mainUrl;
    private String mainUrl2;
    private String type;
    private String service;

    //for merge test

    private ArrayList<StoreDish> storeDishArray= new ArrayList<>();

    public void updateStoreDishList(StoreDishList storeDishList)
    {
        status = storeDishList.getStatus();
        mainUrl = storeDishList.getMainUrl();
        mainUrl2 = storeDishList.getMainUrl2();
        type = storeDishList.getType();
        service = storeDishList.getService();
        int count = storeDishList.getStoreDishArray().size();

        storeDishArray.clear();
        for(int i=0;i<count;i++)
        {
            StoreDish storeDish = storeDishList.getStoreDishArray().get(i);
            storeDishArray.add(storeDish);
        }
    }

    public void updateStoreDishList2(ArrayList<StoreDish> arrayList)
    {
        status = "";
        mainUrl = "";
        mainUrl2 = "";
        type ="";
        service ="";
        int count = arrayList.size();

        storeDishArray.clear();
        for(int i=0;i<count;i++)
        {
            StoreDish storeDish = arrayList.get(i);
            storeDishArray.add(storeDish);
        }
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getMainUrl2() {
        return mainUrl2;
    }

    public void setMainUrl2(String mainUrl2) {
        this.mainUrl2 = mainUrl2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public ArrayList<StoreDish> getStoreDishArray() {
        return storeDishArray;
    }

    public void setStoreDishArray(ArrayList<StoreDish> storeDishArray) {
        this.storeDishArray = storeDishArray;
    }
}
