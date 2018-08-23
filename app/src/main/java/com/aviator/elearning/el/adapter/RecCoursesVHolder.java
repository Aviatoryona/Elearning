package com.aviator.elearning.el.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aviator.elearning.ExploreCourse;
import com.aviator.elearning.MainActivity;
import com.aviator.elearning.R;
import com.aviator.elearning.el.frags.TutorFragCourse;
import com.aviator.elearning.el.models.CourseModel;
import com.lid.lib.LabelImageView;
import com.rafakob.drawme.DrawMeButton;

import avfont.com.aviator.aviatorfontlib.AvFontHelper;

class RecCoursesVHolder extends RecyclerView.ViewHolder {
    public LabelImageView coverImage;
    public DrawMeButton btnTitle;
    public TextView txtTitle, btnStart;
    public Context context;
    public AppCompatActivity appCompatActivity;
    public Fragment fragment;
    public CourseModel courseModel;

    @SuppressLint("SetTextI18n")
    RecCoursesVHolder(View itemView) {
        super(itemView);
        coverImage = itemView.findViewById(R.id.coverImg);
        btnStart = itemView.findViewById(R.id.btnStart);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        btnTitle = itemView.findViewById(R.id.btnTitle);

        btnTitle.setTypeface(AvFontHelper.nucleooutline(context));
        txtTitle.setTypeface(AvFontHelper.nucleooutline(context));

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (appCompatActivity instanceof MainActivity) {
//                    Toast.makeText(context, "MainActivity", Toast.LENGTH_SHORT).show();
                    ExploreCourse();
//                }

            }
        });

        btnTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (appCompatActivity instanceof MainActivity) {
//                    Toast.makeText(context, "MainActivity", Toast.LENGTH_SHORT).show();
                    ExploreCourse();
//                }
            }
        });

    }

    private void ExploreCourse() {
        Bundle bundle = new Bundle();
        bundle.putString("COURSEID", courseModel.getC_uniqid());
        bundle.putString("COVER",courseModel.getC_coverimg());
        bundle.putString("COURSE",courseModel.getC_name());
        Intent intent = new Intent(context, ExploreCourse.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
