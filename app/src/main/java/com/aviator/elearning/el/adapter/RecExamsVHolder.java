package com.aviator.elearning.el.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviator.elearning.ExploreExam;
import com.aviator.elearning.R;
import com.aviator.elearning.el.models.CourseExamModel;
import com.aviator.elearning.el.models.CourseModel;
import com.aviator.elearning.el.models.ExamModel;
import com.lid.lib.LabelButtonView;

import java.util.ArrayList;

@SuppressWarnings("ALL")
class RecExamsVHolder extends RecyclerView.ViewHolder {
    ArrayList<CourseExamModel> courseExamModels;
    public Context context;
    public AppCompatActivity appCompatActivity;
    public Fragment fragment;
    public ExamModel examModel;
    public CourseModel courseModel;

    public LabelButtonView titleBtn;
    public ImageView coverImg, imgMore;
    private TextView txtExplore;

    RecExamsVHolder(@NonNull View itemView) {
        super(itemView);
        titleBtn = itemView.findViewById(R.id.titleBtn);
        coverImg = itemView.findViewById(R.id.coverImg);
        imgMore = itemView.findViewById(R.id.imgMore);
        txtExplore = itemView.findViewById(R.id.txtExplore);

        coverImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExploreExam();
//                Toast.makeText(context, "Explore Exam", Toast.LENGTH_SHORT).show();
            }
        });

        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExploreExam();
//                Toast.makeText(context, "Exam Details", Toast.LENGTH_SHORT).show();
            }
        });

        txtExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExploreExam();
//                Toast.makeText(context, "Explore", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ExploreExam() {
        Bundle bundle = new Bundle();
        bundle.putString("COVER", examModel.getE_coverimg());
        bundle.putString("COURSE", courseModel.getC_name());
        bundle.putString("COURSEUNIQID", courseModel.getC_uniqid());
        bundle.putString("EXAMUINQID", examModel.getE_examuniqid());
        bundle.putDouble("DURATION", examModel.getE_duration());
//        bundle.putParcelable("EXAM", examModel);
//        bundle.putParcelable("COURSE",courseModel);
//        bundle.putParcelableArrayList("CourseExamModel",courseExamModels);
        Intent intent = new Intent(context, ExploreExam.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
