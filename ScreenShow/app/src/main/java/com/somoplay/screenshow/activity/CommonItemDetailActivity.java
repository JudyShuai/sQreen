package com.somoplay.screenshow.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.somoplay.screenshow.R;
import com.somoplay.screenshow.app.Constants;
import com.somoplay.screenshow.database.StoreBranchDB;
import com.somoplay.screenshow.database.StoreMediaDB;
import com.somoplay.screenshow.model.StoreBranch;
import com.somoplay.screenshow.model.StoreMedia;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JudyShuai on 15-08-25.
 */
public class CommonItemDetailActivity extends BaseActivity {

    private TextView title;
    private ImageView storeLogo;
    private ImageView companyLogo;
    private TextView companyText;

    private ImageView storeItemImageView;
    //private ViewFlipper viewFlipper;

    //viewPager
    private List<ImageView> imageViews;
    //private int[]imageResId;
    private List<View> dots;
    private int currentItem = 0;



    private TextView storeItemTvName;
    private TextView storeItemTvPrice;
    private TextView storeItemTvContent;
    private TextView storeItemTvOnSale;
    private TextView storeItemTvExpiration;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private StoreMediaDB storeMediaDB= new StoreMediaDB(this);


    //protected ArrayList<String> imagelocalUrls = new ArrayList<>();
    public ArrayList<StoreMedia> detailPics = new ArrayList<>();
    protected float lastX;

    protected String textName;
    protected String price;
    protected String salePrice;
    protected String expirationDate;
    protected String textContent;
    protected String backgroundColor;
    private LinearLayout backgroundLayout;
    protected String fontColor;
    private  int storeId = 0;
    public int detailElementId;
    public ViewPager viewPager;
    public LinearLayout llDotGroup;
    /*private  int elementIdOffice;
    private  int elementIdCarAndHouse;
    private  int elementIdDish;*/
    //private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        StoreBranchDB storeBranchDB = new StoreBranchDB(this);
        //initView();

        //viewFlipper = (ViewFlipper) findViewById(R.id.flipper);

        sharedPref = getApplicationContext().getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE);
        editor = sharedPref.edit();

        title = (TextView) findViewById(R.id.item_text);
        storeLogo = (ImageView) findViewById(R.id.item_image);

        storeId = sharedPref.getInt(Constants.KEY_STORE_ID, 1);

        //storeItemImageView = (ImageView) findViewById(R.id.flipperImageView);
        storeItemTvName = (TextView) findViewById(R.id.item_name);
        storeItemTvPrice = (TextView) findViewById(R.id.store_item_price);
        storeItemTvOnSale = (TextView) findViewById(R.id.store_item_onSale);
        storeItemTvExpiration = (TextView) findViewById(R.id.store_item_expiration);
        storeItemTvContent = (TextView) findViewById(R.id.store_item_content);

//        int storeId = sharedPref.getInt(Constants.KEY_STORE_ID, 1);
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_ITEM_DETAIL_ACTIVITY);
        editor.commit();

        fontColor = sharedPref.getString(Constants.KEY_FONT_COLOR, "#ffffff");

        if (fontColor == ""){
            fontColor = "#ffffff";
        }else if(fontColor.charAt(0) != '#'){
            fontColor = "#" + fontColor;
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        llDotGroup = (LinearLayout) findViewById(R.id.ll_dot_group);

        //resetTime();
        /*
        backgroundColor = sharedPref.getString(Constants.KEY_BACKGROUND_COLOR, "#0f31e6");
        if (backgroundColor == ""){
            backgroundColor = "#0f31e6";
        }else if(backgroundColor.charAt(0) != '#'){
            backgroundColor = "#" + backgroundColor;
        }
        backgroundLayout = (LinearLayout)findViewById(R.id.common_item_detail_activity);
        backgroundLayout.setBackgroundColor(Color.parseColor(backgroundColor));
        */
        StoreBranch storeBranch = storeBranchDB.getStoreBranchByStoreId(storeId);

        if(storeBranch != null){
            String filePath = new String("file:///" + Constants.DEVICE_PATH_LOGO + "/" +
                    storeBranch.getLogoUrl());
            storeLogo.setImageURI(Uri.parse(filePath));
        }

       /* String getLogo = storeBranch.getLogoUrl();
        Log.d("getLogo String",getLogo);
        if(getLogo != null && getLogo.length() > 0 ) {
            filePath = new String("file:///" + Constants.DEVICE_PATH_LOGO + "/" + getLogo);

            //storeLogo.setImageURI(Uri.parse(filePath));     //may cause crash, use ImageRequest instead
            ImageRequest request = new ImageRequest(filePath,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            storeLogo.setImageBitmap(bitmap);
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Logo error DetailAc ", "error when requesting logo");
                            //storeLogo.setImageResource(R.drawable.image_load_error);
                        }
                    });
// Access the RequestQueue through your singleton class.
            AppController.getInstance().addToRequestQueue(request);
        }
*/


        companyLogo= (ImageView) findViewById(R.id.company_logo);
        String filePathLogo = Constants.DEVICE_PATH_LOGO + "/logo.png";
        companyLogo.setImageURI(Uri.parse(filePathLogo));
        companyText = (TextView)findViewById(R.id.company_text);
        companyText.setTextColor(Color.parseColor(fontColor));

        setText(title, true, 40);

        storeItemTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());

        /*imagelocalUrls.add("http://eadate.com/screenshow/uploads/images/storeitem/Test_storeItem_20151123202625_0315.png");
        imagelocalUrls.add("http://eadate.com/screenshow/uploads/images/storeitem/Test_storeItem_20151123202625_0337.png");*/

        //cimageResId = new int[] {R.drawable.view_pager, R.drawable.view_pager1, R.drawable.view_pager2, R.drawable.view_pager3};


    }

    @Override
    public void refreshActivityContent() {

    }

    @Override
    public void changeCurrentActivity() {

    }

   /* private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        llDotGroup = (LinearLayout) findViewById(R.id.ll_dot_group);

        detailImageViews = new ArrayList<ImageView>();
        int[] imageIDs = new int[] {
                R.drawable.a,
                R.drawable.b,
                R.drawable.c,
                R.drawable.d,
                R.drawable.e,
        };

        ImageView imageView = null;
        View dot = null;
        LayoutParams params = null;
        for (int id : imageIDs) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(id);
            detailImageViews.add(imageView);

            // 每循环一次添加一个点到线行布局中
            dot = new View(this);
            dot.setBackgroundResource(R.drawable.dot_bg_selector);
            params = new LayoutParams(5, 5);
            params.leftMargin = 10;
            dot.setEnabled(false);
            dot.setLayoutParams(params);
            llDotGroup.addView(dot); // 向线性布局中添加"点"
        }

        viewPager.setAdapter(new BannerAdapter());
        viewPager.setOnPageChangeListener(new BannerPageChangeListener());

        // 选中第一个图片、文字描述
        llDotGroup.getChildAt(0).setEnabled(true);
        viewPager.setCurrentItem(0);
    }*/

   /* *//**
     * ViewPager的适配器
     *//*
    private class BannerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(detailImageViews.get(position % detailImageViews.size()));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = detailImageViews.get(position % detailImageViews.size());

           *//* // 为每一个page添加点击事件
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Page 被点击了", Toast.LENGTH_SHORT).show();
                }

            });*//*

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    *//**
     * Banner的Page切换监听器
     *//*
    private class BannerPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // Nothing to do
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // Nothing to do
        }

        @Override
        public void onPageSelected(int position) {
            // 取余后的索引，得到新的page的索引
            int newPositon = position % detailImageViews.size();
            // 把上一个点设置为被选中
            llDotGroup.getChildAt(preDotPosition).setEnabled(false);
            // 根据索引设置那个点被选中
            llDotGroup.getChildAt(newPositon).setEnabled(true);
            // 新索引赋值给上一个索引的位置
            preDotPosition = newPositon;
        }

    }*/



    public void getExtraPics(int mediaType, int itemId, String devicePath){
        /*// todo: test for new store media
        mediaArray = mediaDB.getStoreMediaByMediaTypeAndElementId(mediaType, itemId);
        //mediaArray = mediaDB.getMediaByMediaTypeAndElementId(mediaType, itemId);
        int i;
        for(i = 0; i < mediaArray.size(); i++){
            StoreMedia media = mediaArray.get(i);
            imagelocalUrls.add("file:///" + devicePath + "/" + media.getMediaUrl());
        }

        for (i = 0; i < imagelocalUrls.size(); i++) {          // 添加图片源
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            Bitmap tempBitmap = readBitmapAutoSize(imagelocalUrls.get(i), 1200,1200);

            while (tempBitmap.getHeight()*tempBitmap.getWidth() < 400000){
                tempBitmap = Bitmap.createScaledBitmap(tempBitmap, tempBitmap.getWidth()*2, tempBitmap.getHeight()*2, true);
            }

            iv.setImageBitmap(tempBitmap);
            viewFlipper.addView(iv,i);
        }
        viewFlipper.removeViewAt(i);*/
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            currentItem = position;
            //tv_title.setText(titles[position]);
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return detailPics.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(imageViews.get(arg1));
            return imageViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    public void setDetailPics(int detailElementId){
        this.detailElementId = detailElementId;

        detailPics = storeMediaDB.getStoreMediaByMediaTypeAndElementId(4, detailElementId);
        imageViews = new ArrayList<ImageView>();

        //initial imageviews:
        for (int i=0; i < detailPics.size(); i++){
            String filePath = Constants.DEVICE_PATH_STORE + "/" + detailPics.get(i).getMediaUrl();
            ImageView imageView = new ImageView(this);
            //imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //Bitmap tempBitmap = readBitmapAutoSize(imagelocalUrls.get(i),1200,1200);
            /*while (tempBitmap.getHeight()*tempBitmap.getWidth() < 400000){
                tempBitmap = Bitmap.createScaledBitmap(tempBitmap, tempBitmap.getWidth()*2, tempBitmap.getHeight()*2, true);
            }*/
            imageView.setImageBitmap(readBitmapAutoSize(filePath, 1200, 1200));
            imageViews.add(imageView);
        }

        dots = new ArrayList<View>();
        View dot = null;
        LinearLayout.LayoutParams params = null;
        for(int i=0; i < detailPics.size(); i++){
            //View newDot = new View(this,null,R.style.dot_style);
            dot = new View(this);
            dot.setBackgroundResource(R.drawable.dot_bg_selector);
            //changed the size for dots:
            params = new LinearLayout.LayoutParams(18, 18);
            params.leftMargin = 15;

            dot.setEnabled(false);
            //changed the size for dots:
           /* params = new LinearLayout.LayoutParams(18, 18);
            params.leftMargin = 10;*/
            if(i==0){
                dot.setEnabled(true);
            }else {
                dot.setEnabled(false);
            }
            dot.setLayoutParams(params);
            llDotGroup.addView(dot);
            dots.add(dot);

            //dots.add(findViewById(R.id.v_dot0));
        }


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }


    public void fillTextIntoTextViews(){
        setText(storeItemTvName, false, 30);
        setText(storeItemTvPrice, false, 25);
        setText(storeItemTvOnSale, false, 25);
        setText(storeItemTvExpiration, false, 25);
        setText(storeItemTvContent, false, 25);

        storeItemTvName.append(textName);
        storeItemTvPrice.append(price);
        storeItemTvOnSale.append(salePrice);
        storeItemTvExpiration.append(expirationDate);
        if(textContent==""){
            storeItemTvContent.setVisibility(View.GONE);
        }else {
            storeItemTvContent.append(textContent);
        }
    }



    // Using the following method, we will handle all screen swaps.
    public boolean onTouchEvent(MotionEvent touchevent) {

       /* if(imagelocalUrls.size() > 1){
            switch (touchevent.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    resetTime();
//                    lastX = touchevent.getX();

                    break;
                case MotionEvent.ACTION_UP:
                    *//*float currentX = touchevent.getX();

                    // Handling left to right screen swap.
                    if (lastX < currentX) {
                        // If there aren't any other children, just break.
                        if (viewFlipper.getDisplayedChild() == 0)
                            break;
                        // Next screen comes in from left.
                        viewFlipper.setInAnimation(this, R.anim.in_from_left);
                        // Current screen goes out from right.
                        viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                        // Display next screen.
                        viewFlipper.showNext();

                    }
                    // Handling right to left screen swap.

                    if (lastX > currentX) {
                        // If there is a child (to the left), kust break.

                        if (viewFlipper.getDisplayedChild() == 1)
                            break;
                        // Next screen comes in from right.

                        viewFlipper.setInAnimation(this, R.anim.in_from_right);
                        // Current screen goes out from left.
                        viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                        // Display previous screen.
                        viewFlipper.showPrevious();
                    }*//*
                    break;
            }
        }
        else {*/
            switch (touchevent.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    resetTime();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
        //}
        return false;
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
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888; //设置加载图片的颜色数为16bit，默认是RGB_8888，表示24bit颜色和透明通道，但一般用不上
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
    public void onResume(){
        super.onResume();
        resetTime();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_ITEM_DETAIL_ACTIVITY);
        editor.commit();
    }

    @Override
    public void onPause(){
        super.onPause();
        editor.putInt(Constants.KEY_CURRENT_ACTIVITY, Constants.CURRENT_NO_NEED_TO_UPDATE);
        editor.commit();
    }

    @Override
    public void goToPhotos() {
        Intent intent = new Intent(CommonItemDetailActivity.this, MediaShowActivity.class);
        intent.putExtra(Constants.KEY_IS_ADVERTISEMENT, false);
        intent.putExtra(Constants.KEY_IS_STORE, true);
        startActivity(intent);
    }

    public void setText(TextView tv, boolean isTitle, int fontSize){

        String storeName = sharedPref.getString(Constants.KEY_STORE_NAME, "normal");
        int fontHeadSize = sharedPref.getInt(Constants.KEY_FONT_HEAD_SIZE, 30);
        //int fontTextSize = sharedPref.getInt(Constants.KEY_FONT_TEXT_SIZE, 12);


        //tv.setTextColor(Color.parseColor(fontColor));
        if(isTitle){
            tv.setTextSize(30);
            tv.setText(storeName);
            tv.setTextColor(Color.WHITE);
        }else{
            tv.setTextSize((float) fontSize);
        }

    }


    public String stringToDecimal(String toBeChanged){
        String newString = "";

        if(toBeChanged.length() > 0) {
            for (int i = 0; i < toBeChanged.length(); i++) {
                newString += toBeChanged.charAt(i);
                if (toBeChanged.charAt(i) == '.' && toBeChanged.length() > i + 1) {
                    newString += toBeChanged.charAt(i + 1);
                    if (toBeChanged.length() > i + 2) {
                        newString += toBeChanged.charAt(i + 2);
                    }else{
                        newString += "0";
                    }
                    return newString;
                }
            }
            return newString;
        }
        else{
            return toBeChanged;
        }
    }


}
