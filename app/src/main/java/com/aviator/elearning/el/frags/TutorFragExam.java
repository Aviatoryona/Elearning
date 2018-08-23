package com.aviator.elearning.el.frags;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.MyDecor;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.adapter.RecExamsAdapter_Main;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.CourseExamModel;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class TutorFragExam extends  //TutorFragCommon
        Fragment implements ShowHide {

    MyPreferences myPreferences;

    public TutorFragExam() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myPreferences = new MyPreferences(getContext());
        return inflater.inflate(R.layout.fragment_tutor_frag_exam, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if (stackExams == null) {
            stackExams = new Stack<>();
        }
        AfterInit();

        if (stackExams.isEmpty()) {
            if (myPreferences == null) {
                Snackbar.make(parent, "Error encountered", Snackbar.LENGTH_LONG).show();
                return;
            }
            LoadRecExamData();
        }
    }

    @Override
    public void AfterInit() {
        recExams.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recExams.setItemAnimator(new DefaultItemAnimator());
        recExams.addItemDecoration(new MyDecor(5));

        refreshLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPreferences == null) {
                    Snackbar.make(parent, "Error encountered", Snackbar.LENGTH_LONG).show();
                    return;
                }

                LoadRecExamData();
            }
        });
    }

    void LoadRecExamData() {
        Map<String, String> paMap = new HashMap<>();
        paMap.put("target", "tutor");
        paMap.put("action", "getexams");
        paMap.put("usertype", String.valueOf(1));
        paMap.put("email", myPreferences.getEmail());

        LoadRecCoursesExamsData(paMap);
    }

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
//                ArrayList<ExamModel> arrayList = tc.getExamData(s);
                ArrayList<CourseExamModel> arrayList = tc.getCourseExamModel(s);
                if (arrayList != null) {
                    recExams.setAdapter(
//                            new RecExamsAdapter(
//                                    arrayList,
//                                    getContext(),
//                                    null,
//                                    TutorFragExam.this)
                            new RecExamsAdapter_Main(arrayList,
                                    getContext(),
                                    null,
                                    null)
                    );
                } else {
                    MessageModel messageModel = tc.getMessageModel(s);
                    if (messageModel != null) {
                        ShowMsg(messageModel.getMessage());
                    } else {
                        ShowMsg(s);
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

    @Override
    public void onPause() {
        super.onPause();
        Pause_();
    }

    @Override
    public void onResume() {
        super.onResume();

        Resume_();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Resume_();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Pause_();
    }

    @Override
    public void ShowMsg(String message) {
        pgExams.setVisibility(View.GONE);
        refreshLy.setVisibility(View.VISIBLE);
        txtMsg.setText(message);
    }

    @Override
    public void HideMsg() {
        pgExams.setVisibility(View.GONE);
        refreshLy.setVisibility(View.GONE);
    }

    @Override
    public void Pause_() {
        stackExams.clear();
        if (recExams.getAdapter() != null) {
            if (recExams.getAdapter() instanceof
//                    RecExamsAdapter
                    RecExamsAdapter_Main
                    ) {
//                stackExams.push((ArrayList<ExamModel>) ((RecExamsAdapter) recExams.getAdapter()).getExamModelArrayList());
                stackExams.push((ArrayList<CourseExamModel>)((RecExamsAdapter_Main)recExams.getAdapter()).getCourseExamModels());
            }
        }
    }

    @Override
    public void Resume_() {

        if (stackExams.isEmpty()) {
            LoadRecExamData();
            return;
        }

//        ArrayList<ExamModel> models = stackExams.pop();
        ArrayList<CourseExamModel> models = stackExams.pop();
        if (models != null) {
            recExams.setAdapter(
//                    new RecExamsAdapter(models, getContext(), null, TutorFragExam.this)
                    new RecExamsAdapter_Main(
                            models,
                            getContext(),
                            null,
                            TutorFragExam.this)
            );
        } else {
            LoadRecExamData();
        }

    }


    public FrameLayout parent;
    public SwipeRefreshLayout swipeRefresh;
    public RecyclerView recExams;
    public LinearLayout refreshLy;
    public TextView txtMsg;
    public GoogleProgressBar pgExams;

    public void initViews(View view) {
        parent = (FrameLayout) view.findViewById(R.id.parent);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        recExams = (RecyclerView) view.findViewById(R.id.recExams);
        refreshLy = (LinearLayout) view.findViewById(R.id.refreshLy);
        txtMsg = (TextView) view.findViewById(R.id.txtMsg);
        pgExams = (GoogleProgressBar) view.findViewById(R.id.pgExams);
    }

    //    static Stack<ArrayList<ExamModel>> stackExams;// = new Stack<>();
    static Stack<ArrayList<CourseExamModel>> stackExams;
}
