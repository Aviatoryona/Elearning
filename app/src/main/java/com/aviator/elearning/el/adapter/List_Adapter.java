package com.aviator.elearning.el.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.List_Side_Data;
import com.aviator.elearning.el.models.List_Side_Model;

import avfont.com.aviator.aviatorfontlib.AvFonts;

public class List_Adapter extends BaseAdapter {

    //    private String[] data;
    private Context context;

    public static List_Adapter newInstance(Context context) {
        return new List_Adapter(context);
    }

    private List_Adapter(Context context) {
        this.context = context;
        //  data=this.context.getResources().getStringArray(R.array.menu);
    }

    @Override
    public int getCount() {
        return List_Side_Data.datas.size();
    }

    @Override
    public Object getItem(int position) {
        return List_Side_Data.datas.get(position);//data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"InflateParams", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        List_Side_Model list_side_model = (List_Side_Model) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.list_model, null);
        ImageView imageView = convertView.findViewById(R.id.img);
        TextView textView = convertView.findViewById(R.id.txtItem);
        textView.setTypeface(AvFonts.RobotoRegular(context));
        textView.setText(list_side_model.titleDesc);
        imageView.setImageResource(list_side_model.imgRes);
        return convertView;
    }

//    private int[] imgs = {
//            R.drawable.ic_home_black_24dp,
//            R.drawable.ic_more_horiz_black_24dp,
//            R.drawable.ic_chat_black_24dp,
//            R.drawable.ic_settings_black_24dp,
//            R.drawable.ic_help_black_24dp,
//            R.drawable.ic_question_answer_black_24dp
//    };

//    class InnerModel {
//        int imgRes;
//        String title;
//    }

}
