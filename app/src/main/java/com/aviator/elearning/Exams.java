package com.aviator.elearning;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aviator.elearning.aviator.main.FetchData_Activity;
import com.aviator.elearning.aviator.main.MyDecor;
import com.aviator.elearning.el.adapter.RecExamsAdapter_Main;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.CourseExamModel;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;
import java.util.Stack;

@SuppressWarnings("ALL")
public class Exams extends FetchData_Activity implements ShowHide {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        initViews();
        if (stack == null) {
            stack = new Stack<>();
        }
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
        recExams.setItemAnimator(new DefaultItemAnimator());
        recExams.addItemDecoration(new MyDecor(5));
        recExams.setLayoutManager(new LinearLayoutManager(Exams.this, LinearLayoutManager.VERTICAL, false));

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pgExams.setVisibility(View.GONE);
                        LoadRecExamsData();
                    }
                }, 3000);
            }
        });


        if (stack.isEmpty()) {
            LoadRecExamsData();
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
        if (recExams.getAdapter() != null) {
            if (recExams.getAdapter() instanceof  RecExamsAdapter_Main
//                    RecExamsAdapter
                    ) {
//                stack.push((ArrayList<ExamModel>) ((RecExamsAdapter) recExams.getAdapter()).getExamModelArrayList());
                stack.push(((RecExamsAdapter_Main) recExams.getAdapter()).getCourseExamModels());
            }
        }
    }

    @Override
    public void Resume_() {

        if (stack.isEmpty()) {
            LoadRecExamsData();
            return;
        }

//        ArrayList<ExamModel> models = stack.pop();
        ArrayList<CourseExamModel> models = stack.pop();
        if (models != null) {
//            RecExamsAdapter
            recExams.setAdapter(new RecExamsAdapter_Main(models, Exams.this, Exams.this, null));
        } else {
            LoadRecExamsData();
        }

    }

    public CoordinatorLayout parent;
    private Toolbar toolbar;
    public SwipeRefreshLayout swipeRefresh;
    public RecyclerView recExams;
    public GoogleProgressBar pgExams;
    private FloatingActionButton fab;

    public void initViews() {
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        recExams = (RecyclerView) findViewById(R.id.recExams);
        pgExams = (GoogleProgressBar) findViewById(R.id.pgExams);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    //    static Stack<ArrayList<ExamModel>> stack ;
    static Stack<ArrayList<CourseExamModel>> stack;
}
