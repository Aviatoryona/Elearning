package com.aviator.elearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.aviator.elearning.el.interfaces.ShowHide;

public class TopicContent extends AppCompatActivity implements ShowHide{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    }
}
