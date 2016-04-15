package com.yanxinwei.slidingbottommenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int MESSAGE_1 = 0;
    private static final int MESSAGE_2 = 1;
    private static final int MESSAGE_3 = 2;
    private static final int MESSAGE_4 = 3;

    private static final int MENU_COUNT = 4;

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
        for (int i = 0; i < ids.length; i++){
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
        Log.d(TAG, "@@@@ onPageScrolled position:"+position + "  positionOffset:"+positionOffset +
                "  positionOffsetPixels:"+positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "@@@@ onPageSelected position:"+position);
        setMenuSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "@@@@ onPageScrollStateChanged state:"+state);
    }

    private void setMenuSelected(int index) {

        for (int i = 0; i < MENU_COUNT; i++) {
            if (i == index) {
                mTxtMenus[index].setSelected(true);
            }else {
                mTxtMenus[i].setSelected(false);
            }
        }
    }

    class PageMenuAdapter extends FragmentPagerAdapter{


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
