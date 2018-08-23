package com.aviator.elearning.el.CommonsPkg;

import android.annotation.SuppressLint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.AdminManage;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.adapter.ListAdminManageCourses;
import com.aviator.elearning.el.adapter.ListAdminManageExams;
import com.aviator.elearning.el.adapter.ListAdminManageUsers;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.CourseExamModel;
import com.aviator.elearning.el.models.CourseModel;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.models.UserModel;
import com.aviator.elearning.el.net.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AdminManageHelper extends AppCompatActivity implements ShowHide {

    private void getUnApprovedCourses(final Map<String, String> paStringMap) {
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ParseJsonsHelper fda = new ParseJsonsHelper(AdminManageHelper.this);

                ArrayList<CourseModel> courseModelArrayList = fda.getCourseData(s);
                if (courseModelArrayList != null) {
                    if (AdminManageHelper.this instanceof AdminManage) {
                        ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                        ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                        ((AdminManage) AdminManageHelper.this)
                                .listMange.setAdapter(new ListAdminManageCourses(courseModelArrayList, AdminManageHelper.this));
                    }
                } else {

                    MessageModel messageModel = fda.getMessageModel(s);
                    if (messageModel != null) {
                        if (!messageModel.isError()) {
                            ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                            ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                            Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, messageModel.getMessage(),
                                    Snackbar.LENGTH_LONG).show();
                        } else {
                            ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                            ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                            Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, messageModel.getMessage(),
                                    Snackbar.LENGTH_INDEFINITE).setAction("Reload",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getUnApprovedCourses(paStringMap);
                                        }
                                    }).show();
                        }
                    } else {
                        if (AdminManageHelper.this instanceof AdminManage) {
                            ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                            ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                            Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, SpaceCraft.CONNECTION_ERROR_MSG,
                                    Snackbar.LENGTH_INDEFINITE).setAction("Reload",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getUnApprovedCourses(paStringMap);
                                        }
                                    }).show();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                if (AdminManageHelper.this instanceof AdminManage) {
                    ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                    ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                    Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, SpaceCraft.CONNECTION_ERROR_MSG,
                            Snackbar.LENGTH_INDEFINITE).setAction("Reload",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getUnApprovedCourses(paStringMap);
                                }
                            }).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return paStringMap;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();


    }

    private void getUnapprovedExams(final Map<String, String> paStringMap) {
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ParseJsonsHelper fda = new ParseJsonsHelper(AdminManageHelper.this);

                ArrayList<CourseExamModel> examModelArrayList = fda.getCourseExamModel(s);
                if (examModelArrayList != null) {
                    if (AdminManageHelper.this instanceof AdminManage) {
                        ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                        ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                        ((AdminManage) AdminManageHelper.this)
                                .listMange.setAdapter(new ListAdminManageExams(examModelArrayList, AdminManageHelper.this));
                    }
                } else {

                    MessageModel messageModel = fda.getMessageModel(s);
                    if (messageModel != null) {
                        if (!messageModel.isError()) {
                            ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                            ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                            Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, messageModel.getMessage(),
                                    Snackbar.LENGTH_LONG).show();
                        } else {
                            ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                            ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                            Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, messageModel.getMessage(),
                                    Snackbar.LENGTH_INDEFINITE).setAction("Reload",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getUnapprovedExams(paStringMap);
                                        }
                                    }).show();
                        }
                    } else {
                        if (AdminManageHelper.this instanceof AdminManage) {
                            ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                            ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                            Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, SpaceCraft.CONNECTION_ERROR_MSG,
                                    Snackbar.LENGTH_INDEFINITE).setAction("Reload",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getUnapprovedExams(paStringMap);
                                        }
                                    }).show();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                if (AdminManageHelper.this instanceof AdminManage) {
                    ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                    ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                    Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, SpaceCraft.CONNECTION_ERROR_MSG,
                            Snackbar.LENGTH_INDEFINITE).setAction("Reload",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getUnapprovedExams(paStringMap);
                                }
                            }).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return paStringMap;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    private void getUsers(final Map<String, String> paStringMap) {
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ParseJsonsHelper fda = new ParseJsonsHelper(AdminManageHelper.this);

                ArrayList<UserModel> examModelArrayList = fda.userModelArrayList(s);
                if (examModelArrayList != null) {
                    if (AdminManageHelper.this instanceof AdminManage) {
                        ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                        ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                        ((AdminManage) AdminManageHelper.this)
                                .listMange.setAdapter(new ListAdminManageUsers(examModelArrayList, AdminManageHelper.this));
                    }
                } else {

                    MessageModel messageModel = fda.getMessageModel(s);
                    if (messageModel != null) {
                        if (!messageModel.isError()) {
                            ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                            ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                            Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, messageModel.getMessage(),
                                    Snackbar.LENGTH_LONG).show();
                        } else {
                            ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                            ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                            Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, messageModel.getMessage(),
                                    Snackbar.LENGTH_INDEFINITE).setAction("Reload",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getUsers(paStringMap);
                                        }
                                    }).show();
                        }
                    } else {
                        if (AdminManageHelper.this instanceof AdminManage) {
                            ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                            ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                            Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, SpaceCraft.CONNECTION_ERROR_MSG,
                                    Snackbar.LENGTH_INDEFINITE).setAction("Reload",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getUsers(paStringMap);
                                        }
                                    }).show();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                if (AdminManageHelper.this instanceof AdminManage) {
                    ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                    ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                    Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, SpaceCraft.CONNECTION_ERROR_MSG,
                            Snackbar.LENGTH_INDEFINITE).setAction("Reload",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getUnApprovedCourses(paStringMap);
                                }
                            }).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return paStringMap;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }


    private void ApproveDeapprove(final Map<String, String> paStringMap) {
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(this), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ParseJsonsHelper fda = new ParseJsonsHelper(AdminManageHelper.this);
                MessageModel messageModel = fda.getMessageModel(s);
                if (messageModel != null) {
                    if (!messageModel.isError()) {
                        ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                        ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                        Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, messageModel.getMessage(),
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                        ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                        Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, messageModel.getMessage(),
                                Snackbar.LENGTH_INDEFINITE).setAction("Try Again",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ApproveDeapprove(paStringMap);
                                    }
                                }).show();
                    }
                } else {
                    if (AdminManageHelper.this instanceof AdminManage) {
                        ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                        ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                        Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, SpaceCraft.CONNECTION_ERROR_MSG,
                                Snackbar.LENGTH_INDEFINITE).setAction("Try Again",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ApproveDeapprove(paStringMap);
                                    }
                                }).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                if (AdminManageHelper.this instanceof AdminManage) {
                    ((AdminManage) AdminManageHelper.this).pgLoading.setVisibility(View.GONE);
                    ((AdminManage) AdminManageHelper.this).swipeRefresh.setRefreshing(false);
                    Snackbar.make(((AdminManage) AdminManageHelper.this).swipeRefresh, SpaceCraft.CONNECTION_ERROR_MSG,
                            Snackbar.LENGTH_INDEFINITE).setAction("Try Again",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ApproveDeapprove(paStringMap);
                                }
                            }).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return paStringMap;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    public void ApproveDeapproveExam(String examuniqid, String status) {
        Map<String, String> map = new HashMap<>();
        map.put("target", "admin");
        map.put("action", "approveexam");
        map.put("examuniqid", examuniqid);
        map.put("newstatus", status);
        ApproveDeapprove(map);
    }

    public void ApproveDeapproveCourse(String courseuniqid, String status) {
        Map<String, String> map = new HashMap<>();
        map.put("target", "admin");
        map.put("action", "approvecourse");
        map.put("courseuniqid", courseuniqid);
        map.put("newstatus", status);
        ApproveDeapprove(map);
    }

    public void ApproveDeapproveUser(String email, String status) {
        Map<String, String> map = new HashMap<>();
        map.put("target", "admin");
        map.put("action", "approveuser");
        map.put("email", email);
        map.put("newstatus", status);
        ApproveDeapprove(map);
    }

    public void LoadListExamsData() {
        Map<String, String> map = new HashMap<>();
        map.put("target", "exams");
        map.put("action", "allexams");
        getUnapprovedExams(map);
    }

    public void LoadListCoursesData() {
        Map<String, String> map = new HashMap<>();
        map.put("target", "courses");
        map.put("action", "all");
        getUnApprovedCourses(map);
    }

    public void LoadListUsersData() {
        Map<String, String> map = new HashMap<>();
        map.put("target", "users");
        map.put("action", "allusers");
        map.put("email", new MyPreferences(this).getEmail());
        getUsers(map);
    }

}
