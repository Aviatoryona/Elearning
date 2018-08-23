package com.aviator.elearning;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.aviator.main.CustomViews;
import com.aviator.elearning.aviator.main.MyPreferences;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.CommonsPkg.ParseJsonsHelper;
import com.aviator.elearning.el.interfaces.ShowHide;
import com.aviator.elearning.el.models.MessageModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.rafakob.drawme.DrawMeEditText;
import com.rafakob.drawme.DrawMeTextView;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import avfont.com.aviator.aviatorfontlib.AvFonts;

public class TutorAddExamQnOptions extends AppCompatActivity implements ShowHide {

    MyPreferences myPreferences;
    String EXAMUNIGID, EXAMQN, QNID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutor_add_exam_qn_options);
        myPreferences = new MyPreferences(this);
        initViews();

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle;
        bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("qn_examuniqid")) {
                EXAMUNIGID = bundle.getString("qn_examuniqid");
            } else {
                Toast.makeText(this, "Error encountered", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            if (bundle.containsKey("qn_examqn")) {
                EXAMQN = bundle.getString("qn_examqn");
                txtQn.setText(EXAMQN);
                txtQn.setTypeface(AvFonts.RobotoRegular(this));
            }

            if (bundle.containsKey("qn_id")) {
                QNID = bundle.getString("qn_id");
            }


        }

        AfterInit();
        pgLoading.setVisibility(View.GONE);
    }

    private void AsqQn() {
        final AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        aBuilder.setCancelable(true);
        View view = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.ask_qn, null, false);
        final DrawMeEditText drawMeEditText = view.findViewById(R.id.edtQn);
        FloatingActionButton fabSend = view.findViewById(R.id.fab);
        aBuilder.setView(view);
        final AlertDialog alertDialog = aBuilder.create();
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawMeEditText.getText().toString().isEmpty()) {

                    Inner inner = new Inner();
                    inner.setQnnumber(innerArrayList.size());
                    inner.setOption(drawMeEditText.getText().toString());

                    innerArrayList.add(inner);
                    if (innerArrayList.size() == 6) {
                        fabAdd.setVisibility(View.GONE);
                    }

                    assert innerArrayList != null && !innerArrayList.isEmpty();
                    listOpts.setAdapter(new MyLADAPTER(innerArrayList));
                    alertDialog.dismiss();
                } else {
                    drawMeEditText.setError("Fill in here *");
                }
            }
        });
        alertDialog.show();
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
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsqQn();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (innerArrayList == null) {
                    Toast.makeText(TutorAddExamQnOptions.this, "Saved", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (innerArrayList.isEmpty()) {
                    TastyToast.makeText(TutorAddExamQnOptions.this, "Kindly add options", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
                    return;
                }

                if (opt_optionAnswer == null) {
                    TastyToast.makeText(TutorAddExamQnOptions.this, "Kindly set answer to question", TastyToast.LENGTH_LONG, TastyToast.WARNING).show();
                    return;
                }


                AddExmQNOptions();

            }
        });

        listOpts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Inner inner = (Inner) parent.getAdapter().getItem(position);
                assert inner != null;
                opt_optionAnswer = inner.getOption();
            }
        });

    }

    void AddExmQNOptions() {
        Map<String, String> paMap = new HashMap<>();
        paMap.put("target", "exams");
        paMap.put("action", "addexamqnoptions");
        paMap.put("examuniqid", EXAMUNIGID);
        paMap.put("qnid", QNID);
        paMap.put("option1", innerArrayList.get(0).getOption());
        paMap.put("option2", innerArrayList.get(1).getOption());
        paMap.put("option3", innerArrayList.get(2).getOption());
        paMap.put("option4", innerArrayList.get(3).getOption());
        paMap.put("option5", innerArrayList.get(4).getOption());
        paMap.put("option6", innerArrayList.get(5).getOption());
        paMap.put("optionAns", opt_optionAnswer);
        paMap.put("email", myPreferences.getEmail());

        AddExmQNOptions_(paMap);
    }


    void AddExmQNOptions_(final Map<String, String> paMap) {
        pgLoading.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = VolleySingleton.volleyInstance(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, new SpaceCraft().getUrl(TutorAddExamQnOptions.this),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        requestQueue.stop();

                        pgLoading.setVisibility(View.GONE);

                        MessageModel messageModel = new ParseJsonsHelper(TutorAddExamQnOptions.this).getMessageModel(s);
                        if (messageModel != null) {
                            if (!messageModel.isError()) {

                                TastyToast.makeText(TutorAddExamQnOptions.this, messageModel.getMessage(), TastyToast.LENGTH_LONG
                                        , TastyToast.SUCCESS).show();

                                innerArrayList.clear();
                                opt_optionAnswer = null;
                                finish();
                            } else {
                                Snackbar.make(parent, messageModel.getMessage(), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            Snackbar.make(parent, "Error encountered", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AddExmQNOptions_(paMap);
                                        }
                                    }).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
//                ShowMsg(SpaceCraft.CONNECTION_ERROR_MSG);
                Snackbar.make(parent, SpaceCraft.CONNECTION_ERROR_MSG, Snackbar.LENGTH_INDEFINITE)
                        .setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AddExmQNOptions_(paMap);
                            }
                        }).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return paMap;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    class Inner {
        int qnnumber;
        String option;

        public int getQnnumber() {
            return qnnumber;
        }

        public void setQnnumber(int qnnumber) {
            this.qnnumber = qnnumber;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }
    }

    class MyLADAPTER extends BaseAdapter {
        ArrayList<Inner> myInnerArrayList;

        MyLADAPTER(ArrayList<Inner> myInnerArrayList) {
            this.myInnerArrayList = myInnerArrayList;
        }

        @Override
        public int getCount() {
            return myInnerArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return myInnerArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Inner model = (Inner) getItem(position);
            convertView = CustomViews.getCustomHeader(TutorAddExamQnOptions.this);
            ImageView imgImg = convertView.findViewById(R.id.imgImg);
            DrawMeTextView txtTitle = convertView.findViewById(R.id.txtTitle);
            txtTitle.setTypeface(AvFonts.RobotoBold(TutorAddExamQnOptions.this));
            txtTitle.setText(model.getOption());
            return convertView;
        }
    }

    private CoordinatorLayout parent;
    private Toolbar toolbar;
    private TextView txtQn;
    private ListView listOpts;
    private FloatingActionButton fabAdd;
    private GoogleProgressBar pgLoading;
    private FloatingActionButton fab;

    public void initViews() {
        parent = (CoordinatorLayout) findViewById(R.id.parent);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtQn = (TextView) findViewById(R.id.txtQn);
        listOpts = (ListView) findViewById(R.id.listOpts);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        pgLoading = (GoogleProgressBar) findViewById(R.id.pgLoading);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }


    String opt_optionAnswer;
    ArrayList<Inner> innerArrayList = new ArrayList<>();
}
