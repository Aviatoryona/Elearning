package com.aviator.elearning.el.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.CustomAnimaions;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.el.models.CourseModel;
import com.rafakob.drawme.DrawMeTextView;

import java.util.ArrayList;

public class ListAdminManageCourses extends BaseAdapter {
    private ArrayList<CourseModel> arrayList;
    private Context context;

    public ListAdminManageCourses(ArrayList<CourseModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CourseModel model = (CourseModel) getItem(position);
        convertView = CustomViews.getCustomHeader(context);
        ImageView imgImg = convertView.findViewById(R.id.imgImg);
        imgImg.setImageResource(R.drawable.ic_more_vert_black_24dp);
        String status;
        if (model.getC_approved().contains("1")) {
            status = "Approved";
        } else {
            status = "Unapproved";
        }
        String string = "Course: " + model.getC_name() + "\n" +
                "Approval Status: " + status;
        DrawMeTextView txtTitle = convertView.findViewById(R.id.txtTitle);
        txtTitle.setText(string);
        convertView.startAnimation(CustomAnimaions.FadeIn(context));
        return convertView;
    }
}
