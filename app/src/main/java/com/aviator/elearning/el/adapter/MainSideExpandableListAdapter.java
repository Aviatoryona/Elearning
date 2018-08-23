package com.aviator.elearning.el.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.HttpLoadData;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.models.CourseModel;
import com.aviator.elearning.el.models.NetParamsModel;
import com.rafakob.drawme.DrawMeTextView;

import java.util.ArrayList;

public class MainSideExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private String[] who;
    private ArrayList<CourseModel> arrayList;

//    private final String[] childs = {
//            "Software Testing & Validation",
//            "Design Patterns",
//            "Coreal Draw",
//            "Nutritional Science"};


    public MainSideExpandableListAdapter(Context context, String[] who) {
        this.context = context;
        this.who = who;
    }

    public MainSideExpandableListAdapter(Context context, ArrayList<CourseModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public static MainSideExpandableListAdapter getInstance(Context context, String[] who) {
        return new MainSideExpandableListAdapter(context, who);
    }

    public static MainSideExpandableListAdapter getInstance(Context context, ArrayList<CourseModel> arrayList) {
        return new MainSideExpandableListAdapter(context, arrayList);
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (arrayList != null) {
            return arrayList.size();
        }
        if (who != null) {
            return who.length;
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (arrayList != null) {
            return arrayList.get(childPosition);
        }
        if (who != null) {
            return who[childPosition];
        }
        return null;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_list_model, parent, false);
        DrawMeTextView drawMeTextView = convertView.findViewById(R.id.txtTitle);
        drawMeTextView.setText("My Courses");
        return convertView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_list_child, parent, false);
        DrawMeTextView drawMeTextView = convertView.findViewById(R.id.txtTitle);
        drawMeTextView.setTextColor(context.getResources().getColor(R.color.fbutton_color_orange));
        if (arrayList != null) {
//            return arrayList.get(childPosition);
            drawMeTextView.setText(((CourseModel) getChild(groupPosition, childPosition)).getC_name());
        } else if (who != null) {
//            return who[childPosition];
            drawMeTextView.setText((String) getChild(groupPosition, childPosition));
            drawMeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Load courses for " + getChild(groupPosition, childPosition), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public static void LoadData(final @NonNull int who, final @NonNull Context context1, final @NonNull ExpandableListView listView, final @NonNull MyPreferences myPreferences) {
        String s = HttpLoadData.LoadData(context1, getParamsModels(who, myPreferences));
        if (s != null) {
            ArrayList<CourseModel> arrayList = new ParseJsonsHelper(context1).getCourseData(s);
            if (arrayList != null) {
                listView.setAdapter(MainSideExpandableListAdapter.getInstance(context1, arrayList));
            } else {
                Log.e(SpaceCraft.STRING_DATA, "Empty arraylist for my courses adapter");
            }
        } else {
            Log.e(SpaceCraft.STRING_DATA, "Failed loading data");
        }
    }

    @SuppressLint("Assert")
    private static ArrayList<NetParamsModel> getParamsModels(int who, MyPreferences myPreferences) {
        ArrayList<NetParamsModel> paramsModels = new ArrayList<>();
        NetParamsModel netParamsModel = new NetParamsModel();
        netParamsModel.setKey("target");
        netParamsModel.setValue("tutor");//tutor/student
        paramsModels.add(netParamsModel);

        netParamsModel = new NetParamsModel();
        netParamsModel.setKey("action");
        netParamsModel.setValue("getcourses");
        paramsModels.add(netParamsModel);

        netParamsModel = new NetParamsModel();
        netParamsModel.setKey("usertype");
        netParamsModel.setValue(String.valueOf(who));//examModel.getE_examuniqid()
        paramsModels.add(netParamsModel);

        netParamsModel = new NetParamsModel();
        netParamsModel.setKey("email");
        assert myPreferences != null;
        netParamsModel.setValue(myPreferences.getEmail());//examModel.getE_examuniqid()
        paramsModels.add(netParamsModel);
        return paramsModels;
    }
}