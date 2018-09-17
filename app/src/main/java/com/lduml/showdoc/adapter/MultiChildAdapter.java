package com.lduml.showdoc;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter;

import java.util.List;

public class MultiChildAdapter extends
        BaseExpandableRecyclerViewAdapter<SampleGroupBean, SampleChildBean, MultiChildAdapter.GroupVH, MultiChildAdapter.ChildVH> {

    private List<SampleGroupBean> mList;

    public MultiChildAdapter(List<SampleGroupBean> list) {
        mList = list;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public SampleGroupBean getGroupItem(int position) {
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
    public ChildVH onCreateChildViewHolder(ViewGroup parent, int childViewType) {
        return new ChildVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.child_item_listitem_child, parent, false));
    }

    /*父菜单*/
    @Override
    public void onBindGroupViewHolder(GroupVH holder, SampleGroupBean sampleGroupBean, boolean isExpanding) {
        holder.nameTv.setText(sampleGroupBean.getName());
        if (sampleGroupBean.isExpandable()) {
            holder.foldIv.setVisibility(View.VISIBLE);
            holder.foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding : R.drawable.ic_arrow_folding);
        } else {
            holder.foldIv.setVisibility(View.INVISIBLE);
        }
    }

    /*子菜单*/
    @Override
    public void onBindChildViewHolder(ChildVH holder, SampleGroupBean groupBean, SampleChildBean sampleChildBean) {
        holder.nameTv.setText(sampleChildBean.getName());
    }

    /*父菜单*/
    public class GroupVH extends BaseExpandableRecyclerViewAdapter.BaseGroupViewHolder {
        ImageView foldIv;
        TextView nameTv;

        GroupVH(View itemView) {
            super(itemView);
            foldIv = (ImageView) itemView.findViewById(R.id.group_item_indicator);
            nameTv = (TextView) itemView.findViewById(R.id.group_item_name);
        }

        @Override
        protected void onExpandStatusChanged(RecyclerView.Adapter relatedAdapter, boolean isExpanding) {
            foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding : R.drawable.ic_arrow_folding);
        }
    }

    /*子菜单*/
    public class ChildVH extends RecyclerView.ViewHolder {
        TextView nameTv;

        ChildVH(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.child_item_name);
        }
    }
}
