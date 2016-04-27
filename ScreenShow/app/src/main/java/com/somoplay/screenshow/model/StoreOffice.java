package com.somoplay.screenshow.model;

import java.util.Date;

/**
 * Created by JudyShuai on 15-06-12.
 */
public class StoreOffice {
    private int id;
    private boolean deleted;
    private Date updatedTs;
    private Date createdTs;
    private  String name;
    private int storeId;
    private int storeTypeId;
    private int sequence;
    private int type;
    private double rent;
    private double size;
    private String unit;
    private String email;
    private String phone0;
    private String phone1;
    private String phone2;
    private String website;
    private String faxNumber;
    private String mediaUrl;
    private boolean isAdministration;
    private boolean isRent;
    private boolean isWc;
    private boolean isSale;
    private int salePercent;
    private Date saleExpire;
    private String info;
    private int screenId;

    public boolean getIsAdministration() {
        return isAdministration;
    }

    public void setIsAdministration(boolean isAdministration) {
        this.isAdministration = isAdministration;
    }

    public boolean getIsRent() {
        return isRent;
    }

    public void setIsRent(boolean isRent) {
        this.isRent = isRent;
    }

    public boolean getIsWc() {
        return isWc;
    }

    public void setIsWc(boolean isWc) {
        this.isWc = isWc;
    }

    public boolean getIsSale() {
        return isSale;
    }

    public void setIsSale(boolean isSale) {
        this.isSale = isSale;
    }

    public int getSalePercent() {
        return salePercent;
    }

    public void setSalePercent(int salePercent) {
        this.salePercent = salePercent;
    }

    public Date getSaleExpire() {
        return saleExpire;
    }

    public void setSaleExpire(Date saleExpire) {
        this.saleExpire = saleExpire;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone0() {
        return phone0;
    }

    public void setPhone0(String phone0) {
        this.phone0 = phone0;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getInfoOne() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getInfoTwo() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public StoreOffice(int id,boolean deleted,Date updatedTs,Date createdTs,String name,int storeId,
                       int storeTypeId, int sequence,int type, double rent,double size,String unit,
                       String newEmail, String newPhone0, String newPhone1, String newPhone2,
                       String newWebsite, String newFaxNumber, String mediaUrl, boolean isAdministration,
                       boolean isRent, boolean isWc, boolean isSale, int salePercent, Date saleExpire,
                       String info, int screenId)
    {
        super();
        this.id=id;
        this.deleted=deleted;
        this.updatedTs=updatedTs;
        this.createdTs=createdTs;
        this.name=name;
        this.storeId=storeId;
        this.storeTypeId=storeTypeId;
        this.sequence=sequence;
        this.type=type;
        this.rent=rent;
        this.size=size;
        this.unit=unit;
        email = newEmail;
        phone0 = newPhone0;
        phone1 = newPhone1;
        phone2 = newPhone2;
        website = newWebsite;
        faxNumber = newFaxNumber;
        this.isAdministration = isAdministration;
        this.isRent = isRent;
        this.isWc = isWc;
        this.isSale = isSale;
        this.salePercent = salePercent;
        this.saleExpire = saleExpire;
        this.mediaUrl=mediaUrl;
        this.info=info;
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

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getInfoThree() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }
}
