package com.aviator.elearning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.aviator.main.CustomAnimaions;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.rafakob.drawme.DrawMeEditText;

import java.util.HashMap;
import java.util.Map;

public class TutorAddCourseContent extends AppCompatActivity implements ShowHide {

    View view;
    String TOPICID, TOPIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_tutor_add_course_content, null, false);
        setContentView(view);
        initViews(view);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Toast.makeText(this, "Error encountered", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (!bundle.containsKey("topicid")) {
            Toast.makeText(this, "Error encountered", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (bundle.containsKey("topic")) {
            TOPIC = bundle.getString("topic");
            txtTopic.setText(TOPIC);
        }

        TOPICID = bundle.getString("topicid");
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        Map<String, String> params = new HashMap<>();
        params.put("target", "courses");
        params.put("action", "addsubtopc");
        params.put("topicid", TOPICID);
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

    Context getContext() {
        return TutorAddCourseContent.this;
    }

    @SuppressLint("SetTextI18n")
    public void AddCourseSubTopic_DB(final Map<String, String> paStringStringMap) {
        txtBlanket.setVisibility(View.GONE);
        txtBlanket.setText("Please wait....");
        txtBlanket.setOnClickListener(null);

        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
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
