package com.aviator.elearning;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.adapter.ListExpandableTopicSubTopicAdapte;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.models.SubtopicsModel;
import com.aviator.elearning.el.models.TopicSubtopicModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.bumptech.glide.Glide;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@SuppressWarnings("ALL")
public class ExploreCourse extends AppCompatActivity implements ShowHide {

    String COVER, COURSEID, COURSE;
    MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_course);
        myPreferences = new MyPreferences(this);
        initViews();
        collapseLayout.setTitleEnabled(false);
        setSupportActionBar(toolbar);

        AfterInit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (stack != null) {
            if (stack.isEmpty()) {
                LoadCourseData(this);
            }
        }else{
            stack=new Stack<>();
            if (stack.isEmpty()) {
                LoadCourseData(this);
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stack.clear();
        stack = null;
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Pause_();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Resume_();
    }

    @Override
    public void ShowMsg(String message) {

    }

    @Override
    public void HideMsg() {

    }

    @Override
    public void Pause_() {
        if (stack == null) {
            return;
        }
        stack.clear();
        if (listView.getExpandableListAdapter() instanceof ListExpandableTopicSubTopicAdapte) {
            stack.push(((ListExpandableTopicSubTopicAdapte) listView.getExpandableListAdapter()).getTopicsModel());
        }
    }

    @Override
    public void Resume_() {
        if (stack != null) {
            if (!stack.isEmpty()) {
                listView.setAdapter(new ListExpandableTopicSubTopicAdapte(stack.pop(), ExploreCourse.this));
            }
        }
    }

    @Override
    public void AfterInit() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("COURSEID")) {
                COURSEID = bundle.getString("COURSEID");
            }
            if (bundle.containsKey("COURSE")) {
                COURSE = bundle.getString("COURSE");
                txtTitle.setText(COURSE);
            }
            if (bundle.containsKey("COVER")) {
                COVER = bundle.getString("COVER");
                String url = url = new SpaceCraft().getUrl(ExploreCourse.this) + "courses/" + COVER;
                Glide.with(ExploreCourse.this).load(Uri.parse(url)).placeholder(R.mipmap.bgblue).into(imgImg);
            }
        }
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Explore content
                ListExpandableTopicSubTopicAdapte listExpandableTopicSubTopicAdapte = (ListExpandableTopicSubTopicAdapte) parent.getExpandableListAdapter();
                assert listExpandableTopicSubTopicAdapte != null;
                SubtopicsModel subtopicsModel = (SubtopicsModel) listExpandableTopicSubTopicAdapte.getChild(groupPosition, childPosition);
                if (subtopicsModel != null) {
                    Intent intent = new Intent(ExploreCourse.this, ExploreCourseContent.class);
                    intent.putExtra("tt", subtopicsModel.getCst_subtopic());
                    intent.putExtra("c", subtopicsModel.getCst_content());
                    startActivity(intent);
                } else {
                    TastyToast.makeText(ExploreCourse.this, "No content", TastyToast.LENGTH_LONG
                            , TastyToast.INFO).show();
                }
                return true;
            }
        });
        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddStudentCourse();
            }
        });
    }

    void AddStudentCourse() {
        Map<String, String> map = new HashMap<>();
        map.put("target", "student");
        map.put("action", "addcourse");
        map.put("usertype", String.valueOf(0));
        map.put("name", "");
        map.put("uniqid", COURSEID);//courseModel.getC_uniqid()
        map.put("email", myPreferences.getEmail());
        AddStudentCourseDb(map);
    }

    private void AddStudentCourseDb(final Map<String, String> params) {
        pgPg.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(ExploreCourse.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(ExploreCourse.this),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestQueue.stop();
                        pgPg.setVisibility(View.GONE);

                        ParseJsonsHelper parseJsonsHelper = new ParseJsonsHelper(ExploreCourse.this);
                        MessageModel messageModel = parseJsonsHelper.getMessageModel(s);
                        if (messageModel != null) {
                            if (!messageModel.isError()) {
                                TastyToast.makeText(ExploreCourse.this, messageModel.getMessage(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
//
//                                Intent intent = new Intent(ExploreCourse.this, StartExam.class);
//                                intent.putExtras(mBundle);
//                                startActivity(intent);

                            } else {
                                Snackbar.make(parent, messageModel.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                            return;
                        }

                        Snackbar.make(parent, "Error encountered", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AddStudentCourseDb(params);
                            }
                        }).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                pgPg.setVisibility(View.GONE);
                Snackbar.make(parent, SpaceCraft.CONNECTION_ERROR_MSG, Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddStudentCourseDb(params);
                    }
                }).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    private void LoadCourseData(final Context context) {
        pgPg.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(context), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();
                ParseJsonsHelper parseJsonsHelper = new ParseJsonsHelper(ExploreCourse.this);
                ArrayList<TopicSubtopicModel> topicsModels = parseJsonsHelper.getTopicSubtopicModels(s);
                pgPg.setVisibility(View.GONE);
                if (topicsModels != null) {
                    listView.setAdapter(new ListExpandableTopicSubTopicAdapte(topicsModels, ExploreCourse.this));
                } else {
                    MessageModel messageModel = parseJsonsHelper.getMessageModel(s);
                    if (messageModel != null) {
                        Snackbar.make(collapseLayout, messageModel.getMessage(), Snackbar.LENGTH_INDEFINITE)
                                .setAction("Reload", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        LoadCourseData(context);
                                    }
                                }).show();
                    } else {
                        Snackbar.make(collapseLayout, s, Snackbar.LENGTH_INDEFINITE)
                                .setAction("Reload", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        LoadCourseData(context);
                                    }
                                }).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                pgPg.setVisibility(View.GONE);
                Snackbar.make(collapseLayout, SpaceCraft.CONNECTION_ERROR_MSG, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reload", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LoadCourseData(context);
                            }
                        }).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("target", "courses");
//                map.put("action", "gettopics");
                map.put("action", "gettopics_subtopics");
                assert COURSEID != null;
                map.put("courseuniqid", COURSEID);
                return map;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    private CoordinatorLayout parent;
    private CollapsingToolbarLayout collapseLayout;
    private ImageView imgImg;
    private Toolbar toolbar;
    private TextView txtTitle;
    private ImageView imgFavourite;
    private ExpandableListView listView;
    private GoogleProgressBar pgPg;
    private FloatingActionButton fab;

    public void initViews() {
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        collapseLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseLayout);
        imgImg = (ImageView) findViewById(R.id.imgImg);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        imgFavourite = (ImageView) findViewById(R.id.imgFavourite);
        listView = (ExpandableListView) findViewById(R.id.listView);
        pgPg = (GoogleProgressBar) findViewById(R.id.pgPg);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    static Stack<ArrayList<TopicSubtopicModel>> stack = new Stack<>();
}
