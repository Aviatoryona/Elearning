package com.aviator.elearning;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aviator.elearning.aviator.main.CustomFonts;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.aviator.main.FetchData_Activity;
import com.aviator.elearning.aviator.main.General;
import com.aviator.elearning.aviator.main.List_Side_Data;
import com.aviator.elearning.aviator.main.MyDecor;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.el.adapter.List_Adapter;
import com.aviator.elearning.el.adapter.MainSideExpandableListAdapter;
import com.aviator.elearning.el.adapter.RecCoursesAdapter;
import com.aviator.elearning.el.adapter.RecExamsAdapter_Main;
import com.aviator.elearning.el.models.CourseExamModel;
import com.aviator.elearning.el.models.CourseModel;
import com.aviator.elearning.el.models.List_Side_Model;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.rafakob.drawme.DrawMeTextView;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import avfont.com.aviator.aviatorfontlib.AvFonts;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("ALL")
public class MainActivity extends FetchData_Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    MyPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPreferences = new MyPreferences(MainActivity.this);

        initViews();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadRecCoursesData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadRecExamsData();
                    }
                }, 3000);
            }
        });


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);


        AfterInit();
    }

    @Override
    public void AfterInit() {
        recCourses.addItemDecoration(new MyDecor(2));
        recCourses.setItemAnimator(new DefaultItemAnimator());
        recCourses.setLayoutManager(
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false)
        );
//        recCourses.setItemViewCacheSize(10);

        recExams.addItemDecoration(new MyDecor(5));
        recExams.setItemAnimator(new DefaultItemAnimator());
        recExams.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//                new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false)
        );
//        recExams.setItemViewCacheSize(15);

        txtHeader.setTypeface(AvFonts.AndroidInsomniaRegular(MainActivity.this));
        txtTitle.setTypeface(CustomFonts.LoveYaLikeASister(MainActivity.this));
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START, true);
            }
        });

        listSidebar.setAdapter(List_Adapter.newInstance(MainActivity.this));
        imgManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImgManageClicked();
            }
        });
        listSidebar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CloseDrawer();
                List_Side_Model list_side_model = (List_Side_Model) parent.getAdapter().getItem(position);
                DoActionListdata(list_side_model);

            }
        });

        imgMoreCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Courses.class));
            }
        });

        imgMoreExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Exams.class));
            }
        });
        imgVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VideoTutorials.class));
            }
        });

        Mn.ReadImg(imgDashboard, MainActivity.this);
        txtEmailDashboard.setText(myPreferences.getEmail());
        txtNameDashboard.setText(myPreferences.getName());

        refreshLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLy.setVisibility(View.GONE);
                LoadRecCoursesData();
                if (recExams.getAdapter() == null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LoadRecExamsData();
                        }
                    }, 3000);
                }
            }
        });

        if (TextUtils.isEmpty(myPreferences.getEmail())) {
            ss.setVisibility(View.VISIBLE);
        } else {
            ss.setVisibility(View.GONE);
        }

        txtItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseDrawer();
                if (TextUtils.isEmpty(myPreferences.getEmail())) {
                    finish();
                    Intent intent = new Intent(MainActivity.this, EmailReq.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return;
                }

                String s = getResources().getString(R.string.sign_in);

                ShowDlg(s);
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BasicSearch.class));
            }
        });
    }


    private void ShowDlg(String title) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MainActivity.this);
        alertDialog.setIcon(R.mipmap.ic_launcher_round);
        alertDialog.setMessage(title);
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
                    Intent intent = new Intent(MainActivity.this, PasswordReq.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Pause_();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Resume_();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Pause_();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Resume_();
    }

    @Override
    public void ShowMsg(String message) {

    }

    @Override
    public void HideMsg() {

    }

    @Override
    public void Pause_() {
        map.clear();
        if (recCourses.getAdapter() instanceof RecCoursesAdapter) {
            map.put("recCourses", ((RecCoursesAdapter) recCourses.getAdapter()).getCourseModelArrayList());
        }

        if (recExams.getAdapter() instanceof
//                RecExamsAdapter
                RecExamsAdapter_Main
                ) {
            map.put("recExams",
//                    ((RecExamsAdapter) recExams.getAdapter()).getExamModelArrayList()
                    ((RecExamsAdapter_Main) recExams.getAdapter()).getCourseExamModels()
            );
        }
    }

    @Override
    public void Resume_() {
        if (!map.isEmpty()) {
            if (map.containsKey("recCourses")) {
                ArrayList<CourseModel> model = (ArrayList<CourseModel>) map.get("recCourses");
                if (model != null) {
                    recCourses.setAdapter(new RecCoursesAdapter(MainActivity.this, model, MainActivity.this, null));
                }
            }

            if (map.containsKey("recExams")) {
//                ArrayList<ExamModel> models = (ArrayList<ExamModel>) map.get("recExams");
                ArrayList<CourseExamModel> models = (ArrayList<CourseExamModel>) map.get("recExams");
                if (models != null) {
//                    recExams.setAdapter(new RecExamsAdapter(models, MainActivity.this, MainActivity.this, null));

                    recExams.setAdapter(new RecExamsAdapter_Main(models, MainActivity.this, MainActivity.this, null));
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Init();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String[] userType = UsertType();

                if (userType != null) {

                    if (userType.length == 1) {
                        if (userType[0].equalsIgnoreCase("Student")) {
                            imgManage.setVisibility(View.GONE);
                            myCourses.setVisibility(View.VISIBLE);
                            LoadMyCoursesInBg();
//                            MainSideExpandableListAdapter.LoadData(0, MainActivity.this, myCourses, myPreferences);
                        } else if (userType[0].equalsIgnoreCase("Admin")) {
                            myCourses.setVisibility(View.INVISIBLE);
                            imgManage.setVisibility(View.VISIBLE);
                        } else if (userType[0].equalsIgnoreCase("Tutor")) {
                            myCourses.setVisibility(View.VISIBLE);
                            imgManage.setVisibility(View.VISIBLE);
                            LoadMyCoursesInBg();
//                            MainSideExpandableListAdapter.LoadData(0, MainActivity.this, myCourses, myPreferences);
                        }
                    } else if (userType.length == 2) {
                        imgManage.setVisibility(View.VISIBLE);
                        myCourses.setVisibility(View.VISIBLE);
                        if (userType[0].equalsIgnoreCase("Student") && userType[1].equalsIgnoreCase("Tutor")) {
//                            myCourses.setAdapter(new MainSideExpandableListAdapter(MainActivity.this,userType));
                        } else if (userType[0].equalsIgnoreCase("Student") && userType[1].equalsIgnoreCase("Admin")) {
                            LoadMyCoursesInBg();
//                            MainSideExpandableListAdapter.LoadData(0, MainActivity.this, myCourses, myPreferences);
                        } else if (userType[0].equalsIgnoreCase("Tutor") && userType[1].equalsIgnoreCase("Admin")) {
//                            myCourses.setVisibility(View.VISIBLE);
//                            MainSideExpandableListAdapter.LoadData(1, MainActivity.this, myCourses, myPreferences);
                        }
                    } else if (userType.length == 3) {
                        imgManage.setVisibility(View.VISIBLE);
                        myCourses.setVisibility(View.VISIBLE);
                        String[] astrStrings = new String[2];
                        astrStrings[0] = "Student";
                        astrStrings[1] = "Tutor";
                        myCourses.setAdapter(new MainSideExpandableListAdapter(MainActivity.this, astrStrings));
                    }

                } else {
                    imgManage.setVisibility(View.GONE);
                }

                CustomViews.CustomixeFonts(drawerLayout, MainActivity.this, AvFonts.AndroidInsomniaRegular(MainActivity.this));
            }
        }, 3000);
    }

    void Init() {
        if (map.isEmpty()) {
            LoadRecCoursesData();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadRecExamsData();
                }
            }, 3000);
        } else {
            pgExams.setVisibility(View.GONE);
            pgCourses.setVisibility(View.GONE);
            if (!map.containsKey("recCourses")) {
                LoadRecCoursesData();
            }
            if (!map.containsKey("recExams")) {
                LoadRecExamsData();
            }
        }
    }


    void ImgManageClicked() {
        CloseDrawer();

        if (TextUtils.isEmpty(myPreferences.getEmail())) {
            String s = getResources().getString(R.string.sign_in);

            ShowDlg(s);
            return;
        }

        AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this);
        al.setCancelable(true);
        final View view = CustomViews.getCustomListView(MainActivity.this);
        final GoogleProgressBar progressBar = view.findViewById(R.id.pg);
        final ListView listView = view.findViewById(R.id.listView);
        progressBar.setVisibility(View.GONE);

        listView.setAdapter(new BaseAdapter() {
            final String[] data = UsertType();


            @Override
            public int getCount() {
                return data.length;
            }

            @Override
            public Object getItem(int position) {
                return data[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = CustomViews.getCustomHeader(MainActivity.this);
                ImageView imgImg = convertView.findViewById(R.id.imgImg);
                DrawMeTextView txtTitle = convertView.findViewById(R.id.txtTitle);
                txtTitle.setText((String) getItem(position));
                return convertView;
            }
        });

        al.setView(view);

        final AlertDialog alertDialog = al.create();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ms = (String) parent.getAdapter().getItem(position);
                switch (ms) {
                    case "Tutor": {
                        alertDialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, TutorDashboard.class);
                        startActivity(intent);
                        break;
                    }
                    case "Admin": {
                        alertDialog.dismiss();
                        Intent intent = new Intent(MainActivity.this, AdminPanel.class);
                        startActivity(intent);
                        break;
                    }

                    case "Student": {
                        alertDialog.dismiss();
                        break;
                    }

                }
            }
        });
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.left_from_right);
        view.startAnimation(animation);
        view.setBackgroundColor(getResources().getColor(R.color.fbutton_color_wet_asphalt));
        alertDialog.show();
    }

    String[] UsertType() {
        String who = myPreferences.getUsertype();
        if (who == null || who.isEmpty()) {
            return null;
        }

        String[] myData;
        if (who.contains(";")) {
            myData = who.split(";");
        } else {
            myData = new String[1];
            myData[0] = who;
        }

        final String[] data = new String[myData.length];
        for (int i = 0; i < myData.length; i++) {
            if (myData[i].equalsIgnoreCase("0")) {
                data[i] = "Student";
            }
            if (myData[i].equalsIgnoreCase("1")) {
                data[i] = "Tutor";
            }
            if (myData[i].equalsIgnoreCase("2")) {
                data[i] = "Admin";
            }
        }
        return data;
    }

    void LoadMyCoursesInBg() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                MainSideExpandableListAdapter.LoadData(0, MainActivity.this, myCourses, myPreferences);
                return null;
            }
        }.execute();
    }

    private void DoActionListdata(List_Side_Model list_side_model) {
        assert list_side_model != null;
        switch (list_side_model.titleDesc) {
            case List_Side_Data.Account: {
                if (TextUtils.isEmpty(myPreferences.getEmail())) {
                    String s = getResources().getString(R.string.sign_in);

                    ShowDlg(s);
                    return;
                }
                startActivity(new Intent(MainActivity.this, MyAccount.class));
                return;
            }

            case List_Side_Data.Courses: {
                startActivity(new Intent(MainActivity.this, Courses.class));
                return;
            }

            case List_Side_Data.Exams: {
                startActivity(new Intent(MainActivity.this, Exams.class));
                return;
            }
            case List_Side_Data.Lab: {
                startActivity(new Intent(MainActivity.this, Lab.class));
                return;
            }
            case List_Side_Data.Notification: {
                startActivity(new Intent(MainActivity.this, MyNotifications.class));
                return;
            }
            case List_Side_Data.Settings: {
                startActivity(new Intent(MainActivity.this, MySettings.class));
                return;
            }
            case List_Side_Data.Share: {
                TastyToast.makeText(MainActivity.this, "Share", TastyToast.LENGTH_LONG, TastyToast.INFO).show();
                //startActivity(new Intent(MainActivity.this, MyNotifications.class));
                return;
            }
            case List_Side_Data.Quit: {
                finish();
                System.exit(0);
                return;
            }
            default:
        }
    }

    private void CloseDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class Mn extends General {
    }

    public DrawerLayout drawerLayout;
    public Toolbar toolbar;
    public TextView txtTitle;
    public ImageView imgMoreCourses;
    public RecyclerView recCourses;
    public LinearLayout refreshLy;
    public TextView txtMsg;
    public GoogleProgressBar pgCourses;
    public ImageView imgMoreExams, imgVideos;
    public RecyclerView recExams;
    public GoogleProgressBar pgExams;
    public ImageView imgMenu;
    public TextView txtHeader;
    public ImageView imgSearch;
    public FloatingActionButton fab;
    public NavigationView navView;
    public ImageView imageView;
    public CircleImageView imgDashboard;
    public TextView txtNameDashboard;
    private TextView txtEmailDashboard;
    private ExpandableListView myCourses;
    private ImageView imgManage;
    private LinearLayout ss;
    private ImageView img;
    private TextView txtItem;
    private ListView listSidebar;

    public void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        imgMoreCourses = (ImageView) findViewById(R.id.imgMoreCourses);
        recCourses = (RecyclerView) findViewById(R.id.recCourses);
        refreshLy = (LinearLayout) findViewById(R.id.refreshLy);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        pgCourses = (GoogleProgressBar) findViewById(R.id.pgCourses);
        imgMoreExams = (ImageView) findViewById(R.id.imgMoreExams);
        recExams = (RecyclerView) findViewById(R.id.recExams);
        pgExams = (GoogleProgressBar) findViewById(R.id.pgExams);
        imgMenu = (ImageView) findViewById(R.id.imgMenu);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        navView = (NavigationView) findViewById(R.id.nav_view);
        imageView = (ImageView) findViewById(R.id.imageView);
        imgDashboard = (CircleImageView) findViewById(R.id.imgDashboard);
        txtNameDashboard = (TextView) findViewById(R.id.txtNameDashboard);
        txtEmailDashboard = (TextView) findViewById(R.id.txtEmailDashboard);
        myCourses = (ExpandableListView) findViewById(R.id.myCourses);
        imgManage = (ImageView) findViewById(R.id.imgManage);
        ss = (LinearLayout) findViewById(R.id.ss);
        img = (ImageView) findViewById(R.id.img);
        txtItem = (TextView) findViewById(R.id.txtItem);
        listSidebar = (ListView) findViewById(R.id.listSidebar);
        imgVideos = (ImageView) findViewById(R.id.imgVideos);
    }


    static Map<String, Object> map = new HashMap<>();


}
