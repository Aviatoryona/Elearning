package com.aviator.elearning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aviator.elearning.aviator.main.CustomFonts;
import com.aviator.elearning.aviator.main.General;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.LoginRegisterCommon;
import com.bumptech.glide.Glide;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("ALL")
public class PasswordReq extends LoginRegisterCommon {

    public View view;

    String EMAIL, PIC, PASS;
    public MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.activity_password_req, null, false);
        setContentView(view);

        myPreferences = new MyPreferences(this);

        initViews();
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("PIC")) {
                PIC = bundle.getString("PIC");
                final String url = new SpaceCraft().getUrl(this) + "users/" + PIC;
                Glide.with(this).load(Uri.parse(url)).override(300, 300).centerCrop().placeholder(R.mipmap.picholder).into(imgProfile);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Pq.LoadWriteImgFromServer(Uri.parse(url), PasswordReq.this);
                    }
                }, 2000);
            }

            if (bundle.containsKey("PASS")) {
                PASS = bundle.getString("PASS");
            }
        }

        EMAIL = myPreferences.getEmail();
        txtHeader.setTypeface(CustomFonts.LoveYaLikeASister(this));
        txtHeader.setText("Hello " + myPreferences.getName());

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
        fabPasss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtPass.getText().toString())) {
                    edtPass.setError("required *");
                    return;
                }

                if (EMAIL == null) {
                    Snackbar.make(view, "Sorry, Error encountered", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (PASS != null) {
                    if (edtPass.getText().toString().equals(PASS)) {
                        finish();
                        startActivity(new Intent(PasswordReq.this, MainActivity.class));
                        return;
                    }
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put("target", "users");
                    params.put("action", "auth");
                    params.put("email", EMAIL);
                    params.put("password", edtPass.getText().toString());

                    CheckPass(params);
                }
            }
        });

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(PasswordReq.this, Register.class));
            }
        });

        if (PASS != null) {
            edtPass.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!(edtPass.getText().toString()).equals(PASS)) {
                        txtError.setText("Incorrect password");
                    } else {
                        txtError.setText("");
                    }

                    if ((edtPass.getText().toString()).length() == 0) {
                        txtError.setText("");
                    }
                }
            });
        }
    }

    class Pq extends General {
    }

    private Toolbar toolbar;
    private CircleImageView imgProfile;
    private TextView txtHeader;
    private EditText edtPass;
    private FloatingActionButton fabPasss;
    private TextView txtForgotPass;
    private TextView txtError;
    public GoogleProgressBar pgLoading;

    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        edtPass = (EditText) findViewById(R.id.edtPass);
        fabPasss = (FloatingActionButton) findViewById(R.id.fabPasss);
        txtForgotPass = (TextView) findViewById(R.id.txtForgotPass);
        txtError = (TextView) findViewById(R.id.txtError);
        pgLoading = (GoogleProgressBar) findViewById(R.id.pgLoading);
    }


}
