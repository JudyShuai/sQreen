package com.somoplay.screenshow.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.somoplay.screenshow.service.MediaListRequestService;

/**
 * Created by yaolu on 15-06-03.
 */


//Amrit branch 123
public class ConnectivityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("News reader", "Connectivity changed");

//        ConnectivityManager connectivityManager = (ConnectivityManager)
//                context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo =
//                connectivityManager.getActiveNetworkInfo();
//
//        Intent service = new Intent(context, MediaListRequestService.class);
//
//        if (networkInfo != null && networkInfo.isConnected()){
//            Log.d("News reader", "Connected");
//            context.startService(service);
//        }
//        else {
//            Log.d("News reader", "NOT connected");
//            context.stopService(service);
//        }
    }
}