package com.lduml.showdoc;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lduml.showdoc.Http.DataReceiverCallBack;
import com.lduml.showdoc.Http.NetOkhttp;
import com.lduml.showdoc.model.Catalogs;
import com.lduml.showdoc.model.PaCatalogs;
import com.lduml.showdoc.model.Data;
import com.lduml.showdoc.model.Menu;

import com.lduml.showdoc.model.PageInforBean;
import com.lduml.showdoc.model.PageInforBeanData;
import com.lduml.showdoc.model.Pages;
import com.lduml.showdoc.adapter.TreeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import okhttp3.FormBody;

import static com.lduml.showdoc.Global.PAGE_INFO_URL;
import static com.lduml.showdoc.MainActivity.Default_Catalogs_Name;
import static com.lduml.showdoc.MainActivity.default_pages_list;
import static com.lduml.showdoc.MainActivity.paCatalogs_list;

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
        PaCatalogs parent = (PaCatalogs) list.get(0);
        adapter.addAllChild(parent.getPages(), 0 + 1);
        adapter.addAllChild(parent.getCatalogs(), 0 + 1+ parent.getPages().size());
    }
    public void initView(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));//间距设置，完全copy了别人的代码。。
        recyclerView.setItemAnimator(new SlideInUpAnimator());//这是一个开源的动画效果，非常棒的哦
        list = new ArrayList();
        adapter = new TreeAdapter(this, list, R.layout.layout_treerecycler_item,
                new int[]{R.id.parent_name, R.id.child_name, R.id.third_child_name});
        //这里的点击事件很重要
        adapter.setOnItemClickLitener(new TreeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int a =0 , b =0;
                if (list.get(position) instanceof PaCatalogs){//判断是否为父
                    PaCatalogs parent = (PaCatalogs) list.get(position);
                    if ((position + 1) == list.size()) {//判断是否为最后一个元素
                        //传入对象、在下一位置添加显示所有子元素,以及二级子元素
                        if(parent.getPages()!=null){ b = parent.getPages().size(); }else { b = 0; }
                        adapter.addAllChild(parent.getPages(), position + 1);
                        adapter.addAllChild(parent.getCatalogs(), position + 1+b);

                    } else {
                        if ((list.get(position + 1) instanceof Pages)) {
                            //如果下一个不是父则表示为展开状态，需要折叠--折叠时也需要折叠pages和lastcatalog两个的数量
                            //此处需要判断级目录是否展开，若次级目录展开，则是收起全部的个数，若次级目录未展开，则是收起部分的个数
                            //为了出现越界情况，暂时只收起部分，各级收起各级的部分，即只收起pages部分的
                            //如果属于pages，就收起pages部分
                            Log.d(TAG, "onItemClick: "+position);
                            if(parent.getPages()!=null){ b = parent.getPages().size(); }else { b = 0; }
                            //adapter.deleteAllChild(position + 1, a + b);b
                            adapter.deleteAllChild(position + 1, b);
                        } else if((list.get(position + 1) instanceof Catalogs)){
                            //如果属于Catalogs，就收起Catalogs部分
                            if(parent.getCatalogs()!=null){ a = parent.getCatalogs().size(); }else { a = 0; }
                            adapter.deleteAllChild(position + 1, a);
                        } else {//如果是父则表示为折叠状态需要添加
                            if(parent.getPages()!=null){ b = parent.getPages().size(); }else { b = 0; }
                            adapter.addAllChild(parent.getPages(), position + 1);
                            adapter.addAllChild(parent.getCatalogs(), position + 1+b);
                        }
                    }
                }else if (list.get(position) instanceof Catalogs){// 是子目录的前提下，判断次级目录是否需要展开
                    Catalogs catalogs = (Catalogs) list.get(position);
                    //如果下一个是pages说明需要折叠--否则展开
                    if((position + 1) == list.size()){
                        //需要展开 pages
                        adapter.addAllChild(catalogs.getPages(), position + 1);
                    }else if(list.get(position + 1) instanceof Pages){
                        //折叠
                        adapter.deleteAllChild(position + 1, catalogs.getPages().size());
                    }else{
                        //需要展开 pages
                        adapter.addAllChild(catalogs.getPages(), position + 1);
                    }

                }else if(list.get(position) instanceof Pages){
                    Pages child = (Pages) list.get(position);
                    get_page_info(child.getPage_id());
                    Toast.makeText(getApplicationContext(), child.getPage_title(), Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("001", "else--------------: "+list.get(position).toString());
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
        Gson gson = new Gson();
        // gson.toJson(list.get(position))
        Log.d("001", "else----menu----------: "+gson.toJson(menu));
       // Log.d(TAG, "menu: "+menu.toString());
        /*-------------------处理未分类的pages-------------------------*/
        //默认未分配 目录的 所有 pages list
        //List<Pages> default_pages_list = menu.getPages();

        //得到所有的一级目录
       // List<PaCatalogs> paCatalogs_list = menu.getCatalogs();
        /*List<NewCatalogs> newCatalogsList = new ArrayList<>() ;
        //转换类的名字
        for(int i=0;i<paCatalogs_list.size();i++){
            NewCatalogs newCatalogs = (NewCatalogs)(paCatalogs_list.get(i));
            newCatalogsList.add(newCatalogs);
        }*/

        Log.d(TAG, "paCatalogs_list.size: "+paCatalogs_list.size());
        Log.d(TAG, "list.size(): "+list.size());
        for(int i=0;i<paCatalogs_list.size();i++){//通过循环来赋值给另一个List
            Object object=paCatalogs_list.get(i);
            list.add(object);
        }
        Log.d(TAG, "paCatalogs_list.size: "+paCatalogs_list.size());
        Log.d(TAG, "list.size(): "+list.size());
        //list = paCatalogs_list;
       // list.add(paCatalogs_list);
        adapter.notifyDataSetChanged();

        /*for(int i = 0; i < paCatalogs_list.size(); i++){
            List<LastPages> lastpages = paCatalogs_list.get(i).getLastPages();
            for(int j=0;j<lastpages.size();j++){

            }
            List<Catalogs> catalogs = paCatalogs_list.get(i).getLastCatalogs();

        }

        //一级--目录
        for (int i = 0; i < paCatalogs_list.size(); i++){
            MenuEntity menuEntity = new MenuEntity();
            List<Pages> child_pages_obj_list = paCatalogs_list.get(i).getPages();
            List<Catalogs> mLastCatalog_obj_list = paCatalogs_list.get(i).getCatalogs();
            menuEntity.setId(i);
            menuEntity.setName(paCatalogs_list.get(i).getCat_name());
            List<MenuEntity.FirstPagesEntity> firstPagesEntityList = new ArrayList<>();
            //二级--内容
            for (int j = 0; j < child_pages_obj_list.size(); j++){
                MenuEntity.FirstPagesEntity firstPagesEntity = new MenuEntity.FirstPagesEntity();
                firstPagesEntity.setId(child_pages_obj_list.get(j).getPage_id());
                firstPagesEntity.setName(child_pages_obj_list.get(j).getPage_title());

                List<MenuEntity.FirstCatalogsEntity> firstCatalogsEntityList = new ArrayList<>();
                List<MenuEntity.FirstPagesEntity.ChildCatalogEntity.SecondChildPagesEntity> second_children_list = new ArrayList<>();
                for(int m=0;m<mLastCatalog_obj_list.size();m++){
                    List<Pages> thir_child_pages_obj_list = mLastCatalog_obj_list.get(m).getPages();
                    MenuEntity.FirstPagesEntity.ChildCatalogEntity mChildCatalogEntity_enity = new MenuEntity.FirstPagesEntity.ChildCatalogEntity();
                    Log.d(TAG, "mLastCatalog_obj_list.get(m).getCat_name(): m="+m+": "+mLastCatalog_obj_list.get(m).getCat_name());
                    mChildCatalogEntity_enity.setId(""+m+i);
                    mChildCatalogEntity_enity.setName(mLastCatalog_obj_list.get(m).getCat_name());
                    //若有--三级内容
                    for(int k=0;k<thir_child_pages_obj_list.size();k++){
                        MenuEntity.FirstPagesEntity.ChildCatalogEntity.SecondChildPagesEntity mSecondChild_enity = new MenuEntity.FirstPagesEntity.ChildCatalogEntity.SecondChildPagesEntity();
                        mSecondChild_enity.setId(""+k+i);
                        mSecondChild_enity.setName(thir_child_pages_obj_list.get(k).getPage_title());
                        second_children_list.add(mSecondChild_enity);
                    }
                    mChildCatalogEntity_enity.setSecondChildEntities(second_children_list);
                    firstCatalogsEntityList.add(mChildCatalogEntity_enity);
                }

                firstPagesEntity.setFirstCatalogsEntityList(firstCatalogsEntityList);
                firstPagesEntityList.add(firstPagesEntity);
            }
            menuEntity.setChildren(firstPagesEntityList);*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        paCatalogs_list.clear();
    }
}
