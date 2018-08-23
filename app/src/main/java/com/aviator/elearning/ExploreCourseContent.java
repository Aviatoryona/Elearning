package com.aviator.elearning;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviator.elearning.aviator.main.CustomFonts;

import avfont.com.aviator.aviatorfontlib.AvFonts;

public class ExploreCourseContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_course_content);
        initViews();
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtContent.setTypeface(AvFonts.RobotoThin(this));
        txtTitle.setTypeface(CustomFonts.Reckoner_Bold(this));
        if (getIntent().hasExtra("tt")){
            txtTitle.setText(getIntent().getStringExtra("tt"));
        }
        if (getIntent().hasExtra("c")){
            txtContent.setText(getIntent().getStringExtra("c"));
        }
    }


    private CoordinatorLayout parent;
    private CollapsingToolbarLayout collapseLayout;
    private ImageView imgImg;
    private Toolbar toolbar;
    private TextView txtTitle;
    private TextView txtContent;
    private FloatingActionButton fab;

    public void initViews() {
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        collapseLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseLayout);
        imgImg = (ImageView) findViewById(R.id.imgImg);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtContent = (TextView) findViewById(R.id.txtContent);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

}
