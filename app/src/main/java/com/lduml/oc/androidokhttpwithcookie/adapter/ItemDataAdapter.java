package com.lduml.oc.androidokhttpwithcookie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.lduml.oc.androidokhttpwithcookie.R;
import com.lduml.oc.androidokhttpwithcookie.model.ItemDataList;
import java.util.List;


/*
* 适配主页ItemList的显示
* */
public class ItemDataAdapter extends RecyclerView.Adapter{
    private List<ItemDataList> mItemDataList;
    private Context mcontext;

    public ItemDataAdapter(Context mcontext, List<ItemDataList> itemDataList){
        mItemDataList = itemDataList;
        this.mcontext = mcontext;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeHolder holder;
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_data_list, parent, false);
            holder = new HomeHolder(view);
            return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       /* Glide.with(mcontext)
                .load("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1588799093,979234084&fm=58&bpow=500&bpoh=500")
                .into(((HomeHolder) holder).img_item_name);*/
        ((HomeHolder) holder).tv_item_name.setText(mItemDataList.get(position).getItem_name());
    }

    @Override
    public int getItemCount() {
        return mItemDataList == null?0:mItemDataList.size();
    }
    private class HomeHolder extends RecyclerView.ViewHolder{
        ImageView img_item_name;
        TextView tv_item_name;
        public HomeHolder(View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);;
            img_item_name = itemView.findViewById(R.id.img_item_name);;
        }
    }
}
