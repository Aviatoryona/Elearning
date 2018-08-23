package com.aviator.elearning.el.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.CustomViews;
import com.rafakob.drawme.DrawMeTextView;

import java.util.ArrayList;

import avfont.com.aviator.aviatorfontlib.AvFonts;

public class StringArraylistAdapter extends BaseAdapter {
    private ArrayList<String> arrayList;
    Context context;

    public StringArraylistAdapter(ArrayList<String> arrayList, Context context) {
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
        String model = (String) getItem(position);
        convertView = CustomViews.getCustomHeader(context);
        ImageView imgImg = convertView.findViewById(R.id.imgImg);
        DrawMeTextView txtTitle = convertView.findViewById(R.id.txtTitle);
        txtTitle.setTypeface(AvFonts.RobotoBold(context));
        txtTitle.setText(model);
        return convertView;
    }
}
