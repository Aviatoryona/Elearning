package com.aviator.elearning;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.CourseExamModel;
import com.aviator.elearning.el.models.CourseModel;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.bumptech.glide.Glide;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.lid.lib.LabelButtonView;
import com.lid.lib.LabelImageView;
import com.rafakob.drawme.DrawMeButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import avfont.com.aviator.aviatorfontlib.AvFontHelper;

public class TutorCourseExams extends AppCompatActivity implements ShowHide {

    MyPreferences myPreferences;
    String examorcourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_exams);
        myPreferences = new MyPreferences(this);
        initViews();
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("examorcourse")) {
            examorcourse = getIntent().getStringExtra("examorcourse");
            if (examorcourse.equalsIgnoreCase("getcourses")) {
                toolbar.setTitle("My Courses");
            } else {
                toolbar.setTitle("My Exams");
            }

        }

        if (examorcourse == null) {
            Toast.makeText(this, "Error encountered, exiting...", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        AfterInit();


        LoadRecExamData(examorcourse);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void ShowMsg(String message) {
        pgCourses.setVisibility(View.GONE);

        Snackbar.make(recCourses, message, Snackbar.LENGTH_INDEFINITE).setAction("Reload", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert examorcourse != null;
                LoadRecExamData(examorcourse);
            }
        }).show();
    }

    @Override
    public void HideMsg() {
        if (pgCourses.getVisibility() == View.VISIBLE) {
            pgCourses.setVisibility(View.GONE);
        } else {
            pgCourses.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void Pause_() {

    }

    @Override
    public void Resume_() {

    }

    @Override
    public void AfterInit() {


    }


    void LoadRecExamData(String examorcourse) {
        Map<String, String> paMap = new HashMap<>();
        paMap.put("target", "tutor");
        paMap.put("action", examorcourse);
        paMap.put("usertype", String.valueOf(1));
        paMap.put("email", myPreferences.getEmail());

        LoadRecCoursesExamsData(paMap);
    }


    public void LoadRecCoursesExamsData(final Map<String, String> paStringMap) {
        HideMsg();
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                requestQueue.stop();
                ParseJsonsHelper tc = new ParseJsonsHelper(TutorCourseExams.this);
                HideMsg();

                pgCourses.setVisibility(View.GONE);
                if (examorcourse.equalsIgnoreCase("getcourses")) {
                    ArrayList<CourseModel> arrayList = tc.getCourseData(s);
                    if (arrayList != null) {
                        recCourses.setAdapter(new CourseAdapter(arrayList));
                    } else {
                        MessageModel messageModel = tc.getMessageModel(s);
                        if (messageModel != null) {
                            ShowMsg(messageModel.getMessage());
                        } else {
                            ShowMsg(s);
                        }
                    }
                    return;
                }
                if (examorcourse.equalsIgnoreCase("getexams")) {
                    ArrayList<CourseExamModel> arrayList = tc.getCourseExamModel(s);
                    if (arrayList != null) {

                        recCourses.setAdapter(new ExamAdapter(arrayList));

                    } else {
                        MessageModel messageModel = tc.getMessageModel(s);
                        if (messageModel != null) {
                            ShowMsg(messageModel.getMessage());
                        } else {
                            ShowMsg(s);
                        }
                    }
                }


            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                requestQueue.stop();
                ShowMsg("Internet Connection Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return paStringMap;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    class CourseAdapter extends BaseAdapter {
        private ArrayList<CourseModel> courseExamModels;

        CourseAdapter(ArrayList<CourseModel> courseExamModels) {
            this.courseExamModels = courseExamModels;
        }

        @Override
        public int getCount() {
            return courseExamModels.size();
        }

        @Override
        public Object getItem(int position) {
            return courseExamModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View itemView, ViewGroup parent) {
            CourseModel courseExamModel = (CourseModel) getItem(position);
            LabelImageView coverImage;
            DrawMeButton btnTitle;
            TextView txtTitle, btnStart;
            if (itemView == null) {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.courserecmodel, parent, false);
            }

            coverImage = itemView.findViewById(R.id.coverImg);
            btnStart = itemView.findViewById(R.id.btnStart);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            btnTitle = itemView.findViewById(R.id.btnTitle);

            btnTitle.setTypeface(AvFontHelper.nucleooutline(TutorCourseExams.this));
            txtTitle.setTypeface(AvFontHelper.nucleooutline(TutorCourseExams.this));

            btnStart.setBackground(new ColorDrawable(getResources().getColor(R.color.dark_red)));
            btnStart.setText("View");
            String url = new SpaceCraft().getUrl(TutorCourseExams.this) + "courses/" + courseExamModel.getC_coverimg();
            Glide.with(TutorCourseExams.this).load(Uri.parse(url)).placeholder(R.mipmap.bgblck).into(coverImage);
            return itemView;
        }

    }

    class ExamAdapter extends BaseAdapter {
        private ArrayList<CourseExamModel> courseExamModels;

        ExamAdapter(ArrayList<CourseExamModel> courseExamModels) {
            this.courseExamModels = courseExamModels;
        }

        @Override
        public int getCount() {
            return courseExamModels.size();
        }

        @Override
        public Object getItem(int position) {
            return courseExamModels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View itemView, ViewGroup parent) {
            CourseExamModel courseExamModel = (CourseExamModel) getItem(position);
            LabelButtonView titleBtn;
            ImageView coverImg, imgMore;
            TextView txtExplore;
            if (itemView == null) {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.examrecmodel, parent, false);
            }

            titleBtn = itemView.findViewById(R.id.titleBtn);
            coverImg = itemView.findViewById(R.id.coverImg);
            imgMore = itemView.findViewById(R.id.imgMore);
            txtExplore = itemView.findViewById(R.id.txtExplore);

            titleBtn.setText(courseExamModel.getCourseModel().getC_name());
            String url = new SpaceCraft().getUrl(TutorCourseExams.this) + "exams/" + courseExamModel.getExamModel().getE_coverimg();
            Glide.with(TutorCourseExams.this).load(Uri.parse(url)).placeholder(R.mipmap.bgblck).into(coverImg);
            return itemView;
        }

    }


    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefresh;
    private ListView recCourses;
    private LinearLayout refreshLy;
    private TextView txtMsg;
    private GoogleProgressBar pgCourses;
    private FloatingActionButton fab;

    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        recCourses = (ListView) findViewById(R.id.recCourses);
        refreshLy = (LinearLayout) findViewById(R.id.refreshLy);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        pgCourses = (GoogleProgressBar) findViewById(R.id.pgCourses);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }


}
