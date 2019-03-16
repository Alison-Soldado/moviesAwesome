package com.example.detail.ui;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.detail.ui.review.ReviewFragment;
import com.example.detail.ui.trailer.TrailerFragment;

public class DetailAdapter extends FragmentPagerAdapter {

    private double id;

    DetailAdapter(FragmentManager fragmentManager, double id) {
        super(fragmentManager);
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TrailerFragment.newInstance(id);
            case 1:
                return ReviewFragment.newInstance(id);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) { return position == 0 ? "Trailer" : "Review"; }
}
