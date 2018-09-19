package com.lduml.showdoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lduml.showdoc.model.PaCatalogs;
import com.lduml.showdoc.model.Data;
import com.lduml.showdoc.model.Menu;
import com.lduml.showdoc.model.Pages;

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
        List<PaCatalogs> paCatalogs_list = menu.getCatalogs();

    }
}
