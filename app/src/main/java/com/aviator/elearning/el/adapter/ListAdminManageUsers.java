package com.aviator.elearning.el.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.CustomAnimaions;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.el.models.UserModel;
import com.rafakob.drawme.DrawMeTextView;

import java.util.ArrayList;

public class ListAdminManageUsers extends BaseAdapter {
    private ArrayList<UserModel> courseExamModels;
    Context context;

    public ListAdminManageUsers(ArrayList<UserModel> courseExamModels, Context context) {
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
        UserModel model = (UserModel) getItem(position);
        convertView = CustomViews.getCustomHeader(context);
        ImageView imgImg = convertView.findViewById(R.id.imgImg);
        imgImg.setImageResource(R.drawable.ic_person_black_24dp);
        DrawMeTextView txtTitle = convertView.findViewById(R.id.txtTitle);
        String stringBuilder = model.getUser_name() + "\n" +
                "Contact: " + "<a href=''><font color='#12edf0'>" + model.getUser_contact() + "</font></a>\n" +
                "Email: " + "<b><font color='#12edf0'>" + model.getUser_email() + "</font></b>\n" +
                "Country: " + model.getCountry();
        txtTitle.setText(Html.fromHtml(stringBuilder));
        convertView.setBackground(new ColorDrawable(context.getResources().getColor(R.color.dark_red)));
        convertView.startAnimation(CustomAnimaions.BottomFromTop(context));
        return convertView;
    }
}
