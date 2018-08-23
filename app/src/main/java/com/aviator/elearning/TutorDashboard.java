package com.aviator.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.adapter.TutorPagerAdapter;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.rafakob.drawme.DrawMeButton;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import avfont.com.aviator.aviatorfontlib.AvFontHelper;

@SuppressWarnings("ALL")
public class TutorDashboard extends AppCompatActivity implements ShowHide {

    /*
    Add new course
    Add exam
    View Students registered for exam
    View Student registered for course
    Monitor Online exams
     */

//    DbHelper dbHelper;

    MyPreferences myPreferences;
    static TutorDashboard tutorDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_dashboard);
        tutorDashboard = this;
        myPreferences = new MyPreferences(this);
//        dbHelper = new DbHelper(TutorDashboard.this);

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

    public static void MoveToViewPagerCurrent(int page) {
        TutorDashboard.tutorDashboard.viewPager.setCurrentItem(page);
    }

    @Override
    public void AfterInit() {
        bottomNavView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomSheetBehavior = BottomSheetBehavior.from((View) btmSheet);
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);

        viewPager.setAdapter(new TutorPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(onPageChangeListener);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    fab.setImageResource(R.drawable.ic_close_black_24dp);
//                    fab.setVisibility(View.GONE);
                    return;
                }

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        relNewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                DialogNewCE("New Course", SpaceCraft.NEW_COURSE);
//                Intent intent = new Intent(TutorDashboard.this, TutorNewCourse.class);
//                intent.putExtra("UNIQID", params.get("uniqid"));
//                intent.putExtra("NAME", params.get("name"));
//                startActivity(intent);
            }
        });

        relNewExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                DialogNewCE("New Exam", SpaceCraft.NEW_EXAM);
                Intent intent = new Intent(TutorDashboard.this, TutorNewExam.class);
//                intent.putExtra(SpaceCraft.STRING_DATA, edtName.getText().toString());
                startActivity(intent);
            }
        });
    }

    void DialogNewCE(String title, final int type) {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(TutorDashboard.this);
        aBuilder.setCancelable(true);

        View view = CustomViews.customEditText(TutorDashboard.this);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        txtTitle.setTypeface(AvFontHelper.RobotoBold(TutorDashboard.this));
        txtTitle.setText(title);
        DrawMeButton btnNext = view.findViewById(R.id.btnNxt);
        final EditText edtName = view.findViewById(R.id.edtName);

        aBuilder.setView(view);
        final AlertDialog alertDialog = aBuilder.create();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtName.getText().toString())) {
                    edtName.setError("Required *");
                    return;
                }


                if (type == SpaceCraft.NEW_EXAM) {
                    Intent intent = new Intent(TutorDashboard.this, TutorNewExam.class);
                    intent.putExtra(SpaceCraft.STRING_DATA, edtName.getText().toString());
                    startActivity(intent);
                    return;
                }


                alertDialog.dismiss();
                final String uniqid = String.format("EL-%s", new Date().getTime());
                Map<String, String> params = new HashMap<>();
                params.put("target", "tutor");
                params.put("action", "addcourse");
                params.put("usertype", String.valueOf(1));
                params.put("uniqid", uniqid);
                params.put("name", edtName.getText().toString());
                params.put("email", myPreferences.getEmail());

                AddCourseExamDb(params, SpaceCraft.NEW_COURSE);

            }
        });

        alertDialog.show();
    }

    private void AddCourseExamDb(final Map<String, String> params, final int whatNext) {
        blanket.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(TutorDashboard.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                new SpaceCraft().getUrl(TutorDashboard.this),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestQueue.stop();
                        blanket.setVisibility(View.GONE);

                        MessageModel messageModel = new ParseJsonsHelper(TutorDashboard.this).getMessageModel(s);
                        if (messageModel != null) {

                            if (!messageModel.isError()) {


                                Intent intent;
                                if (whatNext == SpaceCraft.NEW_COURSE) {
                                    intent = new Intent(TutorDashboard.this, TutorNewCourse.class);
                                    intent.putExtra("UNIQID", params.get("uniqid"));
                                    intent.putExtra("NAME", params.get("name"));
                                    startActivity(intent);

                                }
                                return;
                            } else {
                                Snackbar.make(viewPager, s, Snackbar.LENGTH_LONG).setAction(messageModel.getMessage(), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        AddCourseExamDb(params, whatNext);
                                    }
                                }).show();
                            }
                            return;
                        }

                        Snackbar.make(viewPager, s, Snackbar.LENGTH_LONG).setAction("Try again", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AddCourseExamDb(params, whatNext);
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
                        AddCourseExamDb(params, whatNext);
                    }
                }).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();

    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0: {
                    bottomNavView.setSelectedItemId(R.id.action_courses);
                    break;
                }
                case 2: {
                    bottomNavView.setSelectedItemId(R.id.action_exams);
                    break;
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home: {
//                    bottomNavView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_carrot));
                    viewPager.setCurrentItem(0);
                    break;
                }
                case R.id.action_courses: {
//                    bottomNavView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_carrot));
                    viewPager.setCurrentItem(1);
                    break;
                }

                case R.id.action_exams: {
//                    bottomNavView.setBackgroundColor(getResources().getColor(R.color.fbutton_color_alizarin));
                    viewPager.setCurrentItem(2);
                    break;
                }

            }
            return true;
        }
    };

    BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                fab.setImageResource(R.drawable.ic_add_black_24dp);
                fab.setVisibility(View.VISIBLE);
            }
            else if ((newState == BottomSheetBehavior.STATE_EXPANDED)||(newState == BottomSheetBehavior.STATE_COLLAPSED)){
                fab.setImageResource(R.drawable.ic_close_black_24dp);
                Animation animation= AnimationUtils.loadAnimation(TutorDashboard.this,R.anim.fade_out);
                fab.startAnimation(animation);
                fab.setVisibility(View.GONE);
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };


    BottomSheetBehavior bottomSheetBehavior;


    private AppBarLayout appBarLy;
    private CollapsingToolbarLayout collapseLayout;
    private ImageView imgImg;
    private Toolbar toolbar;
    private TextView txtTitle;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavView;
    private LinearLayout btmSheet;
    private RelativeLayout relNewExam;
    private RelativeLayout relNewCourse;
    private RelativeLayout blanket;
    private GoogleProgressBar pgBlanket;
    private FloatingActionButton fab;

    public void initViews() {
        appBarLy = (AppBarLayout) findViewById(R.id.appBarLy);
        collapseLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseLayout);
        imgImg = (ImageView) findViewById(R.id.imgImg);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        bottomNavView = (BottomNavigationView) findViewById(R.id.bottomNavView);
        btmSheet = (LinearLayout) findViewById(R.id.btmSheet);
        relNewExam = (RelativeLayout) findViewById(R.id.relNewExam);
        relNewCourse = (RelativeLayout) findViewById(R.id.relNewCourse);
        blanket = (RelativeLayout) findViewById(R.id.blanket);
        pgBlanket = (GoogleProgressBar) findViewById(R.id.pgBlanket);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }


}
