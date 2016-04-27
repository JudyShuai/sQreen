package com.somoplay.screenshow.model;

import java.util.ArrayList;

/**
 * Created by yaolu on 15-05-25.
 */
public class MediaList {

    private String status;
    private String type;
    private String service;
    private String adsImageUrl;
    private String adsImageUrl2;
    private String adsVideoUrl1;
    private String adsVideoUrl2;
    private String photoShowImageUrl1;
    private String photoShowImageUrl2;
    private String storeImageUrl1;
    private String storeImageUrl2;
    private String officeImageUrl1;
    private String officeImageUrl2;
    private String dishImageUrl1;
    private String dishImageUrl2;
    private String typeImageUrl1;
    private String typeImageUrl2;

    private ArrayList<Media> mediaArray;

    public MediaList(){
        mediaArray = new ArrayList<Media>();
    }

    public ArrayList<Media> getMediaArray() {
        return mediaArray;
    }

    public void setMediaArray(ArrayList<Media> mediaArray) {
        this.mediaArray = mediaArray;
    }

    public String getAdsImageUrl() {
        return adsImageUrl;
    }

    public void setAdsImageUrl(String adsImageUrl) {
        this.adsImageUrl = adsImageUrl;
    }

    public String getAdsImageUrl2() {
        return adsImageUrl2;
    }

    public void setAdsImageUrl2(String adsImageUrl2) {
        this.adsImageUrl2 = adsImageUrl2;
    }

    public String getAdsVideoUrl1() {
        return adsVideoUrl1;
    }

    public void setAdsVideoUrl1(String adsVideoUrl1) {
        this.adsVideoUrl1 = adsVideoUrl1;
    }

    public String getAdsVideoUrl2() {
        return adsVideoUrl2;
    }

    public void setAdsVideoUrl2(String adsVideoUrl2) {
        this.adsVideoUrl2 = adsVideoUrl2;
    }

    public String getPhotoShowImageUrl1() {
        return photoShowImageUrl1;
    }

    public void setPhotoShowImageUrl1(String photoShowImageUrl1) {
        this.photoShowImageUrl1 = photoShowImageUrl1;
    }

    public String getPhotoShowImageUrl2() {
        return photoShowImageUrl2;
    }

    public void setPhotoShowImageUrl2(String photoShowImageUrl2) {
        this.photoShowImageUrl2 = photoShowImageUrl2;
    }

    public String getStoreImageUrl1() {
        return storeImageUrl1;
    }

    public void setStoreImageUrl1(String storeImageUrl1) {
        this.storeImageUrl1 = storeImageUrl1;
    }

    public String getStoreImageUrl2() {
        return storeImageUrl2;
    }

    public void setStoreImageUrl2(String storeImageUrl2) {
        this.storeImageUrl2 = storeImageUrl2;
    }

    public String getOfficeImageUrl1() {
        return officeImageUrl1;
    }

    public void setOfficeImageUrl1(String officeImageUrl1) {
        this.officeImageUrl1 = officeImageUrl1;
    }

    public String getOfficeImageUrl2() {
        return officeImageUrl2;
    }

    public void setOfficeImageUrl2(String officeImageUrl2) {
        this.officeImageUrl2 = officeImageUrl2;
    }

    public String getDishImageUrl1() {
        return dishImageUrl1;
    }

    public void setDishImageUrl1(String dishImageUrl1) {
        this.dishImageUrl1 = dishImageUrl1;
    }

    public String getDishImageUrl2() {
        return dishImageUrl2;
    }

    public void setDishImageUrl2(String dishImageUrl2) {
        this.dishImageUrl2 = dishImageUrl2;
    }

    public String getTypeImageUrl1() {
        return typeImageUrl1;
    }

    public void setTypeImageUrl1(String typeImageUrl1) {
        this.typeImageUrl1 = typeImageUrl1;
    }

    public String getTypeImageUrl2() {
        return typeImageUrl2;
    }

    public void setTypeImageUrl2(String typeImageUrl2) {
        this.typeImageUrl2 = typeImageUrl2;
    }

    public int addMedia(Media media){
        mediaArray.add(media);
        return mediaArray.size();

    }

    public int getDataNumber() {
        return mediaArray.size();
    }
    public boolean haveNewData() {
        return mediaArray.size()>0;
    }

    public  Media getMediaItem(int index){
        return mediaArray.get(index);
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


}
