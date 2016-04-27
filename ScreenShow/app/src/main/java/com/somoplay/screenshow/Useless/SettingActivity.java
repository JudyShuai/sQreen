package com.somoplay.screenshow.Useless;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.activity.LoginActivity;
import com.somoplay.screenshow.app.Constants;

public class SettingActivity extends Activity implements View.OnClickListener{
    private SeekBar brushSeekbar, redSeekbar, greenSeekbar, blueSeekbar;
    private TextView brushSizeNumber, redNumber, greenNumber, blueNumber;
    private ImageButton confirmSetting;
    private int size = 20, red, green, blue;
    private String rgbColor, redTemp, greenTemp, blueTemp, tempRGB, convertColor;
    private ImageView circleView;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    private LoginActivity loginActivity;
    private boolean IS_BACK_FROM_FRAGMENT = true,IS_BACKGROUND_BTN=false,IS_TEXT_BTN;
    private Button backgroudColorButton,textColorButton,saveBackgroundColorBtn,saveTextColorBtn;
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getRgbColor() {
        return rgbColor;
    }

    public void setRgbColor(String rgbColor) {
        this.rgbColor = rgbColor;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        redSeekbar = (SeekBar) findViewById(R.id.redBar);
        greenSeekbar = (SeekBar) findViewById(R.id.greenBar);
        blueSeekbar = (SeekBar) findViewById(R.id.blueBar);

        redNumber = (TextView) findViewById(R.id.redValue);
        greenNumber = (TextView) findViewById(R.id.greenValue);
        blueNumber = (TextView) findViewById(R.id.blueValue);

        circleView = (ImageView) findViewById(R.id.circle);

        backgroudColorButton=(Button)findViewById(R.id.backgroud_color_btn);
        backgroudColorButton.setTextColor(Color.parseColor("#ffffff"));
        saveBackgroundColorBtn=(Button)findViewById(R.id.save_backgroud_color_btn);
        saveTextColorBtn=(Button)findViewById(R.id.save_text_color_btn);
        textColorButton=(Button)findViewById(R.id.text_color_btn);
        textColorButton.setTextColor(Color.parseColor("#000000"));

        confirmSetting = (ImageButton) findViewById(R.id.okButton);
        confirmSetting.setOnClickListener(this);

        // convert color into int
        if(rgbColor!=null){
            convertColor = rgbColor.replace("#", "");

            // separate the red color from rgbcolor
            tempRGB = Character.toString(convertColor.charAt(0)) +
                    Character.toString(convertColor.charAt(1));
            red = Integer.parseInt(tempRGB, 16);

            // separate the green color from rgbcolor
            tempRGB = Character.toString(convertColor.charAt(2)) +
                    Character.toString(convertColor.charAt(3));
            green = Integer.parseInt(tempRGB, 16);

            // separate the blue color from rgbcolor
            tempRGB = Character.toString(convertColor.charAt(4)) +
                    Character.toString(convertColor.charAt(5));
            blue = Integer.parseInt(tempRGB, 16);
        }


        // set the brush seekbar
 /*       brushSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                size = progressValue;
                circleView.getLayoutParams().width = size * 2;
                circleView.getLayoutParams().height = size * 2;
                circleView.requestLayout();
                brushSizeNumber.setText(Integer.toString(size));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                brushSizeNumber.setText(Integer.toString(size));
            }
        });
*/
        // set the red seekbar
        redSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                red = progressValue;
                redNumber.setText(Integer.toString(red));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                redNumber.setText(Integer.toString(red));
                redSeekbar.setProgress(red);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                redNumber.setText(Integer.toString(red));
                convertColor();
            }
        });

        // set the green seekbar
        greenSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                green = progressValue;
                greenNumber.setText(Integer.toString(green));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                greenNumber.setText(Integer.toString(green));
                greenSeekbar.setProgress(green);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                greenNumber.setText(Integer.toString(green));
                convertColor();
            }
        });

        // set the blue seekbar
        blueSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                blue = progressValue;
                blueNumber.setText(Integer.toString(blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                blueNumber.setText(Integer.toString(blue));
                blueSeekbar.setProgress(blue);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                blueNumber.setText(Integer.toString(blue));
                convertColor();
            }
        });
        red = redSeekbar.getProgress();
        green = greenSeekbar.getProgress();
        blue = blueSeekbar.getProgress();
        backgroudColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IS_BACKGROUND_BTN = true;
                backgroudColorButton.setTextColor(Color.parseColor("#ffffff"));
                textColorButton.setTextColor(Color.parseColor("#000000"));
                redSeekbar.setProgress(0);
                greenSeekbar.setProgress(0);
                blueSeekbar.setProgress(0);


            }
        });
        saveBackgroundColorBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                convertColor();
                //saveBackgroundColorBtn.setBackgroundColor(Color.parseColor("#0B2161"));
                SharedPreferences backgroudColorPrefs = SettingActivity.this.getSharedPreferences(
                        Constants.PREF_NAME, Context.MODE_PRIVATE);
                editor = backgroudColorPrefs.edit();
                editor.putString(Constants.KEY_RGBCOLOR_BACKGROUND, rgbColor);
                editor.commit();
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //更改为按下时的背景图片
                    view.setBackgroundColor(Color.parseColor("#0B2161"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this, AlertDialog.THEME_HOLO_LIGHT);
                    builder.setMessage("Background Color has changed!")
                            .setPositiveButton("OK", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //改为抬起时的图片
                    view.setBackgroundColor(Color.parseColor("#395691"));
                }

                return false;
            }
        });




        saveTextColorBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                convertColor();
                SharedPreferences textColorPrefs = SettingActivity.this.getSharedPreferences(
                        Constants.PREF_NAME, Context.MODE_PRIVATE);
                editor = textColorPrefs.edit();
                editor.putString(Constants.KEY_RGBCOLOR_TEXT, rgbColor);
                editor.commit();
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //更改为按下时的背景图片
                    view.setBackgroundColor(Color.parseColor("#0B2161"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this, AlertDialog.THEME_HOLO_LIGHT);
                    builder.setMessage("Text Color has changed!")
                            .setPositiveButton("OK", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //改为抬起时的图片
                    view.setBackgroundColor(Color.parseColor("#395691"));
                }
                return false;

            }
        });

        textColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textColorButton.setTextColor(Color.parseColor("#ffffff"));
                backgroudColorButton.setTextColor(Color.parseColor("#000000"));
                redSeekbar.setProgress(0);
                greenSeekbar.setProgress(0);
                blueSeekbar.setProgress(0);


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_setting, menu);

        return true;
    }

    @Override
    public void onPause() {

        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

       loginActivity = new LoginActivity();



    }

    // ok button clicked
    @Override
    public void onClick(View view) {
      if (view.getId()==R.id.okButton) {


          Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
          startActivity(intent);
          finish();
      }


    }

    // combine the rgbcolor into color code
    public void convertColor() {
        if (red < 10 || green < 10 || blue < 10) {
            if (red<10) {
                redTemp = "0" + Integer.toHexString(red);
                greenTemp = Integer.toHexString(green);
                blueTemp = Integer.toHexString(blue);
                if (green==0) {
                    greenTemp = "0" + Integer.toHexString(green);
                }
                if (blue==0) {
                    blueTemp = "0" + Integer.toHexString(blue);
                }
                rgbColor = "#" + redTemp + greenTemp + blueTemp;
            }
            if (green<10) {
                greenTemp = "0" + Integer.toHexString(green);
                redTemp = Integer.toHexString(red);
                blueTemp = Integer.toHexString(blue);
                if (red==0) {
                    redTemp = "0" + Integer.toHexString(red);
                }
                if (blue==0) {
                    blueTemp = "0" + Integer.toHexString(blue);
                }
                rgbColor = "#" + redTemp + greenTemp + blueTemp;
            }
            if (blue<10) {
                blueTemp = "0" + Integer.toHexString(blue);
                redTemp = Integer.toHexString(red);
                greenTemp = Integer.toHexString(green);
                if (red==0) {
                    redTemp = "0" + Integer.toHexString(red);
                }
                if (green==0) {
                    greenTemp = "0" + Integer.toHexString(green);
                }
                rgbColor = "#" + redTemp + greenTemp + blueTemp;
            }
        } else {
            rgbColor = "#" + Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue);
        }
        circleView.getBackground().setColorFilter(Color.parseColor(rgbColor), PorterDuff.Mode.SRC);
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
}
