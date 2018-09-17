package com.lduml.oc.androidokhttpwithcookie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hgdendi.expandablerecycleradapter.ViewProducer;

import java.util.ArrayList;
import java.util.List;

public class MainItemInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_info_main);

        List<SampleGroupBean> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            final List<SampleChildBean> childList = new ArrayList<>(i);
            for (int j = 0; j < i; j++) {
                childList.add(new SampleChildBean("child " + i));
            }
            list.add(new SampleGroupBean(childList, "group " + i));
        }

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MultiChildAdapter adapter = new MultiChildAdapter(list);
        adapter.setEmptyViewProducer(new ViewProducer() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
                return new DefaultEmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_empty, parent, false)
                );
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder) {

            }
        });
        adapter.setHeaderViewProducer(new ViewProducer() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
                return new DefaultEmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_header, parent, false)
                );
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder) {

            }
        }, false);
        recyclerView.setAdapter(adapter);
    }
}
