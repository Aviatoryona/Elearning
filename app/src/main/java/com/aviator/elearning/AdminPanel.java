package com.aviator.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aviator.elearning.aviator.main.CustomFonts;
import com.aviator.elearning.el.adapter.StringArraylistAdapter;
import com.aviator.elearning.el.interfaces.ShowHide;

import java.util.ArrayList;

public class AdminPanel extends AppCompatActivity implements ShowHide {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        initViews();
        collapseLayout.setTitleEnabled(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        AfterInit();
    }

    @Override
    public void ShowMsg(String message) {

    }

    @Override
    public void HideMsg() {

    }

    @Override
    public void Pause_() {

    }

    @Override
    public void Resume_() {

    }


    @Override
    public void AfterInit() {

        txtTitle.setTypeface(CustomFonts.LoveYaLikeASister(this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdminPanel.this, AdminManage.class);
                switch (position) {
                    case 0: {
                        intent.putExtra("what", "users");
                        break;
                    }
                    case 1: {
                        intent.putExtra("what", "courses");
                        break;
                    }

                    case 2: {
                        intent.putExtra("what", "exams");
                        break;
                    }
                }

                startActivity(intent);
            }
        });

        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Manage Users");
        stringArrayList.add("Manage Courses");
        stringArrayList.add("Manage Exams");

        listView.setAdapter(new StringArraylistAdapter(stringArrayList, this));

    }

    private CoordinatorLayout parent;
    private CollapsingToolbarLayout collapseLayout;
    private ImageView imgImg;
    private Toolbar toolbar;
    private TextView txtTitle;
    private ListView listView;
    private FloatingActionButton fab;

    public void initViews() {
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        collapseLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseLayout);
        imgImg = (ImageView) findViewById(R.id.imgImg);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        listView = (ListView) findViewById(R.id.listView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }


}
