package com.somoplay.screenshow.model;

import java.util.ArrayList;

/**
 * Created by yaolu on 15-06-08.
 */
public class SubtitleList {

    private String status;
    private String type;
    private String service;

    private ArrayList<Subtitle> subtitleArray;

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

    public ArrayList<Subtitle> getSubtitleArray() {
        return subtitleArray;
    }

    public void setSubtitleArray(ArrayList<Subtitle> subtitleArray) {
        this.subtitleArray = subtitleArray;
    }
  /*  @Override
    public String toString(){

        return "status= "+ status + " type= " + type + " service= "+service + "subtitleArray= "+ subtitleArray.toString();
    }*/
}
