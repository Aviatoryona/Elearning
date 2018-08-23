package com.aviator.elearning;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.aviator.main.CustomFonts;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.adapter.ListCourseAdapter;
import com.aviator.elearning.el.adapter.ListExamQnAdapter;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.CourseModel;
import com.aviator.elearning.el.models.ExamQnModel;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.rafakob.drawme.DrawMeButton;
import com.rafakob.drawme.DrawMeEditText;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import avfont.com.aviator.aviatorfontlib.AvFontHelper;

@SuppressWarnings("ALL")
public class TutorNewExam extends AppCompatActivity implements ShowHide {

    MyPreferences myPreferences;
    String EXAMUNIGID;

    static CourseModel courseModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_new_exam);
        myPreferences = new MyPreferences(this);
        initViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AfterInit();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (listTopics.getAdapter() instanceof  ListTopicAdapter) {
        LoadTopicsParams();
//        }
    }

    void LoadTopicsParams() {
        Map<String, String> paMap = new HashMap<>();
        paMap.put("target", "tutor");
        paMap.put("action", "getcourses");
        paMap.put("usertype", String.valueOf(1));
        paMap.put("email", myPreferences.getEmail());

        LoadCourseTopics(paMap);
    }

    void AddExmDb(CourseModel courseModel) {
        if (courseModel == null) {
            ShowMsg("Error encountered");
            return;
        }

        String examuniqid = String.format("EXM-%s", Calendar.getInstance().getTimeInMillis());

        Map<String, String> paMap = new HashMap<>();
        paMap.put("target", "tutor");
        paMap.put("action", "addexam");
        paMap.put("usertype", String.valueOf(1));
        paMap.put("courseuniqid", courseModel.getC_uniqid());
        paMap.put("examuniqid", examuniqid);
        paMap.put("email", myPreferences.getEmail());

        EXAMUNIGID = examuniqid;

        AddExamDb(paMap);
    }

    void AddExamQN() {
        Map<String, String> paMap = new HashMap<>();
        paMap.put("target", "exams");
        paMap.put("action", "addqn");
        paMap.put("examuniqid", EXAMUNIGID);
        paMap.put("qn_examqn", edtQn.getText().toString());
        AddExamQN_(paMap);
    }

    void AddExamQN_(final Map<String, String> paMap) {
        pgLoading.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, new SpaceCraft().getUrl(TutorNewExam.this),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestQueue.stop();
                        pgLoading.setVisibility(View.GONE);
                        MessageModel messageModel = new ParseJsonsHelper(TutorNewExam.this).getMessageModel(s);
                        if (messageModel != null) {
                            if (!messageModel.isError()) {
                                GETExamQNs();
                                Toast.makeText(TutorNewExam.this, "Added", Toast.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(parent, messageModel.getMessage(), Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Resend", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                AddExamQN_(paMap);
                                            }
                                        }).show();
                            }
                        } else {
                            Snackbar.make(parent, "Failed, try again", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Resend", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AddExamQN_(paMap);
                                        }
                                    }).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                pgLoading.setVisibility(View.GONE);
                Snackbar.make(parent, SpaceCraft.CONNECTION_ERROR_MSG, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Resend", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AddExamQN_(paMap);
                            }
                        }).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paMap;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    void GETExamQNs() {  //
//        pgLoading.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, new SpaceCraft().getUrl(TutorNewExam.this),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestQueue.stop();
                        ArrayList<ExamQnModel> arrayList = new ParseJsonsHelper(TutorNewExam.this).getExamQnModel(s);
                        if (arrayList != null) {
                            listQns.setAdapter(new ListExamQnAdapter(arrayList, TutorNewExam.this));
                        } else {
                            MessageModel messageModel = new ParseJsonsHelper(TutorNewExam.this).getMessageModel(s);
                            if (messageModel != null) {
                                if (!messageModel.isError()) {
                                    GETExamQNs();
                                } else {
                                    Snackbar.make(parent, messageModel.getMessage(), Snackbar.LENGTH_INDEFINITE)
                                            .show();
                                }
                            } else {
                                Snackbar.make(parent, "Failed, try again", Snackbar.LENGTH_INDEFINITE)
                                        .show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
//                pgLoading.setVisibility(View.GONE);
                Snackbar.make(parent, SpaceCraft.CONNECTION_ERROR_MSG, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Resend", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GETExamQNs();
                            }
                        }).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                return paMap;
                Map<String, String> paMap = new HashMap<>();
                paMap.put("target", "exams");
                paMap.put("action", "getexambyid");
                paMap.put("examuniqid", EXAMUNIGID);
                return paMap;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }


    @Override
    public void AfterInit() {
        listCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseModel courseModel = (CourseModel) parent.getAdapter().getItem(position);
                TutorNewExam.courseModel = courseModel;
                AddExmDb(courseModel);
            }
        });

        listQns.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //start new activity for adding options using qnid ,
                ExamQnModel examQnModel = (ExamQnModel) parent.getAdapter().getItem(position);
                assert examQnModel != null;
                Bundle bundle = new Bundle();
                bundle.putString("qn_id", String.valueOf(examQnModel.getQn_id()));
                bundle.putString("qn_examuniqid", examQnModel.getQn_examuniqid());
                bundle.putString("qn_examqn", examQnModel.getQn_examqn());

                Intent intent = new Intent(TutorNewExam.this, TutorAddExamQnOptions.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container2.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                container1.setVisibility(View.VISIBLE);
            }
        });

        txtQn.setTypeface(AvFontHelper.RobotoBlack(this));
        txtQno.setTypeface(CustomFonts.LoveYaLikeASister(this));
        edtQn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtQn.setText(edtQn.getText().toString());
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtQn.setText("");
            }
        });

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtQn.getText().toString())) {
                    edtQn.setError("required *");
                    return;
                }

                AddExamQN();
            }
        });

        imgCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    private void AddExamDb(final Map<String, String> paMap) {
        HideMsg();
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(TutorNewExam.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(TutorNewExam.this),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestQueue.stop();
                        HideMsg();


                        MessageModel messageModel = new ParseJsonsHelper(TutorNewExam.this).getMessageModel(s);
                        if (messageModel != null) {
                            if (!messageModel.isError()) {
                                container1.setVisibility(View.GONE);
                                fab.setVisibility(View.VISIBLE);
                                container2.setVisibility(View.VISIBLE);
                                return;
                            }
                            Toast.makeText(TutorNewExam.this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                            return;

                        }
                        Toast.makeText(TutorNewExam.this, s, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                HideMsg();
                Toast.makeText(TutorNewExam.this, "Internet Connection Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return paMap;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    private void LoadCourseTopics(final Map<String, String> paStringStringMap) {
        HideMsg();
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();
                HideMsg();
//                Toast.makeText(TutorNewExam.this, s, Toast.LENGTH_LONG).show();

                ParseJsonsHelper parseJsonsHelper = new ParseJsonsHelper(TutorNewExam.this);
                ArrayList<CourseModel> courseModelArrayList = parseJsonsHelper.getCourseData(s);
                if (courseModelArrayList != null) {
//                    Toast.makeText(TutorNewExam.this, String.valueOf(courseModelArrayList.size()), Toast.LENGTH_SHORT).show();
                    listCourse.setAdapter(new ListCourseAdapter(courseModelArrayList, TutorNewExam.this));

//                    Toast.makeText(TutorNewExam.this, "Adapter set "+(listCourse.getAdapter()!=null), Toast.LENGTH_SHORT).show();
                    return;
                }
//                else{
//                    Toast.makeText(TutorNewExam.this, s, Toast.LENGTH_SHORT).show();
//                }

                MessageModel messageModel = parseJsonsHelper.getMessageModel(s);
                if (messageModel != null) {
                    ShowMsg(messageModel.getMessage());
                    return;
                }

                ShowMsg(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                ShowMsg("Internet Connection Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> paMap = new HashMap<>(paStringStringMap);
//                paMap.remove("action");
//                paMap.put("action", "gettopics");
                return paStringStringMap;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();

    }

    @Override
    public void ShowMsg(String message) {
        pgLoading.setVisibility(View.GONE);
        Snackbar.make(parent, message, Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadTopicsParams();
            }
        }).show();
    }


    @Override
    public void HideMsg() {
        if (pgLoading.getVisibility() == View.VISIBLE) {
            pgLoading.setVisibility(View.GONE);
            return;
        }
        pgLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void Pause_() {

    }

    @Override
    public void Resume_() {

    }

    @SuppressLint("SetTextI18n")
    void SelectCourse() {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(TutorNewExam.this);
        aBuilder.setCancelable(true);

        View view = CustomViews.customSpinner(TutorNewExam.this);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        txtTitle.setTypeface(AvFontHelper.RobotoBold(TutorNewExam.this));
        txtTitle.setText("Select  Course");
        DrawMeButton btnNext = view.findViewById(R.id.btnNxt);
        final Spinner spinner = view.findViewById(R.id.spinCourseName);

        aBuilder.setView(view);
        final AlertDialog alertDialog = aBuilder.create();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItemPosition() == 0) {
                    TastyToast.makeText(TutorNewExam.this, "Incorrect value selected", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
                    return;
                }

                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private CoordinatorLayout parent;
    private Toolbar toolbar;
    private LinearLayout container1;
    private TextView txtBlanket;
    private ListView listCourse;
    private LinearLayout container2;
    private TextView txtQno;
    private ImageView imgCommit;
    private DrawMeEditText edtQn;
    private LinearLayout lyLeft;
    private ImageView imgSave;
    private ImageView imgCancel;
    private TextView txtQn;
    private ListView listQns;
    private GoogleProgressBar pgLoading;
    private FloatingActionButton fab;

    public void initViews() {
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        container1 = (LinearLayout) findViewById(R.id.container1);
        txtBlanket = (TextView) findViewById(R.id.txtBlanket);
        listCourse = (ListView) findViewById(R.id.listCourse);
        container2 = (LinearLayout) findViewById(R.id.container2);
        txtQno = (TextView) findViewById(R.id.txtQno);
        imgCommit = (ImageView) findViewById(R.id.imgCommit);
        edtQn = (DrawMeEditText) findViewById(R.id.edtQn);
        lyLeft = (LinearLayout) findViewById(R.id.lyLeft);
        imgSave = (ImageView) findViewById(R.id.imgSave);
        imgCancel = (ImageView) findViewById(R.id.imgCancel);
        txtQn = (TextView) findViewById(R.id.txtQn);
        listQns = (ListView) findViewById(R.id.listQns);
        pgLoading = (GoogleProgressBar) findViewById(R.id.pgLoading);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

}
