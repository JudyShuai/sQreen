package com.somoplay.screenshow.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.Useless.SessionManager;
import com.somoplay.screenshow.Useless.SettingActivity;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.webrequest.LoginRequest;


/**
 * Created by JudyShuai on 15-06-18.
 */
public class LoginActivity extends Activity {

    protected EditText mEmail;
    protected EditText mPassword;
    protected Button mLoginButton;
    protected Button mGoToShowExample;
    protected Button mAbout;
    /*protected Button mGoToStoreShowButton;
    protected Button mGoToMediaShowButton;
    protected Button mGoToDownloadDataBuuton;*/
    public LoginRequest loginRequest ;
    private String getStatus;
    private String backgroundRgbColor,textRgbColor;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    private RelativeLayout relativeLayout;
    protected TextView mSignUpTextView,titleText;
    private Button  mSettingTextView;
    SessionManager session;
    private ImageView icon;

    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = this.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("Activity_Target", "Activity_Login");
        editor.commit();


        Log.d(TAG, "someone called log in acticity...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginRequest=new LoginRequest(this);

        mEmail = (EditText) findViewById(R.id.usernameField);
        mPassword = (EditText) findViewById(R.id.firstNameField);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mGoToShowExample=(Button) findViewById(R.id.goToShowExample);
        mSettingTextView = (Button) findViewById(R.id.settingText);
        mAbout = (Button) findViewById(R.id.about);
 /*       mGoToMediaShowButton=(Button)findViewById(R.id.goToMediaShow_button);
        mGoToDownloadDataBuuton=(Button)findViewById(R.id.goToDownloadDataButton);*/
        titleText=(TextView)findViewById(R.id.item_text);

      /*  icon = (ImageView) findViewById(R.id.item_image);
        String filePath = Constants.DEVICE_PATH_LOGO + "/logo.png";

        FileInputStream is = null;
        try{
            is = new FileInputStream(filePath);
        }catch (IOException e){
            e.printStackTrace();
        }

        if(is == null){
            icon.setImageResource(R.drawable.logo);
        }else{
            icon.setImageURI(Uri.parse(filePath));
        }
*/
        //********************** 判断横竖屏 ********************************//

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            Log.i("info", "landscape"); // 横屏

        }

        else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            Log.i("info", "portrait"); // 竖屏

        }

        //********************** 判断横竖屏 ********************************//
        //change layour background
        pref = this.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        backgroundRgbColor = pref.getString(Constants.KEY_RGBCOLOR_BACKGROUND, null);
        textRgbColor = pref.getString(Constants.KEY_RGBCOLOR_TEXT, null);

        if(backgroundRgbColor!=null){
            relativeLayout=(RelativeLayout)findViewById(R.id.loginLayOutBackground);
            relativeLayout.setBackgroundColor(Color.parseColor(this.backgroundRgbColor));

        }

        if(textRgbColor!=null){
            mEmail.setTextColor(Color.parseColor(this.textRgbColor));
            mPassword.setTextColor(Color.parseColor(this.textRgbColor));
            mLoginButton.setTextColor(Color.parseColor(this.textRgbColor));
            mGoToShowExample.setTextColor(Color.parseColor(this.textRgbColor));
            mAbout.setTextColor(Color.parseColor(this.textRgbColor));
            mSettingTextView.setTextColor(Color.parseColor(this.textRgbColor));
           /* mGoToMediaShowButton.setTextColor(Color.parseColor(this.textRgbColor));
            mGoToDownloadDataBuuton.setTextColor(Color.parseColor(this.textRgbColor));*/
            titleText.setTextColor(Color.parseColor(this.textRgbColor));

        }


        mSettingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
//


        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        mGoToShowExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(LoginActivity.this, ShowExampleActivity.class);
                //startActivity(intent);
                
                String username = "PaulW";
                String password = "123456";

                loginRequest.sendStringRequest(username, password);
            }
        });

       /* mGoToMediaShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MediaShowActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mGoToDownloadDataBuuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, DownloadActivity.class);
                startActivity(intent);

            }
        });*/

        session = new SessionManager(getApplicationContext());
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            //Intent intent = new Intent(LoginActivity.this, StoreShowActivity.class);
            Intent intent = new Intent(LoginActivity.this, new_StoreShowActivity.class);
            startActivity(intent);
            finish();
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mEmail.getText().toString();
                String password = mPassword.getText().toString();

//                String getAdminId = loginRequest.getId();
//                username = username.trim();
//                password = password.trim();



                if (username.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Please make sure you enter a username and password.")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
//                    // Login.


                    loginRequest.sendStringRequest(username, password);




                    // Starting MainActivity


//                    if (loginRequest.getLogin_status().equals("success")) {
//                        Toast.makeText(getApplicationContext(),
//                                "Login successfully! ", Toast.LENGTH_LONG)
//                                .show();
////                            Intent intent = new Intent(getApplicationContext(), StoreShowActivity.class);
////                            startActivity(intent);
//                    } else {
//                        Toast.makeText(getApplicationContext(),
//                                "Login failed! ", Toast.LENGTH_LONG)
//                                .show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "Sorry!You are not an admin! ", Toast.LENGTH_LONG)
//                            .show();
//                }
//                    finish();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void runThread() {

        new Thread() {
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Login Failed!")
                                    .setTitle("Login Failed!")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void afterGetStatus(String status)
    {
        getStatus=status;

        if(getStatus.equals("sucess")){
           // Toast.makeText(getApplicationContext(),
           //                     "Login successfully! ", Toast.LENGTH_LONG)
            //                    .show();
            Intent intent = new Intent(LoginActivity.this, ChooseScreenActivity.class);
            startActivity(intent);

//            int i=0;
        }
        else {
            runThread();

//                    Toast.makeText(getApplicationContext(),
//                            "Login failed! ", Toast.LENGTH_LONG)
//                            .show();

        }
    }

}
