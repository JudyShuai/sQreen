package com.somoplay.screenshow.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;

/**
 * Created by ann on 2015-12-10.
 */
public class AboutActivity extends BaseActivity {

    AppController appController = AppController.getInstance();
    protected String fontColor;
    protected SharedPreferences sharedPref;
    protected SharedPreferences.Editor editor;
    WebView myWebView;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        appController.setGeneralActivity(this);

        sharedPref = getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        fontColor = sharedPref.getString(Constants.KEY_FONT_COLOR, "#000000");

        if (fontColor.equals("")){
            fontColor = "#000000";
        }else if(fontColor.charAt(0) != '#'){
            fontColor = "#" + fontColor;
        }

        tv = (TextView) findViewById(R.id.about_page);
        tv.setText("This app is created by Somoplay Inc..");
        tv.setTextSize(30);

        myWebView = (WebView) findViewById(R.id.website);
        myWebView.loadUrl("http://www.somoplay.com");

        myWebView.getSettings().setJavaScriptEnabled(true);//设置使用够执行JS脚本
        myWebView.getSettings().setBuiltInZoomControls(true);//设置使支持缩放
//      webView.getSettings().setDefaultFontSize(5);

        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);// 使用当前WebView处理跳转
                return true;//true表示此事件在此处被处理，不需要再广播
            }
            @Override   //转向错误时的处理
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                Toast.makeText(AboutActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void refreshActivityContent() {

    }

    @Override
    public void changeCurrentActivity() {

    }

    @Override
    public void goToPhotos() {

    }

    @Override   //默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
