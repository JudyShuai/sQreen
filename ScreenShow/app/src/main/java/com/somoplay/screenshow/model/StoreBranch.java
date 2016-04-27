package com.somoplay.screenshow.model;

import java.util.Date;

/**
 * Created by Shaohua Mao on 2015-07-17.
 */
public class StoreBranch {
    private int storeId;
    private Date updatedTs;
    private Date createdTs;
    private int adminId;
    private int typeId;
    private String storeName;
    private String address;
    private String city;
    private String province;
    private String postcode;
    private String email;
    private String phone0;
    private String phone1;
    private String phone2;
    private String website;
    private String mediaUrl;
    private String backGroundColor;
    private String fontColor;
    private String fontFamilyName;
    private String fontName;
    private int fontHeadSize;
    private int fontTextSize;
    private String logoUrl = "";
    private String info;
    private String keyOneName;
    private String keyTwoName;
    private String keyThreeName;
    private String infoOneName;
    private String infoTwoName;
    private String infoThreeName;
    //private int screenId;

    public StoreBranch(){
        super();
    }



    public StoreBranch(String storeName, int storeId, Date updatedTs, Date createdTs, int adminId,
                       int typeId, String address, String city, String province, String postcode,
                       String email, String phone0, String phone1, String phone2, String website,
                       String mediaUrl, String backGroundColor, String fontColor, String fontFamilyName,
                       String fontName, int fontHeadSize, int fontTextSize, String logoUrl, String info,
                       String keyOneName, String keyTwoName, String keyThreeName, String infoOneName,
                       String infoTwoName, String infoThreeName) {
        super();

        this.storeName = storeName;
        this.storeId = storeId;
        this.updatedTs = updatedTs;
        this.createdTs = createdTs;
        this.adminId = adminId;
        this.typeId = typeId;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postcode = postcode;
        this.email = email;
        this.phone0 = phone0;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.website = website;
        this.mediaUrl = mediaUrl;
        this.backGroundColor = backGroundColor;
        this.fontColor = fontColor;
        this.fontFamilyName = fontFamilyName;
        this.fontName = fontName;
        this.fontHeadSize = fontHeadSize;
        this.fontTextSize = fontTextSize;
        this.logoUrl = logoUrl;
        this.info = info;
        this.keyOneName = keyOneName;
        this.keyTwoName = keyTwoName;
        this.keyThreeName = keyThreeName;
        this.infoOneName = infoOneName;
        this.infoTwoName = infoTwoName;
        this.infoThreeName = infoThreeName;
//        this.screenId = screenId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public String getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(String backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontFamilyName() {
        return fontFamilyName;
    }

    public void setFontFamilyName(String fontFamilyName) {
        this.fontFamilyName = fontFamilyName;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getFontHeadSize() {
        return fontHeadSize;
    }

    public void setFontHeadSize(int fontHeadSize) {
        this.fontHeadSize = fontHeadSize;
    }

    public int getFontTextSize() {
        return fontTextSize;
    }

    public void setFontTextSize(int fontTextSize) {
        this.fontTextSize = fontTextSize;
    }

    public String getKeyOneName() {
        return keyOneName;
    }

    public void setKeyOneName(String keyOneName) {
        this.keyOneName = keyOneName;
    }

    public String getKeyTwoName() {
        return keyTwoName;
    }

    public void setKeyTwoName(String keyTwoName) {
        this.keyTwoName = keyTwoName;
    }

    public String getKeyThreeName() {
        return keyThreeName;
    }

    public void setKeyThreeName(String keyThreeName) {
        this.keyThreeName = keyThreeName;
    }

    public String getInfoOneName() {
        return infoOneName;
    }

    public void setInfoOneName(String infoOneName) {
        this.infoOneName = infoOneName;
    }

    public String getInfoTwoName() {
        return infoTwoName;
    }

    public void setInfoTwoName(String infoTwoName) {
        this.infoTwoName = infoTwoName;
    }

    public String getInfoThreeName() {
        return infoThreeName;
    }

    public void setInfoThreeName(String infoThreeName) {
        this.infoThreeName = infoThreeName;
    }

    /*public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }*/
}
