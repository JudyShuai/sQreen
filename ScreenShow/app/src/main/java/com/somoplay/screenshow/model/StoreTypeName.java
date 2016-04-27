package com.somoplay.screenshow.model;

import java.util.Date;

/**
 * Created by yaolu on 15-06-08.
 */
public class StoreTypeName {

    private int id;
    private boolean deleted;
    private Date updatedTs;
    private Date createdTs;
    private String name;
    private int storeId;
    private int functionId;
    private int type;
    private int sequence;
    private String mediaUrl;
    //private int screenId;

    public StoreTypeName(int id,boolean deleted,Date updatedTs,Date createdTs,String name,int storeId,
                         int functionId, int type, int sequence,String mediaUrl)
    {
        super();
        this.id=id;
        this.deleted=deleted;
        this.updatedTs=updatedTs;
        this.createdTs=createdTs;
        this.name=name;
        this.storeId=storeId;
        this.functionId = functionId;
        this.type=type;
        this.sequence=sequence;
        this.mediaUrl=mediaUrl;
        //this.screenId = screenId;
    }

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    /*public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }*/
}
