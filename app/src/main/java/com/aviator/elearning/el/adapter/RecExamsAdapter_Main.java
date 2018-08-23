package com.aviator.elearning.el.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.models.CourseExamModel;
import com.aviator.elearning.el.models.CourseModel;
import com.aviator.elearning.el.models.ExamModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecExamsAdapter_Main extends RecyclerView.Adapter<RecExamsVHolder> {
    private ArrayList<CourseExamModel> courseExamModels;
    private Context context;
    private AppCompatActivity appCompatActivity;
    private Fragment fragment;

    public RecExamsAdapter_Main(ArrayList<CourseExamModel> courseExamModels, Context context, AppCompatActivity appCompatActivity, Fragment fragment) {
        this.courseExamModels = courseExamModels;
        this.context = context;
        this.appCompatActivity = appCompatActivity;
        this.fragment = fragment;
    }

    public ArrayList<CourseExamModel> getCourseExamModels() {
        return courseExamModels;
    }

    @NonNull
    @Override
    public RecExamsVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.examrecmodel, parent, false);
        return new RecExamsVHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecExamsVHolder holder, int position) {
        CourseExamModel courseExamModel = courseExamModels.get(position);

        ExamModel examModel = courseExamModel.getExamModel();
        CourseModel courseModel = courseExamModel.getCourseModel();
        holder.courseExamModels = courseExamModels;
        holder.examModel = examModel;
        holder.courseModel = courseModel;
        holder.context = context;
        holder.fragment = fragment;
        holder.appCompatActivity = appCompatActivity;
        holder.titleBtn.setText(courseModel.getC_name());

        String url;
        if (examModel.getE_coverimg() != null) {
            url = new SpaceCraft().getUrl(context) + "exams/" + examModel.getE_coverimg();
        } else {
            url = new SpaceCraft().getUrl(context) + "courses/" + courseModel.getC_coverimg();
        }
        Glide.with(context).load(Uri.parse(url)).placeholder(R.mipmap.bgblue).into(holder.coverImg);
    }

    @Override
    public int getItemCount() {
        return courseExamModels.size();
    }
}
