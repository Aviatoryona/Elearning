package com.aviator.elearning.aviator.main;

import android.annotation.SuppressLint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.Courses;
import com.aviator.elearning.Exams;
import com.aviator.elearning.MainActivity;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.adapter.RecCoursesAdapter;
import com.aviator.elearning.el.adapter.RecExamsAdapter_Main;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.CourseExamModel;
import com.aviator.elearning.el.models.CourseModel;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.net.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class FetchData_Activity extends AppCompatActivity implements ShowHide {

    View snackView;

    public void GetExamData(final Map<String, String> paStringMap) {
        if (FetchData_Activity.this instanceof MainActivity) {
            snackView = ((MainActivity) FetchData_Activity.this).drawerLayout;
            ((MainActivity) FetchData_Activity.this).pgExams.setVisibility(View.VISIBLE);
        }

        if (FetchData_Activity.this instanceof Exams) {
            snackView = ((Exams) FetchData_Activity.this).parent;
            ((Exams) FetchData_Activity.this).pgExams.setVisibility(View.VISIBLE);
        }


        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ParseJsonsHelper fda = new ParseJsonsHelper(FetchData_Activity.this);
//                ArrayList<ExamModel> examModelArrayList = fda.getExamData(s);
                ArrayList<CourseExamModel> examModelArrayList = fda.getCourseExamModel(s);
                if (examModelArrayList != null) {
                    if (FetchData_Activity.this instanceof MainActivity) {
                        ((MainActivity) FetchData_Activity.this).pgExams.setVisibility(View.GONE);
//                        ((MainActivity) FetchData_Activity.this)
//                                .recExams.setAdapter(new RecExamsAdapter(
//                                examModelArrayList,
//                                FetchData_Activity.this,
//                                FetchData_Activity.this,
//                                null
//                        ));

                        ((MainActivity) FetchData_Activity.this).recExams.setAdapter(
                                new RecExamsAdapter_Main(examModelArrayList,
                                        FetchData_Activity.this,
                                        FetchData_Activity.this,
                                        null)
                        );

                        return;
                    }
                    if (FetchData_Activity.this instanceof Exams) {
                        ((Exams) FetchData_Activity.this).swipeRefresh.setRefreshing(false);
                        ((Exams) FetchData_Activity.this).pgExams.setVisibility(View.GONE);
                        ((Exams) FetchData_Activity.this).recExams.setAdapter(
//                                new RecExamsAdapter(
//                                examModelArrayList,
//                                FetchData_Activity.this,
//                                FetchData_Activity.this,
//                                null)
                                new RecExamsAdapter_Main(examModelArrayList,
                                        FetchData_Activity.this,
                                        FetchData_Activity.this,
                                        null)
                        );
                    }

                } else {

                    MessageModel messageModel = fda.getMessageModel(s);
                    if (messageModel != null) {
                        if (FetchData_Activity.this instanceof MainActivity) {
                            ((MainActivity) FetchData_Activity.this).pgExams.setVisibility(View.GONE);
                        }

                        if (FetchData_Activity.this instanceof Exams) {
                            ((Exams) FetchData_Activity.this).swipeRefresh.setRefreshing(false);
                            ((Exams) FetchData_Activity.this).pgExams.setVisibility(View.GONE);
                            Snackbar.make(snackView, messageModel.getMessage(), Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Reload", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            GetExamData(paStringMap);
                                        }
                                    }).show();
                            return;
                        }

                        Snackbar.make(snackView, messageModel.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Reload", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        GetExamData(paStringMap);
                                    }
                                }).show();

                    } else {

                        if (FetchData_Activity.this instanceof MainActivity) {
                            ((MainActivity) FetchData_Activity.this).pgExams.setVisibility(View.GONE);
                        }

                        if (FetchData_Activity.this instanceof Exams) {
                            ((Exams) FetchData_Activity.this).swipeRefresh.setRefreshing(false);
                            ((Exams) FetchData_Activity.this).pgExams.setVisibility(View.GONE);
                        }

                        Snackbar.make(snackView, SpaceCraft.CONNECTION_ERROR_MSG, Snackbar.LENGTH_INDEFINITE)
                                .setAction("Reload", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        GetExamData(paStringMap);
                                    }
                                }).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                if (FetchData_Activity.this instanceof MainActivity) {
                    ((MainActivity) FetchData_Activity.this).pgExams.setVisibility(View.GONE);
                }

                if (FetchData_Activity.this instanceof Exams) {
                    ((Exams) FetchData_Activity.this).swipeRefresh.setRefreshing(false);
                    ((Exams) FetchData_Activity.this).pgExams.setVisibility(View.GONE);
                }

                Snackbar.make(snackView, SpaceCraft.CONNECTION_ERROR_MSG, Snackbar.LENGTH_LONG)
                        .setAction("Reload", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GetExamData(paStringMap);
                            }
                        }).show();
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

    public void GetCourseData(final Map<String, String> paStringMap) {

        if (FetchData_Activity.this instanceof MainActivity) {
            ((MainActivity) FetchData_Activity.this).pgCourses.setVisibility(View.VISIBLE);
            ((MainActivity) FetchData_Activity.this).refreshLy.setVisibility(View.GONE);
        }

        if (FetchData_Activity.this instanceof Courses) {
            snackView = ((Courses) FetchData_Activity.this).parent;
            ((Courses) FetchData_Activity.this).pgCourses.setVisibility(View.VISIBLE);
        }

        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ParseJsonsHelper fda = new ParseJsonsHelper(FetchData_Activity.this);

                ArrayList<CourseModel> courseModelArrayList = fda.getCourseData(s);
                if (courseModelArrayList != null) {
                    if (FetchData_Activity.this instanceof MainActivity) {
                        ((MainActivity) FetchData_Activity.this).pgCourses.setVisibility(View.GONE);
                        ((MainActivity) FetchData_Activity.this).refreshLy.setVisibility(View.GONE);
                        ((MainActivity) FetchData_Activity.this)
                                .recCourses.setAdapter(new RecCoursesAdapter(FetchData_Activity.this,
                                courseModelArrayList,
                                FetchData_Activity.this,
                                null
                        ));
                        return;
                    }
                    if (FetchData_Activity.this instanceof Courses) {
                        ((Courses) FetchData_Activity.this).pgCourses.setVisibility(View.GONE);
                        ((Courses) FetchData_Activity.this).swipeRefresh.setRefreshing(false);
                        ((Courses) FetchData_Activity.this).recCourses.setAdapter(new RecCoursesAdapter(FetchData_Activity.this,
                                courseModelArrayList,
                                FetchData_Activity.this,
                                null
                        ));
                    }

                } else {

                    MessageModel messageModel = fda.getMessageModel(s);
                    if (messageModel != null) {
                        if (FetchData_Activity.this instanceof MainActivity) {
                            ((MainActivity) FetchData_Activity.this).pgCourses.setVisibility(View.GONE);
                            ((MainActivity) FetchData_Activity.this).refreshLy.setVisibility(View.VISIBLE);
                            ((MainActivity) FetchData_Activity.this).txtMsg.setText(messageModel.getMessage());
                            return;
                        }
                        if (FetchData_Activity.this instanceof Courses) {
                            ((Courses) FetchData_Activity.this).pgCourses.setVisibility(View.GONE);
                            ((Courses) FetchData_Activity.this).swipeRefresh.setRefreshing(false);
                            if (((Courses) FetchData_Activity.this).recCourses.getAdapter() == null) {
                                Snackbar.make(snackView, messageModel.getMessage(), Snackbar.LENGTH_LONG)
                                        .setAction("Reload", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                GetCourseData(paStringMap);
                                            }
                                        }).show();
                            }
                        }
                    } else {
                        if (FetchData_Activity.this instanceof MainActivity) {
                            ((MainActivity) FetchData_Activity.this).pgCourses.setVisibility(View.GONE);
                            ((MainActivity) FetchData_Activity.this).refreshLy.setVisibility(View.VISIBLE);
                            ((MainActivity) FetchData_Activity.this).txtMsg.setText(SpaceCraft.CONNECTION_ERROR_MSG);
                            return;
                        }

                        if (FetchData_Activity.this instanceof Courses) {
                            ((Courses) FetchData_Activity.this).pgCourses.setVisibility(View.GONE);
                            ((Courses) FetchData_Activity.this).swipeRefresh.setRefreshing(false);
                            Snackbar.make(snackView, SpaceCraft.CONNECTION_ERROR_MSG, Snackbar.LENGTH_LONG)
                                    .setAction("Reload", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            GetCourseData(paStringMap);
                                        }
                                    }).show();
                        }

                    }

                }
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                if (FetchData_Activity.this instanceof MainActivity) {
                    ((MainActivity) FetchData_Activity.this).pgCourses.setVisibility(View.GONE);
                    ((MainActivity) FetchData_Activity.this).refreshLy.setVisibility(View.VISIBLE);
                    ((MainActivity) FetchData_Activity.this).txtMsg.setText("Internet Connection Error");
                    return;
                }

                if (FetchData_Activity.this instanceof Courses) {
                    ((Courses) FetchData_Activity.this).pgCourses.setVisibility(View.GONE);
                    ((Courses) FetchData_Activity.this).swipeRefresh.setRefreshing(false);
                    Snackbar.make(snackView, "Internet Connection Error", Snackbar.LENGTH_LONG)
                            .setAction("Reload", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    GetCourseData(paStringMap);
                                }
                            }).show();
                }

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


    public void LoadRecExamsData() {
        Map<String, String> map = new HashMap<>();
        map.put("target", "exams");
        map.put("action", "allexams");
        GetExamData(map);
    }

    public void LoadRecCoursesData() {
        Map<String, String> map = new HashMap<>();
        map.put("target", "courses");
        map.put("action", "all");
        GetCourseData(map);
    }



}
