package com.somoplay.screenshow.model;

import java.util.Date;

/**
 * Created by JudyShuai on 15-08-24.
 */
public class CarAndHouse {
    private int storeItemId;
    private boolean deleted;
    private Date updatedTs;
    private Date createdTs;
    private String name;
    private int storeId;
    private int storeTypeId;
    private String localId;
    private int sequence;
    private int type;
    private boolean isKeyOne;
    private boolean isKeyTwo;
    private boolean isKeyThree;
    private double price;
    private double salePercent;
    private Date saleExpire;
    private String infoOne;
    private String infoTwo;
    private String infoThree;
    private String mediaUrl;
    private int screenId;

    public CarAndHouse(int storeItemId, boolean deleted, Date updatedTs, Date createdTs, String name,
                       int storeId, int storeTypeId, String localId, int sequence, int type,
                       boolean isKeyOne, boolean isKeyTwo, boolean isKeyThree, double price,
                       double salePercent, Date saleExpire, String infoOne, String infoTwo,
                       String infoThree, String mediaUrl, int screenId) {
        super();
        this.storeItemId = storeItemId;
        this.deleted = deleted;
        this.updatedTs = updatedTs;
        this.createdTs = createdTs;
        this.name = name;
        this.storeId = storeId;
        this.storeTypeId = storeTypeId;
        this.localId = localId;
        this.sequence = sequence;
        this.type = type;
        this.isKeyOne = isKeyOne;
        this.isKeyTwo = isKeyTwo;
        this.isKeyThree = isKeyThree;
        this.price = price;
        this.salePercent = salePercent;
        this.saleExpire = saleExpire;
        this.infoOne = infoOne;
        this.infoTwo = infoTwo;
        this.infoThree = infoThree;
        this.mediaUrl = mediaUrl;
        this.screenId = screenId;
    }

    public int getStoreItemId() {
        return storeItemId;
    }

    public void setStoreItemId(int storeItemId) {
        this.storeItemId = storeItemId;
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

    public int getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(int storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isKeyOne() {
        return isKeyOne;
    }

    public void setIsKeyOne(boolean isKeyOne) {
        this.isKeyOne = isKeyOne;
    }

    public boolean isKeyTwo() {
        return isKeyTwo;
    }

    public void setIsKeyTwo(boolean isKeyTwo) {
        this.isKeyTwo = isKeyTwo;
    }

    public boolean isKeyThree() {
        return isKeyThree;
    }

    public void setIsKeyThree(boolean isKeyThree) {
        this.isKeyThree = isKeyThree;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSalePercent() {
        return salePercent;
    }

    public void setSalePercent(double salePercent) {
        this.salePercent = salePercent;
    }

    public Date getSaleExpire() {
        return saleExpire;
    }

    public void setSaleExpire(Date saleExpire) {
        this.saleExpire = saleExpire;
    }

    public String getInfoOne() {
        return infoOne;
    }

    public void setInfoOne(String infoOne) {
        this.infoOne = infoOne;
    }

    public String getInfoTwo() {
        return infoTwo;
    }

    public void setInfoTwo(String infoTwo) {
        this.infoTwo = infoTwo;
    }

    public String getInfoThree() {
        return infoThree;
    }

    public void setInfoThree(String infoThree) {
        this.infoThree = infoThree;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }
}
