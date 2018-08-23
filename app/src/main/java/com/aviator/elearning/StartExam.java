package com.aviator.elearning;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.aviator.main.CustomFonts;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.ExamOptionsModel;
import com.aviator.elearning.el.models.ExamQAModel;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.bumptech.glide.Glide;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import avfont.com.aviator.aviatorfontlib.AvFonts;

public class StartExam extends AppCompatActivity implements ShowHide {
     Bundle bundle;
     ArrayList<ExamQAModel> examQAModels;
     ArrayList<ExamQAModel> examQAModelsFake;
     ArrayList<ExamQAModel> myAnswersModel;
     String COVER, COURSE, COURSEUNIQID, EXAMUINQID;
     double DURATION;
     int myScore=0;
     int numQns=0;
    MyPreferences myPreferences;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start_exam);
        view = LayoutInflater.from(this).inflate(R.layout.activity_start_exam, null, false);
        setContentView(view);
        myPreferences = new MyPreferences(this);
        initViews();
        collapseLayout.setTitleEnabled(false);
        setSupportActionBar(toolbar);
        myAnswersModel = new ArrayList<>();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            DecodeBundleValues(bundle);

        }

//        if (examQAModels == null) {
            examQAModels = ExploreExam.examQAModels;
//        }

        if (examQAModels == null) {
            TastyToast.makeText(this, "Error Encountered, exiting...", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            finish();
        } else {
            examQAModelsFake = examQAModels;
             numQns=examQAModels.size();
            AfterInit();
            TastyToast.makeText(this, "Welcome, starting in 3 secs", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    chronoMeter.start();
                    StartRefreshExam();
                }
            }, 5000);
        }


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
            DURATION = bundle.getDouble("DURATION");
            txtTitle.setText(COURSE);
            txtTitle.setTypeface(CustomFonts.Reckoner_Bold(this));

            String url;
            if (COVER != null) {
                url = new SpaceCraft().getUrl(this) + "exams/" + COVER;// examModel.getE_coverimg();
            } else {
                url = new SpaceCraft().getUrl(this) + "courses/" + COVER;// courseModel.getC_coverimg();
            }
            Glide.with(this).load(Uri.parse(url)).placeholder(R.mipmap.bgblue).into(imgImg);

        } else {
            TastyToast.makeText(this, "Error encountered", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

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

        final double value = DURATION * 60;
        txtMaxTime.setText(String.format("%s %s", value, " min"));
        txtMaxTime.setTypeface(CustomFonts.Reckoner_Bold(StartExam.this));
        txtTitle.setTypeface(CustomFonts.LoveYaLikeASister(this));
//        chronoMeter.setFormat("H:MM:SS");
        chronoMeter.setBase(SystemClock.elapsedRealtime());
        chronoMeter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long tt = chronometer.getBase();
                if (tt == value) {
                    Toast.makeText(StartExam.this, "Time is out!!", Toast.LENGTH_SHORT).show();
                    chronometer.stop();
                }
            }

        });
    }

    void StartRefreshExam() {
        int x = ForkInt();
        if (x == -1) {
            Toast.makeText(this, "Exam Completed", Toast.LENGTH_SHORT).show();
            pgLoading.setVisibility(View.VISIBLE);
            chronoMeter.stop();
            listOptions.setAdapter(null);
            txtQn.setText("");
            txtQno.setText("");
            CalculateScore();
        } else {
            ExamQAModel examQAModel = examQAModelsFake.get(x);
            txtQn.setText(examQAModel.getExamQnModel().getQn_examqn());
            txtQno.setText(String.format("Q.NO %s", examQAModel.getExamQnModel().getQn_id()));
            examQAModelsFake.remove(x);
            examQAModelsFake.trimToSize();
            listOptions.setAdapter(new MyListOptionsAdapter(examQAModel));
            pgLoading.setVisibility(View.GONE);
        }
    }

    void CalculateScore() {
//        int score = 0;
        //compare original vs myanswers
//        assert myAnswersModel != null && examQAModels != null;
//        for (ExamQAModel examQAModel : myAnswersModel) {
//            ExamOptionsModel examOptionsModel = examQAModel.getExamOptionsModel();
//            for (ExamQAModel originalExamQAModel1 : examQAModels) {
//                ExamOptionsModel originalOptionsModel = originalExamQAModel1.getExamOptionsModel();
//                if (examOptionsModel.getOpt_qnid() == originalOptionsModel.getOpt_qnid()) {
//                    if (examOptionsModel.getOpt_optionAnswer().equalsIgnoreCase(originalOptionsModel.getOpt_optionAnswer())) {
//                        score = score + 1;
//                    }
//                    break;
//                }
//            }
//        }

        int qnsAttempted = myAnswersModel.size();
        double x;
        try {
            x = ((myScore * 100) / numQns);
        }catch(ArithmeticException e){
            x=0;
        }

        int y=(int) Math.round(x);

        String remarks = DoRemarks(y);

//        Toast.makeText(this, String.valueOf(y), Toast.LENGTH_SHORT).show();

        DoShowExamAnalysis(Integer.parseInt(String.valueOf(y)), qnsAttempted, remarks);
    }

    String DoRemarks(int score) {
        if (score < 40) {
            return "Poor";
        }
        if (score < 65) {
            return "Passed";
        }

        return "Excellent";
    }

    void DoShowExamAnalysis(int score, int qns, String remarks) {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        aBuilder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.score_display, null, false);

        TextView txScore, txQns, txRemarks;
        txScore = view.findViewById(R.id.txtName);
        txScore.setTypeface(CustomFonts.Reckoner_Bold(this));
        txQns = view.findViewById(R.id.txtQno);
        txQns.setTypeface(AvFonts.Motlabold(this));
        txRemarks = view.findViewById(R.id.txtRemarks);
        txRemarks.setTypeface(CustomFonts.LoveYaLikeASister(this));

        txScore.setText(String.format("%s%s", score, "%"));
        txQns.setText(String.valueOf(qns));
        txRemarks.setText(remarks);

        aBuilder.setNeutralButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                ExploreExam.getInstance().finish();
            }
        });

        aBuilder.setView(view);
        AlertDialog alertDialog = aBuilder.create();
        pgLoading.setVisibility(View.GONE);
        alertDialog.show();
        SubmitScoreToDb(score, remarks);
    }


    void SubmitScoreToDb(int score, String remarks) {
        Map<String, String> map = new HashMap<>();
        map.put("target", "exams");
        map.put("action", "updatescore");
        map.put("usertype", String.valueOf(0));
        map.put("courseuniqid", COURSEUNIQID);//courseModel.getC_uniqid()
        map.put("er_examuinqid", EXAMUINQID);//examModel.getE_examuniqid()
        map.put("er_useremail", myPreferences.getEmail());
        map.put("er_score", String.valueOf(score));
        map.put("er_remarks", remarks);
        UpdateStudentExamDb(map);
    }

    private void UpdateStudentExamDb(final Map<String, String> params) {
        pgLoading.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestQueue.stop();
//                        blanket.setVisibility(View.GONE);

                        ParseJsonsHelper parseJsonsHelper = new ParseJsonsHelper(StartExam.this);
                        MessageModel messageModel = parseJsonsHelper.getMessageModel(s);
                        if (messageModel != null) {
                            if (!messageModel.isError()) {
                                TastyToast.makeText(StartExam.this, messageModel.getMessage(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
                            } else {
                                Snackbar.make(view, messageModel.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                            return;
                        }

                        Snackbar.make(view, "Error encountered,kindly click to resend ", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UpdateStudentExamDb(params);
                            }
                        }).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                pgLoading.setVisibility(View.GONE);
                Snackbar.make(view, SpaceCraft.CONNECTION_ERROR_MSG+", kindly click to resend", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdateStudentExamDb(params);
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

    int ForkInt() {
        if (examQAModelsFake.isEmpty()) {
            return -1;
        }
        return new Random().nextInt(examQAModelsFake.size());
    }

    public void DoAnswerSelected(ExamQAModel examQAModel, ExamOptionsModel examOptionsModel, String answer) {
        pgLoading.setVisibility(View.VISIBLE);
        if (examQAModel.getExamOptionsModel().getOpt_optionAnswer().equalsIgnoreCase(answer)){
//            Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
            myScore=myScore+1;
            Toast toast=TastyToast.makeText(this,"Correct Answer",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
            toast.setGravity(Gravity.TOP|Gravity.END,0,0);
            toast.show();
        }else{
//            Toast.makeText(this, "Ooops!!! Incorrect Answer", Toast.LENGTH_SHORT).show();
            Toast toast=TastyToast.makeText(this,"Ooops!!! Incorrect Answer",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            toast.setGravity(Gravity.TOP|Gravity.START,0,0);
            toast.show();
        }

        examOptionsModel.setOpt_optionAnswer(answer);
        examQAModel.setExamOptionsModel(examOptionsModel);
        myAnswersModel.add(examQAModel);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StartRefreshExam();
            }
        }, 800);
    }

    class MyListOptionsAdapter extends BaseAdapter {
        ExamQAModel examQAModel;
        ArrayList<String> options;

        MyListOptionsAdapter(ExamQAModel examQAModel) {
            this.examQAModel = examQAModel;
            options = new ArrayList<>();
            checkOption(this.examQAModel.getExamOptionsModel().getOpt_option1());
            checkOption(this.examQAModel.getExamOptionsModel().getOpt_option2());
            checkOption(this.examQAModel.getExamOptionsModel().getOpt_option3());
            checkOption(this.examQAModel.getExamOptionsModel().getOpt_option4());
            checkOption(this.examQAModel.getExamOptionsModel().getOpt_option5());
            checkOption(this.examQAModel.getExamOptionsModel().getOpt_option6());
        }

        void checkOption(String s) {
            if (s==null){
                return;
            }
            if (s.isEmpty() || s.equalsIgnoreCase("null")) {
                return;
            }
            options.add(s);
        }

        @Override
        public int getCount() {
            return options.size();
        }

        @Override
        public Object getItem(int position) {
            return examQAModel.getExamOptionsModel();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final CheckBox checkBox;
            final ExamOptionsModel examOptionsModel = (ExamOptionsModel) getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chkbox, parent, false);
            }
            checkBox = convertView.findViewById(R.id.chkOption);
            checkBox.setText(options.get(position));

            final View finalConvertView = convertView;
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animation = AnimationUtils.loadAnimation(StartExam.this, R.anim.top_from_bottom);
                    finalConvertView.startAnimation(animation);
                    DoAnswerSelected(examQAModel, examOptionsModel, checkBox.getText().toString());
                }
            });
            Animation animation = AnimationUtils.loadAnimation(StartExam.this, R.anim.fade);
            convertView.startAnimation(animation);
            return convertView;
        }
    }

    private CollapsingToolbarLayout collapseLayout;
    private ImageView imgImg;
    private Chronometer chronoMeter;
    private Toolbar toolbar;
    private TextView txtTitle;
    private TextView txtMaxTime;
    private LinearLayout container2;
    private TextView txtQno;
    private ImageView imgCommit;
    private LinearLayout lyLeft;
    private ImageView imgSave;
    private ImageView imgCancel;
    private TextView txtQn;
    private ListView listOptions;
    private GoogleProgressBar pgLoading;
    private FloatingActionButton fab;

    public void initViews() {
        collapseLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseLayout);
        imgImg = (ImageView) findViewById(R.id.imgImg);
        chronoMeter = (Chronometer) findViewById(R.id.chronoMeter);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtMaxTime = (TextView) findViewById(R.id.txtMaxTime);
        container2 = (LinearLayout) findViewById(R.id.container2);
        txtQno = (TextView) findViewById(R.id.txtQno);
        imgCommit = (ImageView) findViewById(R.id.imgCommit);
        lyLeft = (LinearLayout) findViewById(R.id.lyLeft);
        imgSave = (ImageView) findViewById(R.id.imgSave);
        imgCancel = (ImageView) findViewById(R.id.imgCancel);
        txtQn = (TextView) findViewById(R.id.txtQn);
        listOptions = (ListView) findViewById(R.id.listOptions);
        pgLoading = (GoogleProgressBar) findViewById(R.id.pgLoading);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }


}
