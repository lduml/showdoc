package com.lduml.showdoc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/*import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;*/
import com.google.gson.Gson;
import com.lduml.showdoc.Http.DataReceiverCallBack;
import com.lduml.showdoc.Http.NetOkhttp;
import com.lduml.showdoc.adapter.ItemDataAdapter;
import com.lduml.showdoc.inter.MyOnItemClickListener;
import com.lduml.showdoc.model.Catalogs;
import com.lduml.showdoc.model.Data;
import com.lduml.showdoc.model.ItemDataList;
import com.lduml.showdoc.model.ItemInforBean;
import com.lduml.showdoc.model.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

import static com.lduml.showdoc.Global.ITEM_INFO_URL;
import static com.lduml.showdoc.Global.ITEM_LIST_URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerItemDataView;

    private ItemDataAdapter mItemDataAdapter;

    private List<ItemDataList> mItemDataList = new ArrayList<>();
   // public static Context mcontext;
    public String TAG = "001 - MainActivity";
    public static String Default_Catalogs_Name = "目录";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // mcontext = this;
        //初始化OkHttpClient以及cookie的持久化存储
        NetOkhttp.init_OkHttpClient(this);
        init_mRecycleritemdataView();

        mItemDataAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                /*item_id:52508281797728
                    keyword:
                    default_page_id:0*/
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
        get_item_list();

    }

    /*item_id:52508281797728
        keyword:
        default_page_id:0*/
    public static ItemInforBean itemInforBean;
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
                    Gson gson= new Gson();
                    /*JSONObject jsonobject = JSONObject.fromObject(jsonStr);
　　                  User user= (User)JSONObject.toBean(object,User.class);*/
                    JSONObject jsonObject = new JSONObject(data);
                    int error_code = jsonObject.optInt("error_code");
                    int error_code2 = jsonObject.optInt("jsonObject");
                    Log.d(TAG, "netSuccess: error_code"+error_code+" error_code2:"+error_code2);
                    if(error_code == 0){
                        itemInforBean = gson.fromJson(data, ItemInforBean.class);
                        Log.d(TAG, "\nitemInforBean: "+itemInforBean.toString());
                        Intent intent = new Intent(MainActivity.this, MainTreeActivity.class);//MainItemInfoActivity    MainTreeActivity
                        startActivity(intent);
                     //   finish();
                        /*Data info_data = itemInforBean.getData();
                        Log.d(TAG, "\ninfo_data: "+info_data.toString());
                        Menu menu = info_data.getMenu();
                        Log.d(TAG, "\nmenu: "+menu.toString());
                        List<String> pages_list = menu.getPages();
                        for(int i=0;i<pages_list.size();i++){
                            Log.d(TAG, "\npages_list: "+pages_list.get(i));
                        }
                        List<Catalogs> catalogs_list = menu.getCatalogs();
                        for(int i=0;i<catalogs_list.size();i++){
                            Log.d(TAG, "\ncatalogs_list: "+catalogs_list.get(i));
                        }*/
                       // String info_data = jsonObject.optString("data");
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
