package com.somoplay.screenshow.model;

import java.util.Date;

/**
 * Created by yaolu on 15-05-25.
 */
public class Media {
    private int id;
    private boolean dataDeleted;
    private Date dataUpdatedTs;
    private Date dataCreatedTs;
    private String mediaName;
    private int adminId;
    private int userId;
    private String userName;
    private Date startDate;
    private Date endDate;
    private int showType;
    private int status;
    private int mediaType;
    private int elementId;
    private String mediaUrl;
    private int durationSec;
    private double version;
    private String mediaLocalUrl;
    private int screenMediaId;
    private int screenId;
    private boolean deleted;
    private Date updatedTs;
    private Date createdTs;
    private int sequenceId;
    private int duration;

    public Media(int id, boolean dataDeleted, Date dataUpdatedTs, Date dataCreatedTs, String mediaName,
                 int adminId, int userId, String userName, Date startDate, Date endDate, int showType,
                 int status, int mediaType, int elementId, String mediaUrl, int durationSec, double version,
                 String mediaLocalUrl, int screenMediaId, int screenId, boolean deleted, Date updatedTs,
                 Date createdTs, int sequenceId, int duration) {
        super();

        this.id= id;
        this.dataDeleted = dataDeleted;
        this.dataUpdatedTs = dataUpdatedTs;
        this.dataCreatedTs = dataCreatedTs;
        this.mediaName = mediaName;
        this.adminId = adminId;
        this.userId = userId;
        this.userName = userName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.showType = showType;
        this.status = status;
        this.mediaType = mediaType;
        this.elementId = elementId;
        this.mediaUrl = mediaUrl;
        this.durationSec = durationSec;
        this.version = version;
        this.mediaLocalUrl = mediaLocalUrl;
        this.screenMediaId = screenMediaId;
        this.screenId = screenId;
        this.deleted = deleted;
        this.updatedTs = updatedTs;
        this.createdTs = createdTs;
        this.sequenceId = sequenceId;
        this.duration = duration;
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

    public int getScreenMediaId() {
        return screenMediaId;
    }

    public void setScreenMediaId(int screenMediaId) {
        this.screenMediaId = screenMediaId;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
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

    public boolean isDeleted() {
        return deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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
        return  status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getMediaLocalUrl() {
        return mediaLocalUrl;
    }

    public void setMediaLocalUrl(String mediaLocalUrl) {
        this.mediaLocalUrl = mediaLocalUrl;
    }
}
