package com.lduml.showdoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hgdendi.expandablerecycleradapter.ViewProducer;
import com.lduml.showdoc.adapter.MultiCatalogsPagesAdapter;
import com.lduml.showdoc.adapter.MultiChildAdapter;
import com.lduml.showdoc.inter.MyOnItemClickListener;
import com.lduml.showdoc.model.Catalogs;
import com.lduml.showdoc.model.CatalogsGroupBean;
import com.lduml.showdoc.model.Data;
import com.lduml.showdoc.model.Menu;
import com.lduml.showdoc.model.Pages;
import com.lduml.showdoc.model.PagesChildBean;

import java.util.ArrayList;
import java.util.List;

public class MainItemInfoActivity extends AppCompatActivity {

    public String TAG = "001-MainItemInfoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_info_main);

        Data info_data = MainActivity.itemInforBean.getData();
        Log.d(TAG, "info_data: "+info_data.toString());
        Menu menu = info_data.getMenu();
        Log.d(TAG, "menu: "+menu.toString());
        List<Pages> pages_list = menu.getPages();
        for(int i=0;i<pages_list.size();i++){
            Log.d(TAG, "pages_list: "+pages_list.get(i));
        }
        //得到所有的一级目录
        List<Catalogs> catalogs_list = menu.getCatalogs();
        //adapter 一级目录
        List<CatalogsGroupBean> mCatalogsGroupBean_list = new ArrayList<>();

        for(int i=0;i<catalogs_list.size();i++){
            Log.d(TAG, "catalogs_list: "+catalogs_list.get(i));
            //catalogs 一级目录下的pages 装入child_pages_obj_list中
            List<Pages> child_pages_obj_list = catalogs_list.get(i).getPages();

          //  for (int k = 0; k < catalogs_list.size(); k++){
                final List<PagesChildBean> mPagesChildBean_list = new ArrayList<>();
                for (int j = 0; j < child_pages_obj_list.size(); j++){
                    mPagesChildBean_list.add(new PagesChildBean(child_pages_obj_list.get(j)));
                }
                mCatalogsGroupBean_list.add(new CatalogsGroupBean(mPagesChildBean_list,catalogs_list.get(i)));
         //   }
        }


/*
        List<SampleGroupBean> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            final List<SampleChildBean> childList = new ArrayList<>(i);
            for (int j = 0; j < i; j++) {
                childList.add(new SampleChildBean("child " + i));
            }
            list.add(new SampleGroupBean(childList, "group " + i));
        }*/

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      //  MultiChildAdapter adapter = new MultiChildAdapter(list);
        MultiCatalogsPagesAdapter adapter = new MultiCatalogsPagesAdapter(mCatalogsGroupBean_list);
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
        /*adapter.setHeaderViewProducer(new ViewProducer() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
                return new DefaultEmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_header, parent, false)
                );
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder) {

            }
        }, false);*/
        recyclerView.setAdapter(adapter);

       /* adapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.d(TAG, "onItemClick: ");
            }

            @Override
            public void onItemLongClick(View view, int postion) {
                Log.d(TAG, "onItemLongClick: ");
            }
        });*/
        
    }
}
