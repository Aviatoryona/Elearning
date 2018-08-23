package com.aviator.elearning;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviator.elearning.aviator.main.CustomFonts;
import com.aviator.elearning.el.frags.VideoFragment;

public class VideoTutorials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_tutorials);
        initViews();
        collapseLayout.setTitleEnabled(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitle.setTypeface(CustomFonts.Reckoner_Bold(this));
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new VideoFragment();
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
    }


    private AppBarLayout appBarLy;
    private CollapsingToolbarLayout collapseLayout;
    private ImageView imgImg;
    private Toolbar toolbar;
    private TextView txtTitle;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    public void initViews() {
        appBarLy = (AppBarLayout) findViewById(R.id.appBarLy);
        collapseLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseLayout);
        imgImg = (ImageView) findViewById(R.id.imgImg);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }
}
