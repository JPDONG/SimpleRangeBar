package com.learn.myrangebar;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    private TabHost tabHost;

    private TextView mValue;
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;
    private TextView mText4;
    private TextView mText5;
    private ImageButton mReset;
    private ImageView mImageBlush;
    private RangeBar mBlushRangeBar;
    private RangeBar mSkinSoftenedRangeBar;
    private RangeBar mSkinBrighteningRangeBar;
    private RangeBar mEyesEnhanceRangeBar;
    private RangeBar mCheeksTinedRangeBar;
    private LinearLayout mLinearlayoutBlush;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }

    private void initViews() {

        mReset = (ImageButton) findViewById(R.id.bt_reset);
        tabHost = (TabHost) findViewById(R.id.tabhost);
        mLinearlayoutBlush = (LinearLayout) findViewById(R.id.linearlayout_blush);
        initBlushItem();
        LayoutInflater inflater = getLayoutInflater();
        View mTabIndicator1 = inflater.inflate(R.layout.tab_blush,null);
        View mTabIndicator2 = inflater.inflate(R.layout.tab,null);
        View mTabIndicator3 = inflater.inflate(R.layout.tab,null);
        View mTabIndicator4 = inflater.inflate(R.layout.tab,null);
        View mTabIndicator5 = inflater.inflate(R.layout.tab,null);
        ImageView mImage1 = (ImageView) mTabIndicator1.findViewById(R.id.iv_tab);
        mImage1.setImageResource(R.drawable.ic_beautification_blush);
        mImageBlush = (ImageView) mTabIndicator1.findViewById(R.id.iv_tab_blush);
        mImageBlush.setImageResource(R.drawable.mode_beauty_blush_color_none);
        ImageView mImage2 = (ImageView) mTabIndicator2.findViewById(R.id.iv_tab);
        mImage2.setImageResource(R.drawable.ic_beautification_skin_softened);
        mText2 = (TextView) mTabIndicator2.findViewById(R.id.tv_tab);
        ImageView mImage3 = (ImageView) mTabIndicator3.findViewById(R.id.iv_tab);
        mImage3.setImageResource(R.drawable.ic_beautification_skin_brightening);
        mText3 = (TextView) mTabIndicator3.findViewById(R.id.tv_tab);
        ImageView mImage4 = (ImageView) mTabIndicator4.findViewById(R.id.iv_tab);
        mImage4.setImageResource(R.drawable.ic_beautification_eyes_enhance);
        mText4 = (TextView) mTabIndicator4.findViewById(R.id.tv_tab);
        ImageView mImage5 = (ImageView) mTabIndicator5.findViewById(R.id.iv_tab);
        mImage5.setImageResource(R.drawable.ic_beautification_cheeks_tinned);
        mText5 = (TextView) mTabIndicator5.findViewById(R.id.tv_tab);
        //mBlushRangeBar = (RangeBar) findViewById(R.id.rangebar_blush);
        mSkinSoftenedRangeBar = (RangeBar) findViewById(R.id.rangebar_skin_softened);
        mSkinBrighteningRangeBar = (RangeBar) findViewById(R.id.rangebar_skin_brightening);
        mEyesEnhanceRangeBar = (RangeBar) findViewById(R.id.rangebar_eyes_enhance);
        mCheeksTinedRangeBar = (RangeBar) findViewById(R.id.rangebar_cheeks_tined);
        mValue = (TextView) findViewById(R.id.tv_showvalue);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1").setIndicator(mTabIndicator1)
                .setContent(R.id.horizontal_scrollview_blush);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2").setIndicator(mTabIndicator2)
                .setContent(R.id.rangebar_skin_softened);
        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3").setIndicator(mTabIndicator3)
                .setContent(R.id.rangebar_skin_brightening);
        TabHost.TabSpec tab4 = tabHost.newTabSpec("tab4").setIndicator(mTabIndicator4)
                .setContent(R.id.rangebar_eyes_enhance);
        TabHost.TabSpec tab5 = tabHost.newTabSpec("tab5").setIndicator(mTabIndicator5)
                .setContent(R.id.rangebar_cheeks_tined);
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.addTab(tab4);
        tabHost.addTab(tab5);
        resetFBIndicator();
        updateTab(tabHost);
    }

    private void initBlushItem() {
        for (int i = 0; i < 8; i++) {
            LinearLayout.LayoutParams linearLp = new LinearLayout.LayoutParams(
                    40, LinearLayout.LayoutParams.MATCH_PARENT);
            final LinearLayout myLinear = new LinearLayout(this);
            myLinear.setGravity(Gravity.CENTER);
            linearLp.setMargins(27,0,27,0);
            myLinear.setTag(i);
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(getResources().getIdentifier("mode_beauty_blush_color_0" + (i+1),"drawable",getPackageName()));
            Log.d(TAG, "initBlushItem: " + getResources().getIdentifier("mode_beauty_blush_color_0" + (i+1),"drawable",getPackageName()));
            Log.d(TAG, "initBlushItem: " + getPackageName());
            myLinear.addView(imageView);
            mLinearlayoutBlush.addView(myLinear,linearLp);
            myLinear.setOnClickListener(this);
        }
        LinearLayout.LayoutParams linearLp = new LinearLayout.LayoutParams(
                40, LinearLayout.LayoutParams.MATCH_PARENT);
        final LinearLayout myLinear = new LinearLayout(this);
        myLinear.setGravity(Gravity.CENTER);
        linearLp.setMargins(27,0,27,0);
        myLinear.setTag(8);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.mode_beauty_blush_color_none);
        myLinear.addView(imageView);
        mLinearlayoutBlush.addView(myLinear,linearLp);
        myLinear.setOnClickListener(this);

    }

    private void resetBlushItem() {
        for (int i = 0; i < mLinearlayoutBlush.getChildCount(); i++) {
            mLinearlayoutBlush.getChildAt(i).setBackground(null);
        }
    }

    private void updateTab(TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++)
        {
            View view = tabHost.getTabWidget().getChildAt(i);
            if (tabHost.getCurrentTab() == i)
            {
                //选中
                view.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }
            else
            {
                //不选中
                view.setBackgroundColor(Color.parseColor("#E0E0E0"));
            }
        }
    }


    private void initEvents() {
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mBlushRangeBar.setmThumbIndex(5);
               resetFBIndicator();
            }
        });
        /*mLinearlayoutBlush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+v.getTag());
            }
        });*/
        /*mBlushRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int mThumbIndex) {
                mValue.setText(mThumbIndex + "");
                mText1.setText(mThumbIndex + "");
            }
        });*/
        mSkinSoftenedRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int mThumbIndex) {
                mValue.setText(mThumbIndex+"");
                mText2.setText(mThumbIndex + "");
            }
        });
        mSkinBrighteningRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int mThumbIndex) {
                mValue.setText(mThumbIndex+"");
                mText3.setText(mThumbIndex + "");
            }
        });
        mEyesEnhanceRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int mThumbIndex) {
                mValue.setText(mThumbIndex+"");
                mText4.setText(mThumbIndex + "");
            }
        });
        mCheeksTinedRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int mThumbIndex) {
                mValue.setText(mThumbIndex+"");
                mText5.setText(mThumbIndex + "");
            }
        });
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                /*if(tabId == "tab1"){
                    mValue.setText(mRBSmooth.getTickIndexNow() + "");
                }else{
                    mValue.setText(mRBSkinColor.getTickIndexNow() + "");
                }*/
                tabHost.setCurrentTabByTag(tabId);
                updateTab(tabHost);
                switch(tabHost.getCurrentTab()){
                    case 0:
                        mValue.setText("");
                        break;
                    case 1:
                        mValue.setText(mSkinSoftenedRangeBar.getTickIndexNow() + "");
                        break;
                    case 2:
                        mValue.setText(mSkinBrighteningRangeBar.getTickIndexNow() + "");
                        break;
                    case 3:
                        mValue.setText(mEyesEnhanceRangeBar.getTickIndexNow() + "");
                        break;
                    case 4:
                        mValue.setText(mCheeksTinedRangeBar.getTickIndexNow() + "");
                        break;
                    default:
                        break;

                }
            }
        });
    }

    private void resetFBIndicator() {
        tabHost.setCurrentTab(2);
        mSkinSoftenedRangeBar.setmThumbIndex(5);
        mSkinBrighteningRangeBar.setmThumbIndex(5);
        mEyesEnhanceRangeBar.setmThumbIndex(5);
        mCheeksTinedRangeBar.setmThumbIndex(0);
        mImageBlush.setImageResource(R.drawable.mode_beauty_blush_color_none);
        mText2.setText("5");
        mText3.setText("5");
        mText4.setText("5");
        mText5.setText("0");
        mValue.setText("5");
        mLinearlayoutBlush.getChildAt(8).setBackgroundResource(R.drawable.ic_beautification_highlight);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: " + v.getTag());
        int tag = (int) v.getTag();
        resetBlushItem();
        v.setBackground(getDrawable(R.drawable.ic_beautification_highlight));
        mImageBlush.setImageResource(getResources().getIdentifier("mode_beauty_blush_color_0" + ((int)v.getTag()+1),"drawable",getPackageName()));
        if(tag == 8){
            mImageBlush.setImageResource(R.drawable.mode_beauty_blush_color_none);
        }
    }
}
