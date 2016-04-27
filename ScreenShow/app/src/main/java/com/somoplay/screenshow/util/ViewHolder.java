package com.somoplay.screenshow.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.somoplay.screenshow.app.Constants;

/**
 * Created by Shaohua on 8/24/2015.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position){
        mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public View getConvertView() {
        return mConvertView;
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position){
        if(convertView == null){
            return new ViewHolder(context, parent, layoutId, position);
        }
        else{
            ViewHolder holder = (ViewHolder)convertView.getTag();
            return holder;
        }
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);

        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T)view;
    }
}
