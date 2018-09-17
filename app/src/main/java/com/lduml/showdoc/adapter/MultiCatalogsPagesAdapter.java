package com.lduml.showdoc.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter;
import com.lduml.showdoc.R;
import com.lduml.showdoc.SampleChildBean;
import com.lduml.showdoc.SampleGroupBean;
import com.lduml.showdoc.inter.MyOnItemClickListener;
import com.lduml.showdoc.model.CatalogsGroupBean;
import com.lduml.showdoc.model.PagesChildBean;

import java.util.List;

public class MultiCatalogsPagesAdapter extends
        BaseExpandableRecyclerViewAdapter<CatalogsGroupBean, PagesChildBean, MultiCatalogsPagesAdapter.GroupVH, MyChildItemViewHolder> {
    private String TAG = "001-MultiCatalogsPagesAdapter";
    private List<CatalogsGroupBean> mList;

    private MyOnItemClickListener mListener;// 声明自定义的接口

    public MultiCatalogsPagesAdapter(List<CatalogsGroupBean> list) {
        mList = list;
    }

    @Override
    public void childItemOnclick(CatalogsGroupBean groupBean, PagesChildBean pagesChildBean) {
        Log.d(TAG, "8888888:         childItemOnclick: "+groupBean.getCatalogs().toString());
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public CatalogsGroupBean getGroupItem(int position) {
        return mList.get(position);
    }

    /*父菜单*/
    @Override
    public GroupVH onCreateGroupViewHolder(ViewGroup parent, int groupViewType) {
        return new GroupVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.child_item_listitem_group, parent, false));
    }

    /*子菜单*/
    @Override
    public MyChildItemViewHolder onCreateChildViewHolder(ViewGroup parent, int childViewType) {
        MyChildItemViewHolder mChildVH = new MyChildItemViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.child_item_listitem_child, parent, false),mListener);
        /*return new ChildVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.child_item_listitem_child, parent, false));*/
       /* mChildVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ChildVH");
            }
        });
        mChildVH.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d(TAG, "onLongClick: ");
                return false;
            }
        });*/

        return mChildVH;
    }

    //传递
    public void setOnItemClickListener(MyOnItemClickListener listener) {
        this.mListener = listener;
    }

    /*父菜单*/
    @Override
    public void onBindGroupViewHolder(GroupVH holder, CatalogsGroupBean mCatalogsGroupBean, boolean isExpanding) {
        holder.nameTv.setText(mCatalogsGroupBean.getCatalogs().getCat_name());
        if (mCatalogsGroupBean.isExpandable()) {
            holder.foldIv.setVisibility(View.VISIBLE);
            holder.foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding : R.drawable.ic_arrow_folding);
        } else {
            holder.foldIv.setVisibility(View.INVISIBLE);
        }
    }

    /*子菜单*/
    @Override
    public void onBindChildViewHolder(MyChildItemViewHolder holder, CatalogsGroupBean mCatalogsGroupBean, PagesChildBean mPagesChildBean) {
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
            }
        });*/
        holder.nameTv.setText(mPagesChildBean.getmPages().getPage_title());
    }




    /*父菜单*/
    public class GroupVH extends BaseExpandableRecyclerViewAdapter.BaseGroupViewHolder {
        ImageView foldIv;
        TextView nameTv;

        public GroupVH(View itemView) {
            super(itemView);
            foldIv = (ImageView) itemView.findViewById(R.id.group_item_indicator);
            nameTv = (TextView) itemView.findViewById(R.id.group_item_name);
        }

        @Override
        protected void onExpandStatusChanged(RecyclerView.Adapter relatedAdapter, boolean isExpanding) {
            foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding : R.drawable.ic_arrow_folding);
        }
    }

   /* *//*子菜单*/

  /*  @Override
    public void setListener(ExpandableRecyclerViewOnClickListener<CatalogsGroupBean, PagesChildBean> listener) {
        super.setListener(listener);
        listener.onChildClicked();
        listener.onGroupClicked();
    }*//*
    public class ChildVH extends RecyclerView.ViewHolder {
        TextView nameTv;

        public ChildVH(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.child_item_name);
        }
    }*/
}
