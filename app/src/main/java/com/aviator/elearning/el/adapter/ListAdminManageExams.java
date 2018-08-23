package com.aviator.elearning.el.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.CustomAnimaions;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.el.models.CourseExamModel;
import com.rafakob.drawme.DrawMeTextView;

import java.util.ArrayList;

public class ListAdminManageExams extends BaseAdapter {
    private ArrayList<CourseExamModel> courseExamModels;
    Context context;

    public ListAdminManageExams(ArrayList<CourseExamModel> courseExamModels, Context context) {
        this.courseExamModels = courseExamModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return courseExamModels.size();
    }

    @Override
    public Object getItem(int position) {
        return courseExamModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CourseExamModel model = (CourseExamModel) getItem(position);
        convertView = CustomViews.getCustomHeader(context);
        ImageView imgImg = convertView.findViewById(R.id.imgImg);
        imgImg.setImageResource(R.drawable.ic_more_vert_black_24dp);
        DrawMeTextView txtTitle = convertView.findViewById(R.id.txtTitle);
        String status;
        if (model.getExamModel().getE_status().contains("1")) {
            status = "Approved";
        } else {
            status = "Unapproved";
        }
        String string = "Course: " + model.getCourseModel().getC_name() + "\n" +
                "Approval Status: " + status;
        txtTitle.setText(string);
        convertView.startAnimation(CustomAnimaions.ZoomIn(context));
        return convertView;
    }
}
