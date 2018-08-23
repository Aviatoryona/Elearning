package com.aviator.elearning.el.CommonsPkg;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.sdsmdg.tastytoast.TastyToast;

import avfont.com.aviator.aviatorfontlib.AvFonts;

public abstract class NetIp extends AppCompatActivity implements ShowHide{


    public void ShowNetDg() {
        myPrefs = new MyPreferences(this);
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setCancelable(true);

        View view = CustomViews.netSettings(this);
        alBuilder.setView(view);


        edtIp = view.findViewById(R.id.ipTxt);
        edtPort = view.findViewById(R.id.portTxt);

        edtPort.setTypeface(AvFonts.DroidSans(this));
        edtIp.setTypeface(AvFonts.DroidSans(this));
        chkBox = view.findViewById(R.id.chkBox);
        edtIp.setText(myPrefs.getIp());
        edtPort.setText(myPrefs.getPort());

        chkBox.setTypeface(AvFonts.DroidSans(this));

        final AlertDialog alertDialog = alBuilder.create();
        (view.findViewById(R.id.btnSave)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (chkBox.isSelected()) {
                    CheckConnection();
                    return;
                }
                myPrefs.setIp(edtIp.getText().toString());
                myPrefs.setport(edtPort.getText().toString());

                alertDialog.dismiss();
            }
        });

        alertDialog.show();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void CheckConnection() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connection check....");
        progressDialog.show();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] network;
        if (connectivityManager != null) {
            network = connectivityManager.getAllNetworks();
            for (Network aNetwork : network) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(aNetwork);
                if (networkInfo.isConnected()) {
                    progressDialog.dismiss();
                    TastyToast.makeText(NetIp.this, "Connection Success", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    myPrefs.setIp(edtIp.getText().toString());
                    myPrefs.setport(edtPort.getText().toString());
                    return;
                }
            }
        }
        progressDialog.dismiss();
        myPrefs.setIp(edtIp.getText().toString());
        myPrefs.setport(edtPort.getText().toString());
        TastyToast.makeText(NetIp.this, "Connection Failed", TastyToast.LENGTH_LONG, TastyToast.ERROR);
    }

    EditText edtIp, edtPort;
    CheckBox chkBox;
    MyPreferences myPrefs;
}
