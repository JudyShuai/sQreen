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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.activity.BaseActivity;
import com.somoplay.screenshow.activity.CarAndHouseActivity;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ann on 2015-11-20.
 */
public class MediaShowActivityThread extends BaseActivity implements MediaPlayer.OnCompletionListener {
    private static final String TAG = MediaShowActivityThread.class.getSimpleName();
    //data structure
    public boolean isAdvertisement;
    public boolean isStore;
    public boolean isUserPhoto;

    public CarAndHouseActivity carAndHouseActivity;

    private int subPos = 0;
    private int positionOfMedia = 0;

    private String imageDevicePath = "";
    public String TARGET_BASE_PATH;
    private String testText1;
    private String testText2;
    private String testText3;
    private String testText4;

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

    private ImageView mediaImageView;

    //database
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private SubtitleDB subtitleDB = new SubtitleDB(this);
    private MediaDB mediaDB = new MediaDB(this);

    //controller
    private MediaController mediaController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediashow);

        AppController.getInstance().setGeneralActivity(this);

        carAndHouseActivity = new CarAndHouseActivity();
        initVariables();
        initText();
        initVideoAndImage();
        refreshActivityContent();
    }

    @Override
    public void onResume() {
        super.onResume();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_MEDIA_SHOW_ACTIVITY);
        editor.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
        editor.commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
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

    private void initVariables() {
        isAdvertisement = getIntent().getBooleanExtra(Constants.KEY_IS_ADVERTISEMENT, true);
        isStore = getIntent().getBooleanExtra(Constants.KEY_IS_STORE, false);
        isUserPhoto = getIntent().getBooleanExtra(Constants.KEY_IS_USERPHOTO, false);

        sharedPref = getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        editor = sharedPref.edit();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_MEDIA_SHOW_ACTIVITY);
        editor.commit();

        TARGET_BASE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
    }

    private void initText() {
        subtitleView1 = (LYMarqueeViewThree) findViewById(R.id.subtitleView1);
        subtitleView1.setPauseBetweenAnimations(1);
        subtitleView1.setSpeed(1);
        //subtitleView1.mediaShowActivity = this;
        subtitleView1.isOneOrThree = 3;
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
        subtitleView2.isOneOrThree = 3;
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
        subtitleView3.isOneOrThree = 3;
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                subtitleView3.startMarquee();
            }
        });

        marqueeTextView3 = (TextView) findViewById(R.id.textView3);
        marqueeTextView3.setTextSize(30);
    }


    private void initVideoAndImage() {
        videoView = (VideoView) findViewById(R.id.videoWindow);
        videoView.setVisibility(View.VISIBLE);
        videoView.setMediaController(mediaController);
        videoView.setOnCompletionListener(this);
        mediaController = new MediaController(MediaShowActivityThread.this);
        mediaController.setMediaPlayer(videoView);

        mediaImageView = (ImageView) findViewById(R.id.imageviewActressPhoto);
        mediaImageView.setVisibility(View.GONE);
    }


    @Override
    public void refreshActivityContent() {
        ArrayList<HashMap<String, String>> newimageNamesArray = new ArrayList<>();
        ArrayList<Media> data = new ArrayList<>();

        if (isAdvertisement) {
            int screenId = sharedPref.getInt(Constants.KEY_SCREEN_ID, 0);
            data = mediaDB.getAdvertisementMedias(screenId);
        } else if (!isAdvertisement && isStore) {
            int storeId = sharedPref.getInt(Constants.KEY_STORE_ID, 1);
            data = mediaDB.getMediaByMediaTypeAndElementId(3, storeId);
        } else if (!isAdvertisement && isUserPhoto) {
            data = mediaDB.getUserDrawingPhotos();
        }

        int j = 0;
        boolean isTempChanged = false;
        ArrayList<Media> sortedDateBySequence = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int temp = data.get(0).getSequenceId();

            for (j = 0; j < data.size(); j++) {
                if (temp > data.get(j).getSequenceId()) {
                    temp = data.get(j).getSequenceId();
                    isTempChanged = true;
                }
            }

            if (isTempChanged == true) {
                sortedDateBySequence.add(data.get(j - 1));
                data.remove(j - 1);
                isTempChanged = false;
            } else {
                sortedDateBySequence.add(data.get(0));
                data.remove(0);
            }

            i--;
        }

        for (int i = 0; i < sortedDateBySequence.size(); i++) {
            Media media = sortedDateBySequence.get(i);
            HashMap<String, String> hashMapForMediaType = new HashMap<>();
            hashMapForMediaType.put("url", media.getMediaUrl());
            hashMapForMediaType.put("type", String.valueOf(media.getMediaType()));
            hashMapForMediaType.put("duration_sec", String.valueOf(media.getDurationSec()));//defined by media
            hashMapForMediaType.put("duration", String.valueOf(media.getDuration()));//defined by media screen one
            newimageNamesArray.add(hashMapForMediaType);
        }

        updateImages(newimageNamesArray);//updateImage data and show


        if (isAdvertisement) {
            int screenId = sharedPref.getInt(Constants.KEY_SCREEN_ID, 0);
            ArrayList<Subtitle> subtitleData = subtitleDB.getSubtitles(screenId);
            if (subtitleData.size() > 0) {
                try {
                    this.updateTexts(subtitleData);//GET subtitles data and show the subtitles
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (imageNamesArray.size() != 0) {
            displayMedia();
        }
        if (subtitlesToDisplay.size() != 0) {
            startShowSubtitle();
        }
    }

    //1) update subtitle data
    public void updateTexts1(ArrayList<Subtitle> subtitles) {
        int oldSize = subtitlesToDisplay.size();
        int newSize = subtitles.size();

        for (int i = 0; i < newSize; i++) {
            subtitlesToDisplay.add(subtitles.get(i));
        }

        for (int i = 0; i < oldSize; i++) {
            if (i < subtitlesToDisplay.size()) {
                subtitlesToDisplay.remove(i);
            } else {
                Log.d("Index out error", "i is out subtitlesToDisplay size");
            }
        }

        startShowSubtitle();
    }


    //private Condition condition = lock.newCondition();
    public void threadtext(Subtitle item) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            subtitlesToDisplay.add(item);
            int i = subtitlesToDisplay.size();
            Log.d(TAG, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    .format(new Date()) + " - " + Thread.currentThread() +
                    "获取了写锁，修正数据ID为：" + subtitlesToDisplay.get(i - 1).getSubId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //condition.signal();
            lock.unlock();
        }
    }

    public void updateTexts(final ArrayList<Subtitle> subtitles) throws InterruptedException {
        int oldSize = subtitlesToDisplay.size();
        int newSize = subtitles.size();

        for (int i = 0; i < newSize; i++) {
            final Subtitle item = subtitles.get(i);
            for (int j = 0; j < oldSize; j++) {
                if (j < subtitlesToDisplay.size()) {
                    subtitlesToDisplay.remove(j);
                }
            }
            new Thread() {
                public void run() {
                    threadtext(item);
                }
            }.start();
        }

        if (subtitlesToDisplay.size() != 0) {
            startShowSubtitle();
        }
    }


    //2 change index
    public void startShowSubtitle() {
        if (subPos >= subtitlesToDisplay.size()) {
            subPos = 0;
        }
        Log.d(TAG, "start show subtitle....");
        displaySubtitle(subPos);
        subPos++;
    }

    //3 show subtitle
    protected void displaySubtitle(int pos) {
        int location = subtitlesToDisplay.get(pos).getLocation();
        if (location == 1) {

            subtitleView1.setRepeat(subtitlesToDisplay.get(pos).getRepeatTime());
//            subtitleView1.setSpeed(subtitlesToDisplay.get(pos).getSpeed());
            subtitleView1.setSpeed(2);

            String color = subtitlesToDisplay.get(pos).getColor();
            marqueeTextView1.setTextColor(Color.parseColor(color));
            testText1 = subtitlesToDisplay.get(pos).getTextContent() + "                                                                                                                                                                                                                                    ";
            //testText1  =subtitlesToDisplay.get(pos).getTextContent();
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    marqueeTextView1.setText(testText1);
                    subtitleView1.startMarquee();
                }
            }, 1);
        } else if (location == 2) {

            subtitleView2.setRepeat(subtitlesToDisplay.get(pos).getRepeatTime());
//            subtitleView2.setSpeed(subtitlesToDisplay.get(pos).getSpeed());
            subtitleView2.setSpeed(2);

            String color = subtitlesToDisplay.get(pos).getColor();
            marqueeTextView2.setTextColor(Color.parseColor(color));
            testText2 = subtitlesToDisplay.get(pos).getTextContent() + "                                                                                                                                                                                                                                    ";
            //int lenght = testText2.length();
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    marqueeTextView2.setText(testText2);
                    subtitleView2.startMarquee();
                }
            }, 1);
        } else// (location==3)
        {
            subtitleView3.setRepeat(subtitlesToDisplay.get(pos).getRepeatTime());
//            subtitleView3.setSpeed(subtitlesToDisplay.get(pos).getSpeed());
            subtitleView3.setSpeed(2);
            String color = subtitlesToDisplay.get(pos).getColor();
            marqueeTextView3.setTextColor(Color.parseColor(color));
            testText3 = subtitlesToDisplay.get(pos).getTextContent() + "                                                                                                                                                                                                                                    ";
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    marqueeTextView3.setText(testText3);
                    subtitleView3.startMarquee();
                }
            }, 1);
        }
    }

    // 1) first  update images data
    public void updateImages1(ArrayList<HashMap<String, String>> newimageNamesArray) {
        int oldSize = imageNamesArray.size();

        for (int i = 0; i < newimageNamesArray.size(); i++) {
            HashMap<String, String> imageName = newimageNamesArray.get(i);
            imageNamesArray.add(imageName);
        }

        for (int i = 0; i < oldSize; i++) {
            imageNamesArray.remove(0);
        }

        displayMedia();// start to show image
    }

    public void threadImages(HashMap<String, String> imageName) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        Log.d(TAG, "write lock is already locked");
        try {
            ;
            imageNamesArray.add(imageName);
            Log.d(TAG, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                    .format(new Date()) + " - " + Thread.currentThread() + "获取了写锁，修正数据ID为：" + imageNamesArray.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //condition.signal();
            lock.unlock();
            Log.d(TAG, "write lock is now unlocked");
        }
    }

    public void updateImages(final ArrayList<HashMap<String, String>> newimageNamesArray) {

        int oldSize = imageNamesArray.size();
        int newSize = newimageNamesArray.size();
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < oldSize; j++) {
                if (j < imageNamesArray.size()) {
                    imageNamesArray.remove(j);
                }
            }
            final HashMap<String, String> imageName = newimageNamesArray.get(i);
            new Thread() {
                public void run() {
                    threadImages(imageName);
                }
            }.start();
        }

        Log.d(TAG, "starting show media......");
        displayMedia();// start to show image
    }


    // 2) first  show images
    protected void displayMedia() {
        Log.d(TAG, "start show media.....");
        if (imageNamesArray.size() > 0) {
            if (positionOfMedia > imageNamesArray.size() - 1) {
                positionOfMedia = 0;
            }

            if (Integer.parseInt(imageNamesArray.get(positionOfMedia).get("type")) == 1) {
                videoView.setVisibility(View.VISIBLE);
                mediaImageView.setVisibility(View.GONE);

                String videoName = imageNamesArray.get(positionOfMedia).get("url");
                playVideo(videoName);
            } else {
                videoView.setVisibility(View.GONE);
                mediaImageView.setVisibility(View.VISIBLE);

                if (isAdvertisement) {
                    imageDevicePath = Constants.DEVICE_PATH_ADS;
                } else if (isStore) {
                    imageDevicePath = Constants.DEVICE_PATH_STORE;
                } else if (isUserPhoto) {
                    imageDevicePath = Constants.DEVICE_PATH_PHOTOSHOW;
                }

                showImageFromInternalMemory(imageDevicePath, imageNamesArray.get(positionOfMedia).get("url"));
                int duration = Integer.parseInt(imageNamesArray.get(positionOfMedia).get("duration"));//defined by media screen one

                if (duration <= 0) {
                    duration = Integer.parseInt(imageNamesArray.get(positionOfMedia).get("duration_sec"));//defined by media
                }

                if (duration <= 0) {
                    duration = 50;
                }

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        displayMedia();
                    }
                }, 1000 * duration); // duration of drawing images show and subtitle
            }

            positionOfMedia++;
        }
    }

    // 3) play video
    private void playVideo(String videoName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), videoName);
        if (file.exists()) {
            videoView.setVideoPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + videoName);
            videoView.requestFocus();
            videoView.start();
            mediaController.show();
        }
    }

    //3.1  delegate for play video
    @Override
    public void onCompletion(MediaPlayer mp) {
        displayMedia();
    }

    // 4) find images call 5)
    public void showImageFromInternalMemory(String path, String imageName) {
        try {
            String filePath = path + "/" + imageName;
            mediaImageView.setImageBitmap(readBitmapAutoSize(filePath, 1200, 1200));
        } catch (Exception e) {
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
            int sampleSize = (outWidth / width + outHeight / height) / 2;
            opt.inSampleSize = sampleSize;
        }

        opt.inJustDecodeBounds = false;//最后把标志复原
        return opt;
    }

    @Override
    public void changeCurrentActivity() {
        int showContentType = sharedPref.getInt(Constants.KEY_SHOW_CONTENT_TYPE, 0);

        if (showContentType == 1) {
            Intent intent = new Intent(this, MediaShowActivity.class);
            intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, true);
            startActivity(intent);
        } else if (showContentType == 2) {
            Intent intent = new Intent(this, MediaShowActivity.class);
            intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
            intent.putExtra(Constants.KEY_IS_USERPHOTO, true);
            startActivity(intent);
        } else if (showContentType == 4) {

            //Intent intent = new Intent(this, StoreShowActivity.class);
            Intent intent = new Intent(this, new_StoreShowActivity.class);
            startActivity(intent);
        } else if (showContentType == 5) {
            Intent intent = new Intent(this, OfficeShowActivity.class);
            startActivity(intent);
        }
    }
}

