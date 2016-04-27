package com.somoplay.screenshow.model;

import java.util.ArrayList;

/**
 * Created by JudyShuai on 15-06-12.
 */
public class StoreOfficeList {
    private String status;
    private String mainUrl;
    private String mainUrl2;
    private String type;
    private String service;
    private ArrayList<StoreOffice> storeOfficeArray;

    public ArrayList<StoreOffice> getStoreOfficeArray() {
        return storeOfficeArray;
    }

    public void setStoreOfficeArray(ArrayList<StoreOffice> storeOfficeArray) {
        this.storeOfficeArray = storeOfficeArray;
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

    public void updateStoreOfficeList(ArrayList<StoreOffice> arrayList)
    {
        status = "";
        mainUrl = "";
        mainUrl2 = "";
        type ="";
        service ="";
        int count = arrayList.size();

        storeOfficeArray.clear();
        for(int i=0;i<count;i++)
        {
            StoreOffice storeOffice = arrayList.get(i);
            storeOfficeArray.add(storeOffice);
        }
    }
}
