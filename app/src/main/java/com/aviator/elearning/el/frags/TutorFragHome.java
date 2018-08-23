package com.aviator.elearning.el.frags;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.FloatValueHolder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aviator.elearning.R;
import com.aviator.elearning.TutorCourseExams;
import com.aviator.elearning.TutorDashboard;
import com.aviator.elearning.el.interfaces.ShowHide;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorFragHome extends Fragment implements ShowHide {


    public TutorFragHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutor_frag_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        AfterInit();
    }

    void StartAnimCards() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FlingAnimation flingAnimation = new FlingAnimation(new FloatValueHolder(1));
                flingAnimation.setStartVelocity(-2000);//设置动能开始时的速率
                flingAnimation.setStartValue(500);//开始位置，对应最终需要执行的属性(TranslationY)
                flingAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
                    @Override
                    public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                        Log.i("flingAnimation", "onAnimationUpdate: value -> " + value + " velocity -> " + velocity);
                        card1.setTranslationY(value);
                        card2.setTranslationY(value);
                        card3.setTranslationY(value);
                    }
                });
                flingAnimation.start();
            }
        },500);

    }

    private RelativeLayout card1;
    private RelativeLayout card2;
    private RelativeLayout card3;

    public void initViews(View view) {
        card1 = (RelativeLayout) view.findViewById(R.id.card1);
        card2 = (RelativeLayout) view.findViewById(R.id.card2);
        card3 = (RelativeLayout) view.findViewById(R.id.card3);
    }


    @Override
    public void ShowMsg(String message) {

    }

    @Override
    public void HideMsg() {

    }

    @Override
    public void Pause_() {

    }

    @Override
    public void Resume_() {

    }

    @Override
    public void AfterInit() {
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TutorDashboard.MoveToViewPagerCurrent(1);
                Intent intent=new Intent(getContext(), TutorCourseExams.class);
                intent.putExtra("examorcourse","getcourses");
                startActivity(intent);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  TutorDashboard.MoveToViewPagerCurrent(2);
                Intent intent=new Intent(getContext(), TutorCourseExams.class);
                intent.putExtra("examorcourse","getexams");
                startActivity(intent);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Monitor", Toast.LENGTH_SHORT).show();
            }
        });
        StartAnimCards();
    }
}
