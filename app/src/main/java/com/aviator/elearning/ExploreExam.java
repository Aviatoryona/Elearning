package com.aviator.elearning;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.aviator.main.CustomFonts;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.HttpLoadData;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.ExamQAModel;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.models.NetParamsModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.bumptech.glide.Glide;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.rafakob.drawme.DrawMeButton;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExploreExam extends AppCompatActivity implements ShowHide {

    static Bundle bundle;
    Bundle mBundle;
    //    public static ExamModel examModel;
//    public static CourseModel courseModel;
    static String COVER, COURSE, COURSEUNIQID, EXAMUINQID;
    static double DURATION;
    MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_exam);
        myPreferences = new MyPreferences(this);
        initViews();
        collapseLayout.setTitleEnabled(false);

        setSupportActionBar(toolbar);
        mBundle = new Bundle();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            DecodeBundleValues(bundle);

        }
        AfterInit();
    }

    private void DecodeBundleValues(Bundle bundle) {
//            if (bundle.containsKey("CourseExamModel")) {
        if (bundle.containsKey("COVER") && bundle.containsKey("COURSE")
                && bundle.containsKey("COURSEUNIQID") && bundle.containsKey("EXAMUINQID")
                && bundle.containsKey("DURATION")
                ) {
            COVER = bundle.getString("COVER");
            COURSE = bundle.getString("COURSE");
            COURSEUNIQID = bundle.getString("COURSEUNIQID");
            EXAMUINQID = bundle.getString("EXAMUINQID");
            ;
            DURATION = bundle.getDouble("DURATION");
            txtCourse.setText(COURSE);
            txtTitle.setText(COURSE);
            txtTitle.setTypeface(CustomFonts.Reckoner_Bold(this));
            double value = (DURATION * 60);
            txtTime.setText(String.format("%2s %s", value, "min"));

            mBundle.putString("COVER", COVER);
            mBundle.putString("COURSE", COURSE);
            mBundle.putString("COURSEUNIQID", COURSEUNIQID);
            mBundle.putString("EXAMUINQID", EXAMUINQID);
            mBundle.putDouble("DURATION", DURATION);

        } else {
//                    if (courseModel == null || examModel == null) {
            TastyToast.makeText(this, "Error encountered", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            finish();
//                    }
        }
//            }
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                LoadExamQns();
                Load();
            }
        }, 3000);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(myPreferences.getEmail())) {
                    if (ExploreExam.examQAModels == null) {
                        LoadExamQns();
                        return;
                    }

                    //register student exam
                    AddStudentExam();
                } else {
                    android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ExploreExam.this);
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setMessage("Sign in to continue");
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (myPreferences.Logout()) {
                                finish();
                                Intent intent = new Intent(ExploreExam.this, EmailReq.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });
                    alertDialog.show();
                }
            }
        });
        String url;
        if (COVER != null) {
            url = new SpaceCraft().getUrl(ExploreExam.this) + "exams/" + COVER;// examModel.getE_coverimg();
        } else {
            url = new SpaceCraft().getUrl(ExploreExam.this) + "courses/" + COVER;// courseModel.getC_coverimg();
        }
        Glide.with(ExploreExam.this).load(Uri.parse(url)).placeholder(R.mipmap.bgblue).into(imgImg);

    }

    void AddStudentExam() {
        Map<String, String> map = new HashMap<>();
        map.put("target", "student");
        map.put("action", "addexam");
        map.put("usertype", String.valueOf(0));
        map.put("courseuniqid", COURSEUNIQID);//courseModel.getC_uniqid()
        map.put("examuniqid", EXAMUINQID);//examModel.getE_examuniqid()
        map.put("email", myPreferences.getEmail());
        AddStudentExamDb(map);
    }

    private void AddStudentExamDb(final Map<String, String> params) {
        blanket.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(ExploreExam.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(ExploreExam.this),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestQueue.stop();
                        blanket.setVisibility(View.GONE);

                        ParseJsonsHelper parseJsonsHelper = new ParseJsonsHelper(ExploreExam.this);
                        MessageModel messageModel = parseJsonsHelper.getMessageModel(s);
                        if (messageModel != null) {
                            if (!messageModel.isError()) {
                                TastyToast.makeText(ExploreExam.this, messageModel.getMessage(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                                Intent intent = new Intent(ExploreExam.this, StartExam.class);
                                intent.putExtras(mBundle);
                                startActivity(intent);

                            } else {
                                Snackbar.make(parent, messageModel.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                            return;
                        }

                        Snackbar.make(parent, "Error encountered", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AddStudentExamDb(params);
                            }
                        }).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                blanket.setVisibility(View.GONE);
                Snackbar.make(parent, SpaceCraft.CONNECTION_ERROR_MSG, Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddStudentExamDb(params);
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

    public static ExploreExam getInstance() {
        return new ExploreExam();
    }

    private void LoadExamQns() {
        ArrayList<NetParamsModel> paramsModels = new ArrayList<>();
        NetParamsModel netParamsModel = new NetParamsModel();
        netParamsModel.setKey("target");
        netParamsModel.setValue("exams");
        paramsModels.add(netParamsModel);

        netParamsModel = new NetParamsModel();
        netParamsModel.setKey("action");
        netParamsModel.setValue("getfullexam");
        paramsModels.add(netParamsModel);

        netParamsModel = new NetParamsModel();
        netParamsModel.setKey("examuniqid");
        netParamsModel.setValue(EXAMUINQID);//examModel.getE_examuniqid()
        paramsModels.add(netParamsModel);

        new Load().execute(paramsModels);
    }

    void Load() {
        pgExamsQALoading.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(ExploreExam.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(ExploreExam.this),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestQueue.stop();
                        pgExamsQALoading.setVisibility(View.GONE);
                        ParseJsonsHelper parseJsonsHelper = new ParseJsonsHelper(ExploreExam.this);
                        ExploreExam.examQAModels = parseJsonsHelper.getExamQA(s);
                        if (examQAModels != null) {
                            txtNumQns.setText(String.valueOf(examQAModels.size()));
                        } else {
                            MessageModel messageModel = parseJsonsHelper.getMessageModel(s);
                            assert messageModel != null;
                            if (messageModel.isError()) {
                                Snackbar.make(parent, messageModel.getMessage(), Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Load();
                                    }
                                }).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                pgExamsQALoading.setVisibility(View.GONE);
                Snackbar.make(parent, SpaceCraft.CONNECTION_ERROR_MSG, Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Load();
                    }
                }).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("target", "exams");
                params.put("action", "getfullexam");
                params.put("examuniqid", EXAMUINQID);
                return params;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    class Load extends AsyncTask<ArrayList<NetParamsModel>, Void, ArrayList<ExamQAModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgExamsQALoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<ExamQAModel> doInBackground(ArrayList<NetParamsModel>... params) {
            String json = HttpLoadData.LoadData(ExploreExam.this, params[0]);
            if (json != null) {
                Log.e("JSON DATA", json);
            } else {
                Log.e("JSON DATA", "JSON IS NULL");
                return null;
            }
            return new ParseJsonsHelper(ExploreExam.this).getExamQA(json);
        }

        @Override
        protected void onPostExecute(ArrayList<ExamQAModel> examQAModels) {
            super.onPostExecute(examQAModels);
            pgExamsQALoading.setVisibility(View.GONE);
            if (examQAModels != null) {
                ExploreExam.examQAModels = examQAModels;
                txtNumQns.setText(String.valueOf(examQAModels.size()));
            }
        }

    }

    private CoordinatorLayout parent;
    private CollapsingToolbarLayout collapseLayout;
    private ImageView imgImg;
    private Toolbar toolbar;
    private TextView txtTitle;
    private TextView txtCourse;
    private TextView txtNumQns;
    private TextView txtTime;
    private DrawMeButton btnStart;
    private ProgressBar pgExamsQALoading;
    private RelativeLayout blanket;
    private GoogleProgressBar pgBlanket;
    private FloatingActionButton fab;

    public void initViews() {
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        collapseLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseLayout);
        imgImg = (ImageView) findViewById(R.id.imgImg);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtCourse = (TextView) findViewById(R.id.txtCourse);
        txtNumQns = (TextView) findViewById(R.id.txtNumQns);
        txtTime = (TextView) findViewById(R.id.txtTime);
        btnStart = (DrawMeButton) findViewById(R.id.btnStart);
        pgExamsQALoading = (ProgressBar) findViewById(R.id.pgExamsQALoading);
        blanket = (RelativeLayout) findViewById(R.id.blanket);
        pgBlanket = (GoogleProgressBar) findViewById(R.id.pgBlanket);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }


    public static ArrayList<ExamQAModel> examQAModels;
}
