package com.somoplay.screenshow.app;

/**
 * Created by Shaohua Mao on 2015-07-09.
 */
public class Constants {
    // Shared pref mode
    public static final int PRIVATE_MODE = 0;

    // Image related constants
    public static final int IMAGE_MAX_WIDTH = 400;
    public static final int IMAGE_MAX_HEIGHT = 300;

    //Constants for checking data
    public static final int DISHES_AMOUNT = 3;    //??????????????????
    public static final int OFFICES_AMOUNT = 30;
    public static final int CARANDHOUES_AMOUNT = 30;    //?????????????????????????
    public static final int ADS_MEDIA_AMOUNT = 2;
    public static final int USER_PHOTO_MEDIA_AMOUNT = 2;

    // Sharedpref file name
    public static final String PREF_NAME = "AndroidHivePref";
    public static final String currentTime = "currentTime";
    public static final String firstRun = "firstRun";


    // All Shared Preferences Keys
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_CURRENT_ACTIVITY = "currentActivity";

    //file path
    public static final String DEVICE_PATH_ADS = "/data/data/com.somoplay.screenshow/app_imageDir/advertisement";
    //public static final String DEVICE_PATH_ADS = "/data/data/com.somoplay.screenshow/app_imageDir";
    public static final String DEVICE_PATH_LOGO = "/data/data/com.somoplay.screenshow/app_imageDir/logo";
    public static final String DEVICE_PATH_DISH = "/data/data/com.somoplay.screenshow/app_imageDir/dish";
    public static final String DEVICE_PATH_OFFICE = "/data/data/com.somoplay.screenshow/app_imageDir/office";
    public static final String DEVICE_PATH_PHOTOSHOW = "/data/data/com.somoplay.screenshow/app_imageDir/photoshow";
    public static final String DEVICE_PATH_STORE = "/data/data/com.somoplay.screenshow/app_imageDir/store";
    public static final String DEVICE_PATH_STOREITEM = "/data/data/com.somoplay.screenshow/app_imageDir/storeitem";
    public static final String DEVICE_PATH_TYPE = "/data/data/com.somoplay.screenshow/app_imageDir/type";
    public static final String DEVICE_LOGO_URL = "http://eadate.com/screenshow/uploads/images/logo/logo.png";


    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_ADMIN_ID = "adminId";


    //Login and setting and screenInfo keys
    public static final String KEY_RGBCOLOR_BACKGROUND = "rgbColorBackground";
    public static final String KEY_RGBCOLOR_TEXT = "rgbColorText";
    public static final String KEY_SCREEN_ID = "screen_id";
    public static final String KEY_UPDATED_TS = "updatedTs";
    public static final String KEY_SCREEN_NAME = "screenName";
    public static final String KEY_SHOW_RESTAURANT = "showRestaurant";
    public static final String KEY_SHOW_BUILDING = "showBuilding";
    public static final String KEY_SHOW_GENERAL_STORE = "showGeneralStore";
    public static final String KEY_SHOW_PHOTOS = "showPhotos";
    public static final String KEY_SHOW_ADVERTISEMENT = "showAdvertisement";
    public static final String KEY_SHOW_CONTENT_TYPE = "showContentType";
    public static final String KEY_PREVIOUS_CONTENT_TYPE = "previousContentType";
    public static final String KEY_STATUS = "status";
    public static final String KEY_TOUCH_TYPE = "touchType";
    public static final String KEY_LAYOUT_TYPE = "layoutType";
    public static final String KEY_VERSION = "version";
    public static final String KEY_IP_ADDRESS = "ipAddress";
    public static final String KEY_INFO = "info";
    public static final String KEY_STORE_ID = "storeId";
    public static final String KEY_STORE_NAME = "storeName";
    public static final String KEY_STORE_ADDRESS = "storeAddress";
    public static final String KEY_IS_SCREEN_DELETED = "screenDeleted";

	
	
	
	//Font related keys
    public static final String KEY_BACKGROUND_COLOR = "backgroundColor";
    public static final String KEY_FONT_COLOR= "fontColor";
    public static final String KEY_SRORE_NAME= "storeName";
    public static final String KEY_FONT_NAME = "fontName";
    public static final String KEY_FONT_HEAD_SIZE= "fontHeadSize";
    public static final String KEY_FONT_TEXT_SIZE= "fontTextSize";



    //Constants to check variables for different activities.
    public static final String KEY_IS_ADVERTISEMENT = "isAdvertisement";
    public static final String KEY_IS_USERPHOTO = "isUserPhoto";
    public static final String KEY_IS_BUILDING = "isBuilding";


    public static final int KEY_IS_KEY_ONE = 1;
    public static final int KEY_IS_KEY_TWO = 2;
    public static final int KEY_IS_KEY_THREE = 3;

	
	
	//Constants to check current activity
    public static final int CURRENT_NO_NEED_TO_UPDATE = 0;
	public static final int CURRENT_OFFICE_SHOW_ACTIVITY = 1;
	public static final int CURRENT_MEDIA_SHOW_ACTIVITY = 2;
	public static final int CURRENT_STORE_SHOW_ACTIVITY = 3;
	public static final int CURRENT_ITEM_DETAIL_ACTIVITY = 4;
	public static final int CURRENT_INTRODUCTION_ACTIVITY = 5;
	public static final int CURRENT_CARANDHOUSE_ACTIVITY = 6;



    //0-ad_image,1-ad_video, 2-photo_show, 3-store, 5-office, 6-dish, 7-storetype
    public static final int ADS_IMAGE = 0;
    public static final int ADS_VIDEO = 1;
    public static final int PHOTO_SHOW = 2;
    public static final int STORE_AND_OFFICE_PHOTO = 3;
    public static final int CAR_AND_HOUSE_EXTRA_PHOTO = 4;
    public static final int OFFICE_EXTRA_PHOTO = 5;
    public static final int DISH_EXTRA_PHOTO = 6;

	

    // Email address (make variable public to access from outside)
    public static final String KEY_IS_STORE = "isStore";
    public static final String KEY_IS_DISH = "isDish";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_BRANCH_UPDATE_TS = "branchUpdateTs";
    public static final String KEY_STORE_TYPE_NAME_UPDATE_TS = "storeTypeNameUpdateTs";
    public static final String KEY_OFFICE_TYPE_NAME_UPDATE_TS = "officeTypeNameUpdateTs";
    public static final String KEY_DISH_UPDATE_TS = "dishUpdateTs";
    public static final String KEY_OFFICE_UPDATE_TS = "officeUpdateTs";
    public static final String KEY_STORE_MEDIAS_UPDATE_TS = "storeMediasUpdateTs";
    public static final String KEY_OFFICE_MEDIAS_UPDATE_TS = "officeMediasUpdateTs";
    public static final String KEY_SUBTITLES_UPDATE_TS = "subtitlesUpdateTs";
    public static final String KEY_MEDIALIST_UPDATE_TS = "mediaListUpdateTs";
    public static final String KEY_IS_SUBTITLE_CHANGED = "subtitleIsChanged";

    // database constants
    public static final String DB_NAME = "StoreInfomation.db";
    public static final int    DB_VERSION = 1;

    // storetype table constants
    public static final String STORE_TYPE_NAME_TABLE = "storeTypeName";

    public static final String STORE_TYPE_NAME_ID = "store_type_name_id";
    public static final int    STORE_TYPE_NAME_ID_COL = 0;

    public static final String STORE_TYPE_NAME_DELETED = "deleted";
    public static final int    STORE_TYPE_NAME_DELETED_COL = 1;

    public static final String STORE_TYPE_NAME_UPDATED_TS = "updated_ts";
    public static final int    STORE_TYPE_NAME_UPDATED_TS_COL = 2;

    public static final String STORE_TYPE_NAME_CREATED_TS= "created_ts";
    public static final int    STORE_TYPE_NAME_CREATED_TS_COL = 3;

    public static final String STORE_TYPE_NAME_NAME = "name";
    public static final int    STORE_TYPE_NAME_NAME_COL = 4;

    public static final String STORE_TYPE_NAME_STORE_ID = "store_id";
    public static final int    STORE_TYPE_NAME_STORE_ID_COL = 5;

    public static final String STORE_TYPE_NAME_FUNCTION_ID = "function_id";
    public static final int    STORE_TYPE_NAME_FUNCTION_ID_COL = 6;

    public static final String STORE_TYPE_NAME_TYPE = "type";
    public static final int    STORE_TYPE_NAME_TYPE_COL = 7;

    public static final String STORE_TYPE_NAME_SEQUENCE = "sequence";
    public static final int    STORE_TYPE_NAME_SEQUENCE_COL = 8;

    public static final String STORE_TYPE_NAME_MEDIA_URL = "media_url";
    public static final int    STORE_TYPE_NAME_MEDIA_URL_COL = 9;

//    public static final String STORE_TYPE_NAME_SCREEN_ID = "screen_id";
//    public static final int    STORE_TYPE_NAME_SCREEN_ID_COL = 10;

    public static final String CREATE_STORE_TYPE_NAME_TABLE =
            "CREATE TABLE " + STORE_TYPE_NAME_TABLE + " (" +
                    STORE_TYPE_NAME_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    STORE_TYPE_NAME_DELETED    + " INTEGER NOT NULL, " +
                    STORE_TYPE_NAME_UPDATED_TS      + " TEXT, " +
                    STORE_TYPE_NAME_CREATED_TS  + " TEXT, " +
                    STORE_TYPE_NAME_NAME  + " TEXT, " +
                    STORE_TYPE_NAME_STORE_ID  + " INTEGER, " +
                    STORE_TYPE_NAME_FUNCTION_ID  + " INTEGER, " +
                    STORE_TYPE_NAME_TYPE  + " INTEGER , " +
                    STORE_TYPE_NAME_SEQUENCE  + " INTEGER , " +
                    /*STORE_TYPE_NAME_MEDIA_URL + " TEXT , " +
                    STORE_TYPE_NAME_SCREEN_ID  + " INTEGER);";*/
                    STORE_TYPE_NAME_MEDIA_URL  + " TEXT);";

    public static final String DROP_STORE_TYPE_NAME_TABLE =
            "DROP TABLE IF EXISTS " + STORE_TYPE_NAME_TABLE;



    // subtitle table constants
    public static final String SUBTITLE_TABLE = "subtitle";

    public static final String SUBTITLE_SCREENSUBTITLE_ID = "screenSubtitle_id";
    public static final int    SUBTITLE_SCREENSUBTITLE_ID_COL = 0;

    public static final String SUBTITLE_DELETED = "deleted";
    public static final int    SUBTITLE_DELETED_COL = 1;

    public static final String SUBTITLE_SCREEN_ID = "screen_id";
    public static final int    SUBTITLE_SCREEN_ID_COL = 2;

    public static final String SUBTITLE_UPDATED_TS = "updated_ts";
    public static final int    SUBTITLE_UPDATED_TS_COL = 3;

    public static final String SUBTITLE_CREATED_TS = "created_ts";
    public static final int    SUBTITLE_CREATED_TS_COL = 4;

    public static final String SUBTITLE_SEQUENCE_ID = "sequence_id";
    public static final int    SUBTITLE_SEQUENCE_ID_COL = 5;

    public static final String SUBTITLE_DURATION = "duration";
    public static final int    SUBTITLE_DURATION_COL = 6;

    public static final String SUBTITLE_ID = "subtitle_id";
    public static final int    SUBTITLE_ID_COL = 7;

    public static final String SUBTITLE_DATA_DELETED = "dataDeleted";
    public static final int    SUBTITLE_DATA_DELETED_COL = 8;

    public static final String SUBTITLE_DATA_UPDATED_TS = "dataUpdated_ts";
    public static final int    SUBTITLE_DATA_UPDATED_TS_COL = 9;

    public static final String SUBTITLE_DATA_CREATED_TS = "dataCreated_ts";
    public static final int    SUBTITLE_DATA_CREATED_TS_COL = 10;

    public static final String SUBTITLE_ADMIN_ID = "admin_id";
    public static final int    SUBTITLE_ADMIN_ID_COL = 11;

    public static final String SUBTITLE_START_DATE= "start_data";
    public static final int    SUBTITLE_START_DATE_COL = 12;

    public static final String SUBTITLE_END_DATE= "end_data";
    public static final int    SUBTITLE_END_DATE_COL = 13;

    public static final String SUBTITLE_SHOW_TYPE = "show_type";
    public static final int    SUBTITLE_SHOW_TYPE_COL = 14;

    public static final String SUBTITLE_DURATION_SEC = "duration_sec";
    public static final int    SUBTITLE_DURATION_SEC_COL = 15;

    public static final String SUBTITLE_REPEAT_TIME = "repeat_time";
    public static final int    SUBTITLE_REPEAT_TIME_COL = 16;

    public static final String SUBTITLE_FONTS = "fonts";
    public static final int    SUBTITLE_FONTS_COL = 17;

    public static final String SUBTITLE_COLOR= "color";
    public static final int    SUBTITLE_COLOR_COL = 18;

    public static final String SUBTITLE_LOCATION= "location";
    public static final int    SUBTITLE_LOCATION_COL = 19;

    public static final String SUBTITLE_SPEED = "speed";
    public static final int    SUBTITLE_SPEED_COL = 20;

    public static final String SUBTITLE_TEXT_CONTENT = "text_content";
    public static final int    SUBTITLE_TEXT_CONTENT_COL = 21;

    public static final String CREATE_SUBTITLE_TABLE =
            "CREATE TABLE " + SUBTITLE_TABLE + " (" +
                    SUBTITLE_SCREENSUBTITLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SUBTITLE_DELETED + " INTEGER, " +
                    SUBTITLE_SCREEN_ID + " INTEGER, " +
                    SUBTITLE_UPDATED_TS     + " TEXT, " +
                    SUBTITLE_CREATED_TS + " TEXT, " +
                    SUBTITLE_SEQUENCE_ID + " INTEGER, " +
                    SUBTITLE_DURATION + " INTEGER, " +
                    SUBTITLE_ID        + " INTEGER, " +
                    SUBTITLE_DATA_DELETED    + " INTEGER NOT NULL, " +         //was to null
                    SUBTITLE_DATA_UPDATED_TS     + " TEXT, " +
                    SUBTITLE_DATA_CREATED_TS + " TEXT, " +
                    SUBTITLE_ADMIN_ID + " INTEGER, " +
                    SUBTITLE_START_DATE     + " TEXT, " +
                    SUBTITLE_END_DATE + " TEXT, " +
                    SUBTITLE_SHOW_TYPE + " INTEGER, " +
                    SUBTITLE_DURATION_SEC  + " INTEGER, " +
                    SUBTITLE_REPEAT_TIME + " INTEGER, " +
                    SUBTITLE_FONTS  + " INTEGER, " +
                    SUBTITLE_COLOR  + " INTEGER, " +
                    SUBTITLE_LOCATION  + " INTEGER, " +
                    SUBTITLE_SPEED  + " INTEGER, " +
                    SUBTITLE_TEXT_CONTENT  + " TEXT);";


    public static final String DROP_SUBTITLE_TABLE =
            "DROP TABLE IF EXISTS " + SUBTITLE_TABLE;



    // storeoffice table constants
    public static final String STORE_OFFICE_TABLE = "storeOffice";

    public static final String STORE_OFFICE_ID = "store_office_id";
    public static final int    STORE_OFFICE_ID_COL = 0;

    public static final String STORE_OFFICE_DELETED = "deleted";
    public static final int    STORE_OFFICE_DELETED_COL = 1;

    public static final String STORE_OFFICE_UPDATED_TS = "updated_ts";
    public static final int    STORE_OFFICE_UPDATED_TS_COL = 2;

    public static final String STORE_OFFICE_CREATED_TS= "created_ts";
    public static final int    STORE_OFFICE_CREATED_TS_COL = 3;

    public static final String STORE_OFFICE_NAME = "name";
    public static final int    STORE_OFFICE_NAME_COL = 4;

    public static final String STORE_OFFICE_STORE_ID = "store_id";
    public static final int    STORE_OFFICE_STORE_ID_COL = 5;

    public static final String STORE_OFFICE_STORE_TYPE_ID = "store_type_id";
    public static final int    STORE_OFFICE_STORE_TYPE_ID_COL = 6;

    public static final String STORE_OFFICE_SEQUENCE = "sequence";
    public static final int    STORE_OFFICE_SEQUENCE_COL = 7;

    public static final String STORE_OFFICE_TYPE = "type";
    public static final int    STORE_OFFICE_TYPE_COL = 8;

    public static final String STORE_OFFICE_RENT = "rent";
    public static final int    STORE_OFFICE_RENT_COL = 9;

    public static final String STORE_OFFICE_SIZE = "size";
    public static final int    STORE_OFFICE_SIZE_COL = 10;

    public static final String STORE_OFFICE_UNIT = "unit";
    public static final int    STORE_OFFICE_UNIT_COL = 11;

    public static final String STORE_OFFICE_EMAIL = "email";
    public static final int    STORE_OFFICE_EMAIL_COL = 12;

    public static final String STORE_OFFICE_PHONE0 = "phone0";
    public static final int    STORE_OFFICE_PHONE0_COL = 13;

    public static final String STORE_OFFICE_PHONE1 = "phone1";
    public static final int    STORE_OFFICE_PHONE1_COL = 14;

    public static final String STORE_OFFICE_PHONE2 = "phone2";
    public static final int    STORE_OFFICE_PHONE2_COL = 15;

    public static final String STORE_OFFICE_WEBSITE = "website";
    public static final int    STORE_OFFICE_WEBSITE_COL = 16;

    public static final String STORE_OFFICE_FAX = "fax";
    public static final int    STORE_OFFICE_FAX_COL = 17;

    public static final String STORE_OFFICE_MEDIA_URL = "media_url";
    public static final int    STORE_OFFICE_MEDIA_URL_COL = 18;

    public static final String STORE_OFFICE_IS_ADMINISTRATION = "is_administration";
    public static final int    STORE_OFFICE_IS_ADMINISTRATION_COL = 19;

    public static final String STORE_OFFICE_IS_RENT = "is_rent";
    public static final int    STORE_OFFICE_IS_RENT_COL = 20;

    public static final String STORE_OFFICE_IS_WC = "is_wc";
    public static final int    STORE_OFFICE_IS_WC_COL = 21;

    public static final String STORE_OFFICE_IS_SALE = "is_sale";
    public static final int    STORE_OFFICE_IS_SALE_COL = 22;

    public static final String STORE_OFFICE_SALE_PERCENT = "sale_percent";
    public static final int    STORE_OFFICE_SALE_PERCENT_COL = 23;

    public static final String STORE_OFFICE_SALE_EXPIRE = "sale_expire";
    public static final int    STORE_OFFICE_SALE_EXPIRE_COL = 24;

    public static final String STORE_OFFICE_INFO = "info";
    public static final int    STORE_OFFICE_INFO_COL = 25;

    public static final String STORE_OFFICE_SCREEN_ID = "screen_id";
    public static final int    STORE_OFFICE_SCREEN_ID_COL = 26;

    public static final String CREATE_STORE_OFFICE_TABLE =
            "CREATE TABLE " + STORE_OFFICE_TABLE + " (" +
                    STORE_OFFICE_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    STORE_OFFICE_DELETED    + " INTEGER NOT NULL, " +   //was  NOT NULL
                    STORE_OFFICE_UPDATED_TS      + " TEXT, " +
                    STORE_OFFICE_CREATED_TS  + " TEXT, " +
                    STORE_OFFICE_NAME  + " TEXT, " +
                    STORE_OFFICE_STORE_ID  + " INTEGER, " +
                    STORE_OFFICE_STORE_TYPE_ID  + " INTEGER , " +
                    STORE_OFFICE_SEQUENCE + " INTEGER , " +
                    STORE_OFFICE_TYPE  + " INTEGER , " +
                    STORE_OFFICE_RENT  + " REAL, " +
                    STORE_OFFICE_SIZE  + " REAL, " +
                    STORE_OFFICE_UNIT  + " TEXT, " +
                    STORE_OFFICE_EMAIL  + " TEXT, " +
                    STORE_OFFICE_PHONE0  + " TEXT, " +
                    STORE_OFFICE_PHONE1  + " TEXT, " +
                    STORE_OFFICE_PHONE2  + " TEXT, " +
                    STORE_OFFICE_WEBSITE  + " TEXT, " +
                    STORE_OFFICE_FAX  + " TEXT, " +
                    STORE_OFFICE_MEDIA_URL  + " TEXT, " +
                    STORE_OFFICE_IS_ADMINISTRATION + " INTEGER , " +
                    STORE_OFFICE_IS_RENT  + " INTEGER , " +
                    STORE_OFFICE_IS_WC  + " INTEGER , " +
                    STORE_OFFICE_IS_SALE  + " INTEGER , " +
                    STORE_OFFICE_SALE_PERCENT  + " INTEGER , " +
                    STORE_OFFICE_SALE_EXPIRE  + " TEXT, " +
                    STORE_OFFICE_INFO  + " TEXT, " +
                    STORE_OFFICE_SCREEN_ID  + " INTEGER);";

    public static final String DROP_STORE_OFFICE_TABLE =
            "DROP TABLE IF EXISTS " + STORE_OFFICE_TABLE;

    // storedish table constants
    public static final String DISH_TABLE = "dish";

    public static final String DISH_ID = "dish_id";
    public static final int    DISH_ID_COL = 0;

    public static final String DISH_DELETED = "deleted";
    public static final int    DISH_DELETED_COL = 1;

    public static final String DISH_UPDATED_TS = "updated_ts";
    public static final int    DISH_UPDATED_TS_COL = 2;

    public static final String DISH_CREATED_TS= "created_ts";
    public static final int    DISH_CREATED_TS_COL = 3;

    public static final String DISH_NAME = "name";
    public static final int    DISH_NAME_COL = 4;

    public static final String DISH_STORE_ID = "store_id";
    public static final int    DISH_STORE_ID_COL = 5;

    public static final String DISH_STORE_TYPE_ID = "store_type_id";
    public static final int    DISH_STORE_TYPE_ID_COL = 6;

    public static final String DISH_LOCAL_ID = "local_id";
    public static final int    DISH_LOCAL_ID_COL = 7;

    public static final String DISH_SEQUENCE = "sequence";
    public static final int    DISH_SEQUENCE_COL = 8;

    public static final String DISH_TYPE = "type";
    public static final int    DISH_TYPE_COL = 9;

    public static final String DISH_IS_SPECIALTY = "is_specialty";
    public static final int    DISH_IS_SPECIALTY_COL =10;

    public static final String DISH_IS_DEAL = "is_deal";
    public static final int    DISH_IS_DEAL_COL = 11;

    public static final String DISH_IS_NEW = "is_new";
    public static final int    DISH_IS_NEW_COL = 12;

    public static final String DISH_PRICE = "price";
    public static final int    DISH_PRICE_COL = 13;

    public static final String DISH_SALE_PERCENT = "sale_percent";
    public static final int    DISH_SALE_PERCENT_COL = 14;

    public static final String DISH_SALE_EXPIRE = "sale_expire";
    public static final int    DISH_SALE_EXPIRE_COL = 15;

    public static final String DISH_MATERIALS = "materials";
    public static final int    DISH_MATERIALS_COL = 16;

    public static final String DISH_MEDIA_URL = "media_url";
    public static final int    DISH_MEDIA_URL_COL = 17;

    public static final String DISH_NUTRITION = "nutrition";
    public static final int    DISH_NUTRITION_COL = 18;

    public static final String DISH_INFO = "info";
    public static final int    DISH_INFO_COL = 19;

    public static final String DISH_LOCAL_URL = "media_local_url";
    public static final int    DISH_LOCAL_URL_COL = 20;

    public static final String DISH_SCREEN_ID = "screen_id";
    public static final int    DISH_SCREEN_ID_COL = 21;



    // CREATE and DROP TABLE statements
    public static final String CREATE_DISH_TABLE =
            "CREATE TABLE " + DISH_TABLE + " (" +
                    DISH_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DISH_DELETED    + " INTEGER NOT NULL, " +        //was  NOT NULL
                    DISH_UPDATED_TS      + " TEXT, " +
                    DISH_CREATED_TS  + " TEXT, " +
                    DISH_NAME  + " TEXT, " +
                    DISH_STORE_ID  + " INTEGER, " +
                    DISH_STORE_TYPE_ID  + " INTEGER, " +
                    DISH_LOCAL_ID  + " TEXT, " +
                    DISH_SEQUENCE  + " INTEGER, " +
                    DISH_TYPE  + " INTEGER, " +
                    DISH_IS_SPECIALTY  + " INTEGER NOT NULL, " +    //was  NOT NULL
                    DISH_IS_DEAL  + " INTEGER NOT NULL, " +     //was  NOT NULL
                    DISH_IS_NEW  + " INTEGER NOT NULL, " +     //was  NOT NULL
                    DISH_PRICE     + " REAL, " +
                    DISH_SALE_PERCENT     + " REAL, " +
                    DISH_SALE_EXPIRE  + " TEXT, " +
                    DISH_MATERIALS  + " TEXT, " +
                    DISH_MEDIA_URL  + " TEXT, " +
                    DISH_NUTRITION  + " TEXT, " +
                    DISH_INFO  + " TEXT, " +
                    DISH_LOCAL_URL  + " TEXT, " +
                    DISH_SCREEN_ID     + " INTEGER);";

    public static final String DROP_DISH_TABLE =
            "DROP TABLE IF EXISTS " + DISH_TABLE;



    // media table constants
    public static final String MEDIA_TABLE = "media";

    public static final String MEDIA_ID = "media_id";
    public static final int   MEDIA_ID_COL = 0;

    public static final String MEDIA_DATA_DELETED = "data_deleted";
    public static final int    MEDIA_DATA_DELETED_COL = 1;

    public static final String MEDIA_DATA_UPDATED_TS = "data_updated_ts";
    public static final int    MEDIA_DATA_UPDATED_TS_COL = 2;

    public static final String MEDIA_DATA_CREATED_TS= "data_created_ts";
    public static final int    MEDIA_DATA_CREATED_TS_COL = 3;

    public static final String MEDIA_NAME = "media_name";
    public static final int    MEDIA_NAME_COL = 4;

    public static final String MEDIA_ADMIN_ID = "admin_id";
    public static final int    MEDIA_ADMIN_ID_COL = 5;

    public static final String MEDIA_USER_ID = "user_id";
    public static final int    MEDIA_USER_ID_COL = 6;

    public static final String MEDIA_USER_NAME = "user_name";
    public static final int    MEDIA_USER_NAME_COL = 7;

    public static final String MEDIA_START_DATE = "start_date";
    public static final int    MEDIA_START_DATE_COL = 8;

    public static final String MEDIA_END_DATE = "end_date";
    public static final int    MEDIA_END_DATE_COL = 9;

    public static final String MEDIA_SHOW_TYPE = "show_type";
    public static final int    MEDIA_SHOW_TYPE_COL = 10;

    public static final String MEDIA_STATUS = "status";
    public static final int    MEDIA_STATUS_COL = 11;

    public static final String MEDIA_TYPE = "media_type";
    public static final int    MEDIA_TYPE_COL = 12;

    public static final String MEDIA_ELEMENT_ID = "element_id";
    public static final int    MEDIA_ELEMENT_ID_COL = 13;

    public static final String MEDIA_URL = "media_url";
    public static final int    MEDIA_URL_COL = 14;

    public static final String MEDIA_DURATION_SEC = "duration_sec";
    public static final int    MEDIA_DURATION_SEC_COL = 15;

    public static final String MEDIA_VERSION = "version";
    public static final int    MEDIA_VERSION_COL = 16;

    public static final String MEDIA_LOCAL_URL = "local_url";
    public static final int    MEDIA_LOCAL_URL_COL = 17;

    public static final String MEDIA_SCREEN_MEDIA_ID = "screenMedia_id";
    public static final int    MEDIA_SCREEN_MEDIA_ID_COL = 18;

    public static final String MEDIA_SCREEN_ID = "screen_id";
    public static final int    MEDIA_SCREEN_ID_COL = 19;

    public static final String MEDIA_DELETED = "deleted";
    public static final int    MEDIA_DELETED_COL = 20;

    public static final String MEDIA_UPDATED_TS = "updated_ts";
    public static final int    MEDIA_UPDATED_TS_COL = 21;

    public static final String MEDIA_CREATED_TS= "created_ts";
    public static final int    MEDIA_CREATED_TS_COL = 22;

    public static final String MEDIA_SEQUENCE_ID = "sequence_id";
    public static final int   MEDIA_SEQUENCE_ID_COL = 23;

    public static final String MEDIA_DURATION = "duration";
    public static final int    MEDIA_DURATION_COL = 24;

    public static final String CREATE_MEDIA_TABLE =
            "CREATE TABLE " + MEDIA_TABLE + " (" +
                    MEDIA_ID         + " INTEGER, " +
                    MEDIA_DATA_DELETED    + " INTEGER NOT NULL, " +   //was not null
                    MEDIA_DATA_UPDATED_TS      + " TEXT, " +
                    MEDIA_DATA_CREATED_TS  + " TEXT, " +
                    MEDIA_NAME  + " TEXT, " +
                    MEDIA_ADMIN_ID  + " INTEGER, " +
                    MEDIA_USER_ID  + " INTEGER NOT NULL, " +   //was  NOT NULL
                    MEDIA_USER_NAME  + " TEXT, " +
                    MEDIA_START_DATE  + " TEXT, " +
                    MEDIA_END_DATE  + " TEXT, " +
                    MEDIA_SHOW_TYPE  + " INTEGER NOT NULL, " +    //was  NOT NULL
                    MEDIA_STATUS  + " INTEGER NOT NULL, " +       //was   NOT NULL
                    MEDIA_TYPE  + " INTEGER NOT NULL, " +        //was  NOT NULL
                    MEDIA_ELEMENT_ID  + " INTEGER, " +
                    MEDIA_URL  + " TEXT, " +
                    MEDIA_DURATION_SEC  + " INTEGER, " +
                    MEDIA_VERSION     + " REAL, "+
                    MEDIA_LOCAL_URL  + " TEXT, "+
                    MEDIA_SCREEN_MEDIA_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MEDIA_SCREEN_ID  + " INTEGER, " +
                    MEDIA_DELETED  + " INTEGER, " +
                    MEDIA_UPDATED_TS  + " TEXT, " +
                    MEDIA_CREATED_TS  + " TEXT, " +
                    MEDIA_SEQUENCE_ID  + " INTEGER, " +
                    MEDIA_DURATION  + " INTEGER);";

    public static final String DROP_MEDIA_TABLE =
            "DROP TABLE IF EXISTS " + MEDIA_TABLE;


    // store media table constants
    public static final String STORE_MEDIA_TABLE = "store_media";

    public static final String STORE_MEDIA_ID = "store_media_id";
    public static final int   STORE_MEDIA_ID_COL = 0;

    public static final String STORE_MEDIA_DATA_DELETED = "store_data_deleted";
    public static final int    STORE_MEDIA_DATA_DELETED_COL = 1;

    public static final String STORE_MEDIA_DATA_UPDATED_TS = "store_data_updated_ts";
    public static final int    STORE_MEDIA_DATA_UPDATED_TS_COL = 2;

    public static final String STORE_MEDIA_DATA_CREATED_TS= "store_data_created_ts";
    public static final int    STORE_MEDIA_DATA_CREATED_TS_COL = 3;

    public static final String STORE_MEDIA_NAME = "store_media_name";
    public static final int    STORE_MEDIA_NAME_COL = 4;

    public static final String STORE_MEDIA_ADMIN_ID = "store_admin_id";
    public static final int    STORE_MEDIA_ADMIN_ID_COL = 5;

    public static final String STORE_MEDIA_USER_ID = "store_user_id";
    public static final int    STORE_MEDIA_USER_ID_COL = 6;

    public static final String STORE_MEDIA_USER_NAME = "store_user_name";
    public static final int    STORE_MEDIA_USER_NAME_COL = 7;

    public static final String STORE_MEDIA_SHOW_TYPE = "store_show_type";
    public static final int    STORE_MEDIA_SHOW_TYPE_COL = 8;

    public static final String STORE_MEDIA_STATUS = "store_status";
    public static final int    STORE_MEDIA_STATUS_COL = 9;

    public static final String STORE_MEDIA_TYPE = "store_media_type";
    public static final int    STORE_MEDIA_TYPE_COL = 10;

    public static final String STORE_MEDIA_ELEMENT_ID = "store_element_id";
    public static final int    STORE_MEDIA_ELEMENT_ID_COL = 11;

    public static final String STORE_MEDIA_URL = "store_media_url";
    public static final int    STORE_MEDIA_URL_COL = 12;

    public static final String STORE_MEDIA_DURATION_SEC = "store_duration_sec";
    public static final int    STORE_MEDIA_DURATION_SEC_COL = 13;

    public static final String STORE_MEDIA_VERSION = "store_version";
    public static final int    STORE_MEDIA_VERSION_COL = 14;


    public static final String CREATE_STORE_MEDIA_TABLE =
            "CREATE TABLE " + STORE_MEDIA_TABLE + " (" +
                    STORE_MEDIA_ID         + " INTEGER, " +
                    STORE_MEDIA_DATA_DELETED    + " INTEGER NOT NULL, " +   //was not null
                    STORE_MEDIA_DATA_UPDATED_TS      + " TEXT, " +
                    STORE_MEDIA_DATA_CREATED_TS  + " TEXT, " +
                    STORE_MEDIA_NAME  + " TEXT, " +
                    STORE_MEDIA_ADMIN_ID  + " INTEGER, " +
                    STORE_MEDIA_USER_ID  + " INTEGER NOT NULL, " +   //was  NOT NULL
                    STORE_MEDIA_USER_NAME  + " TEXT, " +
                    STORE_MEDIA_SHOW_TYPE  + " INTEGER NOT NULL, " +    //was  NOT NULL
                    STORE_MEDIA_STATUS  + " INTEGER NOT NULL, " +       //was   NOT NULL
                    STORE_MEDIA_TYPE  + " INTEGER NOT NULL, " +        //was  NOT NULL
                    STORE_MEDIA_ELEMENT_ID  + " INTEGER, " +
                    STORE_MEDIA_URL  + " TEXT, " +
                    STORE_MEDIA_DURATION_SEC  + " INTEGER, " +
                    STORE_MEDIA_VERSION     + " REAL);";

    public static final String DROP_STORE_MEDIA_TABLE =
            "DROP TABLE IF EXISTS " + STORE_MEDIA_TABLE;

    // storebranch table constants
    public static final String BRANCH_TABLE = "branch";

    public static final String BRANCH_STORE_ID = "store_id";
    public static final int    BRANCH_STORE_ID_COL = 0;

    public static final String BRANCH_UPDATED_TS= "updated_ts";
    public static final int    BRANCH_UPDATED_TS_COL = 1;

    public static final String BRANCH_CREATED_TS= "created_ts";
    public static final int    BRANCH_CREATED_TS_COL = 2;

    public static final String BRANCH_ADMIN_ID= "admin_id";
    public static final int    BRANCH_ADMIN_ID_COL = 3;

    public static final String BRANCH_TYPE= "type";
    public static final int    BRANCH_TYPE_COL = 4;

    public static final String BRANCH_STORE_NAME= "store_name";
    public static final int    BRANCH_STORE_NAME_COL = 5;

    public static final String BRANCH_ADDRESS= "address";
    public static final int    BRANCH_ADDRESS_COL = 6;

    public static final String BRANCH_CITY= "city";
    public static final int    BRANCH_CITY_COL = 7;

    public static final String BRANCH_PROVINCE= "province";
    public static final int    BRANCH_PROVINCE_COL = 8;

    public static final String BRANCH_POSTCODE= "postcode";
    public static final int    BRANCH_POSTCODE_COL = 9;

    public static final String BRANCH_EMAIL= "email";
    public static final int    BRANCH_EMAIL_COL = 10;

    public static final String BRANCH_PHONE0= "phone0";
    public static final int    BRANCH_PHONE0_COL = 11;

    public static final String BRANCH_PHONE1= "phone1";
    public static final int    BRANCH_PHONE1_COL = 12;

    public static final String BRANCH_PHONE2= "phone2";
    public static final int    BRANCH_PHONE2_COL = 13;

    public static final String BRANCH_WEBSITE= "website";
    public static final int    BRANCH_WEBSITE_COL = 14;

    public static final String BRANCH_MEDIA_URL= "media_url";
    public static final int    BRANCH_MEDIA_URL_COL = 15;

    public static final String BRANCH_BACKGROUND_COLOR= "background_color";
    public static final int    BRANCH_BACKGROUND_COLOR_COL = 16;

    public static final String BRANCH_FONT_COLOR= "font_color";
    public static final int    BRANCH_FONT_COLOR_COL = 17;

    public static final String BRANCH_FONT_FAMILY_NAME= "font_family_name";
    public static final int    BRANCH_FONT_FAMILY_NAME_COL = 18;

    public static final String BRANCH_FONT_NAME= "font_name";
    public static final int    BRANCH_FONT_NAME_COL = 19;

    public static final String BRANCH_FONT_HEAD_SIZE= "font_head_size";
    public static final int    BRANCH_FONT_HEAD_SIZE_COL = 20;

    public static final String BRANCH_FONT_TEXT_SIZE= "font_text_size";
    public static final int    BRANCH_FONT_TEXT_SIZE_COL = 21;

    public static final String BRANCH_LOGO_URL= "logo_url";
    public static final int    BRANCH_LOGO_URL_COL = 22;

    public static final String BRANCH_INFO= "info";
    public static final int    BRANCH_INFO_COL = 23;

    public static final String BRANCH_KEY_ONE_NAME = "key_one_name";
    public static final int    BRANCH_KEY_ONE_NAME_COL = 24;

    public static final String BRANCH_KEY_TWO_NAME = "key_two_name";
    public static final int    BRANCH_KEY_TWO_NAME_COL = 25;

    public static final String BRANCH_KEY_THREE_NAME = "key_three_name";
    public static final int    BRANCH_KEY_THREE_NAME_COL = 26;

    public static final String BRANCH_INFO_ONE_NAME = "info_one_name";
    public static final int    BRANCH_INFO_ONE_NAME_COL = 27;

    public static final String BRANCH_INFO_TWO_NAME = "info_two_name";
    public static final int    BRANCH_INFO_TWO_NAME_COL = 28;

    public static final String BRANCH_INFO_THREE_NAME = "info_three_name";
    public static final int    BRANCH_INFO_THREE_NAME_COL = 29;

    /*public static final String BRANCH_SCREEN_ID = "screen_id";
    public static final int    BRANCH_SCREEN_ID_COL = 30;*/

    // CREATE and DROP TABLE statements
    public static final String CREATE_BRANCH_TABLE =
            "CREATE TABLE " + BRANCH_TABLE + " (" +
                    BRANCH_STORE_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BRANCH_UPDATED_TS    + " TEXT, " +
                    BRANCH_CREATED_TS    + " TEXT, " +
                    BRANCH_ADMIN_ID      + " INTEGER, " +
                    BRANCH_TYPE  + " INTEGER, " +
                    BRANCH_STORE_NAME  + " TEXT, " +
                    BRANCH_ADDRESS  + " TEXT, " +
                    BRANCH_CITY  + " TEXT, " +
                    BRANCH_PROVINCE  + " TEXT, " +
                    BRANCH_POSTCODE  + " TEXT, " +
                    BRANCH_EMAIL  + " TEXT, " +
                    BRANCH_PHONE0  + " TEXT, " +
                    BRANCH_PHONE1  + " TEXT, " +
                    BRANCH_PHONE2  + " TEXT, " +
                    BRANCH_WEBSITE     + " TEXT, " +
                    BRANCH_MEDIA_URL     + " TEXT, " +
                    BRANCH_BACKGROUND_COLOR     + " TEXT, " +
                    BRANCH_FONT_COLOR     + " TEXT, " +
                    BRANCH_FONT_FAMILY_NAME     + " TEXT, " +
                    BRANCH_FONT_NAME     + " TEXT, " +
                    BRANCH_FONT_HEAD_SIZE     + " INTEGER, " +
                    BRANCH_FONT_TEXT_SIZE     + " INTEGER, " +
                    BRANCH_LOGO_URL     + " TEXT, " +
                    BRANCH_KEY_ONE_NAME  + " TEXT," +
                    BRANCH_KEY_TWO_NAME  + " TEXT," +
                    BRANCH_KEY_THREE_NAME  + " TEXT," +
                    BRANCH_INFO_ONE_NAME  + " TEXT," +
                    BRANCH_INFO_TWO_NAME  + " TEXT," +
                    BRANCH_INFO_THREE_NAME  + " TEXT," +
                    /*BRANCH_INFO + " TEXT," +
                    BRANCH_SCREEN_ID  + " INTEGER);";*/
                    BRANCH_INFO  + " TEXT);";

    public static final String DROP_BRANCH_TABLE =
            "DROP TABLE IF EXISTS " + BRANCH_TABLE;



    // storedish table constants
    public static final String CARHOUSE_TABLE = "carhouse";

    public static final String CARHOUSE_ID = "storeItem_id";
    public static final int    CARHOUSE_ID_COL = 0;

    public static final String CARHOUSE_DELETED = "deleted";
    public static final int    CARHOUSE_DELETED_COL = 1;

    public static final String CARHOUSE_UPDATED_TS = "updated_ts";
    public static final int    CARHOUSE_UPDATED_TS_COL = 2;

    public static final String CARHOUSE_CREATED_TS= "created_ts";
    public static final int    CARHOUSE_CREATED_TS_COL = 3;

    public static final String CARHOUSE_NAME = "name";
    public static final int    CARHOUSE_NAME_COL = 4;

    public static final String CARHOUSE_STORE_ID = "store_id";
    public static final int    CARHOUSE_STORE_ID_COL = 5;

    public static final String CARHOUSE_STORE_TYPE_ID = "show_type_id";
    public static final int    CARHOUSE_STORE_TYPE_ID_COL = 6;

    public static final String CARHOUSE_LOCAL_ID = "local_id";
    public static final int    CARHOUSE_LOCAL_ID_COL = 7;

    public static final String CARHOUSE_SEQUENCE = "sequence";
    public static final int    CARHOUSE_SEQUENCE_COL = 8;

    public static final String CARHOUSE_TYPE = "type";
    public static final int    CARHOUSE_TYPE_COL = 9;

    public static final String CARHOUSE_IS_KEY_ONE = "is_key_one";
    public static final int    CARHOUSE_IS_KEY_ONE_COL =10;

    public static final String CARHOUSE_IS_KEY_TWO = "is_key_two";
    public static final int    CARHOUSE_IS_KEY_TWO_COL = 11;

    public static final String CARHOUSE_IS_KEY_THREE = "is_key_three";
    public static final int    CARHOUSE_IS_KEY_THREE_COL = 12;

    public static final String CARHOUSE_PRICE = "price";
    public static final int    CARHOUSE_PRICE_COL = 13;

    public static final String CARHOUSE_SALE_PERCENT = "sale_percent";
    public static final int    CARHOUSE_SALE_PERCENT_COL = 14;

    public static final String CARHOUSE_SALE_EXPIRE = "sale_expire";
    public static final int    CARHOUSE_SALE_EXPIRE_COL = 15;

    public static final String CARHOUSE_INFO_ONE = "info_one";
    public static final int    CARHOUSE_INFO_ONE_COL = 16;

    public static final String CARHOUSE_INFO_TWO = "info_two";
    public static final int    CARHOUSE_INFO_TWO_COL = 17;

    public static final String CARHOUSE_INFO_THREE = "info_three";
    public static final int    CARHOUSE_INFO_THREE_COL = 18;

    public static final String CARHOUSE_MEDIA_URL = "media_url";
    public static final int    CARHOUSE_MEDIA_URL_COL = 19;

    public static final String CARHOUSE_SCREEN_ID = "screen_id";
    public static final int    CARHOUSE_SCREEN_ID_COL = 20;



    // CREATE and DROP TABLE statements
    public static final String CREATE_CARHOUSE_TABLE =
            "CREATE TABLE " + CARHOUSE_TABLE + " (" +
                    CARHOUSE_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CARHOUSE_DELETED    + " INTEGER NOT NULL, " +    //was not null
                    CARHOUSE_UPDATED_TS      + " TEXT, " +
                    CARHOUSE_CREATED_TS  + " TEXT, " +
                    CARHOUSE_NAME  + " TEXT, " +
                    CARHOUSE_STORE_ID  + " INTEGER, " +
                    CARHOUSE_STORE_TYPE_ID  + " INTEGER, " +
                    CARHOUSE_LOCAL_ID  + " TEXT, " +
                    CARHOUSE_SEQUENCE  + " INTEGER, " +
                    CARHOUSE_TYPE  + " INTEGER, " +
                    CARHOUSE_IS_KEY_ONE  + " INTEGER NOT NULL, " +    //was not null
                    CARHOUSE_IS_KEY_TWO  + " INTEGER NOT NULL, " +      //was  NOT NULL
                    CARHOUSE_IS_KEY_THREE  + " INTEGER NOT NULL, " +      //was  NOT NULL
                    CARHOUSE_PRICE     + " REAL, " +
                    CARHOUSE_SALE_PERCENT     + " REAL, " +
                    CARHOUSE_SALE_EXPIRE  + " TEXT, " +
                    CARHOUSE_INFO_ONE  + " TEXT, " +
                    CARHOUSE_INFO_TWO  + " TEXT, " +
                    CARHOUSE_INFO_THREE  + " TEXT, " +
                    CARHOUSE_MEDIA_URL  + " TEXT, " +
                    CARHOUSE_SCREEN_ID  + " INTEGER);";

    public static final String DROP_CARHOUSE_TABLE =
            "DROP TABLE IF EXISTS " + CARHOUSE_TABLE;
}
