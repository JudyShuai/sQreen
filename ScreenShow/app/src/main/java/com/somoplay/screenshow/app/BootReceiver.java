package com.somoplay.screenshow.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.somoplay.screenshow.activity.LoginActivity;
import com.somoplay.screenshow.activity.StartLoadingActivity;
import com.somoplay.screenshow.service.MediaListRequestService;

/**
 * Created by yaolu on 15-06-03.
 */

public class BootReceiver extends BroadcastReceiver {




    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d("News reader", "Boot completed");
//luogangtestchange
        // start service


//        intent = new Intent (StartLoadingActivity.this, LoginActivity.class);
//        startActivity(intent);
//
//        Intent service = new Intent(context, MediaListRequestService.class);

       // if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {

            Intent myStarterIntent = new Intent(context, StartLoadingActivity.class);
            myStarterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myStarterIntent);

       // }

    }
}