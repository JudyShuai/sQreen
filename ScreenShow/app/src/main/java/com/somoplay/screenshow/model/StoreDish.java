package com.somoplay.screenshow.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yaolu on 15-06-08.
 */
public class StoreDish {

    private int id;
    private boolean deleted;
    private Date updatedTs;
    private Date createdTs;
    private String name;
    private int storeId;
    private int storeTypeId;
    private String localId;
    private int sequence;
    private int type;
    private boolean isSpecialty;
    private boolean isDeal;
    private boolean isNew;
    private double price;
    private double salePercent;
    private Date saleExpire;
    private String materials;
    private String mediaUrl;
    private String nutrition;
    private String info;
    private String mediaLocalUrl;
    private int screenId;

    private ArrayList<Media> mediaArray= new ArrayList<>();

    private String status;
    private String mainUrl;
    private String mainUrl2;
    private String serviceType;
    private String service;


    public StoreDish(int id, boolean deleted, Date updatedTs, Date createdTs, String name, int storeId,
                     int storeTypeId, String localId, int sequence, int type, boolean isSpecialty,
                     boolean isDeal, boolean isNew, double price, double salePercent, Date saleExpire,
                     String materials, String mediaUrl, String nutrition, String info, String mediaLocalUrl, int screenId) {
        super();
        this.id = id;
        this.deleted = deleted;
        this.updatedTs = updatedTs;
        this.createdTs = createdTs;
        this.name = name;
        this.storeId = storeId;
        this.storeTypeId = storeTypeId;
        this.localId = localId;
        this.sequence = sequence;
        this.type = type;
        this.isSpecialty = isSpecialty;
        this.isDeal = isDeal;
        this.isNew = isNew;
        this.price = price;
        this.salePercent = salePercent;
        this.saleExpire = saleExpire;
        this.materials = materials;
        this.mediaUrl = mediaUrl;
        this.nutrition = nutrition;
        this.info = info;
        this.mediaLocalUrl=mediaLocalUrl;
        this.screenId = screenId;
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

    public boolean isSpecialty() {
        return isSpecialty;
    }

    public void setSpecialty(boolean isSpecialty) {
        this.isSpecialty = isSpecialty;
    }

    public boolean isDeal() {
        return isDeal;
    }

    public void setDeal(boolean isDeal) {
        this.isDeal = isDeal;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
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
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getInfoTwo() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public String getInfoThree() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<Media> getMediaArray() {
        return mediaArray;
    }

    public void setMediaArray(ArrayList<Media> mediaArray) {
        this.mediaArray = mediaArray;
    }

    public String getStatus() {
        return status;
    }

    public void setMediaLocalUrl(String mediaLocalUrl) {
        this.mediaLocalUrl = mediaLocalUrl;
    }

    public String getMainUrl() {
        return mainUrl;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getMediaLocalUrl() {
        return mediaLocalUrl;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }
}
