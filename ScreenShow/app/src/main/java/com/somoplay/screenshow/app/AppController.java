package com.somoplay.screenshow.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.somoplay.screenshow.activity.BaseActivity;
import com.somoplay.screenshow.activity.MediaShowActivity;
import com.somoplay.screenshow.Useless.StoreShowActivity;
import com.somoplay.screenshow.activity.new_StoreShowActivity;
import com.somoplay.screenshow.util.LruBitmapCache;

public class AppController extends Application {

    // after upgrade by Judy
//android:theme="@android:style/Theme.Holo.NoActionBar.Fullscreen"
	public static final String TAG = AppController.class.getSimpleName();
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private static AppController mInstance;
	private StoreShowActivity storeShowActivity;
	private new_StoreShowActivity mNewStoreShowActivity;
	private MediaShowActivity mediaShowActivity;
	public BaseActivity generalActivity;
	SharedPreferences sharedPref;

	//for service
    private long feedMillis = -1;
    public void setFeedMillis(long feedMillis) {
        this.feedMillis = feedMillis;
    }
    public long getFeedMillis() {
        return feedMillis;
    }
	public String TARGET_BASE_PATH ;
	//Initialize the appcontroller
	@Override
	public void onCreate() {
		sharedPref = this.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
		String valueOfFirstRun = sharedPref.getString(Constants.firstRun,null);
		if(valueOfFirstRun == null){
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(Constants.KEY_BRANCH_UPDATE_TS, "2000-02-17 16:34:22.0");
			editor.putString(Constants.KEY_STORE_TYPE_NAME_UPDATE_TS, "2000-02-18 22:57:44.0");
			editor.putString(Constants.KEY_OFFICE_TYPE_NAME_UPDATE_TS, "2000-02-17 20:57:44.0");
			editor.putString(Constants.KEY_DISH_UPDATE_TS, "2000-06-17 23:57:44.0");
			editor.putString(Constants.KEY_OFFICE_UPDATE_TS, "2000-02-17 22:57:44.0");
			editor.putString(Constants.KEY_STORE_MEDIAS_UPDATE_TS, "2000-02-17 23:27:00.0");
			editor.putString(Constants.KEY_OFFICE_MEDIAS_UPDATE_TS, "2000-02-17 23:26:55.0");
			editor.putString(Constants.KEY_SUBTITLES_UPDATE_TS, "2000-02-17 23:29:38.0");
			editor.putString(Constants.KEY_MEDIALIST_UPDATE_TS, "2000-11-27 12:12:12.0");


			editor.putString(Constants.firstRun, "it is not the first run");
			editor.commit();

		}else{
			// not first running
			//??????

		}

		super.onCreate();
		mInstance = this;
		TARGET_BASE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";




		/*Intent service = new Intent(this, MediaListRequestService.class);
		startService(service);*/
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}


	//get and set methods
	public StoreShowActivity getStoreShowActivity() {
		return storeShowActivity;
	}

	public new_StoreShowActivity getNewStoreShowActivity(){
		return mNewStoreShowActivity;
	}

	public void setStoreShowActivity(StoreShowActivity storeShowActivity) {
		this.storeShowActivity = storeShowActivity;
	}

	public void setNewStoreShowActivity(new_StoreShowActivity newStoreShowActivity){
		this.mNewStoreShowActivity = newStoreShowActivity;
	}

	public MediaShowActivity getMediaShowActivity() {
		return mediaShowActivity;
	}

	public void setMediaShowActivity(MediaShowActivity mediaShowActivity) {
		this.mediaShowActivity = mediaShowActivity;
	}


	//Volley methods
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}


	public BaseActivity getGeneralActivity() {
		return generalActivity;
	}

	public void setGeneralActivity(BaseActivity generalActivity) {
		this.generalActivity = generalActivity;
	}
}