package com.somoplay.screenshow.model;

import java.util.ArrayList;

/**
 * Created by JudyShuai on 15-07-06.
 */
public class ChooseScreenList {

    private  String status;
    private  String type;
    private  String service;

    private ArrayList<ChooseScreen> chooseScreenArray= new ArrayList<>();

    public void updateStoreTypeNameList2(ArrayList<ChooseScreen> arrayList)
    {
        status ="";
        type ="";
        service = "";
        int count = arrayList.size();

        chooseScreenArray.clear();
        for(int i=0;i<count;i++) {
            ChooseScreen chooseScreen = arrayList.get(i);
            chooseScreenArray.add(chooseScreen);
        }

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public ArrayList<ChooseScreen> getChooseScreenArray() {
        return chooseScreenArray;
    }

    public void setChooseScreenArray(ArrayList<ChooseScreen> chooseScreenArray) {
        this.chooseScreenArray = chooseScreenArray;
    }

}
