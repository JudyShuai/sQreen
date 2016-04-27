package com.somoplay.screenshow.model;

import java.util.ArrayList;

/**
 * Created by yaolu on 15-06-08.
 */
public class StoreTypeNameList {

    private String status;
    private String mainUrl;
    private String mainUrl2;
    private String type;
    private String service;

    private ArrayList<StoreTypeName> storeTypeNameArray=new ArrayList<>();

   /* public void updateStoreTypeNameList(StoreTypeNameList storeTypeNameList)
    {
        status = storeTypeNameList.getStatus();
        mainUrl = storeTypeNameList.getMainUrl();
        mainUrl2 = storeTypeNameList.getMainUrl2();
        type = storeTypeNameList.getType();
        service = storeTypeNameList.getService();
        int count = storeTypeNameList.getStoreTypeNameArray().size();

        storeTypeNameArray.clear();
        for(int i=0;i<count;i++)
        {
            StoreTypeName storeTypeName = storeTypeNameList.getStoreTypeNameArray().get(i);
            storeTypeNameArray.add(storeTypeName);
        }
    }*/

    public void updateStoreTypeNameList2(ArrayList<StoreTypeName> arrayList)
    {
        status ="";
        mainUrl ="";
        mainUrl2 = "";
        type = "";
        service = "";
        int count = arrayList.size();

        storeTypeNameArray.clear();
        for(int i=0;i<count;i++) {
            StoreTypeName storeTypeName = arrayList.get(i);
            storeTypeNameArray.add(storeTypeName);
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

    public ArrayList<StoreTypeName> getStoreTypeNameArray() {
        return storeTypeNameArray;
    }

    public void setStoreTypeNameArray(ArrayList<StoreTypeName> storeTypeNameArray) {
        this.storeTypeNameArray = storeTypeNameArray;
    }
}
