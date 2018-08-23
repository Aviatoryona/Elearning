package com.aviator.elearning.el.CommonsPkg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.EmailReq;
import com.aviator.elearning.MainActivity;
import com.aviator.elearning.PasswordReq;
import com.aviator.elearning.Register;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.models.UserModel;
import com.aviator.elearning.el.net.VolleySingleton;

import java.util.Map;

public abstract class LoginRegisterCommon extends NetIp implements ShowHide{

    public void CheckEmail(final Map<String, String> params) {
        if (this instanceof EmailReq) {
            ((EmailReq) this).pgLoading.setVisibility(View.VISIBLE);
        }

        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();

                ((EmailReq) LoginRegisterCommon.this).pgLoading.setVisibility(View.GONE);

                UserModel userModel = new ParseJsonsHelper(LoginRegisterCommon.this).getUserModel(s);
                if (userModel != null) {
                    if (userModel.getUser_status().equalsIgnoreCase("ONLINE")) {

                        ((EmailReq) LoginRegisterCommon.this).myPreferences.setEmail(userModel.getUser_email());
                        ((EmailReq) LoginRegisterCommon.this).myPreferences.setPhone(userModel.getUser_contact());
                        ((EmailReq) LoginRegisterCommon.this).myPreferences.setName(userModel.getUser_name());
                        ((EmailReq) LoginRegisterCommon.this).myPreferences.setUserType(userModel.getUser_type());
                        ((EmailReq) LoginRegisterCommon.this).myPreferences.setCountry(userModel.getCountry());

                        Intent intent = new Intent(LoginRegisterCommon.this, PasswordReq.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("PIC", userModel.getUser_image());
                        bundle.putString("PASS", userModel.getUser_password());
                        intent.putExtras(bundle);

                        finish();
                        startActivity(intent);

                    } else {
                        Snackbar.make(((EmailReq) LoginRegisterCommon.this).view, "Account Freezed", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CheckEmail(params);
                            }
                        }).show();


                    }
                    return;
                }


                MessageModel messageModel = new ParseJsonsHelper(LoginRegisterCommon.this).getMessageModel(s);
                if (messageModel != null) {
                    Snackbar.make(((EmailReq) LoginRegisterCommon.this).view, messageModel.getMessage(), Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckEmail(params);
                        }
                    }).show();
                    return;
                }

                Snackbar.make(((EmailReq) LoginRegisterCommon.this).view, "Sorry, Error encountered", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckEmail(params);
                    }
                }).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                ((EmailReq) LoginRegisterCommon.this).pgLoading.setVisibility(View.GONE);
                Snackbar.make(((EmailReq) LoginRegisterCommon.this).view, "Internet Connection Error", Snackbar.LENGTH_INDEFINITE).setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowNetDg();
                    }
                }).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }


    public void CheckPass(final Map<String, String> params) {
        if (this instanceof PasswordReq) {
            ((PasswordReq) this).pgLoading.setVisibility(View.VISIBLE);
        }
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();
                ((PasswordReq) LoginRegisterCommon.this).pgLoading.setVisibility(View.GONE);
                UserModel userModel = new ParseJsonsHelper(LoginRegisterCommon.this).getUserModel(s);
                if (userModel != null) {
                    if (userModel.getUser_status().equalsIgnoreCase("ONLINE")) {
                        ((PasswordReq) LoginRegisterCommon.this).myPreferences.setName(userModel.getUser_name());
                        ((PasswordReq) LoginRegisterCommon.this).myPreferences.setEmail(userModel.getUser_email());
                        ((PasswordReq) LoginRegisterCommon.this).myPreferences.setPhone(userModel.getUser_contact());
                        ((PasswordReq) LoginRegisterCommon.this).myPreferences.setUserType(userModel.getUser_type());
                        ((PasswordReq) LoginRegisterCommon.this).myPreferences.setCountry(userModel.getCountry());


                        finish();
                        Intent intent = new Intent(LoginRegisterCommon.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Snackbar.make(((PasswordReq) LoginRegisterCommon.this).view, "Account Freezed", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CheckPass(params);
                            }
                        }).show();
                    }
                    return;
                }

                MessageModel messageModel = new ParseJsonsHelper(LoginRegisterCommon.this).getMessageModel(s);
                if (messageModel != null) {
                    Snackbar.make(((PasswordReq) LoginRegisterCommon.this).view, messageModel.getMessage(), Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckPass(params);
                        }
                    }).show();
                    return;
                }
                Snackbar.make(((PasswordReq) LoginRegisterCommon.this).view, "Sorry, Error encountered", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckPass(params);
                    }
                }).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                ((PasswordReq) LoginRegisterCommon.this).pgLoading.setVisibility(View.GONE);
                Snackbar.make(((PasswordReq) LoginRegisterCommon.this).view, "Internet Connection Error", Snackbar.LENGTH_INDEFINITE).setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ShowNetDg();
//                        finish();
//                        startActivity(new Intent(LoginRegisterCommon.this, MainActivity.class));
//                        CheckPass(params);
                    }
                }).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }


    public void RegisterUser(final Map<String, String> params) {
        if (this instanceof Register) {
            ((Register) this).pgLoading.setVisibility(View.VISIBLE);
        }

        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();

                ((Register) LoginRegisterCommon.this).pgLoading.setVisibility(View.GONE);

                MessageModel messageModel = new ParseJsonsHelper(LoginRegisterCommon.this).getMessageModel(s);
                if (messageModel != null) {
                    if (!messageModel.isError()) {
                        ((Register) LoginRegisterCommon.this).myPreferences.setName(params.get("name"));
                        ((Register) LoginRegisterCommon.this).myPreferences.setEmail(params.get("email"));
                        ((Register) LoginRegisterCommon.this).myPreferences.setPhone(params.get("contact"));
                        ((Register) LoginRegisterCommon.this).myPreferences.setUserType("0");
                        ((Register) LoginRegisterCommon.this).myPreferences.setCountry(params.get("country"));

                        finish();
                        startActivity(new Intent(LoginRegisterCommon.this, MainActivity.class));
                        return;
                    }
                    Snackbar.make(((Register) LoginRegisterCommon.this).view, messageModel.getMessage(), Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RegisterUser(params);
                        }
                    }).show();
                    return;
                }

                Snackbar.make(((Register) LoginRegisterCommon.this).view, "Sorry, Error encountered", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RegisterUser(params);
                    }
                }).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                ((Register) LoginRegisterCommon.this).pgLoading.setVisibility(View.GONE);
                Snackbar.make(((Register) LoginRegisterCommon.this).view, "Internet Connection Error", Snackbar.LENGTH_INDEFINITE).setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ShowNetDg();
//                        finish();
//                        startActivity(new Intent(LoginRegisterCommon.this, MainActivity.class));
//                        CheckPass(params);
                    }
                }).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }
}


