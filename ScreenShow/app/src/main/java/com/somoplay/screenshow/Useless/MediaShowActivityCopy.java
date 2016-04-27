package com.somoplay.screenshow.Useless;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.activity.BaseActivity;
import com.somoplay.screenshow.activity.MediaShowActivity;
import com.somoplay.screenshow.activity.OfficeShowActivity;
import com.somoplay.screenshow.activity.new_StoreShowActivity;
import com.somoplay.screenshow.app.AppController;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.customizedview.LYMarqueeViewThree;
import com.somoplay.screenshow.database.MediaDB;
import com.somoplay.screenshow.database.SubtitleDB;
import com.somoplay.screenshow.model.Media;
import com.somoplay.screenshow.model.Subtitle;
import com.somoplay.screenshow.videodownload.FileDownloadHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MediaShowActivityCopy extends BaseActivity implements MediaPlayer.OnCompletionListener
{
    public boolean isAdvertisement;
    public boolean isStore;
    public boolean isBuilding;
    public boolean isUserPhoto;
    private Handler handler;
    private VideoView videoView;
    private LYMarqueeViewThree subtitleView;
    private TextView marqueeTextView;

    private LYMarqueeViewThree subtitleView1;
    private TextView marqueeTextView1;

    private LYMarqueeViewThree subtitleView2;
    private TextView marqueeTextView2;

    private LYMarqueeViewThree subtitleView3;
    private TextView marqueeTextView3;


    private ImageView mediaImageView;
    private ArrayList<Subtitle> subtitlesToDisplay = new ArrayList();
    private AppController appController;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private SubtitleDB subtitleDB = new SubtitleDB(this);
    private MediaDB mediaDB = new MediaDB(this);

    private int subPos = 0;
    private int repeat = 0;
    private final static int SWITCH_TEXT_CONTET=1;
    private final static int SWITCH_IMAGE_CONTET=2;
    private int positionOfMedia = 0;
    private int videoFlag = 0;

    private ArrayList<HashMap<String, String>> imageNamesArray = new ArrayList<>();
    private boolean firstLoad = true;

    private String imageDevicePath = "";
    private static final String videoDevicePath = "/data/data/com.somoplay.screenshow/app_vedioDir";
    private static final int interval1 = 1000;   //Wait 5 second to go to next image (no use)
    private static final int interval2 = 1000;
    private int moviePlayingIndex;

    public String TARGET_BASE_PATH ;

    private FileDownloadHelper fileDownloadHelper = new FileDownloadHelper();
    private  MediaController mediaController;

    private  TextView textView1;
    private LYMarqueeViewThree mv;

    private String testText1;
    private String testText2;
    private String testText3;
    private String testText4;

    public void showNewSubtitle(){
        if (subtitlesToDisplay.size()==0)
        {
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //textView1.setText(testText1);
                    //textView1.setText("22bbeatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae eatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptate");
                    mv.startMarquee();
                }
            }, 10);
        }

        if (subPos >= subtitlesToDisplay.size()) {
            subPos = 0;
        }





        if (subtitlesToDisplay.size()>0)
        {
            testText4  = subtitlesToDisplay.get(subPos).getTextContent()+"                                                                                                                                                                                                                                    ";
            //testText1  =subtitlesToDisplay.get(pos).getTextContent();
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView1.setText(testText4);
                    //textView1.setText("22bbeatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae eatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptate");

                    //        textView1.setTextSize(subtitlesToDisplay.get(subPos).getFonts());

                    //  mv.setRepeat(subtitlesToDisplay.get(subPos).getRepeatTime());
                    // mv.setSpeed(subtitlesToDisplay.get(subPos).getSpeed());
                    mv.setSpeed(1);
                    //           textView1.setTextSize(25);
                    // String color = subtitlesToDisplay.get(subPos).getColor();
                    //textView1.setTextColor(Color.parseColor(color));
                    mv.startMarquee();
                }
            }, 0);

            subPos++;
        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediashow);
        AppController.getInstance().setGeneralActivity(this);


        initText();

        mv = (LYMarqueeViewThree) findViewById(R.id.marqueeView4);
        mv.setPauseBetweenAnimations(1);
        mv.setSpeed(1);

        mv.isOneOrThree =1;
        //mv.mediaShowActivity = this;
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mv.startMarquee();
            }
        });

        textView1 = (TextView) findViewById(R.id.textView4);
        textView1.setTextSize(30);
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ///textView1.setText(randomStrings[new Random().nextInt(randomStrings.length)]);
//                textView1.setText("beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae suntbo. Nemo enim  ipsam voluptatembeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae suntbo. Nemo enim  ipsam voluptatembeatae");
//                mv.startMarquee();
//            }
//        }, 1000);

        isAdvertisement = getIntent().getBooleanExtra(Constants.KEY_IS_ADVERTISEMENT, true);
        isStore = getIntent().getBooleanExtra(Constants.KEY_IS_STORE, false);
        isUserPhoto = getIntent().getBooleanExtra(Constants.KEY_IS_USERPHOTO, false);

        sharedPref = getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        editor = sharedPref.edit();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_MEDIA_SHOW_ACTIVITY);
        editor.commit();

        TARGET_BASE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        appController = AppController.getInstance();

        videoView = (VideoView) findViewById(R.id.videoWindow);
        videoView.setVisibility(View.VISIBLE);
        videoView.setMediaController(mediaController);
        videoView.setOnCompletionListener(this);
        //mediaController = new MediaController(MediaShowActivity.this);
        mediaController.setMediaPlayer(videoView);

        //subtitleView=(MarqueeView)findViewById(R.id.subtitleView1);
        //marqueeTextView=(TextView)findViewById(R.id.textView1);

        mediaImageView = (ImageView) findViewById(R.id.imageviewActressPhoto);
        mediaImageView.setVisibility(View.GONE);

        refreshActivityContent();
        //displayMedia();

      /*  if(isAdvertisement){
            startHandler();
        }*/


        //loadStaticVideo();
        // initPlayingData();
        // playVideo("video1.mp4");

    }

    @Override
    public void goToPhotos() {

    }

    private void initText(){
        subtitleView1 = (LYMarqueeViewThree) findViewById(R.id.subtitleView1);
        subtitleView1.setPauseBetweenAnimations(1);
        subtitleView1.setSpeed(1);
        //subtitleView1.mediaShowActivity = this;
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
        //subtitleView2.mediaShowActivity = this;
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
        //subtitleView3.mediaShowActivity = this;
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


    }



    public void updateTexts(ArrayList<Subtitle> subtitles ){


//        if(firstLoad) {
//            switch (subtitles.get(0).getLocation()) {
//                case 1:
//                    subtitleView = (LYMarqueeViewThree) findViewById(R.id.subtitleView1);
//                    marqueeTextView = (TextView) findViewById(R.id.textView1);
//                    break;
//                case 2:
//                    subtitleView = (LYMarqueeViewThree) findViewById(R.id.subtitleView2);
//                    marqueeTextView = (TextView) findViewById(R.id.textView2);
//                    break;
//                case 3:
//                    subtitleView = (LYMarqueeViewThree) findViewById(R.id.subtitleView3);
//                    marqueeTextView = (TextView) findViewById(R.id.textView3);
//                    break;
//                default:
//                    subtitleView = (LYMarqueeViewThree) findViewById(R.id.subtitleView2);
//                    marqueeTextView = (TextView) findViewById(R.id.textView2);
//            }
//        }
//        subtitleView.mediaShowActivity = this;
//        firstLoad = false;

        int oldsize = subtitlesToDisplay.size();
        int newsize = subtitles.size();

        for(int i= 0 ; i<newsize; i++)
        {
            subtitlesToDisplay.add(subtitles.get(i));
        }

        for(int i= 0 ; i<oldsize; i++)
        {
            subtitlesToDisplay.remove(i);
        }

        // showNewSubtitle();
        startShowSubtitle();
    }

    public void startShowSubtitle()
    {
        if (subPos >= subtitlesToDisplay.size()) {
            subPos = 0;
        }
        displaySubtitle(subPos);
        //String text =subtitlesToDisplay.get(subPos).getTextContent();


        subPos++;
        /*
        int length = text.length();
        int duration = length*1000/10;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startShowSubtitle();
            }
        }, duration);
        */
    }


//    private void startHandler()//old method
//    {
//        //TO repeat show different image or video
//        //handler.post(runnableMedia);
//
//        handler=new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
////                if (msg.what == SWITCH_IMAGE_CONTET){
////                    displayMedia();
////                }
////                else
//                if (msg.what == SWITCH_TEXT_CONTET)
//                {
//                    if (subPos < subtitlesToDisplay.size()) {
//                        displaySubtitle(subPos);
//                        subPos++;
//                    }
//                }
//            }};
//
//
//        ScheduledThreadPoolExecutor executor= new ScheduledThreadPoolExecutor(1);
//        //executor.scheduleWithFixedDelay(runnableMedia,0,interval1, TimeUnit.MILLISECONDS);
//        executor.scheduleWithFixedDelay(runnableSubtitle,0,interval2, TimeUnit.MILLISECONDS);
//
//        subtitleView.setIsFinished(true);
//    }
//
//    private Runnable runnableSubtitle = new Runnable() {
//        @Override
//        public void run() {
//
//            if (subPos < subtitlesToDisplay.size()) {
//                if(subtitleView.getIsFinished()) {
//                    subtitleView.setIsFinished(false);
//                    System.out.println("#$%^&@@@@@@@@@ SWITCH_TEXT_CONTET  subPos= " +
//                            subtitlesToDisplay.get(subPos).getTextContent());
//                    handler.sendEmptyMessage(SWITCH_TEXT_CONTET);
//                }
//            }else {
//                subPos = 0;
//            }
//        }
//    };

    protected void displaySubtitle(int pos){

//        if (subPos >= subtitlesToDisplay.size()) {
//            subPos = 0;
//        }

        //int location  = subtitlesToDisplay.get(subPos).getLocation();
        int location  =subtitlesToDisplay.get(pos).getLocation();
        //location=2;
        if (location==1)
        {
//            marqueeTextView1.setVisibility(View.VISIBLE);
//            subtitleView1.setVisibility(View.VISIBLE);
//            marqueeTextView2.setVisibility(View.GONE);
//            subtitleView2.setVisibility(View.GONE);
//            marqueeTextView3.setVisibility(View.GONE);
//            subtitleView3.setVisibility(View.GONE);

//            marqueeTextView1.setSelected(true);
//            marqueeTextView1.setTextSize(subtitlesToDisplay.get(pos).getFonts());
            subtitleView1.setRepeat(subtitlesToDisplay.get(pos).getRepeatTime());
            subtitleView1.setSpeed(subtitlesToDisplay.get(pos).getSpeed());
//            subtitleView1.setSpeed(10);
//            subtitleView2.setRepeat(1);
//            marqueeTextView1.setTextSize(25);
            String color = subtitlesToDisplay.get(pos).getColor();
            marqueeTextView1.setTextColor(Color.parseColor(color));
            testText1  = subtitlesToDisplay.get(pos).getTextContent();//+"                                                                                                                                                                                                                                    ";
            //testText1  =subtitlesToDisplay.get(pos).getTextContent();
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    marqueeTextView1.setText(testText1);
                    subtitleView1.startMarquee();
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
            subtitleView2.setSpeed(subtitlesToDisplay.get(pos).getSpeed());
//            subtitleView2.setSpeed(10);
//            subtitleView2.setRepeat(1);
//            marqueeTextView2.setTextSize(25);
            String color = subtitlesToDisplay.get(pos).getColor();
            marqueeTextView2.setTextColor(Color.parseColor(color));
            testText2  = subtitlesToDisplay.get(pos).getTextContent();//+"                                                                                                                                                                                                                                    ";
            //int lenght = testText2.length();
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    marqueeTextView2.setText(testText2);
                    subtitleView2.startMarquee();
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
            subtitleView3.setSpeed(subtitlesToDisplay.get(pos).getSpeed());
//            subtitleView3.setSpeed(10);
//            subtitleView3.setRepeat(1);
//            marqueeTextView2.setTextSize(25);
            String color = subtitlesToDisplay.get(pos).getColor();
            marqueeTextView3.setTextColor(Color.parseColor(color));
            testText3  = subtitlesToDisplay.get(pos).getTextContent();//+"                                                                                                                                                                                                                                    ";
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    marqueeTextView3.setText(testText3);
                    subtitleView3.startMarquee();
                }
            }, 1);
        }




    }



//    protected void displaySubtitle2(int pos){
//      int location  =subtitlesToDisplay.get(pos).getLocation();
//        switch (location) {
//            case 1:
//                subtitleView=(LYMarqueeViewThree)findViewById(R.id.subtitleView1);
//                marqueeTextView=(TextView)findViewById(R.id.textView1);
//                break;
//            case 2:
//                subtitleView=(LYMarqueeViewThree)findViewById(R.id.subtitleView2);
//                marqueeTextView=(TextView)findViewById(R.id.textView2);
//                break;
//            case 3:
//                subtitleView=(LYMarqueeViewThree)findViewById(R.id.subtitleView3);
//                marqueeTextView=(TextView)findViewById(R.id.textView3);
//                break;
//            default:
//                subtitleView=(LYMarqueeViewThree)findViewById(R.id.subtitleView2);
//                marqueeTextView=(TextView)findViewById(R.id.textView2);
//        }
//        subtitleView.mediaShowActivity = this;
//        marqueeTextView.setSelected(true);
//        marqueeTextView.setTextSize(subtitlesToDisplay.get(pos).getFonts());
//        subtitleView.setRepeat(subtitlesToDisplay.get(pos).getRepeatTime());
//        subtitleView.setSpeed(subtitlesToDisplay.get(pos).getSpeed());
//        String color = subtitlesToDisplay.get(pos).getColor();
//        marqueeTextView.setTextColor(Color.parseColor(color));
//        String text2 = subtitlesToDisplay.get(pos).getTextContent()+"                                                                                                                                                                                                                                    ";
//        marqueeTextView.setText(text2);
//        //marqueeTextView.setText("22bbeatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae eatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptate");
//
//        testText1 = text2;//"22bbeatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae beatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae eatae vitae sunt explicabo. Nemo enim ipsam voluptatembeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptatebeatae vitae sunt explicabo. Nemo enim ipsam voluptate";
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ///textView1.setText(randomStrings[new Random().nextInt(randomStrings.length)]);
//                marqueeTextView.setText(testText1);
//                subtitleView.startMarquee();
//            }
//        }, 1000);
//
//
//        String text = "                                                         ";
//        if (pos==0)
//        {
//            text = "how are you how are you how are you how are you how are you how are you how are you how are you";
//        }
//        else if (pos==1)
//        {
//            text = "I am good I am good I am good I am good I am good I am good I am good I am good I am good ";
//        }
//        else if (pos==2)
//        {
//            text = "good weather good weather good weather good weather good weather good weather good weather good weather ";
//        }
//        else if (pos==3)
//        {
//            text = "Sure, it is Sure, it is Sure, it is Sure, it is Sure, it is Sure, it is Sure, it is Sure, it is Sure, it is Sure, it is ";
//        }
//
//        text = text +"                                                                                                                                                                                                                                                                                             ";
//        //marqueeTextView.setText(text);
//
//        System.out.println("$$$$$$$" + subtitlesToDisplay.get(pos).getTextContent());
//    }





   /* private void starManager()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
            }
        }, 100);
    }*/

    protected void displayMedia()
    {

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
            } else {
                videoView.setVisibility(View.GONE);
                mediaImageView.setVisibility(View.VISIBLE);
                //subtitleView.setSelected(true);
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
                int duration = Integer.parseInt(imageNamesArray.get(positionOfMedia).get("duration")) ;

                if(duration<=0){
                    duration = Integer.parseInt(imageNamesArray.get(positionOfMedia).get("duration_sec")) ;
                }
                if(duration<=0) {
                    duration=5;
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        displayMedia();
                    }
                }, 1000 * duration); // duration of drawing images show and subtitle
            }

            positionOfMedia ++;
        }
    }



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

        displayMedia();
    }






    public void showImageFromInternalMemory (String path, String imageName)
    {
        try {
            String filePath = path + "/" + imageName;
            mediaImageView.setImageBitmap(readBitmapAutoSize(filePath, 1200, 1200));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

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
            int sampleSize = (outWidth / width + outHeight / height) / 2;
            opt.inSampleSize = sampleSize;
        }

        opt.inJustDecodeBounds = false;//最后把标志复原
        return opt;
    }

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

    @Override
    public void onCompletion(MediaPlayer mp) {
        displayMedia();


//        String videoName = imageNamesArray.get(positionOfMedia).get("url");
//        playVideo(videoName);
    }








    @Override
    public void refreshActivityContent()
    {
        ArrayList<HashMap<String, String>> newimageNamesArray = new ArrayList<>();
        ArrayList<Media> data = new ArrayList<>();
        if(isAdvertisement){
            int screenId = sharedPref.getInt(Constants.KEY_SCREEN_ID, 0);
            data = mediaDB.getAdvertisementMedias(screenId);
        }else if(!isAdvertisement && isStore){
            int storeId = sharedPref.getInt(Constants.KEY_STORE_ID, 1);
            data = mediaDB.getMediaByMediaTypeAndElementId(3, storeId);
        } else if(!isAdvertisement && isUserPhoto){
            data = mediaDB.getUserDrawingPhotos();
        }

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

        updateImages(newimageNamesArray);

        if(isAdvertisement){
            int screenId = sharedPref.getInt(Constants.KEY_SCREEN_ID, 0);
            ArrayList<Subtitle> subtitleData = subtitleDB.getSubtitles(screenId);
            if (subtitleData.size()>0) {
                this.updateTexts(subtitleData);
            }
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
        super.onPause();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
        editor.commit();
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                //touch screen to go back:
                //finish();
                break;
            default:
                return false;
        }
        return true;
    }
}
