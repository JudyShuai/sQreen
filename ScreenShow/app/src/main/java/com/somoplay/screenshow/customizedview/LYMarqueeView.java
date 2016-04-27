package com.somoplay.screenshow.customizedview ;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.somoplay.screenshow.activity.MediaShowActivity;
//import asia.ivity.android.marqueeview.sample.BuildConfig;
//import asia.ivity.android.marqueeview.sample.R;

/**
 * Provides a simple marquee effect for a single {@link android.widget.TextView}.
 *
 * @author Sebastian Roth <sebastian.roth@gmail.com>
 */
public class LYMarqueeView extends LinearLayout {
    private TextView mTextField;
    private boolean isFinished;//YAO
    private float mTextWidth;//YAO2
    private int currentTextColor;//YAO

    private ScrollView mScrollView;

    private static final int TEXTVIEW_VIRTUAL_WIDTH = 2000;

    private Animation mMoveTextOut = null;
    private Animation mMoveTextIn = null;

    private Paint mPaint;

    private boolean mMarqueeNeeded = false;

    private static final String TAG = LYMarqueeView.class.getSimpleName();

    private float mTextDifference;

    /**
     * Control the speed. The lower this value, the faster it will scroll.
     */
    private static final int DEFAULT_SPEED = 60;
    private static final int DEFAULT_REPEAT = 0;//YAO
    /**
     * Control the pause between the animations. Also, after starting this activity.
     */
    private static final int DEFAULT_ANIMATION_PAUSE =1000;//YAO 2000;

    private int mSpeed = DEFAULT_SPEED;
    private int mRepeat = 0;//YAO
    private boolean repeatFoever = false;

    private int mAnimationPause = DEFAULT_ANIMATION_PAUSE;

    private boolean mAutoStart = true;

    private Interpolator mInterpolator = new LinearInterpolator();

    private boolean mCancelled = false;
    private Runnable mAnimationStartRunnable;

    private boolean mStarted;
    private int textGravity = 1;//YAO2
    public MediaShowActivity mediaShowActivity;//YAO2

    public int getTextGravity() {
        return textGravity;
    }//YAO2
    public void setTextGravity(int textGravity) {
        this.textGravity = textGravity;
    }//YAO2
    /**
     * Sets the animation speed.
     * The lower the value, the faster the animation will be displayed.
     *
     * @param speed Milliseconds per PX.
     */
    public void setSpeed(int speed) {
        this.mSpeed = speed;
    }
    public void setRepeat(int mRepeat) {
        this.mRepeat = mRepeat;
    }//YAO
    /**
     * Sets the pause between animations
     *
     * @param pause In milliseconds.
     */
    public void setPauseBetweenAnimations(int pause) {
        this.mAnimationPause = pause;
    }

    /**
     * Sets a custom interpolator for the animation.
     *
     * @param interpolator Animation interpolator.
     */
    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public LYMarqueeView(Context context) {
        super(context);
        init(context);
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public LYMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
        extractAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public LYMarqueeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context);
        extractAttributes(attrs);
    }

    private void extractAttributes(AttributeSet attrs) {
        //yao
//        if (getContext() == null) {
//            return;
//        }
//
//        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.asia_ivity_android_marqueeview_MarqueeView);
//
//        if (a == null) {
//            return;
//        }
//
//        mSpeed = a.getInteger(R.styleable.asia_ivity_android_marqueeview_MarqueeView_speed, DEFAULT_SPEED);
//        mAnimationPause = a.getInteger(R.styleable.asia_ivity_android_marqueeview_MarqueeView_pause, DEFAULT_ANIMATION_PAUSE);
//        mAutoStart = a.getBoolean(R.styleable.asia_ivity_android_marqueeview_MarqueeView_autoStart, false);
//
//        a.recycle();
    }

    private void init(Context context) {
        // init helper
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mInterpolator = new LinearInterpolator();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (getChildCount() == 0 || getChildCount() > 1) {
            throw new RuntimeException("MarqueeView must have exactly one child element.");
        }

        if (changed) {
            View v = getChildAt(0);
            // Fixes #1: Exception when using android:layout_width="fill_parent". There seems to be an additional ScrollView parent.
            if (v instanceof ScrollView && ((ScrollView) v).getChildCount() == 1) {
                v = ((ScrollView) v).getChildAt(0);
            }

            if (!(v instanceof TextView)) {
                throw new RuntimeException("The child view of this MarqueeView must be a TextView instance.");
            }

            initView(getContext());

            prepareAnimation();

            if (mAutoStart) {
                startMarquee();
            }
        }
    }

    /**
     * Starts the configured marquee effect.
     */
    public void startMarquee() {
        mTextField.setVisibility(VISIBLE);//YAO2
        if (mMarqueeNeeded) {
            startTextFieldAnimation();
        }

        mCancelled = false;
        mStarted = true;
    }

    private void startTextFieldAnimation() {
        mAnimationStartRunnable = new Runnable() {
            public void run() {
                isFinished=false;//YAO2
                mTextField.startAnimation(mMoveTextIn);
            }
        };
        post(mAnimationStartRunnable);
        //postDelayed(mAnimationStartRunnable, mAnimationPause);  YAO
    }

    /**
     * Disables the animations.
     */
    public void reset() {
        mCancelled = true;

        if (mAnimationStartRunnable != null) {
            removeCallbacks(mAnimationStartRunnable);
        }

        mTextField.clearAnimation();
        mStarted = false;

        //mMoveTextOut.reset();
        mMoveTextIn.reset();
//yao
//        mScrollView.removeView(mTextField);
//        mScrollView.addView(mTextField);

        invalidate();
    }

    private void prepareAnimation() {
        // Measure
        mPaint.setTextSize(mTextField.getTextSize());
        mPaint.setTypeface(mTextField.getTypeface());
        float mTextWidth = mPaint.measureText(mTextField.getText().toString());

        // See how much functions are needed at all
        mMarqueeNeeded = mTextWidth > getMeasuredWidth();

        mTextDifference = Math.abs((mTextWidth - getMeasuredWidth()))  + getWidth();//+ 5; YAO
//yao
//        if (BuildConfig.DEBUG) {
//            Log.d(TAG, "mTextWidth       : " + mTextWidth);
//            Log.d(TAG, "measuredWidth    : " + getMeasuredWidth());
//            Log.d(TAG, "mMarqueeNeeded   : " + mMarqueeNeeded);
//            Log.d(TAG, "mTextDifference  : " + mTextDifference);
//        }
        final int duration = (int) (mTextWidth * mSpeed);
        //final int duration = (int) (mTextDifference * mSpeed);  YAO
//yao
//        mMoveTextOut = new TranslateAnimation(0, -mTextDifference, 0, 0);
//        mMoveTextOut.setDuration(duration);
//        mMoveTextOut.setInterpolator(mInterpolator);
//        mMoveTextOut.setFillAfter(true);
//
//        mMoveTextIn = new TranslateAnimation(-mTextDifference, 0, 0, 0);
//        mMoveTextIn.setDuration(duration);
//        mMoveTextIn.setStartOffset(mAnimationPause);
//        mMoveTextIn.setInterpolator(mInterpolator);
//        mMoveTextIn.setFillAfter(true);

        mMoveTextIn = new TranslateAnimation(getWidth(), (-mTextWidth-getWidth()), 0, 0);
        mMoveTextIn.setDuration(duration+getWidth()*mSpeed);
        mMoveTextIn.setStartOffset(0);
        mMoveTextIn.setInterpolator(mInterpolator);
        mMoveTextIn.setFillAfter(true);
        if(!repeatFoever) {
            int repeatCount = mRepeat>0?mRepeat-1:0;
            System.out.println(" ####### repeatCount= "+repeatCount);
            mMoveTextIn.setRepeatCount(repeatCount);
        }else{
            mMoveTextIn.setRepeatCount(Animation.INFINITE);
        }


//        mMoveTextOut.setAnimationListener(new Animation.AnimationListener() {
//            public void onAnimationStart(Animation animation) {
//                expandTextView();
//            }
//
//            public void onAnimationEnd(Animation animation) {
//                if (mCancelled) {
//                    return;
//                }
//
//                mTextField.startAnimation(mMoveTextIn);
//            }
//
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });

        mMoveTextIn.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                mTextField.setTextColor(currentTextColor);//yao
                expandTextView();
            }

            public void onAnimationEnd(Animation animation) {
                //YAO2
                isFinished = true;
                System.out.println("$$$$$$$ *^&*%$#@!$$$   animation finished    SWITCH_TEXT");
                //mTextField.setVisibility(GONE);//YAO2

                cutTextView();
                //mediaShowActivity.startShowSubtitle();//YAO2
                //mediaShowActivity.showNewSubtitle();//YAO2
                if (mCancelled) {
                    return;
                }
               // startTextFieldAnimation();
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void initView(Context context) {
        // Scroll View
        LayoutParams sv1lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        sv1lp.gravity = Gravity.CENTER_HORIZONTAL;
        mScrollView = new ScrollView(context);

        // Scroll View 1 - Text Field
        mTextField = (TextView) getChildAt(0);
        removeView(mTextField);

        mScrollView.addView(mTextField, new ScrollView.LayoutParams(TEXTVIEW_VIRTUAL_WIDTH, LayoutParams.WRAP_CONTENT));

        mTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                final boolean continueAnimation = mStarted;

                reset();
                //yao
                currentTextColor=mTextField.getCurrentTextColor();
                mTextField.setTextColor(Color.TRANSPARENT);
                prepareAnimation();

                cutTextView();

                post(new Runnable() {
                    @Override
                    public void run() {
                        if (continueAnimation) {
                            startMarquee();
                        }
                    }
                });
            }
        });

        addView(mScrollView, sv1lp);
    }

    private void expandTextView() {
        ViewGroup.LayoutParams lp = mTextField.getLayoutParams();
        lp.width = 100000;//2000;//yao
        mTextField.setLayoutParams(lp);
    }

    private void cutTextView() {
        if (mTextField.getWidth() != getMeasuredWidth()) {
            ViewGroup.LayoutParams lp = mTextField.getLayoutParams();
            lp.width = getMeasuredWidth();
            mTextField.setLayoutParams(lp);
        }
    }
}
