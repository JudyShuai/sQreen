package com.somoplay.screenshow.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.TextView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.model.StoreTypeName;
import com.somoplay.screenshow.util.ViewHolder;

import java.util.List;

/**
 * Created by JudyShuai on 15-08-25.
 */
public class StoreTypeAdapter extends CommonAdapter<StoreTypeName> {

    public StoreTypeAdapter(Context context, List<StoreTypeName> data, int layoutId){
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, StoreTypeName storeTypeName) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        String fontColor = sharedPref.getString(Constants.KEY_FONT_COLOR, "#ffffff");
        if (fontColor == ""){
            fontColor = "#ffffff";
        }else if(fontColor.charAt(0) != '#'){
            fontColor = "#" + fontColor;
        }
        ((TextView)holder.getView(R.id.store_type_name)).setTextColor(Color.parseColor(fontColor));
        setTextStyle((TextView) holder.getView(R.id.store_type_name), 16);
        ((TextView)holder.getView(R.id.store_type_name)).setText(storeTypeName.getName());

    }
}
