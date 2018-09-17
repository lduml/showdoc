package com.recycler.tree;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lduml.showdoc.MainActivity;
import com.lduml.showdoc.R;
import com.lduml.showdoc.model.Catalogs;
import com.lduml.showdoc.model.CatalogsGroupBean;
import com.lduml.showdoc.model.Data;
import com.lduml.showdoc.model.Menu;
import com.lduml.showdoc.model.Pages;
import com.lduml.showdoc.model.PagesChildBean;
import com.recycler.tree.adapter.TreeAdapter;
import com.recycler.tree.entity.ParentEntity;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainTreeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TreeAdapter adapter;
    List list;
    public String TAG = "001-MainTreeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_main);
        initView();
        initData();
    }
    public void initView(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));//间距设置，完全copy了别人的代码。。
        recyclerView.setItemAnimator(new SlideInUpAnimator());//这是一个开源的动画效果，非常棒的哦
        list = new ArrayList();
        adapter = new TreeAdapter(this, list, R.layout.layout_treerecycler_item,
                new int[]{R.id.parent_name, R.id.child_name});
        //这里的点击事件很重要
        adapter.setOnItemClickLitener(new TreeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (list.get(position) instanceof ParentEntity){//判断是否为父
                    ParentEntity parent = (ParentEntity) list.get(position);
                    if ((position + 1) == list.size()) {//判断是否为最后一个元素
                        adapter.addAllChild(parent.getChildren(), position + 1);
                    } else {
                        if (list.get(position + 1) instanceof ParentEntity) {//如果是父则表示为折叠状态需要添加儿子
                            adapter.addAllChild(parent.getChildren(), position + 1);
                        } else if (list.get(position + 1) instanceof ParentEntity.ChildEntity) {//如果是儿子则表示为展开状态需要删除儿子
                            adapter.deleteAllChild(position + 1, parent.getChildren().size());
                        }
                    }
                }else {//是儿子你想干啥就干啥吧
                    ParentEntity.ChildEntity child = (ParentEntity.ChildEntity) list.get(position);
                    Toast.makeText(getApplicationContext(), child.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void initData(){

        Data info_data = MainActivity.itemInforBean.getData();
        Log.d(TAG, "info_data: "+info_data.toString());
        Menu menu = info_data.getMenu();
        Log.d(TAG, "menu: "+menu.toString());
        List<String> pages_list = menu.getPages();
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


        for (int i = 0; i < catalogs_list.size(); i++){
            ParentEntity parent = new ParentEntity();
            List<Pages> child_pages_obj_list = catalogs_list.get(i).getPages();
            parent.setId(catalogs_list.get(i).);
            parent.setName(catalogs_list.get(i).getCat_name());
            List<ParentEntity.ChildEntity> children = new ArrayList<>();
            for (int j = 0; j < child_pages_obj_list.size(); j++){
                ParentEntity.ChildEntity child = new ParentEntity.ChildEntity();
                child.setId(j);
                child.setName("我是父"+i+"的儿子" + j);
                children.add(child);
            }
            parent.setChildren(children);
            list.add(parent);
        }
        adapter.notifyDataSetChanged();
    }

}
