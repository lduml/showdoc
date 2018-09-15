package com.lduml.oc.androidokhttpwithcookie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lduml.oc.androidokhttpwithcookie.Http.DataReceiverCallBack;
import com.lduml.oc.androidokhttpwithcookie.Http.NetOkhttp;
import com.lduml.oc.androidokhttpwithcookie.adapter.ItemDataAdapter;
import com.lduml.oc.androidokhttpwithcookie.model.ItemDataList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.lduml.oc.androidokhttpwithcookie.Global.ITEM_LIST_URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerItemDataView;

    private ItemDataAdapter mItemDataAdapter;

    private List<ItemDataList> mItemDataList = new ArrayList<>();
   // public static Context mcontext;
    public String TAG = "001 - MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // mcontext = this;
        //初始化OkHttpClient以及cookie的持久化存储
        NetOkhttp.init_OkHttpClient(this);
        init_mRecycleritemdataView();

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
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mItemDataAdapter = new ItemDataAdapter(this,mItemDataList);
        mRecyclerItemDataView.setLayoutManager(manager);
        mRecyclerItemDataView.setAdapter(mItemDataAdapter);
        /*int space = 8;
        mRecyclerheadView.addItemDecoration(new SpacesItemDecoration(space));*/
    }

}
