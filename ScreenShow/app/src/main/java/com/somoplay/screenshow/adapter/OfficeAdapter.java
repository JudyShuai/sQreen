package com.somoplay.screenshow.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.StoreOffice;
import com.somoplay.screenshow.util.ViewHolder;

import java.util.List;

/**
 * Created by JudyShuai on 15-08-26.
 */
public class OfficeAdapter extends CommonAdapter<StoreOffice> {
    public OfficeAdapter(Context context, List<StoreOffice> data, int layoutId){
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, StoreOffice storeOffice) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        String fontColor = sharedPref.getString(Constants.KEY_FONT_COLOR, "#ffffff");
        if (fontColor == ""){
            fontColor = "#ffffff";
        }else if(fontColor.charAt(0) != '#'){
            fontColor = "#" + fontColor;
        }
        ((TextView)holder.getView(R.id.store_item_name)).setTextColor(Color.parseColor(fontColor));
        String filePath = new String("file:///" + Constants.DEVICE_PATH_OFFICE + "/" + storeOffice.getMediaUrl());
        setTextStyle((TextView) holder.getView(R.id.store_item_name),16);

        ((TextView)holder.getView(R.id.store_item_name)).setText(storeOffice.getName());
        imageLoader.displayImage(filePath, (ImageView) holder.getView(R.id.store_item_image), imgDisplayOptions);
    }
}
