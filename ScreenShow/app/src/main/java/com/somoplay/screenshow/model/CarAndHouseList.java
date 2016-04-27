package com.somoplay.screenshow.model;

import java.util.ArrayList;

/**
 * Created by JudyShuai on 15-08-24.
 */
public class CarAndHouseList {
    private String status;
    private String mainUrl;
    private String mainUrl2;
    private String type;
    private String service;

    //for merge test

    private ArrayList<CarAndHouse> carAndHouseArray = new ArrayList<>();

    public void updateCarAndHouseList(CarAndHouseList carAndHouseList)
    {
        status = carAndHouseList.getStatus();
        mainUrl = carAndHouseList.getMainUrl();
        mainUrl2 = carAndHouseList.getMainUrl2();
        type = carAndHouseList.getType();
        service = carAndHouseList.getService();
        int count = carAndHouseList.getCarAndHouseArray().size();

        carAndHouseArray.clear();
        for(int i=0;i<count;i++)
        {
            CarAndHouse carAndHouse = carAndHouseList.getCarAndHouseArray().get(i);
            carAndHouseArray.add(carAndHouse);
        }
    }

    public void updateCarAndHouseList2(ArrayList<CarAndHouse> arrayList)
    {
        status = "";
        mainUrl = "";
        mainUrl2 = "";
        type ="";
        service ="";
        int count = arrayList.size();

        carAndHouseArray.clear();
        for(int i=0;i<count;i++)
        {
            CarAndHouse carAndHouse = arrayList.get(i);
            carAndHouseArray.add(carAndHouse);
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

    public ArrayList<CarAndHouse> getCarAndHouseArray() {
        return carAndHouseArray;
    }

    public void setCarAndHouseArray(ArrayList<CarAndHouse> carAndHouseArray) {
        this.carAndHouseArray = carAndHouseArray;
    }
}
