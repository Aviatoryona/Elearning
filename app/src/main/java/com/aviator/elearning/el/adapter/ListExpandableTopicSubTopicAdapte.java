package com.aviator.elearning.el.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.CustomAnimaions;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.el.models.SubtopicsModel;
import com.aviator.elearning.el.models.TopicSubtopicModel;
import com.rafakob.drawme.DrawMeTextView;

import java.util.ArrayList;

import avfont.com.aviator.aviatorfontlib.AvFonts;

public class ListExpandableTopicSubTopicAdapte extends BaseExpandableListAdapter {
    private ArrayList<TopicSubtopicModel> topicSubtopicModels;
    Context context;

    public ListExpandableTopicSubTopicAdapte(ArrayList<TopicSubtopicModel> topicSubtopicModels, Context context) {
        this.topicSubtopicModels = topicSubtopicModels;
        this.context = context;
    }

    public ArrayList<TopicSubtopicModel> getTopicsModel(){
        return topicSubtopicModels;
    }

    @Override
    public int getGroupCount() {
        return topicSubtopicModels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<SubtopicsModel> arrayList=(topicSubtopicModels.get(groupPosition)).getSubtopicsModelArrayList();
        if(arrayList==null){
            return 0;
        }
        if (arrayList.isEmpty())return 0;
        return (topicSubtopicModels.get(groupPosition)).getSubtopicsModelArrayList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return topicSubtopicModels.get(groupPosition); //returns TopicSubtopicModel
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ((topicSubtopicModels.get(groupPosition)).getSubtopicsModelArrayList()).get(childPosition);//returns SubtopicsModel
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TopicSubtopicModel model = (TopicSubtopicModel) getGroup(groupPosition);
        convertView = CustomViews.getCustomHeader(context);
        ImageView imgImg = convertView.findViewById(R.id.imgImg);
        DrawMeTextView txtTitle = convertView.findViewById(R.id.txtTitle);
        txtTitle.setTypeface(AvFonts.RobotoBold(context));
        txtTitle.setText(model.getTopicsModel().getTopic());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubtopicsModel model = (SubtopicsModel) getChild(groupPosition, childPosition);
        convertView = CustomViews.getCustomHeader(context);
        ImageView imgImg = convertView.findViewById(R.id.imgImg);
        DrawMeTextView txtTitle = convertView.findViewById(R.id.txtTitle);
        txtTitle.setTypeface(AvFonts.RobotoBold(context));
        txtTitle.setText(model.getCst_subtopic());
        convertView.setBackground(new ColorDrawable(context.getResources().getColor(R.color.dark_red)));
        convertView.startAnimation(CustomAnimaions.BottomFromTop(context));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
