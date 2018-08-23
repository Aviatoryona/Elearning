package com.aviator.elearning.aviator.main;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aviator.elearning.R;
import com.rafakob.drawme.DrawMeTextView;

@SuppressWarnings("ALL")
public class CustomViews {

    public static View getCustomHeader(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.custom_header, null, false);
    }

    public static View getCustomListView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.listview, null, false);
    }

    public static View addSubTopicDg(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.dg_add_sub_topic, null, false);
    }

    public static View customSpinner(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.spinnner_custom, null, false);
    }

    public static View customEditText(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.edt_n_btn, null, false);
    }

    public static View netSettings(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.net, null, false);
    }

    public static void   CustomixeFonts(View parent, Context context, Typeface typeface){
        if (parent instanceof DrawerLayout){
                int count =((DrawerLayout) parent).getChildCount();
                for (int i = 0; i < count; i++) {
                    View view =((DrawerLayout) parent).getChildAt(i);
                    if (view instanceof TextView) {
                        ((TextView) view).setTypeface(typeface);
                    }
                    if (view instanceof DrawMeTextView) {
                        ((DrawMeTextView) view).setTypeface(typeface);
                    }
                    if (view instanceof EditText) {
                        ((DrawMeTextView) view).setTypeface(typeface);
                    }
                }
        }

        if (parent instanceof LinearLayout){
            int count =((LinearLayout) parent).getChildCount();
            for (int i = 0; i < count; i++) {
                View view =((LinearLayout) parent).getChildAt(i);
                if (view instanceof TextView) {
                    ((TextView) view).setTypeface(typeface);
                }
                if (view instanceof DrawMeTextView) {
                    ((DrawMeTextView) view).setTypeface(typeface);
                }
                if (view instanceof EditText) {
                    ((DrawMeTextView) view).setTypeface(typeface);
                }
            }
        }

        if (parent instanceof RelativeLayout){
            int count =((RelativeLayout) parent).getChildCount();
            for (int i = 0; i < count; i++) {
                View view =((RelativeLayout) parent).getChildAt(i);
                if (view instanceof TextView) {
                    ((TextView) view).setTypeface(typeface);
                }
                if (view instanceof DrawMeTextView) {
                    ((DrawMeTextView) view).setTypeface(typeface);
                }

                if (view instanceof EditText) {
                    ((DrawMeTextView) view).setTypeface(typeface);
                }
            }
        }

        if (parent instanceof CoordinatorLayout){
            int count =((CoordinatorLayout) parent).getChildCount();
            for (int i = 0; i < count; i++) {
                View view =((CoordinatorLayout) parent).getChildAt(i);
                if (view instanceof TextView) {
                    ((TextView) view).setTypeface(typeface);
                }
                if (view instanceof DrawMeTextView) {
                    ((DrawMeTextView) view).setTypeface(typeface);
                }

                if (view instanceof EditText) {
                    ((DrawMeTextView) view).setTypeface(typeface);
                }
            }
        }

        if (parent instanceof Toolbar){
            int count =((Toolbar) parent).getChildCount();
            for (int i = 0; i < count; i++) {
                View view =((Toolbar) parent).getChildAt(i);
                if (view instanceof TextView) {
                    ((TextView) view).setTypeface(typeface);
                }
                if (view instanceof DrawMeTextView) {
                    ((DrawMeTextView) view).setTypeface(typeface);
                }

                if (view instanceof EditText) {
                    ((DrawMeTextView) view).setTypeface(typeface);
                }
            }
        }

    }

}
