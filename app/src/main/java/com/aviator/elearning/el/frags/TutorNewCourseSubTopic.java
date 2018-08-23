package com.aviator.elearning.el.frags;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.CustomAnimaions;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.models.TopicsModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.rafakob.drawme.DrawMeEditText;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorNewCourseSubTopic extends Fragment implements ShowHide {
    /*
      Changed for subtopic content
     */
    public static TutorNewCourseSubTopic tutorNewCourseSubTopic;
    TopicsModel topicsModel;

    public TutorNewCourseSubTopic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tutorNewCourseSubTopic = this;
        return inflater.inflate(R.layout.fragment_tutor_new_course_sub_topic, container, false);
    }

    void setTopicsModel(TopicsModel topicsModel1) {
        txtBlanket.setVisibility(View.GONE);
        txtTopic.setText(topicsModel.getTopic());
        this.topicsModel = topicsModel1;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        AfterInit();
    }

    @Override
    public void ShowMsg(String message) {
        txtBlanket.setVisibility(View.GONE);
    }

    @Override
    public void HideMsg() {
        if (txtBlanket.getVisibility() == View.VISIBLE)
            txtBlanket.setVisibility(View.GONE);

        else
            txtBlanket.setVisibility(View.VISIBLE);
    }

    @Override
    public void Pause_() {

    }

    @Override
    public void Resume_() {

    }

    void AddSubTopicsDb() {
        assert topicsModel != null;
        Map<String, String> params = new HashMap<>();
        params.put("target", "courses");
        params.put("action", "addsubtopc");
        params.put("topicid", String.valueOf(topicsModel.getTopicid()));
        params.put("subtopic", edtSubTopic.getText().toString());
        params.put("content", edtContent.getText().toString());
        AddCourseSubTopic_DB(params);
    }

    @Override
    public void AfterInit() {
        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtSubTopic.getText().toString())) {
                    edtSubTopic.setError("Fill in here *");
                    return;
                }

                if (TextUtils.isEmpty(edtContent.getText().toString())) {
                    edtContent.setError("Fill in here *");
                    return;
                }

                AddSubTopicsDb();
            }
        });

        imgFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void AddCourseSubTopic_DB(final Map<String, String> paStringStringMap) {
        txtBlanket.setVisibility(View.VISIBLE);
        txtBlanket.setText("Please wait....");
        txtBlanket.setOnClickListener(null);

        final RequestQueue requestQueue = VolleySingleton.volleyInstance(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(getContext()), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();

                MessageModel messageModel = new ParseJsonsHelper(getContext()).getMessageModel(s);
                if (messageModel != null) {
                    if (!messageModel.isError()) {
                        edtContent.setText("");
                        edtSubTopic.setText("");

                        txtBlanket.setText(messageModel.getMessage());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                txtBlanket.startAnimation(CustomAnimaions.FadeOut(getContext()));
                                txtBlanket.setVisibility(View.GONE);
                                txtBlanket.setText("");
                            }
                        }, 1000);

                        return;
                    }
                    txtBlanket.setVisibility(View.GONE);
                    Toast.makeText(getContext(), messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                txtBlanket.setVisibility(View.VISIBLE);
                txtBlanket.setText(SpaceCraft.CONNECTION_ERROR_MSG + "\n\nclick to retry");
                txtBlanket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddCourseSubTopic_DB(paStringStringMap);
                    }
                });
//                ShowMsg("Internet Connection Error");
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


    private TextView txtTopic;
    private EditText edtSubTopic;
    private DrawMeEditText edtContent;
    private ImageView imgFile;
    private ImageView imgSave;
    private TextView txtBlanket;

    public void initViews(View view) {
        txtTopic = (TextView) view.findViewById(R.id.txtTopic);
        edtSubTopic = (EditText) view.findViewById(R.id.edtSubTopic);
        edtContent = (DrawMeEditText) view.findViewById(R.id.edtContent);
        imgFile = (ImageView) view.findViewById(R.id.imgFile);
        imgSave = (ImageView) view.findViewById(R.id.imgSave);
        txtBlanket = (TextView) view.findViewById(R.id.txtBlanket);
    }


}
