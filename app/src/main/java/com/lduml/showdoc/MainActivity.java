package com.lduml.showdoc;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;;
import android.util.Log;
import android.view.View;

/*import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;*/
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lduml.showdoc.Http.DataReceiverCallBack;
import com.lduml.showdoc.Http.NetOkhttp;
import com.lduml.showdoc.adapter.ItemDataAdapter;
import com.lduml.showdoc.inter.MyOnItemClickListener;
import com.lduml.showdoc.model.Catalogs;
import com.lduml.showdoc.model.ItemDataList;
import com.lduml.showdoc.model.ItemInforBean;
import com.lduml.showdoc.model.PaCatalogs;
import com.lduml.showdoc.model.Pages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

import static com.lduml.showdoc.Global.ITEM_INFO_URL;
import static com.lduml.showdoc.Global.ITEM_LIST_URL;

public class MainActivity extends AppCompatActivity {

    //主页目录显示RecyclerView
    private RecyclerView mRecyclerItemDataView;
    //主页目录的适配器
    private ItemDataAdapter mItemDataAdapter;
    //主页目录的数据list
    private List<ItemDataList> mItemDataList = new ArrayList<>();
    public String TAG = "001 - MainActivity";
    public static String Default_Catalogs_Name = "默认目录";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化OkHttpClient以及cookie的持久化存储
        NetOkhttp.init_OkHttpClient(this);
        //初始化RecyclerView
        init_mRecycleritemdataView();

        //item点击事件 -- 点击 网络 获取子目录MainTreeActivity中显示
        mItemDataAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                /*item_id:52508281797728  keyword:   default_page_id:0*/
                Log.d(TAG, "onItemClick: "+mItemDataList.get(postion).toString());
                String item_id = mItemDataList.get(postion).getItem_id();
                Default_Catalogs_Name = mItemDataList.get(postion).getItem_name();
                http_post_get_item_infor(item_id,"","0");
            }
            @Override
            public void onItemLongClick(View view, int postion) {
                Log.d(TAG, "onItemLongClick: ");
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*获取itemlist 数据*/
        if(!run_get_item_list){
            get_item_list();
        }
    }

    /*item_id:52508281797728
        keyword:
        default_page_id:0*/
    public static ItemInforBean itemInforBean;
    public static List<Pages> default_pages_list = new ArrayList<>();
    public static List paCatalogs_list = new ArrayList<>();
    public void http_post_get_item_infor(String item_id,String keyword,String default_page_id){
        FormBody body = new FormBody.Builder()
                    .add("item_id", item_id)
                    .add("keyword", keyword)
                    .add("default_page_id", default_page_id)
                    .build();
        NetOkhttp.HttpPostWithCookie(ITEM_INFO_URL,body , new DataReceiverCallBack() {
            @Override
            public void netSuccess(String data) {
                Log.d(TAG, "http_post_get_item_infor netSuccess: "+data);
                try {
                    /*获取数据data--通过gson.fromJson转为对象*/
                    Gson gson= new Gson();
                    JSONObject jsonObject = new JSONObject(data);
                    int error_code = jsonObject.optInt("error_code");
                    Log.d(TAG, "netSuccess: error_code"+error_code);
                    if(error_code == 0){
                        String infor_data = jsonObject.optString("data");
                        JSONObject jsonObject_menus = new JSONObject(infor_data);
                        String menus = jsonObject_menus.optString("menu");
                        String item_id = jsonObject_menus.optString("item_id");
                        String item_domain = jsonObject_menus.optString("item_domain");
                        String is_archived = jsonObject_menus.optString("is_archived");
                        String item_name = jsonObject_menus.optString("item_name");
                        String default_page_id = jsonObject_menus.optString("default_page_id");
                        int default_cat_id2 = jsonObject_menus.optInt("default_cat_id2");
                        int default_cat_id3 = jsonObject_menus.optInt("default_cat_id3");
                        String unread_count = jsonObject_menus.optString("unread_count");
                        int item_type = jsonObject_menus.optInt("item_type");
                        boolean is_login = jsonObject_menus.optBoolean("is_login");
                        boolean ItemPermn = jsonObject_menus.optBoolean("ItemPermn");
                        boolean ItemCreator = jsonObject_menus.optBoolean("ItemCreator");

                        JSONObject jsonObject_menus_ = new JSONObject(menus);

                        default_pages_list = gson.fromJson(jsonObject_menus_.opt("pages").toString(), new TypeToken<List<Pages>>(){}.getType());
                        Log.d("001", "else----default_pages_list----------: "+gson.toJson(default_pages_list));
                        JSONArray jsonArray = new JSONArray(jsonObject_menus_.optString("catalogs"));

                        if(default_pages_list.size()>0) {
                            //--------------------默认父目录-------------------
                            PaCatalogs default_parent = new PaCatalogs();
                            default_parent.setItem_id("001");
                            default_parent.setCat_name(Default_Catalogs_Name);
                            default_parent.setPages(default_pages_list);
                            paCatalogs_list.add(default_parent);
                            //list.add(default_parent);
                            //--------------------默认父目录-------------------
                        }

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject_paCatalogs_ = new JSONObject(jsonArray.get(i).toString());
                            PaCatalogs  paCatalogs = new PaCatalogs();
                            String cat_id = jsonObject_paCatalogs_.optString("cat_id");
                            String cat_name = jsonObject_paCatalogs_.optString("cat_name");
                            String item_id_ = jsonObject_paCatalogs_.optString("item_id");
                            String s_number = jsonObject_paCatalogs_.optString("s_number");
                            String addtime = jsonObject_paCatalogs_.optString("addtime");
                            String parent_cat_id = jsonObject_paCatalogs_.optString("parent_cat_id");
                            String level = jsonObject_paCatalogs_.optString("level");
                           // JSONArray jsonArray_pages = new JSONArray(jsonObject_paCatalogs_.optString("pages"));
                            List<Pages> pages = gson.fromJson(jsonObject_paCatalogs_.opt("pages").toString(), new TypeToken<List<Pages>>(){}.getType());
                            List<Catalogs> catalogs = gson.fromJson(jsonObject_paCatalogs_.opt("catalogs").toString(), new TypeToken<List<Catalogs>>(){}.getType());
                            paCatalogs.setAddtime(addtime);
                            paCatalogs.setCat_id(cat_id);
                            paCatalogs.setCat_name(cat_name);
                            paCatalogs.setItem_id(item_id_);
                            paCatalogs.setCatalogs(catalogs);
                            paCatalogs.setPages(pages);
                            paCatalogs.setLevel(level);
                            paCatalogs.setParent_cat_id(parent_cat_id);
                            paCatalogs.setS_number(s_number);
                            //Log.d(TAG, "jsonArray-----------: "+jsonArray.get(i).toString());
                            //PaCatalogs  paCatalogs = gson.fromJson(jsonArray.get(i).toString(),PaCatalogs.class);
                            //if(paCatalogs!=null){
                            paCatalogs_list.add(paCatalogs);
                          //  }
                        }
                        //paCatalogs_list = gson.fromJson(jsonObject_menus_.opt("catalogs").toString(), new TypeToken<List<PaCatalogs>>(){}.getType());
                        //Gson gson = new Gson();
                        Log.d("001", "else----paCatalogs_list----------: "+gson.toJson(paCatalogs_list));


                        itemInforBean = gson.fromJson(data, ItemInforBean.class);
                        Log.d(TAG, "itemInforBean: "+itemInforBean.toString());
                        Intent intent = new Intent(MainActivity.this, MainTreeActivity.class);//MainItemInfoActivity    MainTreeActivity
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void netFail(String faildata) {
            }
        });
    }

    public boolean run_get_item_list = false;
    public void get_item_list(){

        /*传入url----ITEM_LIST_URL*/
        /*
      "item_id": "152190162663851",
      "item_name": "\u817e\u8baf\u4e91\u6559\u7a0b",
      "item_domain": "",
      "item_type": "1",
      "last_update_time": "1536497496",
      "item_description": "",
      "is_del": "0",
      "top": 0
    * */
        NetOkhttp.http_get_with_cookie(ITEM_LIST_URL,new DataReceiverCallBack() {
            @Override
            public void netSuccess(String data) {
                try{
                    JSONArray jsonArray;
                    JSONObject json_data = new JSONObject(data);
                    int err_code = json_data.getInt("error_code");
                    if(err_code == 0){
                        jsonArray = json_data.getJSONArray("data");
                        if(jsonArray!=null){
                            //设置只更新一次
                            run_get_item_list = true;
                            mItemDataList.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject objectOne = jsonArray.optJSONObject(i);
                                ItemDataList itemDataList = new ItemDataList();
                                itemDataList.setItem_name(objectOne.optString("item_name"));
                                itemDataList.setItem_id(objectOne.optString("item_id"));
                                itemDataList.setItem_domain(objectOne.optString("item_domain"));
                                itemDataList.setItem_type(objectOne.optString("item_type"));
                                itemDataList.setLast_update_time(objectOne.optString("last_update_time"));
                                itemDataList.setItem_description(objectOne.optString("item_description"));
                                itemDataList.setIs_del(objectOne.optString("is_del"));
                                itemDataList.setTop(objectOne.optString("top"));
                                Log.d(TAG, "netSuccess: "+itemDataList.toString());
                                mItemDataList.add(itemDataList);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //初始化主页的显示
                                        mItemDataAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }else{
                        Log.d(TAG, "netSuccess: errcode"+data);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (JSONException e) {
                    Log.d(TAG, "JSONException: "+e);
                }

            }
            @Override
            public void netFail(String faildata) {
                Log.d(TAG, "netFail: "+faildata);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /*初始化列表显示--*/
    private  void init_mRecycleritemdataView(){
        /*初始化显示摄像头捕捉的画面的RecyAdapter*/
        mRecyclerItemDataView = findViewById(R.id.recy_item_data_view);
        mRecyclerItemDataView.setItemAnimator(new DefaultItemAnimator());
       // LinearLayoutManager manager = new LinearLayoutManager(this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mItemDataAdapter = new ItemDataAdapter(this,mItemDataList);
        mRecyclerItemDataView.setLayoutManager(manager);
        mRecyclerItemDataView.setAdapter(mItemDataAdapter);
        /*int space = 8;
        mRecyclerheadView.addItemDecoration(new SpacesItemDecoration(space));*/
    }

}
