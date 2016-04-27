package com.somoplay.screenshow.adapter;

import android.content.Context;
import android.widget.TextView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.model.ChooseScreen;
import com.somoplay.screenshow.util.ViewHolder;

import java.util.List;
/**
 * Created by JudyShuai on 15-08-25.
 */
public class ChooseScreenAdapter extends CommonAdapter<ChooseScreen> {
    public ChooseScreenAdapter(Context context, List<ChooseScreen> data, int layoutId){
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ChooseScreen chooseScreen) {
        setTextStyle((TextView)holder.getView(R.id.screeName),15);
        setTextStyle((TextView) holder.getView(R.id.storeName), 15);
        ((TextView)holder.getView(R.id.screeName)).setText("Screen Name: " +chooseScreen.getScreenName());
        int showContentType = chooseScreen.getShowContentType();
        String show = showContentType+"";
        if(showContentType == 1){
            show = "Advertisement";
        }else if(showContentType == 2){
            show = "Photos-ON";
        }else if(showContentType == 3){
            show = "Photos-OFF";
        }
        else if(showContentType == 6){
            show = "General Store";
        }
        ((TextView)holder.getView(R.id.storeName)).setText("Screen Type:   " +show);
    }
}
