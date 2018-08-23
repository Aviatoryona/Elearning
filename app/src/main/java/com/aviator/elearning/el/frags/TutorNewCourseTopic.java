package com.aviator.elearning.el.frags;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aviator.elearning.R;
import com.aviator.elearning.TutorAddCourseContent;
import com.aviator.elearning.TutorNewCourse;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.el.adapter.ListTopicAdapter;
import com.aviator.elearning.el.db.DbHelper;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.TopicsModel;
import com.rafakob.drawme.DrawMeTextView;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import avfont.com.aviator.aviatorfontlib.AvFonts;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class TutorNewCourseTopic extends Fragment implements ShowHide {
    /*
      Enter topic and subtopic

     */
    static String UNIQID, COURSENAME;

    DbHelper dbHelper;

    public TutorNewCourseTopic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutor_new_course_topic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("UNIQID")) {
                UNIQID = bundle.getString("UNIQID");
            }
            if (bundle.containsKey("NAME")) {
                COURSENAME = bundle.getString("NAME");
            }
        }

//        dbHelper = new DbHelper(getContext());
        initViews(view);
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

    @Override
    public void AfterInit() {

        imgAddTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtTopic.getText().toString())) {
                    edtTopic.setError("Required *");
                    return;
                }

//                if (dbHelper.InsertTopic(UNIQID, edtTopic.getText().toString())) {
//                    TastyToast.makeText(getContext(), "Topic Added", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();
//                    edtTopic.setText("");
//                    return;
//                }
                if (UNIQID == null) {
                    TastyToast.makeText(getContext(), "Failed, try again, UNIDQUE ID required", TastyToast.LENGTH_LONG, TastyToast.ERROR).show();
                }

                Map<String, String> params = new HashMap<>();
                params.put("target", "courses");
                params.put("action", "addtopics");
                params.put("courseuniqid", UNIQID);
                params.put("topic", edtTopic.getText().toString());
                TutorNewCourse.getInstance().AddTopicDb(params);
//                AddSubTopicDg(edtTopic.getText().toString());

            }
        });

    }

    public void SyncTopics(final ArrayList<TopicsModel> topicsModels) {
        assert topicsModels != null;
        assert !topicsModels.isEmpty();
        edtTopic.setText("");
        listTopics.setAdapter(new ListTopicAdapter(topicsModels, getContext()));
        listTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                TopicsModel topicsModel= (TopicsModel) parent.getAdapter().getItem(position);
//                TutorNewCourseSubTopic.tutorNewCourseSubTopic.setTopicsModel(topicsModel);

                bundle.putString("topicid",String.valueOf(topicsModel.getTopicid()));
                bundle.putString("topic",topicsModel.getTopic());
                Intent intent=new Intent(getContext(), TutorAddCourseContent.class);
                intent.putExtras(bundle);
                startActivity(intent);
//                TutorNewCourse.tutorNewCourse.MoveToPage(1);
            }
        });
    }

    void AddSubTopicDg(String topic) {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(getContext());
        alBuilder.setCancelable(true);


        View view = CustomViews.addSubTopicDg(getContext());
        TextView txtTopic = view.findViewById(R.id.txtTopic);
        txtTopic.setTypeface(AvFonts.RobotoBold(getContext()));
        txtTopic.setText(topic);
        ImageView imgAddSubTopic = view.findViewById(R.id.imgAddSubTopic);
        final EditText edtSubTopic = view.findViewById(R.id.edtSubTopic);

        alBuilder.setView(view);
        final AlertDialog alertDialog = alBuilder.create();
        imgAddSubTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtSubTopic.getText().toString())) {
                    edtSubTopic.setError("Required *");
                    return;
                }
                edtSubTopic.setText("");
                edtSubTopic.requestFocus();
                Toast.makeText(getContext(), "Sub topic added", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();
    }

    private EditText edtTopic;
    private ImageView imgAddTopic;
    private ListView listTopics;

    public void initViews(View view) {
        edtTopic = (EditText) view.findViewById(R.id.edtTopic);
        imgAddTopic = (ImageView) view.findViewById(R.id.imgAddSubTopic);
        listTopics = (ListView) view.findViewById(R.id.listTopics);
    }

}
