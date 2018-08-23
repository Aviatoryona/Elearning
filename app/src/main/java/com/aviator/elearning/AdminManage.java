package com.aviator.elearning;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aviator.elearning.el.CommonsPkg.AdminManageHelper;
import com.aviator.elearning.el.adapter.ListAdminManageCourses;
import com.aviator.elearning.el.adapter.ListAdminManageExams;
import com.aviator.elearning.el.adapter.ListAdminManageUsers;
import com.aviator.elearning.el.models.CourseExamModel;
import com.aviator.elearning.el.models.CourseModel;
import com.aviator.elearning.el.models.UserModel;

public class AdminManage extends AdminManageHelper {

    String what;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage);
        initViews();
        collapseLayout.setTitleEnabled(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AfterInit();

        if (getIntent().hasExtra("what")) {
            what = getIntent().getStringExtra("what");
            if (what.contains("users")) {
                LoadListUsersData();
            } else if (what.contains("courses")) {
                LoadListCoursesData();
            } else {
                LoadListExamsData();
            }
        } else {
            LoadListUsersData();//default
        }

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
        listMange.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getAdapter() instanceof ListAdminManageCourses) {
                    CourseModel courseModel = (CourseModel) ((ListAdminManageCourses) parent.getAdapter()).getItem(position);
                    assert courseModel != null;
                    ShowActionMenu1(view, courseModel.getC_uniqid(), 0);
                    return;
                }

                if (parent.getAdapter() instanceof ListAdminManageExams) {
                    CourseExamModel courseExamModel = (CourseExamModel) ((ListAdminManageExams) parent.getAdapter()).getItem(position);
                    assert courseExamModel != null;
                    ShowActionMenu1(view, courseExamModel.getExamModel().getE_examuniqid(), 1);
                    return;
                }

                if (parent.getAdapter() instanceof ListAdminManageUsers) {
                    UserModel userModel = (UserModel) ((ListAdminManageUsers) parent.getAdapter()).getItem(position);
                    assert userModel != null;
                    ShowActionMenu2(view, userModel.getUser_email());
                }
            }
        });
    }

    void ShowActionMenu1(View view, final String uniqid, final int wha) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.admin_approve_deapprove, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.approve) {
                    if (wha == 0) {
                        ApproveDeapproveCourse(uniqid, "1");
                        return true;
                    }
                    if (wha == 1) {
                        ApproveDeapproveExam(uniqid, "1");
                        return true;
                    }
                }

                if (item.getItemId() == R.id.unapprove) {
                    if (wha == 0) {
                        ApproveDeapproveCourse(uniqid, "0");
                        return true;
                    }
                    if (wha == 1) {
                        ApproveDeapproveExam(uniqid, "0");
                        return true;
                    }
                }
                return false;
            }
        });
        popupMenu.show();
    }

    void ShowActionMenu2(View view, final String email) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.admin_users_manage, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String status;
                if (item.getItemId() == R.id.student) {
                    status = "0";
                    ApproveDeapproveUser(email, status);
                    return true;
                }
                if (item.getItemId() == R.id.tutor) {
                    status = "1";
                    ApproveDeapproveUser(email, status);
                    return true;
                }
                if (item.getItemId() == R.id.admin_only) {
                    status = "2";
                    ApproveDeapproveUser(email, status);
                    return true;
                }
                if (item.getItemId() == R.id.admin_tutor) {
                    status = "1;2";
                    ApproveDeapproveUser(email, status);
                    return true;
                }

                if (item.getItemId() == R.id.grant_all) {
                    status = "0;1;2";
                    ApproveDeapproveUser(email, status);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private AppBarLayout appBarLy;
    private CollapsingToolbarLayout collapseLayout;
    private ImageView imgImg;
    private Toolbar toolbar;
    private TextView txtTitle;
    public SwipeRefreshLayout swipeRefresh;
    public ListView listMange;
    public ProgressBar pgLoading;
    private FloatingActionButton fab;

    public void initViews() {
        appBarLy = (AppBarLayout) findViewById(R.id.appBarLy);
        collapseLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseLayout);
        imgImg = (ImageView) findViewById(R.id.imgImg);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        listMange = (ListView) findViewById(R.id.listMange);
        pgLoading = (ProgressBar) findViewById(R.id.pgLoading);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }
}
