package com.somoplay.screenshow.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.somoplay.screenshow.R;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.StoreTypeName;
import com.somoplay.screenshow.util.ViewHolder;

import java.util.List;

/**
 * Created by Shaohua on 8/24/2015.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    private static AppController mAppController=AppController.getInstance();

    static ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mAppController)
            .memoryCacheSize(41943040)
            .diskCacheSize(104857600)
            .threadPoolSize(10)
            .build();
    static DisplayImageOptions imgDisplayOptions = new DisplayImageOptions.Builder()
            .cacheInMemory()
            .cacheOnDisk(true)
            .build();
    static ImageLoader imageLoader=ImageLoader.getInstance();

    protected Context context;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;

    public CommonAdapter(Context context, List<T> data, int layoutId){
        this.context = context;
        imageLoader.init(config);
        mDatas = data;
        this.layoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = ViewHolder.get(context, convertView, parent, layoutId, position);

        convert(holder, getItem(position));

        return holder.getConvertView();
    }



    public abstract void convert(ViewHolder holder, T t);



    public void setTextStyle(TextView tv, int fontSize){
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        String backGroundColor = sharedPref.getString(Constants.KEY_BACKGROUND_COLOR, "#ffffff");
        String fontColor = sharedPref.getString(Constants.KEY_FONT_COLOR, "#ffffff");
        String storeName = sharedPref.getString(Constants.KEY_STORE_NAME, "normal");
        int fontHeadSize = sharedPref.getInt(Constants.KEY_FONT_HEAD_SIZE, 30);
//        int fontTextSize = sharedPref.getInt(Constants.KEY_FONT_TEXT_SIZE, 12);
        /*if (fontColor == ""){
            fontColor = "#ffffff";
        }else if(fontColor.charAt(0) != '#'){
            fontColor = "#" + fontColor;
        }*/

        //tv.setTextColor(Color.parseColor(fontColor));
        tv.setTextSize((float) fontSize);
        Typeface typeFace=Typeface.create(storeName, Typeface.NORMAL);
        if(typeFace!=null){
            tv.setTypeface(typeFace);
        }
    }
}
