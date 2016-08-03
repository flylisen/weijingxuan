package com.lisen.android.weijingxuan.adapter;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/28.
 */
public class NewsFragmentAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fm;
    private ArrayList<Fragment> fragments;
    public NewsFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        fragments = new ArrayList<>();
    }

    public NewsFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);

       /* fm.beginTransaction().show(fragment).commit();*/
        return fragment;
    }

    public void setFragments(ArrayList<Fragment> fs) {
        if (this.fragments != null) {
            android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                transaction.remove(f);
            }
            transaction.commit();
            transaction = null;
            fm.executePendingTransactions();
        }
        this.fragments = fs;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {


        /*if (destroyItem) {
            super.destroyItem(container, position, object);
            return;
        }
        Fragment fragment = fragments.get(position);
        fm.beginTransaction().hide(fragment).commit();*/
    }
    private boolean destroyItem = false;

    public void setDestroyType(boolean b) {
        destroyItem = b;
    }
}
