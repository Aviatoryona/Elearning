package com.aviator.elearning;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.aviator.elearning.aviator.main.CustomFonts;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.aviator.main.General;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.rafakob.drawme.DrawMeTextView;

import java.io.IOException;

import avfont.com.aviator.aviatorfontlib.AvFonts;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("ALL")
public class MyAccount extends AppCompatActivity implements ShowHide{
    MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        myPreferences = new MyPreferences(this);

        initViews();
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        Gn.ReadImg(imgProfile, MyAccount.this);

        txtName.setText(myPreferences.getName());
        txtContact.setText(myPreferences.getPhone());
        txtEmail.setText(myPreferences.getEmail());
        txtCountry.setText(myPreferences.getCountry());


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MyAccount.this, imgProfile);
                popupMenu.getMenuInflater().inflate(R.menu.camgal, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_cam) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 100);
                            return true;
                        }

                        if (item.getItemId() == R.id.action_gal) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
//                            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, Intent.EXTRA_ALLOW_MULTIPLE);
                            startActivityForResult(intent, 111);
                            return true;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });


        txtLOGOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MyAccount.this);
                alertDialog.setIcon(R.mipmap.ic_launcher_round);
                alertDialog.setMessage("Sure to sign out?");
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("Sign in now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (myPreferences.Logout()) {
                            finish();
                            Intent intent = new Intent(MyAccount.this, EmailReq.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
                alertDialog.show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CustomViews.CustomixeFonts(parent, MyAccount.this, CustomFonts.LoveYaLikeASister(MyAccount.this));
            }
        }, 3000);
    }

    void Customixe() {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(AvFonts.RobotoRegular(MyAccount.this));
            }
            if (view instanceof DrawMeTextView) {
                ((DrawMeTextView) view).setTypeface(AvFonts.RobotoRegular(MyAccount.this));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {

            case 100: { //captured img
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgProfile.setImageBitmap(bitmap);

                Gn.WriteImg(bitmap, MyAccount.this);
                break;
            }

            case 111: { //picked img
                Uri uri = data.getData();
//                Glide.with(getContext()).loadFromMediaStore(uri).placeholder(R.mipmap.no_image).into(profile);//.load(uri).placeholder(R.mipmap.no_image).into(profile);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(MyAccount.this.getContentResolver(), uri);
                    imgProfile.setImageBitmap(bitmap);
                    Gn.WriteImg(bitmap, MyAccount.this);
                } catch (IOException e) {
                }

                break;
            }

        }
    }

    class Gn extends General {
    }

    private CoordinatorLayout parent;
    private Toolbar toolbar;
    private CircleImageView imgProfile;
    private TextView txtName;
    private TextView txtNumCourses;
    private TextView txtNumExams;
    private TextView txtEmail;
    private TextView txtContact;
    private TextView txtCountry;
    private TextView txtLOGOUT;
    private Button btnEdit;
    private FloatingActionButton fab;

    public void initViews() {
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        txtName = (TextView) findViewById(R.id.txtName);
        txtNumCourses = (TextView) findViewById(R.id.txtNumCourses);
        txtNumExams = (TextView) findViewById(R.id.txtNumExams);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtContact = (TextView) findViewById(R.id.txtContact);
        txtCountry = (TextView) findViewById(R.id.txtCountry);
        txtLOGOUT = (TextView) findViewById(R.id.txtLOGOUT);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }



}
