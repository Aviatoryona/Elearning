package com.aviator.elearning.el.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aviator.elearning.el.frags.TutorFragCourse;
import com.aviator.elearning.el.frags.TutorFragExam;
import com.aviator.elearning.el.frags.TutorFragHome;

public class TutorPagerAdapter extends FragmentPagerAdapter {
    public TutorPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new TutorFragHome();
            }
            case 1: {
                return new TutorFragCourse();
            }
            case 2: {
                return new TutorFragExam();
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
