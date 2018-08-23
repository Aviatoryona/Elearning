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
import com.aviator.elearning.el.models.ExamModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecExamsAdapter extends RecyclerView.Adapter<RecExamsVHolder> {
    private ArrayList<ExamModel> examModelArrayList;
    private Context context;
    private AppCompatActivity appCompatActivity;
    private Fragment fragment;

    public RecExamsAdapter(ArrayList<ExamModel> examModelArrayList, Context context, AppCompatActivity appCompatActivity, Fragment fragment) {
        this.examModelArrayList = examModelArrayList;
        this.context = context;
        this.appCompatActivity = appCompatActivity;
        this.fragment = fragment;
    }

    public ArrayList<ExamModel> getExamModelArrayList() {
        return examModelArrayList;
    }

    @NonNull
    @Override
    public RecExamsVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.examrecmodel, parent, false);
        return new RecExamsVHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecExamsVHolder holder, int position) {
        ExamModel examModel = examModelArrayList.get(position);
        holder.examModel = examModel;
        holder.courseModel = null;
        holder.context = context;
        holder.fragment = fragment;
        holder.appCompatActivity = appCompatActivity;


        String url = new SpaceCraft().getUrl(context) + "exams/" + examModel.getE_coverimg();
        Glide.with(context).load(Uri.parse(url)).placeholder(R.mipmap.bgblue).into(holder.coverImg);

    }

    @Override
    public int getItemCount() {
        return examModelArrayList.size();
    }

}
