package com.somoplay.screenshow.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.customizedview.LYMarqueeViewThree;
import com.somoplay.screenshow.database.MediaDB;
import com.somoplay.screenshow.database.StoreMediaDB;
import com.somoplay.screenshow.database.SubtitleDB;
import com.somoplay.screenshow.model.Media;
import com.somoplay.screenshow.model.StoreMedia;
import com.somoplay.screenshow.model.Subtitle;
import com.somoplay.screenshow.service.MediaListRequestService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MediaShowActivity extends BaseActivity  implements MediaPlayer.OnCompletionListener
{
    private static final String TAG = LYMarqueeViewThree.class.getSimpleName();   //data structure
    public boolean isAdvertisement;
    public boolean isStore;
    public boolean isUserPhoto;

    public CarAndHouseActivity carAndHouseActivity;

    private int subPos = 0;
    private int positionOfMedia = 0;

    private String imageDevicePath = "";
    public  String TARGET_BASE_PATH ;
    private String testText1;
    private String testText2;
    private String testText3;
    //private String testText4;

    private ArrayList<Subtitle> subtitlesToDisplay = new ArrayList();
    private ArrayList<HashMap<String, String>> imageNamesArray = new ArrayList<>();

    //ALL the UI
    private VideoView videoView;

    private LYMarqueeViewThree subtitleView;
    private TextView marqueeTextView;

    private LYMarqueeViewThree subtitleView1;
    private TextView marqueeTextView1;

    private LYMarqueeViewThree subtitleView2;
    private TextView marqueeTextView2;

    private LYMarqueeViewThree subtitleView3;
    private TextView marqueeTextView3;

//    private LYMarqueeViewThree mv;
//    private TextView textView1;

    private ImageView mediaImageView;

    //database
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private SubtitleDB subtitleDB = new SubtitleDB(this);
    private MediaDB    mediaDB    = new MediaDB(this);
    private StoreMediaDB storeMediaDB = new StoreMediaDB(this);

    //controller
    private  MediaController mediaController;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediashow);

        Intent service = new Intent(MediaShowActivity.this, MediaListRequestService.class);
        startService(service);

        AppController.getInstance().setGeneralActivity(this);

        carAndHouseActivity = new CarAndHouseActivity();
        initVariables();
        initText();
        initVideoAndImage();
        refreshActivityContent();
        displayMedia();
        if(!subtitlesToDisplay.isEmpty()) {
            startShowSubtitle();
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_MEDIA_SHOW_ACTIVITY);
        editor.commit();
    }

    @Override
    public void onPause(){
        Log.d(TAG, "this will shown when maedia show acticity is paused");
        BaseActivity baseActivity = AppController.getInstance().getGeneralActivity();
        if(baseActivity != null){
            baseActivity = null;
        }
        super.onPause();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
        editor.commit();
    }
/*
    @Override
    public void onStop(){
        super.onStop();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
        editor.commit();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_MEDIA_SHOW_ACTIVITY);
        editor.commit();
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //touch screen to go back:
                /*commonShowActivity.stopTimer();
                commonShowActivity.startTimer();
                Log.d("Stop Timer", "Timer reset  ");*/
                /*finish();
                Log.d("Timer", "go back from Photos Activity");*/
                break;

            case MotionEvent.ACTION_MOVE:

                break;
            
            case MotionEvent.ACTION_UP:

                SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
                String activityEnterMethod = prefs.getString("Activity_Enter_Method", null);

                if(activityEnterMethod !=null && activityEnterMethod.equals("Automatic"))
                {
                    Intent intent = new Intent (MediaShowActivity.this, LoginActivity.class);
                    startActivity(intent);


                }

                finish();
                Log.d("Timer", "go back from Photos Activity");
                break;

            default:
                return false;
        }
        return true;
    }

    @Override
    public void goToPhotos() {

    }

    private void initVariables(){
        isAdvertisement = getIntent().getBooleanExtra(Constants.KEY_IS_ADVERTISEMENT, false);
        isStore = getIntent().getBooleanExtra(Constants.KEY_IS_STORE, false);
        isUserPhoto = getIntent().getBooleanExtra(Constants.KEY_IS_USERPHOTO, false);

        sharedPref = getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        editor = sharedPref.edit();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_MEDIA_SHOW_ACTIVITY);
        editor.putString("Activity_Target", "Activity_Show");
        editor.commit();

        TARGET_BASE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
    }

    private void initText(){
        subtitleView1 = (LYMarqueeViewThree) findViewById(R.id.subtitleView1);
        subtitleView1.setPauseBetweenAnimations(1);
        subtitleView1.setSpeed(1);
        subtitleView1.mediaShowActivity = this;
        subtitleView1.isOneOrThree =3;
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                subtitleView1.startMarquee();
            }
        });

        marqueeTextView1 = (TextView) findViewById(R.id.textView1);
        marqueeTextView1.setTextSize(30);
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ///textView1.setText(randomStrings[new Random().nextInt(randomStrings.length)]);
//                marqueeTextView1.setText("");
//                subtitleView1.startMarquee();
//            }
//        }, 1000);


        subtitleView2 = (LYMarqueeViewThree) findViewById(R.id.subtitleView2);
        subtitleView2.setPauseBetweenAnimations(1);
        subtitleView2.setSpeed(1);
        subtitleView2.mediaShowActivity = this;
        subtitleView2.isOneOrThree =3;
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                subtitleView2.startMarquee();
            }
        });

        marqueeTextView2 = (TextView) findViewById(R.id.textView2);
        marqueeTextView2.setTextSize(30);
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ///textView1.setText(randomStrings[new Random().nextInt(randomStrings.length)]);
//                marqueeTextView2.setText("");
//                subtitleView2.startMarquee();
//            }
//        }, 1000);




        subtitleView3 = (LYMarqueeViewThree) findViewById(R.id.subtitleView3);
        subtitleView3.setPauseBetweenAnimations(500);
        subtitleView3.setSpeed(1);
        subtitleView3.mediaShowActivity = this;
        subtitleView3.isOneOrThree =3;
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                subtitleView3.startMarquee();
            }
        });

        marqueeTextView3 = (TextView) findViewById(R.id.textView3);
        marqueeTextView3.setTextSize(30);
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ///textView1.setText(randomStrings[new Random().nextInt(randomStrings.length)]);
//                marqueeTextView3.setText("");
//                subtitleView3.startMarquee();
//            }
//        }, 1000);



//        mv = (LYMarqueeViewThree) findViewById(R.id.marqueeView4);
//        mv.setPauseBetweenAnimations(1);
//        mv.setSpeed(1);
//
//        mv.isOneOrThree =1;
//        mv.mediaShowActivity = this;
//        getWindow().getDecorView().post(new Runnable() {
//            @Override
//            public void run() {
//                mv.startMarquee();
//            }
//        });
//
//        textView1 = (TextView) findViewById(R.id.textView4);
//        textView1.setTextSize(30);
////        getWindow().getDecorView().postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                ///textView1.setText(randomStrings[new Random().nextInt(randomStrings.length)]);
////                textView1.setText("beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae suntbo. Nemo enim  ipsam voluptatembeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae suntbo. Nemo enim  ipsam voluptatembeatae");
////                mv.startMarquee();
////            }
////        }, 1000);


        //subtitleView=(MarqueeView)findViewById(R.id.subtitleView1);
        //marqueeTextView=(TextView)findViewById(R.id.textView1);
    }

//    public void showNewSubtitle(){
//        if (subtitlesToDisplay.size()==0)
//        {
//            getWindow().getDecorView().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    //textView1.setText(testText1);
//                    //textView1.setText("22bbeatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae eatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptate");
//                    mv.startMarquee();
//                }
//            }, 10);
//        }
//
//        if (subPos >= subtitlesToDisplay.size()) {
//            subPos = 0;
//        }
//
//
//
//
//
//        if (subtitlesToDisplay.size()>0)
//        {
//            testText4  = subtitlesToDisplay.get(subPos).getTextContent()+"                                                                                                                                                                                                                                    ";
//            //testText1  =subtitlesToDisplay.get(pos).getTextContent();
//            getWindow().getDecorView().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    textView1.setText(testText4);
//                    //textView1.setText("22bbeatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae eatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptate");
//
//                    //        textView1.setTextSize(subtitlesToDisplay.get(subPos).getFonts());
//
//                  //  mv.setRepeat(subtitlesToDisplay.get(subPos).getRepeatTime());
//                   // mv.setSpeed(subtitlesToDisplay.get(subPos).getSpeed());
//                    mv.setSpeed(1);
//        //           textView1.setTextSize(25);
//                   // String color = subtitlesToDisplay.get(subPos).getColor();
//                    //textView1.setTextColor(Color.parseColor(color));
//                    mv.startMarquee();
//                }
//            }, 0);
//
//            subPos++;
//        }
//
//    }



    private void initVideoAndImage(){
        videoView = (VideoView) findViewById(R.id.videoWindow);
        videoView.setVisibility(View.VISIBLE);
        videoView.setMediaController(mediaController);
        videoView.setOnCompletionListener(this);
        mediaController = new MediaController(MediaShowActivity.this);
        mediaController.setMediaPlayer(videoView);

        mediaImageView = (ImageView) findViewById(R.id.imageviewActressPhoto);
        mediaImageView.setVisibility(View.GONE);
    }


    @Override
    public void refreshActivityContent()
    {
        Log.d(TAG, "someone call refresh activity content");
        ArrayList<HashMap<String, String>> newimageNamesArray = new ArrayList<>();
        ArrayList<Media> data = new ArrayList<>();
        ArrayList<StoreMedia> data2 = new ArrayList<>();

        if(isAdvertisement){
            int screenId = sharedPref.getInt(Constants.KEY_SCREEN_ID, 0);
            data = mediaDB.getAdvertisementMedias(screenId);
        }else if(!isAdvertisement && isStore){
            int storeId = sharedPref.getInt(Constants.KEY_STORE_ID, 1);
            data2 = storeMediaDB.getStoreMediaByMediaTypeAndElementId(3, storeId);
            //data = mediaDB.getMediaByMediaTypeAndElementId(3, storeId);
        } else if(!isAdvertisement && isUserPhoto){
            data = mediaDB.getUserDrawingPhotos();
        }

        // sorting of images include in data which is media from advertisment and user photo, not store media
        int j = 0;
        boolean isTempChanged = false;

        ArrayList<Media> sortedDateBySequence = new ArrayList<>();
        for(int i = 0; i < data.size(); i++){
            int temp = data.get(0).getSequenceId();

            for(j = 0; j < data.size(); j++){
                if(temp > data.get(j).getSequenceId()){
                    temp = data.get(j).getSequenceId();
                    isTempChanged = true;
                }
            }

            if(isTempChanged == true){
                sortedDateBySequence.add(data.get(j-1));
                data.remove(j - 1);
                isTempChanged = false;
            }
            else {
                sortedDateBySequence.add(data.get(0));
                data.remove(0);
            }

            i--;
        }

        for(int i=0 ; i < sortedDateBySequence.size(); i++)
        {
            Media media = sortedDateBySequence.get(i);
            HashMap<String, String> hashMapForMediaType = new HashMap<>();
            hashMapForMediaType.put("url",media.getMediaUrl() );
            hashMapForMediaType.put("type", String.valueOf(media.getMediaType()) );
            hashMapForMediaType.put("duration_sec", String.valueOf(media.getDurationSec()) );//defined by media
            hashMapForMediaType.put("duration", String.valueOf(media.getDuration()) );//defined by media screen one
            newimageNamesArray.add(hashMapForMediaType);
        }

        for(int i=0; i<data2.size(); i++){
            StoreMedia media = data2.get(i);
            HashMap<String, String> hashMapForMediaType = new HashMap<>();
            hashMapForMediaType.put("url",media.getMediaUrl() );
            hashMapForMediaType.put("type", String.valueOf(media.getMediaType()) );
            hashMapForMediaType.put("duration_sec", String.valueOf(media.getDurationSec()) );//defined by media
            hashMapForMediaType.put("duration", String.valueOf(-1));//defined by self cause no duration in database of store media
            newimageNamesArray.add(hashMapForMediaType);
        }

        updateImages(newimageNamesArray);//updateImage data and show


        if(isAdvertisement){
            int screenId = sharedPref.getInt(Constants.KEY_SCREEN_ID, 0);
            ArrayList<Subtitle> subtitleData = subtitleDB.getSubtitles(screenId);
            if (subtitleData.size()>0) {
                this.updateTexts(subtitleData);//GET subtitles data and show the subtitles
            }
        }
    }

    //1) update subtitle data
    public void updateTexts(ArrayList<Subtitle> subtitles ){
        int oldSize = subtitlesToDisplay.size();
        int newSize = subtitles.size();

        for(int i= 0 ; i<newSize; i++)
        {
            subtitlesToDisplay.add(subtitles.get(i));
        }

        for(int i= 0 ; i<oldSize; i++)
        {
            if(i< subtitlesToDisplay.size()) {
                subtitlesToDisplay.remove(0);
            }else {
                Log.d("Index out error", "i is out subtitlesToDisplay size");
            }
        }
        //startShowSubtitle();
    }

    //2 change index
    public void startShowSubtitle()
    {
        if (subPos >= subtitlesToDisplay.size()) {
            subPos = 0;
        }
        displaySubtitle(subPos);
        subPos++;
    }

    //3 show subtitle
    protected void displaySubtitle(int pos){
        Log.d(TAG, "someone called display subtitle");
        int location  =subtitlesToDisplay.get(pos).getLocation();
        int Speed = 12 - (subtitlesToDisplay.get(pos).getSpeed() * 3);
        if (location==3) {
//            marqueeTextView1.setVisibility(View.VISIBLE);
//            subtitleView1.setVisibility(View.VISIBLE);
//            marqueeTextView2.setVisibility(View.GONE);
//            subtitleView2.setVisibility(View.GONE);
//            marqueeTextView3.setVisibility(View.GONE);
//            subtitleView3.setVisibility(View.GONE);

//            marqueeTextView1.setSelected(true);
//            marqueeTextView1.setTextSize(subtitlesToDisplay.get(pos).getFonts());
            subtitleView1.setRepeat(subtitlesToDisplay.get(pos).getRepeatTime());
//            subtitleView1.setSpeed(subtitlesToDisplay.get(pos).getSpeed());
            subtitleView1.setSpeed(Speed);
//            subtitleView2.setRepeat(1);
//            marqueeTextView1.setTextSize(25);
            String color = subtitlesToDisplay.get(pos).getColor();
            marqueeTextView1.setTextColor(Color.parseColor(color));
            testText1  = subtitlesToDisplay.get(pos).getTextContent()+"                                                                                                                                                                                                                                    ";
            //testText1  =subtitlesToDisplay.get(pos).getTextContent();
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "pass subtitle view 1 to LYM");
                    marqueeTextView1.setText(testText1);
                    //subtitleView1.startMarquee();
                }
            }, 1);
        }
        else if(location==2)
        {
//            marqueeTextView2.setVisibility(View.VISIBLE);
//            subtitleView2.setVisibility(View.VISIBLE);
//            marqueeTextView1.setVisibility(View.GONE);
//            subtitleView1.setVisibility(View.GONE);
//            marqueeTextView3.setVisibility(View.GONE);
//            subtitleView3.setVisibility(View.GONE);

//            marqueeTextView2.setSelected(true);
//            marqueeTextView2.setTextSize(subtitlesToDisplay.get(pos).getFonts());
            subtitleView2.setRepeat(subtitlesToDisplay.get(pos).getRepeatTime());
//            subtitleView2.setSpeed(subtitlesToDisplay.get(pos).getSpeed());
            subtitleView2.setSpeed(Speed);
//            subtitleView2.setRepeat(1);
//            marqueeTextView2.setTextSize(25);
            String color = subtitlesToDisplay.get(pos).getColor();
            marqueeTextView2.setTextColor(Color.parseColor(color));
            testText2  = subtitlesToDisplay.get(pos).getTextContent() +"                                                                                                                                                                                                                                    ";
            //int lenght = testText2.length();
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "pass subtitle view 2 to LYM");
                    marqueeTextView2.setText(testText2);
                    //subtitleView2.startMarquee();
                }
            }, 1);
        }
        else// (location==3)
        {
//            marqueeTextView3.setVisibility(View.VISIBLE);
//            subtitleView3.setVisibility(View.VISIBLE);
//            marqueeTextView1.setVisibility(View.GONE);
//            subtitleView1.setVisibility(View.GONE);
//            marqueeTextView2.setVisibility(View.GONE);
//            subtitleView2.setVisibility(View.GONE);

//            marqueeTextView3.setSelected(true);
//            marqueeTextView3.setTextSize(subtitlesToDisplay.get(pos).getFonts());
            subtitleView3.setRepeat(subtitlesToDisplay.get(pos).getRepeatTime());
//            subtitleView3.setSpeed(subtitlesToDisplay.get(pos).getSpeed());
            subtitleView3.setSpeed(Speed);
//            subtitleView3.setRepeat(1);
//            marqueeTextView2.setTextSize(25);
            String color = subtitlesToDisplay.get(pos).getColor();
            marqueeTextView3.setTextColor(Color.parseColor(color));
            testText3  = subtitlesToDisplay.get(pos).getTextContent() +"                                                                                                                                                                                                                                    ";
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "pass subtitle view 3 to LYM");
                    marqueeTextView3.setText(testText3);
                    //subtitleView3.startMarquee();
                }
            }, 1);
        }
    }

    // 1) first  update images data
    public void updateImages(ArrayList<HashMap<String, String>> newimageNamesArray)
    {
        int oldSize = imageNamesArray.size();

        for(int i=0; i<newimageNamesArray.size(); i++)
        {
            HashMap<String, String> imageName = newimageNamesArray.get(i);
            imageNamesArray.add(imageName);
        }

        for(int i=0; i<oldSize; i++)
        {
            imageNamesArray.remove(0);
        }


        //displayMedia();// start to show image
    }


    class DisplayMediaAsync extends AsyncTask<Void, Void, Void> {
        private int duration;
        DisplayMediaAsync(int duration) {
            super();
            this.duration = duration;

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000 * duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            displayMedia();
        }
    }

    // 2) first  show images
    protected void displayMedia()
    {
        Log.d(TAG,"someone call display media");
        if(imageNamesArray.size()>0)
        {
            if(positionOfMedia > imageNamesArray.size()-1)
            {
                positionOfMedia = 0;
            }

            if (Integer.parseInt(imageNamesArray.get(positionOfMedia).get("type")) == 1)
            {
                videoView.setVisibility(View.VISIBLE);
                mediaImageView.setVisibility(View.GONE);

                String videoName = imageNamesArray.get(positionOfMedia).get("url");
                playVideo(videoName);
            }
            else
            {
                videoView.setVisibility(View.GONE);
                mediaImageView.setVisibility(View.VISIBLE);

                if(isAdvertisement){
                    imageDevicePath = Constants.DEVICE_PATH_ADS;
                }
                else if(isStore) {
                    imageDevicePath = Constants.DEVICE_PATH_STORE;
                }
                else if(isUserPhoto){
                    imageDevicePath = Constants.DEVICE_PATH_PHOTOSHOW;
                }

                showImageFromInternalMemory(imageDevicePath, imageNamesArray.get(positionOfMedia).get("url"));
                int duration = Integer.parseInt(imageNamesArray.get(positionOfMedia).get("duration")) ;//defined by media screen one

                if(duration<=0){
                    duration = Integer.parseInt(imageNamesArray.get(positionOfMedia).get("duration_sec")) ;//defined by media
                }

                if(duration<=0) {
                    duration=5;
                }


                DisplayMediaAsync displayMediaAsync = new DisplayMediaAsync(duration);
                displayMediaAsync.execute();

               /* final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //displayMedia();
                        DisplayMediaAsync displayMediaAsync = new DisplayMediaAsync();
                        displayMediaAsync.execute();
                    }
                }, 1000 * duration); // duration of drawing images show and subtitle*/
            }

            positionOfMedia ++;
        }
    }

    // 3) play video
    private void playVideo(String videoName){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),videoName);
        if (file.exists())
        {
            videoView.setVideoPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/"+ videoName);
            videoView.requestFocus();
            videoView.start();
            mediaController.show();
        }
    }

    //3.1  delegate for play video
    @Override
    public void onCompletion(MediaPlayer mp) {
        displayMedia();
        /*DisplayMediaAsync displayMediaAsync = new DisplayMediaAsync();
        displayMediaAsync.execute();*/

    }

    // 4) find images call 5)
    public void showImageFromInternalMemory (String path, String imageName)
    {
        try {
            String filePath = path + "/"+ imageName;
            //mediaImageView.setImageBitmap(readBitmapAutoSize(filePath, 1000, 1000));
            mediaImageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    // 5) read image call 6)
    public static Bitmap readBitmapAutoSize(String filePath, int outWidth, int outHeight) {
        //outWidth和outHeight是目标图片的最大宽度和高度，用作限制
        FileInputStream fs = null;
        BufferedInputStream bs = null;
        try {
            fs = new FileInputStream(filePath);
            bs = new BufferedInputStream(fs);
            BitmapFactory.Options options = setBitmapOption(filePath, outWidth, outHeight);
            return BitmapFactory.decodeStream(bs, null, options);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bs.close();
                fs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //6 获取图片信息
    private static BitmapFactory.Options setBitmapOption(String file, int width, int height) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        //设置只是解码图片的边距，此操作目的是度量图片的实际宽度和高度
        BitmapFactory.decodeFile(file, opt);

        int outWidth = opt.outWidth; //获得图片的实际高和宽
        int outHeight = opt.outHeight;
        opt.inDither = false;
        opt.inPreferredConfig = Bitmap.Config.RGB_565; //设置加载图片的颜色数为16bit，默认是RGB_8888，表示24bit颜色和透明通道，但一般用不上
        opt.inSampleSize = 1;
        //设置缩放比,1表示原比例，2表示原来的四分之一....
        //计算缩放比
        if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
            int sampleSize = Math.max(outWidth / width,  outHeight / height);
            //int sampleSize = (outWidth / width + outHeight / height) / 2;
            opt.inSampleSize = sampleSize;
        }

        opt.inJustDecodeBounds = false;//最后把标志复原
        return opt;
    }

    @Override
    public void changeCurrentActivity(){
        int showContentType = sharedPref.getInt(Constants.KEY_SHOW_CONTENT_TYPE, 0);

        if(showContentType == 1){
            Intent intent = new Intent(this, MediaShowActivity.class);
            intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, true);
            startActivity(intent);
        }

        else if(showContentType == 2){
            Intent intent = new Intent(this, MediaShowActivity.class);
            intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
            intent.putExtra(Constants.KEY_IS_USERPHOTO, true);
            startActivity(intent);
        }

        else if(showContentType == 4){
            //Intent intent = new Intent(this, StoreShowActivity.class);
            Intent intent = new Intent(this, new_StoreShowActivity.class);
            startActivity(intent);
        }

        else if(showContentType == 5){
            Intent intent = new Intent(this, OfficeShowActivity.class);
            startActivity(intent);
        }

        else if(showContentType == 6){
            Intent intent = new Intent(this, CarAndHouseActivity.class);
            startActivity(intent);
        }
    }


}
