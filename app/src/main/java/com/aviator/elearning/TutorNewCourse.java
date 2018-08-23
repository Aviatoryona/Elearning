package com.aviator.elearning;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.CommonsPkg.TutorCourseExamCommon;
import com.aviator.elearning.el.frags.TutorNewCourseSubTopic;
import com.aviator.elearning.el.frags.TutorNewCourseTopic;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.models.TopicsModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.rafakob.drawme.DrawMeButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import avfont.com.aviator.aviatorfontlib.AvFontHelper;

@SuppressWarnings("ALL")
public class TutorNewCourse extends TutorCourseExamCommon {

    static String UNIQID, COURSENAME;
   public static TutorNewCourse tutorNewCourse;
    TutorNewCourseTopic tutorNewCourseTopic;
    TutorNewCourseSubTopic tutorNewCourseSubTopic;
    MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_new_course);

        myPreferences = new MyPreferences(TutorNewCourse.this);
        tutorNewCourse = this;

        initViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra("UNIQID")) {
            UNIQID = getIntent().getStringExtra("UNIQID");
        }
        if (getIntent().hasExtra("NAME")) {
            COURSENAME = getIntent().getStringExtra("NAME");
        }
        //
        AfterInit();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Toast.makeText(TutorNewCourse.this, "Please wait", Toast.LENGTH_SHORT).show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (UNIQID == null) {
//                    DialogNewCE();
//                }
//            }
//        }, 3000);

    }

   public void MoveToPage(int page) {
        tutorNewCourse.viewPager.setCurrentItem(page);
    }

    void DialogNewCE() {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(TutorNewCourse.this);
        aBuilder.setCancelable(false);

        View view = CustomViews.customEditText(TutorNewCourse.this);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        txtTitle.setTypeface(AvFontHelper.RobotoBold(TutorNewCourse.this));
        txtTitle.setText("New Course");
        DrawMeButton btnNext = view.findViewById(R.id.btnNxt);
        final EditText edtName = view.findViewById(R.id.edtName);

        aBuilder.setView(view);
        aBuilder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        final AlertDialog alertDialog = aBuilder.create();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtName.getText().toString())) {
                    edtName.setError("Required *");
                    return;
                }

//                if (type == SpaceCraft.NEW_EXAM) {
//                    Intent intent = new Intent(TutorDashboard.this, TutorNewExam.class);
//                    intent.putExtra(SpaceCraft.STRING_DATA, edtName.getText().toString());
//                    startActivity(intent);
//                    return;
//                }


//                alertDialog.dismiss();
                final String uniqid = String.format("EL-%s", new Date().getTime());
                Map<String, String> params = new HashMap<>();
                params.put("target", "tutor");
                params.put("action", "addcourse");
                params.put("usertype", String.valueOf(1));
                params.put("uniqid", uniqid);
                params.put("name", edtName.getText().toString());
                params.put("email", myPreferences.getEmail());

                AddCourseExamDb(params);

            }
        });


        alertDialog.show();
    }

    private void AddCourseExamDb(final Map<String, String> params) {
        blanket.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(TutorNewCourse.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                new SpaceCraft().getUrl(TutorNewCourse.this),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestQueue.stop();
                        blanket.setVisibility(View.GONE);

                        MessageModel messageModel = new ParseJsonsHelper(TutorNewCourse.this).getMessageModel(s);
                        if (messageModel != null) {

                            if (!messageModel.isError()) {


                                UNIQID = params.get("uniqid");
                                COURSENAME = params.get("name");

                                tutorNewCourse.UNIQID = UNIQID;
                                tutorNewCourse.COURSENAME = COURSENAME;
//                                Intent intent;
//                                if (whatNext == SpaceCraft.NEW_COURSE) {
//                                    intent = new Intent(TutorDashboard.this, TutorNewCourse.class);
//                                    intent.putExtra("UNIQID", params.get("uniqid"));
//                                    intent.putExtra("NAME", params.get("name"));
//                                    startActivity(intent);
//
//                                }
                                return;
                            } else {
                                Snackbar.make(viewPager, s, Snackbar.LENGTH_LONG).setAction(messageModel.getMessage(), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AddCourseExamDb(params);
                                    }
                                }).show();
                            }
                            return;
                        }

                        Snackbar.make(viewPager, s, Snackbar.LENGTH_LONG).setAction("Try again", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AddCourseExamDb(params);
                            }
                        }).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                blanket.setVisibility(View.GONE);
                Snackbar.make(viewPager, "Internet Connection Error", Snackbar.LENGTH_LONG).setAction("Try again", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddCourseExamDb(params);
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

    public void AddTopicDb(final Map<String, String> paStringStringMap) {
        AddCourseTopic_DB(paStringStringMap);
    }

    public static TutorNewCourse getInstance() {
        return tutorNewCourse;
    }

    public void SyncTopics(ArrayList<TopicsModel> topicsModels) {
        assert tutorNewCourseTopic != null;
        tutorNewCourseTopic.SyncTopics(topicsModels);
    }

    @Override
    public void Pause_() {

    }

    @Override
    public void Resume_() {

    }

    @Override
    public void AfterInit() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                if (position == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("UNIQID", UNIQID);
                    bundle.putString("NAME", COURSENAME);
                    tutorNewCourseTopic = new TutorNewCourseTopic();
                    tutorNewCourseTopic.setArguments(bundle);
                    return tutorNewCourseTopic;
                }
                if (position == 1) {
                    tutorNewCourseSubTopic = new TutorNewCourseSubTopic();///sub topic content
                    return tutorNewCourseSubTopic;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 1;
            }
        });

        TabLayout.TabLayoutOnPageChangeListener onPageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        TabLayout.ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener = new TabLayout.ViewPagerOnTabSelectedListener(viewPager);
        tabLayout.addOnTabSelectedListener(viewPagerOnTabSelectedListener);
    }

    public CoordinatorLayout parent;
    public Toolbar toolbar;
    public TabLayout tabLayout;
    public TabItem tabItem1;
//    public TabItem tabItem2;
    public ViewPager viewPager;
    public ProgressBar pgPg;
    public RelativeLayout blanket;
    public GoogleProgressBar pgBlanket;

    public void initViews() {
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabItem1 = (TabItem) findViewById(R.id.tabItem1);
//        tabItem2 = (TabItem) findViewById(R.id.tabItem2);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pgPg = (ProgressBar) findViewById(R.id.pgPg);
        blanket = (RelativeLayout) findViewById(R.id.blanket);
        pgBlanket = (GoogleProgressBar) findViewById(R.id.pgBlanket);
    }


}
