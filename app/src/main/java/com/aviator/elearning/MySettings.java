package com.aviator.elearning;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.el.CommonsPkg.NetIp;

import avfont.com.aviator.aviatorfontlib.AvFonts;

@SuppressWarnings("ALL")
public class MySettings extends NetIp {
    MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);

        myPreferences = new MyPreferences(MySettings.this);

        initViews();

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        onClicks();
    }

    public void onClicks() {

        chkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkBox.isSelected()) {
                    Toast.makeText(MySettings.this, "Notifications Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MySettings.this, "Notifications Disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchT.isSelected()) {
                    Toast.makeText(MySettings.this, "Theme Enabled", Toast.LENGTH_SHORT).show();
                }
//                else {
//                    getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
//                }
            }
        });

        switchLscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPreferences.WriteShowLaunchingScreen(switchLscreen.isSelected());
            }
        });

        lockRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReqCode();
            }
        });
        lockRhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowNetSettings();
            }
        });
    }


    @SuppressLint("NewApi")
    private void ReqCode() {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(MySettings.this);
        alBuilder.setCancelable(true);

        final EditText editText = new EditText(MySettings.this);
        editText.setBackground(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        editText.setPadding(16, 16, 16, 16);
        editText.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        editText.setTextColor(getResources().getColor(R.color.colorBlack));
        editText.setTypeface(AvFonts.RobotoRegular(MySettings.this));
        editText.setTextSize(22);
        alBuilder.setView(editText);
        alBuilder.setTitle("Enters purchases code");
        alBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    myPreferences.WriteBsCode(editText.getText().toString());
                }

            }
        }).setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alBuilder.show();
    }

    private void ShowNetSettings() {
        ShowNetDg();
    }

    private Toolbar toolbar;
    private CheckBox chkBox;
    private Switch switchT;
    private Switch switchLscreen;
    private RelativeLayout lockRl;
    private ImageView imgLock;
    private RelativeLayout lockRhttp;
    private ImageView imgHttp;
    private FloatingActionButton fab;

    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        chkBox = (CheckBox) findViewById(R.id.chkBox);
        switchT = (Switch) findViewById(R.id.switchT);
        switchLscreen = (Switch) findViewById(R.id.switchLscreen);
        lockRl = (RelativeLayout) findViewById(R.id.lockRl);
        imgLock = (ImageView) findViewById(R.id.imgLock);
        lockRhttp = (RelativeLayout) findViewById(R.id.lockRhttp);
        imgHttp = (ImageView) findViewById(R.id.imgHttp);
        fab = (FloatingActionButton) findViewById(R.id.fab);
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
