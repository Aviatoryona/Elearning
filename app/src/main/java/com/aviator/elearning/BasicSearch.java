package com.aviator.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

public class BasicSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_search);
        initViews();
        setSupportActionBar(toolbar);
        String[] data = getResources().getStringArray(R.array.sample_courses);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(BasicSearch.this, Courses.class));
            }
        });

    }

    private Toolbar toolbar;
    private SearchView searchView;
    private ListView listView;
    private GoogleProgressBar pgLoading;
    private FloatingActionButton fab;

    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchView = findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.listView);
        pgLoading = (GoogleProgressBar) findViewById(R.id.pgLoading);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

}
