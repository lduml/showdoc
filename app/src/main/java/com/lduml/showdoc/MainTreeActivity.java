package com.lduml.showdoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lduml.showdoc.Http.DataReceiverCallBack;
import com.lduml.showdoc.Http.NetOkhttp;
import com.lduml.showdoc.model.Catalogs;
import com.lduml.showdoc.model.CatalogsGroupBean;
import com.lduml.showdoc.model.Data;
import com.lduml.showdoc.model.ItemInforBean;
import com.lduml.showdoc.model.Menu;
import com.lduml.showdoc.model.PageInforBean;
import com.lduml.showdoc.model.PageInforBeanData;
import com.lduml.showdoc.model.Pages;
import com.lduml.showdoc.model.PagesChildBean;
import com.lduml.showdoc.adapter.TreeAdapter;
import com.lduml.showdoc.entity.ParentEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.FormBody;

import static com.lduml.showdoc.Global.PAGE_INFO_URL;
import static com.lduml.showdoc.MainActivity.Default_Catalogs_Name;

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
        open_default_catalogs();
    }
    public void open_default_catalogs(){
        ParentEntity parent = (ParentEntity) list.get(0);
        adapter.addAllChild(parent.getChildren(), 0 + 1);
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
                            Log.d(TAG, "onItemClick: "+position);
                            adapter.addAllChild(parent.getChildren(), position + 1);
                        } else if (list.get(position + 1) instanceof ParentEntity.ChildEntity) {//如果是儿子则表示为展开状态需要删除儿子
                            adapter.deleteAllChild(position + 1, parent.getChildren().size());
                        }
                    }
                }else {//是子item
                    ParentEntity.ChildEntity child = (ParentEntity.ChildEntity) list.get(position);
                    get_page_info(child.getId());
                    Toast.makeText(getApplicationContext(), child.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public static String str_markdown = "# Hello World!";
    /*获取markdown页面，并显示*/
    public void get_page_info(String id){
        FormBody body = new FormBody.Builder()
                .add("page_id", id)
                .build();
        NetOkhttp.HttpPostWithCookie(PAGE_INFO_URL,body , new DataReceiverCallBack() {
            @Override
            public void netSuccess(String data) {

                try {
                    Gson gson= new Gson();
                    JSONObject jsonObject = new JSONObject(data);
                    int error_code = jsonObject.optInt("error_code");
                    if(error_code == 0) {
                        PageInforBean pageInforBean = gson.fromJson(data, PageInforBean.class);
                        Log.d(TAG, "\nitemInforBean: " + pageInforBean.toString());
                        PageInforBeanData pageInforBeanData = pageInforBean.getData();
                        str_markdown = pageInforBeanData.getPage_content();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(MainTreeActivity.this, PreviewActivity.class);//MainItemInfoActivity    MainTreeActivity
                startActivity(intent);
            }

            @Override
            public void netFail(String faildata) {

            }
        });
    }


    public void initData(){

        Data info_data = MainActivity.itemInforBean.getData();
        Log.d(TAG, "info_data: "+info_data.toString());
        Menu menu = info_data.getMenu();
        Log.d(TAG, "menu: "+menu.toString());
        /*-------------------处理未分类的pages-------------------------*/
        //默认未分配 目录的 所有 pages list
        List<Pages> default_pages_list = menu.getPages();
        if(default_pages_list.size()>0){
            List<ParentEntity.ChildEntity> default_children = new ArrayList<>();
            for(int i=0;i<default_pages_list.size();i++){
                Log.d(TAG, "pages_list: "+default_pages_list.get(i));
                ParentEntity.ChildEntity child = new ParentEntity.ChildEntity();
                child.setId(default_pages_list.get(i).getPage_id());
                child.setName(default_pages_list.get(i).getPage_title());
                default_children.add(child);
            }
            /*-------------------处理未分类的pages-------------------------*/
            /*--------------------默认父目录-------------------*/
            ParentEntity default_parent = new ParentEntity();
            default_parent.setId(0);
            default_parent.setName(Default_Catalogs_Name);
            default_parent.setChildren(default_children);
            list.add(default_parent);
            /*--------------------默认父目录-------------------*/
        }


        //得到所有的一级目录
        List<Catalogs> catalogs_list = menu.getCatalogs();
        for (int i = 0; i < catalogs_list.size(); i++){
            ParentEntity parent = new ParentEntity();
            List<Pages> child_pages_obj_list = catalogs_list.get(i).getPages();
            parent.setId(i);
            parent.setName(catalogs_list.get(i).getCat_name());
            List<ParentEntity.ChildEntity> children = new ArrayList<>();
            for (int j = 0; j < child_pages_obj_list.size(); j++){
                ParentEntity.ChildEntity child = new ParentEntity.ChildEntity();
                child.setId(child_pages_obj_list.get(j).getPage_id());
                child.setName(child_pages_obj_list.get(j).getPage_title());
                children.add(child);
            }
            parent.setChildren(children);
            list.add(parent);
        }
        adapter.notifyDataSetChanged();
    }

}
