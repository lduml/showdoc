package com.lduml.showdoc.adapter;

import android.content.Context;
//import android.support.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.lduml.showdoc.R;
import com.lduml.showdoc.inter.MyOnItemClickListener;
import com.lduml.showdoc.model.ItemDataList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


/*
* 适配主页ItemList的显示
* */
public class ItemDataAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private List<ItemDataList> mItemDataList;
    private Context mcontext;
    private MyOnItemClickListener mListener;// 声明自定义的接口

    public ItemDataAdapter(Context mcontext, List<ItemDataList> itemDataList){
        mItemDataList = itemDataList;
        this.mcontext = mcontext;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_list, parent, false);
            holder = new MyViewHolder(view,mListener);

          /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(V);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemClickListener.onItemLongClick(v);
                    return true;
                }
            });*/

            return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
         /* Glide.with(mcontext)
                .load("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1588799093,979234084&fm=58&bpow=500&bpoh=500")
                .into(((HomeHolder) holder).img_item_name);*/
       // holder = new HomeHolder();
         holder.tv_item_name.setText(mItemDataList.get(position).getItem_name());
        //设置tag
        holder.itemView.setTag(mItemDataList.get(position).getItem_id());
    }

 /*   @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       *//* Glide.with(mcontext)
                .load("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1588799093,979234084&fm=58&bpow=500&bpoh=500")
                .into(((HomeHolder) holder).img_item_name);*//*
        ((HomeHolder) holder).tv_item_name.setText(mItemDataList.get(position).getItem_name());
        //设置tag
        ((HomeHolder) holder).itemView.setTag(mItemDataList.get(position).getItem_id());
    }*/

    //传递
    public void setOnItemClickListener(MyOnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mItemDataList == null?0:mItemDataList.size();
    }
   /* private class HomeHolder extends MyViewHolder{
        ImageView img_item_name;
        TextView tv_item_name;
        public HomeHolder(View itemView) {
            super(itemView,mListener);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);;
            img_item_name = itemView.findViewById(R.id.img_item_name);;

        }
    }*/
}
