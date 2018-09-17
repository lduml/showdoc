/**
  * Copyright 2018 bejson.com 
  */
package com.lduml.showdoc.model;
import android.support.annotation.NonNull;

import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter;
//import com.lduml.showdoc.SampleChildBean;

import java.util.List;

/**
 * Auto-generated: 2018-09-16 8:48:13
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CatalogsGroupBean implements BaseExpandableRecyclerViewAdapter.BaseGroupBean<PagesChildBean>{

    private List<PagesChildBean> mlist;
    private Catalogs mcatalogs;

    public CatalogsGroupBean(@NonNull List<PagesChildBean> list,@NonNull Catalogs catalogs){
        mlist = list;
        mcatalogs = catalogs;
    }

    public Catalogs getCatalogs(){
        return mcatalogs;
    }

    @Override
    public int getChildCount() {
        return mlist.size();
    }

    @Override
    public PagesChildBean getChildAt(int index) {
        return mlist.size() <= index ? null : mlist.get(index);
    }

    @Override
    public boolean isExpandable() {
        return getChildCount() > 0;
    }

}