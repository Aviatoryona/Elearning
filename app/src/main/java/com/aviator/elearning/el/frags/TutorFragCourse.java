package com.aviator.elearning.el.frags;

//unused

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.aviator.elearning.el.adapter.RecCoursesAdapter;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.CourseModel;
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
public class TutorFragCourse extends Fragment implements ShowHide {

    MyPreferences myPreferences;

    public TutorFragCourse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myPreferences = new MyPreferences(getContext());
        return inflater.inflate(R.layout.fragment_tutor_frag_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if (stack==null){
            stack=new Stack<>();
        }

        AfterInit();

        if (stack.isEmpty()) {
            if (myPreferences == null) {
                Snackbar.make(parent, "Error encountered", Snackbar.LENGTH_LONG).show();
                return;
            }
            LoadRecCourseData();
        }
    }


    @Override
    public void AfterInit() {
        recCourses.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recCourses.setItemAnimator(new DefaultItemAnimator());
        recCourses.addItemDecoration(new MyDecor(5));

        refreshLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPreferences == null) {
                    Snackbar.make(parent, "Error encountered", Snackbar.LENGTH_LONG).show();
                    return;
                }

                LoadRecCourseData();
            }
        });
    }

    void LoadRecCourseData() {
        Map<String, String> paMap = new HashMap<>();
        paMap.put("target", "tutor");
        paMap.put("action", "getcourses");
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
                ArrayList<CourseModel> arrayList = tc.getCourseData(s);
                if (arrayList != null) {
                    recCourses.setAdapter(
                                new RecCoursesAdapter(getContext(),
                                        arrayList,
                                        null,
                                        TutorFragCourse.this
                            )
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

    }

    @Override
    public void HideMsg() {

    }

    @Override
    public void Pause_() {
        stack.clear();
        if (recCourses.getAdapter() != null) {
            if (recCourses.getAdapter() instanceof RecCoursesAdapter) {
                stack.push((ArrayList<CourseModel>) ((RecCoursesAdapter) recCourses.getAdapter()).getCourseModelArrayList());
            }
        }
    }

    @Override
    public void Resume_() {

        if (stack.isEmpty()) {
            LoadRecCourseData();
        } else {
            ArrayList<CourseModel> models = stack.pop();
            if (models != null) {
                recCourses.setAdapter(new RecCoursesAdapter(getContext(), models, null, TutorFragCourse.this));
            } else {
                LoadRecCourseData();
            }
        }
    }


    public FrameLayout parent;
    public SwipeRefreshLayout swipeRefresh;
    public RecyclerView recCourses;
    public LinearLayout refreshLy;
    public TextView txtMsg;
    public GoogleProgressBar pgCourses;

    public void initViews(View view) {
        parent = (FrameLayout) view.findViewById(R.id.parent);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        recCourses = (RecyclerView) view.findViewById(R.id.recCourses);
        refreshLy = (LinearLayout) view.findViewById(R.id.refreshLy);
        txtMsg = (TextView) view.findViewById(R.id.txtMsg);
        pgCourses = (GoogleProgressBar) view.findViewById(R.id.pgCourses);
    }

    static Stack<ArrayList<CourseModel>> stack ;
}
