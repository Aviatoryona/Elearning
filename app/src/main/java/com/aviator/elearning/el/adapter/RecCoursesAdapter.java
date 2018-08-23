package com.aviator.elearning.el.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aviator.elearning.MainActivity;
import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.frags.TutorFragCourse;
import com.aviator.elearning.el.models.CourseModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class RecCoursesAdapter extends RecyclerView.Adapter<RecCoursesVHolder> {

    private Context context;
    private AppCompatActivity appCompatActivity;
    private Fragment fragment;
    private ArrayList<CourseModel> courseModelArrayList;

    public RecCoursesAdapter(Context context, ArrayList<CourseModel> courseModelArrayList, AppCompatActivity appCompatActivity, Fragment fragment) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
        this.appCompatActivity = appCompatActivity;
        this.fragment = fragment;
    }

    public ArrayList<CourseModel> getCourseModelArrayList() {
        return courseModelArrayList;
    }

    @NonNull
    @Override
    public RecCoursesVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView;
        if (appCompatActivity instanceof MainActivity) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.courserecmodelships, parent, false);
        } else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.courserecmodel, parent, false);

        }
        return new RecCoursesVHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecCoursesVHolder holder, int position) {
        CourseModel courseModel = courseModelArrayList.get(position);
        holder.courseModel = courseModel;
        holder.fragment = fragment;
        holder.appCompatActivity = appCompatActivity;
        holder.context = context;

        holder.txtTitle.setText(courseModel.getC_name());
        holder.btnTitle.setText(courseModel.getC_name());
        if (fragment instanceof TutorFragCourse) {
            holder.btnStart.setBackground(new ColorDrawable(context.getResources().getColor(R.color.dark_red)));
            holder.btnStart.setText("View");
        }
        if (context != null) {
            String url = new SpaceCraft().getUrl(context) + "courses/" + courseModel.getC_coverimg();
            Glide.with(context).load(Uri.parse(url)).placeholder(R.mipmap.book).into(holder.coverImage);
        }

    }

    @Override
    public int getItemCount() {
        return courseModelArrayList.size();
    }
}
