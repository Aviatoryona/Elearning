package com.aviator.elearning.el.CommonsPkg;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.TutorNewCourse;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.models.TopicsModel;
import com.aviator.elearning.el.net.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class TutorCourseExamCommon extends AppCompatActivity implements ShowHide {

    View view;

    public void AddCourseTopic_DB(final Map<String, String> paStringStringMap) {
        HideMsg();

        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();

                MessageModel messageModel = new ParseJsonsHelper(TutorCourseExamCommon.this).getMessageModel(s);
                if (messageModel != null) {
                    ShowMsg(messageModel.getMessage());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadCourseTopics(paStringStringMap);
                        }
                    }, 2000);
                } else {
                    ShowMsg(s);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                ShowMsg("Internet Connection Error");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return paStringStringMap;
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

                ParseJsonsHelper parseJsonsHelper = new ParseJsonsHelper(TutorCourseExamCommon.this);
                ArrayList<TopicsModel> topicsModelArrayList = parseJsonsHelper.getTopics(s);
                if (topicsModelArrayList != null) {

                    if (TutorCourseExamCommon.this instanceof TutorNewCourse) {
                        ((TutorNewCourse) TutorCourseExamCommon.this).SyncTopics(topicsModelArrayList);
                    }
                    return;
                }

                ShowMsg("Error syncing topics");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                ShowMsg("Error syncing topics");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> paMap = new HashMap<>(paStringStringMap);
                paMap.remove("action");
                paMap.put("action", "gettopics");
                return paMap;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();

    }

    @Override
    public void ShowMsg(String message) {
        if (this instanceof TutorNewCourse) {
            ((TutorNewCourse) this).pgPg.setVisibility(View.GONE);
            assert view != null;
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void HideMsg() {
        if (this instanceof TutorNewCourse) {
            view = ((TutorNewCourse) this).parent;
            if (((TutorNewCourse) this).pgPg.getVisibility() == View.GONE) {
                ((TutorNewCourse) this).pgPg.setVisibility(View.VISIBLE);
                return;
            }
            ((TutorNewCourse) this).pgPg.setVisibility(View.GONE);
        }
    }
}
