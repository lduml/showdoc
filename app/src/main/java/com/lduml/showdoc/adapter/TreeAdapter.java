package com.lduml.showdoc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lduml.showdoc.model.Catalogs;
import com.lduml.showdoc.model.PaCatalogs;
import com.lduml.showdoc.model.Pages;

import java.util.List;

/**
 * Created by 剑雨丶游魂 on 2016/7/18.
 */
public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.MyViewHolder>{
    Context context;
    List list;
    Integer layout;
    int[] to;

    public TreeAdapter (Context context, List<Object> list, Integer layout, int[] to){
        this.context = context;
        this.list = list;
        this.layout = layout;
        this.to = to;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickLitener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    @Override
    public TreeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater
                .from(context).inflate(layout, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(final TreeAdapter.MyViewHolder holder, int position) {
        //显示父视图或子视图
        if (list.get(position) instanceof PaCatalogs){
            holder.child_name.setVisibility(View.GONE);
            holder.third_child_name.setVisibility(View.GONE);
            holder.parent_name.setVisibility(View.VISIBLE);
            PaCatalogs parent = (PaCatalogs) list.get(position);
            Log.d("001", "parentEntity: "+parent.getCat_name());
            holder.parent_name.setText(parent.getCat_name());
        }else if (list.get(position) instanceof Catalogs){
            holder.parent_name.setVisibility(View.GONE);
            holder.third_child_name.setVisibility(View.GONE);
            holder.child_name.setVisibility(View.VISIBLE);
            Catalogs child = (Catalogs) list.get(position);
            Log.d("001", "FirstPagesEntity: "+child.getCat_name());
            holder.child_name.setText(child.getCat_name());
        }else if (list.get(position) instanceof Pages) {
            holder.parent_name.setVisibility(View.GONE);
            holder.child_name.setVisibility(View.GONE);
            holder.third_child_name.setVisibility(View.VISIBLE);
            Pages child = (Pages) list.get(position);
            Log.d("001", "SecondChildPagesEntity: "+child.getPage_title());
            holder.third_child_name.setText(child.getPage_title());
        }else{
            Gson gson = new Gson();
           // gson.toJson(list.get(position))
            Log.d("001", "else--------------: "+gson.toJson(list.get(position)));
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView parent_name;
        private TextView child_name;
        private TextView third_child_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            parent_name = (TextView) itemView.findViewById(to[0]);
            child_name = (TextView) itemView.findViewById(to[1]);
            third_child_name = (TextView) itemView.findViewById(to[2]);
        }
    }

    /**
     * 添加所有child
     * @param lists
     * @param position
     */
    public void addAllChild(List<?> lists, int position) {
        if(lists!=null){
            list.addAll(position, lists);
            notifyItemRangeInserted(position, lists.size());
        }
    }

    /**
     * 删除所有child
     * @param position
     * @param itemnum
     */
    public void deleteAllChild(int position, int itemnum) {
        for (int i = 0; i < itemnum; i++) {
            list.remove(position);
        }
        notifyItemRangeRemoved(position, itemnum);
    }
}
