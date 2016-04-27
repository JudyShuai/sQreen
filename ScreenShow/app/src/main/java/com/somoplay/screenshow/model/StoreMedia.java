package com.somoplay.screenshow.model;

import java.util.Date;

/**
 * Created by ann on 2015-11-24.
 */
public class StoreMedia {
    private int id;
    private boolean dataDeleted;
    private Date dataUpdatedTs;
    private Date dataCreatedTs;
    private int adminId;
    private int userId;
    private String userName;
    private String mediaName;
    private int showType;
    private int status;
    private int mediaType;
    private int elementId;
    private String mediaUrl;
    private int durationSec;
    private double version;

    public StoreMedia(){}

    public StoreMedia(double version, int durationSec, int id, boolean dataDeleted, Date dataUpdatedTs, Date dataCreatedTs, int adminId, int userId, String userName, String mediaName, int showType, int status, int mediaType, int elementId, String mediaUrl) {
        this.version = version;
        this.durationSec = durationSec;
        this.id = id;
        this.dataDeleted = dataDeleted;
        this.dataUpdatedTs = dataUpdatedTs;
        this.dataCreatedTs = dataCreatedTs;
        this.adminId = adminId;
        this.userId = userId;
        this.userName = userName;
        this.mediaName = mediaName;
        this.showType = showType;
        this.status = status;
        this.mediaType = mediaType;
        this.elementId = elementId;
        this.mediaUrl = mediaUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public int getElementId() {
        return elementId;
    }

    public void setElementId(int elementId) {
        this.elementId = elementId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public int getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(int durationSec) {
        this.durationSec = durationSec;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }
}
