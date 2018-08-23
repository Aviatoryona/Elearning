package com.aviator.elearning.el.CommonsPkg;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.net.VolleySingleton;

import java.util.Map;

public class TutorFragCommon extends Fragment implements ShowHide {

    public void LoadRecCoursesExamsData(final Map<String, String> paStringMap) {
        HideMsg();
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(getContext()), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                requestQueue.stop();
                ParseJsonsHelper tc = new ParseJsonsHelper(getContext());
                HideMsg();

//                if (TutorFragCommon.this instanceof TutorFragCourse) {
//                    ArrayList<CourseModel> arrayList = tc.getCourseData(s);
//                    if (arrayList != null) {
//                        ((TutorFragCourse) TutorFragCommon.this).recCourses.setAdapter(
//                                new RecCoursesAdapter(getContext(),
//                                        arrayList,
//                                        null,
//                                        TutorFragCommon.this)
//                        );
//                    } else {
//                        MessageModel messageModel = tc.getMessageModel(s);
//                        if (messageModel != null) {
//                            ShowMsg(messageModel.getMessage());
//                        } else {
//                            ShowMsg(s);
//                        }
//                    }
//
//                }

//                if (TutorFragCommon.this instanceof TutorFragExam) {
//
//                    ArrayList<ExamModel> arrayList = tc.getExamData(s);
//                    if (arrayList != null) {
//                        ((TutorFragExam) TutorFragCommon.this).recExams.setAdapter(
//                                new RecExamsAdapter(
//                                        arrayList,
//                                        getContext(),
//                                        null,
//                                        TutorFragCommon.this
//                                )
//                        );
//                        return;
//                    }
//
//                    MessageModel messageModel = tc.getMessageModel(s);
//                    if (messageModel != null) {
//                        ShowMsg(messageModel.getMessage());
//                        return;
//                    }
//
//                    ShowMsg(s);
//                }
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

    @Override
    public void ShowMsg(String message) {
//        if (TutorFragCommon.this instanceof TutorFragExam) {
//            ((TutorFragExam) TutorFragCommon.this).pgExams.setVisibility(View.GONE);
//            ((TutorFragExam) TutorFragCommon.this).refreshLy.setVisibility(View.VISIBLE);
//            ((TutorFragExam) TutorFragCommon.this).txtMsg.setText(message);
//            return;
//        }
//        if (TutorFragCommon.this instanceof TutorFragCourse) {
//            ((TutorFragCourse) TutorFragCommon.this).pgCourses.setVisibility(View.GONE);
//            ((TutorFragCourse) TutorFragCommon.this).refreshLy.setVisibility(View.VISIBLE);
//            ((TutorFragCourse) TutorFragCommon.this).txtMsg.setText(message);
//        }
    }

    @Override
    public void HideMsg() {
//        if (TutorFragCommon.this instanceof TutorFragExam) {
//            ((TutorFragExam) TutorFragCommon.this).pgExams.setVisibility(View.GONE);
//            ((TutorFragExam) TutorFragCommon.this).refreshLy.setVisibility(View.GONE);
//            ((TutorFragExam) TutorFragCommon.this).recExams.setAdapter(new RecExamsAdapter());
//        }
//        if (TutorFragCommon.this instanceof TutorFragCourse) {
//            ((TutorFragCourse) TutorFragCommon.this).pgCourses.setVisibility(View.GONE);
//            ((TutorFragCourse) TutorFragCommon.this).refreshLy.setVisibility(View.GONE);
//            ((TutorFragCourse) TutorFragCommon.this).swipeRefresh.setRefreshing(
//                   ! ((TutorFragCourse) TutorFragCommon.this).swipeRefresh.isRefreshing()
//            );
//            ((TutorFragCourse) TutorFragCommon.this).recCourses.setAdapter(new RecCoursesAdapter());
//        }
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
}
