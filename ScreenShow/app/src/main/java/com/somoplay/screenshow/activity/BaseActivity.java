package com.somoplay.screenshow.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Shaohua Mao on 2015-07-27.
 */
abstract public class BaseActivity extends Activity {

    public final int TIME_UP = 1;
    public ScreenProtectorHandler mTimeHandler;
    public Message msg;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    abstract public void refreshActivityContent();
    abstract public void changeCurrentActivity();
    abstract public void goToPhotos();

    public class ScreenProtectorHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case TIME_UP:
                    /*Intent intent = new Intent(CarAndHouseActivity.this, MediaShowActivity.class);
                    intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
                    intent.putExtra(Constants.KEY_IS_STORE, true);     //当10秒到达后，作相应的操作。
                    startActivity(intent);*/
                    goToPhotos();

                    Log.i("Timer", "Time's up, go to photos activity");
                    break;
            }
        }
    }

    public void resetTime(){
        if (mTimeHandler != null){
            mTimeHandler.removeMessages(TIME_UP);
            msg = mTimeHandler.obtainMessage(TIME_UP);
            mTimeHandler.sendMessageDelayed(msg , 5*60*1000);   //5 min
        }else{
            mTimeHandler = new ScreenProtectorHandler();
            mTimeHandler.removeMessages(TIME_UP);
            msg = mTimeHandler.obtainMessage(TIME_UP);
            mTimeHandler.sendMessageDelayed(msg , 5*60*1000);
        }
        Log.d("Timer", "Timer is reset");
    }
}
