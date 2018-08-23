package com.aviator.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aviator.elearning.aviator.main.CustomFonts;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.el.CommonsPkg.LoginRegisterCommon;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class EmailReq extends LoginRegisterCommon {

    public View view;
    public MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.activity_email_req, null, false);
        setContentView(view);

        myPreferences = new MyPreferences(EmailReq.this);
        if (myPreferences.CheckLoginStatus()) {
            finish();
            startActivity(new Intent(EmailReq.this, MainActivity.class));
            return;
        }

        initViews();
        setSupportActionBar(toolbar);


        //
        AfterInit();
    }

    @Override
    protected void onStart() {
        super.onStart();

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
        fabMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtMail.getText().toString())) {
                    edtMail.setError("required *");
                    return;
                }

                Map<String, String> params = new HashMap<>();
                params.put("target", "users");
                params.put("action", "ifexists");
                params.put("email", edtMail.getText().toString());
                CheckEmail(params);
            }
        });

        txtRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(EmailReq.this, Register.class));
            }
        });

        txtHeader.setTypeface(CustomFonts.Reckoner_Bold(EmailReq.this));
    }

    private Toolbar toolbar;
    private TextView txtHeader;
    private EditText edtMail;
    private FloatingActionButton fabMail;
    private TextView txtRegisterNow;
    public GoogleProgressBar pgLoading;

    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        edtMail = (EditText) findViewById(R.id.edtMail);
        fabMail = (FloatingActionButton) findViewById(R.id.fabMail);
        txtRegisterNow = (TextView) findViewById(R.id.txtRegisterNow);
        pgLoading = (GoogleProgressBar) findViewById(R.id.pgLoading);
    }
}
