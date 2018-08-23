package com.aviator.elearning;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aviator.elearning.aviator.main.FetchData_Activity;
import com.aviator.elearning.aviator.main.MyDecor;
import com.aviator.elearning.el.adapter.RecCoursesAdapter;
import com.aviator.elearning.el.models.CourseModel;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;
import java.util.Stack;

@SuppressWarnings("ALL")
public class Courses extends FetchData_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        initViews();
        if (stack==null){
            stack=new Stack<>();
        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AfterInit();
    }

    @Override
    public void AfterInit() {
        recCourses.setItemAnimator(new DefaultItemAnimator());
        recCourses.addItemDecoration(new MyDecor(5));
        recCourses.setLayoutManager(
                new LinearLayoutManager(Courses.this, LinearLayoutManager.VERTICAL, false)

//                new GridLayoutManager(Courses.this, 2)
        );

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pgCourses.setVisibility(View.GONE);
                        LoadRecCoursesData();
                    }
                }, 3000);
            }
        });

        if (stack.isEmpty()) {
            LoadRecCoursesData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Pause_();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Resume_();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Resume_();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Pause_();
    }

    @Override
    public void ShowMsg(String message) {

    }

    @Override
    public void HideMsg() {

    }

    @Override
    public void Pause_() {
        stack.clear();
        if (recCourses.getAdapter() != null) {
            if (recCourses.getAdapter() instanceof RecCoursesAdapter) {
                stack.push((ArrayList<CourseModel>) ((RecCoursesAdapter) recCourses.getAdapter()).getCourseModelArrayList());
            }
        }
    }

    @Override
    public void Resume_() {

        if (stack.isEmpty()) {
            LoadRecExamsData();
            return;
        }

        ArrayList<CourseModel> models = stack.pop();
        if (models != null) {
            recCourses.setAdapter(new RecCoursesAdapter(Courses.this, models, Courses.this, null));
        } else {
            LoadRecExamsData();
        }

    }

    public CoordinatorLayout parent;
    private Toolbar toolbar;
    public SwipeRefreshLayout swipeRefresh;
    public RecyclerView recCourses;
    public GoogleProgressBar pgCourses;
    private FloatingActionButton fab;

    public void initViews() {
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        recCourses = (RecyclerView) findViewById(R.id.recCourses);
        pgCourses = (GoogleProgressBar) findViewById(R.id.pgCourses);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    static Stack<ArrayList<CourseModel>> stack ;//= new Stack<>();
}
