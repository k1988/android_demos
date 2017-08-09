package com.zhaoxiuyuan.demos.layouts;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listview)
    ListView mListview;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randData();
            }
        });
        randData();
    }

    private void randData(){
        ArrayList<String> testData = new ArrayList<>(20);
        Random rand = new Random();
        for (int i = 0; i< 20; i++){
            testData.add(String.valueOf(rand.nextInt(9999)));
        }
        if (mAdapter == null) {
            mAdapter = new MyAdapter(this, testData);
        } else {
            mAdapter.setList(testData);
        }
        mListview.setAdapter(mAdapter);
    }
}
