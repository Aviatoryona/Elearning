package com.aviator.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.el.CommonsPkg.LoginRegisterCommon;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class Register extends LoginRegisterCommon {

    public View view;
    public MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(R.anim.bottom_from_top);
        view = getLayoutInflater().inflate(R.layout.activity_register, null, false);
        setContentView(view);

        myPreferences = new MyPreferences(this);

        initViews();
        setSupportActionBar(toolbar);

        //
        AfterInit();
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
        fabRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtName.getText().toString())) {
                    edtName.setError("required *");
                    return;
                }

                if (TextUtils.isEmpty(edtMail.getText().toString())) {
                    edtMail.setError("required *");
                    return;
                }

                if (spinnerCountry.getSelectedItemPosition() == 0) {
                    TastyToast.makeText(Register.this, "Select country", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
                    return;
                }

                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    edtPhone.setError("required *");
                    return;
                }

                if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                    edtPassword.setError("secure account");
                    return;
                }

                Map<String, String> map = new HashMap<>();
                map.put("target", "users");
                map.put("action", "adduser");
                map.put("name", edtName.getText().toString());
                map.put("email", edtMail.getText().toString());
                map.put("country", spinnerCountry.getSelectedItem().toString());
                map.put("contact", edtPhone.getText().toString());
                map.put("password", edtPassword.getText().toString());

                RegisterUser(map);
            }
        });

        txtLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Register.this, EmailReq.class));
            }
        });
    }

    private Toolbar toolbar;
    private TextView txtHeader;
    private EditText edtName;
    private EditText edtMail;
    private Spinner spinnerCountry;
    private EditText edtPhone;
    private EditText edtPassword;
    private TextView txtLoginNow;
    public GoogleProgressBar pgLoading;
    private FloatingActionButton fabRegister;

    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        edtName = (EditText) findViewById(R.id.edtName);
        edtMail = (EditText) findViewById(R.id.edtMail);
        spinnerCountry = (Spinner) findViewById(R.id.spinnerCountry);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        txtLoginNow = (TextView) findViewById(R.id.txtLoginNow);
        pgLoading = (GoogleProgressBar) findViewById(R.id.pgLoading);
        fabRegister = (FloatingActionButton) findViewById(R.id.fabRegister);
    }


}
