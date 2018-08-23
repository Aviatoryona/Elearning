package com.aviator.elearning.aviator.main;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.aviator.elearning.R;

public class CustomAnimaions {

    public static Animation BottomFromTop(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.bottom_from_top);
    }

    public static Animation Bounce(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.bounce);
    }

    public static Animation FadeIn(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.fade);
    }

    public static Animation FadeOut(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.fade_out);
    }

    public static Animation LeftFromRight(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.left_from_right);
    }

    public static Animation RightFromLeft(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.right_from_left);
    }

    public static Animation SlideLeft(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.slide_left);
    }

    public static Animation SlideRight(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.slide_right);
    }

    public static Animation SlideUp(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.slide_up);
    }

    public static Animation TopFromBottom(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.top_from_bottom);
    }

    public static Animation UpFromBottom(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
    }

    public static Animation Welcome(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.welcome);
    }

    public static Animation ZoomIn(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.zoom_in);
    }

    public static Animation ZoomOut(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.zoom_out);
    }

}
