package com.yanxinwei.slidingbottommenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int MESSAGE_1 = 0;
    private static final int MESSAGE_2 = 1;
    private static final int MESSAGE_3 = 2;
    private static final int MESSAGE_4 = 3;

    private static final int MENU_COUNT = 4;

    private static final float MIN_ALPHA = 0.2f;

    private int mCurrentIndex = 0;
    //用于临时存储选中的index,因为 onPageSelected 方法会在多个onPageScrolled方法中间调用
    private int mTmpIndex = 0;

    //标志是否第一次进入
//    private boolean isFirstLaunch = false;
    //标志是否开始滑动
    private boolean isScrolledStart = false;

    private TextView[] mTxtMenus = new TextView[MENU_COUNT];
    private ViewPager mViewPager;

    private MessageFragment[] mFragments = new MessageFragment[MENU_COUNT];

    private int[] ids = {R.id.txt_message1, R.id.txt_message2, R.id.txt_message3, R.id.txt_message4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        for (int i = 0; i < ids.length; i++) {
            mTxtMenus[i] = (TextView) findViewById(ids[i]);
            mFragments[i] = MessageFragment.newInstance(i + 1);
        }
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(MENU_COUNT);
        mViewPager.setAdapter(new PageMenuAdapter(getSupportFragmentManager()));

        mTxtMenus[0].setSelected(true);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "@@@@ onPageScrolled position:" + position + "  positionOffset:" + positionOffset +
                "  positionOffsetPixels:" + positionOffsetPixels);
        //第一次初始化会默认进一次以及当滑动到左边和右边边界时也为0,这些情况都忽略掉
        if (positionOffset != 0) {
            if (isScrolledStart) {
                isScrolledStart = false;
                initTargetMenu(position);
            } else {
                updateTransitionAlpha(position, positionOffset);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "@@@@ onPageSelected position:" + position);
        setMenuSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "@@@@ onPageScrollStateChanged state:" + state);
        //等停下来才把临时index赋值给当前选中的index
        if (state == 0) {
            mCurrentIndex = mTmpIndex;
            setMenuSelected(mCurrentIndex);
        } else if (state == 1) {
            isScrolledStart = true;
        }
    }

    private void setMenuSelected(int index) {
        for (int i = 0; i < MENU_COUNT; i++) {
            if (i == index) {
                setTextSelected(index, true);
            } else {
                setTextSelected(i, false);
            }
            setTextAlpha(i, 1);
        }
        //设置临时index
        mTmpIndex = index;
    }

    private void setTextSelected(int index, boolean isSelected) {
        mTxtMenus[index].setSelected(isSelected);
    }

    private void setTextAlpha(int index, float alpha) {
        mTxtMenus[index].setAlpha(alpha);
    }

    private void initTargetMenu(int position) {
        int targetIndex = getTargetIndex(position);
        if (targetIndex != -1) {
            setTextSelected(targetIndex, true);
            setTextAlpha(targetIndex, MIN_ALPHA);
        }
    }

    private int getTargetIndex(int position) {
        int targetIndex = -1;
        if (mCurrentIndex == position) {
            targetIndex = position + 1;
        } else if (position == mCurrentIndex - 1) {
            targetIndex = position;
        }
        if (targetIndex >= 0 && targetIndex < MENU_COUNT) {
            return targetIndex;
        } else {
            Log.e(TAG, "#### illegal index position:"+position + "  targetIndex:"+targetIndex +
                    "  mCurrentIndex:"+mCurrentIndex);
            return -1;
        }
    }

    private void updateTransitionAlpha(int position, float offset) {
        int targetPosition = getTargetIndex(position);
        if (targetPosition != -1) {
            setTextAlpha(mCurrentIndex, offsetConvertAlpha(1 - offset));
            setTextAlpha(targetPosition, offsetConvertAlpha(offset));
        }
    }

    private float offsetConvertAlpha(float offset) {
        return MIN_ALPHA + offset * 0.8f;
    }

    class PageMenuAdapter extends FragmentPagerAdapter {


        public PageMenuAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }
}
