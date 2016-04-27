package com.somoplay.screenshow.model;

import java.util.Date;

/**
 * Created by yaolu on 15-05-25.
 */
public class Subtitle {

    private int screenSubId;
    private boolean deleted;
    private int screenId;
    private Date updatedTs;
    private Date createdTs;
    private int sequenceId;
    private int duration;

    private int subId;
    private boolean dataDeleted;
    private Date dataUpdatedTs;
    private Date dataCreatedTs;
    private int adminId;
    private Date startDate;
    private Date endDate;
    private int showType;
    private int durationSec;
    private int repeatTime;
    private int fonts;
    private String color;
    private int location;
    private int speed;
    private String textContent;

    public Subtitle(int screenSubId, boolean deleted, int screenId, Date updatedTs, Date createdTs,
            int sequenceId, int duration, int subId, boolean dataDeleted,Date dataUpdatedTs,Date dataCreatedTs,
            int adminId, Date startDate, Date endDate,int showType,int durationSec,int repeatTime,
            int fonts, String color, int location, int speed,String textContent)
    {
        super();

        this.screenSubId = screenSubId;
        this.deleted = deleted;
        this.screenId = screenId;
        this.updatedTs = updatedTs;
        this.createdTs = createdTs;
        this.sequenceId = sequenceId;
        this.duration = duration;

        this.subId=subId;
        this.dataDeleted=dataDeleted;
        this.dataUpdatedTs=dataUpdatedTs;
        this.dataCreatedTs=dataCreatedTs;
        this.adminId=adminId;
        this.startDate=startDate;
        this.endDate=endDate;
        this.showType=showType;
        this.durationSec=durationSec;
        this.repeatTime=repeatTime;
        this.fonts=fonts;
        this.color = color;
        this.location=location;
        this.speed=speed;
        this.textContent=textContent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getScreenSubId() {
        return screenSubId;
    }

    public void setScreenSubId(int screenSubId) {
        this.screenSubId = screenSubId;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public boolean isDataDeleted() {
        return dataDeleted;
    }

    public void setDataDeleted(boolean dataDeleted) {
        this.dataDeleted = dataDeleted;
    }

    public Date getDataUpdatedTs() {
        return dataUpdatedTs;
    }

    public void setDataUpdatedTs(Date dataUpdatedTs) {
        this.dataUpdatedTs = dataUpdatedTs;
    }

    public Date getDataCreatedTs() {
        return dataCreatedTs;
    }

    public void setDataCreatedTs(Date dataCreatedTs) {
        this.dataCreatedTs = dataCreatedTs;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getUpdatedTs() {
        return updatedTs;
    }

    public void setUpdatedTs(Date updatedTs) {
        this.updatedTs = updatedTs;
    }

    public Date getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(Date createdTs) {
        this.createdTs = createdTs;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(int durationSec) {
        this.durationSec = durationSec;
    }

    public int getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(int repeatTime) {
        this.repeatTime = repeatTime;
    }

    public int getFonts() {
        return fonts;
    }

    public void setFonts(int fonts) {
        this.fonts = fonts;
    }


    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public String toString(){
        return "id= "+ subId +" fonts= "+fonts+" color= "+color + " repeatTime= "+repeatTime +
                " location= "+location +" speed= "+speed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
