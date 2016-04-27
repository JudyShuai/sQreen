package com.somoplay.screenshow.model;

/**
 * Created by JudyShuai on 15-07-07.
 */
public class ChooseScreen {
    private int screenId;
    private boolean deleted;
    private String updatedTs;
    private String createdTs;
    private String screenName;
    private int adminId;
    private int storeId;
    private int showRestaurant;
    private int showBuilding;
    private int showGeneralStore;
    private int showPhotos;
    private int showAdvertisement;
    private int showContentType;
    private int status;
    private int touchType;
    private int layoutType;
    private String version;
    private String ipAddress;
    private String info;
    private String storeName;
    private String storeAddress;


    public ChooseScreen(int screenId, boolean deleted, String updatedTs, String createdTs, String screenName,
                        int adminId, int storeId, int showRestaurant, int showBuilding, int showGeneralStore,
                        int showPhotos, int showAdvertisement, int showContentType, int status, int touchType,
                        int layoutType, String version, String ipAddress, String info, String storeName,
                        String storeAddress) {

        super();
        this.screenId = screenId;
        this.deleted = deleted;
        this.updatedTs = updatedTs;
        this.createdTs = createdTs;
        this.screenName = screenName;
        this.adminId = adminId;
        this.showRestaurant = showRestaurant;
        this.showBuilding = showBuilding;
        this.showGeneralStore = showGeneralStore;
        this.showPhotos = showPhotos;
        this.showAdvertisement = showAdvertisement;
        this.showContentType = showContentType;
        this.status = status;
        this.touchType = touchType;
        this.layoutType = layoutType;
        this.version = version;
        this.ipAddress = ipAddress;
        this.info = info;
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
    }

    public int getId() {
        return screenId;
    }

    public void setId(int id) {
        this.screenId = id;
    }

    public String getUpdatedTs() {
        return updatedTs;
    }

    public void setUpdatedTs(String updatedTs) {
        this.updatedTs = updatedTs;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public int getShowRestaurant() {
        return showRestaurant;
    }

    public void setShowRestaurant(int showRestaurant) {
        this.showRestaurant = showRestaurant;
    }

    public int getShowBuilding() {
        return showBuilding;
    }

    public void setShowBuilding(int showBuilding) {
        this.showBuilding = showBuilding;
    }

    public int getShowGeneralStore() {
        return showGeneralStore;
    }

    public void setShowGeneralStore(int showGeneralStore) {
        this.showGeneralStore = showGeneralStore;
    }

    public int getShowPhotos() {
        return showPhotos;
    }

    public void setShowPhotos(int showPhotos) {
        this.showPhotos = showPhotos;
    }

    public int getShowAdvertisement() {
        return showAdvertisement;
    }

    public void setShowAdvertisement(int showAdvertisement) {
        this.showAdvertisement = showAdvertisement;
    }

    public int getShowContentType() {
        return showContentType;
    }

    public void setShowContentType(int showContentType) {
        this.showContentType = showContentType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTouchType() {
        return touchType;
    }

    public void setTouchType(int touchType) {
        this.touchType = touchType;
    }

    public int getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreatedTs() {
        return createdTs;
    }

    public void setCreatedTs(String createdTs) {
        this.createdTs = createdTs;
    }
}
