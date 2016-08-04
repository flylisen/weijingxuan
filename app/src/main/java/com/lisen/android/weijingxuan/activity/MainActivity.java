package com.lisen.android.weijingxuan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lisen.android.weijingxuan.R;
import com.lisen.android.weijingxuan.adapter.NewsFragmentAdapter;
import com.lisen.android.weijingxuan.bean.ChanelItem;
import com.lisen.android.weijingxuan.bean.ChanelManage;
import com.lisen.android.weijingxuan.fragment.NewsFragment;
import com.lisen.android.weijingxuan.fragment.TestFragment;
import com.lisen.android.weijingxuan.util.MyUtils;
import com.lisen.android.weijingxuan.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {


    private ViewPagerIndicator mIndicator;
    /**
     * 当前选中的tab
     */
    private int mCurrentIndex = 0;
    private int mItemWidth;
    private LinearLayout mRadioGroupContent;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private List<ChanelItem> mUserChanels;
    public final static int CHANEL_REQUEST = 1;
    public final static int CHANEL_RESULT = 3;
    private NewsFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }


    /**
     * 初始化整体视图
     */
    private void initView() {
        mIndicator = (ViewPagerIndicator) findViewById(R.id.view_pager_indicator);
        mItemWidth = MyUtils.getScreenWidth(this) / 5;
        mRadioGroupContent = (LinearLayout) findViewById(R.id.mRadioGroup_content);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        LinearLayout llMoreColums = (LinearLayout) findViewById(R.id.ll_more_columns);

        llMoreColums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChannelActivity.class);
                //  mAdapter.setDestroyType(true);
                startActivityForResult(intent, CHANEL_REQUEST);

            }
        });
        setChangeView();
    }

    private void setChangeView() {
        initColumnData();
        initColumnView();
        initFragments();
    }

    /**
     * 初始化栏目数据
     */

    private void initColumnData() {

        mUserChanels = ChanelManage.getChanelManage().getUserChanel();
    }

    /**
     * 初始化fragment
     */
    private void initFragments() {
        mFragments.clear();
        int count = mUserChanels.size();
        boolean flag = false;
        for (int i = 0; i < count; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("chanel_id", mUserChanels.get(i).getId());
            bundle.putString("chanel_name", mUserChanels.get(i).getName());

            NewsFragment newsFragment = new NewsFragment();
            newsFragment.setArguments(bundle);
            mFragments.add(newsFragment);

           /* TestFragment fragment = new TestFragment();
            fragment.setArguments(bundle);
            mFragments.add(fragment);*/
        }


        mAdapter = new NewsFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
                selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void selectTab(int position) {
        mCurrentIndex = position;
        for (int i = 0; i < mRadioGroupContent.getChildCount(); i++) {
            View checkView = mRadioGroupContent.getChildAt(position);
            int l = checkView.getLeft();
            mIndicator.smoothScrollTo(l, 0);
        }

        for (int j = 0; j < mRadioGroupContent.getChildCount(); j++) {
            TextView checkView = (TextView) mRadioGroupContent.getChildAt(j);
            boolean isChecked;
            if (j == position) {
                isChecked = true;
                checkView.setTextColor(Color.GREEN);

            } else {
                isChecked = false;

                checkView.setTextColor(Color.BLACK);
            }

            checkView.setSelected(isChecked);
        }
    }

    /**
     * 初始化栏目视图
     */

    private void initColumnView() {
        mRadioGroupContent.removeAllViews();
        mIndicator.smoothScrollTo(0, 0);
        mCurrentIndex = 0;
        for (int i = 0; i < mUserChanels.size(); i++) {
            final TextView textView = new TextView(this);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(mItemWidth,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            p.leftMargin = 10;
            p.rightMargin = 10;
            //L.d("TAG", "p.width=" + p.width);
            final String columName = mUserChanels.get(i).getName();
            // Log.d("TAG", "colimName=" + columName);
            textView.setText(columName);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setTextColor(Color.BLACK);
            if (i == 0) {
                textView.setTextColor(Color.GREEN);
            }
            if (mCurrentIndex == i) {
                textView.setSelected(true);
                textView.setTextColor(Color.GREEN);
            }

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mRadioGroupContent.getChildCount(); i++) {
                        TextView localView = (TextView) mRadioGroupContent.getChildAt(i);
                        if (localView != v) {
                            localView.setSelected(false);
                            localView.setTextColor(Color.BLACK);
                        } else {
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                            localView.setTextColor(Color.GREEN);
                        }
                    }
                }
            });
            mRadioGroupContent.addView(textView, i, p);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHANEL_REQUEST:
                if (resultCode == CHANEL_RESULT) {
                    setChangeView();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRadioGroupContent.post(new Runnable() {
            @Override
            public void run() {
                Log.d("Width", "mRadioContent width = " + mRadioGroupContent.getMeasuredWidth());
            }
        });
        mIndicator.post(new Runnable() {
            @Override
            public void run() {
                Log.d("Width", "scroll width = " + mIndicator.getMeasuredWidth());
            }
        });
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter = null;
    }
}
