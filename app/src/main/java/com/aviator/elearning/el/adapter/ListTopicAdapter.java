package com.aviator.elearning.el.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.el.models.TopicsModel;
import com.rafakob.drawme.DrawMeTextView;

import java.util.ArrayList;

public class ListTopicAdapter extends BaseAdapter {

    private ArrayList<TopicsModel> arrayList;
    private Context context;

    public ListTopicAdapter(ArrayList<TopicsModel> arrayList, Context context) {
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
        TopicsModel model = (TopicsModel) getItem(position);
        convertView = CustomViews.getCustomHeader(context);
        ImageView imgImg = convertView.findViewById(R.id.imgImg);
        DrawMeTextView txtTitle = convertView.findViewById(R.id.txtTitle);
        txtTitle.setText(model.getTopic());
        return convertView;
    }
}
